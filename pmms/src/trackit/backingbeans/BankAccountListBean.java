/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import trackit.businessobjects.BankAccount;
import trackit.businessobjects.Payee;
import trackit.service.impl.DataInUseException;
import trackit.util.FacesUtils;

/**
 *
 * @author Owner
 */
public class BankAccountListBean extends ListBean {
        
    private List<BankAccountSummaryItem> bankAccountList;
    
    private boolean scrollBarVisible = true;

    @PostConstruct
    public void init() {
        FacesUtils.getSessionBean().setServiceLocator(this.serviceLocator);

        bankAccountList = new ArrayList<BankAccountSummaryItem>();

        List<BankAccount> bankAccounts =
            this.serviceLocator.getBankAccountService().getAllBankAccountsForAccount(
                    FacesUtils.getSessionBean().getCurrentAccountId());

        for (BankAccount bankAccount : bankAccounts) {
            BankAccountSummaryItem item = new BankAccountSummaryItem();
            item.setId(bankAccount.getId());
            item.setDescription(bankAccount.getDescription());
            item.setBalance(bankAccount.getBalance());
            item.setType(bankAccount.getAccountTypeId() == 1?"Checking":"Saving");

            bankAccountList.add(item);
        }

        Collections.sort(bankAccountList, new DescriptionItemComparator());
        
        scrollBarVisible = bankAccountList.size() > 10;
    }

    public List<BankAccountSummaryItem> getAccounts() {
    	if (bankAccountList == null) {
    		init();
    	}
    	
        return bankAccountList;
    }

    
    public String newAction() {
        return NavigationResults.CREATE_BANK_ACCOUNT_REQUESTED;
    }

    public String homeAction() {
        return NavigationResults.HOME;
    }    

    public void accountSummaryAction(ActionEvent evt) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        String idstr = (String) ctx.getExternalContext().getRequestParameterMap().get("modelRow");
        //int id = Integer.parseInt(idstr);
        //BankAccountBean bean = bankAccounts.get(modelRow);
        FacesUtils.getSessionBean().setCurrentBankAccountId(idstr);
    }

	@Override
	public void deleteItem(IListItem item) throws DataInUseException {
		this.serviceLocator.getBankAccountTransactionalService().setServiceLocator(this.serviceLocator);
		this.serviceLocator.getBankAccountTransactionalService().deleteBankAccount(String.valueOf(item.getId()));
	}

	@Override
	public void editItem(IListItem item) {
		BankAccount p = this.serviceLocator.getBankAccountService().findBankAccount(
				String.valueOf(item.getId()));
		
		BankAccountBean eb = (BankAccountBean) FacesUtils.getManagedBean("bankAccountBean");			
		if (eb != null) {
			eb.setServiceLocator(this.getServiceLocator());
			eb.edit(p);
		}
	}

	@Override
	public String getEditNavigationId() {
		return NavigationResults.CREATE_BANK_ACCOUNT_REQUESTED;
	}

	@Override
	public String getListNavigaionId() {
		return NavigationResults.BANK_ACCOUNTS_SUMMARY;
	}

	@Override
	public void reset() {
		bankAccountList = null;	
		FacesUtils.getSessionBean().resetBankAccountInfo();
	}
	

}
