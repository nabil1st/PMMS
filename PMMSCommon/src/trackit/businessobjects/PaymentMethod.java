/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.businessobjects;

/**
 *
 * @author Owner
 */
public enum PaymentMethod {
    UNKNOWN(-1),
    CASH(1),
    CHECK(2),
    MONEY_ORDER(3),
    CASHIERS_CHECK(4),
    CREDIT(5),
    DEBIT(6),
    E_PAYMENT(7);


    private int id;

    private PaymentMethod(int id) {
        this.id = id;
    }

    public int toInt() {
        return id;
    }

    public static PaymentMethod fromInt(int value) {
        switch(value) {
          case 1: return CASH;
          case 2: return CHECK;
          case 3: return MONEY_ORDER;
          case 4: return CASHIERS_CHECK;
          case 5: return CREDIT;
          case 6: return DEBIT;
          case 7: return E_PAYMENT;
          default:
          return UNKNOWN;
        }
    }

}