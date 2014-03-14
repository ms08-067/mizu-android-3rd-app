package com.wmcgroup.wmcvip.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.wmcgroup.wmcvip.db.WMCVIPDBOpenHelper;

public class Profile implements Parcelable {
	
	public static final String LOGTAG="WMCVIP-ProfileModel";
	
	private String f_name;
	private String l_name;
	private String created_date;
	private long   p_o_u;
	private String gender;
	private String dob;
	private String phone;
	private String p_email;
	private String member_id;
	private String outlet_date;
	private String last_visit_place;
	
	
	public String getF_name() {
		return f_name;
	}
	public void setF_name(String f_name) {
		this.f_name = f_name;
	}
	
	public String getL_name() {
		return l_name;
	}
	public void setL_name(String l_name) {
		this.l_name = l_name;
	}
	
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	
	public long getP_o_u() {
		return p_o_u;
	}
	public void setP_o_u(long p_o_u) {
		this.p_o_u = p_o_u;
	}
	
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	

	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getP_email() {
		return p_email;
	}
	public void setP_email(String p_email) {
		this.p_email = p_email;
	}
	
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	
	
	public String getOutlet_date() {
		return outlet_date;
	}
	public void setOutlet_date(String outlet_date) {
		this.outlet_date = outlet_date;
	}
	
	public String getLast_visit_place() {
		return last_visit_place;
	}
	public void setLast_visit_place(String last_visit_place) {
		this.last_visit_place = last_visit_place;
	}
	
	@Override
	public String toString() {
		String result = "";
		
		result = result + "[f_name: " + f_name + " | ";
		result = result + "l_name: "  + l_name + " | ";
		result = result + "created_date: " + created_date + " | ";
		result = result + "p_o_u: "        + p_o_u + " | ";
		result = result + "gender: "       + gender + " | ";
		result = result + "dob: "          + dob + " | ";
		result = result + "phone: "        + phone + " | ";
		result = result + "p_email: "      + p_email + " | ";
		result = result + "member_id: "    + member_id + " | ";
		result = result + "outlet_date: "  + outlet_date + " | ";
		result = result + "last_visit_place: " + last_visit_place + " | ";
		
		return result;
	}
	
    public Profile() {}

    public Profile(Parcel in) {
        
    	 f_name = in.readString();
    	 l_name = in.readString();
    	 created_date = in.readString();
    	 p_o_u  = in.readLong();
    	 gender = in.readString();
    	 dob = in.readString();
    	 phone = in.readString();
    	 p_email = in.readString();
    	 member_id = in.readString();
    	 outlet_date = in.readString();
    	 last_visit_place = in.readString();
    }
   
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        
         dest.writeString(f_name);
         dest.writeString(l_name);
         dest.writeString(created_date);
         dest.writeLong(p_o_u);
         dest.writeString(gender);
         dest.writeString(dob);
         dest.writeString(phone);
         dest.writeString(p_email);
         dest.writeString(member_id);
         dest.writeString(outlet_date);
         dest.writeString(last_visit_place);
        
    }

   
    public Profile (Cursor curProfile) {
    	
    	Log.i(LOGTAG, "Mizu said: Create a Profile from a cursor");
    	f_name = curProfile.getString(curProfile.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_FNAME));
    	l_name = curProfile.getString(curProfile.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_LNAME));
    	created_date = curProfile.getString(curProfile.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_CREATEDDATE));
    	p_o_u = curProfile.getLong(curProfile.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_POU));
    	gender = curProfile.getString(curProfile.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_GENDER));
    	dob = curProfile.getString(curProfile.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_DOB));
    	phone = curProfile.getString(curProfile.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_PHONE));
    	p_email = curProfile.getString(curProfile.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_PEMAIL));
    	member_id = curProfile.getString(curProfile.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_MEMBER));
    	outlet_date = curProfile.getString(curProfile.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_OUTLETDATE));
    	last_visit_place = curProfile.getString(curProfile.getColumnIndex(WMCVIPDBOpenHelper.COLUMN_LASTVISITPLACE));
    	
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
       // values.put(WMCVIPDBOpenHelper.COLUMN_ID, id);
        values.put(WMCVIPDBOpenHelper.COLUMN_FNAME,f_name);
        values.put(WMCVIPDBOpenHelper.COLUMN_LNAME, l_name);
        values.put(WMCVIPDBOpenHelper.COLUMN_CREATEDDATE, created_date);
        values.put(WMCVIPDBOpenHelper.COLUMN_POU,p_o_u);
        values.put(WMCVIPDBOpenHelper.COLUMN_GENDER,gender);
        values.put(WMCVIPDBOpenHelper.COLUMN_DOB,dob);
        values.put(WMCVIPDBOpenHelper.COLUMN_PHONE,phone);
        values.put(WMCVIPDBOpenHelper.COLUMN_PEMAIL,p_email);
        values.put(WMCVIPDBOpenHelper.COLUMN_MEMBER,member_id);
        values.put(WMCVIPDBOpenHelper.COLUMN_OUTLETDATE,outlet_date);
        values.put(WMCVIPDBOpenHelper.COLUMN_LASTVISITPLACE,last_visit_place);      
        
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


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
    
    
    
}
