package org.me.expensetrackerme.database;

import java.util.List;

import trackit.businessobjects.Expense;
import trackit.businessobjects.Payee;
import android.content.Context;

public class LocalDataAccess {

	private static LocalDataAccess instance = new LocalDataAccess();
	private DataHelper dataHelper;
	
	private LocalDataAccess() {		
	}
	
	public static LocalDataAccess getInstance() {
		return instance;
	}
	
	public void createDataHelper(Context context) {
		dataHelper = new DataHelper(context);
	}

	public DataHelper getDataHelper() {
		return dataHelper;
	}

	public void setDataHelper(DataHelper dataHelper) {
		this.dataHelper = dataHelper;
	}
	
	public void createUnsyncedPayee(Payee payee) {
		long id = this.dataHelper.createUnsyncedPayee(payee.getDescription());
		payee.setId(id);
	}
	
	public void createUnsyncedExpense(Expense expense) {
		long id = this.dataHelper.createUnsynedExpense(expense);
		expense.setId(id);
	}
	
	public void insertSyncedPayee(Payee payee) {
		this.dataHelper.insertPayee(payee);
	}
	
	public void updatePayees(List<Payee> payees) {
		dataHelper.deleteAllPayees();
		for (Payee payee : payees) {
			dataHelper.insertPayee(payee);
		}
	}
	
	public List<Payee> getSavedPayees() {
		return dataHelper.selectAllPayees();
	}
	
	public List<Expense> getSavedExpenses() {
		return dataHelper.getAllExpenses();
	}
	
	
}
