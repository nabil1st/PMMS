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
import trackit.businessobjects.CreditCard;
import org.xml.sax.SAXException;

/**
 *
 * @author HP
 */
public class CreditCardsActivity extends ItemsListActivity {
    
    private static final int CREATE_NEW_CREDIT_CARD = 0;

    /* Handles item selections */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.new_credit_card:
            //Toast.makeText(this, "ahha", Toast.LENGTH_LONG).show();
            Intent myIntent = new Intent(this, NewCreditCardActivity.class);
            startActivityForResult(myIntent, CREATE_NEW_CREDIT_CARD);
            return true;
        default:
            return true;
        }

    }

    protected void onActivityResult(int requestCode, int resultCode,
             Intent data) {
         if (requestCode == CREATE_NEW_CREDIT_CARD) {
             if (resultCode == RESULT_OK) {
                try {
                    loadItems();
                } catch (ParserConfigurationException ex) {
                    Logger.getLogger(CreditCardsActivity.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SAXException ex) {
                    Logger.getLogger(CreditCardsActivity.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(CreditCardsActivity.class.getName()).log(Level.SEVERE, null, ex);
                }
             }
         }
     }


    protected void loadItems() throws ParserConfigurationException, SAXException, IOException {
        List<CreditCard> creditCards = CachedData.getInstance().getCreditCards(); 
    	items = new String[creditCards.size()]; 
            	
    	for (int i=0; i<creditCards.size(); i++) {
            items[i] = creditCards.get(i).getDescription();
        }

        setListAdapter(new ArrayAdapter<String>(this,
              android.R.layout.simple_list_item_1, items));
    }

    protected int getOptionsMenuId() {
        return R.menu.credit_cards_options_menu;
    }

}
