/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.businessobjects;

import java.math.BigDecimal;

/**
 *
 * @author Owner
 */
public class MoneyOrderFee {
    private Long id;
    private Long moneyOrderId;
    private BigDecimal fee;

    public MoneyOrderFee() {}

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMoneyOrderId() {
        return moneyOrderId;
    }

    public void setMoneyOrderId(Long moneyOrderId) {
        this.moneyOrderId = moneyOrderId;
    }

}
