/*
 * JCatalog Project
 */
package trackit.dao;

import java.util.List;
import trackit.businessobjects.Income;
import trackit.businessobjects.MoneyOrderFee;


/**
 * The user DAO interface.
 * <p>
 * This class contains user management related data access logic.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface MoneyOrderDao {
	/**
	 * Get user by username.
	 *
	 * @param username the username
	 * @return the user associated with the username
	 */
    public MoneyOrderFee getMoneyOrderFeeByMoneyOrderId(String moneyOrderId);

    public MoneyOrderFee saveMoneyOrderFee(MoneyOrderFee fee);

	public void deleteMoneyOrderFee(String id);


}
