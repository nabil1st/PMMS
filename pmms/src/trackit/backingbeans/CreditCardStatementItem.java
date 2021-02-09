/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import trackit.businessobjects.CreditCardTransactionType;

/**
 *
 * @author ndaoud
 */
public class CreditCardStatementItem extends StatementItem {
    protected CreditCardTransactionType creditCardTransactionType;

    public CreditCardTransactionType getCreditCardTransactionType() {
        return creditCardTransactionType;
    }

    public void setCreditCardTransactionType(CreditCardTransactionType creditCardTransactionType) {
        this.creditCardTransactionType = creditCardTransactionType;
    }

}
