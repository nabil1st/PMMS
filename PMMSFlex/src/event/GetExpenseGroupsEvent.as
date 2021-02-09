package event
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	public class GetExpenseGroupsEvent extends CairngormEvent
	{
		public static const GET_EXPENSE_GROUPS_EVENT:String = "GET_EXPENSE_GROUPS_EVENT";
		
		public var accountId:String;
		
		public function GetExpenseGroupsEvent(accountId:String)
		{
			super("GET_EXPENSE_GROUPS_EVENT");
			this.accountId = accountId;			
		}
	}
}