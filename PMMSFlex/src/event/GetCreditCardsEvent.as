package event
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	public class GetCreditCardsEvent extends CairngormEvent
	{
		public static const GET_CREDIT_CARDS_EVENT:String = "GET_CREDIT_CARDS_EVENT";
		
		public var accountId:String;
		
		public function GetCreditCardsEvent(accountId:String)
		{
			super("GET_CREDIT_CARDS_EVENT");
			this.accountId = accountId;			
		}
	}
}