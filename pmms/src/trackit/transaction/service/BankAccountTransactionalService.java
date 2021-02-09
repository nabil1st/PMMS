/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.transaction.service;

import trackit.backingbeans.BankAccountBean;
import trackit.backingbeans.BankAccountTransactionBean;
import trackit.backingbeans.BankAccountWithdrawlBean;
import trackit.service.impl.DataInUseException;
import trackit.servicelocator.ServiceLocator;

/**
 *
 * @author ndaoud
 */
public interface BankAccountTransactionalService {
    public void saveBankAccount(BankAccountBean bankAccountBean);

    public void saveBankTransaction(BankAccountTransactionBean aThis);

    public void saveBankWithdrawl(BankAccountWithdrawlBean aThis);
    public void setServiceLocator(ServiceLocator locator);
    
    public void deleteBankAccount(String id) throws DataInUseException;

}
