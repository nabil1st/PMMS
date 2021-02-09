/*
 * JCatalog Project
 */
package trackit.service;

import java.util.List;
import trackit.businessobjects.Income;
import trackit.businessobjects.IncomeSource;


/**
 * The user business service interface.
 * <p>
 * It contains all user management related business logic.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface IncomeService {
    public IncomeSource saveIncomeSource(IncomeSource source);
    public IncomeSource findIncomeSource(String id);

    public List<IncomeSource> getAllIncomeSourcesForAccount(String accountId);

    public Income saveIncome(Income income);
    public Income fineIncome(String id);

    public List<Income> getAllUndepositedIncomesForAccount(String accountId);

}
