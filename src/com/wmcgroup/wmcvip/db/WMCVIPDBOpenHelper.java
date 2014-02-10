package com.wmcgroup.wmcvip.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class WMCVIPDBOpenHelper extends SQLiteOpenHelper {

	private static final String LOGTAG = "WMCVIP-DATABASE";

	private static final String DATABASE_NAME = "wmcvip.db";
	private static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_TRANSACTIONS = "transactions";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_BUSINESSDATE = "businessdate";
	public static final String COLUMN_BEGINDATE = "begindate";
	public static final String COLUMN_BEGINTIME = "begintime";
	public static final String COLUMN_ENDDATE = "enddate";
	public static final String COLUMN_ENDTIME = "endtime";
	public static final String COLUMN_OUTLETCODE = "outletcode";
	public static final String COLUMN_CHECKNO = "checkno";
	public static final String COLUMN_MEMBERID = "memberid";
	public static final String COLUMN_NETSALE = "netsale";
	public static final String COLUMN_SERVICECHARGE = "servicecharge";
	public static final String COLUMN_TAX = "tax";
	public static final String COLUMN_ACTUALPAID = "actualpaid";
	
	public static final String COLUMN_IMAGE = "image";

	private static final String TABLE_CREATE = 
			"CREATE TABLE " + TABLE_TRANSACTIONS + " (" +
					COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					COLUMN_BUSINESSDATE + " TEXT, " +
					COLUMN_BEGINDATE + " TEXT, " +
					COLUMN_BEGINTIME + " TEXT, " +
					COLUMN_ENDDATE + " TEXT, " +
					COLUMN_ENDTIME + " TEXT, " +
					COLUMN_OUTLETCODE + " TEXT, " +
					COLUMN_CHECKNO + " TEXT, " +
					COLUMN_MEMBERID + " TEXT, " +
					COLUMN_NETSALE + " NUMERIC, " +
					COLUMN_SERVICECHARGE + " NUMERIC, " +
					COLUMN_TAX + " NUMERIC, " +
					COLUMN_ACTUALPAID + " NUMERIC, " +
					COLUMN_IMAGE + " TEXT " +
					")";

	public static final String TABLE_MYTRANSACTIONS = "mytransactions";
	private static final String TABLE_MYTRANSACTIONS_CREATE = 
			"CREATE TABLE " + TABLE_MYTRANSACTIONS + " (" +
			COLUMN_ID + " INTEGER PRIMARY KEY)";
	
	public WMCVIPDBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
		db.execSQL(TABLE_MYTRANSACTIONS_CREATE);

		Log.i(LOGTAG, "Tables has been created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYTRANSACTIONS);
		onCreate(db);

		Log.i(LOGTAG, "Database has been upgraded from " + 
				oldVersion + " to " + newVersion);
	}

}
