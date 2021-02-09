package trackit.rest.controllers;

import java.io.StringReader;
import java.util.Comparator;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.emory.mathcs.backport.java.util.Collections;

import trackit.businessobjects.Account;
import trackit.businessobjects.Expense;
import trackit.businessobjects.Payee;
import trackit.businessobjects.PayeeList;
import trackit.service.AccountService;
import trackit.service.PayeeService;

@Controller
public class PayeeRestController {

	@Autowired
	private PayeeService payeeService;
	
	@Autowired
	private AccountService accountService;

	public void setPayeeService(PayeeService ps) {
		this.payeeService = ps;
	}
	
	public void setAccountService(AccountService as) {
		this.accountService = as;
	}

	private static final String XML_VIEW_NAME = "marshallingView";

	@RequestMapping(method = RequestMethod.GET, value = "/payees/{id}")
	// @ResponseBody
	public ModelAndView getPayees(@PathVariable String id) {
		List<Payee> payees = payeeService.getAllPayeesForAccount(id);
		Collections.sort(payees, new Comparator<Payee>() {
			@Override
			public int compare(Payee o1, Payee o2) {
				return o1.getDescription().compareTo(o2.getDescription());
			}
		});

		return new ModelAndView(XML_VIEW_NAME, "object", new PayeeList(payees));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/payee/{id}")
	@ResponseBody
	public Payee getPayee(@PathVariable String id) {
		Payee payee = payeeService.findPayee(id);
		return payee;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/newpayee")
	public ModelAndView createPayee(@RequestBody String body) {
		Serializer serializer = new Persister();
		Payee e = null;
		try {
			e = serializer.read(Payee.class, new StringReader(body));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		e = payeeService.savePayee(e);
		
		// Retrieve account object and add payee to payee list
		Account act = accountService.findAccount(String.valueOf(e.getAccountId()));
		if (act != null) {
			act.getPayees().add(e);
			accountService.saveAccount(act);
		}
		return new ModelAndView(XML_VIEW_NAME, "object", e);
	}

}
