package com.wmcgroup.wmcvip.db;

import java.util.ArrayList;
import java.util.List;

import com.wmcgroup.wmcvip.model.Transaction;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TransactionsDataSource {

	public static final String LOGTAG="WMCVIP-TRANSACTIONS";

	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;
	
	private static final String[] allColumns = {
		WMCVIPDBOpenHelper.COLUMN_ID,
		WMCVIPDBOpenHelper.COLUMN_BUSINESSDATE,
		WMCVIPDBOpenHelper.COLUMN_BEGINDATE,
		WMCVIPDBOpenHelper.COLUMN_BEGINTIME,
		WMCVIPDBOpenHelper.COLUMN_ENDDATE,
		WMCVIPDBOpenHelper.COLUMN_ENDTIME,
		WMCVIPDBOpenHelper.COLUMN_OUTLETCODE,
		WMCVIPDBOpenHelper.COLUMN_CHECKNO,
		WMCVIPDBOpenHelper.COLUMN_MEMBERID,
		WMCVIPDBOpenHelper.COLUMN_NETSALE,
		WMCVIPDBOpenHelper.COLUMN_SERVICECHARGE,
		WMCVIPDBOpenHelper.COLUMN_TAX,
		WMCVIPDBOpenHelper.COLUMN_ACTUALPAID,
		WMCVIPDBOpenHelper.COLUMN_IMAGE
		};

	public TransactionsDataSource(Context context) {
		dbhelper = new WMCVIPDBOpenHelper(context);
	}

	public void open() {
		Log.i(LOGTAG, "Database opened");
		database = dbhelper.getWritableDatabase();
	}

	public void close() {
		Log.i(LOGTAG, "Database closed");		
		dbhelper.close();
	}

	public Transaction create(Transaction transaction) {
		ContentValues values = new ContentValues();
		
		values.put(WMCVIPDBOpenHelper.COLUMN_BUSINESSDATE, transaction.getBusinessDate());
		values.put(WMCVIPDBOpenHelper.COLUMN_BEGINDATE, transaction.getBeginDate());
		values.put(WMCVIPDBOpenHelper.COLUMN_BEGINTIME, transaction.getBeginTime());
		values.put(WMCVIPDBOpenHelper.COLUMN_ENDDATE, transaction.getEndDate());
		values.put(WMCVIPDBOpenHelper.COLUMN_ENDTIME, transaction.getEndTime());
		values.put(WMCVIPDBOpenHelper.COLUMN_OUTLETCODE, transaction.getOutletCode());
		values.put(WMCVIPDBOpenHelper.COLUMN_CHECKNO, transaction.getCheckNo());
		values.put(WMCVIPDBOpenHelper.COLUMN_MEMBERID, transaction.getMemberId());
		values.put(WMCVIPDBOpenHelper.COLUMN_NETSALE, transaction.getNetSale());
		values.put(WMCVIPDBOpenHelper.COLUMN_SERVICECHARGE, transaction.getServiceCharge());
		values.put(WMCVIPDBOpenHelper.COLUMN_TAX, transaction.getTax());
		values.put(WMCVIPDBOpenHelper.COLUMN_ACTUALPAID, transaction.getActualPaid());
		values.put(WMCVIPDBOpenHelper.COLUMN_IMAGE, transaction.getImage());
		long insertid = database.insert(WMCVIPDBOpenHelper.TABLE_TRANSACTIONS, null, values);
		transaction.setId(insertid);
		return transaction;
	}

	public List<Transaction> findAll() {

		Cursor cursor = database.query(WMCVIPDBOpenHelper.TABLE_TRANSACTIONS, allColumns, 
				null, null, null, null, null);

		Log.i(LOGTAG, "Returned " + cursor.getCount() + " rows");
		
		List<Transaction> transactions = cursorToList(cursor);
		
		return transactions;
	}

	public List<Transaction> findFiltered(String selection, String orderBy) {

		Cursor cursor = database.query(WMCVIPDBOpenHelper.TABLE_TRANSACTIONS, allColumns, 
				selection, null, null, null, orderBy);

		Log.i(LOGTAG, "Returned " + cursor.getCount() + " rows");
		List<Transaction> transactions = cursorToList(cursor);
		return transactions;
	}

	private List<Transaction> cursorToList(Cursor cursor) {
		List<Transaction> transactions = new ArrayList<Transaction>();
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				Transaction transaction = new Transaction();
				transaction.setId(cursor.getLong(cursor.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_ID)));
				transaction.setBusinessDate(cursor.getString(cursor.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_BUSINESSDATE)));
				transaction.setBeginDate(cursor.getString(cursor.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_BEGINDATE)));
				transaction.setBeginTime(cursor.getString(cursor.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_BEGINTIME)));
				transaction.setEndDate(cursor.getString(cursor.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_ENDDATE)));
				transaction.setEndTime(cursor.getString(cursor.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_ENDTIME)));
				transaction.setOutletCode(cursor.getString(cursor.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_OUTLETCODE)));
				transaction.setCheckNo(cursor.getString(cursor.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_CHECKNO)));
				transaction.setMemberId(cursor.getString(cursor.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_MEMBERID)));
				transaction.setNetSale(cursor.getDouble(cursor.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_NETSALE)));
				transaction.setServiceCharge(cursor.getDouble(cursor.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_SERVICECHARGE)));
				transaction.setTax(cursor.getDouble(cursor.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_TAX)));
				transaction.setActualPaid(cursor.getDouble(cursor.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_ACTUALPAID)));				
				transaction.setImage(cursor.getString(cursor.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_IMAGE)));
				transactions.add(transaction);
			}
		}
		return transactions;
	}
	
	public boolean addToMyTransactions(Transaction transaction) {
		ContentValues values = new ContentValues();
		values.put(WMCVIPDBOpenHelper.COLUMN_ID, transaction.getId());
		long result = database.insert(WMCVIPDBOpenHelper.TABLE_MYTRANSACTIONS, null, values);
		return (result != -1);
	}
	
	public List<Transaction> findMyTransactions() {

		String query = "SELECT transactions.* FROM " +
				"transactions JOIN mytransactions ON " +
				"transactions.id = mytransactions.id";
		Cursor cursor = database.rawQuery(query, null);

		Log.i(LOGTAG, "Returned " + cursor.getCount() + " rows");

		List<Transaction> transactions = cursorToList(cursor);
		return transactions;
	}
	
}
