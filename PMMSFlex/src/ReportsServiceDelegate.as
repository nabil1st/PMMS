package
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;

	public class ReportsServiceDelegate
	{
		private var responder:IResponder;
		private var service:Object;
		
		public function ReportsServiceDelegate(responder:IResponder)
		{
			this.responder = responder;
			this.service = ServiceLocator.getInstance().getRemoteObject("reportsService");
		}
		
		public function getExpenseCategoryStats(accountId:String, startDate:Date, endDate:Date):void {
			var call:AsyncToken = service.getExpenseCategoryStats(accountId, startDate, endDate);
			call.addResponder(this.responder);
		}
		
		public function getExpenseSubCategoryStats(accountId:String, startDate:Date, endDate:Date):void {
			var call:AsyncToken = service.getExpenseSubCategoryStats(accountId, startDate, endDate);
			call.addResponder(this.responder);
		}
		
		public function getExpensePayeeStats(accountId:String, startDate:Date, endDate:Date):void {
			var call:AsyncToken = service.getExpensePayeeStats(accountId, startDate, endDate);
			call.addResponder(this.responder);
		}
		
		public function getBankAccounts(accountId:String):void {
			var call:AsyncToken = service.getBankAccountsForAccount(accountId);
			call.addResponder(this.responder);
		}
		
		public function getCreditCards(accountId:String):void {
			var call:AsyncToken = service.getCreditCardsForAccount(accountId);
			call.addResponder(this.responder);
		}
		
		public function getExpenseGroups(accountId:String):void {
			var call:AsyncToken = service.getExpenseGroupsForAccount(accountId);
			call.addResponder(this.responder);
		}
		
		public function getExpenseCategories(accountId:String):void {
			var call:AsyncToken = service.getExpenseCategoriesForAccount(accountId);
			call.addResponder(this.responder);
		}
		
		public function getExpenseTypesForCategory(categoryId:String):void {
			var call:AsyncToken = service.getExpenseTypesForCategory(categoryId);
			call.addResponder(this.responder);
		}
		
		public function getExpenseSubTypes(accountId:String):void {
			var call:AsyncToken = service.getExpenseSubTypesForAccount(accountId);
			call.addResponder(this.responder);
		}
		
		public function getPayees(accountId:String):void {
			var call:AsyncToken = service.getPayeesForAccount(accountId);
			call.addResponder(this.responder);
		}
		
		public function getUsers(accountId:String):void {
			var call:AsyncToken = service.getUsersForAccount(accountId);
			call.addResponder(this.responder);
		}
		
		public function getExpensePurposeTypes():void {
			var call:AsyncToken = service.getExpensePurposeTypes();
			call.addResponder(this.responder);
		}
		
		public function getPaymentMethods():void {
			var call:AsyncToken = service.getPaymentMethods();
			call.addResponder(this.responder);
		}
		
		public function getExpenseData(accountId:String,
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
									   projectId:String):void {
			var call:AsyncToken = service.getExpenseData(accountId,
				userId,
				startDate,
				endDate,
				payeeId,
				expensePurposeId,
				expenseCategoryId,
				expenseTypeId,
				paymentMethodId,
				bankAccountId,
				creditCardId,
				projectId);
			call.addResponder(this.responder);
		}
		
		public function getExpenseDataForCharting(accountId:String,
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
									   projectId:String,
									   period:int):void {
			var call:AsyncToken = service.getExpenseDataForCharting(accountId,
				userId,
				startDate,
				endDate,
				payeeId,
				expensePurposeId,
				expenseCategoryId,
				expenseTypeId,
				paymentMethodId,
				bankAccountId,
				creditCardId,
				projectId,
				period);
			call.addResponder(this.responder);
		}
	}
}