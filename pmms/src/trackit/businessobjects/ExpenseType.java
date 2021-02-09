/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.businessobjects;

import java.io.Serializable;


/**
 *
 * @author Owner
 */
public class ExpenseType implements Serializable {
    private Long id;
    private String description;

    public ExpenseType() {}

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

}
