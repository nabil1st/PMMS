/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trackit.service.impl;

import java.util.List;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import trackit.businessobjects.Borrower;
import trackit.dao.BorrowerDao;
import trackit.service.BorrowerService;

/**
 *
 * @author Owner
 */
public class BorrowerServiceImpl implements BorrowerService {

    private BorrowerDao dao;

    public void setBorrowerDao(BorrowerDao dao) {
        this.dao = dao;
    }

    public Borrower saveBorrower(Borrower a) {
        Borrower savedBankAccount = dao.saveBorrower(a);
        return savedBankAccount;
    }

    public Borrower findBorrower(String id) {
        return dao.getBorrower(id);
    }

    public Borrower findBorrowerByName(String name) {
        try {
            List list = this.dao.findBorrowerByName(name);
            if (list.size() == 0) {
                return null;
            }

            return (Borrower) list.get(0);
        } catch (HibernateObjectRetrievalFailureException e) {
            return null;
        }
    }

    public List<Borrower> getAllBorrowersForAccount(String accountId) {
        return dao.getAllBorrowersForAccount(accountId);
    }
}
