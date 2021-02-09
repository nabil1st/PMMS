/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.businessobjects;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


/**
 *
 * @author Owner
 */
@Root
public class ExpenseSubType implements Serializable {
	@Element
    private Long id;
	
	@Element
    private String description;
	
    private Long categoryId;
    private Long accountId;

    public ExpenseSubType() {}

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

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    
    @Override
    public String toString() {
    	return this.description;
    }


}
