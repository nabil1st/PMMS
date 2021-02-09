/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.businessobjects;

/**
 *
 * @author Owner
 */
public enum TransactionReason {
    UNKNOWN(-1),
    MONEY_ORDER_PURCHASE(1),
    LOAN(2),
    CREDIT_CARD_PAYMENT(3),
    LOAN_PAYMENT(4);

    private int id;

    private TransactionReason(int id) {
        this.id = id;
    }

    public int toInt() {
        return id;
    }

    public static TransactionReason fromInt(int value) {
        switch(value) {
            case 1: return MONEY_ORDER_PURCHASE;
            case 2: return LOAN;
            case 3: return CREDIT_CARD_PAYMENT;
            case 4: return LOAN_PAYMENT;
            default:
            return UNKNOWN;
        }
    }


}