/*
 * Created on Dec 30, 2009
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.me.expensetrackerme.xml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author C809532
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class IdUtils {
	public static long getFirstAvailableId(long[] ids) {
		if (ids.length == 0) {
			return 1;
		}
		
		Arrays.sort(ids);
		if(ids[0] > 1) {
			return 1;
		}
		
		for (int i=1; i<ids.length; i++) {
			if (ids[i] - ids[i-1] > 1) {
				return ids[i-1] + 1;
			}
		}
		
		return ids[ids.length - 1] + 1;		
	}

        public static long getNextId(List basicTypes) {
		long[] ids = new long[basicTypes.size()];
		for (int i=0; i<basicTypes.size(); i++) {
			BasicType c = (BasicType) basicTypes.get(i);
			ids[i] = c.getId();
		}

		return IdUtils.getFirstAvailableId(ids);
	}

}
