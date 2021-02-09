/*
 * JCatalog Project
 */
package trackit.service;

import java.util.List;
import trackit.businessobjects.Income;
import trackit.businessobjects.IncomeSource;
import trackit.businessobjects.MoneyOrderFee;


/**
 * The user business service interface.
 * <p>
 * It contains all user management related business logic.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface MoneyOrderService {
    public MoneyOrderFee saveMoneyOrderFee(MoneyOrderFee Fee);
    public MoneyOrderFee findMoneyOrderFeeByMoneyOrderId(String moneyOrderId);
    public void deleteMoneyOrderFee(String id);

}
