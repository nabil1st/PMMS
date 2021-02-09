/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.transaction.service;

import trackit.backingbeans.BorrowerBean;
import trackit.backingbeans.LoanBean;
import trackit.backingbeans.LoanPaymentBean;
import trackit.businessobjects.Loan;
import trackit.service.impl.DataInUseException;
import trackit.servicelocator.ServiceLocator;

/**
 *
 * @author ndaoud
 */
public interface LoanTransactionalService {
    public void saveLoan(LoanBean loan);
    public void saveLoan(Loan loan);
    public void saveLoanPayment(LoanPaymentBean loanPayment);
    public void saveBorrower(BorrowerBean borrower);
    public void setServiceLocator(ServiceLocator locator);
    public void deleteLoanPayment(Long id) throws DataInUseException;
}
