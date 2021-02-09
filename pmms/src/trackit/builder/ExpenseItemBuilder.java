/*
 * JCatalog Project
 */
package trackit.builder;

import org.apache.commons.beanutils.BeanUtils;
//
import trackit.backingbeans.ExpenseItemBean;
import trackit.businessobjects.ExpenseItem;
import trackit.exception.EntityException;

/**
 * The builder class for <code>ExpenseBean</code>.
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
public class ExpenseItemBuilder {

    /**
     * Create a new <code>ExpenseBean</code> based on a <code>Expense</code> business object.
     *
     * @param Expense the Expense business object
     * @return the new Expense backing bean
     * @throws CatalogException
     */
    public static ExpenseItemBean createExpenseItemBean(ExpenseItem expense) throws EntityException {
        ExpenseItemBean expenseItemBean = new ExpenseItemBean();

        try {
            BeanUtils.copyProperties(expenseItemBean, expense);
        } catch (Exception e) {
            throw new EntityException("Could not build ExpenseItemBean " + e.toString(), e);
        }

        return expenseItemBean;
    }

    public static ExpenseItem createExpenseItem(ExpenseItemBean expenseItemBean) throws EntityException {
        ExpenseItem expenseItem = new ExpenseItem();

        try {
            BeanUtils.copyProperties(expenseItem, expenseItemBean);
        } catch (Exception e) {
            throw new EntityException("Could not build ExpenseBean " + e.toString(), e);
        }

        return expenseItem;
    }
}