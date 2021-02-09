/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import trackit.businessobjects.BankAccountTransaction;
import trackit.businessobjects.BankTransactionType;
import trackit.service.BankAccountService;

/**
 *
 * @author Owner
 */

public class BankAccountServiceImplTest extends AbstractDependencyInjectionSpringContextTests {

	private BankAccountService service;

	public void setService (BankAccountService s) {
		this.service = s;
	}

    public BankAccountService getService() {
        return this.service;
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[] {"testApplicationContext*.xml"};
    }

    public void testSaveBankAccountTransaction() throws Exception {
        //CurrencyOnHandDaoHibernateImpl dao = new CurrencyOnHandDaoHibernateImpl();
        //service.setCurrencyOnHandDao(dao);

        BankAccountTransaction xaction = new BankAccountTransaction();
        xaction.setAmount(new BigDecimal("320.23"));
        xaction.setBankAccountId(new Long(1));
        xaction.setCheckNumber("1234565");
        xaction.setDate(new Date());
        xaction.setDescription("test");
        xaction.setInitiatorId(new Long(12));
        xaction.setTransactionType(BankTransactionType.CHECK_WITHDRAWL);
        xaction.setUserId(new Long(5));

        this.service.saveBankAccountTransaction(xaction);


    }

//    public void testFindMoneyOrderFeeByMoneyOrderId() throws Exception {
//        MoneyOrderFee fee = service.findMoneyOrderFeeByMoneyOrderId("1");
//        assertNotNull(fee);
//    }


}


