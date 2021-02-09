package trackit.backingbeans;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StatementMatchItem {
	private CSVStatementItem fromStatement;
	private CSVStatementItem fromTransactions;
	
	public StatementMatchItem(CSVStatementItem item1, CSVStatementItem item2) {
		this.fromStatement = item1;
		this.fromTransactions = item2;
	}

	public CSVStatementItem getFromStatement() {
		return fromStatement;
	}

	public void setFromStatement(CSVStatementItem fromStatement) {
		this.fromStatement = fromStatement;
	}

	public CSVStatementItem getFromTransactions() {
		return fromTransactions;
	}

	public void setFromTransactions(CSVStatementItem fromTransactions) {
		this.fromTransactions = fromTransactions;
	}
	
	public String getStatementDate() {
		if (fromStatement == null) return "";
		if (fromStatement.getDate() == null) {
			return "";
		}
		
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		
		return df.format(fromStatement.getDate());
	}
	
	public String getTransactionDate() {
		if (fromTransactions == null) return "";
		if (fromTransactions.getDate() == null) {
			return "";
		}
		
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		
		return df.format(fromTransactions.getDate());
	}
	
	public String getStatementPayee() {
		if (fromStatement == null) return "";
		if (fromStatement.getPayee() == null) {
			return "";
		}
		return fromStatement.getPayee();
	}
	
	public String getTransactionPayee() {
		if (fromTransactions == null) return "";
		if (fromTransactions.getPayee() == null) {
			return "";
		}
		return fromTransactions.getPayee();
	}
	
	public String getStatementAmount() {
		if (fromStatement == null) return "";
		if (fromStatement.getAmount() == null) {
			return "";
		}
		return fromStatement.getAmount().toString();
	}
	
	public String getTransactionAmount() {
		if (fromTransactions == null) return "";
		if (fromTransactions.getAmount() == null) {
			return "";
		}
		return fromTransactions.getAmount().toString();
	}
	
	public Date getItemsDate() {
		if (fromStatement != null) {
			return fromStatement.getDate();
		} else {
			return fromTransactions.getDate();
		}
	}
	
	public String getStyleClass() {
		if (fromStatement != null && fromTransactions != null) {
			return "matchedStatementItem";
		} else if (fromStatement == null) {
			return "unmatchedTransaction";
		} else {
			return "unmatchedStatementItem";
		}
	}

}
