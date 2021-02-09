/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;
import trackit.businessobjects.BankTransactionType;
import trackit.businessobjects.CreditCardTransactionType;
import trackit.exception.EntityException;
import trackit.util.FacesUtils;
import trackit.util.ListUtils;

/**
 *
 * @author Nabil
 */
public class BankAccountTransactionBean extends BaseBean {
    private Date date = new Date();
    private BigDecimal amount;
    private Long bankAccountId;
    private BankTransactionType transactionType;
    private Long userId;

    public BankAccountTransactionBean(){}

    public BigDecimal getAmount() {
        return amount;
    }

    public BankTransactionType getTransactionType() {
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

    public void setTransactionType(BankTransactionType type) {
        this.transactionType = type;
    }

    public void setTransactionTypeId(int typeId) {
        this.transactionType = BankTransactionType.fromInt(typeId);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<SelectItem> getTransactionTypeList() {
        List<SelectItem> dropList = new ArrayList<SelectItem>();
        dropList.add(new SelectItem(BankTransactionType.BANK_FEE.toInt(), "Bank Fee"));
        dropList.add(new SelectItem(BankTransactionType.INTEREST.toInt(), "Interest Paid"));

        return dropList;
    }

    public String createAction() throws EntityException {

        this.serviceLocator.getBankAccountTransactionalService().setServiceLocator(serviceLocator);

        try {
            this.serviceLocator.getBankAccountTransactionalService().saveBankTransaction(this);
        } catch (Exception ex) {
            String msg = "Bank Transaction was not created";
            FacesUtils.addErrorMessage(msg + ", please try again.");
            this.logger.debug(msg);
            return "";
        }

        clearAllFields();

        return NavigationResults.BANK_TRANSACTION_CREATED;
    }

    public void clearAllFields() {
        this.amount = null;
        this.bankAccountId = null;
        this.date = null;
        this.transactionType = BankTransactionType.UNKNOWN;
    }

    public String addBankAccountAction() throws EntityException {
        FacesUtils.getSessionBean().setFollowOnAction(
                NavigationResults.CREATE_BANK_TRANSACTION_REQUESTED);
        return NavigationResults.CREATE_BANK_ACCOUNT_REQUESTED;
    }

    public String cancelAction() throws EntityException {
        clearAllFields();
        return NavigationResults.CREATE_BANK_TRANSACTION_CANCELLED;
    }

    public Long getBankAccountId() {
        if (bankAccountId != null) {
            return bankAccountId;
        }

        if (FacesUtils.getSessionBean().getCurrentBankAccountId() != null) {
            return Long.parseLong(FacesUtils.getSessionBean().getCurrentBankAccountId());
        }

        return null;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public List<SelectItem> getBankAccountList() {
        return ListUtils.getBankAccountList(this.serviceLocator, false);
    }

}
