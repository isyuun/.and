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
 * 2015 All rights (c)KYGroup Co.,Ltd. reserved.
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * <p/>
 * project	:	Karaoke.TV
 * filename	:	Main.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv.btv
 *    |_ Main.java
 * </pre>
 */

package kr.kymedia.kykaraoke.tv.gtv;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;

import com.kumyoung.gtvkaraoke.BuildConfig;

import kr.kymedia.kykaraoke.tv._Main;
import kr.kymedia.kykaraoke.api.IKaraokeTV;

/**
 * <pre>
 *
 * </pre>
 *
 * @author isyoon
 * @since 2015. 2. 26.
 * @version 1.0
 */
class Main extends _Main {
	private final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	private String _toString() {

		return (BuildConfig.DEBUG ? __CLASSNAME__ : getClass().getSimpleName()) + '@' + Integer.toHexString(hashCode());
	}

	@Override
	protected String getMethodName() {
		String name = Thread.currentThread().getStackTrace()[3].getMethodName();
		// int line = Thread.currentThread().getStackTrace()[3].getLineNumber();
		// name = String.format("line:%d - %s() ", line, name);
		name += "() ";
		return name;
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main6XX#setIsKeySound(boolean)
	 * @see kr.kymedia.kykaraoke.tv.Main6XX#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setIsKeySound(false);
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main2#getTypeface()
	 */
	@Override
	public Typeface getTypeface() {

		return null;
	}

	@Override
	protected void init() {

		super.init();
	}

	/**
	 * <pre>
	 * 강제디버그경고표시
	 * 릴리스전반드시푼다
	 * p_debug:"DEBUG"
	 * </pre>
	 *
	 */
	@Override
	protected void showCBT(boolean show) {
		p_debug = "DEBUG";
		super.showCBT(show);
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main3X#init()
	 */
	@Override
	protected void getVender() {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + getIntent() + getIntent().getExtras());
		super.getVender();
	}

	@Override
	protected void onNewIntent(Intent intent) {

		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + intent + intent.getExtras());
		super.onNewIntent(intent);
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + getIntent() + getIntent().getExtras());
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main2#onStart()
	 */
	@Override
	protected void onStart() {

		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName());
		super.onStart();
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main2#onStop()
	 */
	@Override
	protected void onStop() {

		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName());
		super.onStop();
	}

	public void doAlert(int return_code, String return_message, String svc_message) {
		String strReturnCode = "XXXXX";

		switch (return_code) {
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
		//CustomDialog.Builder customBuilder = new CustomDialog.Builder(this);
		//customBuilder.setTitle(strReturnCode)
		//		.setMessage(return_message + "\n\n" + svc_message)
		//		.setPositiveButton("확 인", new DialogInterface.OnClickListener() {
		//			@Override
		//			public void onClick(DialogInterface dialog, int which) {
		//				dialog.dismiss();
		//				finish();
		//			}
		//		});
		//
		//CustomDialog dialog = customBuilder.create();
		//dialog.show();
		doAlertExit(strReturnCode, return_message + "\n\n" + svc_message);
	}

	//public void doAlert(String strMsg, final boolean needkill) {
	//	_LOG(_toString(), getMethodName() + strMsg, "" + needkill);
	//
	//	/*
	//	 * AlertDialog ad = new AlertDialog.Builder(ATVKaraokeActivity.this).create();
	//	 * ad.setCancelable(false); // This blocks the 'BACK' button
	//	 * ad.setMessage(strMsg);
	//	 * ad.setButton( AlertDialog.BUTTON_POSITIVE, "확 인", new DialogInterface.OnClickListener() {
	//	 *
	//	 * @Override
	//	 * public void onClick(DialogInterface dialog, int which) {
	//	 * dialog.dismiss();
	//	 * if ( needkill )
	//	 * {
	//	 * ATVKaraokeActivity.this.finish();
	//	 * }
	//	 * }
	//	 * });
	//	 * ad.show();
	//	 */
	//	//
	//	//CustomDialog.Builder customBuilder = new CustomDialog.Builder(this);
	//	//customBuilder.setTitle("알  림").setMessage(strMsg)
	//	//		/*
	//	//		 * .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	//	//		 * public void onClick(DialogInterface dialog, int which) {
	//	//		 * KYStbKaraokeActivity.this.dismissDialog(CUSTOM_DIALOG);
	//	//		 * }
	//	//		 * })
	//	//		 */.setPositiveButton("확 인", new DialogInterface.OnClickListener() {
	//	//	@Override
	//	//	public void onClick(DialogInterface dialog, int which) {
	//	//		dialog.dismiss();
	//	//		if (needkill) {
	//	//			finish();
	//	//		}
	//	//	}
	//	//});
	//	//
	//	//CustomDialog dialog = customBuilder.create();
	//	//dialog.show();
	//}

}
