/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trackit.backingbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import trackit.businessobjects.ExpenseCategory;
import trackit.businessobjects.ExpenseSubType;
import trackit.businessobjects.ExpenseType;
import trackit.util.FacesUtils;

/**
 *
 * @author Owner
 */
public class ExpenseTypeBean extends BaseBean implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
    private String description;
    private String categoryDescription;
    private Long selectedCategoryId;
    private Long selectedTypeId;

    private boolean selected;

    public ExpenseTypeBean() {
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

    public String createAction(ActionEvent event) {
        return createAction();
    }
    
    

    public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	public Long getSelectedCategoryId() {
		return selectedCategoryId;
	}

	public void setSelectedCategoryId(Long selectedCategoryId) {
		this.selectedCategoryId = selectedCategoryId;
	}	
	
	
	public Long getSelectedTypeId() {
		return selectedTypeId;
	}

	public void setSelectedTypeId(Long selectedTypeId) {
		this.selectedTypeId = selectedTypeId;
	}

	public String addCategoryAction() {
		List<ExpenseCategory> ets = this.serviceLocator.getExpenseCategoryService().getAllExpenseCategoriesForAccount(
				FacesUtils.getSessionBean().getCurrentAccountId());
		
		if (categoryDescription == null || categoryDescription.trim().length() == 0) {
			String msg = "Category name not entered.";
	        FacesUtils.addErrorMessage(msg + ", please try again.");
	        this.logger.debug(msg);
	        return null;
		}
		
		for (ExpenseCategory ec : ets) {
			if (ec.getDescription().equals(categoryDescription)) {
				String msg = "Category " + categoryDescription + " already exists";
		        FacesUtils.addErrorMessage(msg + ", please try again.");
		        this.logger.debug(msg);
		        return null;
			}
		}
		
		ExpenseCategory newOne = new ExpenseCategory();
		newOne.setDescription(categoryDescription);
		newOne.setAccountId(new Long(FacesUtils.getSessionBean().getCurrentAccountId()));
		
		newOne.setSubTypes(new ArrayList<ExpenseSubType>());
		
		newOne = this.serviceLocator.getExpenseCategoryService().saveExpenseCategory(newOne);
		
		this.selectedCategoryId = newOne.getId();
		
		this.categoryDescription = null;
		
		FacesUtils.getSessionBean().resetExpenseCategoryTypeInfo();

        return null;
		
	}
	
	public String addTypeAction() {
		if (description == null || description.trim().length() == 0) {
			String msg = "An expense subtype had not been entered";
	        FacesUtils.addErrorMessage(msg);
	        this.logger.debug(msg);
	        return null;
		}
		
		// get the selected category
		if (this.selectedCategoryId == null || this.selectedCategoryId <= 0) {
			String msg = "An expense category must be selected";
	        FacesUtils.addErrorMessage(msg);
	        this.logger.debug(msg);
	        return null;
		}
		
		ExpenseCategory cat = this.serviceLocator.getExpenseCategoryService().findExpenseCategory(
				selectedCategoryId.toString());
		if (cat == null) {
			String msg = "Error loading category with ID " + selectedCategoryId.toString();
	        FacesUtils.addErrorMessage(msg);
	        this.logger.debug(msg);
	        return null;
		}
		
		ExpenseSubType subType = new ExpenseSubType();
		subType.setCategoryId(selectedCategoryId);
		subType.setDescription(description);
		subType.setAccountId(new Long(FacesUtils.getSessionBean().getCurrentAccountId()));
		
		//cat.getSubTypes().add(subType);
		this.serviceLocator.getExpenseCategoryService().saveExpenseSubType(subType);
		
		this.description = null;
		FacesUtils.getSessionBean().resetExpenseCategoryTypeInfo();
		
		return null;
	}
	
	public String doneAction() {
		if (FacesUtils.getSessionBean().getFollowOnAction() != null) {
            String followOnAction = FacesUtils.getSessionBean().getFollowOnAction();
            FacesUtils.getSessionBean().setFollowOnAction(null);
            return followOnAction;
        }

        return NavigationResults.EXPENSE_TYPE_CREATED;
	}

	public String createAction() {
//        try {

            // Validate that the user name does not already exist

            List<ExpenseType> availableGroups =
                    this.serviceLocator.getExpenseTypeService().getAllExpenseTypesForAccount(
                        FacesUtils.getSessionBean().getCurrentAccountId());

            for (ExpenseType ba : availableGroups) {
                if (ba.getDescription().equals(description)) {
                    String msg = "Expense Type with same description already exists";
                    FacesUtils.addErrorMessage(msg + ", please try again.");
                    this.logger.debug(msg);
                    return null;
                }
            }

//            ExpenseType ba = ExpenseTypeBuilder.createExpenseType(this);
//            ba = this.serviceLocator.getExpenseTypeService().saveExpenseType(ba);
//
//            Account account = this.serviceLocator.
//                    getAccountService().findAccount(
//                        FacesUtils.getSessionBean().getCurrentAccountId());
//            Set<ExpenseType> ExpenseTypes = account.getExpenseTypes();
//            if (ExpenseTypes == null) {
//                ExpenseTypes = new HashSet<ExpenseType>();
//            }
//            ExpenseTypes.add(ba);
//            account.setExpenseTypes(ExpenseTypes);
//
//            this.serviceLocator.getAccountService().saveAccount(account);

            this.serviceLocator.getExpenseTransactionalService().setServiceLocator(serviceLocator);
            try {
                this.serviceLocator.getExpenseTransactionalService().saveExpenseType(this);
            } catch (Exception ex) {
                String msg = "Expense Type was not created";
                FacesUtils.addErrorMessage(msg + ", please try again.");
                this.logger.debug(msg);
                return "";
            }

            if (FacesUtils.getSessionBean().getFollowOnAction() != null) {
                String followOnAction = FacesUtils.getSessionBean().getFollowOnAction();
                FacesUtils.getSessionBean().setFollowOnAction(null);
                return followOnAction;
            }

            return NavigationResults.EXPENSE_TYPE_CREATED;

//        } catch (EntityException ex) {
//            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return null;
    }
	
	public List<SelectItem> getExpenseCategoryList() {
		List<ExpenseCategory> ets = this.serviceLocator.getExpenseCategoryService().getAllExpenseCategoriesForAccount(
				FacesUtils.getSessionBean().getCurrentAccountId());
		List<SelectItem> dropList = new ArrayList<SelectItem>();
		for (ExpenseCategory et : ets) {
		    dropList.add(new SelectItem(et.getId(), et.getDescription()));
		}
		
		if (dropList.size() == 0) {
			dropList.add(new SelectItem(-1, "No categories..."));
		}
		return dropList;
	}
	
	public List<SelectItem> getExpenseTypeList() {
		List<ExpenseCategory> ets = this.serviceLocator.getExpenseCategoryService().getAllExpenseCategoriesForAccount(
				FacesUtils.getSessionBean().getCurrentAccountId());
		List<SelectItem> dropList = new ArrayList<SelectItem>();
		for (ExpenseCategory et : ets) {
		    if (et.getId().equals(selectedCategoryId)) {		    	
		    	for (ExpenseSubType xt : et.getSubTypes()) {
		    		dropList.add(new SelectItem(xt.getId(), xt.getDescription()));
		    	}
		    	
		    	break;
		    }
		}
		
		if (dropList.size() == 0) {
			dropList.add(new SelectItem(-1, "No subtypes..."));
		}
		return dropList;
	}
	
	public void categoryChanged(ValueChangeEvent event) {
		selectedCategoryId = (Long) event.getNewValue();
	}
}
