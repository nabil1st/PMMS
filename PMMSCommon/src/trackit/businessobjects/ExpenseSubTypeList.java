package trackit.businessobjects;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class ExpenseSubTypeList {
	
	@ElementList
	private List<ExpenseSubType> list;
	
	public ExpenseSubTypeList(){}
	
	public ExpenseSubTypeList(List<ExpenseSubType> lst) {
		this.list = lst;
	}

	public List<ExpenseSubType> getList() {
		return list;
	}

	public void setList(List<ExpenseSubType> list) {
		this.list = list;
	}

}
