package command
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import event.GetUsersEvent;
	
	import model.ExpenseMetaDataModelLocator;
	import model.PieChartsModelLocator;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.IResponder;
	
	public class GetUsersCommand implements ICommand, IResponder
	{
		private var getUsersEvent:GetUsersEvent;
		
		public function GetUsersCommand()
		{
		}
		
		public function execute(event:CairngormEvent):void {
			getUsersEvent = GetUsersEvent(event);
			var delegate:ReportsServiceDelegate = new ReportsServiceDelegate(this);
			delegate.getUsers(getUsersEvent.accountId);			
		}
		
		public function result(data:Object):void {
			var dat:ArrayCollection = data.result as ArrayCollection;
						
			var list:ArrayCollection = new ArrayCollection();
			list.addItem("All Users");
			for each (var item:Object in dat) {
				list.addItem(item.description);
			}
			
			ExpenseMetaDataModelLocator.getInstance().users = list;
			ExpenseMetaDataModelLocator.getInstance().usersRawList = dat;
			
		}
		
		public function fault(data:Object):void {
			
		}
	}
}