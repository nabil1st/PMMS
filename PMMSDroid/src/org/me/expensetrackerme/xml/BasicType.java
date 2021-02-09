/*
 * Created on Dec 30, 2009
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.me.expensetrackerme.xml;

import org.simpleframework.xml.Element;

/**
 * @author C809532
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class BasicType implements IMetadataConstants {
	
	@Element
	protected long id;
	
	@Element
	protected String description = "";
	
	protected String tagName;
	
	public BasicType(){}
	
	public BasicType(long id, String name, String tagName) {
		this.id = id;
		this.description = name;
		this.tagName = tagName;
	}
	

	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return description;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.description = name;
	}	
	
	/**
	 * @return Returns the tagName.
	 */
	public String getTagName() {
		return tagName;
	}
	/**
	 * @param tagName The tagName to set.
	 */
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	public void renderWholeTag(StringBuffer sb) {
		sb.append("<").append(tagName).append(" ").append(
				XMLUtils.getXMLForIdNameAttributes(id, description)).append("/>");
	}
	
	public void renderBeginTag(StringBuffer sb) {
		sb.append("<").append(tagName).append(" ").append(
				XMLUtils.getXMLForIdNameAttributes(id, description)).append(">");
	}
	
	public void renderEndTag(StringBuffer sb) {
		sb.append("</").append(tagName).append(">");
	}
	
	public String toXML() {
		StringBuffer sb = new StringBuffer();
		renderWholeTag(sb);
		return sb.toString();
	}

        public String toString() {
            return description;
        }
}
