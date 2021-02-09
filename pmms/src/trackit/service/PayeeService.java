/*
 * JCatalog Project
 */
package trackit.service;

import java.util.List;
import trackit.businessobjects.Payee;
import trackit.service.impl.DataInUseException;
import trackit.servicelocator.ServiceLocator;

/**
 * The user business service interface.
 * <p>
 * It contains all user management related business logic.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface PayeeService {
    public Payee savePayee(Payee accnt);
    public Payee findPayee(String id);
    public Object findPayeeByDescription(String description);

    public List<Payee> getAllPayeesForAccount(String accountId);
    
    public void deletePayee(String id);
    
    public ServiceLocator getServiceLocator();
	public void setServiceLocator(ServiceLocator serviceLocator);
	
	public int getNumberOfExpensesWithPayee(String id);    
    public int getNumberOfLoansWithPayee(String id);
}
