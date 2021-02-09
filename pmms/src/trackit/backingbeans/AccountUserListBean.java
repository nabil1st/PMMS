/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import trackit.builder.BankAccountBuilder;
import trackit.builder.UserBuilder;
import trackit.businessobjects.BankAccount;
import trackit.businessobjects.User;
import trackit.exception.EntityException;
import trackit.util.FacesUtils;

/**
 *
 * @author Owner
 */
public class AccountUserListBean extends ListBean {
    private List<UserBean> users;
    private BigDecimal totalUsersCash;

    public List<UserBean> getUsers() {
    	if (users == null) {
    		createUsersList();
    	}
        return users;
    }
    
    private void createUsersList() {
    	totalUsersCash = new BigDecimal(0);
    	List<User> usersList =
            this.serviceLocator.getUserService().getAllUsersForAccount(
                FacesUtils.getSessionBean().getCurrentAccountId());

        Collections.sort(usersList, new Comparator<User>() {
            public int compare(User o1, User o2) {
                String name1 = o1.getLastName() + ", " + o1.getFirstName();
                String name2 = o2.getLastName() + ", " + o2.getFirstName();

                return name1.compareTo(name2);
            }
        });
        users = new ArrayList<UserBean>();
        for (User u : usersList) {
            try {
                UserBean bean = UserBuilder.createUserBean(u);
                users.add(bean);
                totalUsersCash = totalUsersCash.add(u.getCashBalance());
            } catch (EntityException ex) {
                Logger.getLogger(BankAccountListBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setUsers(List<UserBean> users) {
        this.users = users;
    }

    public String newAction() {
    	FacesUtils.getSessionBean().setCreateNewUser(true);
        return NavigationResults.CREATE_SUB_USER_REQUESTED;
    }
    
    public String transferCashAction() {
    	return NavigationResults.TRANSFER_CASH_REQUESTED;
    }

    public String homeAction() {
        return NavigationResults.HOME;
    }

    public String userSummaryAction() {
        return NavigationResults.SHOW_USER_SUMMARY;
    }

    public void accountSummaryAction(ActionEvent evt) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        String idstr = (String) ctx.getExternalContext().getRequestParameterMap().get("modelRow");
        //int id = Integer.parseInt(idstr);
        //BankAccountBean bean = bankAccounts.get(modelRow);
        FacesUtils.getSessionBean().setSelectedUserId(idstr);
    }

	public BigDecimal getTotalUsersCash() {
		return totalUsersCash;
	}

	public void setTotalUsersCash(BigDecimal totalUsersCash) {
		this.totalUsersCash = totalUsersCash;
	}

	@Override
	public void deleteItem(IListItem item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editItem(IListItem item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getEditNavigationId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getListNavigaionId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
