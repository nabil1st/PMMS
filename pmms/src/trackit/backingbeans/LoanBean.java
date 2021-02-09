package trackit.backingbeans;

import java.util.List;
import java.util.Map;
import javax.faces.model.SelectItem;

import trackit.businessobjects.Entity;
import trackit.businessobjects.Payee;
import trackit.exception.EntityException;
import trackit.util.FacesUtils;
import trackit.util.ListUtils;

/**
 *
 * @author Nabil Daoud
 */
public class LoanBean extends GenericPaymentEnabledBean {
    
    private Long borrowerId;
    private Long payeeId;
    private Long groupId;
    private String description;

    private Long userId;
        
    private Map<Long, Payee> payeeLookup;

    public LoanBean(){}

    public Long getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(Long payeeId) {
        this.payeeId = payeeId;
    }

    public String getPayeeName() {
        if (payeeLookup != null && payeeId != null) {
            Payee payee = payeeLookup.get(payeeId);
            if (payee != null) {
                return payee.getDescription();
            }
        }
        return "";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public List<SelectItem> getBorrowerList() {
        return ListUtils.getBorrowersList(this.serviceLocator, true);
    }

    public List<SelectItem> getPayeeList() {
    	return ListUtils.getPayeeList(this.serviceLocator, true);        
    }
    
    public List<SelectItem> getExpenseGroupList() {
        return ListUtils.getExpenseGroupList(serviceLocator, true);

    }

    public void clearAllFields() {
        super.clearAllFields();
        this.description = null;
        this.payeeId = null;
        this.borrowerId = null;
    }

    public String addExpenseGroupAction() throws EntityException {
        FacesUtils.getSessionBean().setFollowOnAction(
                NavigationResults.CREATE_LOAN_REQUESTED);
        return NavigationResults.CREATE_EXPENSE_GROUP_REQUESTED;
    }

    public String addExpenseTypeAction() throws EntityException {
        FacesUtils.getSessionBean().setFollowOnAction(
                NavigationResults.CREATE_LOAN_REQUESTED);
        return NavigationResults.CREATE_EXPENSE_TYPE_REQUESTED;
    }

    public String addPayeeAction() throws EntityException {
        FacesUtils.getSessionBean().setFollowOnAction(
                NavigationResults.CREATE_LOAN_REQUESTED);
        return NavigationResults.CREATE_PAYEE_REQUESTED;
    }

    public String addBorrowerAction() throws EntityException {
        FacesUtils.getSessionBean().setFollowOnAction(
                NavigationResults.CREATE_LOAN_REQUESTED);
        return NavigationResults.CREATE_BORROWER_REQUESTED;
    }

    @Override
    public String doCancel() {
        clearAllFields();
        return NavigationResults.CREATE_LOAN_CANCELLED;
    }

	@Override
	public String createIt() throws Exception {
		this.serviceLocator.getLoanTransactionalService().setServiceLocator(serviceLocator);
        try {
            this.serviceLocator.getLoanTransactionalService().saveLoan(this);
        } catch (Exception ex) {
            throw new Exception("Loan was not created");            
        }

        clearAllFields();

        return NavigationResults.LOAN_CREATED;
	}
	
	@Override
	public String getListBeanName() {
		return null;
	}

	@Override
	public void validate() throws Exception {
		super.validate();
		
		if (ListUtils.isNullSelection(borrowerId)) {
			throw new Exception("Borrower is a required field");
		}
	}
	
	@Override
	public String getFollowOnAction() {
		return NavigationResults.CREATE_LOAN_REQUESTED;
	}

	@Override
	public void editIt(Entity entity) {
		// TODO Auto-generated method stub
		
	}
}
