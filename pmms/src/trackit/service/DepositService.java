/*
 * JCatalog Project
 */
package trackit.service;

import java.util.Date;
import java.util.List;
import trackit.businessobjects.Deposit;


/**
 * The user business service interface.
 * <p>
 * It contains all user management related business logic.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface DepositService {
    public Deposit saveDeposit(Deposit deposit);
    public Deposit findDeposit(String id);

    public List<Deposit> getAllDepositsForAccount(String accountId);

    public List<Deposit> getAllDepositsForBankAccount(String bankAccountId, Date fromDate, Date toDate);
	public Deposit findDepositByCurrencyOnHandId(String valueOf);
	public void deleteDeposit(String valueOf);
}
