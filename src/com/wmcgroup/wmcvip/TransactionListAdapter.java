package com.wmcgroup.wmcvip;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.wmcgroup.wmcvip.model.Transaction;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TransactionListAdapter extends ArrayAdapter<Transaction> {
	Context context;
	List<Transaction> transactions;
	
	public TransactionListAdapter(Context context, List<Transaction> transactions) {
		super(context, android.R.id.content, transactions);
		this.context = context;
		this.transactions = transactions;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
        View view = vi.inflate(R.layout.listitem_transaction, null);
	
        Transaction transaction = transactions.get(position);
        
        //------------
        TextView tv = (TextView) view.findViewById(R.id.CheckNo);
        tv.setText("Check No.: " + transaction.getCheckNo());
        
        //------------
        tv = (TextView) view.findViewById(R.id.BussinessDate);
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
		try {
			d = f.parse(transaction.getBusinessDate());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        tv.setText("Date: " + DateFormat.format("dd-MM-yyyy", d));
        
        //------------
        tv = (TextView) view.findViewById(R.id.OutletCode);
        tv.setText("Restaurant Code: " + transaction.getOutletCode());
        
        //------------
        tv = (TextView) view.findViewById(R.id.ActualPaid);
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        tv.setText("Actual Paid: " + nf.format(transaction.getActualPaid()));
        
        //------------
        ImageView iv = (ImageView) view.findViewById(R.id.imageView1);
        int imageResource = context.getResources().getIdentifier(
        		transaction.getImage(), "drawable", context.getPackageName());
        if (imageResource != 0) {
        	iv.setImageResource(imageResource);
        }
        
        return view;
	}

}