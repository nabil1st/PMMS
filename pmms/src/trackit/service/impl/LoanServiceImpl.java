/*
 * JCatalog Project
 */
package trackit.service.impl;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import trackit.businessobjects.Loan;
import trackit.dao.LoanDao;
import trackit.service.LoanService;
import trackit.service.UserService;


/**
 * The implementation of the <code>UserService</code>.
 * </p>
 * Spring Framework is used to manage this service bean.
 * Since this class is not dependend on Spring API, it can be used outside the Spring IOC container.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 * @see UserService
 */
public class LoanServiceImpl implements LoanService {
	//the logger for this class
	private Log logger = LogFactory.getLog(this.getClass());

	private LoanDao loanDao;


	public void setLoanDao(LoanDao newExpenseDao) {
		this.loanDao = newExpenseDao;
	}

    public Loan saveLoan(Loan loan) {
        Loan savedLoan = loanDao.saveLoan(loan);
        return savedLoan;
    }

    public Loan getLoan(String id) {
        return this.loanDao.getLoan(id);
    }

    public List<Loan> getAllLoansForAccount(String accountId) {
        return loanDao.getAllLoansForAccount(accountId);
    }

    public List<Loan> getLoansForAccount(String accountId, Date fromDate, Date toDate) {
        return loanDao.getLoansForAccount(accountId, fromDate, toDate);
    }

    public List<Loan> getLoansForExpenseGroup(String groupId) {
        return loanDao.getLoansForExpenseGroup(groupId);
    }
    
    public Loan findExpenseLoan(String expenseItemId) {
    	return loanDao.findExpenseLoan(expenseItemId);
    }
    
    public void deleteLoan(Loan loan) {
    	loanDao.deleteLoan(loan);
    }

	@Override
	public Loan findLoanByCurrencyOnHandId(String id) {
		return loanDao.findLoanByCurrencyOnHandId(id);
	}

}
