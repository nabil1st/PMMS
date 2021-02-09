package trackit.webservices;

import java.math.BigDecimal;

public class ExpenseStatsDataHolder {
	private Long expenseTypeId;
	private Long payeeId;
	private BigDecimal amount;
	
	public ExpenseStatsDataHolder(Long expenseTypeId, Long payeeId, BigDecimal amount) {
		this.expenseTypeId = expenseTypeId;
		this.payeeId = payeeId;
		this.amount = amount;
	}
	
	public Long getExpenseTypeId() {
		return expenseTypeId;
	}
	public void setExpenseTypeId(Long expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}
	public Long getPayeeId() {
		return payeeId;
	}
	public void setPayeeId(Long payeeId) {
		this.payeeId = payeeId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
