/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.transaction.service;

import trackit.backingbeans.IncomeBean;
import trackit.backingbeans.IncomeSourceBean;
import trackit.service.impl.DataInUseException;
import trackit.servicelocator.ServiceLocator;

/**
 *
 * @author ndaoud
 */
public interface IncomeTransactionalService {
    public void saveIncome(IncomeBean income);
    public void setServiceLocator(ServiceLocator locator);
    public void saveIncomeSource(IncomeSourceBean incomeSource);
	public void deleteIncome(Long id) throws DataInUseException;
}
