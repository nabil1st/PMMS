/*
 * JCatalog Project
 */
package trackit.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
//
import trackit.businessobjects.Account;
import trackit.businessobjects.Deposit;
import trackit.businessobjects.User;
import trackit.dao.DepositDao;
import trackit.dao.UserDao;

/**
 * The Hibernate implementation of the <code>UserDao</code>.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 * @see UserDao
 */
public class DepositDaoHibernateImpl extends HibernateDaoSupport
        implements DepositDao {
    /**
     * Default constructor.
     */
    public DepositDaoHibernateImpl() {
            super();
    }

    /**
     * @see UserDao#getUser(String)
     */
    public Deposit getDeposit(String depositId) {
        Deposit deposit = (Deposit)getHibernateTemplate().load(Deposit.class, Long.valueOf(depositId));
        return deposit;
    }

    public Deposit saveDeposit(Deposit deposit) {
        getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
        if (deposit.getId() == null || deposit.getId() == 0) {
            this.getHibernateTemplate().save(deposit);
        } else {
            this.getHibernateTemplate().update(deposit);
        }
        getHibernateTemplate().flush();
        return deposit;
    }

    public List<Deposit> getAllDepositsForAccount(String accountId) {
        List accounts = getHibernateTemplate().find("from Account account where account.id=?", Long.valueOf(accountId));
        Account account = (Account) accounts.get(0);

        //getHibernateTemplate().getSessionFactory().openSession();
        Set<User> users = account.getUsers();
        List<Long> userIds = new ArrayList<Long>();

        for (User user : users) {
            userIds.add(user.getId());
            System.out.println(user.getId().toString());
        }

        String[] paramNames = new String[]{"userIds"};
        Object[] values = new Object[]{userIds};
        List deposits = getHibernateTemplate().findByNamedParam(
                "from Deposit deposit where deposit.userId in (:userIds) order by deposit.date", paramNames, values);
        return new ArrayList<Deposit>(deposits);
    }

    public List<Deposit> getAllDepositsForBankAccount(String bankAccountId, Date fromDate, Date toDate) {
        String[] paramNames = new String[]{"bankAccountId", "fromDate", "toDate"};
        Object[] values = new Object[]{Long.parseLong(bankAccountId), fromDate, toDate};
        List deposits = getHibernateTemplate().findByNamedParam(
                "from Deposit xaction where xaction.bankAccountId = :bankAccountId and xaction.date >= :fromDate and xaction.date < :toDate order by xaction.date", paramNames, values);
        return new ArrayList<Deposit>(deposits);
    }

	@Override
	public Deposit findDepositByCurrencyOnHandId(String id) {
		String hql = "from Deposit d inner join d.depositItems i where i.id = ?";
		List deposits = getHibernateTemplate().find(hql, Long.valueOf(id));
		Object[] result = (Object[]) deposits.get(0);
        Deposit deposit = (Deposit) result[0];
        return deposit;
	}

	@Override
	public void deleteDeposit(String id) {
		Deposit deposit = getDeposit(id);
		getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
		getHibernateTemplate().delete(deposit);
		getHibernateTemplate().flush();
		
	}
}