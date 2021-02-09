/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.model.SelectItem;
import trackit.businessobjects.User;
import trackit.exception.EntityException;
import trackit.util.DateFormatUtil;
import trackit.util.FacesUtils;
import trackit.util.ListUtils;

/**
 *
 * @author Nabil
 */
public class CashTransferBean extends BaseBean {
    private Long id;
    private Date transferDate = new Date();
    private String dateStr = DateFormatUtil.formatDate(transferDate);
    private BigDecimal amount;
    private Long sourceId;
    private Long destinationId;
    private String notes;
    
    private Map<Long,User> userLookup;
    
    public CashTransferBean(){}
        
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getTransferDate() {
        if (transferDate == null) {
            transferDate = new Date();
        }
        return transferDate;
    }

    public void setTransferDate(Date date) {
        this.transferDate = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public Long getSourceId() {
    	if (sourceId == null) {
    		return new Long(-1);
    	}
		return sourceId;
	}

	public void setSourceId(Long sourceAccountId) {
		if (sourceAccountId == -1) {
			this.sourceId = null;
		} else {
			this.sourceId = sourceAccountId;
		}
	}

	public Long getDestinationId() {
		if (destinationId == null) {
			return new Long(-1);
		}
		return destinationId;
	}

	public void setDestinationId(Long destinationAccountId) {
		if (destinationAccountId == -1) {
			this.destinationId = null;
		} else {
			this.destinationId = destinationAccountId;
		}
	}

	public String getSourceAccountName() {
		if (sourceId == null) {
			return "Main Cash Account";
		}
		
		User user = getUserLookup().get(sourceId);
        return user.getFirstName() + " " + user.getLastName();
    }
    
    public String getDestinationAccountName() {
    	if (destinationId == null) {
			return "Main Cash Account";
		}
		
		User user = getUserLookup().get(destinationId);
        return user.getFirstName() + " " + user.getLastName();
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    

    public List<SelectItem> getCashAccountList() {
        List<User> users = ListUtils.getUsers(this.serviceLocator.getUserService());
        
        Collections.sort(users, new Comparator<User>() {
			@Override
			public int compare(User o1, User o2) {
				return o1.getFirstLastName().compareTo(o2.getFirstLastName());
			}        	
        });
        
        List<SelectItem> dropList = new ArrayList<SelectItem>();
        
        dropList.add(new SelectItem(new Long(-1), "Main Cash Account"));
        for (User user : users) {
            dropList.add(new SelectItem(user.getId(), user.getFirstName() + " " + user.getLastName()));
        }
        return dropList;
    }
    
    
    public String createAction() throws EntityException {
    	if (amount == null || amount.doubleValue() == 0) {
    		FacesUtils.addErrorMessage("Transferred amount must be greater than 0");
            return null;
    	}
        if (sourceId == destinationId) {
            FacesUtils.addErrorMessage("Cannot transfer from an account to itself");
            return null;
        }

        this.serviceLocator.getCashTransactionalService().setServiceLocator(serviceLocator);
        try {
            this.serviceLocator.getCashTransactionalService().saveCashTransfer(this);
        } catch (Exception ex) {
            String msg = "Transfer was not created";
            FacesUtils.addErrorMessage(msg + ", please try again.");
            this.logger.debug(msg);
            return "";
        }
        
        clearAllFields();

        return NavigationResults.CASH_TRANSFERED;
    }
    
    public String cancelAction() throws EntityException {
        return NavigationResults.TRANSFER_CASH_CANCELLED;
    }

    public String clearAction() {
        clearAllFields();
        return "";
    }

    public void clearAllFields() {
        this.amount = null;
        this.transferDate = null;
        this.notes = null;
        this.sourceId = null;
        this.destinationId = null;
        this.id = null;
    }

    
    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }    
    
    private Map<Long,User> getUserLookup() {
    	if (userLookup == null) {
    		userLookup = new HashMap<Long,User>();
    		List<User> users = this.serviceLocator.getUserService().getAllUsersForAccount(
                    FacesUtils.getSessionBean().getCurrentAccountId());
    		for (User user : users) {
    			userLookup.put(user.getId(), user);
    		}
    	}
    	
    	return userLookup;
    }
}
