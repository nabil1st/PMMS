/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.expensetrackerme;


import org.me.expensetrackerme.database.DataHelper;
import org.me.expensetrackerme.database.LocalDataAccess;
import org.me.expensetrackerme.services.RestfulDataAccessService;
import org.trackit.model.Login;
import org.me.expesetrackerme.application.AccountInfo;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.net.NetworkInfo;

/**
 *
 * @author HP
 */
public class LoginActivity extends Activity implements OnClickListener {
		
	private static final int PROGRESS_DIALOG = 1;
	
	private EditText emailText;
	private EditText passwordText;
	
	private ProgressDialog progressDialog;
	private Thread t;
	
    private int getViewId() {
        return R.layout.login_screen;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(getViewId());

        LocalDataAccess.getInstance().createDataHelper(getApplicationContext());
                
        Button loginButton = (Button) findViewById(R.id.login);
        emailText = (EditText) findViewById(R.id.email);
        passwordText = (EditText) findViewById(R.id.password);
        
        loginButton.setOnClickListener(this);
        
        Button offlineButton = (Button) findViewById(R.id.offline);
        offlineButton.setOnClickListener(this);

        Button cancelButton = (Button) findViewById(R.id.cancel);
        cancelButton.setOnClickListener(this);
    }

    public void onClick(View view) {

        if (view.getId() == R.id.login) {            
        	showDialog(PROGRESS_DIALOG);
        	t = new Thread() {
        		public void run() {
        			login();
        		}
        	};
        	
        	t.start();
        } else if (view.getId() == R.id.offline) {
        	workOffLine();
        } else {
        	finish();
        }
    }
    
    protected Dialog onCreateDialog(int id) {
    	
    	switch (id) {	    	
	    	case PROGRESS_DIALOG: {
	    		progressDialog = new ProgressDialog(this);
	    		progressDialog.setMessage("Signing In. Please Wait..");
	    		progressDialog.setIndeterminate(true);
	    		progressDialog.setCancelable(true);
	    		return progressDialog;
	    	}
    	}

        return null;
    }
    
    public void login() {
    	
    	if (!isNetworkAvailable()) {
    		removeDialog(PROGRESS_DIALOG);
        	Intent intent = new Intent(getApplicationContext(), LoginFailureActivity.class);
        	intent.putExtra("message", "Network is not available. Please try again later.");
        	startActivity(intent);
        	return;
        }
    	
    	String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        
        Login login = new Login();
        login.setUserName(email);
        login.setPassword(password);
        
        Login result = RestfulDataAccessService.getInstance().login(login);
        
        if (result.getStatus() == Login.LOGIN_SUCCESS) {
        	AccountInfo.getInstance().setAccountId(result.getAccountId());
        	AccountInfo.getInstance().setUserId(result.getUserId());        	
        	LocalDataAccess.getInstance().getDataHelper().createAccount(result.getAccountId(), result.getUserId());
        	removeDialog(PROGRESS_DIALOG);
        	AccountInfo.getInstance().setOffline(false);
        	Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        	startActivity(intent);
        } else if (result.getStatus() == Login.LOGIN_FAILURE){
        	removeDialog(PROGRESS_DIALOG);
        	LocalDataAccess.getInstance().getDataHelper().deleteAccount();
        	Intent intent = new Intent(getApplicationContext(), LoginFailureActivity.class);
        	startActivity(intent);        	
        } else {
        	removeDialog(PROGRESS_DIALOG);
        	Intent intent = new Intent(getApplicationContext(), LoginFailureActivity.class);
        	intent.putExtra("message", "Connection time out. Make sure you're connected to the internet and try again");
        	startActivity(intent);
        }
    }
    
    private void workOffLine() {
    	Login login = LocalDataAccess.getInstance().getDataHelper().getAccountInfo();
    	if (login == null) {
    		Intent intent = new Intent(getApplicationContext(), LoginFailureActivity.class);
        	intent.putExtra("message", "Account information is not available. Offline mode can is enabled after a succefull login!");
        	startActivity(intent);
    	} else {
    		AccountInfo.getInstance().setAccountId(login.getAccountId());
    		AccountInfo.getInstance().setUserId(login.getUserId());
    		AccountInfo.getInstance().setOffline(true);
    		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        	startActivity(intent);
    	}
    }
    
    
    private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null;
	}

}
