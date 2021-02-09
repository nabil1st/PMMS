package event
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	public class GetExpensePurposeTypesEvent extends CairngormEvent
	{
		public static const GET_EXPENSE_PURPOSE_TYPES_EVENT:String = "GET_EXPENSE_PURPOSE_TYPES_EVENT";
				
		
		public function GetExpensePurposeTypesEvent()
		{
			super("GET_EXPENSE_PURPOSE_TYPES_EVENT");						
		}
	}
}