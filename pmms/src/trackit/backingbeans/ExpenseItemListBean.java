/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import trackit.builder.ExpenseItemBuilder;
import trackit.businessobjects.Expense;
import trackit.businessobjects.ExpenseItem;
import trackit.exception.EntityException;
import trackit.util.FacesUtils;

/**
 *
 * @author Owner
 */
public class ExpenseItemListBean extends BaseBean {

    private List<ExpenseItemBean> list;
    private int rowsToAdd;

    public ExpenseItemListBean(){}

    public List<ExpenseItemBean> getExpenseItems() {
        if (list == null) {
            list = new ArrayList<ExpenseItemBean>();
            for (int i=0; i<10; i++) {
                list.add(new ExpenseItemBean());
            }
        }

        return list;
    }

    public String updateAction() {
        ExpenseBean expenseBean = FacesUtils.getSessionBean().getExpenseBean();
//        Set<ExpenseItem> expenseItems = expense.getExpenseItems();

        // Verify that the expense items total is equal to the expense amount
        BigDecimal total = new BigDecimal(0);
        for (ExpenseItemBean b : list) {
            total = total.add(b.getAmount()).add(b.getTax());
        }

        if (!total.equals(expenseBean.getAmount())) {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("Expense items total does not match expense total");
            message.setDetail("Expense items total does not match expense total");
            context.addMessage("ExpenseItems:globalMessages", message);
            return NavigationResults.EXPENSE_ITEMS_ERROR;
        }

//        for (ExpenseItemBean b : list) {
//            if (b.getAmount().doubleValue() > 0) {
//                try {
//                    ExpenseItem ei = ExpenseItemBuilder.createExpenseItem(b);
//                    this.serviceLocator.getExpenseService().saveExpenseItem(ei);
//                    expenseItems.add(ei);
//                } catch (EntityException ex) {
//                    Logger.getLogger(ExpenseItemListBean.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }

        //expense.setExpenseItems(expenseItems);
//        this.serviceLocator.getExpenseService().saveExpense(expense);

        this.serviceLocator.getExpenseTransactionalService().setServiceLocator(serviceLocator);
        try {
            this.serviceLocator.getExpenseTransactionalService().saveItemizedExpense(list);
        } catch(Exception ex) {
            String msg = "Expense Items were not created";
            FacesUtils.addErrorMessage(msg + ", please try again.");
            this.logger.debug(msg);
            return "";
        }

        FacesUtils.getSessionBean().getExpenseBean().clearAllFields();
        list = null;
        return NavigationResults.EXPENSE_ITEMS_SAVED;
    }

    public String cancelAction() {
        list = null;
        return NavigationResults.EXPENSE_ITEMS_CANCELLED;
    }

    public String addRowsAction() {
        //for (int i=0; i<rowsToAdd; i++) {
            list.add(new ExpenseItemBean());
        //}
        
        return NavigationResults.EXPENSE_ITEMS_ROWS_ADDED;
    }

    public int getRowsToAdd() {
        return rowsToAdd;
    }

    public void setRowsToAdd(int rowsToAdd) {
        this.rowsToAdd = rowsToAdd;
    }

    public List<ExpenseItemBean> getList() {
        return list;
    }

    public void setList(List<ExpenseItemBean> list) {
        this.list = list;
    }

}
