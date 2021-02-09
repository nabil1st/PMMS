/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import trackit.businessobjects.CurrencyOnHandType;
import trackit.businessobjects.Entity;
import trackit.exception.EntityException;
import trackit.util.FacesUtils;
import trackit.util.ListUtils;

/**
 *
 * @author Nabil
 */
public class IncomeBean extends GenericEntityBean {
    private Long id;
    private Date date = new Date();
    private BigDecimal amount;
    private Long incomeSourceId;
    private String checkNumber;
    private Long expenseGroupId;
    private Long userId;
    private CurrencyOnHandType currencyType;
    private int currencyTypeId;
    private Long bankAccountId;
//    private Long depositId;

    private boolean renderCheckNumberField;
    private boolean renderBankAccountPanel;

    public IncomeBean(){}

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

    public Long getExpenseGroupId() {
        return expenseGroupId;
    }

    public void setExpenseGroupId(Long expenseGroupId) {
        this.expenseGroupId = expenseGroupId;
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

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public CurrencyOnHandType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyOnHandType currencyType) {
        this.currencyType = currencyType;
    }

    public int getCurrencyTypeId() {
        return currencyTypeId;
    }

    public void setCurrencyTypeId(int currencyTypeId) {
        this.currencyTypeId = currencyTypeId;
        currencyType = CurrencyOnHandType.fromInt(currencyTypeId);
    }
    
    

    public Long getBankAccountId() {
		return bankAccountId;
	}

	public void setBankAccountId(Long bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	public boolean isRenderCheckNumberField() {
//        return this.currencyTypeId == CurrencyOnHandType.CHECK.toInt() ||
//                this.currencyTypeId == CurrencyOnHandType.MONEY_ORDER.toInt() ||
//                this.currencyTypeId == CurrencyOnHandType.CASHIERS_CHECK.toInt();
        return renderCheckNumberField;
    }
	
	public boolean isRenderBankAccountPanel() {
      return renderBankAccountPanel;
	}
	
	public List<SelectItem> getBankAccountList() {
        return ListUtils.getBankAccountList(this.serviceLocator, true);

    }

    



//    public Long getDepositId() {
//        return depositId;
//    }
//
//    public void setDepositId(Long depositId) {
//        this.depositId = depositId;
//    }

    public Long getIncomeSourceId() {
        return incomeSourceId;
    }

    public void setIncomeSourceId(Long incomeSourceId) {
        this.incomeSourceId = incomeSourceId;
    }
    
    public List<SelectItem> getIncomeSourceList() {
        return ListUtils.getIncomeSourceList(this.serviceLocator, true);
    }

    public List<SelectItem> getExpenseGroupList() {
        return ListUtils.getExpenseGroupList(this.serviceLocator, true);
    }

    public List<SelectItem> getCurrencyTypeList() {

        List<SelectItem> dropList = new ArrayList<SelectItem>();

        for (CurrencyOnHandType value : CurrencyOnHandType.values()) {
            if (value.toInt() == -1) {
                continue;
            }
            
            dropList.add(new SelectItem(value.toInt(), value.toString()));
        }

        return dropList;
    }
    
    

    private void clearAllFields() {
        this.amount = null;
        this.checkNumber = null;
        this.currencyType = CurrencyOnHandType.UNKNOWN;
        this.currencyTypeId = -1;
        this.date = null;
        this.expenseGroupId = null;
        this.incomeSourceId = null;
        this.bankAccountId = null;
    }

    public void updateByCurrencyType(ValueChangeEvent  e) {

        renderCheckNumberField = false;
        renderBankAccountPanel = false;
        String selectedValue =  e.getNewValue().toString();

        if (selectedValue.equals(
                String.valueOf(CurrencyOnHandType.MONEY_ORDER.toInt())) ||
                selectedValue.equals(
                    String.valueOf(CurrencyOnHandType.CHECK.toInt())) ||
                selectedValue.equals(
                    String.valueOf(CurrencyOnHandType.CASHIERS_CHECK.toInt()))) {
//            e.getComponent().findComponent("bank_account_panel").setRendered(false);
//            e.getComponent().findComponent("check_number_panel").setRendered(false);
//            e.getComponent().findComponent("credit_card_panel").setRendered(false);
            renderCheckNumberField = true;

        }
        
        if (selectedValue.equals(
                String.valueOf(CurrencyOnHandType.DIRECT_DEPOSIT.toInt()))) {
            renderBankAccountPanel = true;

        }

    }

    public String addExpenseGroupAction() throws EntityException {
        FacesUtils.getSessionBean().setFollowOnAction(
                NavigationResults.CREATE_NEW_INCOME_REQUESTED);
        return NavigationResults.CREATE_EXPENSE_GROUP_REQUESTED;
    }

    public String addIncomeSourceAction() throws EntityException {
        FacesUtils.getSessionBean().setFollowOnAction(
                NavigationResults.CREATE_NEW_INCOME_REQUESTED);
        return NavigationResults.CREATE_INCOME_SOURCE_REQUESTED;
    }

	@Override
	public String doCancel() {
		clearAllFields();
        return NavigationResults.NEW_INCOME_CANCELLED;
	}

	@Override
	public String doCreateIt() throws Exception {
		this.serviceLocator.getIncomeTransactionalService().setServiceLocator(serviceLocator);
        try {
            this.serviceLocator.getIncomeTransactionalService().saveIncome(this);
        } catch (Exception ex) {
            String msg = "Income was not created";
            FacesUtils.addErrorMessage(msg + ", please try again.");
            this.logger.debug(msg);
            return "";
        }

        clearAllFields();

        return NavigationResults.INCOME_CREATED;
	}

	@Override
	public void editIt(Entity entity) {		
		
	}

	@Override
	public String getListBeanName() {
		return "currencyOnHandHistoryBean";
	}

	@Override
	public void validate() throws Exception {
		if (this.amount.doubleValue() <= 0) {
			throw new Exception("Amount must be a positive number");
		}
		
		if (ListUtils.isNullSelection(incomeSourceId)) {
            throw new Exception("Income Source is a required field");            
        }
		
	}
}
