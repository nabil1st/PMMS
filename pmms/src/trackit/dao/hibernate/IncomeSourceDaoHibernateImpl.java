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
import trackit.businessobjects.IncomeSource;
import trackit.dao.IncomeSourceDao;

/**
 *
 * @author Owner
 */
public class IncomeSourceDaoHibernateImpl extends HibernateDaoSupport
        implements IncomeSourceDao {
    /**
     * Default constructor.
     */
    public IncomeSourceDaoHibernateImpl() {
            super();
    }

    /**
     * @see UserDao#getUser(String)
     */
    public IncomeSource getIncomeSource(String id) {
            IncomeSource type = (IncomeSource)getHibernateTemplate().load(IncomeSource.class, id);
            return type;
    }

    public IncomeSource saveIncomeSource(IncomeSource type) {
        getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
        if (type.getId() == null || type.getId() == 0) {
            this.getHibernateTemplate().save(type);
        } else {
            this.getHibernateTemplate().update(type);
        }
        getHibernateTemplate().flush();
        return type;
    }

    public List findIncomeSourceByName(String name) {
        return getHibernateTemplate().find("from IncomeSource source where source.name=?", name);
    }

    public List<IncomeSource> getAllIncomeSourcesForAccount(String accountId) {
        List accounts = getHibernateTemplate().find(
                "from Account account where account.id=?", Long.valueOf(accountId));
        Account account = (Account) accounts.get(0);
        return new ArrayList<IncomeSource>(account.getIncomeSources());
    }
}