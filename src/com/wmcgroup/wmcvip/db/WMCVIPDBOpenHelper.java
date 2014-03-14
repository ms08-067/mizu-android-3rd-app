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
	
	//For table Profile
	public static final String TABLE_PROFILE = "profile";
	public static final String COLUMN_IDPROFILE = "id";
	public static final String COLUMN_FNAME = "f_name";
	public static final String COLUMN_LNAME = "l_name";
	public static final String COLUMN_CREATEDDATE = "created_date";
	public static final String COLUMN_POU = "p_o_u";
	public static final String COLUMN_GENDER = "gender";
	public static final String COLUMN_DOB = "dob";
	public static final String COLUMN_PHONE = "phone";
	public static final String COLUMN_PEMAIL = "p_email";
	public static final String COLUMN_MEMBER = "member_id";
	public static final String COLUMN_OUTLETDATE = "outlet_date";
	public static final String COLUMN_LASTVISITPLACE = "last_visit_place";
	

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
	
	private static final String TABLE_PROFILE_CREATE = 
			
			"CREATE TABLE " + TABLE_PROFILE + " (" +
					COLUMN_IDPROFILE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					COLUMN_FNAME + " TEXT, " +
					COLUMN_LNAME + " TEXT, " +
					COLUMN_CREATEDDATE + " TEXT, " +
					COLUMN_POU + " NUMERIC , " +
					COLUMN_GENDER + " TEXT, " +
					COLUMN_DOB + " TEXT, " +
					COLUMN_PHONE + " TEXT, " +
					COLUMN_PEMAIL + " TEXT, " +
					COLUMN_MEMBER + " NUMERIC, " +
					COLUMN_OUTLETDATE + " TEXT, " +
					COLUMN_LASTVISITPLACE + " TEXT, " +
					")";
	
	

	public static final String TABLE_MYTRANSACTIONS = "mytransactions";
	
	public static final String TABLE_MYPROFILE      = "myprofile";
	
	private static final String TABLE_MYTRANSACTIONS_CREATE = 
			"CREATE TABLE " + TABLE_MYTRANSACTIONS + " (" +
			COLUMN_ID + " INTEGER PRIMARY KEY)";
	
	private static final String TABLE_MYPROFILE_CREATE = 
			"CREATE TABLE " + TABLE_MYPROFILE + " (" +
			COLUMN_ID + " INTEGER PRIMARY KEY)";
	
	public WMCVIPDBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//Create table transaction and table mytransaction
		db.execSQL(TABLE_CREATE);
		db.execSQL(TABLE_MYTRANSACTIONS_CREATE);
		
		//Create table Profile and table myprofile
		db.execSQL(TABLE_PROFILE_CREATE);
		db.execSQL(TABLE_MYPROFILE_CREATE);
		
		Log.i(LOGTAG, "Tables has been created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYTRANSACTIONS);
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYPROFILE);
		
		onCreate(db);

		Log.i(LOGTAG, "Database has been upgraded from " + 
				oldVersion + " to " + newVersion);
	}
	

}
