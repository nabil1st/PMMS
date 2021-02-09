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
import trackit.businessobjects.MoneyOrderFee;
import trackit.businessobjects.User;
import trackit.dao.IncomeDao;
import trackit.dao.MoneyOrderDao;
import trackit.dao.UserDao;

/**
 * The Hibernate implementation of the <code>UserDao</code>.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 * @see UserDao
 */
public class MoneyOrderDaoHibernateImpl extends HibernateDaoSupport
        implements MoneyOrderDao {

    /**
     * Default constructor.
     */
    public MoneyOrderDaoHibernateImpl() {
            super();
    }



    
    /**
     * Finds the MoneyOrderFee for a given money order.
     * @param moneyOrderId the id of the money order
     * @return the MoneyOrderFee object.
     */
    public MoneyOrderFee getMoneyOrderFeeByMoneyOrderId(String moneyOrderId) {
        List list = getHibernateTemplate().find("from MoneyOrderFee fee where fee.moneyOrderId=?", Long.parseLong(moneyOrderId));
        if (list.size() > 0) {
            return (MoneyOrderFee)list.get(0);
        }

        return null;
    }
    
    public MoneyOrderFee getMoneyOrderFee(String id) {
        List list = getHibernateTemplate().find("from MoneyOrderFee fee where fee.id=?", Long.parseLong(id));
        if (list.size() > 0) {
            return (MoneyOrderFee)list.get(0);
        }

        return null;
    }

    public MoneyOrderFee saveMoneyOrderFee(MoneyOrderFee fee) {
        getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
        if (fee.getId() == null || fee.getId() == 0) {
            this.getHibernateTemplate().save(fee);
        } else {
            this.getHibernateTemplate().update(fee);
        }
        getHibernateTemplate().flush();
        return fee;
    }




	@Override
	public void deleteMoneyOrderFee(String id) {
		MoneyOrderFee fee = getMoneyOrderFee(id);
		getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
        getHibernateTemplate().delete(fee);
        getHibernateTemplate().flush();
		
	}
}