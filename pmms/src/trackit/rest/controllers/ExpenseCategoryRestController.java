package trackit.rest.controllers;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.emory.mathcs.backport.java.util.Collections;

import trackit.businessobjects.ExpenseCategory;
import trackit.businessobjects.ExpenseCategoryList;
import trackit.businessobjects.ExpenseSubType;
import trackit.businessobjects.ExpenseSubTypeList;
import trackit.service.ExpenseCategoryService;


@Controller
public class ExpenseCategoryRestController {

	@Autowired
	private ExpenseCategoryService expenseCategoryService;
		
	public void setExpenseCategoryService(ExpenseCategoryService service) {
		this.expenseCategoryService = service;
	}

	private static final String XML_VIEW_NAME = "marshallingView";
	
	
	@RequestMapping(method=RequestMethod.GET, value="/expensecategories/{id}")
	public ModelAndView getExpenseCategories(@PathVariable String id) {
		List<ExpenseCategory> categories = expenseCategoryService.getAllExpenseCategoriesForAccount(id);
		
		Collections.sort(categories, new Comparator<ExpenseCategory>() {

			@Override
			public int compare(ExpenseCategory o1, ExpenseCategory o2) {
				return o1.getDescription().compareTo(o2.getDescription());
			}
			
		});
		
		ExpenseCategoryList list = new ExpenseCategoryList(categories);
		
		return new ModelAndView(XML_VIEW_NAME, "object", list);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/expensetypes/{id}")
//	@ResponseBody
	public ModelAndView getExpenseTypes(@PathVariable String id) {
		ExpenseCategory cat = expenseCategoryService.findExpenseCategory(id);
//		List<DescriptionItem> items = new ArrayList<DescriptionItem>();
		
		List<ExpenseSubType> list = new ArrayList<ExpenseSubType>();
		
		if (cat != null) {
			list = cat.getSubTypes();		
//			for (ExpenseSubType st : types) {
//				DescriptionItem i = new DescriptionItem();
//				i.setId(st.getId());
//				i.setDescription(st.getDescription());
//				items.add(i);
//			}
			
//			Collections.sort(items, new DescriptionItemComparator());
			
			Collections.sort(list, new Comparator<ExpenseSubType>() {
				@Override
				public int compare(ExpenseSubType o1, ExpenseSubType o2) {
					return o1.getDescription().compareTo(o2.getDescription());
				}
			});
			
		}
		
		
		return new ModelAndView(XML_VIEW_NAME, "object", new ExpenseSubTypeList(list));
	}
	
}

