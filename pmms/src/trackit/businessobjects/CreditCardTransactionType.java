/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.businessobjects;

/**
 *
 * @author Owner
 */
public enum CreditCardTransactionType {
    UNKNOWN(-1),
    FINANCE_CHARGE(1),
    LATE_FEE(2),
    CASH_ADVANCE_CHARGE(3),
    PURCHASE(4),
    CASH_BACK(5),
    PAYMENT(6),
    ANNUAL_FEE(7),
    OPENING_BALANCE(8),
    BALANCE_TRANSFER(9);

    private int id;

    private CreditCardTransactionType(int id) {
        this.id = id;
    }

    public int toInt() {
        return id;
    }

    public static CreditCardTransactionType fromInt(int value) {
        switch(value) {
            case 1: return FINANCE_CHARGE;
            case 2: return LATE_FEE;
            case 3: return CASH_ADVANCE_CHARGE;
            case 4: return PURCHASE;
            case 5: return CASH_BACK;
            case 6: return PAYMENT;
            case 7: return ANNUAL_FEE;
            case 8: return OPENING_BALANCE;
            case 9: return BALANCE_TRANSFER;
            default:
            return UNKNOWN;
        }
    }
    
    

}