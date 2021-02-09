package org.me.expensetrackerme.xml.parser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import trackit.businessobjects.*;

public class CreditCardHandler extends DefaultHandler{
    private List<CreditCard> creditCards;
    private CreditCard currentCreditCard;
    private StringBuilder builder;
    
    public List<CreditCard> getCreditCards(){
        return this.creditCards;
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
        if (this.currentCreditCard != null){
            if (localName.equalsIgnoreCase("id")){
            	currentCreditCard.setId(Long.parseLong(builder.toString()));
            } else if (localName.equalsIgnoreCase("description")){
            	currentCreditCard.setDescription(builder.toString());
            } else if (localName.equalsIgnoreCase("balance")){
            	currentCreditCard.setBalance(new BigDecimal(builder.toString()));
            } else if (localName.equalsIgnoreCase("creditcard")){
                creditCards.add(currentCreditCard);
            }
            builder.setLength(0);    
        }
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        creditCards = new ArrayList<CreditCard>();
        builder = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String name,
            Attributes attributes) throws SAXException {
        super.startElement(uri, localName, name, attributes);
        if (localName.equalsIgnoreCase("creditcard")){
            this.currentCreditCard = new CreditCard();
        }
    }
}

