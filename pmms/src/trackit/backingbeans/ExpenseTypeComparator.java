/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.util.Comparator;
import trackit.businessobjects.ExpenseType;

/**
 *
 * @author ndaoud
 */
public class ExpenseTypeComparator implements Comparator<ExpenseType> {

    public int compare(ExpenseType t1, ExpenseType t2) {
        return t1.getDescription().compareTo(t2.getDescription());
    }

}
