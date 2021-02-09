package model
{
	import com.adobe.cairngorm.CairngormError;
	import com.adobe.cairngorm.CairngormMessageCodes;
	import com.adobe.cairngorm.model.IModelLocator;
	
	import mx.collections.ArrayCollection;

	public class PieChartsModelLocator implements IModelLocator
	{
		private static var modelLocator:PieChartsModelLocator;
		
		[Bindable] 
		public var pieChartData:ArrayCollection = new ArrayCollection();
		
		public function PieChartsModelLocator()
		{
			if (modelLocator != null) {
				throw new CairngormError(CairngormMessageCodes.SINGLETON_EXCEPTION, "PieChartsModelLocator");
			}
		}
		
		public static function getInstance():PieChartsModelLocator {
			if (modelLocator == null) {
				modelLocator = new PieChartsModelLocator();
			}
			
			return modelLocator;
		}
	}
}