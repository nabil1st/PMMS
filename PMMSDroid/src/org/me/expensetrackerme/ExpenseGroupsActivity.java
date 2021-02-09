/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.expensetrackerme;

import android.content.Intent;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;

import org.me.expensetrackerme.services.CachedData;
import trackit.businessobjects.ExpenseGroup;
import org.xml.sax.SAXException;


/**
 *
 * @author HP
 */
public class ExpenseGroupsActivity extends ItemsListActivity {

    private static final int CREATE_NEW_EXPENSE_GROUP = 0;

    /* Handles item selections */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.new_expense_group:
            //Toast.makeText(this, "ahha", Toast.LENGTH_LONG).show();
            Intent myIntent = new Intent(this, NewExpenseGroupActivity.class);
            startActivityForResult(myIntent, CREATE_NEW_EXPENSE_GROUP);
            return true;
        default:
            return true;
        }

    }

    protected void onActivityResult(int requestCode, int resultCode,
             Intent data) {
         if (requestCode == CREATE_NEW_EXPENSE_GROUP) {
             if (resultCode == RESULT_OK) {
                try {
                    loadItems();
                } catch (ParserConfigurationException ex) {
                    Logger.getLogger(PayeesActivity.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SAXException ex) {
                    Logger.getLogger(PayeesActivity.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(PayeesActivity.class.getName()).log(Level.SEVERE, null, ex);
                }
             }
         }
     }


    protected void loadItems() throws ParserConfigurationException, SAXException, IOException {
//        IMetadataService md = MetadataServiceFactory.getMetadataService().init();
    	List<ExpenseGroup> list = CachedData.getInstance().getExpenseGroups();
        items = new String[list.size()];
        for (int i=0; i<list.size(); i++) {
            items[i] = ((ExpenseGroup) list.get(i)).getDescription();
        }

        setListAdapter(new ArrayAdapter<String>(this,
              android.R.layout.simple_list_item_1, items));
    }

    protected int getOptionsMenuId() {
        return R.menu.expense_groups_options_menu;
    }

}
