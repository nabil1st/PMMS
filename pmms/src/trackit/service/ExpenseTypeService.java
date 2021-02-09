/*
 * JCatalog Project
 */
package trackit.service;

import java.util.List;
import trackit.businessobjects.ExpenseType;

/**
 * The user business service interface.
 * <p>
 * It contains all user management related business logic.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface ExpenseTypeService {
    public ExpenseType saveExpenseType(ExpenseType accnt);
    public ExpenseType findExpenseType(String id);
    public Object findExpenseTypeByDescription(String description);

    public List<ExpenseType> getAllExpenseTypesForAccount(String accountId);
}
