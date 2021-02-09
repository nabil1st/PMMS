/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.businessobjects;

/**
 *
 * @author Owner
 */
public enum CurrencyOnHandType {
    UNKNOWN(-1),
    CASH(1),
    CHECK(2),
    MONEY_ORDER(3),
    CASHIERS_CHECK(4),
    DIRECT_DEPOSIT(5);

    private int id;

    private CurrencyOnHandType(int id) {
        this.id = id;
    }

    public int toInt() {
        return id;
    }

    public static CurrencyOnHandType fromInt(int value) {
        switch(value) {
           case 1: return CASH;
          case 2: return CHECK;
          case 3: return MONEY_ORDER;
          case 4: return CASHIERS_CHECK;
          case 5: return DIRECT_DEPOSIT;
          default:
          return UNKNOWN;
        }
    }

}