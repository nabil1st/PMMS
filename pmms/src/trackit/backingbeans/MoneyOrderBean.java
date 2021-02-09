package trackit.backingbeans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import trackit.businessobjects.*;
import trackit.util.FacesUtils;
import trackit.util.ListUtils;
import trackit.util.StringUtils;
import trackit.businessobjects.PaymentMethod;


/**
 *
 * @author Nabil
 */
public class MoneyOrderBean extends GenericPaymentEnabledBean {
    private BigDecimal fee;
    private Long issuerId;    
    private Long userId;

    private List<SelectItem> completePaymentMethodList;
    private List<SelectItem> partialPaymentMethodList;
    private List<SelectItem> paymentMethodList;
    private String number;


    public MoneyOrderBean(){
        completePaymentMethodList = new ArrayList<SelectItem>();
        completePaymentMethodList.add(new SelectItem(PaymentMethod.CASH.toInt(), "Cash"));
        completePaymentMethodList.add(new SelectItem(PaymentMethod.CHECK.toInt(), "Check"));
        completePaymentMethodList.add(new SelectItem(PaymentMethod.CREDIT.toInt(), "Credit Card"));
        completePaymentMethodList.add(new SelectItem(PaymentMethod.DEBIT.toInt(), "Debit"));
        completePaymentMethodList.add(new SelectItem(PaymentMethod.E_PAYMENT.toInt(), "E Payment"));
        
        partialPaymentMethodList = new ArrayList<SelectItem>();
        partialPaymentMethodList.add(new SelectItem(PaymentMethod.CASH.toInt(), "Cash"));
        partialPaymentMethodList.add(new SelectItem(PaymentMethod.CHECK.toInt(), "Check"));
        partialPaymentMethodList.add(new SelectItem(PaymentMethod.DEBIT.toInt(), "Debit"));

        paymentMethodList = completePaymentMethodList;
        
    }
    
    public Long getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(Long payeeId) {
        if (ListUtils.isNullSelection(payeeId)) {
            this.issuerId = null;
        } else {
            this.issuerId = payeeId;
        }
    }    
    
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }    
    
    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    @Override
    public List<SelectItem> getPaymentMethodList() {
        return paymentMethodList;
    }

    public List<SelectItem> getIssuerList() {        
    	List<SelectItem> payeeList = ListUtils.getPayeeList(this.serviceLocator, false);        
        payeeList.add(0, new SelectItem(ListUtils.NULL_CONST, "Bank Account"));
        return payeeList;
    }
    
    public void updateByIssuer(ValueChangeEvent  e) {
        if (e.getNewValue().toString().equals("-10")) {
            paymentMethodList = partialPaymentMethodList;
            setRenderBankAccountPanel(true);
            setRenderCheckNumberPanel(false);
            setRenderCreditCardPanel(false);
        } else {
            paymentMethodList = completePaymentMethodList;
        }

    }

    @Override
    public String createIt() throws Exception {
        this.serviceLocator.getMoneyOrderTransactionalService().setServiceLocator(serviceLocator);
        try {
            this.serviceLocator.getMoneyOrderTransactionalService().saveMoneyOrder(this);
        } catch (Exception ex) {
            throw new Exception("Money Order was not created");            
        }

        clearAllFields();

        FacesUtils.getSessionBean().resetMoneyOrderInfo();
        
        if (FacesUtils.getSessionBean().getFollowOnAction() != null) {
            String followOnAction = FacesUtils.getSessionBean().getFollowOnAction();
            FacesUtils.getSessionBean().setFollowOnAction(null);
            return followOnAction;
        }

        return NavigationResults.MONEY_ORDER_CREATED;
    }

    @Override
    public void clearAllFields() {
        super.clearAllFields();
        this.fee = null;
        this.issuerId = null;
        this.number = null;
        this.paymentMethod = PaymentMethod.CASH;        
    }

    
    @Override
    public String doCancel() {
    	if (FacesUtils.getSessionBean().getFollowOnAction() != null) {
            String followOnAction = FacesUtils.getSessionBean().getFollowOnAction();
            FacesUtils.getSessionBean().setFollowOnAction(null);
            return followOnAction;
        }
        return NavigationResults.NEW_MONEY_ORDER_CANCELLED;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    
    @Override
    public String getFollowOnAction() {
    	return NavigationResults.CREATE_MONEY_ORDER_REQUESTED;
    }

	@Override
	public void editIt(Entity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getListBeanName() {
		return "currencyOnHandHistoryBean";
	}
	
	@Override
	public void validate() throws Exception {
		super.validate();
		if (StringUtils.isEmptyString(this.number)) {
			throw new Exception("Money Order Number is a required field");
		}
		
		if (this.fee == null || this.fee.doubleValue() < 0) {
			throw new Exception("Money Order Fee of 0 or more is required");
		}
		
	}


}
