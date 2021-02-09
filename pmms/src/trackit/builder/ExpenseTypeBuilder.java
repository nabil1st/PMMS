/*
 * JCatalog Project
 */
package trackit.builder;

import org.apache.commons.beanutils.BeanUtils;
//
import trackit.backingbeans.ExpenseTypeBean;
import trackit.businessobjects.ExpenseType;
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
public class ExpenseTypeBuilder {

    /**
     * Create a new <code>UserBean</code> based on a <code>User</code> business object.
     *
     * @param user the user business object
     * @return the new user backing bean
     * @throws CatalogException
     */
    public static ExpenseTypeBean createExpenseTypeBean(ExpenseType account) throws EntityException {
        ExpenseTypeBean accountBean = new ExpenseTypeBean();

        try {
            BeanUtils.copyProperties(accountBean, account);
        } catch (Exception e) {
            throw new EntityException("Could not build ExpenseTypeBean " + e.toString(), e);
        }

        return accountBean;
    }

    public static ExpenseType createExpenseType(ExpenseTypeBean accountBean) throws EntityException {
        ExpenseType account = new ExpenseType();

        try {
            BeanUtils.copyProperties(account, accountBean);
        } catch (Exception e) {
            throw new EntityException("Could not build ExpenseType " + e.toString(), e);
        }

        return account;
    }
}