/*
 * JCatalog Project
 */
package trackit.dao;

import java.util.Date;
import java.util.List;
import trackit.businessobjects.Loan;


/**
 * The user DAO interface.
 * <p>
 * This class contains user management related data access logic.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface LoanDao {

    public Loan getLoan(String id);

    public Loan saveLoan(Loan Loan);

    public List<Loan> getAllLoansForAccount(String accountId);

    public List<Loan> getLoansForAccount(String accountId, Date fromDate, Date toDate);

    public List<Loan> getLoansForExpenseGroup(String groupId);
    
    public Loan findExpenseLoan(String expenseItemId);
    
    public void deleteLoan(Loan loan);
    
    public Loan findLoanByCurrencyOnHandId(String id);

}
