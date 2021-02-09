package event
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	public class GetUsersEvent extends CairngormEvent
	{
		public static const GET_USERS_EVENT:String = "GET_USERS_EVENT";
		
		public var accountId:String;
		
		public function GetUsersEvent(accountId:String)
		{
			super("GET_USERS_EVENT");
			this.accountId = accountId;			
		}
	}
}