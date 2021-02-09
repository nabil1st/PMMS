package event
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	public class GetExpenseSubCategoryStatsEvent extends CairngormEvent
	{
		public static const GET_EXPENSE_SUB_CATEGORY_STATS_EVENT:String = 
			"GET_EXPENSE_SUB_CATEGORY_STATS_EVENT";
		
		public var accountId:String;
		public var startDate:Date;
		public var endDate:Date;
		
		public function GetExpenseSubCategoryStatsEvent(accountId:String, sDate:Date, eDate:Date)
		{
			super("GET_EXPENSE_SUB_CATEGORY_STATS_EVENT");
			this.accountId = accountId;
			this.startDate = sDate;
			this.endDate = eDate;
		}
	}
}