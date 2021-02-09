/*
 * JCatalog Project
 */
package trackit.dao;

import java.util.List;
import trackit.businessobjects.IncomeSource;


/**
 * The user DAO interface.
 * <p>
 * This class contains user management related data access logic.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface IncomeSourceDao {

    public List<IncomeSource> getAllIncomeSourcesForAccount(String accountId);


	/**
	 * Get user by username.
	 *
	 * @param username the username
	 * @return the user associated with the username
	 */
    public IncomeSource getIncomeSource(String id);

    public IncomeSource saveIncomeSource(IncomeSource incomeSource);

    public List findIncomeSourceByName(String name);

}
