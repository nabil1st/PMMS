package command
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import event.GetExpenseDataEvent;
	
	import model.ExpenseDataModelLocator;
	import model.ExpenseItem;
	import model.ExpenseMetaDataModelLocator;
	import model.PieChartsModelLocator;
	
	import mx.collections.ArrayCollection;
	import mx.formatters.CurrencyFormatter;
	import mx.rpc.IResponder;
	
	public class GetExpenseDataCommand implements ICommand, IResponder
	{
		private var getExpenseDataEvent:GetExpenseDataEvent;
		
		public function GetExpenseDataCommand()
		{
		}
		
		public function execute(event:CairngormEvent):void {
			getExpenseDataEvent = GetExpenseDataEvent(event);
			var delegate:ReportsServiceDelegate = new ReportsServiceDelegate(this);
			delegate.getExpenseData(getExpenseDataEvent.accountId,
				getExpenseDataEvent.userId,
				getExpenseDataEvent.startDate,
				getExpenseDataEvent.endDate,
				getExpenseDataEvent.payeeId,
				getExpenseDataEvent.expensePurposeId,
				getExpenseDataEvent.expenseCategoryId,
				getExpenseDataEvent.expenseTypeId,
				getExpenseDataEvent.paymentMethodId,
				getExpenseDataEvent.bankAccountId,
				getExpenseDataEvent.creditCardId,
				getExpenseDataEvent.projectId);			
		}
		
		public function result(data:Object):void {
			var result:ArrayCollection = data.result as ArrayCollection;
						
			var total:Number = 0;
			for each (var obj:Object in result) {
				var expenseItem:ExpenseItem = ExpenseItem(obj);
				total += expenseItem.amount;
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
			
			var totalStr:String = formatter.format(total);
			
			ExpenseDataModelLocator.getInstance().expenseData = result;
			ExpenseDataModelLocator.getInstance().expenseDataTotal = totalStr;
			
		}
		
		public function fault(data:Object):void {
			
		}
	}
}