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
public class IncomeSource implements Serializable {
    private Long id;
    private String name;

    public IncomeSource() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
