package org.me.expensetrackerme.database;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


import trackit.businessobjects.Expense;
import trackit.businessobjects.ExpensePurpose;
import trackit.businessobjects.PaymentMethod;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

public class ExpenseDataHelper {
	private static final String EXPENSES_TABLE_NAME = "expenses";
	
	private static final String INSERT_NEW_EXPENSE = "insert into " + EXPENSES_TABLE_NAME
		+ "(date_, amount, paymentmethodid, payeeid, expensegroupid, " 
		+ "paymentrefid, expensetypeid, expensepurposeid, notes, checknumber, insync) " 
		+ "values (?,?,?,?,?,?,?,?,?,?,?)";
	
	private SQLiteStatement insertNewExpenseStmt;
	
	
	private SQLiteDatabase db;
	
	public ExpenseDataHelper(SQLiteDatabase db) {
		this.db = db;
		this.insertNewExpenseStmt = this.db.compileStatement(INSERT_NEW_EXPENSE);
	}
	
	public long createUnsynedExpense(Expense expense) {
		// date_, amount, paymentmethodid, payeeid, 
		// expensegroupid, paymentrefid, expensetypeid, expensepurposeid, notes, checknumber, insync
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String dateStr = df.format(expense.getDate());
		
		this.insertNewExpenseStmt.bindString(1, dateStr);
		this.insertNewExpenseStmt.bindString(2, expense.getAmount().toString());
		this.insertNewExpenseStmt.bindLong(3, expense.getPaymentMethod().toInt());
		this.insertNewExpenseStmt.bindLong(4, expense.getPayeeId());
		this.insertNewExpenseStmt.bindLong(5, expense.getExpenseGroupId());
		
		Long paymentRefId = new Long(-1);
		if (expense.getPaymentMethod().equals(PaymentMethod.CREDIT)) {
			paymentRefId = expense.getCreditCardId();
		} else if (expense.getPaymentMethod().equals(PaymentMethod.CHECK) ||
				expense.getPaymentMethod().equals(PaymentMethod.E_PAYMENT) ||
				expense.getPaymentMethod().equals(PaymentMethod.DEBIT)) {
			paymentRefId = expense.getBankAccountId();
		} else if (expense.getPaymentMethod().equals(PaymentMethod.MONEY_ORDER)) {
			paymentRefId = expense.getMoneyOrderId();
		}
		
		this.insertNewExpenseStmt.bindLong(6, paymentRefId);
		this.insertNewExpenseStmt.bindLong(7, expense.getExpenseTypeId());
		this.insertNewExpenseStmt.bindLong(8, expense.getExpensePurpose().toInt());
		this.insertNewExpenseStmt.bindString(9, expense.getDescription() != null?expense.getDescription():"");
		this.insertNewExpenseStmt.bindString(10, expense.getCheckNumber() != null?expense.getCheckNumber():"");
		this.insertNewExpenseStmt.bindLong(11, 0);  // insync
		
		
		return this.insertNewExpenseStmt.executeInsert();
		
	}
	
	public void deleteAllExpenses() {
		this.db.delete(EXPENSES_TABLE_NAME, null, null);
	}
	
	public List<Expense> selectAllExpenses() {
		// date_, amount, paymentmethodid, payeeid, 
		// expensegroupid, paymentrefid, expensetypeid, expensepurposeid, 
		// notes, checknumber, insync
		List<Expense> list = new ArrayList<Expense>();
		Cursor cursor = this.db.query(EXPENSES_TABLE_NAME, 
				new String[] { "id", "date_", "amount", "paymentmethodid", 
					"payeeid", "expensegroupid", "paymentrefid", 
					"expensetypeid", "expensepurposeid", "notes", 
					"checknumber", "insync" },
				null, null, null, null, "id asc");
		SimpleDateFormat dataFormat = new SimpleDateFormat("MM/dd/yyyy");
		if (cursor.moveToFirst()) {
			do {
				Expense e = new Expense();
				e.setId(cursor.getLong(0));
				
				try {
					e.setDate(dataFormat.parse(cursor.getString(1)));
				} catch (ParseException e1) {
					
				}
				
				e.setAmount(new BigDecimal(cursor.getString(2)));
				e.setPaymentMethod(PaymentMethod.fromInt((int) cursor.getLong(3)));
				e.setPayeeId(cursor.getLong(4));
				e.setExpenseGroupId(cursor.getLong(5));
				
				if (e.getPaymentMethod().equals(PaymentMethod.CHECK) ||
						e.getPaymentMethod().equals(PaymentMethod.E_PAYMENT) ||
						e.getPaymentMethod().equals(PaymentMethod.DEBIT)) {
					e.setBankAccountId(cursor.getLong(6));
				} else if (e.getPaymentMethod().equals(PaymentMethod.CREDIT)) {
					e.setCreditCardId(cursor.getLong(6));
				} else if (e.getPaymentMethod().equals(PaymentMethod.MONEY_ORDER)) {
					e.setMoneyOrderId(cursor.getLong(6));
				}
				
				e.setExpenseTypeId(cursor.getLong(7));
				e.setExpensePurpose(ExpensePurpose.fromInt((int) cursor.getLong(8)));
				e.setDescription(cursor.getString(9));
				e.setCheckNumber(cursor.getString(10));
				e.setInSync(cursor.getLong(11) > 0);
								
				list.add(e);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}
	
	public static void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + EXPENSES_TABLE_NAME
				+ "(id INTEGER PRIMARY KEY, " 
				+ "date_ TEXT, amount TEXT, paymentmethodid INTEGER, payeeid INTEGER, "
				+ "expensegroupid INTEGER, paymentrefid INTEGER, expensetypeid INTEGER, "
				+ "expensepurposeid INTEGER, notes TEXT, checknumber TEXT, insync INTEGER)");
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + EXPENSES_TABLE_NAME);
	}
}
