package com.wmcgroup.wmcvip;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.wmcgroup.wmcvip.db.TransactionsDataSource;
import com.wmcgroup.wmcvip.model.Transaction;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class TransactionDetailActivity extends Activity {

	private static final String LOGTAG = "WMCVIP-TRANSACTION-DETAIL-VIEW";

	Transaction transaction;
	TransactionsDataSource datasource;
	boolean isMyTransactions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transaction_detail);

		Bundle b = getIntent().getExtras();
		transaction = b.getParcelable(".model.Transaction");
		isMyTransactions = b.getBoolean("isMyTransactions");

		refreshDisplay();

		datasource = new TransactionsDataSource(this);

	}

	private void refreshDisplay() {
		
		//------------
		TextView tv = (TextView) findViewById(R.id.CheckNo);
		tv.setText("Check No.: " + transaction.getCheckNo());
		
		//------------
		tv = (TextView) findViewById(R.id.MemberId);
		tv.setText("VIP Number: " + transaction.getMemberId());
	
		//------------
		tv = (TextView) findViewById(R.id.BusinessDate);
		
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
		try {
			d = df.parse(transaction.getBusinessDate());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        tv.setText("Date: " + DateFormat.format("dd-MM-yyyy", d));
		
		//------------
		//tv = (TextView) findViewById(R.id.BeginDate);
		//tv.setText(transaction.getBeginDate());
		
		//------------
		//tv = (TextView) findViewById(R.id.BeginTime);
		//tv.setText(transaction.getBeginTime());
		
		//------------
		//tv = (TextView) findViewById(R.id.EndDate);
		//tv.setText(transaction.getEndDate());
		
		//------------
		tv = (TextView) findViewById(R.id.EndTime);
		
        Date t = null;
		try {
			t = df.parse(transaction.getEndTime());
		} catch (ParseException e) {
		  // TODO Auto-generated catch block
			e.printStackTrace();
		}
        tv.setText("Time: " + DateFormat.format("hh:mm:ss", t));
        
		//------------
		tv = (TextView) findViewById(R.id.OutletCode);
		tv.setText("Restaurant Code: " + transaction.getOutletCode());
		
		//------------
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		//------------
		
		//------------
		tv = (TextView) findViewById(R.id.NetSale);
		tv.setText("Sub Total: " + nf.format(transaction.getNetSale()));
		
		//------------
		tv = (TextView) findViewById(R.id.ServiceCharge);
		tv.setText("Service Charge: " + nf.format(transaction.getServiceCharge()));
		
		//------------
		tv = (TextView) findViewById(R.id.Tax);
		tv.setText("Tax: " + nf.format(transaction.getTax()));
		
		//------------
		tv = (TextView) findViewById(R.id.ActualPaid);
		tv.setText("Actual Paid: " + nf.format(transaction.getActualPaid()));
		
		//------------
		ImageView iv = (ImageView) findViewById(R.id.imageView1);
		int imageResource = getResources().getIdentifier(
			transaction.getImage(), "drawable", getPackageName());
		if (imageResource != 0) {
			iv.setImageResource(imageResource);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.transaction_detail, menu);
		
		// Show delete menu item if we came from My Tours
		menu.findItem(R.id.menu_delete)
		.setVisible(isMyTransactions);
		
		// Show add menu item if we didn't come from My Tours
		menu.findItem(R.id.menu_add)
		.setVisible(!isMyTransactions);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_add:
			if (datasource.addToMyTransactions(transaction)) {
				Log.i(LOGTAG, "Transaction added");
			} else {
				Log.i(LOGTAG, "Transaction not added");
			}
			break;
		case R.id.menu_delete:
			
			break;
		}
			
		return super.onOptionsItemSelected(item);
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