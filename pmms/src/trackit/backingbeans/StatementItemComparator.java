/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.util.Comparator;
import trackit.businessobjects.BankTransactionType;

/**
 *
 * @author ndaoud
 */
public class StatementItemComparator implements Comparator<StatementItem> {

    public int compare(StatementItem item1, StatementItem item2) {        
        // Opening balance transactions should be first in the list
        if (item1.getTransactionType().equals(BankTransactionType.OPENING_BALANCE) &&
            !item2.getTransactionType().equals(BankTransactionType.OPENING_BALANCE)) {
            return -1;
        } else if (!item1.getTransactionType().equals(BankTransactionType.OPENING_BALANCE) &&
            item2.getTransactionType().equals(BankTransactionType.OPENING_BALANCE)) {
            return 1;
        }

        return item1.getDate().compareTo(item2.getDate());
    }

}
