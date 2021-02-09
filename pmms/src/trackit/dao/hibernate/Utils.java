/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.orm.hibernate3.HibernateTemplate;
import trackit.businessobjects.Account;
import trackit.businessobjects.ExpenseSubType;
import trackit.businessobjects.User;

/**
 *
 * @author ndaoud
 */
public class Utils {

    public static List<Long> getUserIdsForAccountId(HibernateTemplate template, String accountId) {
        List accounts = template.find("from Account account where account.id=?", Long.valueOf(accountId));
        Account account = (Account) accounts.get(0);

        //getHibernateTemplate().getSessionFactory().openSession();
        Set<User> users = account.getUsers();
        List<Long> userIds = new ArrayList<Long>();

        for (User user : users) {
            userIds.add(user.getId());
            System.out.println(user.getId().toString());
        }

        return userIds;
    }
    
    public static List<Long> getExpenseTypeIdsForCategoryId(HibernateTemplate template, String categoryId) {
    	List<ExpenseSubType> subTypes = template.find("from ExpenseSubType subType where subType.categoryId=?", Long.valueOf(categoryId));
    	List<Long> typeIds = new ArrayList<Long>();
        
        //getHibernateTemplate().getSessionFactory().openSession();
        for (ExpenseSubType st : subTypes) {
        	typeIds.add(st.getId());            
        }

        return typeIds;
    }

}
