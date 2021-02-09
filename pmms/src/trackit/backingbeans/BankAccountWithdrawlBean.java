/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import trackit.exception.EntityException;
import trackit.util.FacesUtils;
import trackit.util.ListUtils;

/**
 *
 * @author Nabil
 */
public class BankAccountWithdrawlBean extends BaseBean {
    private Date date = new Date();
    private BigDecimal amount;
    private Long bankAccountId;
    private String checkNumber;
    private Long userId;

    public BankAccountWithdrawlBean(){}

    public BigDecimal getAmount() {
        return amount;
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


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String createAction() throws EntityException {

        this.serviceLocator.getBankAccountTransactionalService().setServiceLocator(serviceLocator);

        try {
            this.serviceLocator.getBankAccountTransactionalService().saveBankWithdrawl(this);
        } catch (Exception ex) {
            String msg = "Bank Transaction was not created";
            FacesUtils.addErrorMessage(msg + ", please try again.");
            this.logger.debug(msg);
            return "";
        }

        clearAllFields();

        return NavigationResults.BANK_WITHDRAWL_CREATED;
    }

    public void clearAllFields() {
        this.amount = null;
        this.bankAccountId = null;
        this.date = null;
        this.checkNumber = null;
    }

    public String addBankAccountAction() throws EntityException {
        FacesUtils.getSessionBean().setFollowOnAction(
                NavigationResults.CREATE_BANK_WITHDRAWL_REQUESTED);
        return NavigationResults.CREATE_BANK_ACCOUNT_REQUESTED;
    }

    public String cancelAction() throws EntityException {
        clearAllFields();
        return NavigationResults.CREATE_BANK_WITHDRAWL_CANCELLED;
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

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }



}
