package command
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import event.GetExpenseDataForChartItemEvent;
	
	import model.ExpenseDataModelLocator;
	import model.ExpenseMetaDataModelLocator;
	import model.PieChartsModelLocator;
	
	import mx.collections.ArrayCollection;
	import mx.formatters.CurrencyFormatter;
	import mx.managers.PopUpManager;
	import mx.rpc.IResponder;
	
	import reports.components.DataGridDialog;
	
	public class GetExpenseDataForChartItemCommand implements ICommand, IResponder
	{
		private var getExpenseDataForChartItemEvent:GetExpenseDataForChartItemEvent;
		
		public function GetExpenseDataForChartItemCommand()
		{
		}
		
		public function execute(event:CairngormEvent):void {
			getExpenseDataForChartItemEvent = GetExpenseDataForChartItemEvent(event);
			var delegate:ReportsServiceDelegate = new ReportsServiceDelegate(this);
			delegate.getExpenseData(getExpenseDataForChartItemEvent.accountId,
				getExpenseDataForChartItemEvent.userId,
				getExpenseDataForChartItemEvent.startDate,
				getExpenseDataForChartItemEvent.endDate,
				getExpenseDataForChartItemEvent.payeeId,
				getExpenseDataForChartItemEvent.expensePurposeId,
				getExpenseDataForChartItemEvent.expenseCategoryId,
				getExpenseDataForChartItemEvent.expenseTypeId,
				getExpenseDataForChartItemEvent.paymentMethodId,
				getExpenseDataForChartItemEvent.bankAccountId,
				getExpenseDataForChartItemEvent.creditCardId,
				getExpenseDataForChartItemEvent.projectId);			
		}
		
		public function result(data:Object):void {
			var result:ArrayCollection = data.result as ArrayCollection;
			
			ExpenseDataModelLocator.getInstance().expenseData = result;
			
						
			var dataGridDialog:DataGridDialog = new DataGridDialog();
//			dataGridDialog.dataProvider = result;
			
			var total:Number = 0;
			for each (var obj:Object in result) {
				total += obj.amount as Number;
			}				
			
			var formatter:CurrencyFormatter = new CurrencyFormatter();
			
			formatter.precision = 2;
			formatter.decimalSeparatorTo = ".";
			formatter.decimalSeparatorFrom = ".";
			formatter.currencySymbol = "$";
			formatter.rounding = "nearest";
			formatter.useThousandsSeparator = true;
			formatter.alignSymbol = "left";
			formatter.useNegativeSign = true;
			
//			dataGridDialog.total = formatter.format(total);
			
			ExpenseDataModelLocator.getInstance().expenseDataTotal = formatter.format(total);
			
			PopUpManager.addPopUp(dataGridDialog, getExpenseDataForChartItemEvent.parentUI, true);
			PopUpManager.centerPopUp(dataGridDialog);
			
		}
		
		public function fault(data:Object):void {
			
		}
	}
}