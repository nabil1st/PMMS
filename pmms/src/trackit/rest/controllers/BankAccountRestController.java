package trackit.rest.controllers;


import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.emory.mathcs.backport.java.util.Collections;

import trackit.businessobjects.BankAccount;
import trackit.businessobjects.BankAccountList;
import trackit.service.BankAccountService;


@Controller
public class BankAccountRestController {

	private static final String XML_VIEW_NAME = "marshallingView";
	
	@Autowired
	private BankAccountService bankAccountService;
	
	public void setBankAccountService(BankAccountService bankAccountService) {
		this.bankAccountService = bankAccountService;
	}



	@RequestMapping(method=RequestMethod.GET, value="/bankaccounts/{id}")
	public ModelAndView getBankAccounts(@PathVariable String id) {
		List<BankAccount> bankAccounts = bankAccountService.getAllBankAccountsForAccount(id);
		
		Collections.sort(bankAccounts, new Comparator<BankAccount>() {

			@Override
			public int compare(BankAccount o1, BankAccount o2) {
				return o1.getDescription().compareTo(o2.getDescription());
			}
			
		});
		return new ModelAndView(XML_VIEW_NAME, "object", new BankAccountList(bankAccounts));
	}	
	
}

