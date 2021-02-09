package event
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	public class GetExpenseCategoriesEvent extends CairngormEvent
	{
		public static const GET_EXPENSE_CATEGORIES_EVENT:String = "GET_EXPENSE_CATEGORIES_EVENT";
		
		public var accountId:String;
		
		public function GetExpenseCategoriesEvent(accountId:String)
		{
			super("GET_EXPENSE_CATEGORIES_EVENT");
			this.accountId = accountId;			
		}
	}
}