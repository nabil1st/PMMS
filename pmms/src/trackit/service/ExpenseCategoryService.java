/*
 * JCatalog Project
 */
package trackit.service;

import java.util.List;
import trackit.businessobjects.ExpenseCategory;
import trackit.businessobjects.ExpenseSubType;

/**
 * The user business service interface.
 * <p>
 * It contains all user management related business logic.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface ExpenseCategoryService {
    public ExpenseCategory saveExpenseCategory(ExpenseCategory accnt);
    public ExpenseCategory findExpenseCategory(String id);
    public ExpenseSubType findExpenseSubType(String id);
    public Object findExpenseCategoryByDescription(String description);

    public List<ExpenseCategory> getAllExpenseCategoriesForAccount(String accountId);

    public ExpenseSubType saveExpenseSubType(ExpenseSubType type);
}
