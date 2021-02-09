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
public class StatementItemBean extends BaseBean {

    public enum StatementItemType {
        EXPENSE,
        DEPOSIT,
        REFUND
    };
    private Long id;
    private StatementItemType itemType;
    private String checkNumber;
    private String description;
    private Date date;
    private String withdrawl;
    private String deposit;

    public String getWithdrawl() {
        return withdrawl;
    }

    public void setWithdrawl(String amount) {
        this.withdrawl = amount;
    }

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

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public StatementItemType getItemType() {
        return itemType;
    }

    public void setItemType(StatementItemType itemType) {
        this.itemType = itemType;
    }
}
