/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.service.impl;

import java.util.List;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import trackit.businessobjects.ExpenseCategory;
import trackit.businessobjects.ExpenseSubType;
import trackit.service.ExpenseCategoryService;

/**
 *
 * @author Owner
 */

public class ExpenseCategoryServiceImplTest
        extends AbstractDependencyInjectionSpringContextTests {

	private ExpenseCategoryService service;

	public void setService (ExpenseCategoryService s) {
		this.service = s;
	}

    public ExpenseCategoryService getService() {
        return this.service;
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[] {"testApplicationContext*.xml"};
    }

    public void testSaveExpenseCategory() throws Exception {
        //CurrencyOnHandDaoHibernateImpl dao = new CurrencyOnHandDaoHibernateImpl();
        //service.setCurrencyOnHandDao(dao);

        ExpenseCategory cat = new ExpenseCategory();
        cat.setAccountId(null);
        cat.setDescription("Utilities");

        cat = this.service.saveExpenseCategory(cat);

        ExpenseSubType subType = new ExpenseSubType();
        subType.setCategoryId(cat.getId());
        subType.setDescription("Phone");

        this.service.saveExpenseSubType(subType);

        subType = new ExpenseSubType();
        subType.setCategoryId(cat.getId());
        subType.setDescription("Gas");

        this.service.saveExpenseSubType(subType);

        subType = new ExpenseSubType();
        subType.setCategoryId(cat.getId());
        subType.setDescription("Electric");

        this.service.saveExpenseSubType(subType);

        subType = new ExpenseSubType();
        subType.setCategoryId(cat.getId());
        subType.setDescription("Custom");
        subType.setAccountId(new Long(1));

        this.service.saveExpenseSubType(subType);

        List<ExpenseCategory> categories =
                this.service.getAllExpenseCategoriesForAccount("1");

        assertEquals(1, categories.size());

        List<ExpenseSubType> subTypes = categories.get(0).getSubTypes();
        assertEquals(4, subTypes.size());

        ExpenseSubType st = subTypes.get(0);
        assertEquals("Custom", st.getDescription());

        st = subTypes.get(1);
        assertEquals("Electric", st.getDescription());

        st = subTypes.get(2);
        assertEquals("Gas", st.getDescription());

        st = subTypes.get(3);
        assertEquals("Phone", st.getDescription());



    }

//    public void testFindMoneyOrderFeeByMoneyOrderId() throws Exception {
//        MoneyOrderFee fee = service.findMoneyOrderFeeByMoneyOrderId("1");
//        assertNotNull(fee);
//    }


}


