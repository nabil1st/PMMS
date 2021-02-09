package trackit.businessobjects;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import trackit.businessobjects.CreditCard;

@Root
public class CreditCardList {
	
	@ElementList
	private List<CreditCard> creditCardList;
	
	public CreditCardList(){}
	
	public CreditCardList(List<CreditCard> cards) {
		creditCardList = cards;
	}

	public List<CreditCard> getCreditCardList() {
		return creditCardList;
	}

	public void setCreditCardList(List<CreditCard> cards) {
		this.creditCardList = cards;
	}	
	 
}
