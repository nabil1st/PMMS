/*
 * JCatalog Project
 */
package trackit.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import trackit.businessobjects.BankAccount;
import trackit.businessobjects.BankAccountTransaction;

/**
 * The user business service interface.
 * <p>
 * It contains all user management related business logic.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface BankAccountService {
    public BankAccount saveBankAccount(BankAccount accnt);
    public BankAccount findBankAccount(String id);
    public Object findBankAccountByDescription(String description);

    public BankAccountTransaction saveBankAccountTransaction(BankAccountTransaction accnt);
    public BankAccountTransaction findBankAccountTransaction(String id);
    public BankAccountTransaction findBankAccountOpeningBalanceTransaction(String id);

    public List<BankAccount> getAllBankAccountsForAccount(String accountId);

    public List<BankAccountTransaction> getBankAccountTransactionsForAccount(
            String accountId, Date fromDate, Date toDate);
	public void deleteBankAccount(String valueOf);
	
	public void deleteBankAccountTransaction(String id);
	
	
	public int getNumberOfExpensesWithBankAccount(String id);	
	public int getNumberOfLoansWithBankAccount(String id);	
	public int getNumberOfDepositsWithBankAccount(String id);	
	public int getNumberOfTransactionsWithBankAccount(String id);
	
	public void deleteAllOpeningBalanceTransactions(String bankAccountId);
	public BigDecimal getOpeningBalance(String bankAccountId);
		
}
