/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import trackit.businessobjects.ExpenseCategory;
import trackit.businessobjects.ExpenseSubType;
import trackit.dao.ExpenseCategoryDao;
import trackit.dao.UserDao;

/**
 *
 * @author Owner
 */
public class ExpenseCategoryDaoHibernateImpl extends HibernateDaoSupport
        implements ExpenseCategoryDao {
    /**
     * Default constructor.
     */
    public ExpenseCategoryDaoHibernateImpl() {
            super();
    }

    /**
     * @see UserDao#getUser(String)
     */
    public ExpenseCategory getExpenseCategory(String id) {
        ExpenseCategory category = (ExpenseCategory)getHibernateTemplate().load(ExpenseCategory.class, Long.parseLong(id));
        
        // Load template subtypes for each template category            
        Object[] arguments = new Object[]{category.getId()};
        List listSubTypes = getHibernateTemplate().find(
                "from ExpenseSubType type where type.categoryId=? order by type.description",
                arguments);
        List<ExpenseSubType> subTypes = new ArrayList<ExpenseSubType>(listSubTypes);
        category.setSubTypes(subTypes);
        
        return category;
    }

    public ExpenseCategory saveExpenseCategory(ExpenseCategory type) {
        getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
        if (type.getId() == null || type.getId() == 0) {
            this.getHibernateTemplate().save(type);
        } else {
            this.getHibernateTemplate().update(type);
        }
        getHibernateTemplate().flush();
        return type;
    }

    public List findExpenseCategoryByDescription(String description) {
        return getHibernateTemplate().find("from ExpenseCategory card where card.description=?", description);
    }

    public List<ExpenseCategory> getAllExpenseCategoriesForAccount(String accountId) {        

        // Get all template categories
        List listCats = getHibernateTemplate().find(
                "from ExpenseCategory cat where cat.accountId is null or cat.accountId=? order by cat.description",
                Long.valueOf(accountId));

        List<ExpenseCategory> categories =
                new ArrayList<ExpenseCategory>(listCats);

        // Load template subtypes for each template category
        for (ExpenseCategory category : categories) {
            Object[] arguments = new Object[]{category.getId(), Long.parseLong(accountId)};
            List listSubTypes = getHibernateTemplate().find(
                    "from ExpenseSubType type where type.categoryId=? and (type.accountId is null or type.accountId=?) order by type.description",
                    arguments);
            List<ExpenseSubType> subTypes = new ArrayList<ExpenseSubType>(listSubTypes);
            category.setSubTypes(subTypes);
        }


        return categories;



    }

    public ExpenseSubType saveExpenseSubType(ExpenseSubType type) {
        getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
        if (type.getId() == null || type.getId() == 0) {
            this.getHibernateTemplate().save(type);
        } else {
            this.getHibernateTemplate().update(type);
        }
        getHibernateTemplate().flush();
        return type;
    }

    public ExpenseSubType getExpenseSubType(String id) {
        ExpenseSubType type = (ExpenseSubType)getHibernateTemplate().load(ExpenseSubType.class, id);
        return type;
    }
}