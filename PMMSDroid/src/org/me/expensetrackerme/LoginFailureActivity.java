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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 *
 * @author HP
 */
public class LoginFailureActivity extends Activity implements OnClickListener {
	
    private int getViewId() {
        return R.layout.login_failure;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewId());

        Button tryAgain = (Button) findViewById(R.id.tryAgain);
        tryAgain.setOnClickListener(this);        
        
        
        String message = getIntent().getStringExtra("message");
        if (message != null) {
        	TextView textView = (TextView) findViewById(R.id.message);
        	textView.setText(message);
        }
    }

    public void onClick(View view) {

        if (view.getId() == R.id.tryAgain) {
        	Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        	startActivity(intent);
        }
    }
}
