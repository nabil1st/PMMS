/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.util.Comparator;

/**
 *
 * @author ndaoud
 */
public class DatedItemComparator implements Comparator<IDatedItem> {

    public int compare(IDatedItem o1, IDatedItem o2) {        
        return o1.getDate().compareTo(o2.getDate());
    }

}
