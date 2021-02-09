/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.businessobjects;

/**
 *
 * @author ndaoud
 */
public class CreditCardPayment extends Entity {
    private Long creditCardId;
    private Long creditCardTransactionId;
    private PaymentMethod paymentMethod;
    private Long transactionId;

    public CreditCardPayment() {}
    
    public Long getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(Long creditCardId) {
        this.creditCardId = creditCardId;
    }

    public Long getCreditCardTransactionId() {
        return creditCardTransactionId;
    }

    public void setCreditCardTransactionId(Long creditCardTransactionId) {
        this.creditCardTransactionId = creditCardTransactionId;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    

}
