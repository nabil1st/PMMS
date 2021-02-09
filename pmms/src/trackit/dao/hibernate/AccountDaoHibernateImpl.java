/*
 * JCatalog Project
 */
package trackit.dao.hibernate;

import java.util.List;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
//
import trackit.businessobjects.Account;
import trackit.businessobjects.User;
import trackit.dao.AccountDao;
import trackit.dao.UserDao;

/**
 * The Hibernate implementation of the <code>UserDao</code>.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 * @see UserDao
 */
public class AccountDaoHibernateImpl extends HibernateDaoSupport implements AccountDao {
    /**
     * Default constructor.
     */
    public AccountDaoHibernateImpl() {
            super();
    }

    /**
     * @see UserDao#getUser(String)
     */
    public Account getAccount(String accountId) {
            Account account = (Account)getHibernateTemplate().load(Account.class, Long.valueOf(accountId));
            return account;
    }

    public Account saveAccount(Account account) {
        getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
        if (account.getId() == null || account.getId() == 0) {
            this.getHibernateTemplate().save(account);
        } else {
            this.getHibernateTemplate().update(account);
        }
        getHibernateTemplate().flush();
        return account;
    }
}