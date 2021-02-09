/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author HP
 */
public class UserPermissions {

    public static String[][] PERMISSIONS = {
                       //{"ROLE_CAN_VIEW_HOME_PAGE", "View home page"},
                       {"ROLE_CAN_VIEW_BANK_INFO", "View bank information"},
                       {"ROLE_CAN_VIEW_CREDIT_CARD_INFO", "View credit card information"},
                       {"ROLE_CAN_VIEW_EXPENSE_INFO", "View expense information"},
                       {"ROLE_CAN_VIEW_USER_INFO", "View user information"},
                       {"ROLE_CAN_VIEW_LOAN_INFO", "View loan information"},
                       {"ROLE_CAN_VIEW_USER_PERMISSIONS", "View User Permissions"},
                       {"ROLE_CAN_VIEW_CASH_TRANSFERS", "View Cash Transfers"},
                       {"ROLE_CAN_VIEW_CURRENCY_ON_HAND", "View Currency On Hand"},
//                       {"ROLE_CAN_VIEW_EXPENSE_GROUP_REPORT", "View Project Report"},
//                       {"ROLE_CAN_VIEW_CAPITAL_REPORT", "View Capital Report"},
                       {"ROLE_CAN_VIEW_CHARTS", "View Reports"}};

    public static List<String> getAllPermissions() {
        List<String> list = new ArrayList<String>();
        for (int i=0; i<UserPermissions.PERMISSIONS.length; i++) {
            list.add(PERMISSIONS[i][0]);
        }

        return list;
    }

    public static String buildDBValue(List<String> permissions) {
        
        StringBuffer buffer = new StringBuffer();
        int index = 0;
        for (String perm : permissions) {
            buffer.append(perm);
            if (index < permissions.size() - 1) {
                buffer.append(",");
            }
            index++;

        }

        return buffer.toString();
    }

    public static List<String> parseDBValue(String str) {
        List<String> permissions = new ArrayList<String>();
        StringTokenizer t = new StringTokenizer(str, ",");
        while (t.hasMoreTokens()) {
            permissions.add(t.nextToken());
        }

        return permissions;

    }

    
}
