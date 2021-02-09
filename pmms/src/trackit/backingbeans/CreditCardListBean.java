/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import trackit.businessobjects.BankAccount;
import trackit.businessobjects.CreditCard;
import trackit.service.impl.DataInUseException;
import trackit.util.FacesUtils;

/**
 *
 * @author Owner
 */
public class CreditCardListBean extends ListBean {        
    private List<CreditCardSummaryItem> list;
    private boolean scrollBarVisible = true;

    public List<CreditCardSummaryItem> getAccounts() {
    	if (list == null) {
    		initialize();
    	}
    	
    	return list;
    }
    
    private void initialize() {
    	list = new ArrayList<CreditCardSummaryItem>();

        List<CreditCard> creditCards =
            this.serviceLocator.getCreditCardService().getAllCreditCardsForAccount(
                FacesUtils.getSessionBean().getCurrentAccountId());

        for (CreditCard creditCard : creditCards) {
            CreditCardSummaryItem item = new CreditCardSummaryItem();
            item.setId(creditCard.getId());
            item.setBalance(creditCard.getBalance());
            item.setDescription(creditCard.getDescription());
            list.add(item);
        }


        Collections.sort(list, new DescriptionItemComparator());
        
        scrollBarVisible = list.size() > 10;
        
    }

    public String newAction() {
        return NavigationResults.CREATE_CREDIT_CARD_REQUESTED;
    }

    public String homeAction() {
        return NavigationResults.HOME;
    }

    public void accountSummaryAction(ActionEvent evt) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        String idstr = (String) ctx.getExternalContext().getRequestParameterMap().get("modelRow");
        FacesUtils.getSessionBean().setCurrentCreditCardId(idstr);
    }

	@Override
	public void deleteItem(IListItem item) throws DataInUseException {
		this.serviceLocator.getCreditCardTransactionalService().setServiceLocator(this.serviceLocator);
		this.serviceLocator.getCreditCardTransactionalService().deleteCreditCard(String.valueOf(item.getId()));
		
	}

	@Override
	public void editItem(IListItem item) {
		CreditCard p = this.serviceLocator.getCreditCardService().findCreditCard(
				String.valueOf(item.getId()));
		
		CreditCardBean eb = (CreditCardBean) FacesUtils.getManagedBean("creditCardBean");			
		if (eb != null) {
			eb.setServiceLocator(this.getServiceLocator());
			eb.edit(p);
		}
		
	}

	@Override
	public String getEditNavigationId() {
		return NavigationResults.CREATE_CREDIT_CARD_REQUESTED;
	}

	@Override
	public String getListNavigaionId() {
		return NavigationResults.CREDIT_CARD_SUMMARY;
	}

	@Override
	public void reset() {
		list = null;
		FacesUtils.getSessionBean().resetCreditCardInfo();
	}
	

}
