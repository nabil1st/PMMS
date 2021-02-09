/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.expensetrackerme;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author HP
 */
public abstract class ItemsListActivity extends ListActivity {

    protected String[] items ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            loadItems();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ItemsListActivity.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(ItemsListActivity.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ItemsListActivity.class.getName()).log(Level.SEVERE, null, ex);
        }
        getListView().setTextFilterEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(getOptionsMenuId(), menu);
        return true;
    }

    
    protected abstract void loadItems() throws ParserConfigurationException, SAXException, IOException;

    protected abstract int getOptionsMenuId();


}
