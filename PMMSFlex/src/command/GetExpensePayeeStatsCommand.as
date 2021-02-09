package command
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import model.PieChartsModelLocator;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.IResponder;
	import event.GetExpensePayeeStatsEvent;
	
	public class GetExpensePayeeStatsCommand implements ICommand, IResponder
	{
		private var getExpensePayeeStatsEvent:GetExpensePayeeStatsEvent;
		
		public function GetExpensePayeeStatsCommand()
		{
		}
		
		public function execute(event:CairngormEvent):void {
			getExpensePayeeStatsEvent = GetExpensePayeeStatsEvent(event);
			var delegate:ReportsServiceDelegate = new ReportsServiceDelegate(this);
			delegate.getExpensePayeeStats(getExpensePayeeStatsEvent.accountId,
				getExpensePayeeStatsEvent.startDate,
				getExpensePayeeStatsEvent.endDate);
			
		}
		
		public function result(data:Object):void {
			var stats:ArrayCollection = data.result as ArrayCollection;
			PieChartsModelLocator.getInstance().pieChartData = stats;
			
		}
		
		public function fault(data:Object):void {
			
		}
	}
}