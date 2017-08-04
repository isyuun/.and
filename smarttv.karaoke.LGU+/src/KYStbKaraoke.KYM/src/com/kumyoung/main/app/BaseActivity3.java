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
 * 2015 All rights (c)KYmedia Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * 
 * project	:	KYStbKaraoke.KYM
 * filename	:	BaseActivity3.java
 * author	:	isyoon
 *
 * <pre>
 * com.kumyoung.main.app
 *    |_ BaseActivity3.java
 * </pre>
 * 
 */

package com.kumyoung.main.app;

import android.content.DialogInterface;

import com.kumyoung.gtvkaraoke.CustomDialog;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author	isyoon
 * @since	2015. 6. 12.
 * @version 1.0
 */
public class BaseActivity3 extends BaseActivity2 {
	public void doAlert(int return_code, String return_message, String svc_message) {

		String strReturnCode = "XXXXX";

		switch (return_code)
		{
		case 20004:
			strReturnCode = String.format("서비스 오류 (R-71009)");
			return_message = String.format("서비스 이용 중 오류가 발생하였습니다.\n재시도 후 문제가 반복되면 셋탑의 전원을\n뺏다가 꽂아 주세요. 문제가 해결되지 않으면");
			svc_message = String.format("고객센터(국번없이 101번)로 문의 주시기 바립니다.");
			break;
		case 40000:
			strReturnCode = String.format("인터넷 오류 (R-74014)");
			break;
		case 40001:
			strReturnCode = String.format("서비스 오류 (R-74015)");
			break;
		case 40002:
			strReturnCode = String.format("서비스 오류 (R-74016)");
			break;
		case 50000:
			strReturnCode = String.format("서비스 오류 (R-74017)");
			break;
		case 50001:
			strReturnCode = String.format("서비스 미가입");
			break;
		case 50002:
			strReturnCode = String.format("서비스 가입처리 중");
			break;
		case 50003:
			strReturnCode = String.format("가입자 오류 (R-72018)");
			break;
		case 50004:
			strReturnCode = String.format("서비스 오류 (R-72019)");
			break;
		case 50005:
			strReturnCode = String.format("가입자 오류 (R-72030)");
			break;
		case 50006:
			strReturnCode = String.format("가입자 오류 (R-72031)");
			break;
		case 60000:
			strReturnCode = String.format("서비스 오류 (R-72032)");
			break;
		case 99999:
			strReturnCode = String.format("서비스 오류 (R-72021)");
			break;
		case 99998:
			strReturnCode = String.format("업데이트");
			break;

		default:
			strReturnCode = String.format("서비스 오류(%05d)", return_code);
			break;
		}

		/*
		 * AlertDialog ad = new AlertDialog.Builder(ATVKaraokeActivity.this).create();
		 * ad.setCancelable(false); // This blocks the 'BACK' button
		 * ad.setMessage("서비스를 이용하실 수 없습니다.\n\n"
		 * + "오류코드 : " + strReturnCode + "\n"
		 * + "오류메시지 : " + return_message + "\n"
		 * + "\n"
		 * + SIMClientHandlerLGU.svc_kumyoung
		 * );
		 * 
		 * ad.setButton( AlertDialog.BUTTON_POSITIVE, "확 인", new DialogInterface.OnClickListener() {
		 * 
		 * @Override
		 * public void onClick(DialogInterface dialog, int which) {
		 * dialog.dismiss();
		 * ATVKaraokeActivity.this.finish();
		 * }
		 * });
		 * ad.show();
		 */
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(this);
		customBuilder.setTitle(strReturnCode)
				.setMessage(return_message + "\n\n" + svc_message)
				.setPositiveButton("확 인", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						finish();
					}
				});

		CustomDialog dialog = customBuilder.create();
		dialog.show();

	}
}

