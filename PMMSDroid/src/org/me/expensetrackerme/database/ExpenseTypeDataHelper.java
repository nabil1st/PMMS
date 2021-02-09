package org.me.expensetrackerme.database;

import java.util.ArrayList;
import java.util.List;

import org.me.expesetrackerme.application.AccountInfo;

import trackit.businessobjects.ExpenseGroup;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

public class ExpenseTypeDataHelper {
	private static final String TABLE_NAME = "expensegroups";
	
	private SQLiteStatement insertNewStmt;
	private static final String INSERT_NEW = "insert into " + TABLE_NAME
			+ "(name, insync) values (?, ?)";
	
	private SQLiteStatement insertExistingStmt;
	private static final String INSERT_EXISTING = "insert into " + TABLE_NAME
			+ "(id, name, insync) values (?, ?, ?)";
	
	
	private SQLiteDatabase db;
	
	public ExpenseTypeDataHelper(SQLiteDatabase db) {
		this.db = db;
		this.insertNewStmt = this.db.compileStatement(INSERT_NEW);
		this.insertExistingStmt = this.db.compileStatement(INSERT_EXISTING);
	}
	
	public long createUnsyncedExpenseGroup(String name) {
		this.insertNewStmt.bindString(1, name);
		this.insertNewStmt.bindLong(2, 0);
		return this.insertNewStmt.executeInsert();
	}
	
	public long insertExpenseGroup(ExpenseGroup eg) {
		this.insertExistingStmt.bindLong(1, eg.getId());
		this.insertExistingStmt.bindString(2, eg.getDescription());
		this.insertExistingStmt.bindLong(3, 1);
		return this.insertExistingStmt.executeInsert();
	}
	
	public void deleteAllExpenseGroups() {
		this.db.delete(TABLE_NAME, null, null);
	}
	
	public List<ExpenseGroup> selectAllExpenseGroup() {
		List<ExpenseGroup> list = new ArrayList<ExpenseGroup>();
		Cursor cursor = this.db.query(TABLE_NAME, new String[] { "id", "name", "insync" },
				null, null, null, null, "name asc");
		if (cursor.moveToFirst()) {
			do {
				ExpenseGroup p = new ExpenseGroup();
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
		db.execSQL("CREATE TABLE " + TABLE_NAME
				+ "(id INTEGER PRIMARY KEY, name TEXT, insync INTEGER)");
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	}
}
