package command
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import event.GetCreditCardsEvent;
	
	import model.ExpenseMetaDataModelLocator;
	import model.PieChartsModelLocator;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.IResponder;
	
	public class GetCreditCardsCommand implements ICommand, IResponder
	{
		private var getCreditCardsEvent:GetCreditCardsEvent;
		
		public function GetCreditCardsCommand()
		{
		}
		
		public function execute(event:CairngormEvent):void {
			getCreditCardsEvent = GetCreditCardsEvent(event);
			var delegate:ReportsServiceDelegate = new ReportsServiceDelegate(this);
			delegate.getCreditCards(getCreditCardsEvent.accountId);			
		}
		
		public function result(data:Object):void {
			var dat:ArrayCollection = data.result as ArrayCollection;
			
			var list:ArrayCollection = new ArrayCollection();
			list.addItem("All Credit Cards");
			for each (var item:Object in dat) {
				list.addItem(item.description);
			}
					
			
			ExpenseMetaDataModelLocator.getInstance().creditCards = list;
			ExpenseMetaDataModelLocator.getInstance().creditCardsRawList = dat;
			
		}
		
		public function fault(data:Object):void {
			
		}
	}
}