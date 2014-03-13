package com.wmcgroup.wmcvip;

import java.util.ArrayList;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.wmcgroup.wmcvip.adapter.Constants;
import com.wmcgroup.wmcvip.db.TransactionsDataSource;
import com.wmcgroup.wmcvip.model.Transaction;

public class MainWMCVip extends ListActivity {
	
	public static final String LOGTAG="WMCVIP-Homepage";
	public static final String USERNAME="pref_username";
	public static final String VIEWIMAGE="pref_viewimages";
	private static final int TRANSACTION_DETAIL_ACTIVITY = 1001;
	
	private AccountManager mAccountManager;
    private String authToken = null;
    private String VIPAccount = "";
    private Account mConnectedAccount;
    
	private SharedPreferences settings;
	private OnSharedPreferenceChangeListener listener;
	private ArrayList<Transaction> transactions;
	boolean isMyTransactions;

	TransactionsDataSource datasource;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_wmcvip);
		
		mAccountManager = AccountManager.get(this);
		
		// Yeu cau dang nhap ngay tu dau
		getTokenForAccountCreateIfNeeded(Constants.ACCOUNT_TYPE,Constants.AUTHTOKEN_TYPE);
        
		// Code Xu ly voi nut Dang Nhap
		Button b = (Button) findViewById(R.id.btn_connect);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getTokenForAccountCreateIfNeeded(Constants.ACCOUNT_TYPE,Constants.AUTHTOKEN_TYPE);
			}
		});
		
		
		settings = PreferenceManager.getDefaultSharedPreferences(this);

		listener = new OnSharedPreferenceChangeListener() {

			@Override
			public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
					String key) {
				MainWMCVip.this.refreshDisplay();
			}
		};
		settings.registerOnSharedPreferenceChangeListener(listener);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_wmcvip, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		//Mizugi:  Dung datasource de giao tiep voi database; tam thoi tat di, de su dung provider adapter
		
		switch (item.getItemId()) {

		case R.id.menu_settings:
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			break;

		case R.id.menu_all:
			//transactions = datasource.findAll();
			refreshDisplay();
			isMyTransactions = false;
			break;

		case R.id.menu_cheap:
			//transactions = datasource.findFiltered("netsale <= 50000", "netsale ASC");
			refreshDisplay();
			isMyTransactions = false;
			break;

		case R.id.menu_fancy:
			//transactions = datasource.findFiltered("netsale >= 50000", "netsale DESC");
			refreshDisplay();
			isMyTransactions = false;
			break;

		case R.id.menu_mytransactions:
			//transactions = datasource.findMyTransactions();
			refreshDisplay();
			isMyTransactions = true;
			break;
			
		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	public void refreshDisplay() {
		
		boolean viewImages = settings.getBoolean(VIEWIMAGE, false);
		
		if (viewImages) {
			ArrayAdapter<Transaction> adapter = new TransactionListAdapter(this, transactions);
			setListAdapter(adapter);
		} else {
			ArrayAdapter<Transaction> adapter = new ArrayAdapter<Transaction>(this, 
					android.R.layout.simple_list_item_1, transactions);
			setListAdapter(adapter);
		}
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//datasource.open();
	}

	@Override
	protected void onPause() {
		super.onPause();
		//datasource.close();
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Transaction transaction = transactions.get(position);
		
		Intent intent = new Intent(this, TransactionDetailActivity.class);
		
		intent.putExtra(".model.Transaction", transaction);
		intent.putExtra("isMyTransactions", isMyTransactions);
		
		startActivityForResult(intent, TRANSACTION_DETAIL_ACTIVITY);
		
	}
	
	/**
     * Get an auth token for the account.
     * If not exist - add it and then return its auth token.
     * If one exist - return its auth token.
     * If more than one exists - show a picker and return the select account's auth token.
     * @param accountType
     * @param authTokenType
     */
    private void getTokenForAccountCreateIfNeeded(String accountType, String authTokenType) {
        final AccountManagerFuture<Bundle> future = mAccountManager.getAuthTokenByFeatures(accountType, authTokenType, null, this, null, null,
                new AccountManagerCallback<Bundle>() {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future) {
                        Bundle bnd = null;
                        try {
                            bnd = future.getResult();
                            authToken = bnd.getString(AccountManager.KEY_AUTHTOKEN);
                            VIPAccount = bnd.getString(AccountManager.KEY_ACCOUNT_NAME);
                            		
                            if (authToken != null) {
                                String accountName = bnd.getString(AccountManager.KEY_ACCOUNT_NAME);
                                mConnectedAccount = new Account(accountName, Constants.ACCOUNT_TYPE);
                                //initButtonsAfterConnect();
                            }
                            showMessage(((authToken != null) ? "SUCCESS!\ntoken: " + authToken : "FAIL"));
                            Log.d(LOGTAG, "Bundle is " + bnd + " Account is: " + VIPAccount);
                            
                            // Lay transactions cua VIP Number, dua vao list
                    		// Get Local Transactions from Phone Database via Provider
                            transactions = new ArrayList<Transaction>();

                            final ContentResolver resolver = getContentResolver();
                            Cursor curTransactions = resolver.query(Constants.CONTENT_URI, null, "memberid='" + VIPAccount + "'", null, "businessdate DESC");
                            
                            if (curTransactions != null) {
                                while (curTransactions.moveToNext()) {
                                	transactions.add(new Transaction(curTransactions));
                                }
                                curTransactions.close();
                            }
                    		
                            // Gan transactions co duoc vao List Adapter
                            refreshDisplay();
                            
                            Log.i(LOGTAG, "Local Transaction Records: " +transactions.size() + " for Account: " + VIPAccount);
                        } catch (Exception e) {
                            e.printStackTrace();
                            showMessage(e.getMessage());
                        }
                    }
                }
        , null);
    }
    
    private void showMessage(final String msg) {
        if (msg == null || msg.trim().equals(""))
            return;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
