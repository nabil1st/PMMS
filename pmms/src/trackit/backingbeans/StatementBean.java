/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import trackit.businessobjects.BankAccount;
import trackit.businessobjects.BankAccountTransaction;
import trackit.businessobjects.BankTransactionType;
import trackit.businessobjects.CreditCard;
import trackit.businessobjects.CurrencyOnHand;
import trackit.businessobjects.Deposit;
import trackit.businessobjects.Expense;
import trackit.businessobjects.Payee;
import trackit.businessobjects.PaymentMethod;
import trackit.businessobjects.TransactionReason;
import trackit.util.DateFormatUtil;
import trackit.util.FacesUtils;

/**
 *
 * @author Owner
 */
public class StatementBean extends ListBean {
    private List<StatementItem> statementItems;
    
    private Date toDate;
    private Date fromDate;

    private BankAccount bankAccount;

    public StatementBean() {        
        toDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(toDate);
        c.add(Calendar.DAY_OF_MONTH, -60);
        fromDate = c.getTime();
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {        
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {        
        this.toDate = toDate;
    }


    public List<StatementItem> getStatementItems() {

        
        //if (statementItems == null) {

            // Use the payee name in the description field. Need to lookup
            // payee name by payeeId.
            // therefore, load list of payees in advance
        String currentAccountId = FacesUtils.getSessionBean().getCurrentAccountId();

        this.bankAccount = getBankAccount();

        List<Payee> payees = this.serviceLocator.getPayeeService().getAllPayeesForAccount(
                currentAccountId);

        Map<Long,String> payeeLookupMap = new HashMap<Long,String>();
        for (Payee p : payees) {
            payeeLookupMap.put(p.getId(), p.getDescription());
        }

        List<CreditCard> creditCards = 
                this.serviceLocator.getCreditCardService().
                    getAllCreditCardsForAccount(currentAccountId);
        Map<Long,String> creditCardLookupMap = new HashMap<Long,String>();
        for (CreditCard c : creditCards) {
            creditCardLookupMap.put(c.getId(), c.getDescription());
        }

        Calendar c = Calendar.getInstance();

        c.setTime(toDate);
        c.add(Calendar.DAY_OF_MONTH, 1);
        
        Date to_date = c.getTime();


        statementItems = new ArrayList<StatementItem>();
        List<Expense> expenses = this.serviceLocator.getExpenseService().getAllExpensesForBankAccount(
                FacesUtils.getSessionBean().getCurrentBankAccountId(), fromDate, to_date);
        for (Expense e : expenses) {
            StatementItem si = new StatementItem();
            si.setDate(e.getDate());
            si.setDateStr(DateFormatUtil.formatDate(e.getDate()));
            si.setTo(payeeLookupMap.get(e.getPayeeId()));
            si.setAmount(e.getAmount());
            si.setTransactionType(BankTransactionType.UNKNOWN);

            StringBuffer description = new StringBuffer();

            if (e.getPaymentMethod().equals(PaymentMethod.CHECK)) {
                description.append("Check Number ").append(e.getCheckNumber());
            } else if (e.getPaymentMethod().equals(PaymentMethod.DEBIT)) {
                description.append("Payment by Debit Card");
            } else if (e.getPaymentMethod().equals(PaymentMethod.E_PAYMENT)) {
                description.append("Electronic Payment");
            }

            si.setDescription(description.toString());
            
            statementItems.add(si);
        }

        // get all other bank account transactions for bank account
        List<BankAccountTransaction> transactions =  this.serviceLocator.getBankAccountService().
                getBankAccountTransactionsForAccount(
                FacesUtils.getSessionBean().getCurrentBankAccountId(), fromDate, to_date);

        for (BankAccountTransaction e : transactions) {
            StatementItem si = new StatementItem();
            si.setDate(e.getDate());
            si.setDateStr(DateFormatUtil.formatDate(e.getDate()));
            si.setAmount(e.getAmount());
            
            
            if (e.getInitiatorId() != null) {
                if (e.getTransactionReason().equals(TransactionReason.CREDIT_CARD_PAYMENT)) {
                    si.setTo(creditCardLookupMap.get(e.getInitiatorId()));
                } else {
                    si.setTo(payeeLookupMap.get(e.getInitiatorId()));
                }
            }


            StringBuffer description = new StringBuffer();

            if (e.getTransactionType().equals(BankTransactionType.ATM_WITHDRAWL)) {
                description.append("ATM Withdrawl");
            } else if (e.getTransactionType().equals(BankTransactionType.BANK_FEE)) {
                description.append("Bank Fee");
            } else if (e.getTransactionType().equals(BankTransactionType.DEPOSIT)) {
                description.append("Deposit");
            } else if (e.getTransactionType().equals(BankTransactionType.INTEREST)) {
                description.append("Interest Paid");
            } else if (e.getTransactionType().equals(BankTransactionType.OPENING_BALANCE)) {
                description.append("Starting Balance");
            } else if (e.getTransactionType().equals(BankTransactionType.CHECK_WITHDRAWL)) {
                description.append("Check Withdrawl ").append(e.getCheckNumber());
            } else if (e.getTransactionType().equals(BankTransactionType.DEBIT)) {
                description.append("Debit");
            }

            if (e.getTransactionReason() != null) {
                if (e.getTransactionReason().equals(TransactionReason.MONEY_ORDER_PURCHASE)) {
                    description.append(" ").append("Money Order Purchase");
                } else if (e.getTransactionReason().equals(TransactionReason.CREDIT_CARD_PAYMENT)) {
                    description.append(" ").append("Credit Card Payment");
                } if (e.getTransactionReason().equals(TransactionReason.LOAN)) {
                    description.append(" ").append("Loan");
                }
            }

            si.setDescription(description.toString());

            si.setTransactionType(e.getTransactionType());
            
            statementItems.add(si);
        }

        List<Deposit> deposits = 
                this.serviceLocator.getDepositService().getAllDepositsForBankAccount(
                    FacesUtils.getSessionBean().getCurrentBankAccountId(), fromDate, to_date);

        for (Deposit deposit : deposits) {
            StatementItem si = new StatementItem();
            si.setDate(deposit.getDate());
            si.setDateStr(DateFormatUtil.formatDate(deposit.getDate()));

            BigDecimal total = new BigDecimal(0);
            if (deposit.getTotalCash() != null) {
                total = total.add(deposit.getTotalCash());
            }

            if (deposit.getDepositItems() != null) {
                for (CurrencyOnHand coh : deposit.getDepositItems()) {
                    total = total.add(coh.getAmount());
                }
            }

            si.setAmount(total);

            si.setDescription("Deposit");

            si.setTo("");

            si.setTransactionType(BankTransactionType.DEPOSIT);

            statementItems.add(si);
        }
        


        //}

        Collections.sort(statementItems, new StatementItemComparator());
        return statementItems;
    }

    public void setStatementItems(List<StatementItem> statementItems) {
        this.statementItems = statementItems;
    }

    public String homeAction() {
        return NavigationResults.HOME;
    }

    public String summaryAction() {
        return NavigationResults.BANK_ACCOUNTS_SUMMARY;
    }

    public String depositAction() {
        return NavigationResults.NEW_DEPOSIT;
    }

    public String chargesAction() {
        return NavigationResults.CREATE_BANK_TRANSACTION_REQUESTED;
    }

    public String withdrawlsAction() {
        return NavigationResults.CREATE_BANK_WITHDRAWL_REQUESTED;
    }

    public BigDecimal getBankAccountBalance() {
        return getBankAccount().getBalance();
    }
    
    public String getBankName(){
        return getBankAccount().getDescription();
    }

    private BankAccount getBankAccount() {
//        if (this.bankAccount == null ||
//            !this.bankAccount.getId().toString().equals(FacesUtils.getSessionBean().getCurrentBankAccountId())) {

        if (FacesUtils.getSessionBean().getCurrentBankAccountId() != null) {
                BankAccount account = this.serviceLocator.getBankAccountService().findBankAccount(
                    FacesUtils.getSessionBean().getCurrentBankAccountId());
                return account;
        }

        return null;
//        } else {
//            return this.bankAccount;
//        }
    }

    public boolean isRendered() {
        return getBankAccount() != null;
    }

	@Override
	public void deleteItem(IListItem item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editItem(IListItem item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getEditNavigationId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getListNavigaionId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
