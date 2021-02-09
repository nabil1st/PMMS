/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import trackit.businessobjects.CurrencyOnHand;
import trackit.businessobjects.CurrencyOnHandType;
import trackit.businessobjects.MoneyOrderFee;
import trackit.dao.hibernate.CurrencyOnHandDaoHibernateImpl;
import trackit.service.CurrencyOnHandService;
import trackit.service.MoneyOrderService;

/**
 *
 * @author Owner
 */

public class MoneyOrderServiceImplTest extends AbstractDependencyInjectionSpringContextTests {

	private MoneyOrderService service;

	public void setService (MoneyOrderService s) {
		this.service = s;
	}

    public MoneyOrderService getService() {
        return this.service;
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[] {"testApplicationContext*.xml"};
    }

    /*public void testSaveMoneyOrderFee() throws Exception {
        //CurrencyOnHandDaoHibernateImpl dao = new CurrencyOnHandDaoHibernateImpl();
        //service.setCurrencyOnHandDao(dao);

        MoneyOrderFee fee = new MoneyOrderFee();
        fee.setMoneyOrderId(new Long(1));
        fee.setFee(new BigDecimal("0.75"));
        service.saveMoneyOrderFee(fee);


    }*/

    public void testFindMoneyOrderFeeByMoneyOrderId() throws Exception {
        MoneyOrderFee fee = service.findMoneyOrderFeeByMoneyOrderId("1");
        assertNotNull(fee);
    }


}


