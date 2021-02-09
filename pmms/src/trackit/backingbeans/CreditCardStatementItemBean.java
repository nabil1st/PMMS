/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Owner
 */
public class CreditCardStatementItemBean extends BaseBean {

    public enum CreditCardStatementItemType {
        REFUND,
        CHARGE,
        PAYMENT
    };
    private Long id;
    private CreditCardStatementItemType itemType;
    private String description;
    private Date date;
    private String charge;
    private String refund;
    private String payment;

    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public CreditCardStatementItemType getItemType() {
        return itemType;
    }

    public void setItemType(CreditCardStatementItemType itemType) {
        this.itemType = itemType;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getRefund() {
        return refund;
    }

    public void setRefund(String refund) {
        this.refund = refund;
    }
    
}
