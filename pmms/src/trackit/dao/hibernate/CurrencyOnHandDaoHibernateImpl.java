/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import trackit.businessobjects.Account;
import trackit.businessobjects.CurrencyOnHand;
import trackit.businessobjects.CurrencyOnHandSourceType;
import trackit.businessobjects.CurrencyOnHandStatus;
import trackit.businessobjects.CurrencyOnHandType;
import trackit.businessobjects.User;
import trackit.dao.CurrencyOnHandDao;

/**
 *
 * @author Owner
 */
public class CurrencyOnHandDaoHibernateImpl extends  
        HibernateDaoSupport implements CurrencyOnHandDao {
    
    public List<CurrencyOnHand> getAllCurrencyOnHandsForAccount(String accountId) {
        // use the accountId to get all user ids for account and then use the user ids
        // to get the expenses at the given date range

        List<Long> userIds = Utils.getUserIdsForAccountId(getHibernateTemplate(), accountId);

        String[] paramNames = new String[]{"userIds"};
        Object[] values = new Object[]{userIds};
        List incomes = getHibernateTemplate().findByNamedParam(
                "from CurrencyOnHand income where income.userId in (:userIds) order by income.date", paramNames, values);
        return new ArrayList<CurrencyOnHand>(incomes);
    }

    public CurrencyOnHand getCurrencyOnHand(String id) {
        CurrencyOnHand coh = (CurrencyOnHand)getHibernateTemplate().load(CurrencyOnHand.class, Long.parseLong(id));
        return coh;
    }

    public CurrencyOnHand saveCurrencyOnHand(CurrencyOnHand source) {
        getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
        if (source.getId() == null || source.getId() == 0) {
            this.getHibernateTemplate().save(source);
        } else {
            this.getHibernateTemplate().update(source);
        }
        getHibernateTemplate().flush();
        return source;
    }

    public List<CurrencyOnHand> getAllUnusedCurrenciesOnHandForAccount(String currentAccountId) {
        List<CurrencyOnHandType> acceptedTypes = new ArrayList<CurrencyOnHandType>();
        acceptedTypes.add(CurrencyOnHandType.CASHIERS_CHECK);
        acceptedTypes.add(CurrencyOnHandType.CHECK);
        acceptedTypes.add(CurrencyOnHandType.MONEY_ORDER);

        return getUnusedCurrencyOnHandsForAccountByType(currentAccountId, acceptedTypes);

        
    }

    public List<CurrencyOnHand> getAllUnusedMoneyOrdersOnHandForAccount(String currentAccountId) {
        List<CurrencyOnHandType> acceptedTypes = new ArrayList<CurrencyOnHandType>();
        acceptedTypes.add(CurrencyOnHandType.CASHIERS_CHECK);
        acceptedTypes.add(CurrencyOnHandType.MONEY_ORDER);

        return getUnusedCurrencyOnHandsForAccountByType(currentAccountId, acceptedTypes);
    }

    private List<CurrencyOnHand> getUnusedCurrencyOnHandsForAccountByType(
            String currentAccountId, List<CurrencyOnHandType> types) {
        return getCurrencyOnHands(
                currentAccountId,
                types,
                CurrencyOnHandStatus.ON_HAND,
                new ArrayList<CurrencyOnHandSourceType>());
    }
    
    private List<CurrencyOnHand> getCurrencyOnHands(
            String currentAccountId, 
            List<CurrencyOnHandType> types, 
            CurrencyOnHandStatus state,
            List<CurrencyOnHandSourceType> incomeSourceTypes) {
        List accounts = getHibernateTemplate().find("from Account account where account.id=?",
                Long.valueOf(currentAccountId));
        Account account = (Account) accounts.get(0);

        //getHibernateTemplate().getSessionFactory().openSession();
        Set<User> users = account.getUsers();
        List<Long> userIds = new ArrayList<Long>();

        for (User user : users) {
            userIds.add(user.getId());
        }

        List<String> paramNamesList = new ArrayList<String>();
        List paramValues = new ArrayList();

        paramNamesList.add("userIds");
        paramValues.add(userIds);

        String[] paramNames = null;
        Object[] values = null;
        
        StringBuffer sql = new StringBuffer();
        
        sql.append("from CurrencyOnHand income where income.userId in (:userIds) ");
        
        if (types != null && types.size() > 0) {
            sql.append("and income.type in (:acceptedTypes) ");
            paramNamesList.add("acceptedTypes");
            paramValues.add(types);
        }
        
        if (incomeSourceTypes != null && incomeSourceTypes.size() > 0) {
            sql.append("and income.sourceType in (:incomeSourceTypes) ");
            paramNamesList.add("incomeSourceTypes");
            paramValues.add(incomeSourceTypes);
        }
        
        if (state.equals(CurrencyOnHandStatus.NOT_ON_HAND)) {
            sql.append("and income.used = 1 ");
        } else if (state.equals(CurrencyOnHandStatus.ON_HAND)) {
            sql.append("and income.used = 0 ");
        }
        
        sql.append("order by income.date");

        paramNames = paramNamesList.toArray(new String[]{});
        values = paramValues.toArray();
        
        List incomes = getHibernateTemplate().findByNamedParam(
                sql.toString(), paramNames, values);
        return new ArrayList<CurrencyOnHand>(incomes);
    }

    public List<CurrencyOnHand> getAllLoanPaymentsForAccount(
            String accountId) {
        List<CurrencyOnHandSourceType> acceptedTypes = new ArrayList<CurrencyOnHandSourceType>();
        acceptedTypes.add(CurrencyOnHandSourceType.LOAN_PAYMENT);

        return getCurrencyOnHands(
                accountId,
                new ArrayList<CurrencyOnHandType>(),
                CurrencyOnHandStatus.ALL,
                acceptedTypes);
    }

    public List<CurrencyOnHand> getAllLoanPaymentsForLoan(String loanId) {
        String[] paramNames = new String[] {"referenceIds", "incomeSourceTypes"};

        List<Long> refIds = new ArrayList<Long>();
        refIds.add(Long.parseLong(loanId));

        List<CurrencyOnHandSourceType> sourceTypes = new ArrayList<CurrencyOnHandSourceType>();
        sourceTypes.add(CurrencyOnHandSourceType.LOAN_PAYMENT);

        Object[] values = new Object[]{refIds, sourceTypes};

        String sql = "from CurrencyOnHand income where " +
                "income.referenceId in (:referenceIds) " +
                "and income.sourceType in (:incomeSourceTypes) " +
                "order by income.date";

        List incomes = getHibernateTemplate().findByNamedParam(
                sql.toString(), paramNames, values);
        return new ArrayList<CurrencyOnHand>(incomes);
    }

    public List<CurrencyOnHand> getCurrencyOnHand(
            String currentAccountId,
            Date toDate,
            Date fromDate,
            boolean showAllDates,
            CurrencyOnHandStatus status,
            CurrencyOnHandType type,
            CurrencyOnHandSourceType sourceType,
            Long sourceId,
            Long groupId) {

        List accounts = getHibernateTemplate().find("from Account account where account.id=?",
                Long.valueOf(currentAccountId));
        Account account = (Account) accounts.get(0);

        //getHibernateTemplate().getSessionFactory().openSession();
        Set<User> users = account.getUsers();
        List<Long> userIds = new ArrayList<Long>();

        for (User user : users) {
            userIds.add(user.getId());
        }

        List<String> paramNamesList = new ArrayList<String>();
        List paramValues = new ArrayList();

        String[] paramNames = null;
        Object[] values = null;

        paramNamesList.add("userIds");
        paramValues.add(userIds);

        

        StringBuffer sql = new StringBuffer(
                "from CurrencyOnHand income where income.userId in (:userIds) ");

        if (!showAllDates) {
            paramNamesList.add("fromDate");
            paramValues.add(fromDate);
            paramNamesList.add("toDate");
            paramValues.add(toDate);

            sql.append(" and income.date >= :fromDate and income.date < :toDate");
        }

        if (CurrencyOnHandStatus.NOT_ON_HAND.equals(status)) {
            sql.append(" and income.used = 1");
        } else if (CurrencyOnHandStatus.ON_HAND.equals(status)) {
            sql.append(" and income.used = 0");
        }

        if (!(CurrencyOnHandType.UNKNOWN.equals(type) || type == null)) {
            List<CurrencyOnHandType> acceptedTypes = new ArrayList<CurrencyOnHandType>();
            acceptedTypes.add(type);

            sql.append(" and income.type in (:acceptedTypes) ");
            paramNamesList.add("acceptedTypes");
            paramValues.add(acceptedTypes);
        }

        if (!(CurrencyOnHandSourceType.UNKNOWN.equals(sourceType) || sourceType == null)) {
            List<CurrencyOnHandSourceType> acceptedTypes = new ArrayList<CurrencyOnHandSourceType>();
            acceptedTypes.add(sourceType);

            sql.append(" and income.sourceType in (:acceptedSourceTypes) ");
            paramNamesList.add("acceptedSourceTypes");
            paramValues.add(acceptedTypes);
        }

        if (sourceId != null && sourceId != -1) {
            List<Long> sourceIds = new ArrayList<Long>();
            sourceIds.add(sourceId);

            sql.append(" and income.sourceId in (:sourceIds) ");
            paramNamesList.add("sourceIds");
            paramValues.add(sourceIds);
        }

        if (groupId != null && groupId != -1) {
            List<Long> groupIds = new ArrayList<Long>();
            groupIds.add(groupId);

            sql.append(" and income.groupId in (:groupIds) ");
            paramNamesList.add("groupIds");
            paramValues.add(groupIds);
        }

        paramNames = paramNamesList.toArray(new String[]{});
        values = paramValues.toArray();

        List incomes = getHibernateTemplate().findByNamedParam(
                sql.toString(), paramNames, values);
        return new ArrayList<CurrencyOnHand>(incomes);
    }

    public List<CurrencyOnHand> getCurrencyOnHandForExpenseGroup(String groupId) {
        List<Long> groupIds = new ArrayList<Long>();
        groupIds.add(Long.parseLong(groupId));
        String[] paramNames = new String[]{"groupIds"};
        Object[] values = new Object[]{groupIds};
        List incomes = getHibernateTemplate().findByNamedParam(
                "from CurrencyOnHand income where income.groupId in (:groupIds) order by income.date", paramNames, values);
        return new ArrayList<CurrencyOnHand>(incomes);
    }

	@Override
	public void deleteCurrencyOnHand(String id) {
		CurrencyOnHand coh = getCurrencyOnHand(id);
		getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
		getHibernateTemplate().delete(coh);
		getHibernateTemplate().flush();
	}

}
