/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.expensetrackerme;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;

import org.me.expensetrackerme.services.CachedData;
import trackit.businessobjects.ExpenseCategory;
import trackit.businessobjects.ExpenseSubType;
import org.xml.sax.SAXException;

/**
 *
 * @author HP
 */
public class ExpenseTypesActivity extends ListActivity {

    private int CREATE_NEW_EXPENSE_TYPE = 1;
    
    private String[] items;
    private String parentCategory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      parentCategory = getIntent().getStringExtra("SelectedCategory");
      loadExpenseTypes();
      getListView().setTextFilterEnabled(true);
    }

    private void loadExpenseTypes() {
        
//            IMetadataService md = MetadataServiceFactory.getMetadataService().init();
//            items = md.getExpenseTypesNamesForCategory(parentCategory);
    	ExpenseCategory ec = CachedData.getInstance().getExpenseCategoryByName(parentCategory);
    	if (ec != null) {
    		List<ExpenseSubType> types = CachedData.getInstance().getExpenseTypesForCategory(ec.getId());
    		items = new String[types.size()];
    		
    		for (int i=0; i<items.length; i++) {
    			items[i] = types.get(i).getDescription();
    		}
    		
    		setListAdapter(new ArrayAdapter<String>(this,
    	              android.R.layout.simple_list_item_1, items));
    	}
        
      
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.expense_types_options_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.new_expense_type:
            //Toast.makeText(this, "ahha", Toast.LENGTH_LONG).show();
            Intent myIntent = new Intent(ExpenseTypesActivity.this, NewExpenseTypeActivity.class);
            myIntent.putExtra("SelectedCategory", parentCategory);
            ExpenseTypesActivity.this.startActivityForResult(myIntent, CREATE_NEW_EXPENSE_TYPE);
            return true;
        default:
            return true;
        }

    }

    protected void onActivityResult(int requestCode, int resultCode,
             Intent data) {
         if (requestCode == CREATE_NEW_EXPENSE_TYPE) {
             if (resultCode == RESULT_OK) {

                 Toast.makeText(this, R.string.new_expense_type_saved_msg, Toast.LENGTH_SHORT);

                 String expenseType = data.getStringExtra("NewExpenseType");

                 
                //try {                    
                    ExpenseCategory ec = CachedData.getInstance().getExpenseCategoryByName(parentCategory);
                     if (ec != null) {
                        //long newId = md.getNextExpenseTypeId();
                        //ExpenseType et = new ExpenseType(newId, expenseType);
                        //ec.getExpenseTypes().add(et);
                        //md.save();
                     }
//                } catch (ParserConfigurationException ex) {
//                    Logger.getLogger(ExpenseTypesActivity.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (SAXException ex) {
//                    Logger.getLogger(ExpenseTypesActivity.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (IOException ex) {
//                    Logger.getLogger(ExpenseTypesActivity.class.getName()).log(Level.SEVERE, null, ex);
//                }
                 

                 // A contact was picked.  Here we will just display it
                 // to the user.
                 //startActivity(new Intent(Intent.ACTION_VIEW, data));

                 loadExpenseTypes();
             }
         }
     }

}
