package trackit.backingbeans;

import java.util.List;

import trackit.businessobjects.Entity;
import trackit.businessobjects.Payee;
import trackit.util.FacesUtils;

/**
 *
 * @author Owner
 */
public class PayeeBean extends GenericEntityBean {
    
    private String description;

    
    public PayeeBean() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String doCreateIt() throws Exception {        

        this.serviceLocator.getExpenseTransactionalService().setServiceLocator(serviceLocator);
        try {
            this.serviceLocator.getExpenseTransactionalService().savePayee(this);
        } catch (Exception ex) {
            throw new Exception("Payee was not created");            
        }

        if (FacesUtils.getSessionBean().getFollowOnAction() != null) {
            String followOnAction = FacesUtils.getSessionBean().getFollowOnAction();
            FacesUtils.getSessionBean().setFollowOnAction(null);
            return followOnAction;
        }

        return NavigationResults.PAYEE_CREATED;
    }
    
    @Override
    public String getListBeanName() {
    	return "payeeListBean";
    }

    @Override
    public String doCancel() {

        if (FacesUtils.getSessionBean().getFollowOnAction() != null) {
            String followOnAction = FacesUtils.getSessionBean().getFollowOnAction();
            FacesUtils.getSessionBean().setFollowOnAction(null);
            return followOnAction;
        }

        return NavigationResults.CREATE_PAYEE_CANCELLED;
    }
    
    @Override
    public void editIt(Entity entity) {
    	Payee payee = (Payee) entity;    	
    	this.description = payee.getDescription();    	
    }
    
    public String getTitle() {
    	if (editMode) {
    		return "Edit Payee";
    	} else {
    		return "New Payee";
    	}
    }

	@Override
	public void validate() throws Exception {
		List<Payee> availablePayees =
            this.serviceLocator.getPayeeService().getAllPayeesForAccount(
                FacesUtils.getSessionBean().getCurrentAccountId());

		for (Payee ba : availablePayees) {
			if (ba.getDescription().equals(description)) {
				throw new Exception("A payee with the same name already exists");				
			}
		}
		
	}
}
