/*
 * JCatalog Project
 */
package trackit.builder;

import org.apache.commons.beanutils.BeanUtils;
//
import trackit.backingbeans.ExpenseBean;
import trackit.backingbeans.ItemizedExpenseBean;
import trackit.businessobjects.Expense;
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
public class ExpenseBuilder {

    /**
     * Create a new <code>ExpenseBean</code> based on a <code>Expense</code> business object.
     *
     * @param Expense the Expense business object
     * @return the new Expense backing bean
     * @throws CatalogException
     */
    public static ExpenseBean createExpenseBean(Expense Expense) throws EntityException {
        ExpenseBean ExpenseBean = new ExpenseBean();

        try {
            BeanUtils.copyProperties(ExpenseBean, Expense);
        } catch (Exception e) {
            throw new EntityException("Could not build ExpenseBean " + e.toString(), e);
        }

        ExpenseBean.setPaymentMethod(Expense.getPaymentMethod());

        return ExpenseBean;
    }

    public static Expense createExpense(ExpenseBean ExpenseBean) throws EntityException {
        Expense expense = new Expense();

        try {
            BeanUtils.copyProperties(expense, ExpenseBean);
        } catch (Exception e) {
            throw new EntityException("Could not build ExpenseBean " + e.toString(), e);
        }

        expense.setPaymentMethod(ExpenseBean.getPaymentMethod());

        return expense;
    }
    
    public static Expense createExpense(ItemizedExpenseBean ExpenseBean) throws EntityException {
        Expense expense = new Expense();

        try {
            BeanUtils.copyProperties(expense, ExpenseBean);
        } catch (Exception e) {
            throw new EntityException("Could not build ExpenseBean " + e.toString(), e);
        }

        expense.setPaymentMethod(ExpenseBean.getPaymentMethod());

        return expense;
    }
}