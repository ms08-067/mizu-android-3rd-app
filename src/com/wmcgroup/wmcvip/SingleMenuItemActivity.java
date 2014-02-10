package com.wmcgroup.wmcvip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SingleMenuItemActivity  extends Activity {
	
	// JSON Node names
	private static final String TAG_TRANSACTIONS = "transactions";
	private static final String TAG_BEGINDATE = "BeginDate";
	private static final String TAG_BEGINTIME = "BeginTime";
	private static final String TAG_OUTLETCODE = "OutletCode";
	private static final String TAG_CHECKNO = "CheckNo";
	private static final String TAG_VIPID = "MemberID";
	private static final String TAG_NETSALE = "NetSale";
	private static final String TAG_ACTUALPAID = "ActualPaid";
		
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_list_item);
        
        // getting intent data
        Intent in = getIntent();
        
        // Get JSON values from previous intent
        String BeginDate = in.getStringExtra(TAG_BEGINDATE);
        String BeginTime = in.getStringExtra(TAG_BEGINTIME);
        String OutletCode = in.getStringExtra(TAG_OUTLETCODE);
        String CheckNo = in.getStringExtra(TAG_CHECKNO);
        String MemberId = in.getStringExtra(TAG_VIPID);
        String NetSale = in.getStringExtra(TAG_NETSALE);
        String ActualPaid = in.getStringExtra(TAG_ACTUALPAID);
        
        // Displaying all values on the screen
        TextView lblCheckNo = (TextView) findViewById(R.id.checkno_label);
        TextView lblBeginDate = (TextView) findViewById(R.id.begindate_label);
        TextView lblBeginTime = (TextView) findViewById(R.id.begintime_label);
        TextView lblOutletCode = (TextView) findViewById(R.id.outletcode_label);
        TextView lblMemberId = (TextView) findViewById(R.id.memberid_label);
        TextView lblNetSale = (TextView) findViewById(R.id.netsale_label);
        TextView lblActualPaid = (TextView) findViewById(R.id.actualpaid_label);
        
        lblCheckNo.setText(CheckNo);
        lblBeginDate.setText(BeginDate);
        lblBeginTime.setText(BeginTime);
        lblOutletCode.setText(OutletCode);
        lblMemberId.setText(MemberId);
        lblNetSale.setText(NetSale);
        lblActualPaid.setText(ActualPaid);
    }
}
