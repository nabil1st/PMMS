package trackit.backingbeans;

import java.math.BigDecimal;
import java.util.List;

import trackit.businessobjects.CreditCard;
import trackit.businessobjects.Entity;
import trackit.util.FacesUtils;

/**
 *
 * @author Owner
 */
public class CreditCardBean extends GenericEntityBean {
    
    private String description;
    private BigDecimal currentBalance = new BigDecimal(0);

    public CreditCardBean() {
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
   
    @Override
	public String doCreateIt() throws Exception {
        this.serviceLocator.getCreditCardTransactionalService().setServiceLocator(this.serviceLocator);

        try {
            this.serviceLocator.getCreditCardTransactionalService().saveCreditCard(this);
        } catch (Exception ex) {
            throw new Exception("Credit Card was not created");
        }

        if (FacesUtils.getSessionBean().getFollowOnAction() != null) {
            String followOnAction = FacesUtils.getSessionBean().getFollowOnAction();
            FacesUtils.getSessionBean().setFollowOnAction(null);
            return followOnAction;
        }

        return NavigationResults.CREDIT_CARD_CREATED;

    }
    
    @Override 
    public String getListBeanName() {
    	return "creditCardListBean";
    }
    
    @Override
    public void validate() throws Exception {
    	List<CreditCard> availableAccounts =
            this.serviceLocator.getCreditCardService().getAllCreditCardsForAccount(
                FacesUtils.getSessionBean().getCurrentAccountId());

	    for (CreditCard ba : availableAccounts) {
	        if ((!editMode && ba.getDescription().equals(description)) ||
	            	(editMode && ba.getDescription().equals(description) && ba.getId().longValue() != getId().longValue())) {
	            throw new Exception("Credit card with same description already exists");	            
	        }
	    }
    }

    @Override
    public String doCancel() {
        if (FacesUtils.getSessionBean().getFollowOnAction() != null) {
            String followOnAction = FacesUtils.getSessionBean().getFollowOnAction();
            FacesUtils.getSessionBean().setFollowOnAction(null);
            return followOnAction;
        }

        return NavigationResults.CREATE_CREDIT_CARD_CANCELLED;
    }
    
    @Override
    public void editIt(Entity entity) {
    	CreditCard cc = (CreditCard) entity;    	
		this.description = cc.getDescription();
		this.currentBalance = getOpeningBalance(cc);		
    }
    
    private BigDecimal getOpeningBalance(CreditCard cc) {
		return serviceLocator.getCreditCardService().getOpeningBalance(String.valueOf(cc.getId()));
	}
    
    public String getTitle() {
		if (editMode) {
			return "Edit Credit Card";
		} else {
			return "New Credit Card";
		}
	}
	
	public String getOpeningBalanceTitle() {
		if (editMode) {
			return "Opening Card Balance";
		} else {
			return "Current Card Balance";
		}
	}
}
