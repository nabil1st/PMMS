package command
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import model.PieChartsModelLocator;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.IResponder;
	import event.GetExpenseSubCategoryStatsEvent;
	
	public class GetExpenseSubCategoryStatsCommand implements ICommand, IResponder
	{
		private var getExpenseSubCategoryStatsEvent:GetExpenseSubCategoryStatsEvent;
		
		public function GetExpenseSubCategoryStatsCommand()
		{
		}
		
		public function execute(event:CairngormEvent):void {
			getExpenseSubCategoryStatsEvent = GetExpenseSubCategoryStatsEvent(event);
			var delegate:ReportsServiceDelegate = new ReportsServiceDelegate(this);
			delegate.getExpenseSubCategoryStats(getExpenseSubCategoryStatsEvent.accountId,
				getExpenseSubCategoryStatsEvent.startDate,
				getExpenseSubCategoryStatsEvent.endDate);
			
		}
		
		public function result(data:Object):void {
			var stats:ArrayCollection = data.result as ArrayCollection;
			PieChartsModelLocator.getInstance().pieChartData = stats;
			
		}
		
		public function fault(data:Object):void {
			
		}
	}
}