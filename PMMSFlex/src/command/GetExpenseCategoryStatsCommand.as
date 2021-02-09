package command
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import model.PieChartsModelLocator;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.IResponder;
	import event.GetExpenseCategoryStatsEvent;

	public class GetExpenseCategoryStatsCommand implements ICommand, IResponder
	{
		private var getExpenseCategoryStatsEvent:GetExpenseCategoryStatsEvent;
		
		public function GetExpenseCategoryStatsCommand()
		{
		}
		
		public function execute(event:CairngormEvent):void {
			getExpenseCategoryStatsEvent = GetExpenseCategoryStatsEvent(event);
			var delegate:ReportsServiceDelegate = new ReportsServiceDelegate(this);
			delegate.getExpenseCategoryStats(getExpenseCategoryStatsEvent.accountId,
				getExpenseCategoryStatsEvent.startDate,
				getExpenseCategoryStatsEvent.endDate);
			
		}
		
		public function result(data:Object):void {
			var stats:ArrayCollection = data.result as ArrayCollection;
			PieChartsModelLocator.getInstance().pieChartData = stats;
			
		}
		
		public function fault(data:Object):void {
			
		}
	}
}