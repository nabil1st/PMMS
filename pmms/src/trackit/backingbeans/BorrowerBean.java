/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trackit.backingbeans;

import java.util.List;

import trackit.businessobjects.Borrower;
import trackit.util.FacesUtils;

/**
 *
 * @author Owner
 */
public class BorrowerBean extends BaseBean {

    private Long id;
    private String name;


    public BorrowerBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String createAction() {


            // Validate that the user name does not already exist

            List<Borrower> availableBorrowers =
                    this.serviceLocator.getBorrowerService().getAllBorrowersForAccount(
                        FacesUtils.getSessionBean().getCurrentAccountId());

            for (Borrower ba : availableBorrowers) {
                if (ba.getName().equals(name)) {
                    String msg = "Borrower with same name already exists";
                    FacesUtils.addErrorMessage(msg + ", please try again.");
                    this.logger.debug(msg);
                    return null;
                }
            }

            this.serviceLocator.getLoanTransactionalService().setServiceLocator(serviceLocator);
            try {
                this.serviceLocator.getLoanTransactionalService().saveBorrower(this);
            } catch (Exception ex) {
                String msg = "Borrower was not created";
                FacesUtils.addErrorMessage(msg + ", please try again.");
                this.logger.debug(msg);
                return "";
            }
            
            FacesUtils.getSessionBean().resetBorrowerInfo();

            if (FacesUtils.getSessionBean().getFollowOnAction() != null) {
                String followOnAction = FacesUtils.getSessionBean().getFollowOnAction();
                FacesUtils.getSessionBean().setFollowOnAction(null);
                return followOnAction;
            }

            // clear the bean contents
            setId(null);
            setName(null);

            return NavigationResults.BORROWER_CREATED;


    }

    public String cancelAction() {
        setId(null);
        setName(null);
        return NavigationResults.SHOW_BORROWERS;
    }
}
