package trackit.webservices;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.jws.WebParam;

import trackit.backingbeans.DatedAmount;
import trackit.backingbeans.DescriptionItem;
import trackit.backingbeans.ExpenseItem;

@WebService(targetNamespace="http://webservices.trackit")
public interface ExpensesReportsService {
	public List<ExpenseCategoryStats> getExpenseCategoryStats(@WebParam(name = "accountId") String accountId, 
			   @WebParam(name = "startDate") Date startDate,
			   @WebParam(name = "endDate") Date endDate);
	
	public List<ExpenseCategoryStats> getExpenseSubCategoryStats(@WebParam(name = "accountId") String accountId, 
			   @WebParam(name = "startDate") Date startDate,
			   @WebParam(name = "endDate") Date endDate);
	
	public List<ExpenseCategoryStats> getExpensePayeeStats(@WebParam(name = "accountId") String accountId, 
			   @WebParam(name = "startDate") Date startDate,
			   @WebParam(name = "endDate") Date endDate);
	
	public List<ExpenseItem> getExpenseData(@WebParam(name = "accountId") String accountId,
				@WebParam(name = "userId") String userId,
			   @WebParam(name = "startDate") Date startDate,
			   @WebParam(name = "endDate") Date endDate,
			   @WebParam(name = "payeeId") String payeeId,
			   @WebParam(name = "expensePurposeId") String expensePurposeId,
			   @WebParam(name = "expenseCategoryId") String expenseCategoryId,
			   @WebParam(name = "expenseTypeId") String expenseTypeId,
			   @WebParam(name = "paymentMethodId") String paymentMethodId,
			   @WebParam(name = "bankAccountId") String bankAccountId,
			   @WebParam(name = "creditCardId") String creditCardId,
			   @WebParam(name = "projectId") String projectId);
	
	public List<DescriptionItem> getUsersForAccount(@WebParam(name="accountId") String accountId);
	
	public List<DescriptionItem> getPayeesForAccount(@WebParam(name="accountId") String accountId);
	
	public List<DescriptionItem> getExpenseGroupsForAccount(@WebParam(name="accountId") String accountId);
	
	public List<DescriptionItem> getBankAccountsForAccount(@WebParam(name="accountId") String accountId);
	
	public List<DescriptionItem> getCreditCardsForAccount(@WebParam(name="accountId") String accountId);
	
	public List<DescriptionItem> getExpenseCategoriesForAccount(@WebParam(name="accountId") String accountId);
	
	public List<DescriptionItem> getExpenseTypesForCategory(@WebParam(name="categoryId") String categoryId);
	
	public List<DescriptionItem> getExpenseSubTypesForAccount(@WebParam(name="accountId") String accountId);
	
	public List<DescriptionItem> getPaymentMethods();
	
	public List<DescriptionItem> getExpensePurposeTypes();
	
	public List<DatedAmount> getExpenseDataForCharting(@WebParam(name = "accountId") String accountId,
			@WebParam(name = "userId") String userId,
			   @WebParam(name = "startDate") Date startDate,
			   @WebParam(name = "endDate") Date endDate,
			   @WebParam(name = "payeeId") String payeeId,
			   @WebParam(name = "expensePurposeId") String expensePurposeId,
			   @WebParam(name = "expenseCategoryId") String expenseCategoryId,
			   @WebParam(name = "expenseTypeId") String expenseTypeId,
			   @WebParam(name = "paymentMethodId") String paymentMethodId,
			   @WebParam(name = "bankAccountId") String bankAccountId,
			   @WebParam(name = "creditCardId") String creditCardId,
			   @WebParam(name = "projectId") String projectId,
			   @WebParam(name = "period") int period);
	 
}
