/*
 * JCatalog Project
 */
package trackit.dao;

import java.util.Date;
import java.util.List;
import trackit.businessobjects.Deposit;


/**
 * The user DAO interface.
 * <p>
 * This class contains user management related data access logic.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface DepositDao {

    public List<Deposit> getAllDepositsForBankAccount(String bankAccountId, Date fromDate, Date toDate);
	/**
	 * Get user by username.
	 *
	 * @param username the username
	 * @return the user associated with the username
	 */
    public Deposit getDeposit(String depositId);

    public Deposit saveDeposit(Deposit deposit);

    public List<Deposit> getAllDepositsForAccount(String accountId);
	public Deposit findDepositByCurrencyOnHandId(String id);
	public void deleteDeposit(String id);

}
