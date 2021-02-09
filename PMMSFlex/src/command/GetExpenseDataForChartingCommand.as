package command
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import event.GetExpenseDataForChartingEvent;		
	import model.ExpenseDataModelLocator;
	import model.PieChartsModelLocator;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.IResponder;
	
	public class GetExpenseDataForChartingCommand implements ICommand, IResponder
	{
		private var getExpenseDataForChartingEvent:GetExpenseDataForChartingEvent;
		
		public function GetExpenseDataForChartingCommand()
		{
		}
		
		public function execute(event:CairngormEvent):void {
			getExpenseDataForChartingEvent = GetExpenseDataForChartingEvent(event);
			var delegate:ReportsServiceDelegate = new ReportsServiceDelegate(this);
			delegate.getExpenseDataForCharting(getExpenseDataForChartingEvent.accountId,
				getExpenseDataForChartingEvent.userId,
				getExpenseDataForChartingEvent.startDate,
				getExpenseDataForChartingEvent.endDate,
				getExpenseDataForChartingEvent.payeeId,
				getExpenseDataForChartingEvent.expensePurposeId,
				getExpenseDataForChartingEvent.expenseCategoryId,
				getExpenseDataForChartingEvent.expenseTypeId,
				getExpenseDataForChartingEvent.paymentMethodId,
				getExpenseDataForChartingEvent.bankAccountId,
				getExpenseDataForChartingEvent.creditCardId,
				getExpenseDataForChartingEvent.projectId,
				getExpenseDataForChartingEvent.period);			
		}
		
		public function result(data:Object):void {
			var dat:ArrayCollection = data.result as ArrayCollection;
			ExpenseDataModelLocator.getInstance().expenseDataForChartingLength = dat.length;
			ExpenseDataModelLocator.getInstance().expenseDataForCharting = dat;
			
			
		}
		
		public function fault(data:Object):void {
			
		}
	}
}