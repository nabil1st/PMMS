package trackit.backingbeans;

import trackit.businessobjects.Entity;
import trackit.util.FacesUtils;
import trackit.util.ListUtils;

/**
 *
 * @author Nabil Daoud.
 */
public class CreditCardPaymentBean extends GenericPaymentEnabledBean {
    
    private Long creditCardToPayId;    
    private Long userId;
    
    public CreditCardPaymentBean(){}

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String createIt() throws Exception {
        this.serviceLocator.getCreditCardTransactionalService().setServiceLocator(serviceLocator);

        try {
            this.serviceLocator.getCreditCardTransactionalService().saveCreditCardPayment(this);
        } catch (Exception ex) {
            throw new Exception("Credit Card was not created");            
        }

        clearAllFields();

        return NavigationResults.CREDIT_CARD_PAYMENT_CREATED;
    }
    
    @Override 
    public String getListBeanName() {
    	return null;
    }

    @Override
    public void clearAllFields() {
        super.clearAllFields();
        this.creditCardToPayId = null;
    }

    public String doCancel() {
        clearAllFields();
        return NavigationResults.CREATE_CREDIT_CARD_PAYMENT_CANCELLED;
    }
    
    public Long getCreditCardToPayId() {
    	if (creditCardToPayId == null) {
    		String currentCreditCardId = FacesUtils.getSessionBean().getCurrentCreditCardId();
    		if (currentCreditCardId != null) {
    			creditCardToPayId = Long.parseLong(currentCreditCardId);
    		}
    	}
        return creditCardToPayId;
    }

    public void setCreditCardToPayId(Long creditCardToPayId) {
        this.creditCardToPayId = creditCardToPayId;
    }

	@Override
	public String getFollowOnAction() {
		return NavigationResults.CREATE_CREDIT_CARD_PAYMENT_REQUESTED;
	}
	
	@Override
	public void validate() throws Exception {
		super.validate();
		if (ListUtils.isNullSelection(creditCardToPayId)) {
			throw new Exception("Credit Card To Pay is a required field");
		}
	}

	@Override
	public void editIt(Entity entity) {
		// TODO Auto-generated method stub
		
	}

    
}
