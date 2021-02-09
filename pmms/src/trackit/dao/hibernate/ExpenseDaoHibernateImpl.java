/*
 * JCatalog Project
 */
package trackit.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
//
import trackit.businessobjects.Deposit;
import trackit.businessobjects.Expense;
import trackit.businessobjects.ExpenseItem;
import trackit.businessobjects.ExpensePurpose;
import trackit.businessobjects.PaymentMethod;
import trackit.dao.ExpenseDao;
import trackit.dao.UserDao;
import trackit.util.DateFormatUtil;

/**
 * The Hibernate implementation of the <code>UserDao</code>.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 * @see UserDao
 */
public class ExpenseDaoHibernateImpl extends HibernateDaoSupport
        implements ExpenseDao {
    /**
     * Default constructor.
     */
    public ExpenseDaoHibernateImpl() {
            super();
    }

    /**
     * @see UserDao#getUser(String)
     */
    public Expense getExpense(String id) {
            Expense ex = (Expense)getHibernateTemplate().load(Expense.class, Long.parseLong(id));
            return ex;
    }

    public Expense saveExpense(Expense ex) {
        getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
        if (ex.getId() == null || ex.getId() == 0) {
            this.getHibernateTemplate().save(ex);
        } else {
            this.getHibernateTemplate().update(ex);
        }
        getHibernateTemplate().flush();
        return ex;
    }

    public List<Expense> getAllExpensesForAccount(String accountId) {
        //List accounts = getHibernateTemplate().find("from Account account where account.id=?", Long.valueOf(accountId));
        //Account account = (Account) accounts.get(0);
        //return new ArrayList<Expense>(account.getExpenses());
    	
    	List<Long> userIds = Utils.getUserIdsForAccountId(getHibernateTemplate(), accountId);

        String[] paramNames = new String[]{"userIds"};
        Object[] values = new Object[]{userIds};
        List expenses = getHibernateTemplate().findByNamedParam("from Expense expense where expense.userId in (:userIds) order by expense.date", paramNames, values);
        return new ArrayList<Expense>(expenses);
    }

    public List<Expense> getExpensesForAccount(String accountId, Date fromDate, Date toDate) {
        // use the accountId to get all user ids for account and then use the user ids
        // to get the expenses at the given date range
        List<Long> userIds = Utils.getUserIdsForAccountId(getHibernateTemplate(), accountId);

        String[] paramNames = new String[]{"userIds", "fromDate", "toDate"};
        Object[] values = new Object[]{userIds, fromDate, DateFormatUtil.ceiling(toDate)};
        List expenses = getHibernateTemplate().findByNamedParam("from Expense expense where expense.userId in (:userIds) and expense.date >= :fromDate and expense.date < :toDate order by expense.date", paramNames, values);
        return new ArrayList<Expense>(expenses);
    }

    public List<Expense> getAllExpensesForBankAccount(String id, Date fromDate, Date toDate) {
        Object[] values = new Object[]{Long.valueOf(id), fromDate, toDate};
        List expenses = getHibernateTemplate().find("from Expense expense where expense.bankAccountId=? and expense.date >= ? and expense.date < ? order by expense.date", values);
        return new ArrayList<Expense>(expenses);
    }

    public List<Expense> getAllExpensesForCreditCard(String id, Date fromDate, Date toDate) {
        Object[] values = new Object[]{Long.valueOf(id), fromDate, toDate};
        List expenses = getHibernateTemplate().find("from Expense expense where expense.creditCardId=? and expense.date >= ? and expense.date < ? order by expense.date", values);
        return new ArrayList<Expense>(expenses);
    }

    public List<Expense> getExpensesForExpenseGroup(String groupId) {
        List<Long> groupIds = new ArrayList<Long>();
        groupIds.add(Long.parseLong(groupId));
        String[] paramNames = new String[]{"groupIds"};
        Object[] values = new Object[]{groupIds};
        List expenses = getHibernateTemplate().findByNamedParam(
                "from Expense expense where expense.expenseGroupId in (:groupIds) order by expense.date", paramNames, values);
        return new ArrayList<Expense>(expenses);
    }

    public void delete(String id) {
        Expense expense = getExpense(id);

        getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
        getHibernateTemplate().delete(expense);
        getHibernateTemplate().flush();
    }
    
    public List<Expense> getExpenses(String accountId,
       	   String userId,
   		   Date startDate,
   		   Date endDate,
   		   String payeeId,
   		   String expensePurposeId,
   		   String expenseCategoryId,
   		   String expenseTypeId,
   		   String paymentMethodId,
   		   String bankAccountId,
   		   String creditCardId,
   		   String projectId) {
      	DetachedCriteria criteria = DetachedCriteria.forClass(Expense.class);
      	
      	if (userId != null) {
      		criteria.add(Property.forName("userId").eq(Long.parseLong(userId)));
      	} else {
      		List<Long> userIds = Utils.getUserIdsForAccountId(getHibernateTemplate(), accountId);
      		criteria.add(Property.forName("userId").in(userIds));      		
      	}
      	
      	if (startDate != null) {
      		criteria.add(Property.forName("date").ge(startDate));
      	}
      	
      	if (endDate != null) {
      		criteria.add(Property.forName("date").le(endDate));
      	}
      	
      	if (payeeId != null) {
      		criteria.add(Property.forName("payeeId").eq(Long.parseLong(payeeId)));
      	}
      	
      	if (expensePurposeId != null) {
      		criteria.add(Property.forName("expensePurpose").eq(
      				ExpensePurpose.fromInt(Integer.parseInt(expensePurposeId))));
      	}
      	
      	if (expenseTypeId != null) {
      		criteria.add(Property.forName("expenseTypeId").eq(Long.parseLong(expenseTypeId)));
      	} else if (expenseCategoryId != null){
      		// Get all expenseTypeIds for selected expense category
      		List<Long> expenseTypeIds = Utils.getExpenseTypeIdsForCategoryId(getHibernateTemplate(), expenseCategoryId);
      		criteria.add(Property.forName("expenseTypeId").in(expenseTypeIds));      		
      	}
      	
      	if (paymentMethodId != null) {
      		criteria.add(Property.forName("paymentMethod").eq(
      				PaymentMethod.fromInt(Integer.parseInt(paymentMethodId))));
      	}
      	
      	if (bankAccountId != null) {
      		criteria.add(Property.forName("bankAccountId").eq(Long.parseLong(bankAccountId)));
      	}
      	
      	if (creditCardId != null) {
      		criteria.add(Property.forName("creditCardId").eq(Long.parseLong(creditCardId)));
      	}
      	
      	if (projectId != null) {
      		criteria.add(Property.forName("expenseGroupId").eq(Long.parseLong(projectId)));
      	}
      	
      	return getHibernateTemplate().findByCriteria(criteria);
      }
    
    //@Override
	public List<Object> findExpensesByExpenseItems(String accountId,
	       	   String userId,
	   		   Date startDate,
	   		   Date endDate,
	   		   String payeeId,
	   		   String expensePurposeId,
	   		   String expenseCategoryId,
	   		   String expenseTypeId,
	   		   String paymentMethodId,
	   		   String bankAccountId,
	   		   String creditCardId,
	   		   String projectId) {
		
		List<String> paramNames = new ArrayList<String>();
        List values = new ArrayList();
        
        List<Long> userIds = new ArrayList<Long>();
		if (userId != null) {
      		userIds.add(Long.parseLong(userId));
      	} else {
      		userIds = Utils.getUserIdsForAccountId(getHibernateTemplate(), accountId);      		      		
      	}
		
		paramNames.add("userIds");
		values.add(userIds);
		
		if (startDate != null) {
			paramNames.add("startDate");
			values.add(startDate);
		}
		
		if (endDate != null) {
			paramNames.add("endDate");
			values.add(endDate);
		}
		
		if (payeeId != null) {
			paramNames.add("payeeId");
			values.add(Long.parseLong(payeeId));
		}
		
		if (expensePurposeId != null) {
			paramNames.add("expensePurpose");
			values.add(ExpensePurpose.fromInt(Integer.parseInt(expensePurposeId)));
		}
		
		List<Long> expenseTypeIds = new ArrayList<Long>();
		if (expenseTypeId != null) {
			expenseTypeIds.add(Long.parseLong(expenseTypeId));
			paramNames.add("expenseTypeIds");
			values.add(expenseTypeIds);
      	} else if (expenseCategoryId != null){
      		// Get all expenseTypeIds for selected expense category
      		expenseTypeIds = Utils.getExpenseTypeIdsForCategoryId(getHibernateTemplate(), expenseCategoryId);
      		paramNames.add("expenseTypeIds");
      		values.add(expenseTypeIds);      		
      	}
		
		if (paymentMethodId != null) {
			paramNames.add("paymentMethodId");
			values.add(Long.parseLong(paymentMethodId));
		}
		
		if (bankAccountId != null) {
			paramNames.add("bankAccountId");
			values.add(Long.parseLong(bankAccountId));
		}
		
		if (creditCardId != null) {
			paramNames.add("creditCardId");
			values.add(Long.parseLong(creditCardId));
		}
		
		if (projectId != null) {
			paramNames.add("projectId");
			values.add(Long.parseLong(projectId));
		}
		
		String hql = "from Expense e inner join e.expenseItems i where ";
		hql += "e.userId in (:userIds) ";
		
		if (startDate != null) {
			hql += "and e.date >= :startDate ";
		}
		
		if (endDate != null) {
			hql += "and e.date <= :endDate ";
		}
		
		if (payeeId != null) {
			hql += "and e.payeeId = :payeeId ";
		}
		
		if (paymentMethodId != null) {
			hql += "and e.paymentMethodId = :paymentMethodId ";
		}
		
		if (bankAccountId != null) {
			hql += "and e.bankAccountId = :bankAccountId ";
		}
		
		if (creditCardId != null) {
			hql += "and e.creditCardId = :creditCardId ";
		}
		
		if (expensePurposeId != null) {
			hql += "and i.expensePurpose = :expensePurpose ";
		}
		
		if (projectId != null) {
			hql += "and i.expenseGroupId = :projectId ";
		}
		
		if (expenseTypeId != null || expenseCategoryId != null) {
			hql += "and i.expenseTypeId in (:expenseTypeIds) ";
		}	
		
		
		List results = getHibernateTemplate().findByNamedParam(hql, paramNames.toArray(new String[]{}), values.toArray());
		//Object[] result = (Object[]) deposits.get(0);
        //Deposit deposit = (Deposit) result[0];
		
        return results;
	}
}