package trackit.businessobjects;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class BankAccountList {
	
	@ElementList
	private List<BankAccount> list;
	
	public BankAccountList(){}
	
	public BankAccountList(List<BankAccount> lst) {
		this.list = lst;
	}

	public List<BankAccount> getList() {
		return list;
	}

	public void setList(List<BankAccount> list) {
		this.list = list;
	}
}
