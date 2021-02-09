/*
 * JCatalog Project
 */
package trackit.dao;

import java.util.Date;
import java.util.List;
import trackit.businessobjects.CurrencyOnHand;
import trackit.businessobjects.CurrencyOnHandSourceType;
import trackit.businessobjects.CurrencyOnHandStatus;
import trackit.businessobjects.CurrencyOnHandType;


/**
 * The user DAO interface.
 * <p>
 * This class contains user management related data access logic.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface CurrencyOnHandDao {

    public List<CurrencyOnHand> getAllCurrencyOnHandsForAccount(String accountId);

    public List<CurrencyOnHand> getAllUnusedCurrenciesOnHandForAccount(String currentAccountId);
    
    
    public List<CurrencyOnHand> getAllUnusedMoneyOrdersOnHandForAccount(String currentAccountId);

    public CurrencyOnHand getCurrencyOnHand(String id);
    public CurrencyOnHand saveCurrencyOnHand(CurrencyOnHand source);

    public List<CurrencyOnHand> getAllLoanPaymentsForAccount(String accountId);

    public List<CurrencyOnHand> getAllLoanPaymentsForLoan(String loanId);

    public List<CurrencyOnHand> getCurrencyOnHand(
            String currentAccountId,
            Date toDate,
            Date fromDate,
            boolean showAllDates,
            CurrencyOnHandStatus status,
            CurrencyOnHandType type,
            CurrencyOnHandSourceType sourceType,
            Long sourceId,
            Long groupId);

    public List<CurrencyOnHand> getCurrencyOnHandForExpenseGroup(String groupid);

	public void deleteCurrencyOnHand(String id);
}
