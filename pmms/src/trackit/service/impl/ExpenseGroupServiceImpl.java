/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trackit.service.impl;

import java.util.List;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import trackit.businessobjects.ExpenseGroup;
import trackit.dao.ExpenseGroupDao;
import trackit.service.ExpenseGroupService;

/**
 *
 * @author Owner
 */
public class ExpenseGroupServiceImpl implements ExpenseGroupService {

    private ExpenseGroupDao dao;

    public void setExpenseGroupDao(ExpenseGroupDao dao) {
        this.dao = dao;
    }

    public ExpenseGroup saveExpenseGroup(ExpenseGroup a) {
        ExpenseGroup savedBankAccount = dao.saveExpenseGroup(a);
        return savedBankAccount;
    }

    public ExpenseGroup findExpenseGroup(String id) {
        return dao.getExpenseGroup(id);
    }
    
    public void deleteExpenseGroup(String id) {
    	dao.deleteExpenseGroup(id);
    }

    public ExpenseGroup findExpenseGroupByDescription(String description) {
        try {
            List list = this.dao.findExpenseGroupByDescription(description);
            if (list.size() == 0) {
                return null;
            }

            return (ExpenseGroup) list.get(0);
        } catch (HibernateObjectRetrievalFailureException e) {
            return null;
        }
    }

    public List<ExpenseGroup> getAllExpenseGroupsForAccount(String accountId) {
        return dao.getAllExpenseGroupsForAccount(accountId);
    }
    
    public int getNumberOfExpensesWithExpenseGroup(String id) {
    	return dao.getNumberOfExpensesWithExpenseGroup(id);
    }
    
    public int getNumberOfExpenseItemsWithExpenseGroup(String id) {
    	return dao.getNumberOfExpenseItemsWithExpenseGroup(id);
    }
    
	public int getNumberOfIncomesWithExpenseGroup(String id) {
		return dao.getNumberOfIncomesWithExpenseGroup(id);
	}
	
	public int getNumberOfLoansWithExpenseGroup(String id) {
		return dao.getNumberOfLoansWithExpenseGroup(id);
	}
	
	public int getNumberOfCurrencyOnHandWithExpenseGroup(String id) {
		return dao.getNumberOfCurrencyOnHandWithExpenseGroup(id);
	}
}
