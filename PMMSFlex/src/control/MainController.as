package control
{
	import com.adobe.cairngorm.control.FrontController;
	
	import command.GetBankAccountsCommand;
	import command.GetCreditCardsCommand;
	import command.GetExpenseCategoriesCommand;
	import command.GetExpenseCategoryStatsCommand;
	import command.GetExpenseDataCommand;
	import command.GetExpenseDataForChartItemCommand;
	import command.GetExpenseDataForChartingCommand;
	import command.GetExpenseGroupsCommand;
	import command.GetExpensePayeeStatsCommand;
	import command.GetExpensePurposeTypesCommand;
	import command.GetExpenseSubCategoryStatsCommand;
	import command.GetExpenseSubTypesCommand;
	import command.GetExpenseTypesForCategoryCommand;
	import command.GetPayeesCommand;
	import command.GetPaymentMethodsCommand;
	import command.GetUsersCommand;
	
	import event.GetBankAccountsEvent;
	import event.GetCreditCardsEvent;
	import event.GetExpenseCategoriesEvent;
	import event.GetExpenseCategoryStatsEvent;
	import event.GetExpenseDataEvent;
	import event.GetExpenseDataForChartItemEvent;
	import event.GetExpenseDataForChartingEvent;
	import event.GetExpenseGroupsEvent;
	import event.GetExpensePayeeStatsEvent;
	import event.GetExpensePurposeTypesEvent;
	import event.GetExpenseSubCategoryStatsEvent;
	import event.GetExpenseSubTypesEvent;
	import event.GetExpenseTypesForCategoryEvent;
	import event.GetPayeesEvent;
	import event.GetPaymentMethodsEvent;
	import event.GetUsersEvent;

	public class MainController extends FrontController
	{
		public function MainController()
		{
			super();
			addCommand(GetExpenseCategoryStatsEvent.GET_EXPENSE_CATEGORY_STATS_EVENT, GetExpenseCategoryStatsCommand);
			addCommand(GetExpenseSubCategoryStatsEvent.GET_EXPENSE_SUB_CATEGORY_STATS_EVENT, GetExpenseSubCategoryStatsCommand);
			addCommand(GetExpensePayeeStatsEvent.GET_EXPENSE_PAYEE_STATS_EVENT, GetExpensePayeeStatsCommand);
			addCommand(GetExpenseCategoriesEvent.GET_EXPENSE_CATEGORIES_EVENT, GetExpenseCategoriesCommand);
			addCommand(GetExpenseDataEvent.GET_EXPENSE_DATA_EVENT, GetExpenseDataCommand);
			addCommand(GetExpenseDataForChartingEvent.GET_EXPENSE_DATA_FOR_CHARTING_EVENT, GetExpenseDataForChartingCommand);
			addCommand(GetExpenseGroupsEvent.GET_EXPENSE_GROUPS_EVENT, GetExpenseGroupsCommand);
			addCommand(GetExpensePurposeTypesEvent.GET_EXPENSE_PURPOSE_TYPES_EVENT, GetExpensePurposeTypesCommand);
			addCommand(GetExpenseSubTypesEvent.GET_EXPENSE_SUBTYPES_EVENT, GetExpenseSubTypesCommand);
			addCommand(GetExpenseTypesForCategoryEvent.GET_EXPENSE_TYPES_FOR_CATEGORY_EVENT, GetExpenseTypesForCategoryCommand);
			addCommand(GetUsersEvent.GET_USERS_EVENT, GetUsersCommand);
			addCommand(GetBankAccountsEvent.GET_BANK_ACCOUNTS_EVENT, GetBankAccountsCommand);
			addCommand(GetCreditCardsEvent.GET_CREDIT_CARDS_EVENT, GetCreditCardsCommand);
			addCommand(GetPayeesEvent.GET_PAYEES_EVENT, GetPayeesCommand);
			addCommand(GetPaymentMethodsEvent.GET_PAYMENT_METHODS_EVENT, GetPaymentMethodsCommand);
			addCommand(GetExpenseDataForChartItemEvent.GET_EXPENSE_DATA_FOR_CHART_ITEM_EVENT, GetExpenseDataForChartItemCommand);
		}
	}
}