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
 * 2012 All rights (c)KYGroup Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.KPOP
 * filename	:	GCM3IntentService.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ GCM3IntentService.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps.gcm;

import java.util.Set;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.Activity;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps._BaseApplication;
import kr.kymedia.karaoke.util.TextUtil;

/**
 * 
 * TODO<br>
 * 
 * <pre></pre>
 * 
 * @author isyoon
 * @since 2013. 12. 3.
 * @version 1.0
 */
public class GCM2IntentService extends IntentService {
	final private String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	public GCM2IntentService(String name) {
		super(name);
	}

	protected String getMethodName() {
		String name = Thread.currentThread().getStackTrace()[3].getMethodName();
		String text = String.format("%s()", name);
		// int line = Thread.currentThread().getStackTrace()[3].getLineNumber();
		// text = String.format("line:%d - %s() ", line, name);
		return text;
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		android.util.Log.d("[GCM]", "onHandleIntent:" + intent);

		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		// The getMessageType() intent parameter must be the intent you received
		// in your BroadcastReceiver.
		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) { // has effect of unparcelling Bundle
			// Filter messages based on message type. It is likely that GCM will be extended in the future
			// with new message types, so just ignore message types you're not interested in, or that you
			// don't recognize.
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
				// It's an error.
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
				// Deleted messages on the server.
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
				// It's a regular GCM message, do some work.
				// sendBroadcast(intent);
			}
			handleGCMMessage(getApplicationContext(), intent);
		}
	}

	/**
	 * 푸쉬처리된인텐트를 받은후 알림설정(노티피케이션)을 한다.<br>
	 * 기본적으로 홈화면으로 이동후 onNewIntent(...)/onPostCreate(...)에서 해당화면으로 이동한다.<br>
	 * 
	 * <pre>
	 * 전달데이터
	 * 노티피케이션용데이터
	 * 	index: 노티피케이션용id 
	 * 	ticker: 노티피케이션용티커
	 * 	title: 노티피케이션용타이틀
	 * 	message: 노티피케이션용메시지
	 * 화면이동데이터
	 * 	id: 옵션/컨텍스트 메뉴아이디
	 * 	type: KP전문용 "type"값
	 * 	info: info 태그데이터
	 * 	list: list 태그데이터
	 * </pre>
	 * 
	 * <br>
	 * 
	 * @see BaseActivity2#onPostCreate(Bundle)
	 * @see BaseActivity2#handleGCMMessage(Context, Intent, boolean, boolean)
	 * @see BaseActivity2#getIntentData()
	 */
	private void handleGCMMessage(Context context, Intent intent) {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[START]");
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, "[INTENT]" + intent);
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, "[EXTRAS]" + intent.getExtras());
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "[PARAMS]" + intent.getData());

		try {

			// 화면깨우기
			// PushWakeLock.acquireCpuWakeLock(context);

			// 노티피케이션용데이터
			String index = intent.getStringExtra("index");
			String ticker = intent.getStringExtra("ticker");
			String title = intent.getStringExtra("title");
			String message = intent.getStringExtra("message");
			// 화면이동데이터
			String id = intent.getStringExtra("id");
			if (TextUtil.isEmpty(id)) {
				id = intent.getStringExtra("menu_id");
			}
			String type = intent.getStringExtra("type");
			KPItem info = new KPItem(intent.getStringExtra("info"));
			KPItem list = new KPItem(intent.getStringExtra("list"));

			String text = "";
			text = "!!!![PUSH]!!!!";
			text += "\n[context]" + context;
			text += "\n[index]" + index;
			text += "\n[id]" + id;
			text += "\n[type]" + type;
			text += "\n[ticker]" + ticker;
			text += "\n[title]" + title;
			text += "\n[message]" + message;
			text += "\n[info]\n" + info.toString(2);
			text += "\n[list]\n" + list.toString(2);
			if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + "\n" + text);

			Intent nIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
			String action = nIntent.getAction();
			Set<String> categories = nIntent.getCategories();
			int flags = nIntent.getFlags();

			// 어플실행여부확인후어플호출
			// GCM2Activity에서 모두처리
			Activity activity = ((_BaseApplication) context).getActivity();
			if (activity != null) {
				nIntent = new Intent(context, GCM2Activity.class);
				action = activity.getIntent().getAction();
				categories = activity.getIntent().getCategories();
				flags = activity.getIntent().getFlags();
			}

			nIntent.setAction(action);
			if (categories != null) {
				for (String category : categories) {
					nIntent.addCategory(category);
				}
			}
			nIntent.addFlags(flags);

			if (intent != null) {
				nIntent.putExtras(intent.getExtras());
			}

			if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, nIntent + "\n" + nIntent.getExtras());

			// 바로오픈확인
			boolean open = Boolean.parseBoolean(list.getValue("open"));

			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + " - OPEN:" + open);

			if (open) {
				context.startActivity(nIntent);
			} else {
				int requestCode = 0;

				if (TextUtil.isNumeric(index)) {
					requestCode = Integer.parseInt(index);
				}

				PendingIntent pIntent = PendingIntent.getActivity(context, requestCode, nIntent, PendingIntent.FLAG_ONE_SHOT);

				if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "" + pIntent);

				long when = System.currentTimeMillis();

				// openPushNotification(context, pIntent, Integer.parseInt(index), when, ticker, title, message, false, ((_Application) context).notification_sound,
				// ((_Application) context).notification_vibration);
				((_BaseApplication) context).openNotification(pIntent, Integer.parseInt(index), when, ticker, title, message, false, ((_BaseApplication) context).notification_sound,
						((_BaseApplication) context).notification_vibration);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// /**
	// * 푸쉬서비스용<br>
	// * 알림창열기<br>
	// */
	// private void openPushNotification(Context context, PendingIntent cIntent, int id, long when,
	// CharSequence ticker, CharSequence title, CharSequence message, boolean ongoing,
	// boolean sound, boolean vibration) {
	//
	// if (_IKaraoke.DEBUG)Log2.e(__CLASSNAME__, getMethodName() + "[START]");
	// if (_IKaraoke.DEBUG)Log2.i(__CLASSNAME__, "[INTENT]" + cIntent);
	// if (_IKaraoke.DEBUG)Log2.i(__CLASSNAME__, getMethodName() + id + "," + ticker + "," + title + "," + message);
	//
	// try {
	// // Get Notification Service
	// NotificationManager nm = (NotificationManager) context
	// .getSystemService(Context.NOTIFICATION_SERVICE);
	//
	// Drawable d = context.getResources().getDrawable(R.drawable.ic_launcher);
	// Bitmap bm = ((BitmapDrawable) d).getBitmap();
	//
	// NotificationCompat.Builder nb = new NotificationCompat.Builder(context);
	// nb.setSmallIcon(R.drawable.ic_launcher_small).setLargeIcon(bm).setTicker(ticker)
	// .setWhen(when).setContentTitle(title).setContentText(message).setContentIntent(cIntent)
	// .setOngoing(ongoing).setOnlyAlertOnce(true).setAutoCancel(true);
	//
	// Notification nf = nb.build();
	// if (sound) {
	// nf.defaults |= Notification.DEFAULT_SOUND;
	// }
	// if (vibration) {
	// nf.defaults |= Notification.DEFAULT_VIBRATE;
	// }
	//
	// nm.notify(id, nf);
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	//
	// }

}
