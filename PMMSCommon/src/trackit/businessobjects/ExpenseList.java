package trackit.businessobjects;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class ExpenseList {
	
	@ElementList
	private List<Expense> list;
	
	public ExpenseList(){}
	
	public ExpenseList(List<Expense> lst) {
		this.list = lst;
	}

	public List<Expense> getList() {
		return list;
	}

	public void setList(List<Expense> list) {
		this.list = list;
	}
	
	
}
