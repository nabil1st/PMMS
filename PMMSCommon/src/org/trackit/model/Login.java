package org.trackit.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Login { 
	
	public static final byte LOGIN_SUCCESS = 1;
	public static final byte LOGIN_FAILURE = 2;
	public static final byte CONNECTION_TIMED_OUT = 3;
	
	@Element(required=false)
	private String userName;
	
	@Element(required=false)
	private String password;
	
	@Element(required=false)
	private byte status;
	
	@Element(required=false)
	private Long accountId;
	
	@Element(required=false)
	private Long userId;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
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

}
