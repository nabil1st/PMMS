/*
 * JCatalog Project
 */
package trackit.dao;

import java.util.List;
import trackit.businessobjects.Payee;


/**
 * The user DAO interface.
 * <p>
 * This class contains user management related data access logic.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface PayeeDao {

    public List<Payee> getAllPayeesForAccount(String accountId);


	/**
	 * Get user by username.
	 *
	 * @param username the username
	 * @return the user associated with the username
	 */
    public Payee getPayee(String id);

    public Payee savePayee(Payee account);

    public List findPayeeByDescription(String description);

    public void deletePayee(String id);
	public int getNumberOfExpensesWithPayee(String id);
	public int getNumberOfLoansWithPayee(String id);

}
