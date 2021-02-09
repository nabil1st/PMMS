package command
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import event.GetExpenseTypesForCategoryEvent;
	
	import model.ExpenseMetaDataModelLocator;
	import model.PieChartsModelLocator;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.IResponder;
	
	public class GetExpenseTypesForCategoryCommand implements ICommand, IResponder
	{
		private var getExpenseTypesForCategoryEvent:GetExpenseTypesForCategoryEvent;
		
		public function GetExpenseTypesForCategoryCommand()
		{
		}
		
		public function execute(event:CairngormEvent):void {
			getExpenseTypesForCategoryEvent = GetExpenseTypesForCategoryEvent(event);
			var delegate:ReportsServiceDelegate = new ReportsServiceDelegate(this);
			delegate.getExpenseTypesForCategory(getExpenseTypesForCategoryEvent.categoryId);			
		}
		
		public function result(data:Object):void {
			var dat:ArrayCollection = data.result as ArrayCollection;
			
			var list:ArrayCollection = new ArrayCollection();
			list.addItem("All Expense Types");
			for each (var item:Object in dat) {
				list.addItem(item.description);
			}
						
			ExpenseMetaDataModelLocator.getInstance().expenseTypesForCategory.removeAll();
			ExpenseMetaDataModelLocator.getInstance().expenseTypesForCategory.addAll(list);
			ExpenseMetaDataModelLocator.getInstance().expenseTypesForCategoryRawList = dat;
			
		}
		
		public function fault(data:Object):void {
			
		}
	}
}