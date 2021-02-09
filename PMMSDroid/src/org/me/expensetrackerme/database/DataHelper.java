package org.me.expensetrackerme.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

import org.trackit.model.Login;

import trackit.businessobjects.Expense;
import trackit.businessobjects.Payee;

public class DataHelper {

	private static final String DATABASE_NAME = "pmms.db";
	private static final int DATABASE_VERSION = 2;
	

	private Context context;
	private SQLiteDatabase db;
	
	private PayeeDataHelper payeeHelper;
	private AccountDataHelper accountHelper;	
	private ExpenseDataHelper expenseHelper;
	
		
	public DataHelper(Context context) {
		this.context = context;
		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();
		payeeHelper = new PayeeDataHelper(db);
		accountHelper = new AccountDataHelper(db);
		expenseHelper = new ExpenseDataHelper(db);
	}

	public long createUnsyncedPayee(String name) {
		return this.payeeHelper.createUnsyncedPayee(name);
	}
	
	public long insertPayee(Payee payee) {
		return this.payeeHelper.insertPayee(payee);
	}
	
	public long createAccount(long accountId, long userId) {
		return accountHelper.createAccount(accountId, userId);
	}
	
	public long createUnsynedExpense(Expense expense) {
		return expenseHelper.createUnsynedExpense(expense);		
	}

	public void deleteAllPayees() {
		this.payeeHelper.deleteAllPayees();
	}
	
	public void deleteAccount() {
		this.accountHelper.deleteAccount();
	}

	public List<Payee> selectAllPayees() {
		return this.payeeHelper.selectAllPayees();
	}
	
	public Login getAccountInfo() {
		return accountHelper.getAccountInfo();
	}
	
	public List<Expense> getAllExpenses() {
		return expenseHelper.selectAllExpenses();
	}
	
	public void deleteAllExpenses() {
		expenseHelper.deleteAllExpenses();
	}

	private static class OpenHelper extends SQLiteOpenHelper {

		OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			PayeeDataHelper.onCreate(db);
			AccountDataHelper.onCreate(db);
			ExpenseDataHelper.onCreate(db);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("PMMS",
					"Upgrading database, this will drop tables and recreate.");
			PayeeDataHelper.onUpgrade(db, oldVersion, newVersion);
			AccountDataHelper.onUpgrade(db, oldVersion, newVersion);
			ExpenseDataHelper.onUpgrade(db, oldVersion, newVersion);
			onCreate(db);
		}
	}
}
