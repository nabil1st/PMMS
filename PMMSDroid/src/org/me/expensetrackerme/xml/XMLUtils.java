/*
 * Created on Dec 29, 2009
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
public class XMLUtils {
	public static String getXMLForIdNameAttributes(long id, String name) {
		StringBuffer sb = new StringBuffer();
		sb.append("id=\"").append(id).append("\" name=\"").append(name).append("\"");
		return sb.toString();
	}
	
	public static String getXMLForAttribute(String name, String value) {
		StringBuffer sb = new StringBuffer();
		sb.append(name).append("=\"").append(value).append("\"");
		return sb.toString();
	}
	
	public static String makeBeginTag(String name) {
		return "<" + name + ">";
	}

        public static String makeBeginOpenTag(String name) {
		return "<" + name + " ";
	}
	
	public static String makeEndTag(String name) {
		return "</" + name + ">";
	}

}
