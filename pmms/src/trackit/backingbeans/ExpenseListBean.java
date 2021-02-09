package trackit.backingbeans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import trackit.businessobjects.Expense;
import trackit.businessobjects.PaymentMethod;
import trackit.service.impl.DataInUseException;
import trackit.util.DateFormatUtil;
import trackit.util.FacesUtils;
import trackit.util.ListUtils;

/**
 * 
 * @author Nabil
 */
public class ExpenseListBean extends ListBean {
	private Date fromDate;
	private Date toDate;
	private List<ExpenseItem> expenseItems;
	private ExpenseItem expenseToDelete;
	private boolean scrollBarVisible = true;

	public ExpenseListBean() {
		toDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(toDate);
		c.add(Calendar.DAY_OF_MONTH, -30);
		fromDate = c.getTime();
	}

	public List<ExpenseItem> getExpensesList() {
		if (expenseItems == null) {
			initializeExpenseItemsList();
			updateScrollBarVisibility();
		}

		return expenseItems;
	}

	private void initializeExpenseItemsList() {
		List<Expense> expenses = this.serviceLocator.getExpenseService()
				.getExpensesForAccount(
						FacesUtils.getSessionBean().getCurrentAccountId(),
						fromDate, toDate);
		expenseItems = new ArrayList<ExpenseItem>();

		if (expenses.size() > 0) {

			Map<Long, String> expenseGroupLookup = ListUtils
					.getExpenseGroupLookup(serviceLocator);
			Map<Long, String> payeeLookup = ListUtils
					.getPayeeLookup(serviceLocator);

			Map<Long, String> expenseTypeLookup = ListUtils
					.getExpenseTypeLookup(serviceLocator);

			Map<Long, String> userNameLookup = ListUtils
					.getUserNameLookup(serviceLocator);

			for (Expense ba : expenses) {

				ExpenseItem item = new ExpenseItem();

				item.setId(ba.getId());
				item.setDateStr(DateFormatUtil.formatDate(ba.getDate()));
				item.setDate(ba.getDate());
				item.setAmount(ba.getAmount());
				item.setCheckNumber(ba.getCheckNumber());
				item.setDescription(ba.getDescription());
				item.setExpenseType(expenseTypeLookup
						.get(ba.getExpenseTypeId()));
				item.setGroup(expenseGroupLookup.get(ba.getExpenseGroupId()));
				item.setPayee(payeeLookup.get(ba.getPayeeId()));
				item.setPaymentMethod(ba.getPaymentMethod().toString());
				item.setItemized(ba.getExpenseItems() != null
						&& ba.getExpenseItems().size() > 0);
				item.setUserName(userNameLookup.get(ba.getUserId()));

				expenseItems.add(item);

			}
		}

		sortExpenseItems(expenseItems);
	}

	public void updateScrollBarVisibility() {
		this.scrollBarVisible = expenseItems.size() > 10;
	}

	private void sortExpenseItems(List<ExpenseItem> expenseItems) {
		Collections.sort(expenseItems, new DatedItemComparator());
	}

	public String newAction() {
		ExpenseBean eb = (ExpenseBean) FacesUtils.getManagedBean("expenseBean");
		if (eb != null) {
			eb.setServiceLocator(getServiceLocator());
			eb.clearAllFields();
		}
		return NavigationResults.CREATE_EXPENSE_REQUESTED;
	}

	public String itemizeAction() {
		return NavigationResults.ITEMIZE_EXPENSE_REQUESTED;
	}

	public String homeAction() {
		return NavigationResults.HOME;
	}

	public String refreshAction() {
		expenseItems = null;
		return "show_expenses";
	}

	public String expenseGroupsAction() {
		return NavigationResults.EXPENSE_GROUPS;
	}

	public String expenseTypesAction() {
		// return NavigationResults.SHOW_MANAGE_EXPENSE_CATEGORIES;
		FacesUtils.getSessionBean().setFollowOnAction(
				NavigationResults.SHOW_EXPENSES);
		return NavigationResults.CREATE_EXPENSE_TYPE_REQUESTED;
	}

	public String payeesAction() {
		return NavigationResults.PAYEES;
	}

	public String updateAction() {
		return "";
	}

	public String deleteAction() {
		return "";
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

//	public void deleteAction(ActionEvent evt) {
//		FacesContext ctx = FacesContext.getCurrentInstance();
//		String idstr = (String) ctx.getExternalContext()
//				.getRequestParameterMap().get("modelRow");
//		// int id = Integer.parseInt(idstr);
//		this.serviceLocator.getExpenseTransactionalService().setServiceLocator(
//				serviceLocator);
//		try {
//			this.serviceLocator.getExpenseTransactionalService().deleteExpense(
//					idstr);
//		} catch (DataInUseException e) {
//			FacesUtils.addErrorMessage(e.getMessage());
//		}
//
//	}

	public String deleteIt() {
		if (expenseToDelete != null) {
			this.serviceLocator.getExpenseTransactionalService()
					.setServiceLocator(serviceLocator);
			try {
				this.serviceLocator.getExpenseTransactionalService()
						.deleteExpense(String.valueOf(expenseToDelete.getId()));
				
//				if (expenseToDelete.getPaymentMethod().equals(PaymentMethod.MONEY_ORDER) || 
//					expenseToDelete.getPaymentMethod().equals(PaymentMethod.CASHIERS_CHECK)) {
					CurrencyOnHandHistoryBean eb = (CurrencyOnHandHistoryBean) FacesUtils
						.getManagedBean("currencyOnHandHistoryBean");
					if (eb != null) {
						eb.setServiceLocator(this.getServiceLocator());
						eb.reset();
					}
//				}
				
			} catch (DataInUseException e) {
				FacesUtils.addErrorMessage(e.getMessage());
			}
			expenseToDelete = null;
		}

		reset();

		return "show_expenses";

	}

	public String editIt() {
		if (expenseToDelete != null) {
			Expense expenseToEdit = this.serviceLocator.getExpenseService()
					.getExpense(String.valueOf(expenseToDelete.getId()));

			if (expenseToEdit.getExpenseItems().size() > 0) {
				ItemizedExpenseBean eb = (ItemizedExpenseBean) FacesUtils
						.getManagedBean("itemizedExpenseBean");
				if (eb != null) {
					eb.setServiceLocator(this.getServiceLocator());
					eb.edit(expenseToEdit);
				}

				expenseToDelete = null;
				return NavigationResults.ITEMIZE_EXPENSE_REQUESTED;
			} else {

				ExpenseBean eb = (ExpenseBean) FacesUtils
						.getManagedBean("expenseBean");
				if (eb != null) {
					eb.setServiceLocator(this.getServiceLocator());
					eb.edit(expenseToEdit);
				}

				expenseToDelete = null;
				return NavigationResults.NEW_EXPENSE;
			}
		}

		return "show_expenses";

	}

	public String refund() {
		if (expenseToDelete != null) {
			Expense expenseToEdit = this.serviceLocator.getExpenseService()
					.getExpense(String.valueOf(expenseToDelete.getId()));
			
			if (expenseToEdit.getPaymentMethod().equals(PaymentMethod.CHECK) ||
					expenseToEdit.getPaymentMethod().equals(PaymentMethod.CASHIERS_CHECK) ||
					expenseToEdit.getPaymentMethod().equals(PaymentMethod.MONEY_ORDER)) {
				FacesUtils.addErrorMessage("Cannot refund using selected payment method.");
				return "show_expenses";
			}
				
			
			Expense refundBean = new Expense();
			refundBean.setBankAccountId(expenseToEdit.getBankAccountId());
			refundBean.setCreditCardId(expenseToEdit.getCreditCardId());
			refundBean.setExpenseGroupId(expenseToEdit.getExpenseGroupId());
			refundBean.setPayeeId(expenseToEdit.getPayeeId());
			refundBean.setPaymentMethod(expenseToEdit.getPaymentMethod());
			refundBean.setUserId(expenseToEdit.getUserId());

			if (expenseToEdit.getExpenseItems().size() > 0) {
				//
			} else {				
				refundBean.setExpenseTypeId(expenseToEdit.getExpenseTypeId());
				refundBean.setExpensePurpose(expenseToEdit.getExpensePurpose());
			}
			
			ExpenseBean eb = (ExpenseBean) FacesUtils
					.getManagedBean("expenseBean");
			if (eb != null) {
				eb.setServiceLocator(this.getServiceLocator());
				eb.refund(refundBean);
			}
		
			expenseToDelete = null;
			return NavigationResults.NEW_EXPENSE;
		}

		return "show_expenses";
	}

	public ExpenseItem getExpenseToDelete() {
		return expenseToDelete;
	}

	public void setExpenseToDelete(ExpenseItem expenseToDelete) {
		this.expenseToDelete = expenseToDelete;
	}

	public void reset() {
		expenseItems = null;
		FacesUtils.getSessionBean().resetMoneyOrderInfo();		
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

}
