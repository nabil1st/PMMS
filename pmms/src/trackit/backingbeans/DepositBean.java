/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import trackit.businessobjects.IncomeSource;
import trackit.exception.EntityException;
import trackit.businessobjects.CurrencyOnHand;
import trackit.util.FacesUtils;
import trackit.util.ListUtils;

/**
 *
 * @author Nabil
 */
public class DepositBean extends BaseBean {
    private Long id;
    private Date date = new Date();
    private BigDecimal totalCash;
    private BigDecimal depositTotal;
    private Long bankAccountId;

    private List<String> depositCheckIds;

    private Long userId;

    private List<CurrencyOnHand> undepositedChecksList;

    public DepositBean(){}

    public Date getDate() {
        if (date == null) {
            date = new Date();
        }
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getBankAccountId() {
    	if (bankAccountId == null) {
    		String currentBankAccountId = FacesUtils.getSessionBean().getCurrentBankAccountId();
    		if (currentBankAccountId != null) {
    			bankAccountId = Long.parseLong(currentBankAccountId);
    		}
    	}
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(BigDecimal cashAmount) {
        this.totalCash = cashAmount;
    }

    public BigDecimal getDepositTotal() {
        return depositTotal;
    }

    public void setDepositTotal(BigDecimal depositTotal) {
        this.depositTotal = depositTotal;
    }

    public List<SelectItem> getBankAccountList() {
        return ListUtils.getBankAccountList(this.serviceLocator, false);

    }    

    public List<SelectItem> getUndepositedChecksList() {

        // build income source lookup map
        List<IncomeSource> incomeSources =
                this.serviceLocator.getIncomeService().
                    getAllIncomeSourcesForAccount(
                        FacesUtils.getSessionBean().getCurrentAccountId());

        Map<Long,IncomeSource> incomeSourceLookup = new HashMap<Long,IncomeSource>();
        for (IncomeSource source : incomeSources) {
            incomeSourceLookup.put(source.getId(), source);
        }

        undepositedChecksList = 
                this.serviceLocator.getCurrencyOnHandService().getAllUnusedCurrenciesOnHandForAccount(
                    FacesUtils.getSessionBean().getCurrentAccountId());
        List<SelectItem> dropList = new ArrayList<SelectItem>();
        for (CurrencyOnHand et : undepositedChecksList) {
            et.setIncomeSourceLookup(incomeSourceLookup);
            dropList.add(new SelectItem(et.getId(), et.toString()));
        }

        return dropList;

    }

    public List<String> getDepositCheckIds() {
        return depositCheckIds;
    }

    public void setDepositCheckIds(List<String> depositCheckIds) {
        this.depositCheckIds = depositCheckIds;
    }


        

    public String createAction() throws EntityException {

        // Validate that the deposit is not an empty one
        if (totalCash == null || totalCash.doubleValue() == 0) {
            if (depositCheckIds == null || depositCheckIds.size() == 0) {
                // Invalid deposit
                FacesContext context = FacesContext.getCurrentInstance();
                FacesMessage message = new FacesMessage();
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                message.setSummary("Deposit cannot be empty");
                message.setDetail("Deposit cannot be empty");
                context.addMessage("", message);
                return NavigationResults.DEPOSIT_CREATION_ERROR;
            }
        }

        this.serviceLocator.getDepositTransactionalService().setServiceLocator(serviceLocator);

        try {
            this.serviceLocator.getDepositTransactionalService().saveDeposit(this);
        } catch (Exception ex) {
            String msg = "Bank account was not created";
            FacesUtils.addErrorMessage(msg + ", please try again.");
            this.logger.debug(msg);
            return "";
        }

        clearAllFields();

        return NavigationResults.DEPOSIT_CREATED;
    }

    private CurrencyOnHand findCurrencyOnHand(Long checkId) {
        for (CurrencyOnHand income : undepositedChecksList) {
            if (income.getId().equals(checkId)) {
                return income;
            }
        }

        return null;
    }

    public String calculateTotalAction() throws EntityException {
        BigDecimal total = new BigDecimal(0);

        if (depositCheckIds != null) {
            for (String checkId : depositCheckIds) {
                CurrencyOnHand income = findCurrencyOnHand(Long.parseLong(checkId));
                if (income != null) {
                    total = total.add(income.getAmount());
                }
            }
        }

        if (totalCash != null) {
            total = total.add(totalCash);
        }
        
        depositTotal = total;
        
        return NavigationResults.DEPOSIT_TOTAL_CALCULATED;
    }

    public String cancelAction() throws EntityException {
        clearAllFields();
        return NavigationResults.NEW_DEPOSIT_CANCELLED;
    }

    private void clearAllFields() {
        this.bankAccountId = null;
        this.date = null;
        this.depositCheckIds = null;
        this.depositTotal = null;
        this.totalCash = null;
    }

}
