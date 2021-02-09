/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import trackit.businessobjects.BankAccount;
import trackit.businessobjects.Borrower;
import trackit.businessobjects.CreditCard;
import trackit.businessobjects.CurrencyOnHand;
import trackit.businessobjects.CurrencyOnHandSourceType;
import trackit.businessobjects.IncomeSource;
import trackit.businessobjects.Loan;
import trackit.util.DateFormatUtil;
import trackit.util.FacesUtils;
import trackit.util.ListUtils;

/**
 *
 * @author Owner
 */
public class CapitalReportBean extends BaseBean {

    private List<CurrencyOnHandHistoryItem> currencyOnHandList;
    private List<BankAccount> bankAccountsList;
    private List<LoanSummaryItem> loanList;
    private List<CreditCard> creditCardsList;

    private BigDecimal currencyOnHandBalance = new BigDecimal(0);
    private BigDecimal bankAccountBalance = new BigDecimal(0);
    private BigDecimal loanBalance = new BigDecimal(0);
    private BigDecimal creditCardBalance = new BigDecimal(0);
    private BigDecimal cashBalance = new BigDecimal(0);

    private boolean currencyOnHandListInitialized;
    private boolean bankAccountsInitialized;
    private boolean loanListInitialized;
    private boolean creditCardsInitialized;
    private boolean cashBalanceInitialized;


    public CapitalReportBean() {
    }


    private void initCurrencyOnHandList() {
        String currentAccountId = FacesUtils.getSessionBean().getCurrentAccountId();

        Map<Long,String> incomeSourceLookup = new HashMap<Long,String>();
        Map<Long,String> borrowerLookup = new HashMap<Long,String>();

        //if (CurrencyOnHandSourceType.INCOME.equals(sourceType)) {
            List<IncomeSource> incomeSources =
                    this.serviceLocator.getIncomeService().getAllIncomeSourcesForAccount(currentAccountId);
            for (IncomeSource source : incomeSources) {
                incomeSourceLookup.put(source.getId(), source.getName());
            }
        //} else if (CurrencyOnHandSourceType.LOAN_PAYMENT.equals(sourceType)) {
            List<Borrower> borrowers =
                    this.serviceLocator.getBorrowerService().getAllBorrowersForAccount(currentAccountId);
            for (Borrower borrower : borrowers) {
                borrowerLookup.put(borrower.getId(), borrower.getName());
            }
        //}

        currencyOnHandList = new ArrayList<CurrencyOnHandHistoryItem>();

        List<CurrencyOnHand> currenciesOnHand =
            this.serviceLocator.getCurrencyOnHandService().
                getAllUnusedCurrenciesOnHandForAccount(currentAccountId);

        for (CurrencyOnHand e : currenciesOnHand) {
            CurrencyOnHandHistoryItem si = new CurrencyOnHandHistoryItem();
            si.setDate(e.getDate());
            si.setDateStr(DateFormatUtil.formatDate(e.getDate()));
            si.setAmount(e.getAmount());
            si.setDescription("");

            if (e.getType() != null) {
                si.setCurrencyType(e.getType().toString());
            }

            if (e.getSourceType() != null) {
            	si.setCurrencyOnHandSourceType(e.getSourceType());                
            }

            if (e.getSourceId() != null) {
                if (CurrencyOnHandSourceType.LOAN_PAYMENT.equals(e.getSourceType())) {
                    if (borrowerLookup.containsKey(e.getSourceId())) {
                        si.setSource(borrowerLookup.get(e.getSourceId()));
                    }
                } else if (CurrencyOnHandSourceType.INCOME.equals(e.getSourceType())) {
                    if (incomeSourceLookup.containsKey(e.getSourceId())) {
                        si.setSource(incomeSourceLookup.get(e.getSourceId()));
                    }
                }
            }

            if (!e.isUsed()) {
                si.setOnHand("Yes");
            }

            currencyOnHandList.add(si);

            currencyOnHandBalance = currencyOnHandBalance.add(si.getAmount());
        }

        Collections.sort(currencyOnHandList, new DatedItemComparator());

        currencyOnHandListInitialized = true;
    }

    private void initLoansList() {

        String currentAccountId = FacesUtils.getSessionBean().getCurrentAccountId();

        Map<Long,String> borrowerLookupMap = ListUtils.getBorrowersLookup(serviceLocator);

        List<LoanSummaryItem> loanItems = new ArrayList<LoanSummaryItem>();        

        List<Loan> loans = this.serviceLocator.getLoanService().getAllLoansForAccount(currentAccountId);

        for (Loan e : loans) {
            LoanSummaryItem si = new LoanSummaryItem();
            si.setId(e.getId());
            si.setDate(e.getDate());
            si.setDateStr(DateFormatUtil.formatDate(e.getDate()));
            si.setBorrowerName(borrowerLookupMap.get(e.getBorrowerId()));
            si.setAmount(e.getAmount());

            BigDecimal balance = LoanUtils.getLoanBalance(e, serviceLocator);
            si.setBalance(balance);
            si.setDescription(e.getDescription());
            loanItems.add(si);

            loanBalance = loanBalance.add(balance);
        }


        Collections.sort(loanItems, new DatedItemComparator());

        loanList = loanItems;

        loanListInitialized = true;
    }
    
    private void initBankAccounts() {

        String currentAccountId = FacesUtils.getSessionBean().getCurrentAccountId();

        bankAccountsList =
                this.serviceLocator.getBankAccountService().getAllBankAccountsForAccount(currentAccountId);

        if (bankAccountsList.size() > 0) {

            for (BankAccount ba : bankAccountsList) {
                bankAccountBalance = bankAccountBalance.add(ba.getBalance());
            }
        }


        //Collections.sort(expenseItems, new DatedItemComparator());
        
        bankAccountsInitialized = true;
    }

    public List<BankAccount> getBankAccounts() {
        if (!bankAccountsInitialized) {
            initBankAccounts();
        }
        return bankAccountsList;
    }


    private void initCreditCards() {

        String currentAccountId = FacesUtils.getSessionBean().getCurrentAccountId();

        creditCardsList =
                this.serviceLocator.getCreditCardService().getAllCreditCardsForAccount(currentAccountId);

        if (creditCardsList.size() > 0) {

            for (CreditCard cc : creditCardsList) {
                creditCardBalance = creditCardBalance.add(cc.getBalance());
            }
        }


        //Collections.sort(expenseItems, new DatedItemComparator());

        creditCardsInitialized = true;
    }

    public List<CreditCard> getCreditCards() {
        if (!creditCardsInitialized) {
            initCreditCards();
        }
        return creditCardsList;
    }

    private void initCashBalance() {
        String currentAccountId = FacesUtils.getSessionBean().getCurrentAccountId();

        cashBalance = this.serviceLocator.getAccountService().findAccount(currentAccountId).getCashBalance();

        cashBalanceInitialized = true;
    }

        
    public String homeAction() {
        return NavigationResults.HOME;
    }

    public BigDecimal getBankAccountBalance() {
        if (!bankAccountsInitialized) {
            initBankAccounts();
        }

        return bankAccountBalance;
    }

    public List<BankAccount> getBankAccountsList() {
        if (!bankAccountsInitialized) {
            initBankAccounts();
        }
        return bankAccountsList;
    }

    public BigDecimal getCashBalance() {
        if (!cashBalanceInitialized) {
            initCashBalance();
        }
        return cashBalance;
    }

    public BigDecimal getCreditCardBalance() {
        if (!creditCardsInitialized) {
            initCreditCards();
        }
        return creditCardBalance;
    }

    public List<CreditCard> getCreditCardsList() {
        if (!creditCardsInitialized) {
            initCreditCards();
        }
        return creditCardsList;
    }

    public BigDecimal getCurrencyOnHandBalance() {
        if (!currencyOnHandListInitialized) {
            initCurrencyOnHandList();
        }
        return currencyOnHandBalance;
    }

    public List<CurrencyOnHandHistoryItem> getCurrencyOnHandList() {
        if (!currencyOnHandListInitialized) {
            initCurrencyOnHandList();
        }
        return currencyOnHandList;
    }

    public BigDecimal getLoanBalance() {
        if (!loanListInitialized) {
            initLoansList();
        }
        return loanBalance;
    }

    public List<LoanSummaryItem> getLoanList() {
        if (!loanListInitialized) {
            initLoansList();
        }
        return loanList;
    }

    public BigDecimal getTotalAvailableCash() {
        if (!currencyOnHandListInitialized) {
            initCurrencyOnHandList();
        }

        if (!bankAccountsInitialized) {
            initBankAccounts();
        }

        if (!cashBalanceInitialized) {
            initCashBalance();
        }

        return currencyOnHandBalance.add(bankAccountBalance).add(cashBalance);
    }

    public BigDecimal getTotalAvailableCashMinusTotalDebts() {
        BigDecimal totalAvailableCash = getTotalAvailableCash();

        if (!creditCardsInitialized) {
            initCreditCards();
        }

        return totalAvailableCash.subtract(creditCardBalance);
    }
}
