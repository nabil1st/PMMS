package trackit.rest.controllers;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.emory.mathcs.backport.java.util.Collections;

import trackit.businessobjects.ExpenseGroup;
import trackit.businessobjects.ExpenseGroupList;
import trackit.businessobjects.Payee;
import trackit.businessobjects.PayeeList;
import trackit.service.ExpenseGroupService;
import trackit.service.PayeeService;

@Controller
public class ExpenseGroupController {

	@Autowired
	private ExpenseGroupService expenseGroupService;
	
	public void setExpenseGroupService(ExpenseGroupService expenseGroupService) {
		this.expenseGroupService = expenseGroupService;
	}

	private static final String XML_VIEW_NAME = "marshallingView";

	@RequestMapping(method = RequestMethod.GET, value = "/expensegroups/{id}")
	public ModelAndView getExpenseGroups(@PathVariable String id) {
		List<ExpenseGroup> expenseGroups = expenseGroupService.getAllExpenseGroupsForAccount(id);
		Collections.sort(expenseGroups, new Comparator<ExpenseGroup>() {
			@Override
			public int compare(ExpenseGroup o1, ExpenseGroup o2) {
				return o1.getDescription().compareTo(o2.getDescription());
			}
		});

		return new ModelAndView(XML_VIEW_NAME, "object", new ExpenseGroupList(expenseGroups));
	}	
}
