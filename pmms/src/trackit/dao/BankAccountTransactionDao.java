/*
 * JCatalog Project
 */
package trackit.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import trackit.businessobjects.BankAccountTransaction;


/**
 * The user DAO interface.
 * <p>
 * This class contains user management related data access logic.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface BankAccountTransactionDao {

    public List<BankAccountTransaction> getAllBankAccountTransactions(String accountId, Date fromDate, Date toDate);


	/**
	 * Get user by username.
	 *
	 * @param username the username
	 * @return the user associated with the username
	 */
    public BankAccountTransaction getBankAccountTransaction(String id);

    public BankAccountTransaction saveBankAccountTransaction(BankAccountTransaction account);


	public void deleteTransaction(String id);
	
	 public void deleteAllOpeningBalanceTransactions(String bankAccountId);


	public BigDecimal getOpeningBalance(String bankAccountId);


	public BankAccountTransaction findBankAccountOpeningBalanceTransaction(String bankAccountId);


}
