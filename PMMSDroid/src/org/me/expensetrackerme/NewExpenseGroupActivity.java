/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.expensetrackerme;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;

import org.me.expensetrackerme.xml.BasicType;
import org.me.expensetrackerme.xml.ObjectAlreadyExistsException;
import org.xml.sax.SAXException;

/**
 *
 * @author HP
 */
public class NewExpenseGroupActivity extends NewBasicTypeActivity {
    protected int getViewId() {
        return R.layout.new_expense_group;
    }
    
    protected void createAndSaveType() throws ObjectAlreadyExistsException {
        String name = entryEditText.getText().toString();
//        Metadata md = Metadata.init();
//        ExpenseGroup ec = new ExpenseGroup(
//            md.getNextExpenseGroupId(), name);
//        md.getExpenseGroups().add(ec);
//        //Toast.makeText(this, "saving", Toast.LENGTH_LONG).show();
//        md.save();
    }

    protected int getExistsMessage() {
        return R.string.expense_group_exists_message;
    }

}
