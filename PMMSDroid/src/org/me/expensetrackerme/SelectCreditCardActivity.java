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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.me.expensetrackerme.services.CachedData;
import trackit.businessobjects.CreditCard;

/**
 *
 * @author HP
 */
public class SelectCreditCardActivity extends Activity implements OnClickListener {

    private Spinner creditCardSpinner;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewId());

//        Metadata md = null;
//        try {
//            md = Metadata.init();
//        } catch (ParserConfigurationException ex) {
//            Logger.getLogger(NewExpenseActivity.class.getName()).log(Level.SEVERE, null, ex);
//            return;
//        } catch (SAXException ex) {
//            Logger.getLogger(NewExpenseActivity.class.getName()).log(Level.SEVERE, null, ex);
//            return;
//        } catch (IOException ex) {
//            Logger.getLogger(NewExpenseActivity.class.getName()).log(Level.SEVERE, null, ex);
//            return;
//        }

        setupCreditCardSpinner();


        Button okButton = (Button) findViewById(R.id.ok);
        okButton.setOnClickListener(this);

        Button cancelButton = (Button) findViewById(R.id.cancel);
        cancelButton.setOnClickListener(this);
    }

    protected int getViewId() {
        return R.layout.select_credit_card;
    }

    public void onClick(View view) {

        if (view.getId() == R.id.ok) {

            Intent data = new Intent();
            CreditCard selectedCreditCard = (CreditCard) creditCardSpinner.getSelectedItem();
            data.putExtra("CreditCardId", selectedCreditCard.getId());
            data.putExtra("CreditCardName", selectedCreditCard.getDescription());
            setResult(RESULT_OK, data);
        } else {
            setResult(RESULT_CANCELED);
        }

        finish();
    }
    
    private void setupCreditCardSpinner() {
        creditCardSpinner = (Spinner) findViewById(R.id.credit_card_spinner);
        
//        CreditCard[] items = (CreditCard[]) MetadataService.getInstance().getCreditCards().toArray(new CreditCard[]{});
        
        CreditCard[] items = CachedData.getInstance().getCreditCards().toArray(new CreditCard[]{});
        ArrayAdapter<CreditCard> adapter =
                new ArrayAdapter<CreditCard>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        creditCardSpinner.setAdapter(adapter);
    }    

}
