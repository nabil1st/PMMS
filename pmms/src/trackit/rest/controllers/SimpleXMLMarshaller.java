package trackit.rest.controllers;

import java.io.IOException;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.XmlMappingException;

import trackit.businessobjects.BankAccount;
import trackit.businessobjects.BankAccountList;
import trackit.businessobjects.CreditCard;
import trackit.businessobjects.CreditCardList;
import trackit.businessobjects.Expense;
import trackit.businessobjects.ExpenseCategory;
import trackit.businessobjects.ExpenseCategoryList;
import trackit.businessobjects.ExpenseGroup;
import trackit.businessobjects.ExpenseGroupList;
import trackit.businessobjects.ExpenseList;
import trackit.businessobjects.ExpenseSubType;
import trackit.businessobjects.ExpenseSubTypeList;
import trackit.businessobjects.PayeeList;
import trackit.businessobjects.Payee;
import org.trackit.model.Login;

public class SimpleXMLMarshaller implements Marshaller {

	@Override
	public void marshal(Object graph, Result result) throws IOException,
			XmlMappingException {
		Serializer serializer = new Persister();
		StreamResult sr = (StreamResult) result;
		try {
			serializer.write(graph, sr.getOutputStream());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		serializer.write(graph, result.)
		
	}

	@Override
	public boolean supports(Class<?> clazz) {
		if (clazz == PayeeList.class || clazz == Payee.class 
				|| clazz == CreditCardList.class 
				|| clazz == CreditCard.class
				|| clazz == Login.class
				|| clazz == ExpenseGroup.class
				|| clazz == ExpenseGroupList.class
				|| clazz == ExpenseCategory.class
				|| clazz == ExpenseCategoryList.class
				|| clazz == ExpenseSubType.class
				|| clazz == ExpenseSubTypeList.class
				|| clazz == BankAccount.class
				|| clazz == BankAccountList.class
				|| clazz == Expense.class
				|| clazz == ExpenseList.class) {
			return true;
		}
		return false;
	}

}
