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
import trackit.businessobjects.CurrencyOnHandSourceType;
import trackit.businessobjects.CurrencyOnHandType;
import trackit.dao.hibernate.CurrencyOnHandDaoHibernateImpl;
import trackit.service.CurrencyOnHandService;

/**
 *
 * @author Owner
 */

public class CurrencyOnHandServiceImplTest extends AbstractDependencyInjectionSpringContextTests {

	private CurrencyOnHandService service;

	public void setService (CurrencyOnHandService s) {
		this.service = s;
	}

    public CurrencyOnHandService getService() {
        return this.service;
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[] {"testApplicationContext*.xml"};
    }

    public void testSaveCurrencyOnHand() throws Exception {
        //CurrencyOnHandDaoHibernateImpl dao = new CurrencyOnHandDaoHibernateImpl();
        //service.setCurrencyOnHandDao(dao);

        CurrencyOnHand coh = new CurrencyOnHand();
        coh.setAmount(new BigDecimal(100));
        coh.setDate(new Date());
        coh.setGroupId(new Long(1));
        coh.setSourceType(CurrencyOnHandSourceType.INCOME);
        coh.setSourceId(new Long(20));
        coh.setType(CurrencyOnHandType.CASH);
        coh.setUserId(new Long(15));

        service.saveCurrencyOnHand(coh);

        coh = new CurrencyOnHand();
        coh.setAmount(new BigDecimal(200));
        coh.setDate(new Date());
        coh.setGroupId(new Long(2));
        coh.setSourceType(CurrencyOnHandSourceType.UNKNOWN);
        coh.setSourceId(new Long(10));
        coh.setType(CurrencyOnHandType.MONEY_ORDER);
        coh.setUserId(new Long(17));

        service.saveCurrencyOnHand(coh);
    }

    /*public void testGetAllCurrencyOnHandsForAccount() throws Exception {
        List<CurrencyOnHand> list =
                service.getAllCurrencyOnHandsForAccount("15");
        assertEquals(2, list.size());
    }*/


}


