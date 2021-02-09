/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.businessobjects;

/**
 *
 * @author Owner
 */
public enum BankTransactionType {
    UNKNOWN(-1),
    ATM_WITHDRAWL(1),
    CHECK_WITHDRAWL(2),
    DEBIT(3),
    DEPOSIT(4),
    INTEREST(5),
    BANK_FEE(6),
    OPENING_BALANCE(7),
    TRANSFER_IN(8),
    TRANSFER_OUT(9);

    private int id;

    private BankTransactionType(int id) {
        this.id = id;
    }

    public int toInt() {
        return id;
    }

    public static BankTransactionType fromInt(int value) {
        switch(value) {
            case 1: return ATM_WITHDRAWL;
            case 2: return CHECK_WITHDRAWL;
            case 3: return DEBIT;
            case 4: return DEPOSIT;
            case 5: return INTEREST;
            case 6: return BANK_FEE;
            case 7: return OPENING_BALANCE;
            case 8: return TRANSFER_IN;
            case 9: return TRANSFER_OUT;
            default:
            return UNKNOWN;
        }
    }
    

}