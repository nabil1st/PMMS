/*
 * JCatalog Project
 */
package trackit.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
//
import trackit.businessobjects.Account;
import trackit.businessobjects.Income;
import trackit.businessobjects.User;
import trackit.dao.IncomeDao;
import trackit.dao.UserDao;

/**
 * The Hibernate implementation of the <code>UserDao</code>.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 * @see UserDao
 */
public class IncomeDaoHibernateImpl extends HibernateDaoSupport
        implements IncomeDao {
    
    /**
     * Default constructor.
     */
    public IncomeDaoHibernateImpl() {
            super();
    }

    /**
     * @see UserDao#getUser(String)
     */
    public Income getIncome(String incomeId) {
        Income income = (Income)getHibernateTemplate().load(Income.class, Long.valueOf(incomeId));
        return income;
    }

    public Income saveIncome(Income income) {
        getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
        if (income.getId() == null || income.getId() == 0) {
            this.getHibernateTemplate().save(income);
        } else {
            this.getHibernateTemplate().update(income);
        }
        getHibernateTemplate().flush();
        return income;
    }

    public List<Income> getAllIncomesForAccount(String accountId) {
        return new ArrayList<Income>();
    }

    public List<Income> getAllUndepositedIncomesForAccount(String accountId) {
        // use the accountId to get all user ids for account and then use the user ids
        // to get the expenses at the given date range

        List accounts = getHibernateTemplate().find("from Account account where account.id=?", Long.valueOf(accountId));
        Account account = (Account) accounts.get(0);

        //getHibernateTemplate().getSessionFactory().openSession();
        Set<User> users = account.getUsers();
        List<Long> userIds = new ArrayList<Long>();

        for (User user : users) {
            userIds.add(user.getId());
            System.out.println(user.getId().toString());
        }

        String[] paramNames = new String[]{"userIds"};
        Object[] values = new Object[]{userIds};
        List incomes = getHibernateTemplate().findByNamedParam(
                "from Income income where income.userId in (:userIds) and income.depositId < 1 and income.checkNumber is not null order by income.date", paramNames, values);
        return new ArrayList<Income>(incomes);
    }
}