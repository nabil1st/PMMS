package trackit.rest.controllers;


import java.io.StringReader;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.emory.mathcs.backport.java.util.Collections;

import trackit.businessobjects.Expense;
import trackit.businessobjects.ExpenseList;
import trackit.businessobjects.Payee;
import trackit.businessobjects.PayeeList;
import trackit.service.ExpenseService;
import trackit.service.PayeeService;


/**
 * Reference article http://www.ibm.com/developerworks/webservices/library/wa-spring3webserv/index.html
 * @author ndaoud
 *
 */

@Controller
public class ExpenseRestController {

	@Autowired
	private ExpenseService expenseService;
		
	private static final String XML_VIEW_NAME = "marshallingView";
	
	@RequestMapping(method=RequestMethod.POST, value="/newexpense")
	public ModelAndView createExpense(@RequestBody String body) {
//		Source source = new StreamSource(new StringReader(body));
		
		Serializer serializer = new Persister();
		Expense e = null;
		try {
			e = serializer.read(Expense.class, new StringReader(body));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		Expense e = (Expense) jaxb2Mashaller.unmarshal(source);
		e.validate();
//		e.setUserId(new Long(3));
		e = expenseService.saveExpense(e);		
		return new ModelAndView(XML_VIEW_NAME, "object", e);
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/expenses/{id}")
	// @ResponseBody
	public ModelAndView getExpenses(@PathVariable String id) {
		Date currentDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.add(Calendar.DAY_OF_MONTH, -10);
		Date prevDate = cal.getTime();
		
		List<Expense> expenses = expenseService.getExpensesForAccount(id, prevDate, currentDate);
		
		return new ModelAndView(XML_VIEW_NAME, "object", new ExpenseList(expenses));
	}
	
//	@RequestMapping(method=RequestMethod.GET, value="/employee/{id}")
//	public ModelAndView getEmployee(@PathVariable String id) {
//		Employee e = employeeDS.get(Long.parseLong(id));
//		return new ModelAndView(XML_VIEW_NAME, "object", e);
//	}
//	
//	@RequestMapping(method=RequestMethod.PUT, value="/employee/{id}")
//	public ModelAndView updateEmployee(@RequestBody String body) {
//		Source source = new StreamSource(new StringReader(body));
//		Employee e = (Employee) jaxb2Mashaller.unmarshal(source);
//		employeeDS.update(e);
//		return new ModelAndView(XML_VIEW_NAME, "object", e);
//	}
//	
//	@RequestMapping(method=RequestMethod.POST, value="/employee")
//	public ModelAndView addEmployee(@RequestBody String body) {
//		Source source = new StreamSource(new StringReader(body));
//		Employee e = (Employee) jaxb2Mashaller.unmarshal(source);
//		employeeDS.add(e);
//		return new ModelAndView(XML_VIEW_NAME, "object", e);
//	}
//	
//	@RequestMapping(method=RequestMethod.DELETE, value="/employee/{id}")
//	public ModelAndView removeEmployee(@PathVariable String id) {
//		employeeDS.remove(Long.parseLong(id));
//		List<Employee> employees = employeeDS.getAll();
//		EmployeeList list = new EmployeeList(employees);
//		return new ModelAndView(XML_VIEW_NAME, "employees", list);
//	}
//	
//	@RequestMapping(method=RequestMethod.GET, value="/employees")
//	public ModelAndView getEmployees() {
//		List<Employee> employees = employeeDS.getAll();
//		EmployeeList list = new EmployeeList(employees);
//		return new ModelAndView(XML_VIEW_NAME, "employees", list);
//	}	
	
}

