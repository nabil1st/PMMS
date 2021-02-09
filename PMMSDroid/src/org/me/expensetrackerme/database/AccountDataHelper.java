package org.me.expensetrackerme.database;

import java.util.ArrayList;
import java.util.List;

import org.me.expesetrackerme.application.AccountInfo;
import org.trackit.model.Login;

import trackit.businessobjects.Payee;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

public class AccountDataHelper {
	private static final String ACCOUNT_TABLE_NAME = "account";
	
	private SQLiteStatement insertIntoAccountStmt;
	private static final String INSERT_INTO_ACCOUNT = "insert into " + ACCOUNT_TABLE_NAME
			+ "(accountId, userId) values (?, ?)";	
	
	private SQLiteDatabase db;
	
	public AccountDataHelper(SQLiteDatabase db) {
		this.db = db;
		this.insertIntoAccountStmt = this.db.compileStatement(INSERT_INTO_ACCOUNT);
	}
	
	public long createAccount(long accountId, long userId) {
		this.insertIntoAccountStmt.bindLong(1, accountId);
		this.insertIntoAccountStmt.bindLong(2, userId);
		return this.insertIntoAccountStmt.executeInsert();
	}
	
		
	public void deleteAccount() {
		this.db.delete(ACCOUNT_TABLE_NAME, null, null);
	}
	
	public Login getAccountInfo() {
		Cursor cursor = this.db.query(ACCOUNT_TABLE_NAME, new String[] { "id", "accountid", "userid"},
				null, null, null, null, "id asc");
		Login login = null;
		
		if (cursor.moveToFirst()) {
			login = new Login();
			login.setAccountId(cursor.getLong(1));
			login.setUserId(cursor.getLong(2));
		}
		
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		
		return login;
	}
	
	public static void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + ACCOUNT_TABLE_NAME
				+ "(id INTEGER PRIMARY KEY, accountid INTEGER, userId INTEGER)");
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + ACCOUNT_TABLE_NAME);
	}
}
