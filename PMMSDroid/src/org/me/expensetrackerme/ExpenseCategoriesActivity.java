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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;

import org.me.expensetrackerme.services.CachedData;
import org.me.expensetrackerme.services.RestfulDataAccessService;
import org.xml.sax.SAXException;

import trackit.businessobjects.ExpenseCategory;

/**
 *
 * @author Nabil
 */
public class ExpenseCategoriesActivity extends ListActivity implements OnItemClickListener {

    private static final int CREATE_NEW_CATEGORY = 0;

    String[] items ; //= new String[]{"Auto", "Groceries", "Entertainment"};
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      loadExpenseCategories();
      getListView().setTextFilterEnabled(true);

      getListView().setOnItemClickListener(this);
    }

    
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        //Toast.makeText(this, "" + id + " " + position, Toast.LENGTH_LONG).show();

        Intent myIntent = new Intent(ExpenseCategoriesActivity.this, ExpenseTypesActivity.class);
        myIntent.putExtra("SelectedCategory", items[position]);
        ExpenseCategoriesActivity.this.startActivity(myIntent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    /* Handles item selections */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.new_category:
            //Toast.makeText(this, "ahha", Toast.LENGTH_LONG).show();
            Intent myIntent = new Intent(ExpenseCategoriesActivity.this, NewCategoryActivity.class);
            ExpenseCategoriesActivity.this.startActivityForResult(myIntent, CREATE_NEW_CATEGORY);
            return true;
        default:
            return true;
        }

    }

    protected void onActivityResult(int requestCode, int resultCode,
             Intent data) {
         if (requestCode == CREATE_NEW_CATEGORY) {
             if (resultCode == RESULT_OK) {

                 Toast.makeText(this, R.string.new_category_saved_msg, Toast.LENGTH_SHORT);
                 // A contact was picked.  Here we will just display it
                 // to the user.
                 //startActivity(new Intent(Intent.ACTION_VIEW, data));

                 loadExpenseCategories();
             }
         }
     }

    private void loadExpenseCategories() {
        
            // Load expense categories into a String[]
//            IMetadataService md = MetadataServiceFactory.getMetadataService().init();
//            items = new String[md.getExpenseCategories().size()];
    	
    	List<ExpenseCategory> list = CachedData.getInstance().getExpenseCategories();
    	items = new String[list.size()];
        for (int i=0; i<list.size(); i++) {
            items[i] = list.get(i).getDescription();
        }
        

      setListAdapter(new ArrayAdapter<String>(this,
              android.R.layout.simple_list_item_1, items));
    }


}
