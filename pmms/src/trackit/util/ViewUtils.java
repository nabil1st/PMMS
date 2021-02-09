/*
 * JCatalog Project
 */
package trackit.util;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Utility class for the presentation tier.
 * 
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public class ViewUtils {
	/**
	 * Convert a list to a set.
	 * 
	 * @param orig the list to be converted
	 * @return the set converted from the list
	 */
	public static Set convertToSet(List orig) {
		Set result = new HashSet();
		
		if (orig != null) {
			Iterator ite = orig.iterator();
			
			while (ite.hasNext()) {
				result.add(ite.next());
			}
		}
		
		return result;
	}
	
	/**
	 * Convert a set to a list.
	 * @param orig the set to be converted
	 * @return the list converted from the set
	 */
	public static List convertToList(Set orig) {
		List result = new ArrayList();
		
		if (orig != null) {
			Iterator ite = orig.iterator();
			
			while (ite.hasNext()) {
				result.add(ite.next());
			}
		}
		
		return result;
	}
}
