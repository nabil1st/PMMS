/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trackit.service.impl;

import trackit.businessobjects.MoneyOrderFee;
import trackit.dao.MoneyOrderDao;
import trackit.service.MoneyOrderService;

/**
 *
 * @author Owner
 */
public class MoneyOrderServiceImpl implements MoneyOrderService {

    private MoneyOrderDao moDao;

    public void setMoneyOrderDao(MoneyOrderDao moDao) {
        this.moDao = moDao;
    }

    public MoneyOrderFee saveMoneyOrderFee(MoneyOrderFee fee) {
        return moDao.saveMoneyOrderFee(fee);
    }

    public MoneyOrderFee findMoneyOrderFeeByMoneyOrderId(String moneyOrderId) {
        return moDao.getMoneyOrderFeeByMoneyOrderId(moneyOrderId);
    }

	@Override
	public void deleteMoneyOrderFee(String id) {
		moDao.deleteMoneyOrderFee(id);		
	}



}
