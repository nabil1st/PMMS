/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trackit.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import trackit.businessobjects.Account;
import trackit.businessobjects.ExpenseGroup;
import trackit.businessobjects.Payee;
import trackit.dao.PayeeDao;
import trackit.service.PayeeService;
import trackit.servicelocator.ServiceLocator;
import trackit.util.FacesUtils;

/**
 *
 * @author Owner
 */
public class PayeeServiceImpl implements PayeeService {

    private PayeeDao dao;
    private ServiceLocator serviceLocator;

    public void setPayeeDao(PayeeDao dao) {
        this.dao = dao;
    }

    public Payee savePayee(Payee a) {
        Payee savedBankAccount = dao.savePayee(a);
        return savedBankAccount;
    }

    public Payee findPayee(String id) {
        return dao.getPayee(id);
    }

    public Payee findPayeeByDescription(String description) {
        try {
            List list = this.dao.findPayeeByDescription(description);
            if (list.size() == 0) {
                return null;
            }

            return (Payee) list.get(0);
        } catch (HibernateObjectRetrievalFailureException e) {
            return null;
        }
    }

    public List<Payee> getAllPayeesForAccount(String accountId) {
        return dao.getAllPayeesForAccount(accountId);
    }
    
    public void deletePayee(String id) {
    	dao.deletePayee(id);
    }
    
    public int getNumberOfExpensesWithPayee(String id) {
    	return dao.getNumberOfExpensesWithPayee(id);
    }
    
    public int getNumberOfLoansWithPayee(String id) {
    	return dao.getNumberOfLoansWithPayee(id);
    }

	public ServiceLocator getServiceLocator() {
		return serviceLocator;
	}

	public void setServiceLocator(ServiceLocator serviceLocator) {
		this.serviceLocator = serviceLocator;
	}
    
    
}
