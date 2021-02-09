/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.transaction.service;

import trackit.backingbeans.MoneyOrderBean;
import trackit.service.impl.DataInUseException;
import trackit.servicelocator.ServiceLocator;

/**
 *
 * @author ndaoud
 */
public interface MoneyOrderTransactionalService {
    public void saveMoneyOrder(MoneyOrderBean moneyOrder);
    public void setServiceLocator(ServiceLocator locator);
    public void deleteMoneyOrder(final Long moneyOrderId) throws DataInUseException;
}
