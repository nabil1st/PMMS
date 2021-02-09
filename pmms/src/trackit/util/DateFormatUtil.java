/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author ndaoud
 */
public class DateFormatUtil {

    public static String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        return format.format(date);
    }

    public static Date removeTime(Date toDate) {
        Calendar c = Calendar.getInstance();
        c.setLenient(false);
        c.setTime(toDate);

        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTime();
    }

    public static Date addDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setLenient(false);
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

    public static Date getNextWholeDate(Date date) {
        return addDay(removeTime(date));
    }
    
    public static Date ceiling( Date date )
    {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    
	    cal.add(Calendar.DAY_OF_MONTH, 1);
	    cal.set(Calendar.HOUR, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.MILLISECOND, 0);
	    
	    return cal.getTime();
	    
	    
    }

}
