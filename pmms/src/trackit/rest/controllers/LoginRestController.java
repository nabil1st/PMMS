package trackit.rest.controllers;


import java.io.StringReader;


import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import trackit.businessobjects.User;
import org.trackit.model.Login;
import trackit.service.UserService;


/**
 * Reference article http://www.ibm.com/developerworks/webservices/library/wa-spring3webserv/index.html
 * @author ndaoud
 *
 */

@Controller
public class LoginRestController {		
	@Autowired
	private UserService userService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	private static final String XML_VIEW_NAME = "marshallingView";
	
	@RequestMapping(method=RequestMethod.POST, value="/login")
	public ModelAndView getPayees(@RequestBody String body) {
		Serializer serializer = new Persister();
		Login e = null;
		try {
			e = serializer.read(Login.class, new StringReader(body));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Login result = new Login();
		User user = userService.findUserByEmail(e.getUserName());
		
		if(user == null || !user.getPassword().equals(e.getPassword())) {
			result.setStatus(Login.LOGIN_FAILURE);			
		} else {
			result.setAccountId(user.getAccountId());
			result.setUserId(user.getId());
			result.setStatus(Login.LOGIN_SUCCESS);
			result.setUserName(user.getFirstName());			
		}
		
		return new ModelAndView(XML_VIEW_NAME, "object", result);
	}
	

	
}

