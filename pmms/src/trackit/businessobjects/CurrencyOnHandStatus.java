/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.businessobjects;

/**
 *
 * @author Owner
 */
public enum CurrencyOnHandStatus {
    UNKNOWN(-1),
    ALL(1),
    ON_HAND(2),
    NOT_ON_HAND(3);


    private int id;

    private CurrencyOnHandStatus(int id) {
        this.id = id;
    }

    public int toInt() {
        return id;
    }

    public static CurrencyOnHandStatus fromInt(int value) {
        switch(value) {
          case 1: return ALL;
          case 2: return ON_HAND;
          case 3: return NOT_ON_HAND;
          default:
          return UNKNOWN;
        }
    }

}