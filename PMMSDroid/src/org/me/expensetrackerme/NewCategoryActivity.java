/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.expensetrackerme;

import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;

import org.me.expensetrackerme.xml.BasicType;
import org.me.expensetrackerme.xml.ObjectAlreadyExistsException;

import trackit.businessobjects.ExpenseCategory;
import org.xml.sax.SAXException;

/**
 *
 * @author HP
 */
public class NewCategoryActivity extends NewBasicTypeActivity {
    protected int getViewId() {
        return R.layout.new_category;
    }

    protected BasicType findBasicType() throws ParserConfigurationException, SAXException, IOException {
        String categoryStr = entryEditText.getText().toString();
//        Metadata md = Metadata.init();
        return null;
    }

    protected void createAndSaveType() throws ObjectAlreadyExistsException {
        String categoryStr = entryEditText.getText().toString();
//        Metadata md = Metadata.init();
//        ExpenseCategory ec = new ExpenseCategory(
//            md.getNextExpenseCategoryId(), categoryStr, new ArrayList());
//        md.getExpenseCategories().add(ec);
//        //Toast.makeText(this, "saving", Toast.LENGTH_LONG).show();
//        md.save();
    }

    protected int getExistsMessage() {
        return R.string.category_exists_message;
    }

}
