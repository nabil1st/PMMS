/*
 * JCatalog Project
 */
package trackit.service;

import java.util.Date;
import java.util.List;
import trackit.businessobjects.Loan;


/**
 * The user business service interface.
 * <p>
 * It contains all user management related business logic.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface LoanService {

    public Loan saveLoan(Loan ex);
    public Loan getLoan(String id);
    public List<Loan> getAllLoansForAccount(String accountId);
    public List<Loan> getLoansForAccount(String accountId, Date fromDate, Date toDate);
    public List<Loan> getLoansForExpenseGroup(String groupId);
    public Loan findExpenseLoan(String expenseItemId);
    public void deleteLoan(Loan loan);
    public Loan findLoanByCurrencyOnHandId(String id);

}
