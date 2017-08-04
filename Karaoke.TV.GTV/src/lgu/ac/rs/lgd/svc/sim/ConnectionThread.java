/*
 * Copyright 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * 2016 All rights (c)KYmedia Co.,Ltd. reserved.
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * <p/>
 * project	:	.prj
 * filename	:	ConnectionThread.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv.api.gtv.lgu
 *    |_ ConnectionThread.java
 * </pre>
 */
package lgu.ac.rs.lgd.svc.sim;

import android.util.Log;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;

import lgu.ac.rs.com.model.RequestData;
import lgu.ac.rs.com.model.ResponseData;

/**
 * <pre>
 *
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2016-08-05
 */
public class ConnectionThread implements Runnable {
	private String hosturl;
	private boolean isreturn = false;
	private boolean isconnect = false;
	private URL url = null;
	private HttpURLConnection urlConn = null;
	private boolean iskill = false;
	private int check_term = 100;
	private int current_time = 0;
	private int out_time = 5000;
	private RequestData requestdata;
	private ResponseData returndata;

	public ConnectionThread() {
	}

	public ConnectionThread(String strUrl) {
		this.hosturl = strUrl;
	}

	public boolean isConnect() {
		return this.isconnect;
	}

	public boolean isReturn() {
		return this.isreturn;
	}

	public ResponseData getReturnData() {
		return this.returndata;
	}

	public void setCheckTerm(int check_term) {
		this.check_term = check_term;
	}

	public void setOutTime(int out_time) {
		this.out_time = out_time;
	}

	public void setRequestData(RequestData requestdata) {
		this.requestdata = requestdata;
	}

	public void kill(boolean iskill) {
		this.iskill = iskill;
	}

	public void run() {
		this.returndata = new ResponseData();

		try {
			this.url = new URL(this.hosturl);
			this.urlConn = (HttpURLConnection)this.url.openConnection();
			this.urlConn.setDoInput(true);
			this.urlConn.setDoOutput(true);
			this.urlConn.setUseCaches(false);
			this.urlConn.setDefaultUseCaches(false);
			this.urlConn.setRequestProperty("Content-Type", "application/octet-stream");
			ObjectOutputStream e = new ObjectOutputStream(this.urlConn.getOutputStream());
			e.writeObject(this.requestdata);
			e.reset();
			e.close();
			ObjectInputStream ois = new ObjectInputStream(this.urlConn.getInputStream());
			ResponseData responsedata = (ResponseData)ois.readObject();
			ois.close();
			if(!(responsedata instanceof ResponseData)) {
				System.out.println("Error : ClassCastException");
				this.returndata.return_code = "40003";
				this.returndata.return_message = "객체를 수신하지 못하였습니다.";
				this.returndata.fields = new Hashtable();
			} else {
				this.returndata = responsedata;
			}

			if(this.urlConn.getResponseCode() == 200) {
				this.isconnect = true;
			} else {
				this.isconnect = false;
			}
		} catch (Exception var4) {
			var4.printStackTrace();
			System.out.println("[RSRequestClient].sendData(Obj) Exception : " + var4.toString());
			Log.wtf("[VASS]" + "[ConnectionThread]", "run()" + Log.getStackTraceString(var4));
			this.returndata.return_code = "40000";
			this.returndata.return_message = "server와의 통신 중 에러가 발생하였습니다.\n" + var4.toString();
		}

		this.isreturn = true;

		try {
			while(!this.iskill) {
				Thread.sleep((long)this.check_term);
				this.current_time += this.check_term;
				if(this.current_time > this.out_time) {
					break;
				}
			}
		} catch (InterruptedException var5) {
			var5.printStackTrace();
		}

	}
}
