package event
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	public class GetExpenseDataEvent extends CairngormEvent
	{
		public static const GET_EXPENSE_DATA_EVENT:String = "GET_EXPENSE_DATA_EVENT";
		
		public var accountId:String;
		public var userId:String;
		public var startDate:Date;
		public var endDate:Date;
		public var payeeId:String;
		public var expensePurposeId:String;
		public var expenseCategoryId:String;
		public var expenseTypeId:String;
		public var paymentMethodId:String;
		public var bankAccountId:String;
		public var creditCardId:String;
		public var projectId:String;
		
		public function GetExpenseDataEvent(accountId:String,
											userId:String,
											startDate:Date,
											endDate:Date,
											payeeId:String,
											expensePurposeId:String,
											expenseCategoryId:String,
											expenseTypeId:String,
											paymentMethodId:String,
											bankAccountId:String,
											creditCardId:String,
											projectId:String)
		{
			super("GET_EXPENSE_DATA_EVENT");
			this.accountId = accountId;			
			this.userId = userId;
			this.startDate = startDate;
			this.endDate = endDate;
			this.payeeId = payeeId;
			this.expensePurposeId = expensePurposeId;
			this.expenseCategoryId = expenseCategoryId;
			this.expenseTypeId = expenseTypeId;
			this.paymentMethodId = paymentMethodId;
			this.bankAccountId = bankAccountId;
			this.creditCardId = creditCardId;
			this.projectId = projectId;
		}
	}
}