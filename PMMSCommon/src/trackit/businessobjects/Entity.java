package trackit.businessobjects;

import org.simpleframework.xml.Element;

public class Entity {
	
	@Element(required=false)
	protected Long id;
	
	@Element(required=false)
	protected Long accountId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	
}
