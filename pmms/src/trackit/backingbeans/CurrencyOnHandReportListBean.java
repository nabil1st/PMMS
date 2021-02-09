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
import trackit.businessobjects.Borrower;
import trackit.businessobjects.CurrencyOnHand;
import trackit.businessobjects.CurrencyOnHandSourceType;
import trackit.businessobjects.ExpenseGroup;
import trackit.businessobjects.IncomeSource;
import trackit.util.DateFormatUtil;
import trackit.util.FacesUtils;

/**
 *
 * @author Owner
 */
public class CurrencyOnHandReportListBean extends ListBean {
    private List<CurrencyOnHandHistoryItem> historyItems;
    private BigDecimal total = new BigDecimal(0);

    public CurrencyOnHandReportListBean() {        
    }

    public List<CurrencyOnHandHistoryItem> getHistoryItems() {
    	if (historyItems == null) {
    		initializeData();
    	}
        return historyItems;
    }
    
    public BigDecimal getTotal() {
    	if (historyItems == null) {
    		initializeData();
    	}
    	
    	return total;
    }
    
    private void initializeData() {
    	String currentAccountId = FacesUtils.getSessionBean().getCurrentAccountId();

        Map<Long,String> groupLookup = new HashMap<Long,String>();
        List<ExpenseGroup> groups = 
                this.serviceLocator.getExpenseGroupService().getAllExpenseGroupsForAccount(currentAccountId);

        for (ExpenseGroup g : groups) {
            groupLookup.put(g.getId(), g.getDescription());
        }

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
        
        List<CurrencyOnHand> currenciesOnHand =
                this.serviceLocator.getCurrencyOnHandService().getAllUnusedCurrenciesOnHandForAccount(currentAccountId);                

        for (CurrencyOnHand e : currenciesOnHand) {
            CurrencyOnHandHistoryItem si = new CurrencyOnHandHistoryItem();
            si.setDate(e.getDate());
            si.setDateStr(DateFormatUtil.formatDate(e.getDate()));
            si.setAmount(e.getAmount());
            si.setDescription("");
            
            if (e.getType() != null) {
                si.setCurrencyType(e.getType().toString());
            }

            if (e.getGroupId() != null) {
                si.setGroup(groupLookup.get(e.getGroupId()));
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
            
            total = total.add(si.getAmount());
            
            historyItems.add(si);
        }

        Collections.sort(historyItems, new DatedItemComparator());
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
