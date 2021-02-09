/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.businessobjects;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


/**
 *
 * @author Owner
 */
@Root
public class ExpenseGroup {
	@Element
    private Long id;
	
	@Element
    private String description;
	
	private boolean inSync;
	
	private Long accountId;

    public ExpenseGroup() {}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }    
    
    public boolean isInSync() {
		return inSync;
	}

	public void setInSync(boolean inSync) {
		this.inSync = inSync;
	}

	
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	@Override
    public String toString() {
    	return this.description;
    }

}
