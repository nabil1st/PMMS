package trackit.backingbeans;

import java.math.BigDecimal;
import java.util.Date;

public class DatedAmount {
	private Date date;
	private BigDecimal amount;
	
	public DatedAmount() {
		
	}
	public DatedAmount(Date dt, BigDecimal am) {
		this.date = dt;
		this.amount = am;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
