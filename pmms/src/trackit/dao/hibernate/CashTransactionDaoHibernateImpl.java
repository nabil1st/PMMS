/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import trackit.businessobjects.CashTransaction;
import trackit.businessobjects.CashTransfer;
import trackit.businessobjects.Expense;
import trackit.businessobjects.MoneyOrderFee;
import trackit.dao.CashTransactionDao;

/**
 *
 * @author Owner
 */
public class CashTransactionDaoHibernateImpl
        extends HibernateDaoSupport
        implements CashTransactionDao {
    /**
     * Default constructor.
     */
    public CashTransactionDaoHibernateImpl() {
            super();
    }

    /**
     * @see UserDao#getUser(String)
     */
    public CashTransaction getCashTransaction(String id) {
            CashTransaction account =
                    (CashTransaction)getHibernateTemplate().load(
                    CashTransaction.class, Long.parseLong(id));
            return account;
    }

    public CashTransaction saveCashTransaction(
            CashTransaction transaction) {

        getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);

        if (transaction.getId() == null || transaction.getId() == 0) {
            this.getHibernateTemplate().save(transaction);
        } else {
            this.getHibernateTemplate().update(transaction);
        }
        getHibernateTemplate().flush();
        return transaction;
    }

	@Override
	public CashTransfer getCashTransfer(String id) {
		CashTransfer transfer =
            (CashTransfer)getHibernateTemplate().load(
            CashTransfer.class, id);
		return transfer;
	}

	@Override
	public List<CashTransfer> getCashTransfers(String accountId, Date fromDate, Date toDate) {
		List<Long> userIds = Utils.getUserIdsForAccountId(getHibernateTemplate(), accountId);

        String[] paramNames = new String[]{"userIds", "fromDate", "toDate"};
        Object[] values = new Object[]{userIds, fromDate, toDate};
        List transfers = getHibernateTemplate().findByNamedParam("from CashTransfer transfer where (transfer.sourceId in (:userIds) or transfer.destinationId in (:userIds)) and transfer.transferDate >= :fromDate and transfer.transferDate < :toDate order by transfer.transferDate", paramNames, values);
        return new ArrayList<CashTransfer>(transfers);
	}

	@Override
	public CashTransfer saveCashTransfer(CashTransfer transfer) {
		getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);

        if (transfer.getId() == null || transfer.getId() == 0) {
            this.getHibernateTemplate().save(transfer);
        } else {
            this.getHibernateTemplate().update(transfer);
        }
        getHibernateTemplate().flush();
        return transfer;
	}

	@Override
	public void deleteCashTransaction(String id) {
		CashTransaction x = getCashTransaction(id);
		getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
		getHibernateTemplate().delete(x);
		getHibernateTemplate().flush();
	}
}

