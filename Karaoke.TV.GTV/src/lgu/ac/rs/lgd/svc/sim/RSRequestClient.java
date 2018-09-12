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
 * 2016 All rights (c)KYGroup Co.,Ltd. reserved.
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * <p/>
 * project	:	.prj
 * filename	:	RSRequestClient.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv.api.gtv.lgu
 *    |_ RSRequestClient.java
 * </pre>
 */
package lgu.ac.rs.lgd.svc.sim;

import android.util.Log;

/**
 * <pre>
 *
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2016-08-05
 */
public class RSRequestClient {
	private String hostname = "127.0.0.1";
	private int port = 8080;
	private String hosturl = "/SIMListener";
	private lgu.ac.rs.com.model.ResponseData returndata = null;

	public RSRequestClient() {
	}

	public RSRequestClient(String hostname, int port, String hosturl) {
		this.hostname = hostname;
		this.port = port;
		this.hosturl = hosturl;
	}

	public void setHostUrl(String hosturl) {
		this.hosturl = hosturl;
	}

	public lgu.ac.rs.com.model.ResponseData sendData(lgu.ac.rs.com.model.RequestData requestdata) {
		Object urlConn = null;
		this.returndata = new lgu.ac.rs.com.model.ResponseData();

		try {
			String ex = "http://" + this.hostname + ":" + Integer.toString(this.port) + this.hosturl;
			System.out.println("[RSRequestClient].sendData(obj) call url >> " + ex);
			Log.wtf("[VASS]" + "RSRequestClient", "sendData()" + ex);
			byte check_term = 100;
			int current_time = 0;
			short out_time = 5000;
			ConnectionThread ct = new ConnectionThread(ex);
			ct.setCheckTerm(check_term);
			ct.setOutTime(out_time);
			ct.setRequestData(requestdata);
			Thread thread = new Thread(ct);
			thread.start();

			try {
				boolean e = true;

				while(e) {
					if(ct.isReturn()) {
						if(ct.isConnect()) {
							this.returndata = ct.getReturnData();
						} else {
							this.returndata.return_code = "40000";
							this.returndata.return_message = "서버와의 통신 중 에러가 발생하였습니다.";
						}

						ct.kill(true);
						break;
					}

					current_time += check_term;
					if(current_time > out_time) {
						this.returndata.return_code = "40000";
						this.returndata.return_message = "server와의 통신 중 에러가 발생하였습니다.\n";
						ct.kill(true);
						break;
					}

					try {
						Thread.sleep((long)check_term);
					} catch (InterruptedException var11) {
						var11.printStackTrace();
						break;
					}
				}
			} catch (Exception var12) {
				var12.printStackTrace();
			}
		} catch (Exception var13) {
			var13.printStackTrace();
			System.out.println("[RSRequestClient].sendData(Obj) Exception : " + var13.toString());
			Log.wtf("[VASS]" + "[RSRequestClient]", "sendData()" + Log.getStackTraceString(var13));
			this.returndata.return_code = "40000";
			this.returndata.return_message = "server와의 통신 중 에러가 발생하였습니다.\n" + var13.toString();
		}

		return this.returndata;
	}

	public Object getReturnData() {
		return this.returndata;
	}
}
