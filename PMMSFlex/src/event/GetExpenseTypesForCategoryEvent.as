package event
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	public class GetExpenseTypesForCategoryEvent extends CairngormEvent
	{
		public static const GET_EXPENSE_TYPES_FOR_CATEGORY_EVENT:String = "GET_EXPENSE_TYPES_FOR_CATEGORY_EVENT";
		
		public var categoryId:String;
		
		public function GetExpenseTypesForCategoryEvent(categoryId:String)
		{
			super("GET_EXPENSE_TYPES_FOR_CATEGORY_EVENT");
			this.categoryId = categoryId;			
		}
	}
}