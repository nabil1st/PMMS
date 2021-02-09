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
public class DescriptionItemComparator implements Comparator<IDescriptionItem> {

    public int compare(IDescriptionItem item1, IDescriptionItem item2) {
        return item1.getDescription().compareTo(item2.getDescription());
    }

}
