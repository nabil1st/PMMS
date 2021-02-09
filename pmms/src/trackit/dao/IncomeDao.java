/*
 * JCatalog Project
 */
package trackit.dao;

import java.util.List;
import trackit.businessobjects.Income;


/**
 * The user DAO interface.
 * <p>
 * This class contains user management related data access logic.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface IncomeDao {
	/**
	 * Get user by username.
	 *
	 * @param username the username
	 * @return the user associated with the username
	 */
    public Income getIncome(String incomeId);

    public Income saveIncome(Income income);

    public List<Income> getAllIncomesForAccount(String accountId);

    public List<Income> getAllUndepositedIncomesForAccount(String accountId);

}
