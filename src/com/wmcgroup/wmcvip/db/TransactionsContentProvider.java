package com.wmcgroup.wmcvip.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

// Lay cac thuoc tinh cua Content Providers
import static com.wmcgroup.wmcvip.adapter.Constants.AUTHORITY;
import com.wmcgroup.wmcvip.adapter.Constants;

/**
 * Created by Mizugi on 10/01/14.
 */
public class TransactionsContentProvider extends ContentProvider {
	
	private static final String TAG = "TransactionsContentProvider";
	
    public static final UriMatcher URI_MATCHER = buildUriMatcher();
    public static final String PATH = "transactions";
    public static final int PATH_TOKEN = 100;
    public static final String PATH_FOR_ID = "transactions/*";
    public static final int PATH_FOR_ID_TOKEN = 200;

    // Uri Matcher for the content provider
    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = AUTHORITY;
        matcher.addURI(authority, PATH, PATH_TOKEN);
        matcher.addURI(authority, PATH_FOR_ID, PATH_FOR_ID_TOKEN);
        return matcher;
    }

    // Content Provider stuff

    private WMCVIPDBOpenHelper dbHelper;

    @Override
    public boolean onCreate() {
    	Log.i(TAG, "Dinh: Content Provider - Call the on Create()");
        Context ctx = getContext();
        dbHelper = new WMCVIPDBOpenHelper(ctx);
        return true;
    }

    @Override
    public String getType(Uri uri) {
    	Log.i(TAG, "Dinh: Content Provider - Call the getType()");
        final int match = URI_MATCHER.match(uri);
        switch (match) {
            case PATH_TOKEN:
                return Constants.CONTENT_TYPE_DIR;
            case PATH_FOR_ID_TOKEN:
                return Constants.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("URI " + uri + " is not supported.");
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        final int match = URI_MATCHER.match(uri);
        switch (match) {
            // retrieve tv shows list
            case PATH_TOKEN: {
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(WMCVIPDBOpenHelper.TABLE_TRANSACTIONS);
                Log.i(TAG, "Dinh: Content Provider - Call the Cursor Query with PATH_TOKEN value: " + PATH_TOKEN);
                return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            }
            case PATH_FOR_ID_TOKEN: {
            	Log.i(TAG, "Dinh: Content Provider - Call the Cursor Query with PATH_FOR_ID_TOKEN value: " + PATH_FOR_ID_TOKEN);
                int TransactionId = (int)ContentUris.parseId(uri);
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(WMCVIPDBOpenHelper.TABLE_TRANSACTIONS);
                builder.appendWhere(WMCVIPDBOpenHelper.COLUMN_ID + "=" + TransactionId);
                return builder.query(db, projection, selection,selectionArgs, null, null, sortOrder);
            }
            default:
            	Log.i(TAG, "Dinh: Content Provider - Call the Cursor Query: NULL???");
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int token = URI_MATCHER.match(uri);
        switch (token) {
            case PATH_TOKEN: {
            	Log.i(TAG, "Dinh: Content Provider - Call the Uri Insert with PATH_TOKEN value: " + PATH_TOKEN);
                long id = db.insert(WMCVIPDBOpenHelper.TABLE_TRANSACTIONS, null, values);
                if (id != -1)
                    getContext().getContentResolver().notifyChange(uri, null);
                return Constants.CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
            }
            default: {
            	Log.i(TAG, "Dinh: Content Provider - Call the Uri Insert with NULL???");
                throw new UnsupportedOperationException("URI: " + uri + " not supported.");
            }
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
    	Log.i(TAG, "Dinh: Content Provider - Call the Delete");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int token = URI_MATCHER.match(uri);
        int rowsDeleted = -1;
        switch (token) {
            case (PATH_TOKEN):
                rowsDeleted = db.delete(WMCVIPDBOpenHelper.TABLE_TRANSACTIONS, selection, selectionArgs);
                break;
            case (PATH_FOR_ID_TOKEN):
                String transactionsIdWhereClause = WMCVIPDBOpenHelper.COLUMN_ID + "=" + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection))
                	transactionsIdWhereClause += " AND " + selection;
                rowsDeleted = db.delete(WMCVIPDBOpenHelper.TABLE_TRANSACTIONS, transactionsIdWhereClause, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        // Notifying the changes, if there are any
        if (rowsDeleted != -1)
            getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    /**
     * Man..I'm tired..
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
    	Log.i(TAG, "Dinh: Content Provider - Call the Update");
        return 0;
    }
}