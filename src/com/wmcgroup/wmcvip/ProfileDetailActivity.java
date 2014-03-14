package com.wmcgroup.wmcvip;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.wmcgroup.wmcvip.db.TransactionsDataSource;
import com.wmcgroup.wmcvip.model.Transaction;

public class ProfileDetailActivity extends Activity {

	private static final String LOGTAG = "WMCVIP-TRANSACTION-DETAIL-VIEW";

	Transaction transaction;
	TransactionsDataSource datasource;
	boolean isMyTransactions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_profile_layout);

		//Intent intent = getIntent(); 
	    
		// 2. get message value from intent
	    //transaction = intent.getExtra("transactions");
		//Log("hung phan - test", transaction);
		Bundle b = getIntent().getExtras();
		transaction = b.getParcelable(".model.Transaction");
		
	    // Bundle b = gsetIntent().getExtras();
		//transaction = b.getParcelable(".model.Transaction");
		
	    //isMyTransactions = b.getBoolean("isMyTransactions");

		refreshDisplay();

		datasource = new TransactionsDataSource(this);

	}

	private void refreshDisplay() {
		
		//------------
		TextView tv = (TextView) findViewById(R.id.point_balance);
		tv.setText("Point Balance: " + transaction.getCheckNo());
		
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		datasource.open();
	}

	@Override
	protected void onPause() {
		super.onPause();
		datasource.close();
	}

}