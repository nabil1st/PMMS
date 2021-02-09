package event
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	public class GetPaymentMethodsEvent extends CairngormEvent
	{
		public static const GET_PAYMENT_METHODS_EVENT:String = "GET_PAYMENT_METHODS_EVENT";
		
		
		public function GetPaymentMethodsEvent()
		{
			super("GET_PAYMENT_METHODS_EVENT");						
		}
	}
}