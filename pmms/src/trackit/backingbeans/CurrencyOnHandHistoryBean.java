/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import trackit.businessobjects.Borrower;
import trackit.businessobjects.CurrencyOnHand;
import trackit.businessobjects.CurrencyOnHandSourceType;
import trackit.businessobjects.CurrencyOnHandStatus;
import trackit.businessobjects.CurrencyOnHandType;
import trackit.businessobjects.ExpenseGroup;
import trackit.businessobjects.IncomeSource;
import trackit.service.impl.DataInUseException;
import trackit.util.DateFormatUtil;
import trackit.util.FacesUtils;
import trackit.util.ListUtils;

/**
 *
 * @author Owner
 */
public class CurrencyOnHandHistoryBean extends ListBean {    

    private Date toDate;
    private Date fromDate;
    private boolean showAllDates;

    private CurrencyOnHandStatus status = CurrencyOnHandStatus.ALL;
    private CurrencyOnHandType currencyType = CurrencyOnHandType.UNKNOWN;
    private CurrencyOnHandSourceType sourceType = CurrencyOnHandSourceType.UNKNOWN;
    private Long sourceId;
    private Long groupId;
    
    
    private List<CurrencyOnHandHistoryItem> list = null;
        

    public CurrencyOnHandHistoryBean() {
        toDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(toDate);
        c.add(Calendar.DAY_OF_MONTH, -30);
        fromDate = c.getTime();
    }

    public List<CurrencyOnHandHistoryItem> getHistoryItems() {
    	if (list == null) {
    		initializeList();
    	}
    	
    	return list;
    }
    
    private void initializeList() {
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
            List<IncomeSource> incomeSources = ListUtils.getIncomeSources(this.serviceLocator.getIncomeService());                    
            for (IncomeSource source : incomeSources) {
                incomeSourceLookup.put(source.getId(), source.getName());
            }
        //} else if (CurrencyOnHandSourceType.LOAN_PAYMENT.equals(sourceType)) {
            List<Borrower> borrowers = ListUtils.getBorrowers(this.serviceLocator.getBorrowerService());
                    
            for (Borrower borrower : borrowers) {
                borrowerLookup.put(borrower.getId(), borrower.getName());
            }
        //}

        list = new ArrayList<CurrencyOnHandHistoryItem>();       

        Date toDateToUse = DateFormatUtil.getNextWholeDate(toDate);
        
        if (ListUtils.NULL_CONST.equals(groupId)) {
        	groupId = null;
        }
        
        List<CurrencyOnHand> currenciesOnHand =
                this.serviceLocator.getCurrencyOnHandService().getCurrencyOnHand(
                currentAccountId,
                toDateToUse,
                fromDate,
                showAllDates,
                status,
                currencyType,
                sourceType,
                sourceId,
                groupId);

        for (CurrencyOnHand e : currenciesOnHand) {
            CurrencyOnHandHistoryItem si = new CurrencyOnHandHistoryItem();
            si.setId(e.getId());
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
            
            si.setCurrencyOnHandSourceType(e.getSourceType());            

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
            
            list.add(si);
        }

        Collections.sort(list, new DatedItemComparator());        
    }

    public int getCurrencyType() {
        if (currencyType == null) {
            return CurrencyOnHandType.UNKNOWN.toInt();
        }

        return currencyType.toInt();
    }

    public void setCurrencyType(int type) {
        this.currencyType = CurrencyOnHandType.fromInt(type);
    }

    
    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public boolean isShowAllDates() {
        return showAllDates;
    }

    public void setShowAllDates(boolean showAllDates) {
        this.showAllDates = showAllDates;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public int getSourceType() {
        if (this.sourceType == null) {
            return CurrencyOnHandSourceType.UNKNOWN.toInt();
        }

        return sourceType.toInt();


    }

    public void setSourceType(int sourceTypeId) {
        this.sourceType = CurrencyOnHandSourceType.fromInt(sourceTypeId);
    }

    public int getStatus() {
        if (status == null) {
            return CurrencyOnHandStatus.ALL.toInt();
        }


        return status.toInt();
    }

    public void setStatus(int statusId) {
        this.status = CurrencyOnHandStatus.fromInt(statusId);
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    // Lists needed to populate list UI elements

    public List<SelectItem> getGroupList() {
        return ListUtils.getExpenseGroupList(serviceLocator, true);
    }

    public List<SelectItem> getCurrencyOnHandStatusList() {
        return ListUtils.getCurrencyOnHandStatusList();
    }

    public List<SelectItem> getCurrencyOnHandTypeList() {
        return ListUtils.getCurrencyOnHandTypeList();
    }

    public List<SelectItem> getCurrencyOnHandSourceTypeList() {
        return ListUtils.getCurrencyOnHandSourceTypeList();
    }

    public List<SelectItem> getCurrencyOnHandSourceList() {
        if (sourceType != null && sourceType.toInt() == CurrencyOnHandSourceType.INCOME.toInt()) {
            return ListUtils.getIncomeSourceList(serviceLocator, true);
        } else if (sourceType != null && sourceType.toInt() == CurrencyOnHandSourceType.LOAN_PAYMENT.toInt()) {
            return ListUtils.getBorrowersList(serviceLocator, true);
        } else {
            return new ArrayList<SelectItem>();
        }
    }

    public String homeAction() {
        return NavigationResults.HOME;
    }

    public String incomeSourcesAction() {
        return NavigationResults.SHOW_INCOME_SOURCES;
    }

    public String newIncomeAction() {
        return NavigationResults.CREATE_NEW_INCOME_REQUESTED;
    }

    public String newMoneyOrderAction() {
        return NavigationResults.CREATE_NEW_MONEY_ORDER_REQUESTED;
    }

    public boolean isCurrencyOnHandSourceListEnabled() {
        return sourceType.toInt() == CurrencyOnHandSourceType.INCOME.toInt() ||
                sourceType.toInt() == CurrencyOnHandSourceType.LOAN_PAYMENT.toInt();
    }

    public void updateSourceType(ValueChangeEvent  e) {
        sourceId = null;
    }

	@Override
	public void deleteItem(IListItem item) throws DataInUseException {		
		CurrencyOnHandHistoryItem it = (CurrencyOnHandHistoryItem) item;
		if (it.getCurrencyOnHandSourceType().equals(CurrencyOnHandSourceType.MONEY_ORDER_PURCHASE)) {
			this.serviceLocator.getMoneyOrderTransactionalService().setServiceLocator(this.serviceLocator);
			this.serviceLocator.getMoneyOrderTransactionalService().deleteMoneyOrder(it.getId());
		} else if (it.getCurrencyOnHandSourceType().equals(CurrencyOnHandSourceType.INCOME) ||
				it.getCurrencyOnHandSourceType().equals(CurrencyOnHandSourceType.LOAN_PAYMENT)){
			this.serviceLocator.getLoanTransactionalService().setServiceLocator(this.serviceLocator);
			this.serviceLocator.getLoanTransactionalService().deleteLoanPayment(it.getId());
		} 
	}

	@Override
	public void editItem(IListItem item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getEditNavigationId() {
		// Depends on what's being edited
		return "";
	}

	@Override
	public String getListNavigaionId() {
		return NavigationResults.SHOW_CURRENCY_ON_HAND_SUMMARY;
	}

	@Override
	public void reset() {
		list = null;		
		
	}
	
	public String refresh() {
		reset();
		return "SHOW_CURRENCY_ON_HAND_SUMMARY";
	}

}
