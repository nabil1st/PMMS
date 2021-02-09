/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.richfaces.component.html.HtmlPanelBar;
import org.richfaces.component.html.HtmlPanelBarItem;


/**
 *
 * @author Owner
 */
public class MainPageBean extends BaseBean {

    private String selectedTabId;
    private String selectedExpensesPanelId;
    private String selectedExpensesTabId;
    private String selectedBankTransactionsTabId;

    private HtmlPanelBar expensesPanelBar;

    public MainPageBean() {
    }

    public String getSelectedExpensesPanelId() {
        return selectedExpensesPanelId;
    }

    public void setSelectedExpensesPanelId(String selectedPanelId) {
        this.selectedExpensesPanelId = selectedPanelId;
    }

    public String getSelectedTabId() {
        return selectedTabId;
    }

    public void setSelectedTabId(String selectedTabId) {
        this.selectedTabId = selectedTabId;
    }

    public void expensePanelChanged(ValueChangeEvent e) {
        this.selectedExpensesPanelId = e.getNewValue().toString();
    }

    public HtmlPanelBar getExpensesPanelBar() {
        return expensesPanelBar;
    }

    public void setExpensesPanelBar(HtmlPanelBar expensesPanelBar) {
        this.expensesPanelBar = expensesPanelBar;
    }

    public void expensesPanelClicked() {
        HtmlPanelBarItem  item = (HtmlPanelBarItem) this.expensesPanelBar.getSelectedPanel();
        selectedExpensesPanelId = item.getId();
    }

    public String getSelectedExpensesTabId() {
        return selectedExpensesTabId;
    }

    public void setSelectedExpensesTabId(String selectedExpensesTabId) {
        this.selectedExpensesTabId = selectedExpensesTabId;
    }

    public String getSelectedBankTransactionsTabId() {
        return selectedBankTransactionsTabId;
    }

    public void setSelectedBankTransactionsTabId(String selectedBankTransactionsTabId) {
        this.selectedBankTransactionsTabId = selectedBankTransactionsTabId;
    }




}
