package
{
	import mx.collections.ArrayCollection;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.soap.WebService;
	
	public class ExpenseReportsRemoteService
	{
		private var remoteService : WebService = new WebService();
		
		public function ExpenseReportsRemoteService(hostURL:String)
		{
			remoteService = new WebService();
//      		remoteService.wsdl = "http://pmms.mine.nu:8080/pmms/services/expensereports?wsdl";
			remoteService.wsdl = "http://" + hostURL + ":8080/pmms/services/expensereports?wsdl";
      		remoteService.loadWSDL();
      		remoteService.addEventListener(ResultEvent.RESULT, handleNextInSequenceResult);
		}
		
		public function callGetExpensePayeeStats(accountId:String, 
				startDate:Date, endDate:Date, callback:Function) : void {
      		var call : Object = remoteService.getExpensePayeeStats.send(accountId, startDate, endDate);
      		call.callbackFunction = callback;
  		}
  		
  		public function callGetExpenseCategoryStats(accountId:String, 
  			startDate:Date, endDate:Date, callback:Function) : void {
      		var call : Object = remoteService.getExpenseCategoryStats.send(accountId);
      		call.callbackFunction = callback;
  		}
  		
  		public function callGetExpenseSubCategoryStats(accountId:String, 
  				startDate:Date, endDate:Date, callback:Function) : void {
      		var call : Object = remoteService.getExpenseSubCategoryStats.send(accountId);
      		call.callbackFunction = callback;
  		}
  		
  		public function callGetUsersForAccount(accountId:String, callback:Function):void {
  			var call : Object = remoteService.getUsersForAccount.send(accountId);
      		call.callbackFunction = callback;
  		}
  		
  		public function callGetProjectsForAccount(accountId:String, callback:Function):void {
  			var call : Object = remoteService.getExpenseGroupsForAccount.send(accountId);
      		call.callbackFunction = callback;
  		}
  		
  		public function callGetExpenseCategoriesForAccount(accountId:String, callback:Function):void {
  			var call : Object = remoteService.getExpenseCategoriesForAccount.send(accountId);
      		call.callbackFunction = callback;
  		}
  		
  		public function callGetExpenseTypesForCategory(categoryId:String, callback:Function):void {
  			var call : Object = remoteService.getExpenseTypesForCategory.send(categoryId);
      		call.callbackFunction = callback;
  		}
  		
  		public function callGetExpenseSubTypesForAccount(accountId:String, callback:Function):void {
  			var call : Object = remoteService.getExpenseSubTypesForAccount.send(accountId);
      		call.callbackFunction = callback;
  		}
  		
  		public function callGetPayeesForAccount(accountId:String, callback:Function):void {
  			var call : Object = remoteService.getPayeesForAccount.send(accountId);
      		call.callbackFunction = callback;
  		}
  		
  		public function callGetBankAccountsForAccount(accountId:String, callback:Function):void {
  			var call : Object = remoteService.getBankAccountsForAccount.send(accountId);
      		call.callbackFunction = callback;
  		}
  		
  		public function callGetCreditCardsForAccount(accountId:String, callback:Function):void {
  			var call : Object = remoteService.getCreditCardsForAccount.send(accountId);
      		call.callbackFunction = callback;
  		}
  		
  		public function callGetPaymentMethods(callback:Function):void {
  			var call : Object = remoteService.getPaymentMethods.send();
      		call.callbackFunction = callback;
  		}
  		
  		public function callGetExpensePurposeTypes(callback:Function):void {
  			var call : Object = remoteService.getExpensePurposeTypes.send();
      		call.callbackFunction = callback;
  		}

		private function handleNextInSequenceResult(event : ResultEvent) : void {
		    var token : Object = event.token;
		    if (event.result is ArrayCollection) {
		    	token.callbackFunction(event.result);
		    } else if (event.result == null) {
		    	var emptyCollection:ArrayCollection = new ArrayCollection();
		    	token.callbackFunction(emptyCollection);
		    } else {
		    	var collection:ArrayCollection = new ArrayCollection();
		    	collection.addItem(event.result);
		    	token.callbackFunction(collection);
		    }
		}
		
		public function callGetExpenseData(accountId:String, userId:String, startDate:Date, endDate:Date,
			payeeId:String, expensePurposeId:String, expenseCategoryId:String, expenseTypeId:String, paymentMethodId:String,
			bankAccountId:String, creditCardId:String, projectId:String, callback:Function) : void {
			
			var call : Object = remoteService.getExpenseData.send(accountId, userId, startDate, endDate, 
				payeeId, expensePurposeId, expenseCategoryId, expenseTypeId, paymentMethodId, bankAccountId, creditCardId, projectId);
      		call.callbackFunction = callback;
		}
		
		public function callGetExpenseDataForCharting(accountId:String, userId:String, startDate:Date, endDate:Date,
			payeeId:String, expensePurposeId:String, expenseCategoryId:String, expenseTypeId:String, paymentMethodId:String,
			bankAccountId:String, creditCardId:String, projectId:String, period:int, callback:Function) : void {
			
			var call : Object = remoteService.getExpenseDataForCharting.send(accountId, userId, startDate, endDate, 
				payeeId, expensePurposeId, expenseCategoryId, expenseTypeId, paymentMethodId, 
				bankAccountId, creditCardId, projectId, period);
      		call.callbackFunction = callback;
		}
	}
}