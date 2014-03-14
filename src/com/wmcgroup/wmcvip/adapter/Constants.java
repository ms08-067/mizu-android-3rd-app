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
package com.wmcgroup.wmcvip.adapter;

import android.net.Uri;

public class Constants {

	/* 
     * Cac thong so sau lien quan den Adapter: rat de sinh ra bug; no fai trung voi authenticator.xml va synadapter.xml
     */
    public static final String ACCOUNT_TYPE = "com.wmcgroup.wmcvip";

    /**
     * Authtoken type string: rat de sinh ra bug; no fai trung voi authenticator.xml va synadapter.xml
     */
    public static final String AUTHTOKEN_TYPE = "com.wmcgroup.wmcvip";
    
    /* 
     * Cac thong so sau lien quan den Content Provider
     */
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/com.wmcgroup.wmcvip";
    public static final String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/com.wmcgroup.wmcvip";

    public static final String AUTHORITY = "com.wmcgroup.wmcvip.provider";
    // content://<authority>/<path to type>
    public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/transactions");
    
    public static final String PROVIDER_ID = "id";
	public static final String PROVIDER_BUSINESSDATE = "businessdate";
	public static final String PROVIDER_BEGINDATE = "begindate";
	public static final String PROVIDER_BEGINTIME = "begintime";
	public static final String PROVIDER_ENDDATE = "enddate";
	public static final String PROVIDER_ENDTIME = "endtime";
	public static final String PROVIDER_OUTLETCODE = "outletcode";
	public static final String PROVIDER_CHECKNO = "checkno";
	public static final String PROVIDER_MEMBERID = "memberid";
	public static final String PROVIDER_NETSALE = "netsale";
	public static final String PROVIDER_SERVICECHARGE = "servicecharge";
	public static final String PROVIDER_TAX = "tax";
	public static final String PROVIDER_ACTUALPAID = "actualpaid";
	
}
