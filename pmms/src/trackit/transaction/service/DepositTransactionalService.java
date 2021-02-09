/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.transaction.service;

import trackit.backingbeans.DepositBean;
import trackit.servicelocator.ServiceLocator;

/**
 *
 * @author ndaoud
 */
public interface DepositTransactionalService {
    public void saveDeposit(DepositBean depositBean);
    public void setServiceLocator(ServiceLocator locator);
}
