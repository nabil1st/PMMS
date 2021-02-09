/*
 * JCatalog Project
 */
package trackit.service;

import trackit.businessobjects.Account;

/**
 * The user business service interface.
 * <p>
 * It contains all user management related business logic.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface AccountService {

    public Account saveAccount(Account account);

    public Account findAccount(String accountId);
}
