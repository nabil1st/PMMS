/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.businessobjects;

/**
 *
 * @author Owner
 */
public enum ExpensePurpose {
    UNKNOWN(-1),
    Personal(1),
    Business(2),
    Loan(3);


    private int id;

    private ExpensePurpose(int id) {
        this.id = id;
    }

    public int toInt() {
        return id;
    }

    public static ExpensePurpose fromInt(int value) {
        switch(value) {
          case 1: return Personal;
          case 2: return Business;
          case 3: return Loan;          
          default:
          return UNKNOWN;
        }
    }    
    
}