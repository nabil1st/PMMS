/*
 * JCatalog Project
 */
package trackit.dao;

import java.util.Date;
import java.util.List;

import trackit.businessobjects.CashTransaction;
import trackit.businessobjects.CashTransfer;


/**
 * The user DAO interface.
 * <p>
 * This class contains user management related data access logic.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface CashTransactionDao {


	/**
	 * Get user by username.
	 *
	 * @param username the username
	 * @return the user associated with the username
	 */
    public CashTransaction getCashTransaction(String id);

    public CashTransaction saveCashTransaction(CashTransaction account);
    
    public CashTransfer getCashTransfer(String id);
    
    public CashTransfer saveCashTransfer(CashTransfer transfer);
    
    public List<CashTransfer> getCashTransfers(String accountId, Date start, Date end);

	public void deleteCashTransaction(String id);

}
