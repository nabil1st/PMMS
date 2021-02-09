/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.expensetrackerme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class MainActivity extends Activity implements OnClickListener {


    //private static final Logger logger = Logger.getLogger(MainActivity.class.getName());
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //try {
            super.onCreate(savedInstanceState);

            //FileHandler fileHandler;// = new SimpleFileHandler();
            setContentView(R.layout.main);
            Button expenseCategoriesButton = (Button) findViewById(R.id.expenseCategories);
            expenseCategoriesButton.setOnClickListener(this);
            Button creditCardsButton = (Button) findViewById(R.id.creditCards);
            creditCardsButton.setOnClickListener(this);
            Button bankAccountsButton = (Button) findViewById(R.id.bankAccounts);
            bankAccountsButton.setOnClickListener(this);
            Button payeesButton = (Button) findViewById(R.id.payees);
            payeesButton.setOnClickListener(this);
            Button incomesButton = (Button) findViewById(R.id.incomes);
            incomesButton.setOnClickListener(this);
            Button expenseGroupsButton = (Button) findViewById(R.id.expense_groups);
            expenseGroupsButton.setOnClickListener(this);
            Button expensesButton = (Button) findViewById(R.id.expenses);
            expensesButton.setOnClickListener(this);

            //Metadata md = new Metadata();
            //md.save();
        //} catch (IOException ex) {
        //    Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
        //}
    }

    public void onClick(View view) {
        Intent myIntent = null;
        
        if (view.getId() == R.id.expenseCategories) {
            myIntent = new Intent(MainActivity.this, ExpenseCategoriesActivity.class);
        } else if (view.getId() == R.id.creditCards) {
            myIntent = new Intent(MainActivity.this, CreditCardsActivity.class);
        } else if (view.getId() == R.id.bankAccounts) {
            myIntent = new Intent(MainActivity.this, BankAccountsActivity.class);
        } else if (view.getId() == R.id.payees) {
            myIntent = new Intent(MainActivity.this, PayeesActivity.class);
        } else if (view.getId() == R.id.expense_groups) {
            myIntent = new Intent(MainActivity.this, ExpenseGroupsActivity.class);
        } else if (view.getId() == R.id.expenses) {
            myIntent = new Intent(MainActivity.this, ExpensesActivity.class);
        }

        MainActivity.this.startActivity(myIntent);
    }

}
