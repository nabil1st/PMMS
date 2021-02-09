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
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import trackit.businessobjects.Borrower;
import trackit.businessobjects.CurrencyOnHand;
import trackit.businessobjects.CurrencyOnHandSourceType;
import trackit.businessobjects.Expense;
import trackit.businessobjects.IncomeSource;
import trackit.businessobjects.Loan;
import trackit.businessobjects.MoneyOrderFee;
import trackit.businessobjects.PaymentMethod;
import trackit.util.DateFormatUtil;
import trackit.util.FacesUtils;
import trackit.util.ListUtils;

/**
 *
 * @author Owner
 */
public class ExpenseGroupReportBean extends BaseBean {
    private List<CurrencyOnHandHistoryItem> historyItems;

    private Long groupId;
    private List<ExpenseItem> expenseList;
    private List<LoanSummaryItem> loanList;
    private List<CurrencyOnHandHistoryItem> incomeList;

    private BigDecimal expensesTotal = new BigDecimal(0);
    private BigDecimal incomesTotal = new BigDecimal(0);
    private BigDecimal loanBalanceTotal = new BigDecimal(0);

    private boolean expenseListInitialized;
    private boolean incomeListInitialized;
    private boolean loanListInitialized;


    public ExpenseGroupReportBean() {
    }

    private void initLoansList() {
        Map<Long,String> borrowerLookupMap = ListUtils.getBorrowersLookup(serviceLocator);

        List<LoanSummaryItem> loanItems = new ArrayList<LoanSummaryItem>();

        if (ListUtils.isNullSelection(groupId)) {
            loanList = loanItems;
            return;
        }

        List<Loan> loans = this.serviceLocator.getLoanService().getLoansForExpenseGroup(groupId.toString());

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

            loanBalanceTotal = loanBalanceTotal.add(balance);
        }


        Collections.sort(loanItems, new DatedItemComparator());

        loanList = loanItems;

        loanListInitialized = true;
    }
    public List<LoanSummaryItem> getLoansList() {
        if (!loanListInitialized) {
            initLoansList();
        }
        return loanList;

    }

    private void initExpenseItems() {
        List<ExpenseItem> expenseItems = new ArrayList<ExpenseItem>();

        if (ListUtils.isNullSelection(groupId)) {
            expenseList = expenseItems;
            return;
        }

        List<Expense> expenses =
                this.serviceLocator.getExpenseService().getAllExpensesForExpenseGroup(groupId.toString());

        Map<Long,String> payeeLookup = ListUtils.getPayeeLookup(serviceLocator);
        Map<Long,String> expenseTypeLookup = ListUtils.getExpenseTypeLookup(serviceLocator);


        if (expenses.size() > 0) {

            for (Expense ba : expenses) {
                ExpenseItem item = new ExpenseItem();
                item.setDateStr(DateFormatUtil.formatDate(ba.getDate()));
                item.setDate(ba.getDate());
                item.setAmount(ba.getAmount());
                item.setCheckNumber(ba.getCheckNumber());
                item.setPayee(payeeLookup.get(ba.getPayeeId()));
                item.setPaymentMethod(ba.getPaymentMethod().toString());
                item.setExpenseType(expenseTypeLookup.get(ba.getExpenseTypeId()));
                item.setDescription(ba.getDescription());
                expenseItems.add(item);

                expensesTotal = expensesTotal.add(item.getAmount());

                if (ba.getPaymentMethod().equals(PaymentMethod.MONEY_ORDER)) {
                    if (ba.getMoneyOrderId() != null && ba.getMoneyOrderId() != -1) {
                        MoneyOrderFee fee =
                                this.serviceLocator.getMoneyOrderService().
                                    findMoneyOrderFeeByMoneyOrderId(
                                    ba.getMoneyOrderId().toString());

                        if (fee != null) {
                            ExpenseItem feeItem = new ExpenseItem();
                            feeItem.setDate(ba.getDate());
                            feeItem.setDateStr(DateFormatUtil.formatDate(ba.getDate()));
                            feeItem.setAmount(fee.getFee());
                            feeItem.setDescription("Money Order Fee");
                            expenseItems.add(feeItem);
                            
                            expensesTotal = expensesTotal.add(feeItem.getAmount());
                        }
                    }
                }
            }
        }


        Collections.sort(expenseItems, new DatedItemComparator());
        expenseList = expenseItems;
        expenseListInitialized = true;
    }

    public List<ExpenseItem> getExpensesList() {
        if (!expenseListInitialized) {
            initExpenseItems();
        }
        return expenseList;        
    }

    private void initIncomeList() {
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

        historyItems = new ArrayList<CurrencyOnHandHistoryItem>();

        if (ListUtils.isNullSelection(groupId)) {
            incomeList = historyItems;
            return;
        }


        List<CurrencyOnHand> currenciesOnHand =
            this.serviceLocator.getCurrencyOnHandService().
                getCurrencyOnHandForExpenseGroup(groupId.toString());

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

            historyItems.add(si);

            incomesTotal = incomesTotal.add(si.getAmount());
        }

        Collections.sort(historyItems, new DatedItemComparator());
        incomeList =  historyItems;
        incomeListInitialized = true;
    }

    public List<CurrencyOnHandHistoryItem> getIncomesForGroup() {
        if (!incomeListInitialized) {
            initIncomeList();
        }
        return incomeList;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }


    // Lists needed to populate list UI elements

    public List<SelectItem> getGroupList() {
        return ListUtils.getExpenseGroupList(serviceLocator, true);
    }


    public void setHistoryItems(List<CurrencyOnHandHistoryItem> historyItems) {
        this.historyItems = historyItems;
    }

    public String homeAction() {
        return NavigationResults.HOME;
    }


    public void updateExpenseGroup(ValueChangeEvent  e) {
        initExpenseItems();
        initIncomeList();
        initLoansList();

        // discard current view and force a fresh one
//        FacesContext context = FacesContext.getCurrentInstance();
//        Application application = context.getApplication();
//        ViewHandler viewHandler = application.getViewHandler();
//        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
//        context.setViewRoot(viewRoot);
//        context.renderResponse();
    }

    public BigDecimal getExpensesTotal() {
        if (!expenseListInitialized) {
            initExpenseItems();
        }
        
        return expensesTotal;
    }

    public void setExpensesTotal(BigDecimal expensesTotal) {
        this.expensesTotal = expensesTotal;
    }

    public BigDecimal getIncomesTotal() {
        if (!incomeListInitialized) {
            initIncomeList();
        }
        return incomesTotal;
    }

    public void setIncomesTotal(BigDecimal incomesTotal) {
        this.incomesTotal = incomesTotal;
    }

    public BigDecimal getLoanBalanceTotal() {
        if (!loanListInitialized) {
            initLoansList();
        }
        return loanBalanceTotal;
    }

    public void setLoanBalanceTotal(BigDecimal loanBalanceTotal) {
        this.loanBalanceTotal = loanBalanceTotal;
    }

    public BigDecimal getIncomeMinusExpensesTotal() {
        if (!expenseListInitialized) {
            initExpenseItems();
        }

        if (!loanListInitialized) {
            initLoansList();
        }

        if (!incomeListInitialized) {
            initIncomeList();
        }

        return incomesTotal.subtract(expensesTotal);
    }

    public BigDecimal getIncomePlusLoanBalanceMinusExpensesTotal() {
        if (!expenseListInitialized) {
            initExpenseItems();
        }

        if (!loanListInitialized) {
            initLoansList();
        }

        if (!incomeListInitialized) {
            initIncomeList();
        }
        
        return incomesTotal.add(loanBalanceTotal).subtract(expensesTotal);
    }


}
