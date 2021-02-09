package event
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	public class GetBankAccountsEvent extends CairngormEvent
	{
		public static const GET_BANK_ACCOUNTS_EVENT:String = "GET_BANK_ACCOUNTS_EVENT";
		
		public var accountId:String;
				
		public function GetBankAccountsEvent(accountId:String)
		{
			super("GET_BANK_ACCOUNTS_EVENT");
			this.accountId = accountId;			
		}
	}
}