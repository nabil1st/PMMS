package command
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import event.GetPaymentMethodsEvent;
	
	import model.ExpenseMetaDataModelLocator;
	import model.PieChartsModelLocator;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.IResponder;
	
	public class GetPaymentMethodsCommand implements ICommand, IResponder
	{
		private var getPaymentMethodsEvent:GetPaymentMethodsEvent;
		
		public function GetPaymentMethodsCommand()
		{
		}
		
		public function execute(event:CairngormEvent):void {
			getPaymentMethodsEvent = GetPaymentMethodsEvent(event);
			var delegate:ReportsServiceDelegate = new ReportsServiceDelegate(this);
			delegate.getPaymentMethods();			
		}
		
		public function result(data:Object):void {
			var dat:ArrayCollection = data.result as ArrayCollection;
			
			var list:ArrayCollection = new ArrayCollection();
			list.addItem("All Payment Methods");
			for each (var item:Object in dat) {
				list.addItem(item.description);
			}
			
			ExpenseMetaDataModelLocator.getInstance().paymentMethods = list;
			ExpenseMetaDataModelLocator.getInstance().paymentMethodsRawList = dat;
			
		}
		
		public function fault(data:Object):void {
			
		}
	}
}