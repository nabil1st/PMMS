package org.me.expensetrackerme.database;

import java.util.ArrayList;
import java.util.List;

import org.me.expesetrackerme.application.AccountInfo;

import trackit.businessobjects.Payee;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

public class PayeeDataHelper {
	private static final String PAYEES_TABLE_NAME = "payees";
	
	private SQLiteStatement insertNewPayeeStmt;
	private static final String INSERT_NEW_PAYEE = "insert into " + PAYEES_TABLE_NAME
			+ "(name, insync) values (?, ?)";
	
	private SQLiteStatement insertExistingPayeeStmt;
	private static final String INSERT_EXISTING_PAYEE = "insert into " + PAYEES_TABLE_NAME
			+ "(id, name, insync) values (?, ?, ?)";
	
	
	private SQLiteDatabase db;
	
	public PayeeDataHelper(SQLiteDatabase db) {
		this.db = db;
		this.insertNewPayeeStmt = this.db.compileStatement(INSERT_NEW_PAYEE);
		this.insertExistingPayeeStmt = this.db.compileStatement(INSERT_EXISTING_PAYEE);
	}
	
	public long createUnsyncedPayee(String name) {
		this.insertNewPayeeStmt.bindString(1, name);
		this.insertNewPayeeStmt.bindLong(2, 0);
		return this.insertNewPayeeStmt.executeInsert();
	}
	
	public long insertPayee(Payee payee) {
		this.insertExistingPayeeStmt.bindLong(1, payee.getId());
		this.insertExistingPayeeStmt.bindString(2, payee.getDescription());
		this.insertExistingPayeeStmt.bindLong(3, 1);
		return this.insertExistingPayeeStmt.executeInsert();
	}
	
	public void deleteAllPayees() {
		this.db.delete(PAYEES_TABLE_NAME, null, null);
	}
	
	public List<Payee> selectAllPayees() {
		List<Payee> list = new ArrayList<Payee>();
		Cursor cursor = this.db.query(PAYEES_TABLE_NAME, new String[] { "id", "name", "insync" },
				null, null, null, null, "name asc");
		if (cursor.moveToFirst()) {
			do {
				Payee p = new Payee();
				p.setId(cursor.getLong(0));
				p.setDescription(cursor.getString(1));
				p.setInSync(cursor.getInt(2) > 0);
				p.setAccountId(AccountInfo.getInstance().getAccountId());
				list.add(p);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}
	
	public static void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + PAYEES_TABLE_NAME
				+ "(id INTEGER PRIMARY KEY, name TEXT, insync INTEGER)");
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + PAYEES_TABLE_NAME);
	}
}
