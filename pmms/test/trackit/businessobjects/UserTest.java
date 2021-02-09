/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.businessobjects;

import trackit.businessobjects.User;
import org.hibernate.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import trackit.service.impl.UserServiceImpl;
import trackit.util.HibernateUtil;
import static org.junit.Assert.*;

/**
 *
 * @author Nabil
 */
public class UserTest {

    public UserTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testPersistUser() {
        /*Session session =
            HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();*/
        User user = new User();
        user.setEmail("nabil_1st@hotmail.com");
        user.setFirstName("Bill");
        user.setLastName("Daoud");
        user.setUserName("bill");
        user.setPassword("sankoo7");
        
        
        /*session.save(user);
        session.getTransaction().commit();*/
        
        ClassPathXmlApplicationContext appContext = 
                new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
        
        UserServiceImpl userService = (UserServiceImpl) appContext.getBean("userService");
        
        User savedUser = userService.saveUser(user);
        
        assertTrue(true);
     }

}