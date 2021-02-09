package command
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import event.GetExpenseSubTypesEvent;
	
	import model.ExpenseMetaDataModelLocator;
	import model.PieChartsModelLocator;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.IResponder;
	
	public class GetExpenseSubTypesCommand implements ICommand, IResponder
	{
		private var getExpenseSubTypesEvent:GetExpenseSubTypesEvent;
		
		public function GetExpenseSubTypesCommand()
		{
		}
		
		public function execute(event:CairngormEvent):void {
			getExpenseSubTypesEvent = GetExpenseSubTypesEvent(event);
			var delegate:ReportsServiceDelegate = new ReportsServiceDelegate(this);
			delegate.getExpenseSubTypes(getExpenseSubTypesEvent.accountId);			
		}
		
		public function result(data:Object):void {
			var dat:ArrayCollection = data.result as ArrayCollection;
			ExpenseMetaDataModelLocator.getInstance().expenseSubTypes = dat;
			
		}
		
		public function fault(data:Object):void {
			
		}
	}
}