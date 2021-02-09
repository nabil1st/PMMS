/*
 * Created on Dec 30, 2009
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.me.expensetrackerme.xml;

import junit.framework.TestCase;

/**
 * @author C809532
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class IdUtilsTest extends TestCase {

	public void testGetFirstAvailableId() {
		long[] ids = {1,2,3,4,5,6};
		long id = IdUtils.getFirstAvailableId(ids);
		assertEquals(7, id);
		
		long[] ids1 = {1,2,4,5,6};
		id = IdUtils.getFirstAvailableId(ids1);
		assertEquals(3, id);
		
		long[] ids2 = {2,1,6,5,7};
		id = IdUtils.getFirstAvailableId(ids2);
		assertEquals(3, id);
		
		long[] ids3 = {2,3,6,5,7};
		id = IdUtils.getFirstAvailableId(ids3);
		assertEquals(1, id);
		
		long[] ids4 = {};
		id = IdUtils.getFirstAvailableId(ids4);
		assertEquals(1, id);
	}

}
