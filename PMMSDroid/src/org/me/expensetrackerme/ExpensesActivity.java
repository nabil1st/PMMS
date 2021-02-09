/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.expensetrackerme;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import trackit.businessobjects.Expense;
import trackit.businessobjects.Payee;

import org.me.expensetrackerme.database.LocalDataAccess;
import org.me.expensetrackerme.services.CachedData;
import org.me.expensetrackerme.services.RestfulDataAccessService;
import org.me.expensetrackerme.xml.ExpenseViewBean;
import org.me.expesetrackerme.application.AccountInfo;

/**
 * 
 * @author HP
 */
public class ExpensesActivity extends ListActivity implements
		OnItemClickListener, OnItemLongClickListener {

	private static final int CREATE_NEW_EXPENSE = 0;
	private int dialogResult;

	ExpenseViewBean[] items; // = new String[]{"Auto", "Groceries",
								// "Entertainment"};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Expenses.clear();
		loadExpenses();
		getListView().setTextFilterEnabled(true);

		getListView().setOnItemClickListener(this);
		getListView().setOnItemLongClickListener(this);
	}

	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long id) {
		// Toast.makeText(this, "" + id + " " + position,
		// Toast.LENGTH_LONG).show();

		// Intent myIntent = new Intent(ExpensesActivity.this,
		// ShowExpenseActivity.class);
		// myIntent.putExtra("SelectedCategory", items[position]);
		// ExpensesActivity.this.startActivity(myIntent);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.expenses_options_menu, menu);
		return true;
	}

	/* Handles item selections */
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.new_expense:
			// Toast.makeText(this, "ahha", Toast.LENGTH_LONG).show();
			Intent myIntent = new Intent(ExpensesActivity.this,
					NewExpenseActivity.class);
			ExpensesActivity.this.startActivityForResult(myIntent,
					CREATE_NEW_EXPENSE);
			return true;
		default:
			return true;
		}

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CREATE_NEW_EXPENSE) {
			if (resultCode == RESULT_OK) {

				Toast.makeText(this, R.string.new_category_saved_msg,
						Toast.LENGTH_SHORT);
				// A contact was picked. Here we will just display it
				// to the user.
				// startActivity(new Intent(Intent.ACTION_VIEW, data));

				loadExpenses();
			}
		}
	}

	private void loadExpenses() {
		
		List<Expense> expenses = new ArrayList<Expense>();
		
		if (AccountInfo.getInstance().isOffline()) {
			expenses = LocalDataAccess.getInstance().getSavedExpenses();
		} else {
			expenses = RestfulDataAccessService.getInstance()
				.getAllExpenses();
		}
		
		items = new ExpenseViewBean[expenses.size()];
		// items = new String[expenses.getExpenses().size()];
		for (int i = 0; i < expenses.size(); i++) {
			// items[i] = ((Expense)
			// expenses.getExpenses().get(i)).toString(md);
			Expense expense = (Expense) expenses.get(i);
			Payee payee = CachedData.getInstance().getPayeeById(
					expense.getPayeeId());
			String desc = payee.getDescription() + " "
					+ expense.getAmount().toString();
			if (!expense.isInSync()) {
				desc = "*" + desc;
			}
			items[i] = new ExpenseViewBean(expense.getId(), desc);
		}

		setListAdapter(new ArrayAdapter<ExpenseViewBean>(this,
				android.R.layout.simple_list_item_1, items));
	}

	public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
			int position, long id) {
		// Toast.makeText(this, "" + id + " " + position,
		// Toast.LENGTH_LONG).show();

		// Intent myIntent = new Intent(ExpensesActivity.this,
		// ShowExpenseActivity.class);
		// myIntent.putExtra("SelectedCategory", items[position]);
		// ExpensesActivity.this.startActivity(myIntent);

		// if (dialogResult == -1) {
		// return true;
		// }

		// ExpenseViewBean expenseVB = items[position];
		// try {
		// Expenses expenses = Expenses.load();
		// expenses.removeExpenseById(expenseVB.getExpenseId());
		// expenses.save();
		// loadExpenses();
		// } catch (ParserConfigurationException ex) {
		// Logger.getLogger(ExpensesActivity.class.getName()).log(Level.SEVERE,
		// null, ex);
		// } catch (SAXException ex) {
		// Logger.getLogger(ExpensesActivity.class.getName()).log(Level.SEVERE,
		// null, ex);
		// } catch (IOException ex) {
		// Logger.getLogger(ExpensesActivity.class.getName()).log(Level.SEVERE,
		// null, ex);
		// }
		return true;
	}

	@Override
	public Dialog onCreateDialog(int id) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to exit?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// ExpensesActivity.this.finish();
								dialog.cancel();
								dialogResult = 1;
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						dialogResult = -1;
					}
				});
		AlertDialog alert = builder.create();
		return alert;
	}

}
