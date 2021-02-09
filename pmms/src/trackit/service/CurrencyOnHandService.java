/*
 * JCatalog Project
 */
package trackit.service;

import java.util.Date;
import java.util.List;
import trackit.businessobjects.CurrencyOnHand;
import trackit.businessobjects.CurrencyOnHandSourceType;
import trackit.businessobjects.CurrencyOnHandStatus;
import trackit.businessobjects.CurrencyOnHandType;



/**
 * The user business service interface.
 * <p>
 * It contains all user management related business logic.
 *
 * @author Nabil
 */
public interface CurrencyOnHandService {

    public List<CurrencyOnHand> getAllUnusedCurrenciesOnHandForAccount(String currentAccountId);
    
    
    public List<CurrencyOnHand> getAllUnusedMoneyOrdersOnHandForAccount(String currentAccountId);
    public CurrencyOnHand saveCurrencyOnHand(CurrencyOnHand source);
    public CurrencyOnHand findCurrencyOnHand(String id);

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

    public List<CurrencyOnHand> getCurrencyOnHandForExpenseGroup(String groupId);


	public void deleteCurrencyOnHand(String valueOf);


}
