/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.transaction.service;

import trackit.backingbeans.AccountBean;
import trackit.backingbeans.AccountUserBean;
import trackit.backingbeans.UserBean;
import trackit.servicelocator.ServiceLocator;

/**
 *
 * @author ndaoud
 */
public interface AccountTransactionalService {
    public void saveAccount(UserBean user, AccountBean account);
    public void setServiceLocator(ServiceLocator locator);
    public void saveAccountUser(AccountUserBean accountUser);
}
