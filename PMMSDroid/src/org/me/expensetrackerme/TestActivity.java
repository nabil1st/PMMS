package org.me.expensetrackerme;

import org.me.expensetrackerme.services.RestClient;

import android.app.Activity;
import android.os.Bundle;

public class TestActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String result = RestClient.callRESTService("http://192.168.1.2:8080/pmms/rest/payees/26");
        setContentView(R.layout.main);
        
    }
}