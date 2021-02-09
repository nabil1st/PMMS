/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.expensetrackerme;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.me.expensetrackerme.xml.BasicType;
import org.me.expensetrackerme.xml.ObjectAlreadyExistsException;

import trackit.businessobjects.ExpenseCategory;
import org.xml.sax.SAXException;

/**
 *
 * @author HP
 */
public abstract class NewBasicTypeActivity extends Activity implements OnClickListener {

    protected EditText entryEditText;
    private static final int EXISTS_DIALOG = 0;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewId());

        Button okButton = (Button) findViewById(R.id.ok);
        okButton.setOnClickListener(this);

        Button cancelButton = (Button) findViewById(R.id.cancel);
        cancelButton.setOnClickListener(this);

        entryEditText  = (EditText) findViewById(R.id.entry);
    }

    protected abstract int getViewId();

    protected abstract void createAndSaveType() throws ObjectAlreadyExistsException;

    protected int getExistsMessage() {
        return R.string.category_exists_message;
    }

    public void onClick(View view) {

        if (view.getId() == R.id.ok) {
            try {                
            	createAndSaveType();                
            } catch (ObjectAlreadyExistsException ex) {
            	showDialog(EXISTS_DIALOG);
            	return;
            } 

            setResult(RESULT_OK);

        } else {
            setResult(RESULT_CANCELED);
        }

        finish();
    }

    protected Dialog onCreateDialog(int id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getExistsMessage());
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
           }
       });

        AlertDialog alert = builder.create();

        return alert;

    }

}
