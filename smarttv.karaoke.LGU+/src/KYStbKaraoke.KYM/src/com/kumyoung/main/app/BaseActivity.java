package com.kumyoung.main.app;

//import kr.kumyoung.akaraoke.R;
//import kr.kumyoung.akaraoke.book.SongBookListActivity;
//import kr.kumyoung.akaraoke.category.CategoryListActivity;
//import kr.kumyoung.akaraoke.main.MainActivity;
//import kr.kumyoung.akaraoke.mysong.MysongListActivity;
//import kr.kumyoung.akaraoke.search.SearchListActivity;
//import kr.kumyoung.akaraoke.setting.SettingActivity;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * 
 * @author macuser
 *         하단 메뉴 바
 *
 */
public class BaseActivity extends Activity {
	public static String BROADCASTACTION = "others.menu.start";

	// bgkim APP KILL 이벤트 받으면 실제로 죽이는 리시버
	private static final String ACTION_DO_SUSPEND = "com.marvell.AppKiller.DO_SUSPEND";

	private final BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// closeThisMenu();
		}
	};

	private final BroadcastReceiver receiverAppKill = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			finish();
		}
	};

	/*
	 * private void closeThisMenu() {
	 * if(this instanceof ATVKaraokeActivity) {
	 * 
	 * } else {
	 * finish();
	 * }
	 * }
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// commonBtnSetting();

		IntentFilter filter = new IntentFilter();
		filter.addAction(BROADCASTACTION);
		registerReceiver(receiver, filter);

		IntentFilter filterAppKill = new IntentFilter();
		filterAppKill.addAction(ACTION_DO_SUSPEND);
		registerReceiver(receiverAppKill, filterAppKill);
	}

	// UI처리나 스케줄링을 위한 액티비티 마다있는 핸들러
	protected Handler mainHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			recieveMessage(msg);
		}
	};

	protected void setTitle(String titleStr) {
		/*
		 * TextView titleTextView = (TextView) findViewById(R.id.common_main_song_list_title);
		 * if(titleTextView != null) {
		 * titleTextView.setText(titleStr);
		 * }
		 */
	}

	// 메인핸들러 메시지를 받아주는 메소드
	protected void recieveMessage(Message msg) {
	}

	@Override
	public void startActivity(Intent intent) {
		startActivityForResult(intent, -99999);
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
		overridePendingTransition(0, 0);
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(0, 0);

		try {
			if (receiver != null) {
				unregisterReceiver(receiver);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (receiverAppKill != null) {
				unregisterReceiver(receiverAppKill);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
