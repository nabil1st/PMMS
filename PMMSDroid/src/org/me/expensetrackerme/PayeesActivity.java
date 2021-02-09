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
import trackit.businessobjects.Payee;
import org.xml.sax.SAXException;


/**
 *
 * @author Nabil
 */
public class PayeesActivity extends ItemsListActivity {

    private static final int CREATE_NEW_PAYEE = 0;

    /* Handles item selections */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.new_payee:
            //Toast.makeText(this, "ahha", Toast.LENGTH_LONG).show();
            Intent myIntent = new Intent(this, NewPayeeActivity.class);
            startActivityForResult(myIntent, CREATE_NEW_PAYEE);
            return true;
        default:
            return true;
        }

    }

    protected void onActivityResult(int requestCode, int resultCode,
             Intent data) {
         if (requestCode == CREATE_NEW_PAYEE) {
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
        //Metadata md = Metadata.init();
//    	List<Payee> payees = MetadataService.getInstance().getPayees();
    	List<Payee> payees = CachedData.getInstance().getPayees();
		
//        items = new String[md.getPayees().size()];
    	items = new String[payees.size()];
        for (int i=0; i<payees.size(); i++) {
            items[i] = payees.get(i).getDescription();
        }

        setListAdapter(new ArrayAdapter<String>(this,
              android.R.layout.simple_list_item_1, items));
    }

    protected int getOptionsMenuId() {
        return R.menu.payees_options_menu;
    }

}
