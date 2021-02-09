package org.me.expesetrackerme.application;

import org.me.expensetrackerme.database.DataHelper;

public class AccountInfo {
	private static AccountInfo instance = new AccountInfo();
	private Long accountId;
	private Long userId;
	
	private boolean offline = false;
	
	private AccountInfo() {
		
	}
	
	public static AccountInfo getInstance() {
		return instance;
	}
	
	public Long getAccountId() {
		return accountId;
	}
	
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public boolean isOffline() {
		return offline;
	}

	public void setOffline(boolean offline) {
		this.offline = offline;
	}
}
