/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.businessobjects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ndaoud
 */
public class Utils {

    public static Map<Long,Payee> buildPayeeLookupMap(List<Payee> list) {
        Map<Long,Payee> map = new HashMap<Long,Payee>();
        for (Payee payee : list) {
            map.put(payee.getId(), payee);
        }

        return map;
    }

    public static Map<Long,ExpenseGroup> buildExpenseGroupLookupMap(List<ExpenseGroup> list) {
        Map<Long,ExpenseGroup> map = new HashMap<Long,ExpenseGroup>();
        for (ExpenseGroup eg : list) {
            map.put(eg.getId(), eg);
        }

        return map;
    }

}
