/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.businessobjects;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Owner
 */
public class Deposit {
    private Long id;
    private Long bankAccountId;
    private Date date;
    private BigDecimal totalCash;
    private Long userId;
    private Set<CurrencyOnHand> depositItems;



    public Deposit() {}

    public Long getId() {
        return id;
    }

    public void setId(Long accountId) {
        this.id = accountId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public BigDecimal getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(BigDecimal totalCash) {
        this.totalCash = totalCash;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<CurrencyOnHand> getDepositItems() {
        return depositItems;
    }

    public void setDepositItems(Set<CurrencyOnHand> depositItems) {
        this.depositItems = depositItems;
    }


}
