package model
{
	[RemoteClass(alias="trackit.webservices.ExpenseCategoryStats")]
	public class ExpenseCategoryStats
	{
		public var category:String;
		public var percentage:int;
		public var amount:Number;
		
		public function ExpenseCategoryStats()
		{
		}
	}
}