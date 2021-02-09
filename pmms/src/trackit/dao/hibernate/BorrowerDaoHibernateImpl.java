/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import trackit.businessobjects.Account;
import trackit.businessobjects.Borrower;
import trackit.dao.BorrowerDao;
import trackit.dao.UserDao;

/**
 *
 * @author Owner
 */
public class BorrowerDaoHibernateImpl extends HibernateDaoSupport
        implements BorrowerDao {
    /**
     * Default constructor.
     */
    public BorrowerDaoHibernateImpl() {
            super();
    }

    /**
     * @see UserDao#getUser(String)
     */
    public Borrower getBorrower(String id) {
            Borrower type = (Borrower)getHibernateTemplate().load(Borrower.class, Long.parseLong(id));
            return type;
    }

    public Borrower saveBorrower(Borrower type) {
        getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
        if (type.getId() == null || type.getId() == 0) {
            this.getHibernateTemplate().save(type);
        } else {
            this.getHibernateTemplate().update(type);
        }
        getHibernateTemplate().flush();
        return type;
    }

    public List findBorrowerByName(String name) {
        return getHibernateTemplate().find("from Borrower b where b.name=?", name);
    }

    public List<Borrower> getAllBorrowersForAccount(String accountId) {
        List accounts = getHibernateTemplate().find(
                "from Account account where account.id=?", Long.valueOf(accountId));
        Account account = (Account) accounts.get(0);
        return new ArrayList<Borrower>(account.getBorrowers());
    }
}