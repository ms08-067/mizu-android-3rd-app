package com.wmcgroup.wmcvip.model;

import java.text.NumberFormat;

import com.wmcgroup.wmcvip.MainWMCVip;
import com.wmcgroup.wmcvip.db.WMCVIPDBOpenHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Transaction implements Parcelable {
	
	public static final String LOGTAG="WMCVIP-TransactionModel";
	
	private long id;
	private String businessdate;
	private String begindate;
	private String begintime;
	private String enddate;
	private String endtime;
	private String outletcode;
	private String checkno;
	private String memberid;
	private double netsale;
	private double servicecharge;
	private double tax;
	private double actualpaid;
	private String image;	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getBusinessDate() {
		return businessdate;
	}
	public void setBusinessDate(String businessdate) {
		this.businessdate = businessdate;
	}
	public String getBeginDate() {
		return begindate;
	}
	public void setBeginDate(String begindate) {
		this.begindate = begindate;
	}
	public String getBeginTime() {
		return begintime;
	}
	public void setBeginTime(String begintime) {
		this.begintime = begintime;
	}
	public String getEndDate() {
		return enddate;
	}
	public void setEndDate(String enddate) {
		this.enddate = enddate;
	}
	public String getEndTime() {
		return endtime;
	}
	public void setEndTime(String endtime) {
		this.endtime = endtime;
	}
	public String getOutletCode() {
		return outletcode;
	}
	public void setOutletCode(String outletcode) {
		this.outletcode = outletcode;
	}
	public String getCheckNo() {
		return checkno;
	}
	public void setCheckNo(String checkno) {
		this.checkno = checkno;
	}
	public String getMemberId() {
		return memberid;
	}
	public void setMemberId(String memberid) {
		this.memberid = memberid;
	}
	public double getNetSale() {
		return netsale;
	}
	public void setNetSale(double netsale) {
		this.netsale = netsale;
	}
	public double getServiceCharge() {
		return servicecharge;
	}
	public void setServiceCharge(double servicecharge) {
		this.servicecharge = servicecharge;
	}

	public double getTax() {
		return tax;
	}
	public void setTax(double tax) {
		this.tax = tax;
	}
	public double getActualPaid() {
		return actualpaid;
	}
	public void setActualPaid(double actualpaid) {
		this.actualpaid = actualpaid;
	}
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	@Override
	// Duoc su dung cho truong hop ko co layout (listitem_transaction.xml), testing (layout nay cua Android android.R.layout.simple_list_item_1)
	public String toString() {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		
		String result = "";
		
		result = result + "[id: " + id + " | ";
		result = result + "businessdate: " + businessdate + " | ";
		result = result + "begindate: " + begindate + " | ";
		result = result + "begintime: " + begintime + " | ";
		result = result + "enddate: " + enddate + " | ";
		result = result + "endtime: " + endtime + " | ";
		result = result + "outletcode: " + outletcode + " | ";
		result = result + "checkno: " + checkno + " | ";
		result = result + "memberid: " + memberid + " | ";
		result = result + "netsale: " + netsale + " | ";
		result = result + "servicecharge: " + servicecharge + " | ";
		result = result + "tax: " + tax + " | ";
		result = result + "actualpaid: " + actualpaid + " | ";
		result = result + "image: " + image + "]";
		
		return result;//"CheckNo: " + checkno + "\n(" + nf.format(actualpaid) + ")";
	}
	
    public Transaction() {
    }

    public Transaction(Parcel in) {
         Log.i(LOGTAG, "Mizu said: Parcel constructor");
        
         id = in.readLong();
         businessdate = in.readString();
         begindate = in.readString();
         begintime = in.readString();
         enddate = in.readString();
         endtime = in.readString();
         outletcode = in.readString();
         checkno = in.readString();
         memberid = in.readString();
         netsale = in.readDouble();
         servicecharge = in.readDouble();
         tax = in.readDouble();
         actualpaid = in.readDouble();
         image = in.readString();
    }

    @Override
    public int describeContents() {
         return 0;
    }
   
    @Override
    public void writeToParcel(Parcel dest, int flags) {
         Log.i(LOGTAG, "Mizu said: writeToParcel");
        
         dest.writeLong(id);
         dest.writeString(businessdate);
         dest.writeString(begindate);
         dest.writeString(begintime);
         dest.writeString(enddate);
         dest.writeString(endtime);
         dest.writeString(outletcode);
         dest.writeString(checkno);
         dest.writeString(memberid);
         dest.writeDouble(netsale);
         dest.writeDouble(servicecharge);
         dest.writeDouble(tax);
         dest.writeDouble(actualpaid);
         dest.writeString(image);
    }

    // Create a Transaction from a cursor
    public Transaction (Cursor curTransactions) {
    	Log.i(LOGTAG, "Mizu said: Create a Transaction from a cursor");
        
        id = curTransactions.getLong(curTransactions.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_ID));
        businessdate = curTransactions.getString(curTransactions.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_BUSINESSDATE));
        begindate = curTransactions.getString(curTransactions.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_BEGINDATE));
        begintime = curTransactions.getString(curTransactions.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_BEGINTIME));
        enddate = curTransactions.getString(curTransactions.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_ENDDATE));
        endtime = curTransactions.getString(curTransactions.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_ENDTIME));
        outletcode = curTransactions.getString(curTransactions.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_OUTLETCODE));
        checkno = curTransactions.getString(curTransactions.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_CHECKNO));
        memberid = curTransactions.getString(curTransactions.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_MEMBERID));
        netsale = curTransactions.getDouble(curTransactions.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_NETSALE));
        servicecharge = curTransactions.getDouble(curTransactions.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_SERVICECHARGE));
        tax = curTransactions.getDouble(curTransactions.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_TAX));
        actualpaid = curTransactions.getDouble(curTransactions.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_ACTUALPAID));
        image = curTransactions.getString(curTransactions.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_IMAGE));
    }
    
    /**
     * Convenient method to get the objects data members in ContentValues object.
     * This will be useful for Content Provider operations,
     * which use ContentValues object to represent the data.
     *
     * @return
     */
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        
        values.put(WMCVIPDBOpenHelper.COLUMN_ID, id);
        values.put(WMCVIPDBOpenHelper.COLUMN_BUSINESSDATE,businessdate);
        values.put(WMCVIPDBOpenHelper.COLUMN_BEGINDATE, begindate);
        values.put(WMCVIPDBOpenHelper.COLUMN_BEGINTIME, begintime);
        values.put(WMCVIPDBOpenHelper.COLUMN_ENDDATE,enddate);
        values.put(WMCVIPDBOpenHelper.COLUMN_ENDTIME,endtime);
        values.put(WMCVIPDBOpenHelper.COLUMN_OUTLETCODE,outletcode);
        values.put(WMCVIPDBOpenHelper.COLUMN_CHECKNO,checkno);
        values.put(WMCVIPDBOpenHelper.COLUMN_MEMBERID,memberid);
        values.put(WMCVIPDBOpenHelper.COLUMN_NETSALE,netsale);
        values.put(WMCVIPDBOpenHelper.COLUMN_SERVICECHARGE,servicecharge);
        values.put(WMCVIPDBOpenHelper.COLUMN_TAX,tax);
        values.put(WMCVIPDBOpenHelper.COLUMN_ACTUALPAID,actualpaid);
        values.put(WMCVIPDBOpenHelper.COLUMN_IMAGE,image);        
        
        return values;
    }
    
    public static final Parcelable.Creator<Transaction> CREATOR =
              new Parcelable.Creator<Transaction>() {

         @Override
         public Transaction createFromParcel(Parcel source) {
              Log.i(LOGTAG, "Mizu said: createFromParcel");
              return new Transaction(source);
         }

         @Override
         public Transaction[] newArray(int size) {
              Log.i(LOGTAG, "Mizu said: newArray");
              return new Transaction[size];
         }

    };
}
