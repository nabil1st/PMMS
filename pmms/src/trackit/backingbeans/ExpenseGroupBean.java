/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trackit.backingbeans;

import java.util.List;
import trackit.businessobjects.ExpenseGroup;
import trackit.exception.EntityException;
import trackit.util.FacesUtils;

/**
 *
 * @author Owner
 */
public class ExpenseGroupBean extends BaseBean {

	private boolean editMode = false;
    private Long id;
    private String description;

    private boolean selected;

    public ExpenseGroupBean() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String createAction() {

            List<ExpenseGroup> availableGroups =
                    this.serviceLocator.getExpenseGroupService().getAllExpenseGroupsForAccount(
                        FacesUtils.getSessionBean().getCurrentAccountId());

            for (ExpenseGroup ba : availableGroups) {
                if (ba.getDescription().equals(description)) {
                    String msg = "A project with the same description already exists";
                    FacesUtils.addErrorMessage(msg + ", please try again.");
                    this.logger.debug(msg);
                    return null;
                }
            }


            this.serviceLocator.getExpenseTransactionalService().setServiceLocator(serviceLocator);

            try {
                this.serviceLocator.getExpenseTransactionalService().saveExpenseGroup(this);
            } catch (Exception ex) {
                String msg = "The project was not created";
                FacesUtils.addErrorMessage(msg + ", please try again.");
                this.logger.debug(msg);
                return "";
            }
            
            ExpenseGroupListBean eb = (ExpenseGroupListBean) FacesUtils.getManagedBean("expenseGroupListBean");			
    		if (eb != null) {
    			eb.reset();
    		}

            if (FacesUtils.getSessionBean().getFollowOnAction() != null) {
                String followOnAction = FacesUtils.getSessionBean().getFollowOnAction();
                FacesUtils.getSessionBean().setFollowOnAction(null);
                return followOnAction;
            }

            return NavigationResults.EXPENSE_GROUP_CREATED;

    }

    public String cancelAction() throws EntityException {
    	clearAllFields();

        if (FacesUtils.getSessionBean().getFollowOnAction() != null) {
            String followOnAction = FacesUtils.getSessionBean().getFollowOnAction();
            FacesUtils.getSessionBean().setFollowOnAction(null);
            return followOnAction;
        }

        return NavigationResults.CREATE_EXPENSE_GROUP_CANCELLED;
    }

    private void clearAllFields() {
    	editMode = false;
        this.id = null;
        this.description = null;        
    }

	public void editExpenseGroup(ExpenseGroup eg) {
		this.editMode = true;
		this.id = eg.getId();
		this.description = eg.getDescription();
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}
	
	public String getTitle() {
		if (editMode) {
			return "Edit Project";
		} else {
			return "New Project";
		}
	}
	
}
