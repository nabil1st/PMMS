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
public class NewCreditCardActivity extends NewBalanceTypeActivity {
    
    protected int getViewId() {
        return R.layout.new_credit_card;
    }

    protected int getNameEditTextId() {
        return R.id.cardName;
    }

    protected int getBalanceEditTextId() {
        return R.id.balance;
    }

    protected int getExistsMessage() {
        return R.string.credit_card_exists_message;
    }

    protected BasicType findItem() throws ParserConfigurationException, SAXException, IOException {
        String cardName = nameEditText.getText().toString();
        String balanceStr = balanceEditText.getText().toString();
//        Metadata md = Metadata.init();
        return null;

    }

    protected void createAndSaveItem() throws ParserConfigurationException, SAXException, IOException {
        String cardName = nameEditText.getText().toString();
        String balanceStr = balanceEditText.getText().toString();
        BigDecimal balance = new BigDecimal(balanceStr);

//        Metadata md = Metadata.init();
//        CreditCard cc = new CreditCard(
//            md.getNextCreditCardId(), cardName, balance);
//        md.getCreditCards().add(cc);
//        //Toast.makeText(this, "saving", Toast.LENGTH_LONG).show();
//        md.save();
    }

}
