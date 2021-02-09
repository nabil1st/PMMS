package model
{
	import com.adobe.cairngorm.CairngormError;
	import com.adobe.cairngorm.CairngormMessageCodes;
	import com.adobe.cairngorm.model.IModelLocator;
	
	import mx.collections.ArrayCollection;
	
	public class ExpenseDataModelLocator implements IModelLocator
	{
		private static var modelLocator:ExpenseDataModelLocator;
		
		[Bindable] 
		public var expenseData:ArrayCollection = new ArrayCollection();
		
		[Bindable]
		public var expenseDataTotal:String = "$0.00";
		
		[Bindable]
		public var expenseDataForCharting:ArrayCollection = new ArrayCollection();
		
		[Bindable]
		public var expenseDataForChartingLength:int = 0;
		
		[Bindable]
		public var expenseChartLabelText:String = "";
		
		public function ExpenseDataModelLocator()
		{
			if (modelLocator != null) {
				throw new CairngormError(CairngormMessageCodes.SINGLETON_EXCEPTION, "ExpenseDataModelLocator");
			}
		}
		
		public static function getInstance():ExpenseDataModelLocator {
			if (modelLocator == null) {
				modelLocator = new ExpenseDataModelLocator();
			}
			
			return modelLocator;
		}
	}
}