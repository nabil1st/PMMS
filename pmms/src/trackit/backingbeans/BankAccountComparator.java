/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.util.Comparator;
import trackit.businessobjects.BankAccount;

/**
 *
 * @author ndaoud
 */
public class BankAccountComparator implements Comparator<BankAccount> {

    public int compare(BankAccount o1, BankAccount o2) {        
        return o1.getDescription().compareTo(o2.getDescription());
    }

}
