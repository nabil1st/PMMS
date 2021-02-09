/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.expensetrackerme;

import java.util.List;

import org.me.expensetrackerme.services.CachedData;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import trackit.businessobjects.BankAccount;

/**
 *
 * @author HP
 */
public class SelectCheckActivity extends Activity implements OnClickListener {

    private Spinner bankAccountSpinner;
    private EditText checkNumber;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewId());

        setupBankAccountSpinner();

        checkNumber = (EditText) findViewById(R.id.check_number);


        Button okButton = (Button) findViewById(R.id.ok);
        okButton.setOnClickListener(this);

        Button cancelButton = (Button) findViewById(R.id.cancel);
        cancelButton.setOnClickListener(this);
    }

    protected int getViewId() {
        return R.layout.select_check;
    }

    public void onClick(View view) {

        if (view.getId() == R.id.ok) {

            Intent data = new Intent();
            BankAccount selectedBankAccount = (BankAccount) bankAccountSpinner.getSelectedItem();
            data.putExtra("BankAccountId", selectedBankAccount.getId());
            data.putExtra("CheckNumber", checkNumber.getText().toString());
            data.putExtra("Description", selectedBankAccount.getDescription() + " " + checkNumber.getText().toString());
            setResult(RESULT_OK, data);
        } else {
            setResult(RESULT_CANCELED);
        }

        finish();
    }

    private void setupBankAccountSpinner() {
        bankAccountSpinner = (Spinner) findViewById(R.id.bank_accounts_spinner);

        List<BankAccount> bankAccounts = CachedData.getInstance().getBankAccounts();
        BankAccount[] items = bankAccounts.toArray(new BankAccount[]{});
        ArrayAdapter<BankAccount> adapter =
                new ArrayAdapter<BankAccount>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bankAccountSpinner.setAdapter(adapter);
    }

}
