package command
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import event.GetPayeesEvent;
	
	import model.ExpenseMetaDataModelLocator;
	import model.PieChartsModelLocator;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.IResponder;
	
	public class GetPayeesCommand implements ICommand, IResponder
	{
		private var getPayeesEvent:GetPayeesEvent;
		
		public function GetPayeesCommand()
		{
		}
		
		public function execute(event:CairngormEvent):void {
			getPayeesEvent = GetPayeesEvent(event);
			var delegate:ReportsServiceDelegate = new ReportsServiceDelegate(this);
			delegate.getPayees(getPayeesEvent.accountId);			
		}
		
		public function result(data:Object):void {
			var dat:ArrayCollection = data.result as ArrayCollection;
			
			var list:ArrayCollection = new ArrayCollection();
			list.addItem("All Payees");
			for each (var item:Object in dat) {
				list.addItem(item.description);
			}
			
			ExpenseMetaDataModelLocator.getInstance().payees = list;
			ExpenseMetaDataModelLocator.getInstance().payeesRawList = dat;
			
		}
		
		public function fault(data:Object):void {
			
		}
	}
}