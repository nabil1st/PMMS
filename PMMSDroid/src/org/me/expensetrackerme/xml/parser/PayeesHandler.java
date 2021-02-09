package org.me.expensetrackerme.xml.parser;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import trackit.businessobjects.*;

public class PayeesHandler extends DefaultHandler{
    private List<Payee> payees;
    private Payee currentPayee;
    private StringBuilder builder;
    
    public List<Payee> getPayees(){
        return this.payees;
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
        if (this.currentPayee != null){
            if (localName.equalsIgnoreCase("id")){
                currentPayee.setId(Long.parseLong(builder.toString()));
            } else if (localName.equalsIgnoreCase("description")){
                currentPayee.setDescription(builder.toString());
            } else if (localName.equalsIgnoreCase("payee")){
                payees.add(currentPayee);
            }
            builder.setLength(0);    
        }
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        payees = new ArrayList<Payee>();
        builder = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String name,
            Attributes attributes) throws SAXException {
        super.startElement(uri, localName, name, attributes);
        if (localName.equalsIgnoreCase("payee")){
            this.currentPayee = new Payee();
        }
    }
}

