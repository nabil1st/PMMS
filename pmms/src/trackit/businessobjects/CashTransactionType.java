/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.businessobjects;

/**
 *
 * @author Owner
 */
public enum CashTransactionType {
    UNKNOWN(-1),
    OPENING_BALANCE(1),
    CASH_OUT(2),
    CASH_IN(3);

    private int id;

    private CashTransactionType(int id) {
        this.id = id;
    }

    public int toInt() {
        return id;
    }

    public static CashTransactionType fromInt(int value) {
        switch(value) {
            case 1: return OPENING_BALANCE;
            case 2: return CASH_OUT;
            case 3: return CASH_IN;
            default:
            return UNKNOWN;
        }
    }


}