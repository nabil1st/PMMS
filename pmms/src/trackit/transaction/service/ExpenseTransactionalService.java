/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.transaction.service;

import java.util.List;
import trackit.backingbeans.ExpenseBean;
import trackit.backingbeans.ExpenseGroupBean;
import trackit.backingbeans.ExpenseItemBean;
import trackit.backingbeans.ExpenseTypeBean;
import trackit.backingbeans.ItemizedExpenseBean;
import trackit.backingbeans.PayeeBean;
import trackit.service.impl.DataInUseException;
import trackit.servicelocator.ServiceLocator;

/**
 *
 * @author ndaoud
 */
public interface ExpenseTransactionalService {
    public void saveExpense(ExpenseBean expense);
    public void setServiceLocator(ServiceLocator locator);
    public void saveExpenseGroup(ExpenseGroupBean expenseGroup);
    public void saveItemizedExpense(List<ExpenseItemBean> expenseItems);
    public void saveItemizedExpense(ItemizedExpenseBean itemizedExpenseBean) throws DataInUseException;
    public void saveExpenseType(ExpenseTypeBean expenseType);
    public void savePayee(PayeeBean payeeBean);

    public void deleteExpense(String id) throws DataInUseException;
    public void deleteExpenseGroup(final String expenseGroupId) throws DataInUseException;
    
    public void verifyExpenseItemCanBeDeleted(String expenseItemId) throws DataInUseException;
}
