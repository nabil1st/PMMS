/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.expensetrackerme;

import android.app.Activity;
import android.app.Dialog;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 *
 * @author HP
 */
public class NewExpenseTypeActivity extends Activity implements OnClickListener {

    private EditText expenseTypeNameEditText;
    private static final int EXPENSE_TYPE_ALREADY_EXISTS = 0;
    private static final int EXPENSE_TYPE_EXISTS_DIALOG = 0;

    private String parentCategory;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_expense_type);

        Button okButton = (Button) findViewById(R.id.ok);
        okButton.setOnClickListener(this);

        Button cancelButton = (Button) findViewById(R.id.cancel);
        cancelButton.setOnClickListener(this);

        expenseTypeNameEditText  = (EditText) findViewById(R.id.entry);

        parentCategory = getIntent().getStringExtra("SelectedCategory");

    }

    public void onClick(View view) {

        if (view.getId() == R.id.ok) {

            String typeStr = expenseTypeNameEditText.getText().toString();
//            try {
//                Metadata md = Metadata.init();
//                if (MetadataService.getInstance().getExpenseTypeByName(parentCategory, typeStr) != null) {
//                    showDialog(EXPENSE_TYPE_ALREADY_EXISTS);
//                    return;
//                }
//            } catch (ParserConfigurationException ex) {
//                Logger.getLogger(NewExpenseTypeActivity.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (SAXException ex) {
//                Logger.getLogger(NewExpenseTypeActivity.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//                Logger.getLogger(NewExpenseTypeActivity.class.getName()).log(Level.SEVERE, null, ex);
//            }
            Intent data = new Intent();
            data.putExtra("NewExpenseType", typeStr);
            setResult(RESULT_OK, data);
        } else {
            setResult(RESULT_CANCELED);
        }

        finish();
    }

    protected Dialog onCreateDialog(int id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.expense_type_exists_message);
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
           }
       });

        AlertDialog alert = builder.create();

        return alert;

    }

}
