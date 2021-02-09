package org.me.expensetrackerme.xml.parser;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.me.expensetrackerme.xml.*;

import trackit.businessobjects.ExpenseSubType;

public class ExpenseTypeHandler extends DefaultHandler{
    private List<ExpenseSubType> list;
    private ExpenseSubType current;
    private StringBuilder builder;
    
    public List<ExpenseSubType> getList(){
        return this.list;
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
        if (this.current != null){
            if (localName.equalsIgnoreCase("id")){
            	current.setId(Long.parseLong(builder.toString()));
            } else if (localName.equalsIgnoreCase("description")){
            	current.setDescription(builder.toString());
            } else if (localName.equalsIgnoreCase("descriptionitem")){
                list.add(current);
            }
            builder.setLength(0);    
        }
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        list = new ArrayList<ExpenseSubType>();
        builder = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String name,
            Attributes attributes) throws SAXException {
        super.startElement(uri, localName, name, attributes);
        if (localName.equalsIgnoreCase("descriptionitem")){
            this.current = new ExpenseSubType();
        }
    }
}

