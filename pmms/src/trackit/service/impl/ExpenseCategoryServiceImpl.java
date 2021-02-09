/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trackit.service.impl;

import java.util.List;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import trackit.businessobjects.ExpenseCategory;
import trackit.businessobjects.ExpenseSubType;
import trackit.dao.ExpenseCategoryDao;
import trackit.service.ExpenseCategoryService;

/**
 *
 * @author Owner
 */
public class ExpenseCategoryServiceImpl implements ExpenseCategoryService {

    private ExpenseCategoryDao dao;

    public void setExpenseCategoryDao(ExpenseCategoryDao dao) {
        this.dao = dao;
    }

    public ExpenseCategory saveExpenseCategory(ExpenseCategory a) {
        ExpenseCategory savedBankAccount = dao.saveExpenseCategory(a);
        return savedBankAccount;
    }

    public ExpenseCategory findExpenseCategory(String id) {
        return dao.getExpenseCategory(id);
    }

    public ExpenseCategory findExpenseCategoryByDescription(String description) {
        try {
            List list = this.dao.findExpenseCategoryByDescription(description);
            if (list.size() == 0) {
                return null;
            }

            return (ExpenseCategory) list.get(0);
        } catch (HibernateObjectRetrievalFailureException e) {
            return null;
        }
    }

    public List<ExpenseCategory> getAllExpenseCategoriesForAccount(String accountId) {
        return dao.getAllExpenseCategoriesForAccount(accountId);
    }

    public ExpenseSubType saveExpenseSubType(ExpenseSubType a) {
        ExpenseSubType saved = dao.saveExpenseSubType(a);
        return saved;
    }

    public ExpenseSubType findExpenseSubType(String id) {
        return dao.getExpenseSubType(id);
    }
}
