/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.businessobjects;

import java.math.BigDecimal;

/**
 *
 * @author Nabil
 */
public class User {
    
    private Long id;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private Long accountId;
    private String permissions;
    private BigDecimal cashBalance;
    private Boolean mainUser = false;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

	public BigDecimal getCashBalance() {
		return cashBalance;
	}

	public void setCashBalance(BigDecimal cashBalance) {
		this.cashBalance = cashBalance;
	}
    
	public void addToBalance(BigDecimal amount) {
        this.cashBalance = getCurrentBalance().add(amount);
    }

    public void subtractFromBalance(BigDecimal amount) {
        this.cashBalance = getCurrentBalance().subtract(amount);
    }
    
    private BigDecimal getCurrentBalance() {
        if (this.cashBalance == null) {
            return new BigDecimal(0);
        }

        return this.cashBalance;
    }
    
    public String getFirstLastName() {
    	return firstName + " " + lastName;
    }

	public Boolean getMainUser() {
		return mainUser;
	}

	public void setMainUser(Boolean mainUser) {
		this.mainUser = mainUser;
	}
    
    

}
