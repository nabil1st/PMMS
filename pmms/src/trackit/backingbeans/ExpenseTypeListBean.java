/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import trackit.businessobjects.ExpenseType;
import trackit.util.FacesUtils;

/**
 *
 * @author Owner
 */
public class ExpenseTypeListBean extends BaseBean implements Serializable{
    private List<ExpenseType> expenseTypeList;
    private ExpenseType selectedExpenseType;
    private Set<Integer> rowsToUpdate = new HashSet<Integer>();

    @PostConstruct
    public void init() {
        FacesUtils.getSessionBean().setServiceLocator(this.serviceLocator);

        expenseTypeList =
            this.serviceLocator.getExpenseTypeService().getAllExpenseTypesForAccount(
                FacesUtils.getSessionBean().getCurrentAccountId());
        
        Collections.sort(expenseTypeList, new ExpenseTypeComparator());
    }

    public List<ExpenseType> getExpenseTypes() {
//        List<ExpenseType> ExpenseTypes =
//            this.serviceLocator.getExpenseTypeService().getAllExpenseTypesForAccount(
//                FacesUtils.getSessionBean().getCurrentAccountId());
//        List<ExpenseTypeBean> expenseTypeBeans = new ArrayList<ExpenseTypeBean>();
//        for (ExpenseType ba : ExpenseTypes) {
//            try {
//                ExpenseTypeBean bean = ExpenseTypeBuilder.createExpenseTypeBean(ba);
//                expenseTypeBeans.add(bean);
//
//            } catch (EntityException ex) {
//                Logger.getLogger(BankAccountListBean.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//
//        accounts = expenseTypeBeans;
//
//        return expenseTypeBeans;

        return expenseTypeList;

    }

    public void setExpenseTypes(List<ExpenseType> accounts) {
        this.expenseTypeList = accounts;
    }

    public String newAction() {
        return NavigationResults.CREATE_EXPENSE_TYPE_REQUESTED;
    }

    public String homeAction() {
        return NavigationResults.HOME;
    }

    public String expenseSummaryAction() {
        return NavigationResults.SHOW_EXPENSES;
    }

    public ExpenseType getSelectedExpenseType() {
        return selectedExpenseType;
    }

    public void setSelectedExpenseType(ExpenseType selectedExpenseType) {
        this.selectedExpenseType = selectedExpenseType;
    }

    public Set<Integer> getRowsToUpdate() {
        return rowsToUpdate;
    }

    public void save (ActionEvent event) {
        rowsToUpdate.clear();
        rowsToUpdate.add(expenseTypeList.indexOf(selectedExpenseType));

        if (FacesUtils.getSessionBean().getServiceLocator() != null) {
            FacesUtils.getSessionBean().getServiceLocator().getExpenseTypeService().saveExpenseType(selectedExpenseType);
        }
    }


    

}
