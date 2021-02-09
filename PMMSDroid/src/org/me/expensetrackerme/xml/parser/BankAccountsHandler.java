package org.me.expensetrackerme.xml.parser;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import trackit.businessobjects.*;

public class BankAccountsHandler extends DefaultHandler{
    private List<BankAccount> bankAccounts;
    private BankAccount currentBankAccount;
    private StringBuilder builder;
    
    public List<BankAccount> getBankAccounts(){
        return this.bankAccounts;
    }
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        super.characters(ch, start, length);
        builder.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String name)
            throws SAXException {
        super.endElement(uri, localName, name);
        if (this.currentBankAccount != null){
            if (localName.equalsIgnoreCase("id")){
            	currentBankAccount.setId(Long.parseLong(builder.toString()));
            } else if (localName.equalsIgnoreCase("description")){
            	currentBankAccount.setDescription(builder.toString());
            } else if (localName.equalsIgnoreCase("payee")){
                bankAccounts.add(currentBankAccount);
            }
            builder.setLength(0);    
        }
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        bankAccounts = new ArrayList<BankAccount>();
        builder = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String name,
            Attributes attributes) throws SAXException {
        super.startElement(uri, localName, name, attributes);
        if (localName.equalsIgnoreCase("payee")){
            this.currentBankAccount = new BankAccount();
        }
    }
}

