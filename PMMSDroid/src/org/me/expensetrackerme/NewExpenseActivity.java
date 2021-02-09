/*
+
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.expensetrackerme;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.me.expensetrackerme.database.LocalDataAccess;
import org.me.expensetrackerme.services.CachedData;
import org.me.expensetrackerme.services.RestfulDataAccessService;
import org.me.expesetrackerme.application.AccountInfo;

import trackit.businessobjects.BankAccount;
import trackit.businessobjects.CreditCard;
import trackit.businessobjects.Expense;
import trackit.businessobjects.ExpensePurpose;
import trackit.businessobjects.Payee;
import trackit.businessobjects.ExpenseGroup;
import trackit.businessobjects.PaymentMethod;

/**
 * 
 * @author HP
 */
public class NewExpenseActivity extends Activity 
				implements OnClickListener, OnItemSelectedListener {

	protected EditText entryEditText;
	private static final int EXISTS_DIALOG = 0;
	private static final int DATE_DIALOG_ID = 1;

	private int SELECT_CREDIT_CARD = 1;
	private int SELECT_BANK_ACCOUNT = 2;
	private int SELECT_CHECK = 3;
	private int SELECT_EXPENSE_TYPE = 4;
	private int TAKE_PHOTO = 5;

	private int mYear;
	private int mMonth;
	private int mDay;

	private Spinner payeesSpinner;
	private Spinner expenseGroupsSpinner;
	private Spinner paymentMethodsSpinner;
	private Button changeDateButton;
	private Button selectExpenseType;

	private Button photoButton;
	private Button okButton;
	private Button cancelButton;

	private TextView paymentMethodSummary;
	private TextView amount;

	private Expense expense;

	private boolean ignoreSelection = false;

	// the callback received when the user "sets" the date in the dialog
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDateDisplay();
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, mYear);
			cal.set(Calendar.MONTH, mMonth);
			cal.set(Calendar.DAY_OF_MONTH, mDay);
			expense.setDate(cal.getTime());
		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getViewId());
		
		Object data = getLastNonConfigurationInstance();
		if (data != null) {
			expense = (Expense) data;
		} else {
			expense = new Expense();
			expense.setExpensePurpose(ExpensePurpose.Personal);
		}
		
		findUIComponents();
		
		setupPayeesSpinnerData();
		setupExpenseGroupsSpinnerData();
		setupPaymentMethodsSpinnerData();
		
		expenseGroupsSpinner.setOnItemSelectedListener(this);
		changeDateButton.setOnClickListener(this);

		// add a click listener to the button
//		changeDateButton.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
//				showDialog(DATE_DIALOG_ID);
//			}
//		});

		
		okButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);
		photoButton.setOnClickListener(this);
		selectExpenseType.setOnClickListener(this);
		
		initializeAmountChangeListener();

		initializeDate();
		
		if (data != null) {
			populateViewFromModel();
		} 
		
		initializePaymentMethodSelectionListener();
			
	}
	
	private void initializeAmountChangeListener() {
		amount.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				try {
					Double.parseDouble(amount.getText().toString());
				} catch (NumberFormatException ex) {
					Toast.makeText(NewExpenseActivity.this, "Amount entered not valid",
							Toast.LENGTH_SHORT).show();
				}

				expense.setAmount(new BigDecimal(amount.getText().toString()));
				
			}
		});
	}
	
	private void initializePaymentMethodSelectionListener() {
		paymentMethodsSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (ignoreSelection) {
					ignoreSelection = false;
					return;
				}

				switch (arg0.getId()) {
				case R.id.payment_methods_spinner:
					PaymentMethod selected = (PaymentMethod) paymentMethodsSpinner
							.getSelectedItem();
					if (selected.equals(PaymentMethod.CREDIT)) {
						Intent myIntent = new Intent(
								NewExpenseActivity.this,
								SelectCreditCardActivity.class);
						startActivityForResult(myIntent,
								SELECT_CREDIT_CARD);
					} else if (selected.equals(PaymentMethod.DEBIT)) {
						Intent myIntent = new Intent(
								NewExpenseActivity.this,
								SelectBankAccountActivity.class);
						startActivityForResult(myIntent,
								SELECT_BANK_ACCOUNT);
					} else if (selected.equals(PaymentMethod.CHECK)) {
						Intent myIntent = new Intent(
								NewExpenseActivity.this,
								SelectCheckActivity.class);
						startActivityForResult(myIntent, SELECT_CHECK);
					} else {
						expense.setCreditCardId(new Long(-1));
						expense.setBankAccountId(new Long(-1));
						expense.setCheckNumber("");
						paymentMethodSummary.setText("");
					}
				}
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				System.out.println("Nothing selected");
			}

		});
	}
	
	private void initializeDate() {
		// get the current date
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		
		expense.setDate(c.getTime());

		// display the current date
		updateDateDisplay();
	}
	
	private void findUIComponents() {
		changeDateButton = (Button) findViewById(R.id.change_date);
		selectExpenseType = (Button) findViewById(R.id.select_expense_type);
		okButton = (Button) findViewById(R.id.ok);
		cancelButton = (Button) findViewById(R.id.cancel);
		photoButton = (Button) findViewById(R.id.photo);
		paymentMethodSummary = (TextView) findViewById(R.id.payment_method_summary);
		amount = (TextView) findViewById(R.id.amount);
		payeesSpinner = (Spinner) findViewById(R.id.payees_spinner);
		expenseGroupsSpinner = (Spinner) findViewById(R.id.expense_groups_spinner);
		paymentMethodsSpinner = (Spinner) findViewById(R.id.payment_methods_spinner);
	}
	
	private void populateViewFromModel() {
		Payee[] items = CachedData.getInstance().getPayees()
			.toArray(new Payee[] {});
		
		if (expense.getPayeeId() != null && expense.getPayeeId() > -1) {
			for (int i=0; i<items.length; i++) {
				if (items[i].getId().longValue() == expense.getPayeeId().longValue()) {
					payeesSpinner.setSelection(i);
					break;
				}
			}			
		}
		
		List<ExpenseGroup> expenseGroups = new ArrayList<ExpenseGroup>();
		expenseGroups.addAll(CachedData.getInstance().getExpenseGroups());
		ExpenseGroup eg = new ExpenseGroup();
		eg.setId(new Long(-1));
		eg.setDescription("");
		expenseGroups.add(0, eg);
		
		ExpenseGroup[] egs = (ExpenseGroup[]) expenseGroups
				.toArray(new ExpenseGroup[] {});
		if (expense.getExpenseGroupId() != null && expense.getExpenseGroupId() > -1) {
			for (int i=0; i<egs.length; i++) {
				if (egs[i].getId().longValue() == expense.getExpenseGroupId().longValue()) {
					expenseGroupsSpinner.setSelection(i);
					break;
				}
			}
			
		}
		
		
		PaymentMethod[] pms = new PaymentMethod[] { PaymentMethod.CASH,
				PaymentMethod.CREDIT, PaymentMethod.DEBIT, PaymentMethod.CHECK };
		
		if (expense.getPaymentMethod() != null) {
			for (int i=0; i<pms.length; i++) {
				if (pms[i].equals(expense.getPaymentMethod())) {
					payeesSpinner.setSelection(i);
					
					if (pms[i].equals(PaymentMethod.CREDIT)) {
						if (expense.getCreditCardId() != null && 
								expense.getCreditCardId().longValue() > -1) {
							CreditCard cc = CachedData.getInstance().getCreditCardById(
								expense.getCreditCardId());
							paymentMethodSummary.setText(cc.getDescription());
						}						
					} else if (pms[i].equals(PaymentMethod.DEBIT)) {
						if (expense.getBankAccountId() != null &&
								expense.getBankAccountId().longValue() > -1) {
							BankAccount ba = CachedData.getInstance().getBankAccountById(
									expense.getBankAccountId());
							paymentMethodSummary.setText(ba.getDescription());
						}
					} else if (pms[i].equals(PaymentMethod.CHECK)) {
						if (expense.getBankAccountId() != null &&
								expense.getBankAccountId().longValue() > -1) {
							BankAccount ba = CachedData.getInstance().getBankAccountById(
									expense.getBankAccountId());
							paymentMethodSummary.setText(ba.getDescription());
						}
					} else {
						paymentMethodSummary.setText("");
					}
					
					break;
				}
			}
		}
		
		amount.setText(expense.getAmount().toString());
		
		if (expense.getDate() != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(expense.getDate());
			mYear = cal.get(Calendar.YEAR);
			mMonth = cal.get(Calendar.MONTH);
			mDay = cal.get(Calendar.DAY_OF_MONTH);
			
			updateDateDisplay();
		}
		
		if (expense.getExpenseTypeId() != null && 
				expense.getExpenseTypeId().longValue() > -1) {
			selectExpenseType.setText(expense.getExpenseTypeStr());
		}
		
	}

	protected int getViewId() {
		return R.layout.new_expense;
	}

	// protected abstract BasicType findBasicType() throws
	// ParserConfigurationException, SAXException, IOException;

	// protected abstract void createAndSaveType() throws
	// ParserConfigurationException, SAXException, IOException;

	protected int getExistsMessage() {
		return R.string.category_exists_message;
	}

	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		case EXISTS_DIALOG:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(getExistsMessage());
			builder.setNegativeButton("Ok",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});

			AlertDialog alert = builder.create();
			return alert;
		default:
			return null;
		}

	}

	private void setupPayeesSpinnerData() {
		Payee[] items = CachedData.getInstance().getPayees()
				.toArray(new Payee[] {});
		ArrayAdapter<Payee> payeesAdapter = new ArrayAdapter<Payee>(this,
				android.R.layout.simple_spinner_item, items);
		payeesAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		payeesSpinner.setAdapter(payeesAdapter);
	}

	private void setupExpenseGroupsSpinnerData() {		
		List<ExpenseGroup> expenseGroups = new ArrayList<ExpenseGroup>();
		expenseGroups.addAll(CachedData.getInstance().getExpenseGroups());
		ExpenseGroup eg = new ExpenseGroup();
		eg.setId(new Long(-1));
		eg.setDescription("");
		expenseGroups.add(0, eg);
		
		ExpenseGroup[] items = (ExpenseGroup[]) expenseGroups
				.toArray(new ExpenseGroup[] {});
		ArrayAdapter<ExpenseGroup> adapter = new ArrayAdapter<ExpenseGroup>(
				this, android.R.layout.simple_spinner_item, items);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		expenseGroupsSpinner.setAdapter(adapter);	
	}

	private void setupPaymentMethodsSpinnerData() {		
		PaymentMethod[] items = new PaymentMethod[] { PaymentMethod.CASH,
				PaymentMethod.CREDIT, PaymentMethod.DEBIT, PaymentMethod.CHECK };

		ArrayAdapter<PaymentMethod> adapter = new ArrayAdapter<PaymentMethod>(
				this, android.R.layout.simple_spinner_item, items);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		paymentMethodsSpinner.setAdapter(adapter);
	}

	// updates the date we display in the TextView
	private void updateDateDisplay() {
		StringBuilder date = new StringBuilder()
				// Month is 0 based so add 1
				.append(mMonth + 1).append("-").append(mDay).append("-")
				.append(mYear).append(" ");

		changeDateButton.setText(date);	
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_CANCELED) {
			ignoreSelection = true;
			if (expense.getBankAccountId() > 0
					&& expense.getCheckNumber() != null
					&& expense.getCheckNumber().trim().length() > 0) {
				paymentMethodsSpinner.setSelection(3);
			} else if (expense.getBankAccountId() > 0) {
				paymentMethodsSpinner.setSelection(2);
			} else if (expense.getCreditCardId() > 0) {
				paymentMethodsSpinner.setSelection(1);
			} else {
				paymentMethodsSpinner.setSelection(0);
			}

		} else {
			if (requestCode == SELECT_CREDIT_CARD) {
				expense.setPaymentMethod((PaymentMethod) paymentMethodsSpinner
						.getSelectedItem());
				expense.setCreditCardId(data.getLongExtra("CreditCardId",
						resultCode));
				paymentMethodSummary.setText(data
						.getStringExtra("CreditCardName"));
			} else if (requestCode == SELECT_BANK_ACCOUNT) {
				expense.setPaymentMethod((PaymentMethod) paymentMethodsSpinner
						.getSelectedItem());
				expense.setBankAccountId(data.getLongExtra("BankAccountId",
						resultCode));
				paymentMethodSummary.setText(data
						.getStringExtra("BankAccountName"));
			} else if (requestCode == SELECT_CHECK) {
				expense.setPaymentMethod((PaymentMethod) paymentMethodsSpinner
						.getSelectedItem());
				expense.setBankAccountId(data.getLongExtra("BankAccountId",
						resultCode));
				expense.setCheckNumber(data.getStringExtra("CheckNumber"));
				paymentMethodSummary
						.setText(data.getStringExtra("Description"));
			} else if (requestCode == SELECT_EXPENSE_TYPE) {
				expense.setExpenseTypeId(data.getLongExtra("ExpenseTypeId",
						resultCode));
				selectExpenseType.setText(data
						.getStringExtra("ExpenseTypeName"));
				expense.setExpenseTypeStr(data
						.getStringExtra("ExpenseTypeName"));
			} else if (requestCode == TAKE_PHOTO) {
				// change text of button to photo name
				long photoId = -1;
				// try {
				// photoId = Expenses.load().getNextExpenseId();
				// } catch (ParserConfigurationException ex) {
				// Logger.getLogger(NewExpenseActivity.class.getName()).log(Level.SEVERE,
				// null, ex);
				// } catch (SAXException ex) {
				// Logger.getLogger(NewExpenseActivity.class.getName()).log(Level.SEVERE,
				// null, ex);
				// } catch (IOException ex) {
				// Logger.getLogger(NewExpenseActivity.class.getName()).log(Level.SEVERE,
				// null, ex);
				// }

				if (photoId > -1) {
					photoButton.setText("photo" + photoId + ".jpg");
				}
			}
		}
	}

	public void onClick(View view) {
		Intent myIntent;
		switch (view.getId()) {
		case R.id.payment_methods_spinner:
			myIntent = new Intent(this, SelectCreditCardActivity.class);
			startActivityForResult(myIntent, SELECT_CREDIT_CARD);
			break;
		case R.id.select_expense_type:
			myIntent = new Intent(this, SelectExpenseTypeActivity.class);
			startActivityForResult(myIntent, SELECT_EXPENSE_TYPE);
			break;
		case R.id.change_date:
			showDialog(DATE_DIALOG_ID);
			break;
		case R.id.photo:
			myIntent = new Intent(this, PhotoActivity.class);
			// try {
			// long nextExpenseId = Expenses.load().getNextExpenseId();
			// myIntent.putExtra("NextExpenseId", nextExpenseId);
			// } catch (ParserConfigurationException ex) {
			// Logger.getLogger(NewExpenseActivity.class.getName()).log(Level.SEVERE,
			// null, ex);
			// } catch (SAXException ex) {
			// Logger.getLogger(NewExpenseActivity.class.getName()).log(Level.SEVERE,
			// null, ex);
			// } catch (IOException ex) {
			// Logger.getLogger(NewExpenseActivity.class.getName()).log(Level.SEVERE,
			// null, ex);
			// }
			startActivityForResult(myIntent, TAKE_PHOTO);
			break;
		case R.id.ok:
			// Validate amount
			if (expense.getAmount() == null || expense.getAmount().doubleValue() <= 0) {
				Toast.makeText(this, "Amount value must be entered",
						Toast.LENGTH_SHORT).show();
			}
			
//			if (amount.getText() == null || amount.getText().length() == 0) {
//				Toast.makeText(this, "Amount value must be entered",
//						Toast.LENGTH_SHORT).show();
//			}

			/*try {
				Double.parseDouble(amount.getText().toString());
			} catch (NumberFormatException ex) {
				Toast.makeText(this, "Amount entered not valid",
						Toast.LENGTH_SHORT).show();
			}

			expense.setAmount(new BigDecimal(amount.getText().toString()));*/

			ExpenseGroup eg = (ExpenseGroup) expenseGroupsSpinner
					.getSelectedItem();
			if (eg != null) {
				expense.setExpenseGroupId(eg.getId());
			}

			Payee p = (Payee) payeesSpinner.getSelectedItem();
			if (p != null) {
				expense.setPayeeId(p.getId());
			}

			if (expense.getPaymentMethod() == null) {
				expense.setPaymentMethod(PaymentMethod.CASH);
			}
			
			if (AccountInfo.getInstance().isOffline()) {
				LocalDataAccess.getInstance().createUnsyncedExpense(expense);
			} else {
				Expense saved = RestfulDataAccessService.getInstance().saveExpense(
					expense);
			}

			Intent data = new Intent();
			setResult(RESULT_OK, data);
			finish();
			break;
		case R.id.cancel:
			Intent data_ = new Intent();
			setResult(RESULT_CANCELED, data_);
			finish();
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> view, View arg1, int arg2,
			long arg3) {		
		switch (view.getId()) {
			case R.id.expense_groups_spinner:
				ExpenseGroup selected = (ExpenseGroup) expenseGroupsSpinner.getSelectedItem();
				expense.setExpenseGroupId(selected.getId());
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Object onRetainNonConfigurationInstance() {
		return expense;
	}
}
