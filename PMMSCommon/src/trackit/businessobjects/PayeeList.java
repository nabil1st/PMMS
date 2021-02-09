package trackit.businessobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;


//@XmlRootElement(name="payees")
@Root
public class PayeeList {
	
	@ElementList
	private List<Payee> payeeList;
	
	public PayeeList(){}
	
	public PayeeList(List<Payee> payees) {
		payeeList = payees;
	}

//	@XmlElement(name="payee")
	public List<Payee> getPayeeList() {
		return payeeList;
	}

	public void setPayeeList(List<Payee> payeeList) {
		this.payeeList = payeeList;
	}	
	 
}
