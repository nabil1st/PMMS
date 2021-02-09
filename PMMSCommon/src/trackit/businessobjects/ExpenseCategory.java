/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.businessobjects;

import java.io.Serializable;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


/**
 *
 * @author Owner
 */
@Root
public class ExpenseCategory implements Serializable {
	@Element
    private Long id;
	
	@Element
    private String description;
    private Long accountId;

    private List<ExpenseSubType> subTypes;

    public ExpenseCategory() {}

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

    public List<ExpenseSubType> getSubTypes() {
        return subTypes;
    }

    public void setSubTypes(List<ExpenseSubType> subTypes) {
        this.subTypes = subTypes;
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
