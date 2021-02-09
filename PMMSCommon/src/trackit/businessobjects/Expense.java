package trackit.businessobjects;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author Owner
 */
@Root
public class Expense extends Entity {
	@Element
    private Date date;
	
	@Element
    private BigDecimal amount;
	@Element
    private PaymentMethod paymentMethod;
	@Element(required=false)
    private Long bankAccountId;
	
	@Element(required=false)
    private String checkNumber;
	@Element(required=false)
    private Long creditCardId;
	@Element
    private Long payeeId;
	@Element
    private Long expenseTypeId;
	@Element(required=false)	
    private Long expenseGroupId;
	
    private String description;
    
    @Element
    private Long userId;
    private Long moneyOrderId;
    private ExpensePurpose expensePurpose;

    private Set<ExpenseItem> expenseItems;
    
    private String expenseTypeStr;
    
    private boolean inSync;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public Long getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(Long creditCardId) {
        this.creditCardId = creditCardId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
	public Long getExpenseGroupId() {
        return expenseGroupId;
    }

    public void setExpenseGroupId(Long expenseGroupId) {
        this.expenseGroupId = expenseGroupId;
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

    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Set<ExpenseItem> getExpenseItems() {
        return expenseItems;
    }

    public void setExpenseItems(Set<ExpenseItem> expenseItems) {
        this.expenseItems = expenseItems;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMoneyOrderId() {
        return moneyOrderId;
    }

    public void setMoneyOrderId(Long moneyOrderId) {
        this.moneyOrderId = moneyOrderId;
    }   
    
    public ExpensePurpose getExpensePurpose() {
		return expensePurpose;
	}

	public void setExpensePurpose(ExpensePurpose expensePurpose) {
		this.expensePurpose = expensePurpose;
	}
	
	public String getExpensePurposeName() {
		if (expensePurpose != null) {
			return this.expensePurpose.toString();
		}
		
		return "";
	}
	
	public void validate() {
		if (this.bankAccountId != null && this.bankAccountId <= 0) {
			this.bankAccountId = null;
		}
		
		if (this.creditCardId != null && this.creditCardId <= 0) {
			this.creditCardId = null;
		}
		
		if (this.expenseGroupId != null && this.expenseGroupId <= 0) {
			this.expenseGroupId = null;
		}
		
		if (this.expensePurpose == null) {
			this.expensePurpose = ExpensePurpose.Personal;
		}
		
		if (this.moneyOrderId != null && this.moneyOrderId <= 0) {
			this.moneyOrderId = null;
		}
	}

	public String getExpenseTypeStr() {
		return expenseTypeStr;
	}

	public void setExpenseTypeStr(String expenseTypeStr) {
		this.expenseTypeStr = expenseTypeStr;
	}

	public boolean isInSync() {
		return inSync;
	}

	public void setInSync(boolean inSync) {
		this.inSync = inSync;
	}

}
