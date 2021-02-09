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
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import trackit.businessobjects.BankAccount;
import trackit.businessobjects.Borrower;
import trackit.businessobjects.CurrencyOnHandType;
import trackit.businessobjects.Loan;
import trackit.businessobjects.Payee;
import trackit.exception.EntityException;
import trackit.util.DateFormatUtil;
import trackit.util.FacesUtils;
import trackit.util.ListUtils;

/**
 *
 * @author Nabil
 */
public class LoanPaymentBean extends BaseBean {
    private Long id;
    private Date date = new Date();
    private BigDecimal amount;
    private Long loanId;
    private Long payerId;
    private String checkNumber;
    private Long userId;
    private CurrencyOnHandType currencyType;
    private Long bankAccountId;
    
    private boolean renderBankAccountPanel;
    private boolean renderCheckNumberField;

    public LoanPaymentBean(){}

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


    public boolean isRenderCheckNumberField() {
        return renderCheckNumberField;
    }

    public Long getLoanId() {
        if (loanId != null) {
            return loanId;
        }

        return Long.parseLong(FacesUtils.getSessionBean().getCurrentLoanId());
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public Long getPayerId() {
        return payerId;
    }

    public void setPayerId(Long payerId) {
        this.payerId = payerId;
    }


    public List<SelectItem> getPayerList() {
        return ListUtils.getBorrowersList(this.serviceLocator, true);
    }

    public List<SelectItem> getLoanList() {
        List<Loan> loans = this.serviceLocator.getLoanService().getAllLoansForAccount(
                FacesUtils.getSessionBean().getCurrentAccountId());
        List<SelectItem> dropList = new ArrayList<SelectItem>();
        Map<Long,Payee> payeeLookup = createPayeeLookup();
        for (Loan p : loans) {
            p.setPayeeLookup(payeeLookup);
            Borrower borrower = this.serviceLocator.getBorrowerService().findBorrower(p.getBorrowerId().toString());
            StringBuffer description = new StringBuffer();
            description.append(borrower.getName()).
                    append("-").
                    append(DateFormatUtil.formatDate(p.getDate())).
                    append(" (").
                    append(p.getAmount()).
                    append(")");
            dropList.add(new SelectItem(p.getId(), description.toString()));
        }
        return dropList;
    }

    private Map<Long,Payee> createPayeeLookup() {
        List<Payee> payees = this.serviceLocator.getPayeeService().getAllPayeesForAccount(
                FacesUtils.getSessionBean().getCurrentAccountId());
        Map<Long,Payee> lookup = new HashMap<Long,Payee>();
        for (Payee payee : payees) {
            lookup.put(payee.getId(), payee);
        }

        return lookup;
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

    public String createAction() throws EntityException {
        //Income income = IncomeBuilder.createIncome(this);
//        Long currentUserId = Long.parseLong(
//                FacesUtils.getSessionBean().getCurrentUserId());
//
//        Loan loan = this.serviceLocator.getLoanService().getLoan(String.valueOf(loanId));
//
//        CurrencyOnHand coh = new CurrencyOnHand();
//        coh.setUserId(currentUserId);
//        coh.setAmount(getAmount());
//        coh.setCheckNumber(getCheckNumber());
//        coh.setDate(getDate());
//        coh.setType(currencyType);
//        coh.setSourceType(CurrencyOnHandSourceType.LOAN_PAYMENT);
//        if (payerId != null) {
//            coh.setSourceId(payerId);
//        } else {
//            coh.setSourceId(loan.getBorrowerId());
//        }
//
//        coh = this.serviceLocator.getCurrencyOnHandService().saveCurrencyOnHand(coh);
//
//
//
//        loan.getLoanPayments().add(coh);
//
//        this.serviceLocator.getLoanService().saveLoan(loan);

        this.serviceLocator.getLoanTransactionalService().setServiceLocator(serviceLocator);
        try {
            this.serviceLocator.getLoanTransactionalService().saveLoanPayment(this);
            CurrencyOnHandHistoryBean eb = (CurrencyOnHandHistoryBean) FacesUtils
				.getManagedBean("currencyOnHandHistoryBean");
			if (eb != null) {
				eb.setServiceLocator(this.getServiceLocator());
				eb.reset();
			}
        } catch (Exception ex) {
            String msg = "Loan payment was not created";
            FacesUtils.addErrorMessage(msg + ", please try again.");
            this.logger.debug(msg);
            return "";
        }

        clearAllFields();

        return NavigationResults.LOAN_PAYMENT_CREATED;
    }

    public String cancelAction() throws EntityException {
        clearAllFields();
        return NavigationResults.NEW_LOAN_PAYMENT_CANCELLED;
    }


    private void clearAllFields() {
        this.amount = null;
        this.checkNumber = null;
        this.currencyType = CurrencyOnHandType.UNKNOWN;
        this.date = null;
        this.loanId = null;
        this.payerId = null;
        this.renderCheckNumberField = false;
        
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
            renderCheckNumberField = true;

        } else if (selectedValue.equals(
                String.valueOf(CurrencyOnHandType.DIRECT_DEPOSIT.toInt()))) {
            renderBankAccountPanel = true;

        }

    }

    public int getCurrencyTypeId() {
        if (this.currencyType != null) {
            return currencyType.toInt();
        }

        return CurrencyOnHandType.UNKNOWN.toInt();
    }

    public void setCurrencyTypeId(int value) {
        this.currencyType = CurrencyOnHandType.fromInt(value);
    }
    
    public String addBankAccountAction() throws EntityException {
        FacesUtils.getSessionBean().setFollowOnAction(
                NavigationResults.CREATE_EXPENSE_REQUESTED);
        return NavigationResults.CREATE_LOAN_PAYMENT_REQUESTED;
    }
    
    public List<SelectItem> getBankAccountList() {
        List<BankAccount> ets = this.serviceLocator.getBankAccountService().
                getAllBankAccountsForAccount(
                    FacesUtils.getSessionBean().getCurrentAccountId());
        List<SelectItem> dropList = new ArrayList<SelectItem>();
        for (BankAccount et : ets) {
            dropList.add(new SelectItem(et.getId(), et.getDescription()));
        }
        return dropList;

    }

	public Long getBankAccountId() {
		return bankAccountId;
	}

	public void setBankAccountId(Long bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	public boolean isRenderBankAccountPanel() {
		return renderBankAccountPanel;
	}

	public void setRenderBankAccountPanel(boolean renderBankAccountPanel) {
		this.renderBankAccountPanel = renderBankAccountPanel;
	}
    
    
}
