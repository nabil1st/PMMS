package trackit.transaction.service.impl;

import java.util.Set;

import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import trackit.businessobjects.Account;
import trackit.businessobjects.Payee;
import trackit.service.PayeeService;
import trackit.service.impl.DataInUseException;
import trackit.servicelocator.ServiceLocator;
import trackit.transaction.service.PayeeTransactionalService;
import trackit.util.FacesUtils;

public class PayeeTransactionalServiceImpl implements PayeeTransactionalService {
	
	private HibernateTransactionManager transactionManager;
    private ServiceLocator serviceLocator;

	@Override
	public void deletePayee(final String id) throws DataInUseException {
		
		verifyPayeeCanBeDeleted(id);
		
		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {
            	String currentAccountId = FacesUtils.getSessionBean().getCurrentAccountId();
            	Account account = getServiceLocator().getAccountService().findAccount(currentAccountId);
            	
            	Set<Payee> set = account.getPayees();
            	for (Payee p : set) {
            		if (p.getId() == Long.parseLong(id)) {
            			set.remove(p);
            			getServiceLocator().getAccountService().saveAccount(account);
            			break;
            		}
            	}
            	
            	getServiceLocator().getPayeeService().deletePayee(id);
            }
        });
		
	}
	
	private void verifyPayeeCanBeDeleted(String id) throws DataInUseException {
		
		PayeeService payeeService = getServiceLocator().getPayeeService();
		
		int numberOfExpensesUsingIt = payeeService.getNumberOfExpensesWithPayee(id);
		
		if (numberOfExpensesUsingIt > 0) {
			throw new DataInUseException("One or more Expenses are already tied to this payee.");
		}	
		
		int numberOfLoansUsingIt = payeeService.getNumberOfLoansWithPayee(id);
		
		if (numberOfLoansUsingIt > 0) {
			throw new DataInUseException("One or more Loans are already tied to this payee.");
		}
	}
	
	private ServiceLocator getServiceLocator() {
		return this.serviceLocator;
	}

	@Override
	public void setServiceLocator(ServiceLocator serviceLocator) {
		this.serviceLocator = serviceLocator;		
	}

	public HibernateTransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(HibernateTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	

}
