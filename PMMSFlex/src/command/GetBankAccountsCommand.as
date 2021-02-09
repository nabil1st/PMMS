package command
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import event.GetBankAccountsEvent;
	
	import model.ExpenseMetaDataModelLocator;
	import model.PieChartsModelLocator;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.IResponder;
	
	public class GetBankAccountsCommand implements ICommand, IResponder
	{
		private var getBankAccountsEvent:GetBankAccountsEvent;
		
		public function GetBankAccountsCommand()
		{
		}
		
		public function execute(event:CairngormEvent):void {
			getBankAccountsEvent = GetBankAccountsEvent(event);
			var delegate:ReportsServiceDelegate = new ReportsServiceDelegate(this);
			delegate.getBankAccounts(getBankAccountsEvent.accountId);			
		}
		
		public function result(data:Object):void {
			var dat:ArrayCollection = data.result as ArrayCollection;
			
			var list:ArrayCollection = new ArrayCollection();
			list.addItem("All Bank Accounts");
			for each (var item:Object in dat) {
				list.addItem(item.description);
			}
			
			ExpenseMetaDataModelLocator.getInstance().bankAccounts = list;
			ExpenseMetaDataModelLocator.getInstance().bankAccountsRawList = dat;
			
		}
		
		public function fault(data:Object):void {
			
		}
	}
}