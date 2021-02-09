package command
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import event.GetExpensePurposeTypesEvent;
	
	import model.ExpenseMetaDataModelLocator;
	import model.PieChartsModelLocator;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.IResponder;
	
	public class GetExpensePurposeTypesCommand implements ICommand, IResponder
	{
		private var getExpensePurposeTypesEvent:GetExpensePurposeTypesEvent;
		
		public function GetExpensePurposeTypesCommand()
		{
		}
		
		public function execute(event:CairngormEvent):void {
			getExpensePurposeTypesEvent = GetExpensePurposeTypesEvent(event);
			var delegate:ReportsServiceDelegate = new ReportsServiceDelegate(this);
			delegate.getExpensePurposeTypes();			
		}
		
		public function result(data:Object):void {
			var dat:ArrayCollection = data.result as ArrayCollection;			
			
			var list:ArrayCollection = new ArrayCollection();
			list.addItem("All Expense Purposes");
			for each (var item:Object in dat) {
				list.addItem(item.description);
			}
									
			ExpenseMetaDataModelLocator.getInstance().expensePurposeTypes = list;
			ExpenseMetaDataModelLocator.getInstance().expensePurposeTypesRawList = dat;
			
		}
		
		public function fault(data:Object):void {
			
		}
	}
}