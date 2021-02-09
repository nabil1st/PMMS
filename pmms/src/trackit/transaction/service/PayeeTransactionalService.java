package trackit.transaction.service;

import trackit.service.impl.DataInUseException;
import trackit.servicelocator.ServiceLocator;


public interface PayeeTransactionalService {
	public void setServiceLocator(ServiceLocator serviceLocator);
	public void deletePayee(String id) throws DataInUseException;
}
