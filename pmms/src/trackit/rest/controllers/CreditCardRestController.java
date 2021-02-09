package trackit.rest.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import trackit.businessobjects.CreditCard;
import trackit.businessobjects.CreditCardList;
import trackit.service.CreditCardService;


@Controller
public class CreditCardRestController {

	private static final String XML_VIEW_NAME = "marshallingView";
	
	@Autowired
	private CreditCardService creditCardService;
	
	public void setCreditCardService(CreditCardService creditCardService) {
		this.creditCardService = creditCardService;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/creditcards/{id}")
//	@ResponseBody
	public ModelAndView getCreditCards(@PathVariable String id) {
		List<CreditCard> creditCards = creditCardService.getAllCreditCardsForAccount(id);
		return new ModelAndView(XML_VIEW_NAME, "object", new CreditCardList(creditCards));
	}	
	
}

