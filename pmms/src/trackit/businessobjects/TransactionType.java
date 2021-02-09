/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.businessobjects;

/**
 *
 * @author ndaoud
 */
public enum TransactionType {
    UNKNOWN(-1),
    CASH(1),
    BANK(2),
    CREDIT(3),
    LOAN(4);

    private int id;

    private TransactionType(int id) {
        this.id = id;
    }

    public int toInt() {
        return id;
    }

    public static TransactionType fromInt(int value) {
        switch(value) {
           case 1: return CASH;
          case 2: return BANK;
          case 3: return CREDIT;
          case 4: return LOAN;
          default:
          return UNKNOWN;
        }
    }
}
