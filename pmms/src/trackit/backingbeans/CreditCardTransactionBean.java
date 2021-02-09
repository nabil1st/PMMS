/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import trackit.businessobjects.CreditCardTransactionType;
import trackit.exception.EntityException;
import trackit.util.FacesUtils;
import trackit.util.ListUtils;

/**
 *
 * @author Nabil
 */
public class CreditCardTransactionBean extends BaseBean {
    private Date date = new Date();
    private BigDecimal amount;
    private Long creditCardId;
    private CreditCardTransactionType transactionType;
    private Long userId;

    public CreditCardTransactionBean(){}

    public BigDecimal getAmount() {
        return amount;
    }

    public CreditCardTransactionType getTransactionType() {
        return this.transactionType;
    }

    public void setAmount(BigDecimal amount) {
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

    

    public int getTransactionTypeId() {
        if (transactionType != null) {
            return transactionType.toInt();
        }
        return CreditCardTransactionType.UNKNOWN.toInt();
    }

    public void setTransactionType(CreditCardTransactionType type) {
        this.transactionType = type;
    }

    public void setTransactionTypeId(int typeId) {
        this.transactionType = CreditCardTransactionType.fromInt(typeId);
    }   

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List getTransactionTypeList() {
        return ListUtils.getCreditCardTransactionTypeList();
    }    

    public String createAction() throws EntityException {       

        this.serviceLocator.getCreditCardTransactionalService().setServiceLocator(serviceLocator);

        try {
            this.serviceLocator.getCreditCardTransactionalService().saveCreditCardTransaction(this);
        } catch (Exception ex) {
            String msg = "Credit Card Transaction was not created";
            FacesUtils.addErrorMessage(msg + ", please try again.");
            this.logger.debug(msg);
            return "";
        }

        clearAllFields();

        return NavigationResults.CREDIT_CARD_TRANSACTION_CREATED;
    }

    public void clearAllFields() {
        this.amount = null;
        this.creditCardId = null;
        this.date = null;
        this.transactionType = CreditCardTransactionType.UNKNOWN;
    }

    public String addCreditCardAction() throws EntityException {
        FacesUtils.getSessionBean().setFollowOnAction(
                NavigationResults.CREATE_CREDIT_CARD_TRANSACTION_REQUESTED);
        return NavigationResults.CREATE_CREDIT_CARD_REQUESTED;
    }

    public String cancelAction() throws EntityException {
        clearAllFields();
        return NavigationResults.CREATE_CREDIT_CARD_TRANSACTION_CANCELLED;
    }

    public Long getCreditCardId() {
        if (creditCardId != null) {
            return creditCardId;
        }

        if (FacesUtils.getSessionBean().getCurrentCreditCardId() != null) {
            return Long.parseLong(FacesUtils.getSessionBean().getCurrentCreditCardId());
        }

        return null;
    }

    public void setCreditCardId(Long creditCardId) {
        this.creditCardId = creditCardId;
    }   

    public List<SelectItem> getCreditCardList() {
        return ListUtils.getCreditCardList(this.serviceLocator, true);
    }

}
