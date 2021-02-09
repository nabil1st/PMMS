package command
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import event.GetExpenseCategoriesEvent;
	
	import model.ExpenseMetaDataModelLocator;
	import model.PieChartsModelLocator;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.IResponder;
	
	public class GetExpenseCategoriesCommand implements ICommand, IResponder
	{
		private var getExpenseCategoriesEvent:GetExpenseCategoriesEvent;
		
		public function GetExpenseCategoriesCommand()
		{
		}
		
		public function execute(event:CairngormEvent):void {
			getExpenseCategoriesEvent = GetExpenseCategoriesEvent(event);
			var delegate:ReportsServiceDelegate = new ReportsServiceDelegate(this);
			delegate.getExpenseCategories(getExpenseCategoriesEvent.accountId);			
		}
		
		public function result(data:Object):void {
			var dat:ArrayCollection = data.result as ArrayCollection;
			
			var list:ArrayCollection = new ArrayCollection();
			list.addItem("All Expense Categories");
			for each (var item:Object in dat) {
				list.addItem(item.description);
			}
			
			ExpenseMetaDataModelLocator.getInstance().expenseCategories = list;
			ExpenseMetaDataModelLocator.getInstance().expenseCategoriesRawList = dat;
			
		}
		
		public function fault(data:Object):void {
			
		}
	}
}