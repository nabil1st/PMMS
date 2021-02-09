/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trackit.backingbeans;

import java.util.List;
import trackit.businessobjects.IncomeSource;
import trackit.exception.EntityException;
import trackit.util.FacesUtils;

/**
 *
 * @author Owner
 */
public class IncomeSourceBean extends BaseBean {

    private Long id;
    private String name;

    public IncomeSourceBean() {
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
//        try {

            // Validate that the user name does not already exist

            List<IncomeSource> availableIncomeSources =
                    this.serviceLocator.getIncomeService().getAllIncomeSourcesForAccount(
                        FacesUtils.getSessionBean().getCurrentAccountId());

            for (IncomeSource ba : availableIncomeSources) {
                if (ba.getName().equals(name)) {
                    String msg = "Income source with same description already exists";
                    FacesUtils.addErrorMessage(msg + ", please try again.");
                    this.logger.debug(msg);
                    return null;
                }
            }

//            IncomeSource ba = IncomeSourceBuilder.createIncomeSource(this);
//            ba = this.serviceLocator.getIncomeService().saveIncomeSource(ba);
//
//            Account account = this.serviceLocator.
//                    getAccountService().findAccount(
//                        FacesUtils.getSessionBean().getCurrentAccountId());
//            Set<IncomeSource> incomeSources = account.getIncomeSources();
//            if (incomeSources == null) {
//                incomeSources = new HashSet<IncomeSource>();
//            }
//            incomeSources.add(ba);
//            account.setIncomeSources(incomeSources);
//
//            this.serviceLocator.getAccountService().saveAccount(account);

            this.serviceLocator.getIncomeTransactionalService().setServiceLocator(serviceLocator);
            try {
                this.serviceLocator.getIncomeTransactionalService().saveIncomeSource(this);
            } catch (Exception ex) {
                String msg = "Income source was not created";
                FacesUtils.addErrorMessage(msg + ", please try again.");
                this.logger.debug(msg);
                return "";
            }

            FacesUtils.getSessionBean().resetIncomeSourceInfo();
            
            if (FacesUtils.getSessionBean().getFollowOnAction() != null) {
                String followOnAction = FacesUtils.getSessionBean().getFollowOnAction();
                FacesUtils.getSessionBean().setFollowOnAction(null);
                return followOnAction;
            }
            
            return NavigationResults.INCOME_SOURCE_CREATED;

//        } catch (EntityException ex) {
//            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return null;
    }


    public String cancelAction() throws EntityException {
        clearAllFields();

        if (FacesUtils.getSessionBean().getFollowOnAction() != null) {
            String followOnAction = FacesUtils.getSessionBean().getFollowOnAction();
            FacesUtils.getSessionBean().setFollowOnAction(null);
            return followOnAction;
        }
        
        return NavigationResults.CREATE_INCOME_SOURCE_CANCELLED;
    }

    private void clearAllFields() {
        this.id = null;
        this.name = null;
    }
}
