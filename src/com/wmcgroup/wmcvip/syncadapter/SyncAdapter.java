/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.wmcgroup.wmcvip.syncadapter;

import com.wmcgroup.wmcvip.adapter.Constants;
import com.wmcgroup.wmcvip.json.JSONParser;
import com.wmcgroup.wmcvip.model.Transaction;

import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * SyncAdapter implementation for syncing sample SyncAdapter contacts to the
 * platform ContactOperations provider.  This sample shows a basic 2-way
 * sync between the client and a sample server.  It also contains an
 * example of how to update the contacts' status messages, which
 * would be useful for a messaging or social networking client.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {

    private static final String TAG = "WMCVIP-SyncAdapter";
    
    private static final String SYNC_MARKER_KEY = "com.wmcgroup.wmcvip.syncadapter.marker";
    private static final boolean NOTIFY_AUTH_FAILURE = true;

    private final AccountManager mAccountManager;

    private final Context mContext;
    
    // JSON Node names
	final String TAG_TRANSACTIONS = "transactions";
	final String TAG_ID = "id";
	final String TAG_BUSINESSDATE = "date_receipt";
	final String TAG_BEGINDATE = "";
	final String TAG_BEGINTIME = "";
	final String TAG_ENDDATE = "";
	final String TAG_ENDTIME = "endtime";
	final String TAG_OUTLETCODE = "outlet";
	final String TAG_CHECKNO = "transaction_id";
	final String TAG_VIPID = "vip_number";
	final String TAG_NETSALE = "netsale";
	final String TAG_SERVICECHARGE = "sc";
	final String TAG_TAX = "vat";
	final String TAG_ACTUALPAID = "total";
	final String TAG_IMAGE = "";
	
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContext = context;
        mAccountManager = AccountManager.get(context);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,
        ContentProviderClient provider, SyncResult syncResult) {
    	
    	// Building a print of the extras we got
        StringBuilder sb = new StringBuilder();
        if (extras != null) {
            for (String key : extras.keySet()) {
                sb.append(key + "[" + extras.get(key) + "] ");
            }
        }
        
        Log.d(TAG, "onPerformSync for account[" + account.name + "]. Extras: "+sb.toString());
        
        try {
            // Neu Sync lan dau tien, no se co gia tri 0, nhung lan ke tiep no co gia tri milliseconds LONG type
            long lastSyncMarker = getServerSyncMarker(account);

            if (lastSyncMarker == 0) {
                // Lam gi do neu Sync lan dau
            }
            
            // Use the account manager to request the AuthToken we'll need
            // to talk to WMCVIP.vn server.  If we don't have an AuthToken
            // yet, this could involve a round-trip to the server to request
            // and AuthToken.
            final String authtoken = mAccountManager.blockingGetAuthToken(account,
                    Constants.AUTHTOKEN_TYPE, NOTIFY_AUTH_FAILURE);

            
            Log.d(TAG, "Mizu: Sync time: [" + lastSyncMarker + "] | AuthToken: [" + authtoken + "] | Account name: [" + account.name + "] ");
            
            // Creating JSON Parser instance
    		JSONParser jParser = new JSONParser();
    		
    		// getting JSON string from URL
    		JSONObject json = jParser.getJSONTransactionsFromUrl("http://wmcvip.vn/android/synadapter.php", account.name, authtoken, "transaction", Long.toString(lastSyncMarker));
    		Log.i(TAG, "JSON Parser info: " + json.toString());
    		
    		// contacts JSONArray
    		JSONArray transactionsJSON = null;
    		
    		// Lay Transactions tren Server xuong gan vao ArrayList, dung de so sanh voi local
    		ArrayList<Transaction> remoteTransactions = new ArrayList<Transaction>();
    		
    		try {
    			// Getting Array of Transactions
    			transactionsJSON = json.getJSONArray(TAG_TRANSACTIONS);
    			
    			// Chi lam viec chi tiet tren tung transaction ton tai 
    			if (transactionsJSON.length() > 0){
	    			long newSyncState = 0;
	    			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date d = null;
					
	    			// looping through All Contacts
	    			for(int i = 0; i < transactionsJSON.length(); i++){
	    				
	    				// Add cac field vao 1 record transaction
	    				Transaction currentTransaction = new Transaction();
	    				
	    				JSONObject c = transactionsJSON.getJSONObject(i);
	    				
	    				// Storing each JSON item in variable
	    				String id = c.getString(TAG_ID);
	    				String businessdate = c.getString(TAG_BUSINESSDATE);
	    				String begindate = TAG_BEGINDATE;
	    				String begintime = TAG_BEGINTIME;
	    				String enddate = TAG_ENDDATE;
	    				String endtime = c.getString(TAG_ENDTIME);
	    				String outletcode = c.getString(TAG_OUTLETCODE);
	    				String checkno = c.getString(TAG_CHECKNO);
	    				String vipno = c.getString(TAG_VIPID);
	    				String netsale = c.getString(TAG_NETSALE);
	    				String servicecharge = c.getString(TAG_SERVICECHARGE);
	    				String tax = c.getString(TAG_TAX);
	    				String actualpaid = c.getString(TAG_ACTUALPAID);
	    				String image = TAG_IMAGE;
	    				
	    				currentTransaction.setId(Long.parseLong(id));
	    				currentTransaction.setBusinessDate(businessdate);
	    				currentTransaction.setBeginDate(begindate);
	    				currentTransaction.setBeginTime(begintime);
	    				currentTransaction.setEndDate(enddate);
	    				currentTransaction.setEndTime(endtime);
	    				currentTransaction.setOutletCode(outletcode);
	    				currentTransaction.setCheckNo(checkno);
	    				currentTransaction.setMemberId(vipno);
	    				currentTransaction.setNetSale(Double.parseDouble(netsale));
	    				currentTransaction.setServiceCharge(Double.parseDouble(servicecharge));
	    				currentTransaction.setTax(Double.parseDouble(tax));
	    				currentTransaction.setActualPaid(Double.parseDouble(actualpaid));
	    				currentTransaction.setImage(image);
	    				
	    				// Cap nhat thoi gian lay record ve, dua tren transaction 
						try {
							d = f.parse(endtime);
						} catch (java.text.ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						newSyncState = d.getTime();
	    				
	    				remoteTransactions.add(currentTransaction);
	    			}
	    			
	    			// Save off the new sync marker. On our next sync, we only want to receive
	                // transactions that have changed since this sync...
	                setServerSyncMarker(account, newSyncState);
    			}
    		} catch (JSONException e) {
    			Log.e(TAG, e.toString());
    		}
    		
    		// Chi bat dau Sync khi server co gi do moi: chu y truong hop muon Sync data tu Local len Server, neu co truong hop nay thi fai bo cau IF nay ra  
			if (transactionsJSON.length() > 0){
				
				// Lay data tren server; Lay data duoi local; So sanh de update lan nhau
	    		
	    		// Get Local Transactions from Phone Database via Provider
	            ArrayList<Transaction> localTransactions = new ArrayList<Transaction>();

	            final ContentResolver resolver = mContext.getContentResolver();
	            Cursor curTransactions = resolver.query(Constants.CONTENT_URI, null, null, null, null);
	            
	            if (curTransactions != null) {
	                while (curTransactions.moveToNext()) {
	                	localTransactions.add(new Transaction(curTransactions));
	                }
	                curTransactions.close();
	            }
	            
	            // Ham nay se sinh ra loi neu trong arraylist khong co record nao...
	            // Log.d(TAG, "Mizu - View Local Transactions: " + localTransactions.get(0).toString());
	            
	            // Kiem tra xem co gi ca upload len server hay ko
	            ArrayList<Transaction> showsToRemote = new ArrayList<Transaction>();
	            
	            // Khong hieu vi sao ham duoi khong dung duoc, nen fai viet ham thay the khac
	            //for (Transaction localTransaction : localTransactions) {
	            //    if (!remoteTransactions.contains(localTransaction))
	            //        showsToRemote.add(localTransaction);
	            //    Log.d(TAG, "Mizu - View Compare Remote and Local: " + remoteTransactions.contains(localTransaction));
	            //}
			            
	            for (Transaction localTransaction : localTransactions) {
	            	boolean addThisItem = true; 
	            	for (Transaction remoteTransaction : remoteTransactions) {
	            		// So sanh outlet code va transaction id co ton tai hay ko
	            		if (localTransaction.getCheckNo().equals(remoteTransaction.getCheckNo()) & localTransaction.getOutletCode().equals(remoteTransaction.getOutletCode())) {
	            			addThisItem = false;
	            			break;
	            		}
	            	}
	            	if (addThisItem) showsToRemote.add(localTransaction);
	            }

	            // Kiem tra xem co record nao can download ve Local khong?
	            ArrayList<Transaction> showsToLocal = new ArrayList<Transaction>();
	            for (Transaction remoteTransaction : remoteTransactions) {
	            	boolean addThisItem = true; 
	            	for (Transaction localTransaction : localTransactions) {
	            		// So sanh outlet code va transaction id co ton tai hay ko
	            		if (remoteTransaction.getCheckNo().equals(localTransaction.getCheckNo()) & remoteTransaction.getOutletCode().equals(localTransaction.getOutletCode())) {
	            			addThisItem = false;
	            			break;
	            		}
	            	}
	            	if (addThisItem) showsToLocal.add(remoteTransaction);
	            }
	    		
	            // Giai doan upload nhung khac biet duoi local len server
	            if (showsToRemote.size() == 0) {
	                Log.d(TAG, "UPLOAD Status (Mizugi said): Khong co gi moi de upload len server");
	            } else {
	                Log.d(TAG, "UPLOAD Status (Mizugi said): Co " + showsToRemote.size() + " records can upload len server");
	            }
	            
	            // Giai doan upload nhung khac biet tu server xuong local database
	            if (showsToLocal.size() == 0) {
	            	Log.d("TAG", "DOWNLOAD Status (Mizugi said): Khong co gi moi de download ve local");
	            } else {
	            	Log.d("TAG", "DOWNLOAD Status (Mizugi said): Co " + showsToLocal.size() + " records can download ve local");

	                // Updating local tv shows
	                int i = 0;
	                ContentValues showsToLocalValues[] = new ContentValues[showsToLocal.size()];
	                for (Transaction remoteTransaction : showsToLocal) {
	                    Log.d(TAG, "Downloading - Transaction number: [" + remoteTransaction.getCheckNo() + "]");
	                    showsToLocalValues[i++] = remoteTransaction.getContentValues();
	                }
	                provider.bulkInsert(Constants.CONTENT_URI, showsToLocalValues);
	            }
			} else {
				Log.d(TAG, "Mizugi said: khong co gi de Sync ca.");
			}
    		
            Log.d(TAG, "Mizugi said: Ket thuc qua trinh Sync nhe.");
    		
        } catch (final AuthenticatorException e) {
            Log.e(TAG, "AuthenticatorException", e);
            syncResult.stats.numParseExceptions++;
        } catch (final OperationCanceledException e) {
            Log.e(TAG, "OperationCanceledExcetpion", e);
        } catch (final IOException e) {
            Log.e(TAG, "IOException", e);
            syncResult.stats.numIoExceptions++;
        } catch (final ParseException e) {
            Log.e(TAG, "ParseException", e);
            syncResult.stats.numParseExceptions++;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "RemoteException", e);
	    } catch (Exception e) {
	    	Log.e(TAG, "Exception", e);
	    }
    }

    /**
     * Lay thoi gian Sync gan nhat ra; = 0 neu la lan dau tien Sync
     */
    private long getServerSyncMarker(Account account) {
        String markerString = mAccountManager.getUserData(account, SYNC_MARKER_KEY);
        if (!TextUtils.isEmpty(markerString)) {
            return Long.parseLong(markerString);
        }
        return 0;
    }

    /**
     * Cap nhat lai thoi gian Sync: lay field endtime cua transaction gan cho no: chuyen het thanh LONG MilliSeconds 
     */
    private void setServerSyncMarker(Account account, long marker) {
        mAccountManager.setUserData(account, SYNC_MARKER_KEY, Long.toString(marker));
    }
}

