package com.kumyoung.gtvkaraoke;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.kumyoung.main.app.LiveUpdateActivity;

public class LiveUpdateBroadcastReceiver extends BroadcastReceiver {
	// private final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	@Override
	public String toString() {
		super.toString();
		return getClass().getSimpleName() + '@' + Integer.toHexString(hashCode());
	}

	protected String getMethodName() {
		String name = Thread.currentThread().getStackTrace()[3].getMethodName();
		// int line = Thread.currentThread().getStackTrace()[3].getLineNumber();
		// name = String.format("line:%d - %s() ", line, name);
		name += "() ";
		return name;
	}

	private final Handler handler = new Handler();

	protected void removeCallbacks(Runnable r) {
		if (handler != null) {
			handler.removeCallbacks(r);
		}
	}

	protected void post(Runnable r) {
		if (handler != null) {
			handler.post(r);
		}
	}

	protected void postDelayed(Runnable r, long delayMillis) {
		if (handler != null) {
			handler.postDelayed(r, delayMillis);
		}
	}

	private boolean isUpdate = false;
	private int newVersionCode = 0;
	private String receivedPid;
	private boolean isSuccess = false;
	private int errorCode = 0;

	@Override
	public void onReceive(final Context context, final Intent intent)
	{
		Bundle extras = intent.getExtras();
		String action = intent.getAction();

		Log.e(toString() + "[LiveUpdateBroadcastReceiver]", getMethodName() + "action:" + action + ", extras:" + extras);

		if (action.equals("android.lgt.appstore.LIVE_UPDATE_AUTO")) {
			String text = "";

			isSuccess = extras.getBoolean("IS_SUCCESS");

			if (isSuccess) {
				isUpdate = extras.getBoolean("IS_UPDATE");
				receivedPid = extras.getString("PID");
				newVersionCode = extras.getInt("UPDATE_VERSION_CODE");

				// pid와 패키지 네임으로 해당 어플에 대한 update 정보인지 확인
				if (isUpdate)
				{
					if (receivedPid.equals("Q13011202819")) {

						// // update 존재
						// // Toast.makeText(context, "어플리케이션 업데이트를 위해 다시 시작됩니다.", Toast.LENGTH_SHORT).show();
						// if (Global.Inst().app != null) {
						// Global.Inst().app.doAlert(99998, "최신 버전으로 자동 업데이트 중입니다.", "어플리케이션을 다시 실행해주세요.");
						// }
						// // Log.e(toString() + "[LiveUpdateBroadcastReceiver]", getMethodName() + "최신 버전으로 자동 업데이트 중입니다.");
						//
						// String pkgName = "com.lguplus.iptv3.updatecheck";
						// String clsName = pkgName + ".UpdateService";
						//
						// Intent intentUpdate = new Intent();
						// intentUpdate.setAction("ozstore.external.linked");
						// intentUpdate.setComponent(new ComponentName(pkgName, clsName));
						// intentUpdate.setData(Uri.parse("ozstore://UPDATE/Q13011202819"));
						// ComponentName componentName = context.startService(intentUpdate);
						// Log.e(toString() + "[LiveUpdateBroadcastReceiver]", getMethodName() + "[startService]" + componentName + ":" + intentUpdate + ":" + intentUpdate.getData());
						//
						// isyoon:액티비티에서실행해야한다.
						Log.e(toString() + "[LiveUpdateBroadcastReceiver]", getMethodName() + "[startLiveUpdateService]" + (context instanceof LiveUpdateActivity) + ":" + context);
						if ((context != null) && (context instanceof LiveUpdateActivity)) {
							((LiveUpdateActivity) context).startLiveUpdateService();
						}

					}
				}
				else
				{
					// update 존재하지 않음.
				}
				// Toast.makeText(context, "접속성공 isUpdate : " + isUpdate +
				// "receivedPid : " + receivedPid +
				// "newVersionCode :" + newVersionCode,
				// Toast.LENGTH_SHORT
				// ).show();
				text = "접속성공:IS_UPDATE:" + isUpdate + ", PID:" + receivedPid + ", UPDATE_VERSION_CODE:" + newVersionCode;
			}
			else
			{
				// errorCode = extras.getInt("ERROR_CODE");
				// Toast.makeText( context, errorCode + " 접속실패", Toast.LENGTH_SHORT).show();
				errorCode = extras.getInt("ERROR_CODE");
				text = "접속실패:ERROR_CODE:" + errorCode;
			}

			// if (!TextUtils.isEmpty(text)) {
			// Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
			// }

			Log.e(toString() + "[LiveUpdateBroadcastReceiver]", getMethodName() + text);
		}

	}
}
