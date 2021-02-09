/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.businessobjects;

import javax.xml.bind.annotation.XmlRootElement;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


/**
 *
 * @author Nabil
 */
//@XmlRootElement(name="payee")
@Root
public class Payee extends Entity {
	
	@Element
    private String description;
	
	private boolean inSync;

    public Payee() {}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    public boolean isInSync() {
		return inSync;
	}

	public void setInSync(boolean inSync) {
		this.inSync = inSync;
	}

	@Override 
    public String toString() {
    	return this.description;
    }
}
