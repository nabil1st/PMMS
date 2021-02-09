/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trackit.backingbeans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import trackit.businessobjects.BankAccount;
import trackit.businessobjects.Entity;
import trackit.util.FacesUtils;

/**
 *
 * @author Owner
 */

public class BankAccountBean extends GenericEntityBean implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String description;
    private Long accountTypeId;
    private Long currentCheckNumber;
    private BigDecimal currentBalance = new BigDecimal(0);

    public BankAccountBean() {
        System.out.println("Constructor");
    }

    public Long getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(Long accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public Long getCurrentCheckNumber() {
        return currentCheckNumber;
    }

    public void setCurrentCheckNumber(Long currentCheckNumber) {
        this.currentCheckNumber = currentCheckNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    
    public String createAction(ActionEvent event) {
        return createAction();
    }
    
    @Override
    public String doCreateIt() throws Exception {        

        this.serviceLocator.getBankAccountTransactionalService().setServiceLocator(serviceLocator);

        try {
            this.serviceLocator.getBankAccountTransactionalService().saveBankAccount(this);
        } catch (Exception ex) {
            throw new Exception("Bank account was not created");            
        }
		
        // In case the bank account was created from a page other than
        // the bank accounts page, we need to return to that page.
        if (FacesUtils.getSessionBean().getFollowOnAction() != null) {
            String followOnAction = FacesUtils.getSessionBean().getFollowOnAction();
            FacesUtils.getSessionBean().setFollowOnAction(null);
            return followOnAction;
        }
        
        return NavigationResults.BANK_ACCOUNT_CREATED;
    }
    
    @Override
    public void validate() throws Exception {
    	if (this.serviceLocator == null) {
            this.serviceLocator = FacesUtils.getSessionBean().getServiceLocator();
        }

        List<BankAccount> availableAccounts =
                this.serviceLocator.getBankAccountService().getAllBankAccountsForAccount(
                    FacesUtils.getSessionBean().getCurrentAccountId());

        for (BankAccount ba : availableAccounts) {
            if ((!editMode && ba.getDescription().equals(description)) ||
            	(editMode && ba.getDescription().equals(description) && ba.getId().longValue() != getId().longValue())) {
                throw new Exception("Bank account with same description already exists");                
            }
        }
    }
    
    @Override
    public String getListBeanName() {
    	return "bankAccountListBean";
    }

    public List<SelectItem> getAccountTypeList() {
        List<SelectItem> dropList = new ArrayList<SelectItem>();
        dropList.add(new SelectItem(Long.valueOf("1"), "Checking"));
        dropList.add(new SelectItem(Long.valueOf("2"), "Saving"));
        return dropList;
    }

    @Override
    public String doCancel() {

        if (FacesUtils.getSessionBean().getFollowOnAction() != null) {
            String followOnAction = FacesUtils.getSessionBean().getFollowOnAction();
            FacesUtils.getSessionBean().setFollowOnAction(null);
            return followOnAction;
        }

        return NavigationResults.CREATE_BANK_ACCOUNT_CANCELLED;
    }

    @Override
	public void editIt(Entity entity) {
    	BankAccount p = (BankAccount) entity;
		this.description = p.getDescription();
		this.currentBalance = getOpeningBalance(p);
		this.accountTypeId = p.getAccountTypeId();		
	}
	
	private BigDecimal getOpeningBalance(BankAccount p) {
		return serviceLocator.getBankAccountService().getOpeningBalance(String.valueOf(p.getId()));
	}
		
	public String getTitle() {
		if (editMode) {
			return "Edit Bank Account";
		} else {
			return "New Bank Account";
		}
	}
	
	public String getOpeningBalanceTitle() {
		if (editMode) {
			return "Opening Account Balance";
		} else {
			return "Current Account Balance";
		}
	}
	
	
}
