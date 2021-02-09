package trackit.backingbeans;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.faces.model.SelectItem;

import org.springframework.jdbc.config.InitializeDatabaseBeanDefinitionParser;

import edu.emory.mathcs.backport.java.util.Collections;

import au.com.bytecode.opencsv.CSVParser;

import trackit.businessobjects.CreditCardTransaction;
import trackit.businessobjects.CreditCardTransactionType;
import trackit.businessobjects.Payee;
import trackit.exception.EntityException;
import trackit.servicelocator.ServiceLocator;
import trackit.util.FacesUtils;
import trackit.util.ListUtils;

public class StatementColumnSelectionBean extends BaseBean {
	
	private String[] columns = null;
	private String[][] model = null;
	private String dateColumn;
	private String payeeColumn;
	private String amountColumn;
	
	public String[] getColumns() {
		if (columns == null) {
			initialize();
		}
		return columns;
	}
	public void setColumns(String[] columns) {
		this.columns = columns;
	}
	public String[][] getModel() {
		if (model == null) {
			initialize();
		}
		return model;
	}
	public void setModel(String[][] model) {
		this.model = model;
	}
	
	private void initialize() {
		
		List<String[]> arraysList = new ArrayList<String[]>();
		
		List<List<String>> modelList = new ArrayList<List<String>>();
		String csv = FacesUtils.getSessionBean().getStatementCSV();		
		
		StringTokenizer tokenizer = new StringTokenizer(csv, "\n");
		while (tokenizer.hasMoreTokens()) {
			String line = tokenizer.nextToken();
			
			// Ignore non-separator commas
			
			
			CSVParser parser = new CSVParser();
			String[] tokens;
			try {
				tokens = parser.parseLine(line);
			} catch (IOException e) {
				tokens = new String[]{};
			}
			
			if (columns == null) {
				columns = new String[tokens.length];				
			}

			arraysList.add(tokens);
		}
		
		model = new String[arraysList.size()][columns.length];
		
		for (int i=0; i<arraysList.size(); i++) {
			model[i] = arraysList.get(i);
		}
		
		// Get rid of empty columns
		List<Integer> emptyColumns = new ArrayList<Integer>();
		for (int j=0; j<columns.length; j++) {
			if (isColumnEmpty(model, j)) {
				emptyColumns.add(j);
			}
		}
		
		if (emptyColumns.size() > 0) {
			String[][] newModel = new String[model.length][model[0].length - emptyColumns.size()];
			int targetCol = 0;
			for (int sourceCol=0; sourceCol<model[0].length; sourceCol++) {
				if (emptyColumns.contains(sourceCol)) {
					continue;
				}
				
				for (int row=0; row<model.length; row++) {
					newModel[row][targetCol] = model[row][sourceCol];
				}
				
				targetCol++;
			}
			
			model = newModel;
		}
		
		columns = new String[model[0].length];
		for (int j=0; j<columns.length; j++) {
			int index = j + 1;
			columns[j] = "Column " + index; 
		}
		
	}
	
	private boolean isColumnEmpty(String[][] array, int colIndex) {
		for (int i=0; i<array.length; i++) {
			if (array[i][colIndex] != null && array[i][colIndex].trim().length() != 0) {
				return false;
			}
		}
		
		return true;
	}
	public String getDateColumn() {
		return dateColumn;
	}
	public void setDateColumn(String dateColumn) {
		this.dateColumn = dateColumn;
	}
	public String getPayeeColumn() {
		return payeeColumn;
	}
	public void setPayeeColumn(String payeeColumn) {
		this.payeeColumn = payeeColumn;
	}
	public String getAmountColumn() {
		return amountColumn;
	}
	public void setAmountColumn(String amountColumn) {
		this.amountColumn = amountColumn;
	}
	
	public String confirmSelection() throws EntityException {
		int amountColumnIndex = extractIndex(amountColumn) - 1;
		int dateColumnIndex = extractIndex(dateColumn) - 1;
		int payeeColumnIndex = extractIndex(payeeColumn) - 1;
		
		List<CSVStatementItem> itemsFromStatement = new ArrayList<CSVStatementItem>();
		
		for (int i=0; i<model.length; i++) {
			CSVStatementItem item = new CSVStatementItem();
			String amountStr = model[i][amountColumnIndex];
			item.setAmount(new BigDecimal(amountStr));
			String dateStr = model[i][dateColumnIndex];
			Date date = extractDate(dateStr);
			if (date == null) {			
				StringTokenizer st = new StringTokenizer(dateStr);
				if (st.countTokens() > 1) {
					while (st.hasMoreTokens()) {
						String temp = st.nextToken();
						date = extractDate(temp);
						if (date != null) {
							break;
						}
					}
				}
			}
			
			if (date == null) {
				FacesUtils.addErrorMessage("Unable to find a valid date field.");
				return "";
			}
			
			item.setDate(date);
			
			item.setPayee(model[i][payeeColumnIndex]);
			
			itemsFromStatement.add(item);
		}
		
		
		// Now retrieve all transactions within the same date period.
		Collections.sort(itemsFromStatement, new Comparator<CSVStatementItem>(){
			@Override
			public int compare(CSVStatementItem o1, CSVStatementItem o2) {
				return o1.getDate().compareTo(o2.getDate());
			}
			
		});
		
		Date fromDate = itemsFromStatement.get(0).getDate();
		Date toDate = itemsFromStatement.get(itemsFromStatement.size() - 1).getDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(toDate);
		cal.add(Calendar.HOUR, 24);
		toDate = cal.getTime();
		
		List<CreditCardStatementItem> transactions = ListUtils.getCreditCardTransactions(fromDate, toDate, this.serviceLocator);
		List<CSVStatementItem> itemsFromTransactions = convertToCSVItems(transactions);
		
		List<StatementMatchItem> matchedItems = new ArrayList<StatementMatchItem>();
		
		
		for (CSVStatementItem statementItem : itemsFromStatement) {
			StatementMatchItem matchItem = new StatementMatchItem(statementItem, findMatch(statementItem, itemsFromTransactions));
			matchedItems.add(matchItem);
		}
		
		for (CSVStatementItem transactionItem : itemsFromTransactions) {
			if (transactionItem.isUsed()) {
				continue;
			}
			
			StatementMatchItem matchItem = new StatementMatchItem(null, transactionItem);
			matchedItems.add(matchItem);			
		}
		
		Collections.sort(matchedItems, new Comparator<StatementMatchItem>() {

			@Override
			public int compare(StatementMatchItem o1, StatementMatchItem o2) {
				return o1.getItemsDate().compareTo(o2.getItemsDate());
			}
			
		});
		
		FacesUtils.getSessionBean().setStatementMatchModel(matchedItems);
		
		
		
        return NavigationResults.STATEMENT_COMPARISON;
    }
	
	private CSVStatementItem findMatch(CSVStatementItem statementItem, List<CSVStatementItem> transactions) {
		// Try to find an item with same amount, date and payee
		for (CSVStatementItem xItem : transactions) {
			if (xItem.isUsed()) {
				continue;
			}
			
			if (statementItem.getAmount().doubleValue() == xItem.getAmount().doubleValue() &&
					datesEqualIgnoreTimeComponent(statementItem.getDate(), xItem.getDate())) {
				statementItem.setUsed(true);
				xItem.setUsed(true);
				return xItem;
			}
		}
		
		return null;
	}
	
	private boolean datesEqualIgnoreTimeComponent(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		
		return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
			cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
			cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
		
		
	}
	
	
	private List<CSVStatementItem> convertToCSVItems(List<CreditCardStatementItem> transactions) {
		List<CSVStatementItem> list = new ArrayList<CSVStatementItem>();
		for (CreditCardStatementItem tx : transactions) {
			CSVStatementItem item = new CSVStatementItem();
			item.setDate(tx.getDate());
			
			if (tx.getCreditCardTransactionType().equals(CreditCardTransactionType.PAYMENT)) {
				item.setAmount(tx.getAmount().multiply(new BigDecimal(-1)));
				item.setPayee("Payment");
			} else {
				item.setAmount(tx.getAmount());
				item.setPayee(tx.getTo());
			}
			
			
			list.add(item);
		}
		
		return list;
	}
	
	private static Date extractDate(String dateStr) {
		Date date = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		try {
			date = dateFormat.parse(dateStr);
			return date;
		} catch (ParseException e) {
			return null;
		}
	}
	
	private static int extractIndex(String columnStr) {
		String indexStr = columnStr.substring("Column ".length());
		return Integer.parseInt(indexStr);
	}
	
	public List<SelectItem> getSelectionColumns() {
    	
		if (columns == null) {
			initialize();
		}
		
    	List<SelectItem> dropList = new ArrayList<SelectItem>();
	    
    	for (String col : columns) {
    		dropList.add(new SelectItem(col, col));
    	}
    	
	    return dropList;
    }
	
	public static void main(String[] args) {
		String dateStr = "10/28/2010";
		Date dt = extractDate(dateStr);
		System.out.println(dt.toString());
	}
	
	

}
