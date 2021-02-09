package command
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import event.GetExpenseGroupsEvent;
	
	import model.ExpenseMetaDataModelLocator;
	import model.PieChartsModelLocator;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.IResponder;
	
	public class GetExpenseGroupsCommand implements ICommand, IResponder
	{
		private var getExpenseGroupsEvent:GetExpenseGroupsEvent;
		
		public function GetExpenseGroupsCommand()
		{
		}
		
		public function execute(event:CairngormEvent):void {
			getExpenseGroupsEvent = GetExpenseGroupsEvent(event);
			var delegate:ReportsServiceDelegate = new ReportsServiceDelegate(this);
			delegate.getExpenseGroups(getExpenseGroupsEvent.accountId);			
		}
		
		public function result(data:Object):void {
			var dat:ArrayCollection = data.result as ArrayCollection;
			
			var list:ArrayCollection = new ArrayCollection();
			list.addItem("All Projects");
			for each (var item:Object in dat) {
				list.addItem(item.description);
			}
						
			ExpenseMetaDataModelLocator.getInstance().expenseGroups = list;
			ExpenseMetaDataModelLocator.getInstance().expenseGroupsRawList = dat;
			
		}
		
		public function fault(data:Object):void {
			
		}
	}
}