/*
 * JCatalog Project
 */
package trackit.builder;

import org.apache.commons.beanutils.BeanUtils;
//
import trackit.backingbeans.AccountBean;
import trackit.backingbeans.CashTransferBean;
import trackit.backingbeans.CashTransferItem;
import trackit.backingbeans.CashTransferListBean;
import trackit.backingbeans.UserBean;
import trackit.businessobjects.Account;
import trackit.businessobjects.CashTransfer;
import trackit.businessobjects.User;
import trackit.exception.EntityException;

/**
 * The builder class for <code>UserBean</code>.
 * <p>
 * The backing beans are used view objects in the presentation tier,
 * and the business objects are used in the business logic tier.
 * This builder class is used to convert in between the backing beans and business objects.
 * <p>
 * Commons BeanUtils is used.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 * @see <a href="http://jakarta.apache.org/commons/beanutils/">Commons BeanUtils</a>
 */
public class CashTransferBuilder {

    /**
     * Create a new <code>UserBean</code> based on a <code>User</code> business object.
     *
     * @param user the user business object
     * @return the new user backing bean
     * @throws CatalogException
     */
    public static CashTransferBean createCashTransferBean(CashTransfer transfer) throws EntityException {
    	CashTransferBean cashTransferBean = new CashTransferBean();

        try {
            BeanUtils.copyProperties(cashTransferBean, transfer);
        } catch (Exception e) {
            throw new EntityException("Could not build UserBean " + e.toString(), e);
        }

        return cashTransferBean;
    }

    public static CashTransfer createCashTransfer(CashTransferBean cashTransferBean) throws EntityException {
        CashTransfer cashTransfer = new CashTransfer();

        try {
            BeanUtils.copyProperties(cashTransfer, cashTransferBean);
        } catch (Exception e) {
            throw new EntityException("Could not build UserBean " + e.toString(), e);
        }
        
        if (cashTransfer.getSourceId() == -1) {
        	cashTransfer.setSourceId(null);
        }
        
        if (cashTransfer.getDestinationId() == -1) {
        	cashTransfer.setDestinationId(null);
        }

        return cashTransfer;
    }
}