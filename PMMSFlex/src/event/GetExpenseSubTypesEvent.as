package event
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	public class GetExpenseSubTypesEvent extends CairngormEvent
	{
		public static const GET_EXPENSE_SUBTYPES_EVENT:String = "GET_EXPENSE_SUBTYPES_EVENT";
		
		public var accountId:String;
		
		public function GetExpenseSubTypesEvent(accountId:String)
		{
			super("GET_EXPENSE_SUBTYPES_EVENT");
			this.accountId = accountId;			
		}
	}
}