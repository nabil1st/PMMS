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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.me.expensetrackerme.xml.BasicType;
import org.xml.sax.SAXException;

/**
 *
 * @author HP
 */
public abstract class NewBalanceTypeActivity extends Activity implements OnClickListener {

    protected EditText nameEditText;
    protected EditText balanceEditText;
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

        nameEditText  = (EditText) findViewById(getNameEditTextId());
        balanceEditText = (EditText) findViewById(getBalanceEditTextId());
    }

    protected abstract int getViewId();

    protected abstract int getNameEditTextId();

    protected abstract int getBalanceEditTextId();

    protected abstract int getExistsMessage();

    protected abstract BasicType findItem() throws ParserConfigurationException, SAXException, IOException;

    protected abstract void createAndSaveItem() throws ParserConfigurationException, SAXException, IOException;

    public void onClick(View view) {

        if (view.getId() == R.id.ok) {
            try {
                BasicType ec = findItem();
                if (ec == null) {
                    createAndSaveItem();
                } else {
                    showDialog(EXISTS_DIALOG);
                    return;
                }


            } catch (ParserConfigurationException ex) {
                Logger.getLogger(NewBasicTypeActivity.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(NewBasicTypeActivity.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(NewBasicTypeActivity.class.getName()).log(Level.SEVERE, null, ex);
            }

            setResult(RESULT_OK);

        } else {
            setResult(RESULT_CANCELED);
        }

        finish();
    }

    @Override
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
