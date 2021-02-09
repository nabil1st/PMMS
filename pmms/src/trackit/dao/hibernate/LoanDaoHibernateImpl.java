/*
 * JCatalog Project
 */
package trackit.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
//
import trackit.businessobjects.CurrencyOnHandType;
import trackit.businessobjects.Deposit;
import trackit.businessobjects.Loan;
import trackit.businessobjects.TransactionType;
import trackit.dao.LoanDao;
import trackit.dao.UserDao;

/**
 * The Hibernate implementation of the <code>UserDao</code>.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 * @see UserDao
 */
public class LoanDaoHibernateImpl extends HibernateDaoSupport
        implements LoanDao {
    /**
     * Default constructor.
     */
    public LoanDaoHibernateImpl() {
            super();
    }

    /**
     * @see UserDao#getUser(String)
     */
    public Loan getLoan(String id) {
            Loan ex = (Loan)getHibernateTemplate().load(Loan.class, Long.parseLong(id));
            return ex;
    }

    public Loan saveLoan(Loan ex) {
        getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
        if (ex.getId() == null || ex.getId() == 0) {
            this.getHibernateTemplate().save(ex);
        } else {
            this.getHibernateTemplate().update(ex);
        }
        getHibernateTemplate().flush();
        return ex;
    }

    public List<Loan> getAllLoansForAccount(String accountId) {
        List<Long> userIds = Utils.getUserIdsForAccountId(getHibernateTemplate(), accountId);

        String[] paramNames = new String[]{"userIds"};
        Object[] values = new Object[]{userIds};
        List loans = getHibernateTemplate().findByNamedParam(
                "from Loan loan where loan.userId in (:userIds) order by loan.date", paramNames, values);
        return new ArrayList<Loan>(loans);
    }

    public List<Loan> getLoansForAccount(String accountId, Date fromDate, Date toDate) {
        List<Long> userIds = Utils.getUserIdsForAccountId(getHibernateTemplate(), accountId);

        String[] paramNames = new String[]{"userIds", "fromDate", "toDate"};
        Object[] values = new Object[]{userIds, fromDate, toDate};
        
        List loans = getHibernateTemplate().findByNamedParam(
                "from Loan loan where loan.userId in (:userIds) and loan.date >= :fromDate and loan.date < :toDate order by loan.date", paramNames, values);
        return new ArrayList<Loan>(loans);
    }

    public List<Loan> getLoansForExpenseGroup(String groupId) {
        List<Long> groupIds = new ArrayList<Long>();
        groupIds.add(Long.parseLong(groupId));
        String[] paramNames = new String[]{"groupIds"};
        Object[] values = new Object[]{groupIds};
        List loans = getHibernateTemplate().findByNamedParam(
                "from Loan loan where loan.groupId in (:groupIds) order by loan.date", paramNames, values);
        return new ArrayList<Loan>(loans);
    }
    
    public Loan findExpenseLoan(String expenseItemId) {
    	List<Long> transactionIds = new ArrayList<Long>();
        transactionIds.add(Long.parseLong(expenseItemId));
        
        List<TransactionType> acceptedTypes = new ArrayList<TransactionType>();
        acceptedTypes.add(TransactionType.LOAN);
        
        String[] paramNames = new String[]{"transactionIds", "transactionTypes"};
        Object[] values = new Object[]{transactionIds, acceptedTypes};
    	List loans = getHibernateTemplate().findByNamedParam(
                "from Loan loan where loan.transactionId in (:transactionIds) and loan.transactionType in (:transactionTypes)", paramNames, values);
    	if (loans != null && loans.size() > 0) {
    		return (Loan) loans.get(0);
    	}
        return null;
    }
    
    public void deleteLoan(Loan loan) {
    	getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
        getHibernateTemplate().delete(loan);
        getHibernateTemplate().flush();
    }
    
    @Override
	public Loan findLoanByCurrencyOnHandId(String id) {
		String hql = "from Loan d inner join d.loanPayments i where i.id = ?";
		List loans = getHibernateTemplate().find(hql, Long.valueOf(id));
		Object[] result = (Object[]) loans.get(0);
        Loan loan = (Loan) result[0];
        return loan;
	}
}