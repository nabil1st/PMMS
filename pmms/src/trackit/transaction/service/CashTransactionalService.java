/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.transaction.service;

import trackit.backingbeans.CashTransferBean;
import trackit.servicelocator.ServiceLocator;

/**
 *
 * @author ndaoud
 */
public interface CashTransactionalService {
    public void saveCashTransfer(CashTransferBean bean);
    public void setServiceLocator(ServiceLocator locator);
    
}
