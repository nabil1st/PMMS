/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import trackit.businessobjects.CreditCardTransaction;
import trackit.businessobjects.CreditCardTransactionType;
import trackit.service.*;

/**
 *
 * @author Owner
 */

public class CreditCardServiceImplTest extends AbstractDependencyInjectionSpringContextTests {

	private CreditCardService service;

	public void setService (CreditCardService s) {
		this.service = s;
	}

    public CreditCardService getService() {
        return this.service;
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[] {"testApplicationContext*.xml"};
    }

    public void testSaveCreditCardTransaction() throws Exception {
        //CurrencyOnHandDaoHibernateImpl dao = new CurrencyOnHandDaoHibernateImpl();
        //service.setCurrencyOnHandDao(dao);

        CreditCardTransaction xaction = new CreditCardTransaction();
        xaction.setAmount(new BigDecimal("320.23"));
        xaction.setCreditCardId(new Long(1));
        xaction.setDate(new Date());
        xaction.setDescription("test");
        xaction.setInitiatorId(new Long(12));
        xaction.setTransactionType(CreditCardTransactionType.PAYMENT);
        xaction.setUserId(new Long(5));

        this.service.saveCreditCardTransaction(xaction);

    }

//    public void testFindMoneyOrderFeeByMoneyOrderId() throws Exception {
//        MoneyOrderFee fee = service.findMoneyOrderFeeByMoneyOrderId("1");
//        assertNotNull(fee);
//    }


}


