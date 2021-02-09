/*
 * JCatalog Project
 */
package trackit.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
//
import trackit.businessobjects.Account;
import trackit.businessobjects.User;
import trackit.dao.UserDao;

/**
 * The Hibernate implementation of the <code>UserDao</code>.
 * 
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 * @see UserDao
 */
public class UserDaoHibernateImpl extends HibernateDaoSupport implements UserDao {
    /**
     * Default constructor.
     */
    public UserDaoHibernateImpl() {
            super();
    }

    /**
     * @see UserDao#getUser(String)
     */
    public User getUser(String userId) {
        User user = (User)getHibernateTemplate().load(User.class, Long.valueOf(userId));
        return user;
    }
    
    public List findUserByEmail(String email) {
        return getHibernateTemplate().find("from User user where user.email=?", email);
    }

    public User saveUser(User user) {
        getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
        if (user.getId() == null || user.getId() == 0) {
            this.getHibernateTemplate().save(user);
        } else {
            this.getHibernateTemplate().update(user);
        }
        getHibernateTemplate().flush();
        return user;
    }

    public List<User> getAllUsersForAccount(String currentAccountId) {
        List accounts = getHibernateTemplate().find("from Account account where account.id=?", Long.valueOf(currentAccountId));
        Account account = (Account) accounts.get(0);
        return new ArrayList<User>(account.getUsers());
    }
}