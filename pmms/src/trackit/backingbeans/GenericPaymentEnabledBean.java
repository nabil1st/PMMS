package trackit.backingbeans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import trackit.businessobjects.CurrencyOnHand;
import trackit.businessobjects.PaymentMethod;
import trackit.exception.EntityException;
import trackit.util.FacesUtils;
import trackit.util.ListUtils;
import trackit.util.StringUtils;

public abstract class GenericPaymentEnabledBean extends GenericEntityBean {
	
	protected Date date = new Date();
    
    protected BigDecimal amount = new BigDecimal(0);
    protected PaymentMethod paymentMethod;
    protected Long bankAccountId;
    protected String checkNumber;
    protected Long creditCardId;
    protected Long moneyOrderId;
    protected Long oldMoneyOrderId;
    
    private boolean renderBankAccountPanel;
    private boolean renderCheckNumberPanel;
    private boolean renderCreditCardPanel;
    private boolean renderMoneyOrderPanel;
	
	public String doCreateIt() throws Exception {
		String result = createIt();
		
//		if (paymentMethod.equals(PaymentMethod.MONEY_ORDER) || paymentMethod.equals(PaymentMethod.CASHIERS_CHECK)) {
			FacesUtils.getSessionBean().resetMoneyOrderInfo();
			CurrencyOnHandHistoryBean eb = (CurrencyOnHandHistoryBean) FacesUtils
				.getManagedBean("currencyOnHandHistoryBean");
			if (eb != null) {
				eb.setServiceLocator(this.getServiceLocator());
				eb.reset();
			}
//		}
		return result;
	}
	
	public abstract String createIt() throws Exception;
	
	public abstract String getFollowOnAction();
	
	@Override
	public void validate() throws Exception {
		if (getAmount() == null || getAmount().doubleValue() == 0) {
    		throw new Exception("Amount entered is not valid");
    	}
        
        if (getPaymentMethod().equals(PaymentMethod.CREDIT) && ListUtils.isNullSelection(getCreditCardId())) {
        	throw new Exception("Credit Card is a required field");        	
        }
        
        if ((getPaymentMethod().equals(PaymentMethod.CHECK) ||
        		getPaymentMethod().equals(PaymentMethod.DEBIT) ||
        		getPaymentMethod().equals(PaymentMethod.E_PAYMENT)) && ListUtils.isNullSelection(getBankAccountId())) {
        	throw new Exception("Bank Account is a required field");        	
        }
        
        if (getPaymentMethod().equals(PaymentMethod.CHECK) && StringUtils.isEmptyString(getCheckNumber())) {
        	throw new Exception("Check Number is a required field");        	
        }
        
        if ((getPaymentMethod().equals(PaymentMethod.CASHIERS_CHECK) || 
        		getPaymentMethod().equals(PaymentMethod.MONEY_ORDER)) && ListUtils.isNullSelection(getMoneyOrderId())) {
        	throw new Exception("Money Order is a required field");
        }
	}
	
	
	public BigDecimal getAmount() {
    	return amount;
    }

    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }

    public void setAmount(BigDecimal amount) {
    	if (PaymentMethod.CASHIERS_CHECK.equals(getPaymentMethod()) || 
    			PaymentMethod.MONEY_ORDER.equals(getPaymentMethod())) {
    		return;
    	}
    	
    	this.amount = amount;
    	
    }

    public Date getDate() {
        if (date == null) {
            date = new Date();
        }
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public int getPaymentMethodId() {
        if (paymentMethod != null) {
            return paymentMethod.toInt();
        }
        return PaymentMethod.UNKNOWN.toInt();
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethod = PaymentMethod.fromInt(paymentMethodId);
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    
    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }
    
    public Long getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(Long creditCardId) {
        this.creditCardId = creditCardId;
    }

    public Long getMoneyOrderId() {
        return moneyOrderId;
    }

    public void setMoneyOrderId(Long moneyOrderId) {
        this.moneyOrderId = moneyOrderId;
    }
    
    public void updateByPaymentMethod(ValueChangeEvent  e) {
        updateByPaymentMethod(e.getNewValue().toString());
    }
    
    public void updateByPaymentMethod(String newValue) {
    	
    	if (newValue.equals(String.valueOf(PaymentMethod.CASH.toInt()))) {

            renderBankAccountPanel = false;
            renderCheckNumberPanel = false;
            renderCreditCardPanel = false;
            renderMoneyOrderPanel = false;
        }
        // check
        else if (newValue.equals(String.valueOf(PaymentMethod.CHECK.toInt()))) {
            renderBankAccountPanel = true;
            renderCheckNumberPanel = true;
            renderCreditCardPanel = false;
            renderMoneyOrderPanel = false;
        // credit card
        } else if (newValue.equals(String.valueOf(PaymentMethod.CREDIT.toInt()))) {
            
            renderBankAccountPanel = false;
            renderCheckNumberPanel = false;
            renderCreditCardPanel = true;
            renderMoneyOrderPanel = false;
           
        // Check card
        } else if (newValue.equals(String.valueOf(PaymentMethod.DEBIT.toInt())) ||
        		newValue.equals(String.valueOf(PaymentMethod.E_PAYMENT.toInt()))) {
            renderBankAccountPanel = true;
            renderCheckNumberPanel = false;
            renderCreditCardPanel = false;

        // Money Order
        } else if (newValue.equals(String.valueOf(PaymentMethod.MONEY_ORDER.toInt())) ||
        		newValue.equals(String.valueOf(PaymentMethod.CASHIERS_CHECK.toInt()))) {
            renderBankAccountPanel = false;
            renderCheckNumberPanel = false;
            renderCreditCardPanel = false;
            renderMoneyOrderPanel = true;
            
            if (newValue.equals(String.valueOf(PaymentMethod.MONEY_ORDER.toInt()))) {
            	this.paymentMethod = PaymentMethod.MONEY_ORDER;
            } else if (newValue.equals(String.valueOf(PaymentMethod.CASHIERS_CHECK.toInt()))) {
            	this.paymentMethod = PaymentMethod.CASHIERS_CHECK;
            }
            
            if (getMoneyOrderId() == null || ListUtils.isNullSelection(getMoneyOrderId())) {
            	this.amount = new BigDecimal(0);
            } else {
            	CurrencyOnHand coh = findMoneyOrder(getMoneyOrderId());
            	if (coh != null) {
            		this.amount = coh.getAmount();
            	}
            }
        }
    }
    
    public void updateByMoneyOrder(ValueChangeEvent  e) {
    	Long selectedId = null;
    	if (e.getNewValue() != null) {
    		selectedId = Long.parseLong(e.getNewValue().toString());
    	}
    	
    	if (selectedId == null || ListUtils.isNullSelection(selectedId)) {
        	this.amount = new BigDecimal(0);
        } else {
        	CurrencyOnHand coh = findMoneyOrder(selectedId);
        	if (coh != null) {
        		this.amount = coh.getAmount();
        	}
        }
    }
    
    
    private CurrencyOnHand findMoneyOrder(Long id) {
    	if (editMode && id.longValue() == oldMoneyOrderId.longValue()) {
    		CurrencyOnHand oldOne = this.serviceLocator.getCurrencyOnHandService().findCurrencyOnHand(
    				String.valueOf(this.oldMoneyOrderId));
    		return oldOne;
    	} else {
    		List<CurrencyOnHand> availableMoneyOrders = ListUtils.getAvailableMoneyOrdersList(
    			this.serviceLocator.getCurrencyOnHandService());
    		for (CurrencyOnHand mo : availableMoneyOrders) {
    			if (mo.getId().longValue() == id.longValue()) {
    				return mo;
    			}
    		}
    	}
    	
    	return null;
    }
    
    
    public boolean isRenderBankAccountPanel() {
        return renderBankAccountPanel;
    }

    public void setRenderBankAccountPanel(boolean renderBankAccountPanel) {
        this.renderBankAccountPanel = renderBankAccountPanel;
    }

    public boolean isRenderCheckNumberPanel() {
        return renderCheckNumberPanel;
    }

    public void setRenderCheckNumberPanel(boolean renderCheckNumberPanel) {
        this.renderCheckNumberPanel = renderCheckNumberPanel;
    }

    public boolean isRenderCreditCardPanel() {
        return renderCreditCardPanel;
    }

    public void setRenderCreditCardPanel(boolean renderCreditCardPanel) {
        this.renderCreditCardPanel = renderCreditCardPanel;
    }
    
    public boolean isRenderMoneyOrderPanel() {
        return renderMoneyOrderPanel;
    }

    public void setRenderMoneyOrderPanel(boolean renderMoneyOrderPanel) {
        this.renderMoneyOrderPanel = renderMoneyOrderPanel;
    }

    public boolean isRenderExpenseAmountPanel() {
        return !this.renderMoneyOrderPanel;
    }
    
    public void clearAllFields() {
    	this.amount = null;        
        this.bankAccountId = null;
        this.checkNumber = null;
        this.creditCardId = null;
        this.date = null;
        this.moneyOrderId = null;
        this.paymentMethod = null;
        this.renderBankAccountPanel = false;
        this.renderCheckNumberPanel = false;
        this.renderCreditCardPanel = false;
        this.renderMoneyOrderPanel = false;        
    }
    
    public List<SelectItem> getMoneyOrderList() {
		CurrencyOnHand oldOne = null;
		if (editMode) {
	        if (this.oldMoneyOrderId != null && this.oldMoneyOrderId != -1) {
	    		// Find the old money order and add it to the list. It is not added by default because
	    		// it has already been used. 
	    		oldOne = this.serviceLocator.getCurrencyOnHandService().findCurrencyOnHand(
	    				String.valueOf(this.oldMoneyOrderId));	    		
	        		
	        }
        }
		
		return ListUtils.getAvailableMoneyOrdersUIList(this.serviceLocator, oldOne, true);
		
    }
	
	public List<SelectItem> getPaymentMethodList() {
    	return ListUtils.getPaymentMethodList(serviceLocator);
    }
	
	public List<SelectItem> getBankAccountList() {
		if (this.serviceLocator == null) {
            this.serviceLocator = FacesUtils.getSessionBean().getServiceLocator();
        }
        return ListUtils.getBankAccountList(serviceLocator, true);
    }
            
    public List<SelectItem> getCreditCardList() {
    	if (this.serviceLocator == null) {
            this.serviceLocator = FacesUtils.getSessionBean().getServiceLocator();
        }
        return ListUtils.getCreditCardList(serviceLocator, true);
        
    }
    
    public String addBankAccountAction() throws EntityException {
        FacesUtils.getSessionBean().setFollowOnAction(
        		getFollowOnAction());
        return NavigationResults.CREATE_BANK_ACCOUNT_REQUESTED;
    }
    
    public String addMoneyOrderAction() throws EntityException {
        FacesUtils.getSessionBean().setFollowOnAction(
        		getFollowOnAction());
        return NavigationResults.CREATE_MONEY_ORDER_REQUESTED;
    }
    
    public String addCreditCardAction() throws EntityException {
        FacesUtils.getSessionBean().setFollowOnAction(
        		getFollowOnAction());
        return NavigationResults.CREATE_CREDIT_CARD_REQUESTED;
    }
}
