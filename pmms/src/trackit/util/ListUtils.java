/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.model.SelectItem;

import trackit.backingbeans.CreditCardStatementItem;
import trackit.backingbeans.CreditCardStatementItemComparator;
import trackit.businessobjects.BankAccount;
import trackit.businessobjects.Borrower;
import trackit.businessobjects.CreditCard;
import trackit.businessobjects.CreditCardTransaction;
import trackit.businessobjects.CreditCardTransactionType;
import trackit.businessobjects.CurrencyOnHand;
import trackit.businessobjects.CurrencyOnHandSourceType;
import trackit.businessobjects.CurrencyOnHandStatus;
import trackit.businessobjects.CurrencyOnHandType;
import trackit.businessobjects.Expense;
import trackit.businessobjects.ExpenseCategory;
import trackit.businessobjects.ExpenseGroup;
import trackit.businessobjects.ExpensePurpose;
import trackit.businessobjects.ExpenseSubType;
import trackit.businessobjects.IncomeSource;
import trackit.businessobjects.Payee;
import trackit.businessobjects.PaymentMethod;
import trackit.businessobjects.TransactionReason;
import trackit.businessobjects.User;
import trackit.service.BankAccountService;
import trackit.service.BorrowerService;
import trackit.service.CreditCardService;
import trackit.service.CurrencyOnHandService;
import trackit.service.ExpenseGroupService;
import trackit.service.IncomeService;
import trackit.service.PayeeService;
import trackit.service.UserService;
import trackit.servicelocator.ServiceLocator;

/**
 *
 * @author ndaoud
 */
public class ListUtils {
	
	public static Long NULL_CONST = new Long(-1000);

    public static Map<Long, String> getBorrowersLookup(ServiceLocator serviceLocator) {
        String currentAccountId = FacesUtils.getSessionBean().getCurrentAccountId();
        
        List<Borrower> borrowers = serviceLocator.getBorrowerService().getAllBorrowersForAccount(
                currentAccountId);

        Map<Long,String> borrowerLookupMap = new HashMap<Long,String>();
        for (Borrower p : borrowers) {
            borrowerLookupMap.put(p.getId(), p.getName());
        }

        return borrowerLookupMap;
    }

    public static List<SelectItem> getCurrencyOnHandStatusList() {
        List<SelectItem> dropList = new ArrayList<SelectItem>();
        dropList.add(new SelectItem(CurrencyOnHandStatus.ALL.toInt(), "All"));
        dropList.add(new SelectItem(CurrencyOnHandStatus.ON_HAND.toInt(), "On Hand"));
        dropList.add(new SelectItem(CurrencyOnHandStatus.NOT_ON_HAND.toInt(), "Not On Hand"));
        return dropList;
    }

    public static List<SelectItem> getCurrencyOnHandTypeList() {
        List<SelectItem> dropList = new ArrayList<SelectItem>();
        dropList.add(new SelectItem(CurrencyOnHandType.UNKNOWN.toInt(), "All"));
        dropList.add(new SelectItem(CurrencyOnHandType.CASH.toInt(), "Cash"));
        dropList.add(new SelectItem(CurrencyOnHandType.CASHIERS_CHECK.toInt(), "Cashier's Check"));
        dropList.add(new SelectItem(CurrencyOnHandType.CHECK.toInt(), "Check"));
        dropList.add(new SelectItem(CurrencyOnHandType.MONEY_ORDER.toInt(), "Money Order"));
        return dropList;
    }

    public static List<SelectItem> getCurrencyOnHandSourceTypeList() {
        List<SelectItem> dropList = new ArrayList<SelectItem>();
        dropList.add(new SelectItem(CurrencyOnHandSourceType.UNKNOWN.toInt(), "All"));
        dropList.add(new SelectItem(CurrencyOnHandSourceType.GIFT.toInt(), "Gift"));
        dropList.add(new SelectItem(CurrencyOnHandSourceType.INCOME.toInt(), "Income"));
        dropList.add(new SelectItem(CurrencyOnHandSourceType.LOAN_PAYMENT.toInt(), "Loan Payment"));
        dropList.add(new SelectItem(CurrencyOnHandSourceType.MONEY_ORDER_PURCHASE.toInt(), "Money Order Purchase"));
        return dropList;
    }

    public static List<SelectItem> getExpenseGroupList(ServiceLocator serviceLocator, boolean addBlankItem) {
    	List<ExpenseGroup> ets = getExpenseGroups(serviceLocator);
        
        List<SelectItem> dropList = new ArrayList<SelectItem>();
        
        if (addBlankItem) {
        	dropList.add(new SelectItem(NULL_CONST, ""));
        }

        for (ExpenseGroup et : ets) {
            dropList.add(new SelectItem(et.getId(), et.getDescription()));
        }
        return dropList;
    }
    
    private static List<ExpenseGroup> getExpenseGroups(ServiceLocator serviceLocator) {
    	return getExpenseGroups(serviceLocator.getExpenseGroupService());
    }
    
    private static List<ExpenseGroup> getExpenseGroups(ExpenseGroupService service) {
    	if (FacesUtils.getSessionBean().getExpenseGroupList() == null) {
	        List<ExpenseGroup> ets = service.getAllExpenseGroupsForAccount(
	                    FacesUtils.getSessionBean().getCurrentAccountId());
	        Collections.sort(ets, new Comparator<ExpenseGroup>() {
	
				@Override
				public int compare(ExpenseGroup o1, ExpenseGroup o2) {
					return o1.getDescription().compareTo(o2.getDescription());
				}
	        	
	        });
	        
	        FacesUtils.getSessionBean().setExpenseGroupList(ets);
    	}
    	
    	return FacesUtils.getSessionBean().getExpenseGroupList();
    }
    
    public static Map<Long,String> getExpenseGroupLookup(ServiceLocator serviceLocator) {
    	List<ExpenseGroup> ets = getExpenseGroups(serviceLocator.getExpenseGroupService());
    	
    	Map<Long,String> lookup = new HashMap<Long,String>();
		for (ExpenseGroup eg : ets) {
			lookup.put(eg.getId(), eg.getDescription());
		}
		
		return lookup;
    }

    public static List<SelectItem> getIncomeSourceList(ServiceLocator serviceLocator, boolean addBlankItem) {
    	List<IncomeSource> ets = getIncomeSources(serviceLocator.getIncomeService());
    	
        List<SelectItem> dropList = new ArrayList<SelectItem>();
        
        if (addBlankItem) {
        	dropList.add(new SelectItem(NULL_CONST, ""));
        }

        for (IncomeSource et : ets) {
            dropList.add(new SelectItem(et.getId(), et.getName()));
        }
        return dropList;
    }
    
    public static List<IncomeSource> getIncomeSources(IncomeService service) {
    	List<IncomeSource> ets = null;
    	if (FacesUtils.getSessionBean().getIncomeSourceList() != null) {
    		ets = FacesUtils.getSessionBean().getIncomeSourceList();
    	} else {
    		ets = service.getAllIncomeSourcesForAccount(FacesUtils.getSessionBean().getCurrentAccountId());
    		Collections.sort(ets, new Comparator<IncomeSource>() {
    			
				@Override
				public int compare(IncomeSource o1, IncomeSource o2) {
					return o1.getName().compareTo(o2.getName());
				}
	        	
	        });
    		FacesUtils.getSessionBean().setIncomeSourceList(ets);
    	}
    	
    	return ets;
    }

    public static List<SelectItem> getBorrowersList(ServiceLocator serviceLocator, boolean addBlankItem) {
    	
    	List<Borrower> ets = getBorrowers(serviceLocator.getBorrowerService());
        
        List<SelectItem> dropList = new ArrayList<SelectItem>();
        
        if (addBlankItem) {
        	dropList.add(new SelectItem(NULL_CONST, ""));
        }

        for (Borrower et : ets) {
            dropList.add(new SelectItem(et.getId(), et.getName()));
        }
        return dropList;
    }
    
    public static List<Borrower> getBorrowers(BorrowerService service) {
    	List<Borrower> ets = null;
    	
    	if (FacesUtils.getSessionBean().getBorrowersList() != null) {
    		ets = FacesUtils.getSessionBean().getBorrowersList();
    	} else {    	
    		ets = service.getAllBorrowersForAccount(FacesUtils.getSessionBean().getCurrentAccountId());
    		Collections.sort(ets, new Comparator<Borrower>() {    			
				@Override
				public int compare(Borrower o1, Borrower o2) {
					return o1.getName().compareTo(o2.getName());
				}
            	
            });
    		FacesUtils.getSessionBean().setBorrowersList(ets);
    	}
    	
    	return ets;

    }

    public static List<SelectItem> getPaymentMethodList(ServiceLocator serviceLocator) {
        List<SelectItem> dropList = new ArrayList<SelectItem>();
        dropList.add(new SelectItem(PaymentMethod.CASH.toInt(), "Cash"));
        dropList.add(new SelectItem(PaymentMethod.CHECK.toInt(), "Check"));
        dropList.add(new SelectItem(PaymentMethod.MONEY_ORDER.toInt(), "Money Order"));
        dropList.add(new SelectItem(PaymentMethod.CREDIT.toInt(), "Credit Card"));
        dropList.add(new SelectItem(PaymentMethod.DEBIT.toInt(), "Debit Card"));
        dropList.add(new SelectItem(PaymentMethod.E_PAYMENT.toInt(), "E Payment"));
        return dropList;
    }
    
    public static List<SelectItem> getExpensePurposeList(ServiceLocator serviceLocator) {
        List<SelectItem> dropList = new ArrayList<SelectItem>();
        dropList.add(new SelectItem(NULL_CONST, ""));
        dropList.add(new SelectItem(ExpensePurpose.Personal.toInt(), "Personal"));
        dropList.add(new SelectItem(ExpensePurpose.Business.toInt(), "Business"));
        dropList.add(new SelectItem(ExpensePurpose.Loan.toInt(), "Loan"));        
        return dropList;
    }

    public static Map<Long,String> getPayeeLookup(ServiceLocator serviceLocator) {    	
    	Map<Long,String> payeeLookup = new HashMap<Long,String>();    	
    	List<Payee> payees = getPayees(serviceLocator.getPayeeService());
    	
        for (Payee payee : payees) {
        	payeeLookup.put(payee.getId(), payee.getDescription());
        }
        
        return payeeLookup;
    }
    
    
    public static List<SelectItem> getPayeeList(ServiceLocator serviceLocator, boolean addBlankItem) {
    	
    	List<Payee> payees = getPayees(serviceLocator.getPayeeService());
	    
	    List<SelectItem> dropList = new ArrayList<SelectItem>();
	    
	    if (addBlankItem) {
	    	dropList.add(new SelectItem(NULL_CONST, ""));
	    }
	    
	    for (Payee p : payees) {
	        dropList.add(new SelectItem(p.getId(), p.getDescription()));
	    }
	    return dropList;
    }
    
    public static List<Payee> getPayees(PayeeService service) {
    	List<Payee> payees = null;
    	
    	if (FacesUtils.getSessionBean().getPayeeList() != null) {
    		payees = FacesUtils.getSessionBean().getPayeeList();
    	} else {    	
    		payees = service.getAllPayeesForAccount(FacesUtils.getSessionBean().getCurrentAccountId());
    		Collections.sort(payees, new Comparator<Payee>() {
    			@Override
    			public int compare(Payee o1, Payee o2) {
    				return o1.getDescription().compareTo(o2.getDescription());
    			}	    	
    	    });
    		FacesUtils.getSessionBean().setPayeeList(payees);
    	}
	    
    	return payees;	    
    }
    
    public static List<SelectItem> getBankAccountList(ServiceLocator serviceLocator, boolean addBlankItem) {
    	
    	List<BankAccount> ets = getBankAccounts(serviceLocator.getBankAccountService());
    	
		List<SelectItem> dropList = new ArrayList<SelectItem>();
		
		if (addBlankItem) {
			dropList.add(new SelectItem(NULL_CONST, ""));
		}
		
		for (BankAccount et : ets) {
		    dropList.add(new SelectItem(et.getId(), et.getDescription()));
		}
		return dropList;
    }
    
    public static List<BankAccount> getBankAccounts(BankAccountService service) {
    	List<BankAccount> list = null;
    	if (FacesUtils.getSessionBean().getBankAccounts() != null) {
    		list = FacesUtils.getSessionBean().getBankAccounts();
    	} else {
    		list = service.getAllBankAccountsForAccount(
    				FacesUtils.getSessionBean().getCurrentAccountId());
        	Collections.sort(list, new Comparator<BankAccount>() {
    			@Override
    			public int compare(BankAccount o1, BankAccount o2) {
    				return o1.getDescription().compareTo(o2.getDescription());
    			}    		
        	});
        	FacesUtils.getSessionBean().setBankAccounts(list);
    	}
    	
    	return list;
    }
    
    public static List<SelectItem> getCreditCardList(ServiceLocator serviceLocator, boolean addBlankItem) {
    	
    	List<CreditCard> ets = getCreditCards(serviceLocator.getCreditCardService());
		
    	List<SelectItem> dropList = new ArrayList<SelectItem>();
    	
    	if (addBlankItem) {
    		dropList.add(new SelectItem(NULL_CONST, ""));
    	}
		for (CreditCard et : ets) {
		    dropList.add(new SelectItem(et.getId(), et.getDescription()));
		}
		return dropList;
    }
    
    public static List<CreditCard> getCreditCards(CreditCardService service) {
    	List<CreditCard> list = null;
    	if (FacesUtils.getSessionBean().getCreditCards() != null) {
    		list = FacesUtils.getSessionBean().getCreditCards();
    	} else {
    		list = service.getAllCreditCardsForAccount(FacesUtils.getSessionBean().getCurrentAccountId());
    		Collections.sort(list, new Comparator<CreditCard>() {

				@Override
				public int compare(CreditCard o1, CreditCard o2) {
					return o1.getDescription().compareTo(o2.getDescription());
				}
				
			});
    		
    		FacesUtils.getSessionBean().setCreditCards(list);
    	}
    	
    	return list;
    }
    
    public static Map<Long,String> getExpenseTypeLookup(ServiceLocator serviceLocator) {
    	
    	if (FacesUtils.getSessionBean().getExpenseTypeLookup() != null) {
    		return FacesUtils.getSessionBean().getExpenseTypeLookup();
    	} else {
	    	Map<Long,String> expenseTypeLookup = new HashMap<Long,String>();
	        List<ExpenseCategory> expenseCategories = 
	        	serviceLocator.getExpenseCategoryService().getAllExpenseCategoriesForAccount(
	        			FacesUtils.getSessionBean().getCurrentAccountId());
	
	        for (ExpenseCategory cat : expenseCategories) {
	        	List<ExpenseSubType> types = cat.getSubTypes();
	        	for (ExpenseSubType st : types) {
	        		expenseTypeLookup.put(st.getId(), cat.getDescription() + ":" + st.getDescription());
	        	}
	        }
	        
	        FacesUtils.getSessionBean().setExpenseTypeLookup(expenseTypeLookup);
	
	        return expenseTypeLookup;
    	}
    }
    
    public static List<SelectItem> getExpenseCategoryTypeList(ServiceLocator serviceLocator) {
    	
    	if (FacesUtils.getSessionBean().getExpenseCategoryTypeList() != null) {
    		return FacesUtils.getSessionBean().getExpenseCategoryTypeList();
    	} else {    	
	    	List<SelectItem> dropList = new ArrayList<SelectItem>();
	    	
	    	dropList.add(new SelectItem(NULL_CONST, ""));
	    	
	        String currentAccountId = FacesUtils.getSessionBean().getCurrentAccountId();
	        
	        List<ExpenseCategory> availableCategories =
	                serviceLocator.getExpenseCategoryService().
	                    getAllExpenseCategoriesForAccount(currentAccountId);
	
	        Collections.sort(availableCategories, new Comparator<ExpenseCategory>(){
	
				@Override
				public int compare(ExpenseCategory o1, ExpenseCategory o2) {
					return o1.getDescription().compareTo(o2.getDescription());
				}
	        	
	        });
	        
	        for (ExpenseCategory cat : availableCategories) {                       
	            if (cat.getSubTypes() != null && cat.getSubTypes().size() > 0) {
	            	List<ExpenseSubType> subTypeList = cat.getSubTypes();
	            	Collections.sort(subTypeList, new Comparator<ExpenseSubType>(){
	
						@Override
						public int compare(ExpenseSubType o1, ExpenseSubType o2) {
							return o1.getDescription().compareTo(o2.getDescription());
						}
	            		
	            	});
	            	
	                for (ExpenseSubType subType : subTypeList) {
	                    String description = cat.getDescription() + ":" + subType.getDescription();
	                    dropList.add(new SelectItem(subType.getId(), description));
	                }
	            } 
	        }
	        
	        FacesUtils.getSessionBean().setExpenseCategoryTypeList(dropList);
        
	        return dropList;
    	}
    }
    
    public static Map<Long,String> getUserNameLookup(ServiceLocator serviceLocator) {
    	Map<Long,String> lookup = new HashMap<Long,String>();
        
        List<User> list = getUsers(serviceLocator.getUserService());

        for (User user : list) {
            lookup.put(user.getId(), user.getFirstLastName());
            
        }

        return lookup;
    }
    
    
    
    public static Map<Long,User> getUserLookup(ServiceLocator serviceLocator) {        
        Map<Long,User> userLookup = new HashMap<Long,User>();
        List<User> users = getUsers(serviceLocator.getUserService());

        for (User user : users) {
            userLookup.put(user.getId(), user);
        }

        return userLookup;
    }
    
    public static List<User> getUsers(UserService service) {
    	List<User> list = null;
    	if (FacesUtils.getSessionBean().getUsers() != null) {
    		list = FacesUtils.getSessionBean().getUsers();    	
    	} else {
    		list = service.getAllUsersForAccount(FacesUtils.getSessionBean().getCurrentAccountId());
    		Collections.sort(list, new Comparator<User>(){
    			
				@Override
				public int compare(User o1, User o2) {
					return o1.getFirstLastName().compareTo(o2.getFirstLastName());
				}
	        	
	        });
    		
    		FacesUtils.getSessionBean().setUsers(list);    		
    	}
    	
    	return list;
    }

	public static List<SelectItem> getCreditCardTransactionTypeList() {
		List<SelectItem> dropList = new ArrayList<SelectItem>();
        dropList.add(new SelectItem(CreditCardTransactionType.ANNUAL_FEE.toInt(), "Annual Fee"));
        dropList.add(new SelectItem(CreditCardTransactionType.FINANCE_CHARGE.toInt(), "Finance Charge"));
        dropList.add(new SelectItem(CreditCardTransactionType.LATE_FEE.toInt(), "Late Fee"));
        
        return dropList;
	}
	
	
	public static List<SelectItem> getAvailableMoneyOrdersUIList(
			ServiceLocator serviceLocator, CurrencyOnHand addToList, boolean addBlankItem) {
	    List<CurrencyOnHand> availableMoneyOrders =
	            getAvailableMoneyOrdersList(serviceLocator.getCurrencyOnHandService());
	    Map<Long, IncomeSource> incomeSourceLookup = getIncomeSourceLookup(serviceLocator.getIncomeService());
	    
	    List<SelectItem> dropList = new ArrayList<SelectItem>();
	    for (CurrencyOnHand et : availableMoneyOrders) {
	        et.setIncomeSourceLookup(incomeSourceLookup);
	        dropList.add(new SelectItem(et.getId(), et.toString()));
	    }
	    
	    if (addToList != null) {
	    	addToList.setIncomeSourceLookup(incomeSourceLookup);
	    	dropList.add(0, new SelectItem(addToList.getId(), addToList.toString()));
	    }
	    
	    if (addBlankItem) {
	    	dropList.add(0, new SelectItem(NULL_CONST, ""));
	    }
	
	    return dropList;
	}
	
	public static List<CurrencyOnHand> getAvailableMoneyOrdersList(CurrencyOnHandService service) {
		if (FacesUtils.getSessionBean().getAvailableMoneyOrderList() != null) {
			return FacesUtils.getSessionBean().getAvailableMoneyOrderList();
		} else {
			List<CurrencyOnHand> list = service.getAllUnusedMoneyOrdersOnHandForAccount(
                    FacesUtils.getSessionBean().getCurrentAccountId());
			FacesUtils.getSessionBean().setAvailableMoneyOrderList(list);
			return list;
		}
	}
	
	public static Map<Long, IncomeSource> getIncomeSourceLookup(IncomeService service) {
		if (FacesUtils.getSessionBean().getIncomeSourceLookup() != null) {
			return FacesUtils.getSessionBean().getIncomeSourceLookup();
		} else {
			List<IncomeSource> incomeSourceList = getIncomeSources(service);
			Map<Long,IncomeSource> incomeSourceLookup = new HashMap<Long,IncomeSource>();
		    for (IncomeSource source : incomeSourceList) {
		        incomeSourceLookup.put(source.getId(), source);
		    }
		    
		    FacesUtils.getSessionBean().setIncomeSourceLookup(incomeSourceLookup);
		    
		    return incomeSourceLookup;
		}
	}
	
	public static boolean isNullSelection(Long id) {
		return id == null || id.longValue() == NULL_CONST.longValue();
	}
	
	
	public static List<CreditCardStatementItem> getCreditCardTransactions(
			Date fromDate, Date toDate, ServiceLocator serviceLocator) {
		
		Map<Long,String> payeeLookupMap = getPayeeLookup(serviceLocator);
        
        List<CreditCardStatementItem> statementItems = new ArrayList<CreditCardStatementItem>();
        
        List<Expense> expenses = serviceLocator.getExpenseService().getAllExpensesForCreditCard(
                FacesUtils.getSessionBean().getCurrentCreditCardId(), fromDate, toDate);
        for (Expense e : expenses) {
            CreditCardStatementItem si = new CreditCardStatementItem();
            si.setDate(e.getDate());
            si.setDateStr(DateFormatUtil.formatDate(e.getDate()));
            si.setTo(payeeLookupMap.get(e.getPayeeId()));
            si.setAmount(e.getAmount());
            si.setCreditCardTransactionType(CreditCardTransactionType.UNKNOWN);

            StringBuffer description = new StringBuffer();

            description.append("Purchase");

            si.setDescription(description.toString());

            statementItems.add(si);
        }

        // get all other bank account transactions for bank account
        List<CreditCardTransaction> transactions =  serviceLocator.getCreditCardService().
                getAllCreditCardTransactionsForCreditCard(
                    FacesUtils.getSessionBean().getCurrentCreditCardId(), fromDate, toDate);

        for (CreditCardTransaction e : transactions) {
            CreditCardStatementItem si = new CreditCardStatementItem();
            si.setDate(e.getDate());
            si.setDateStr(DateFormatUtil.formatDate(e.getDate()));
            si.setAmount(e.getAmount());


            if (e.getInitiatorId() != null) {
                si.setTo(payeeLookupMap.get(e.getInitiatorId()));
            }


            StringBuffer description = new StringBuffer();

            if (e.getTransactionType().equals(CreditCardTransactionType.ANNUAL_FEE)) {
                description.append("Annual Fee");
            } else if (e.getTransactionType().equals(CreditCardTransactionType.BALANCE_TRANSFER)) {
                description.append("Balance Transfer");
            } else if (e.getTransactionType().equals(CreditCardTransactionType.CASH_ADVANCE_CHARGE)) {
                description.append("Cash Advance Charge");
            } else if (e.getTransactionType().equals(CreditCardTransactionType.CASH_BACK)) {
                description.append("Cash Back Paid");
            } else if (e.getTransactionType().equals(CreditCardTransactionType.FINANCE_CHARGE)) {
                description.append("Finance Charge");
            } else if (e.getTransactionType().equals(CreditCardTransactionType.LATE_FEE)) {
                description.append("Late Fee");
            } else if (e.getTransactionType().equals(CreditCardTransactionType.OPENING_BALANCE)) {
                description.append("Starting Balance");
            } else if (e.getTransactionType().equals(CreditCardTransactionType.PAYMENT)) {
                description.append("Payment. Thank you!");
            } else if (e.getTransactionType().equals(CreditCardTransactionType.PURCHASE)) {
                description.append("Purchase");
            }

            if (e.getTransactionReason() != null) {
                if (e.getTransactionReason().equals(TransactionReason.MONEY_ORDER_PURCHASE)) {
                    description.append(" ").append("Money Order Purchase");
                } else if (e.getTransactionReason().equals(TransactionReason.CREDIT_CARD_PAYMENT)) {
                    description.append(" ").append("Credit Card Payment");
                } if (e.getTransactionReason().equals(TransactionReason.LOAN)) {
                    description.append(" ").append("Loan");
                }
            }

            si.setDescription(description.toString());

            si.setCreditCardTransactionType(e.getTransactionType());

            statementItems.add(si);
        }        


        Collections.sort(statementItems, new CreditCardStatementItemComparator());
        return statementItems;
	}
    
    

}
