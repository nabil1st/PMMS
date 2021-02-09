package trackit.businessobjects;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;



@Root
public class ExpenseGroupList {
	
	@ElementList
	private List<ExpenseGroup> list;
	
	public ExpenseGroupList(){}
	
	public ExpenseGroupList(List<ExpenseGroup> lst) {
		this.list = lst;
	}

	public List<ExpenseGroup> getList() {
		return list;
	}

	public void setList(List<ExpenseGroup> list) {
		this.list = list;
	}
	
}
