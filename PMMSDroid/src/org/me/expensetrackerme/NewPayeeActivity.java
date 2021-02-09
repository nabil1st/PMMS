/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.expensetrackerme;

import org.me.expensetrackerme.services.CachedData;
import org.me.expensetrackerme.xml.ObjectAlreadyExistsException;

import trackit.businessobjects.Payee;

/**
 *
 * @author HP
 */
public class NewPayeeActivity extends NewBasicTypeActivity {
    protected int getViewId() {
        return R.layout.new_payee;
    }
    
    protected void createAndSaveType() throws ObjectAlreadyExistsException {
        String name = entryEditText.getText().toString();
        Payee newPayee = new Payee();
        newPayee.setDescription(name);
        CachedData.getInstance().createPayee(newPayee);
    }

    protected int getExistsMessage() {
        return R.string.payee_exists_message;
    }

}
