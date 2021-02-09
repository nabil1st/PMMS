/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.businessobjects;

/**
 *
 * @author Owner
 */
public interface IUser {
    
    public String getEmail();

    public void setEmail(String email);

    public String getFirstName();

    public void setFirstName(String firstName);

    public Long getId();

    public void setId(Long id);

    public String getLastName();

    public void setLastName(String lastName);

    public String getUserName();

    public void setUserName(String userName);

    public String getPassword();

    public void setPassword(String password);

    public Long getAccountId();

    public void setAccountId(Long accountId);

}
