/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.transaction.service;

import trackit.backingbeans.CreditCardBean;
import trackit.backingbeans.CreditCardPaymentBean;
import trackit.backingbeans.CreditCardTransactionBean;
import trackit.service.impl.DataInUseException;
import trackit.servicelocator.ServiceLocator;

/**
 *
 * @author ndaoud
 */
public interface CreditCardTransactionalService {
    public void saveCreditCard(CreditCardBean creditCardBean);

    public void saveCreditCardTransaction(CreditCardTransactionBean aThis);
    public void setServiceLocator(ServiceLocator locator);
    public void saveCreditCardPayment(CreditCardPaymentBean creditCardPayment);
    
    public void deleteCreditCard(final String id) throws DataInUseException;

}
