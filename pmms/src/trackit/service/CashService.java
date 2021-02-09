/*
 * JCatalog Project
 */
package trackit.service;

import java.util.Date;
import java.util.List;

import trackit.businessobjects.CashTransaction;
import trackit.businessobjects.CashTransfer;


/**
 * The user business service interface.
 * <p>
 * It contains all user management related business logic.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface CashService {
    public CashTransaction saveCashTransaction(
            CashTransaction accnt);
    public CashTransaction findCashTransaction(String id);
    
    public CashTransfer findCashTransfer(String id);
    
    public CashTransfer saveCashTransfer(CashTransfer transfer);
    
    public List<CashTransfer> getCashTransfers(String accountId, Date fromDate, Date toDate);
	public void deleteCashTransaction(String valueOf);
    
    
    
}
