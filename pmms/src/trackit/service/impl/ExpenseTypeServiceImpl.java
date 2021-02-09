/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trackit.service.impl;

import java.util.List;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import trackit.businessobjects.ExpenseType;
import trackit.dao.ExpenseTypeDao;
import trackit.service.ExpenseTypeService;

/**
 *
 * @author Owner
 */
public class ExpenseTypeServiceImpl implements ExpenseTypeService {

    private ExpenseTypeDao dao;

    public void setExpenseTypeDao(ExpenseTypeDao dao) {
        this.dao = dao;
    }

    public ExpenseType saveExpenseType(ExpenseType a) {
        ExpenseType savedBankAccount = dao.saveExpenseType(a);
        return savedBankAccount;
    }

    public ExpenseType findExpenseType(String id) {
        return dao.getExpenseType(id);
    }

    public ExpenseType findExpenseTypeByDescription(String description) {
        try {
            List list = this.dao.findExpenseTypeByDescription(description);
            if (list.size() == 0) {
                return null;
            }

            return (ExpenseType) list.get(0);
        } catch (HibernateObjectRetrievalFailureException e) {
            return null;
        }
    }

    public List<ExpenseType> getAllExpenseTypesForAccount(String accountId) {
        return dao.getAllExpenseTypesForAccount(accountId);
    }
}
