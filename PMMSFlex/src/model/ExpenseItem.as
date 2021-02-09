package model
{
	[RemoteClass(alias="trackit.backingbeans.ExpenseItem")]
	public class ExpenseItem
	{
		public var id:Number;
		public var date:Date;
		public var dateStr:String;
		public var group:String;
		public var payee:String;
		public var paymentMethod:String;
		public var checkNumber:String;
		public var amount:Number;
		public var expenseCategory:String;
		public var expenseType:String;
		public var description:String;
		public var itemized:Boolean;
		public var userName:String;
		
		public function ExpenseItem()
		{
		}
	}
}