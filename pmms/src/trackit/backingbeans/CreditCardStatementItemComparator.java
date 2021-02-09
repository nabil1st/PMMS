/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.util.Comparator;
import trackit.businessobjects.CreditCardTransactionType;

/**
 *
 * @author ndaoud
 */
public class CreditCardStatementItemComparator implements Comparator<CreditCardStatementItem> {

    public int compare(CreditCardStatementItem item1, CreditCardStatementItem item2) {        
        // Opening balance transactions should be first in the list
        if (CreditCardTransactionType.OPENING_BALANCE.equals(item1.getTransactionType()) &&
            !CreditCardTransactionType.OPENING_BALANCE.equals(item2.getTransactionType())) {
            return -1;
        } else if (!CreditCardTransactionType.OPENING_BALANCE.equals(item1.getTransactionType()) &&
            CreditCardTransactionType.OPENING_BALANCE.equals(item2.getTransactionType())) {
            return 1;
        }

        return item1.getDate().compareTo(item2.getDate());
    }

}
