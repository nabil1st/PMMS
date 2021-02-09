/*
 * Created on Dec 30, 2009
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.me.expensetrackerme.xml;

/**
 * @author C809532
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class IncomeSource extends BasicType {

	public IncomeSource() {
		this.tagName = INCOME_SOURCE_TAG;
	}

	public IncomeSource(long id, String name) {
		super(id, name, INCOME_SOURCE_TAG); 
	}

}
