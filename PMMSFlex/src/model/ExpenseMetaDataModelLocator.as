package model
{
	import com.adobe.cairngorm.CairngormError;
	import com.adobe.cairngorm.CairngormMessageCodes;
	import com.adobe.cairngorm.model.IModelLocator;
	
	import mx.collections.ArrayCollection;
	
	public class ExpenseMetaDataModelLocator implements IModelLocator
	{
		private static var modelLocator:ExpenseMetaDataModelLocator;
		
		[Bindable] 
		public var bankAccounts:ArrayCollection = new ArrayCollection();
		public var bankAccountsRawList:ArrayCollection = new ArrayCollection;
		
		[Bindable]
		public var creditCards:ArrayCollection = new ArrayCollection();
		public var creditCardsRawList:ArrayCollection = new ArrayCollection();
		
		[Bindable]
		public var expenseGroups:ArrayCollection = new ArrayCollection();
		public var expenseGroupsRawList:ArrayCollection = new ArrayCollection();
		
		[Bindable]
		public var expenseCategories:ArrayCollection = new ArrayCollection();
		public var expenseCategoriesRawList:ArrayCollection = new ArrayCollection();
		
		[Bindable]
		public var expenseTypesForCategory:ArrayCollection = new ArrayCollection();
		public var expenseTypesForCategoryRawList:ArrayCollection = new ArrayCollection();
		
		[Bindable]
		public var expenseSubTypes:ArrayCollection = new ArrayCollection();
		public var expenseSubTypesRawList:ArrayCollection = new ArrayCollection();
		
		[Bindable]
		public var expensePurposeTypes:ArrayCollection = new ArrayCollection();
		public var expensePurposeTypesRawList:ArrayCollection = new ArrayCollection();
		
		[Bindable]
		public var users:ArrayCollection = new ArrayCollection();
		public var usersRawList:ArrayCollection = new ArrayCollection();
		
		[Bindable]
		public var payees:ArrayCollection = new ArrayCollection();
		public var payeesRawList:ArrayCollection = new ArrayCollection();
		
		[Bindable]
		public var paymentMethods:ArrayCollection = new ArrayCollection();
		public var paymentMethodsRawList:ArrayCollection = new ArrayCollection();
		
		public function ExpenseMetaDataModelLocator()
		{
			if (modelLocator != null) {
				throw new CairngormError(CairngormMessageCodes.SINGLETON_EXCEPTION, "ExpenseMetaDataModelLocator");
			}
		}
		
		public static function getInstance():ExpenseMetaDataModelLocator {
			if (modelLocator == null) {
				modelLocator = new ExpenseMetaDataModelLocator();
			}
			
			return modelLocator;
		}
	}
}