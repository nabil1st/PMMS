package trackit.businessobjects;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class ExpenseCategoryList {
	
	@ElementList
	private List<ExpenseCategory> list;
	
	public ExpenseCategoryList(){}
	
	public ExpenseCategoryList(List<ExpenseCategory> lst) {
		this.list = lst;
	}

	public List<ExpenseCategory> getList() {
		return list;
	}

	public void setList(List<ExpenseCategory> list) {
		this.list = list;
	}

}
