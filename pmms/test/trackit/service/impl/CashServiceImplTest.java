/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import trackit.businessobjects.CashTransaction;
import trackit.businessobjects.CashTransactionType;
import trackit.service.CashService;

/**
 *
 * @author Owner
 */

public class CashServiceImplTest extends AbstractDependencyInjectionSpringContextTests {

	private CashService service;

	public void setService (CashService s) {
		this.service = s;
	}

    public CashService getService() {
        return this.service;
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[] {"testApplicationContext*.xml"};
    }

    public void testSaveBankAccountTransaction() throws Exception {
        //CurrencyOnHandDaoHibernateImpl dao = new CurrencyOnHandDaoHibernateImpl();
        //service.setCurrencyOnHandDao(dao);

        CashTransaction xaction = new CashTransaction();
        xaction.setAmount(new BigDecimal("320.23"));
        xaction.setDate(new Date());
        xaction.setDescription("test");
        xaction.setInitiatorId(new Long(12));
        xaction.setTransactionType(CashTransactionType.OPENING_BALANCE);
        xaction.setUserId(new Long(5));

        this.service.saveCashTransaction(xaction);


    }

//    public void testFindMoneyOrderFeeByMoneyOrderId() throws Exception {
//        MoneyOrderFee fee = service.findMoneyOrderFeeByMoneyOrderId("1");
//        assertNotNull(fee);
//    }


}


