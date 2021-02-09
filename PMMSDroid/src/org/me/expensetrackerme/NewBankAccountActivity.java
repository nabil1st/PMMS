/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.expensetrackerme;

import java.io.IOException;
import java.math.BigDecimal;
import javax.xml.parsers.ParserConfigurationException;

import org.me.expensetrackerme.xml.BasicType;
import org.xml.sax.SAXException;

/**
 *
 * @author HP
 */
public class NewBankAccountActivity extends NewBalanceTypeActivity {

    protected int getViewId() {
        return R.layout.new_bank_account;
    }

    protected int getNameEditTextId() {
        return R.id.bankAccountName;
    }

    protected int getBalanceEditTextId() {
        return R.id.balance;
    }

    protected int getExistsMessage() {
        return R.string.bank_account_exists_message;
    }

    protected BasicType findItem() throws ParserConfigurationException, SAXException, IOException {
        String name = nameEditText.getText().toString();
        String balanceStr = balanceEditText.getText().toString();
        //Metadata md = Metadata.init();
        return null;

    }

    protected void createAndSaveItem() throws ParserConfigurationException, SAXException, IOException {
        String name = nameEditText.getText().toString();
        String balanceStr = balanceEditText.getText().toString();
        BigDecimal balance = new BigDecimal(balanceStr);

//        Metadata md = Metadata.init();
//        BankAccount cc = new BankAccount(
//            md.getNextBankAccountId(), name, balance);
//        md.getBankAccounts().add(cc);
//        //Toast.makeText(this, "saving", Toast.LENGTH_LONG).show();
//        md.save();
    }

}
