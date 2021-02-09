/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.businessobjects;

/**
 *
 * @author Owner
 */
public enum CurrencyOnHandSourceType {
    UNKNOWN(-1),
    INCOME(1),
    LOAN_PAYMENT(2),
    MONEY_ORDER_PURCHASE(3),
    GIFT(4);

    private int id;

    private CurrencyOnHandSourceType(int id) {
        this.id = id;
    }

    public int toInt() {
        return id;
    }

    public static CurrencyOnHandSourceType fromInt(int value) {
        switch(value) {
           case 1: return INCOME;
          case 2: return LOAN_PAYMENT;
          case 3: return MONEY_ORDER_PURCHASE;
          case 4: return GIFT;
          default:
          return UNKNOWN;
        }
    }

}