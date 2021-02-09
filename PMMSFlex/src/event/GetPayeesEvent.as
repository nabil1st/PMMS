package event
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	public class GetPayeesEvent extends CairngormEvent
	{
		public static const GET_PAYEES_EVENT:String = "GET_PAYEES_EVENT";
		
		public var accountId:String;
		
		public function GetPayeesEvent(accountId:String)
		{
			super("GET_PAYEES_EVENT");
			this.accountId = accountId;			
		}
	}
}