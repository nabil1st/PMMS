package org.me.expensetrackerme.database;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.me.expesetrackerme.application.AccountInfo;

import trackit.businessobjects.CreditCard;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

public class CreditCardDataHelper {
	private static final String TABLE_NAME = "creditcards";
	
	private SQLiteStatement insertNewStmt;
	private static final String INSERT_NEW = "insert into " + TABLE_NAME
			+ "(name, balance, insync) values (?, ?, ?)";
	
	private SQLiteStatement insertExistingStmt;
	private static final String INSERT_EXISTING = "insert into " + TABLE_NAME
			+ "(id, name, balance, insync) values (?, ?, ?, ?)";
	
	
	private SQLiteDatabase db;
	
	public CreditCardDataHelper(SQLiteDatabase db) {
		this.db = db;
		this.insertNewStmt = this.db.compileStatement(INSERT_NEW);
		this.insertExistingStmt = this.db.compileStatement(INSERT_EXISTING);
	}
	
	public long createUnsyncedCreditCard(String name, BigDecimal balance) {
		this.insertNewStmt.bindString(1, name);
		this.insertNewStmt.bindString(2, balance.toString());
		this.insertNewStmt.bindLong(3, 0);
		return this.insertNewStmt.executeInsert();
	}
	
	public long insertCreditCard(CreditCard account) {
		this.insertExistingStmt.bindLong(1, account.getId());
		this.insertExistingStmt.bindString(2, account.getDescription());
		this.insertExistingStmt.bindString(3, account.getBalance().toString());
		this.insertExistingStmt.bindLong(4, 1);
		return this.insertExistingStmt.executeInsert();
	}
	
	public void deleteAllCreditCards() {
		this.db.delete(TABLE_NAME, null, null);
	}
	
	public List<CreditCard> selectAllCreditCards() {
		List<CreditCard> list = new ArrayList<CreditCard>();
		Cursor cursor = this.db.query(TABLE_NAME, new String[] { "id", "name", "balance", "insync" },
				null, null, null, null, "name asc");
		if (cursor.moveToFirst()) {
			do {
				CreditCard p = new CreditCard();
				p.setId(cursor.getLong(0));
				p.setDescription(cursor.getString(1));
				p.setBalance(new BigDecimal(cursor.getString(2)));
				p.setInSync(cursor.getInt(3) > 0);
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
				+ "(id INTEGER PRIMARY KEY, name TEXT, balance TEXT, insync INTEGER)");
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	}
}
