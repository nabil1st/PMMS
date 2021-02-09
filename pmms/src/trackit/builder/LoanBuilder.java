/*
 * JCatalog Project
 */
package trackit.builder;

import org.apache.commons.beanutils.BeanUtils;
//
import trackit.backingbeans.LoanBean;
import trackit.backingbeans.LoanBean;
import trackit.businessobjects.Expense;
import trackit.businessobjects.Loan;
import trackit.exception.EntityException;

/**
 * The builder class for <code>LoanBean</code>.
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
public class LoanBuilder {

    /**
     * Create a new <code>LoanBean</code> based on a <code>Expense</code> business object.
     *
     * @param Expense the Expense business object
     * @return the new Expense backing bean
     * @throws CatalogException
     */
    public static LoanBean createLoanBean(Loan loan) throws EntityException {
        LoanBean LoanBean = new LoanBean();

        try {
            BeanUtils.copyProperties(LoanBean, loan);
        } catch (Exception e) {
            throw new EntityException("Could not build LoanBean " + e.toString(), e);
        }

        LoanBean.setPaymentMethod(loan.getPaymentMethod());

        return LoanBean;
    }

    public static Loan createLoan(LoanBean loanBean) throws EntityException {
        Loan loan = new Loan();

        try {
            BeanUtils.copyProperties(loan, loanBean);
        } catch (Exception e) {
            throw new EntityException("Could not build LoanBean " + e.toString(), e);
        }

        loan.setPaymentMethod(loanBean.getPaymentMethod());

        return loan;
    }
}