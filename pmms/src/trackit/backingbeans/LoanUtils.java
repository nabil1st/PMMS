/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import trackit.businessobjects.CurrencyOnHand;
import trackit.businessobjects.Loan;
import trackit.servicelocator.ServiceLocator;

/**
 *
 * @author ndaoud
 */
public class LoanUtils {

    public static BigDecimal getLoanBalance(Loan loan, ServiceLocator serviceLocator) {
        BigDecimal totalAmount = loan.getAmount();
//        List<CurrencyOnHand> payments = serviceLocator.getCurrencyOnHandService().
//                getAllLoanPaymentsForLoan(loan.getId().toString());
        if (loan.getLoanPayments() != null) {
        	List<CurrencyOnHand> payments = new ArrayList<CurrencyOnHand>(loan.getLoanPayments());
        	for (CurrencyOnHand payment : payments) {
        		totalAmount = totalAmount.subtract(payment.getAmount());
        	}
        }

        return totalAmount;
    }

}
