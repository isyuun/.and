package com.kumyoung.gtvkaraoke;

/*
 import java.io.File;
 import java.io.FileOutputStream;
 import java.io.InputStream;
 import java.net.URL;
 import java.net.URLConnecti/on;
 */

import java.io.IOException;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Instrumentation;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import isyoon.com.devscott.karaengine.Disp;
import isyoon.com.devscott.karaengine.Global;
import isyoon.com.devscott.karaengine.KEnvr;
import isyoon.com.devscott.karaengine.KMenu;
import isyoon.com.devscott.karaengine.KMidiFS;
import isyoon.com.devscott.karaengine.KMsg;
import isyoon.com.devscott.karaengine.KOSD;
import isyoon.com.devscott.karaengine.KPlay;
import isyoon.com.devscott.karaengine.KScore;
import isyoon.com.devscott.karaengine.Player;
import isyoon.com.devscott.karaengine.Setup;
import kr.kumyoung.gtvkaraoke.R;

import com.joyul.streaming.StreamingMp3Player;
import com.kumyoung.common.Constants;
import com.kumyoung.main.app.LiveUpdateActivity;
import com.kumyoung.stbcomm.ISUHandler;
import com.kumyoung.stbcomm.SIMClientHandlerLGU;
import com.kumyoung.stbcomm.SetopHandler;
import com.kumyoung.stbui.AdPopupView;
import com.kumyoung.stbui.AdView;
import com.kumyoung.stbui.BackgroundView;
import com.kumyoung.stbui.BottomMenu;
import com.kumyoung.stbui.BuyTicketView;
import com.kumyoung.stbui.GuideListView;
import com.kumyoung.stbui.LyricView;
import com.kumyoung.stbui.MainMenuView;
import com.kumyoung.stbui.MyVideoView;
import com.kumyoung.stbui.OSDView;
import com.kumyoung.stbui.ScoreView;
import com.kumyoung.stbui.SongListView;
import com.kumyoung.stbui.SongSearchHeaderView;
import com.kumyoung.stbui.SoundManager;
import com.kumyoung.stbui.TitleBarView;
import com.kumyoung.stbui.TitleView;
import com.kumyoung.stbui.ViewManager;
import com.lguplus.iptv3.base.settingmanager.GetILgIptvShmRemote;

public class ATVKaraokeActivity extends LiveUpdateActivity implements OnErrorListener
// OnBufferingUpdateListener,
/*
 * ,OnCompletionListener
 * ,MediaPlayer.OnPreparedListener
 * ,SurfaceHolder.Callback
 */
{
	// bgkim APP KILL 이벤트 받으면 실제로 죽이는 리시버
	private static final String ACTION_DO_SUSPEND = "com.marvell.AppKiller.DO_SUSPEND";

	public static boolean key_accept = false;
	public boolean isBreak = false;

	public static boolean tgMic = true;
	ProgressDialog progress_dialog = null;
	private static final String TAG = ATVKaraokeActivity.class.getSimpleName();
	private static final int CUSTOM_DIALOG = 1;
	private static final int DEFAULT_DIALOG = 2;
	// private static final int KEY_SONGSEARCH = 132;
	// private MediaPlayer dtvPlayer = null;
	// private MediaPlayer mp = null;
	public MyVideoView mVideoView = null;
	private int key_sleep = 0;
	public PopupWindow popup = null;
	public boolean m_bShowADPopup = false;
	public static EditText edit = null;

	// first
	// private SurfaceView mPreview;
	// private SurfaceHolder holder;
	// sec
	// private android.widget.VideoView mVideoView = null;
	private static GetILgIptvShmRemote remoteLG = null;
	private FrameLayout layoutWork = null;
	private LinearLayout.LayoutParams layout_params = null;

	private Handler mBackHandler;
	private boolean onPressBackKey = false;
	private static int nInternetCheckCount = 0;
	public static XmlFinder xf = null;

	private final BroadcastReceiver receiverAppKill = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			ATVKaraokeActivity.this.finish();
		}
	};

	public static boolean isReachable(Context context) {

		if (Global.Inst().isTestBed)
			return true;

		UrlStatusCheck uchk = new UrlStatusCheck(DataHandler.pingServer, 10000);
		int responseCode = uchk.getResponseCode();

		/*
		 * if ( responseCode == -1)
		 * {
		 * uchk = new UrlStatusCheck( DataHandler.serverKYURL+"/GetSongInfo_lgugtv.asp" , 2000 );
		 * responseCode = uchk.getResponseCode();
		 * }
		 * if ( responseCode == -1)
		 * {
		 * uchk = new UrlStatusCheck( DataHandler.serverKYURL+"/GetSongInfo_lgugtv.asp" , 2000 );
		 * responseCode = uchk.getResponseCode();
		 * }
		 */
		uchk = null;

		if (responseCode == 200)
			nInternetCheckCount = 0;

		Log.d("KE", "Internet check   (" + responseCode);

		return (responseCode == 200);
	}

	/*
	 * private void playVideo()
	 * {
	 * // String SD_PATH = "http://kumyoung.hscdn.com/M12088RA040110KY1708.mp4";
	 * String SD_PATH = "http://kumyoung.hscdn.com/GTV_Video/47666_BAP_Warrior_IPTVtest5Mcbr.mp4";
	 * try {
	 * final String path = SD_PATH;
	 * Log.v(TAG, "path: " + path);
	 * // If the path has not changed, just start the media player
	 * if (mp != null) {
	 * mp.start();
	 * mp.setVolume(0.0f, 0.0f); // left, right 1/5
	 * return;
	 * }
	 * //current = path;
	 * // Create a new media player and set the listeners
	 * mp = new MediaPlayer();
	 * // Set the surface for the video output
	 * 
	 * mp.setDisplay( mPreview.getHolder() );
	 * 
	 * mp.setVolume(0.0f, 0.0f); // left, right 1/5
	 * // Set the data source in another thread
	 * // which actually downloads the mp3 or videos
	 * // to a temporary location
	 * mp.setDataSource( this , Uri.parse(path));
	 * Runnable r = new Runnable() {
	 * public void run() {
	 * //try {
	 * //mp.setDataSource(path);
	 * // } catch (IOException e) {
	 * // Log.e(TAG, e.getMessage(), e);
	 * // }
	 * try {
	 * mp.prepare();
	 * mp.setVolume(0.0f, 0.0f); // left, right 1/5
	 * } catch(IllegalStateException e) {
	 * e.printStackTrace();
	 * } catch(IOException e) {
	 * e.printStackTrace();
	 * }
	 * Log.v(TAG, "Duration:  ===>" + mp.getDuration());
	 * mp.start();
	 * }
	 * };
	 * new Thread(r).start();
	 * 
	 * } catch (Exception e) {
	 * Log.e(TAG, "error: " + e.getMessage(), e);
	 * if (mp != null) {
	 * mp.stop();
	 * mp.release();
	 * }
	 * }
	 * }
	 */
	public void doInternet()
	{
		nInternetCheckCount++;
		{
			String strMsg = "인터넷 연결이 올바르지 않습니다.\n" +
					"수신기 연결상태를 확인하시기 바랍니다.\n\n" +
					DataHandler.telephone;

			CustomDialog.Builder customBuilder = new CustomDialog.Builder(ATVKaraokeActivity.this);
			customBuilder.setTitle("알  림")
					.setMessage(strMsg)
					.setPositiveButton("확 인", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							if (nInternetCheckCount > 3)
								ATVKaraokeActivity.this.finish();
							else
							{
								if (DataHandler.isReachable == false)
									Global.Inst().app.doMenu(2);
							}
						}
					});

			CustomDialog dialog = customBuilder.create();
			dialog.show();
		}
	}

	public void doPopupAD()
	{
		AdPopupView popupview = new AdPopupView(ATVKaraokeActivity.this, DataHandler.ADPOPUP_image);
		popup = new PopupWindow(popupview, Constants.widthReal, Constants.heightReal, true);       // full 화면을 채워 마우스 포인터를 못가게 한다.
		// popup.showAsDropDown( ViewManager.Inst().lpOsdView, 0, 0);
		popup.showAtLocation(ViewManager.Inst().lpOsdView, Gravity.CENTER, 0, 0);
		// popup.setOutsideTouchable(false); //윈도우 밖에 터치를 받아들일때 사용한다.
		popupview.setSpot();
		popupview.setParent(popup);       // 종료하기위하여
	}

	public void doSelectMessage(String strMsg)
	{

		CustomDialog.Builder customBuilder = new CustomDialog.Builder(ATVKaraokeActivity.this);
		customBuilder.setTitle("알  림").setMessage(strMsg)

				.setPositiveButton("  확인  ",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int whichButton) {
								// OK 버튼 클릭시 , 여기서 선택한 값을 메인 Activity 로 넘기면 된다.
								{
									try {
										Intent i = new Intent(Intent.ACTION_VIEW);
										i.setData(Uri.parse(DataHandler.serverUpdateURL));
										i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
										startActivity(i);

										ATVKaraokeActivity.this.finish();

									} catch (ActivityNotFoundException e) {
										// Toast.makeText(this, "Could not open Android Market.", Toast.LENGTH_SHORT).show();
									}
									dialog.dismiss();
								}
							}
						});

		CustomDialog dialog = customBuilder.create();
		dialog.show();

	}

	public void doMessage(String strMsg, final boolean needkill) {

		/*
		 * AlertDialog ad = new AlertDialog.Builder(ATVKaraokeActivity.this).create();
		 * ad.setCancelable(false); // This blocks the 'BACK' button
		 * ad.setMessage(strMsg);
		 * ad.setButton( AlertDialog.BUTTON_POSITIVE, "확 인", new DialogInterface.OnClickListener() {
		 * 
		 * @Override
		 * public void onClick(DialogInterface dialog, int which) {
		 * dialog.dismiss();
		 * if ( needkill )
		 * {
		 * ATVKaraokeActivity.this.finish();
		 * }
		 * }
		 * });
		 * ad.show();
		 */

		CustomDialog.Builder customBuilder = new CustomDialog.Builder(ATVKaraokeActivity.this);
		customBuilder.setTitle("알  림")
				.setMessage(strMsg)
				/*
				 * .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				 * public void onClick(DialogInterface dialog, int which) {
				 * KYStbKaraokeActivity.this.dismissDialog(CUSTOM_DIALOG);
				 * }
				 * })
				 */
				.setPositiveButton("확 인", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						if (needkill)
						{
							ATVKaraokeActivity.this.finish();
						}
					}
				});

		CustomDialog dialog = customBuilder.create();
		dialog.show();

	}

	// void doAlert(int return_code, String return_message, String svc_message) {
	//
	// String strReturnCode = "XXXXX";
	//
	// switch (return_code)
	// {
	// case 20004:
	// strReturnCode = String.format("서비스 오류 (R-71009)");
	// return_message = String.format("서비스 이용 중 오류가 발생하였습니다.\n재시도 후 문제가 반복되면 셋탑의 전원을\n뺏다가 꽂아 주세요. 문제가 해결되지 않으면");
	// svc_message = String.format("고객센터(국번없이 101번)로 문의 주시기 바립니다.");
	// break;
	// case 40000:
	// strReturnCode = String.format("인터넷 오류 (R-74014)");
	// break;
	// case 40001:
	// strReturnCode = String.format("서비스 오류 (R-74015)");
	// break;
	// case 40002:
	// strReturnCode = String.format("서비스 오류 (R-74016)");
	// break;
	// case 50000:
	// strReturnCode = String.format("서비스 오류 (R-74017)");
	// break;
	// case 50001:
	// strReturnCode = String.format("서비스 미가입");
	// break;
	// case 50002:
	// strReturnCode = String.format("서비스 가입처리 중");
	// break;
	// case 50003:
	// strReturnCode = String.format("가입자 오류 (R-72018)");
	// break;
	// case 50004:
	// strReturnCode = String.format("서비스 오류 (R-72019)");
	// break;
	// case 50005:
	// strReturnCode = String.format("가입자 오류 (R-72030)");
	// break;
	// case 50006:
	// strReturnCode = String.format("가입자 오류 (R-72031)");
	// break;
	// case 60000:
	// strReturnCode = String.format("서비스 오류 (R-72032)");
	// break;
	// case 99999:
	// strReturnCode = String.format("서비스 오류 (R-72021)");
	// break;
	// case 99998:
	// strReturnCode = String.format("업데이트");
	// break;
	//
	// default:
	// strReturnCode = String.format("서비스 오류(%05d)", return_code);
	// break;
	// }
	//
	// /*
	// * AlertDialog ad = new AlertDialog.Builder(ATVKaraokeActivity.this).create();
	// * ad.setCancelable(false); // This blocks the 'BACK' button
	// * ad.setMessage("서비스를 이용하실 수 없습니다.\n\n"
	// * + "오류코드 : " + strReturnCode + "\n"
	// * + "오류메시지 : " + return_message + "\n"
	// * + "\n"
	// * + SIMClientHandlerLGU.svc_kumyoung
	// * );
	// *
	// * ad.setButton( AlertDialog.BUTTON_POSITIVE, "확 인", new DialogInterface.OnClickListener() {
	// *
	// * @Override
	// * public void onClick(DialogInterface dialog, int which) {
	// * dialog.dismiss();
	// * ATVKaraokeActivity.this.finish();
	// * }
	// * });
	// * ad.show();
	// */
	// CustomDialog.Builder customBuilder = new CustomDialog.Builder(ATVKaraokeActivity.this);
	// customBuilder.setTitle(strReturnCode)
	// .setMessage(return_message + "\n\n" + svc_message)
	// .setPositiveButton("확 인", new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int which) {
	// dialog.dismiss();
	// ATVKaraokeActivity.this.finish();
	// }
	// });
	//
	// CustomDialog dialog = customBuilder.create();
	// dialog.show();
	//
	// }

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// if (data.appInfo.targetSdkVersion > 9)
		// StrictMode.enableDeathOnNetwork();
		/*
		 * SystemSettingManager.getInstance().getNetworkControl().getMac();
		 * UserPreference ui;
		 * getProductList();
		 * showMACAddress();
		 */
		Global.Inst().as = this.getAssets();
		// font load
		// FontDrv.getInstance().font_load( this.getResources() );
		setContentView(R.layout.main);		// custmize layout
		KEnvr.envr_set(KEnvr.ENVR_IDLE);

		DataHandler.getInstance();

		DataHandler.videoPath = new String("http://kumyoung.hscdn.com/GTV_Video/47666_BAP_Warrior_IPTVtest5Mcbr.mp4");

		Log.d("TAG", "KARAOKE start() -----------------------------------------");
		/* Background */
		ViewManager.Inst().lpBackgroundView = (BackgroundView) findViewById(R.id.backgroundView);

		/* Bottom Menu */
		ViewManager.Inst().lpBottomMenu = (BottomMenu) findViewById(R.id.bottomView1);
		ViewManager.Inst().lpBottomMenu.setFocusable(false);

		/* OSD 영역 */
		ViewManager.Inst().lpOsdView = (OSDView) findViewById(R.id.osdView);
		ViewManager.Inst().lpOsdView.setSpot();
		ViewManager.Inst().lpOsdView.setFocusable(true);

		/* 작업영역 */
		layoutWork = (FrameLayout) findViewById(R.id.frameMenuViewLayout);
		if (layoutWork == null)
		{
			Log.e(TAG, "not found layoutwork ");
		}

		xf = new XmlFinder();
		mBackHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0) {
					onPressBackKey = false;
				}
			}
		};

		/* mHandler hear */

		/*
		 * // network sync
		 * while( DataHandler.have_userinfo == false) {
		 * try {
		 * Thread.sleep(50);
		 * } catch (InterruptedException e) {
		 * e.printStackTrace();
		 * }
		 * }
		 */
		/**/
		layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		calcMatrics();
		addMainMenu();

		/**/
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		mVideoView = (MyVideoView) this.findViewById(R.id.VideoView);

		/*
		 * // dataBase
		 * if ( false == Database.Inst().connect() )
		 * Toast.makeText(this,"Can't open database connection.",Toast.LENGTH_LONG).show();
		 */

		//
		// sound effect initial
		SoundManager.getInstance().initSounds(this);
		SoundManager.getInstance().addSound(1, R.raw.key);
		SoundManager.getInstance().addSound(2, R.raw.select);

		// --------------------
		//
		Global.Inst().app = this;
		/*
		 * // mp3 streaming play
		 * TextView tv = (TextView) findViewById(R.id.textViewMP3);
		 * ProgressBar pg = (ProgressBar) findViewById(R.id.progressBar1);
		 */
		Global.Inst().streamMp3player = new StreamingMp3Player(this, null, null);
		Global.Inst().streamMp3player.initialize();

		Global.Inst().midifs = new KMidiFS();
		Global.Inst().midifs.mfs_init();

		Global.Inst().player = new Player();
		Global.Inst().setup = new Setup();
		Global.Inst().disp = new Disp();

		// ----------------------------
		KScore.Inst().score_task_init();
		KOSD.Inst().osd_task_init();
		KPlay.Inst().play_task_init();
		Global.Inst().disp.disp_task_init();

		/*
		 * Global.Inst().midifs.mfs_init();
		 * Global.Inst().midifs.mfs_exist( sno );
		 * if ( ! Global.Inst().midifs.mfs_header(sno, Global.Inst().fp.song) )
		 * {
		 * }
		 * Global.Inst().disp.make_title();
		 * Global.Inst().cur_fh = Global.Inst().midifs.mfs_open(sno, true );
		 * if ( Global.Inst().cur_fh == 0 )
		 * {
		 * Log.e("player", "opend "+ sno );
		 * return;
		 * }
		 * Log.i("player", "opend "+ sno );
		 * Global.Inst().player.infor_song();
		 * Global.Inst().player.start_song();
		 * Global.Inst().midifs.mfs_close( Global.Inst().cur_fh);
		 */

		//
		// karaoke view
		//
		// Global.Inst().disp.disp_task_init();
		Global.Inst().player.play_notask_init();
		ViewManager.Inst().lpMenuView.setSpot();

		/** key monitor */
		Runnable r = new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
						key_sleep++;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (key_sleep > 5) {
						key_sleep = 0;
						if (KOSD.Inst().is(KOSD.eOSD_BOOK_EDIT))
							KPlay.Inst().play_send(KMsg.MSG_KBD_STOP);
					}
				}
			}
		};
		new Thread(r).start();

		Runnable rReachable = new Runnable() {
			@Override
			public void run() {
				while (true) {
					DataHandler.isReachable = isReachable(getApplicationContext());
					// Log.d("TAG", "Recah  " + DataHandler.isReachable );
					try {
						Thread.sleep(11000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		new Thread(rReachable).start();

		IntentFilter filterAppKill = new IntentFilter();
		filterAppKill.addAction(ACTION_DO_SUSPEND);
		registerReceiver(receiverAppKill, filterAppKill);
	}

	void onBackQuit()
	{
		if (onPressBackKey) {

			ATVKaraokeActivity.this.finish();
			/*
			 * // app 종료유도
			 * ActivityManager am = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
			 * am.killBackgroundProcesses(getPackageName());
			 * System.exit(0);
			 */

		} else {
			onPressBackKey = true;
			Toast.makeText(this, "한 번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG).show();

			mBackHandler.sendEmptyMessageDelayed(0, 5000);  // 3000mS < value
			// doMessage( " 한 번 더 누르시면 종료됩니다.\n  더 이상 진행 할수 없습니다. \n", true);
		}
	}

	/**
	 * sample을 이용k하여 틱 계산을 한다.
	 * 개선 사항. 버퍼 카운터가 왔다 갔다 할수 있고.. 버퍼가 커지면 오차가 생기므로
	 * 나간 버퍼 기준으로 시간계산을 해야한다.
	 */

	/*
	 * void mainGameLoop ()
	 * {
	 * if ( (current_sample_bak != g_output_sample) || bFirst)
	 * {
	 * bFirst = NO;
	 * // 곡 맨 처음엔 여기 들어와야 한다.
	 * sample = current_sample_bak;
	 * 
	 * // CFTimeInterval time;
	 * spand_time = CFAbsoluteTimeGetCurrent();
	 * current_sample_bak = g_output_sample;
	 * sample_add = 0;
	 * }
	 * else
	 * {
	 * float cal = CFAbsoluteTimeGetCurrent() - spand_time; // 세밀.
	 * sample_add = ( cal * 44100); // 세밀 보상해준다. 시간에 따라 샘플 진행수를
	 * }
	 * 
	 * float delta;
	 * float time = (float)( (sample+sample_add) / (midi_time_ratio * 44100));
	 * delta = (time - lastTime);
	 * // if ( delta < 0 ) { assert(true); }
	 * // if ( !g_pGroup->g_std_tick)
	 * // return;
	 * 
	 * delta += lastMod;
	 * float ms = (float)g_pGroup->g_std_tick/(float)1000000L;
	 * int ntick = delta/ ms;
	 * lastMod = delta - (ntick*ms);
	 * if ( g_pGroup->in_play //&& !g_pGroup->is_pause )
	 * {
	 * g_midi_tick_count += ntick;
	 * 
	 * timeout += ntick;
	 * 
	 * if ( g_midi_tick_count < 0)
	 * {
	 * // NSLog(@"sdf");
	 * }
	 * 
	 * //NSLog(@"g_midi_tick %d ntick %d  sampe %d\n",g_midi_tick_count, ntick, (sample+sample_add));
	 * }
	 * // 가사테스크 구동
	 * if (g_midi_tick_count > 0 )
	 * {
	 * int exit_code = m_Play.interval_loop();
	 * [self renderScene]; // 씬을 랜더링 한다.
	 * }
	 * lastTime = time;
	 * }
	 */

	/**
     * 
     */

	// 포커스가 화면에 보이는것에 없어 이곳으로 키가 들어온다.
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (key_accept == false)
			return true;

		if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_TV) {
			SetopHandler.getInstance().setKaraokeMode(false);
		}

		// ViewManager.Inst().lpMenuView.setSpot();
		if (KMenu.IsOSD(KMenu.MENU_IDLE) && KEnvr.is(KEnvr.ENVR_IDLE)) {
			if (ViewManager.Inst().lpMenuView != null &&
					(keyCode == KeyEvent.KEYCODE_DPAD_CENTER ||
							keyCode == KeyEvent.KEYCODE_DPAD_LEFT ||
							keyCode == KeyEvent.KEYCODE_DPAD_RIGHT ||
							keyCode == KeyEvent.KEYCODE_DPAD_UP ||
					keyCode == KeyEvent.KEYCODE_DPAD_DOWN))
			{
				Log.d(TAG, "MainMenu SetSpot");
				ViewManager.Inst().lpMenuView.onKeyDown(keyCode, event);
				return true;
			}
		}

		key_sleep = 0;
		SoundManager.getInstance().playSound(2);
		Log.w(TAG, "onKeyDown(" + keyCode + "," + event + ")");

		if (keyCode == 170)   // Exit Button;
		{
			ATVKaraokeActivity.this.finish();
		}
		if (keyCode == 132)		// KeyEvent.KEYCODE_SEARCH
		{
			ATVKaraokeActivity.this.showDialog(1);
		}

		if (keyCode == KeyEvent.KEYCODE_MENU)		// menu key
		{
			/*
			 * callback_buyticket();
			 * // 1. SIMClientHandler.user_base_info_sendRequestData
			 * // 2. SIMClientHandler.user_regist_sendRequestData();
			 * // 2. static public void subscription_product_sendRequestData() 월정액 가입여부 확인
			 * // 3. get_user_info
			 * // 4. 조회 - 다시 알려줌 . ;
			 * //5.가입 SIMClientHandler.subscription_product_sendRequestData();
			 * //5.가입 SIMClientHandler.subscription_register_sendRequestData();
			 * // addMainMenu();
			 * Log.d("sim", "4.1.4. 서비스(일/월정액) 사용자 인증 요청과 반환 값의 예");
			 * SIMClientHandler.user_regist_sendRequestData();
			 * Log.d("sim", "4.2.3.1. 가입자 기본 정보 ");
			 * SIMClientHandler.user_base_info_sendRequestData();
			 * Log.d("sim", "4.2.3.2. 가입자 기본정보, 주민등록번호, 이메일주소");
			 * SIMClientHandler.user_advance_info_sendRequestData();
			 * Log.d("sim", "4.3.4 종량상품 과금요청/정산정보  요청과 반환 값의 ");
			 * SIMClientHandler.pay_product_sendRequestData();
			 * Log.d("sim", "4.4.4 가입형 상품 가입  요청과 반환 값의 예");
			 * SIMClientHandler.subscription_product_sendRequestData();
			 * Log.d("sim", "4.5.4 가입형상품 가입/해지 요청과 반환 값의" );
			 * SIMClientHandler.subscription_register_sendRequestData();
			 * Log.d("sim", "4.6.4 가입자 주문내역 정보 요청과 반환 값의 예");
			 * SIMClientHandler.orders_info_sendRequestData();
			 * channelDTV("191"); //mnet hd
			 */
			return true;
		}
		/*
		 * if ( keyCode == 89) // red key // 리모콘으로 마이크 에코 끄거나 켜는부분 삭제)
		 * {
		 * Log.d("service", "setKaraokeMode : " + SetopHandler.getInstance().setKaraokeMode(tgMic) );
		 * Toast.makeText(getApplicationContext(), "마이크 에코 : " + (tgMic? "켜짐" :"꺼짐") , Toast.LENGTH_SHORT).show();
		 * tgMic = !tgMic;
		 * }
		 */
		if (keyCode == 184)	// green key
		{
			if (Global.Inst().in_play == false) 		// ppp
				KPlay.Inst().play_send(KMsg.MSG_KBD_CANCEL);
			else
				KPlay.Inst().play_send(KMsg.MSG_KBD_STOP);
		}

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			// onBackQuit();
			// back키를 Stop 키로 바꿔서 루프를 타게 한다. 이후 message에 의해 제어루틴을 실행하게 수정할것
			if (Global.Inst().in_play == true) {    // ppp
				if (KOSD.Inst().is(KOSD.eOSD_BOOK_EDIT))
					KPlay.Inst().play_send(KMsg.MSG_KBD_STOP);
			}
			else
			{
				if (KOSD.Inst().is(KOSD.eOSD_BOOK_EDIT))
					KPlay.Inst().play_send(KMsg.MSG_KBD_STOP);
				else
				{
					layoutWork.removeAllViews();
					addMainMenu();

					// KPlay.Inst().play_send(KMsg.MSG_KBD_CANCEL);
				}
			}

			// 종료를 막음
			Log.d(TAG, "cancel EXIT");
			return true;
		}

		// iv.onKeyDown( keyCode, event );
		// KOSD.Inst().osd_send( keyCode);
		KPlay.Inst().play_send(keyCode);
		if (KMenu.IsOSD(KMenu.MENU_IDLE))
		{
			if (ViewManager.Inst().lpMenuView != null) {
				// ViewManager.Inst().lpMenuView.onKeyDown( keyCode, event);
			}
			if (ViewManager.Inst().lpSongSearchListView != null) {
				// ViewManager.Inst().lpSongSearchView.onKeyDown(keyCode, event);
			}
			if (ViewManager.Inst().lpChallengeFiftyView != null) {
				// ViewManager.Inst().lpChallengeFiftyView.onKeyDown(keyCode, event);
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * Build the desired Dialog
	 * CUSTOM or DEFAULT
	 */
	@Override
	public Dialog onCreateDialog(int dialogId) {
		Dialog dialog = null;
		switch (dialogId) {

		case CUSTOM_DIALOG:
			SelectGenreDialog.Builder customBuilder = new SelectGenreDialog.Builder(ATVKaraokeActivity.this);
			customBuilder.setTitle("Custom title")
					.setMessage("Custom body")
					.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							ATVKaraokeActivity.this.dismissDialog(CUSTOM_DIALOG);
						}
					})
					.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});

			dialog = customBuilder.create();
			break;

		/*
		 * case CUSTOM_DIALOG :
		 * CustomDialog.Builder customBuilder = new CustomDialog.Builder(KYStbKaraokeActivity.this);
		 * customBuilder.setTitle("Custom title")
		 * .setMessage("Custom body")
		 * .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		 * public void onClick(DialogInterface dialog, int which) {
		 * KYStbKaraokeActivity.this.dismissDialog(CUSTOM_DIALOG);
		 * }
		 * })
		 * .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
		 * public void onClick(DialogInterface dialog, int which) {
		 * dialog.dismiss();
		 * }
		 * });
		 * dialog = customBuilder.create();
		 * break;
		 */
		case DEFAULT_DIALOG:
			AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ATVKaraokeActivity.this);
			alertBuilder.setTitle("Default title")
					.setMessage("Default body")
					.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					})
					.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							ATVKaraokeActivity.this
									.dismissDialog(DEFAULT_DIALOG);
						}
					});
			dialog = alertBuilder.create();
			break;

		case 3:
			return ViewManager.Inst().lpSongSearchListView.reservedDialog();
		}
		return dialog;
	}

	/**
	 * 
	 */
	void registerKeyPad()
	{
		/*
		 * findViewById(R.id.button5).setOnClickListener(new OnClickListener() {
		 * public void onClick(View v) {
		 * // KYStbKaraokeActivity.this.showDialog(CUSTOM_DIALOG);
		 * }
		 * });
		 */
		// SongSearch
		findViewById(R.id.buttonSearch).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						new Instrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_SEARCH);
					}
				}).start();
			}
		});
		findViewById(R.id.buttonStart).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						new Instrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_ENTER);
					}
				}).start();
			}
		});

		findViewById(R.id.buttonStop).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						new Instrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_DEL);
					}
				}).start();
			}
		});

		// ten number key.
		findViewById(R.id.button10).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				new Thread(new Runnable() {
					@Override
					public void run() {
						new Instrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_0);
					}
				}).start();
			}
		});
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				new Thread(new Runnable() {
					@Override
					public void run() {
						new Instrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_1);
					}
				}).start();
			}
		});
		findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				new Thread(new Runnable() {
					@Override
					public void run() {
						new Instrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_2);
					}
				}).start();
			}
		});
		findViewById(R.id.button3).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				new Thread(new Runnable() {
					@Override
					public void run() {
						new Instrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_3);
					}
				}).start();
			}
		});
		findViewById(R.id.button4).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				new Thread(new Runnable() {
					@Override
					public void run() {
						new Instrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_4);
					}
				}).start();
			}
		});
		findViewById(R.id.button5).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				new Thread(new Runnable() {
					@Override
					public void run() {
						new Instrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_5);
					}
				}).start();
			}
		});
		findViewById(R.id.button6).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				new Thread(new Runnable() {
					@Override
					public void run() {
						new Instrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_6);
					}
				}).start();
			}
		});
		findViewById(R.id.button7).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				new Thread(new Runnable() {
					@Override
					public void run() {
						new Instrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_7);
					}
				}).start();
			}
		});
		findViewById(R.id.button8).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				new Thread(new Runnable() {
					@Override
					public void run() {
						new Instrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_8);
					}
				}).start();
			}
		});
		findViewById(R.id.button9).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				new Thread(new Runnable() {
					@Override
					public void run() {
						new Instrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_9);
					}
				}).start();
			}
		});
	}

	public void showSearchView()
	{
		ATVKaraokeActivity.this.showDialog(1);
		/*
		 * layoutWork.removeAllViews();
		 * //sv.setVisibility(View.VISIBLE);
		 * //layoutWork.addView( sv, layout_params );
		 * layoutWork.addView( frameSearch, p );
		 */
	}

	private void calcMatrics()
	{

		StringBuffer buf = null;
		WindowManager wm = null;
		Display display = null;
		DisplayMetrics metrics = null;

		wm = getWindowManager();
		display = wm.getDefaultDisplay();
		metrics = new DisplayMetrics();
		display.getMetrics(metrics);

		Global.Inst().scale = this.getResources().getDisplayMetrics().density;
		Global.Inst().density = this.getResources().getDisplayMetrics().densityDpi;// Dpi;

		buf = new StringBuffer();
		buf.append("Window display id: " + display.getDisplayId() + "\n");
		buf.append("Window orientation: " + display.getOrientation() + "\n");
		buf.append("Window width: " + display.getWidth() + "\n");
		buf.append("Window height: " + display.getHeight() + "\n");
		buf.append("Window pixel format: " + display.getPixelFormat() + "\n");
		buf.append("Window refresh rate: " + display.getRefreshRate() + "\n");
		buf.append("Window width pixels: " + metrics.widthPixels + "\n");
		buf.append("Window height pixels: " + metrics.heightPixels + "\n");
		// testMsg.setText(buf.toString());

		Constants.widthReal = metrics.widthPixels;
		Constants.heightReal = metrics.heightPixels;
		Log.d("ke", buf.toString());

	}

	/**
	 * 노래 재생 레이아웃
	 */
	private void addTitle()
	{

		LinearLayout frameTitle = (LinearLayout) View.inflate(this, R.layout.title_layout, null);			// layout xml load
		layoutWork.addView(frameTitle, layout_params);

		// frameTitle.addView( lpTitle, p );

		if (ViewManager.Inst()._lpTitleView == null)
			ViewManager.Inst()._lpTitleView = (TitleView) findViewById(R.id.lyricTitleView);
		// ViewManager.Inst().lpTitleView.SetText( "Clear", "" );
		// layoutWork.addView( ViewManager.Inst().lpTitleView, layout_params );

		ViewManager.Inst().lpLyric1View = (LyricView) findViewById(R.id.lyric1View);
		ViewManager.Inst().lpLyric1View.setText("");
		// ViewManager.Inst().lpLyric1View.dumpLyric();
		ViewManager.Inst().lpLyric1View.aligment(1);

		ViewManager.Inst().lpLyric2View = (LyricView) findViewById(R.id.lyric2View);
		ViewManager.Inst().lpLyric2View.setText("");
		// ViewManager.Inst().lpLyric2View.dumpLyric();
		ViewManager.Inst().lpLyric2View.aligment(1);

		// TitleView lpTitle = (TitleView)findViewById( R.id.titleView);
		// lpTitle.setText( "Title view", "" );
		// SongSearchView lpSearchView = ( SongSearchView ) findViewById( R.id.searchView );
		// menuView = (MenuView) findViewById( R.id.menuView);

		/**
		 * 
		 */

		/*
		 * BottomMenu lp = (BottomMenu) findViewById(R.id.bottomView1);
		 * if ( lp != null)
		 * {
		 * lp.setSpot();
		 * }
		 */

	}

	public void addScore()
	{
		LinearLayout frameScore = (LinearLayout) View.inflate(this, R.layout.score_layout, null);			// layout xml load
		layoutWork.addView(frameScore, layout_params);

		if (ViewManager.Inst().lpScoreView == null)
			ViewManager.Inst().lpScoreView = (ScoreView) findViewById(R.id.scoreView);
	}

	public void removeScore()
	{

		LinearLayout frameScore = (LinearLayout) View.inflate(this, R.layout.score_layout, null);			// layout xml load
		// / LinearLayout frameMainMenu = (LinearLayout) View.inflate(this, R.layout.mainmenu, null); //layout xml load
		layoutWork.removeView(frameScore);
		ViewManager.Inst().lpScoreView = null;
	}

	/*
	 * public void addChallenge()
	 * {
	 * LinearLayout frame = (LinearLayout) View.inflate(this, R.layout.challengefifty, null); //layout xml load
	 * layoutWork.addView( frame, layout_params );
	 * 
	 * ViewManager.Inst().lpChallengeFiftyView = ( ChallengeFiftyView ) findViewById( R.id.challengeView1 );
	 * ViewManager.Inst().lpMenuView = null;
	 * 
	 * ViewManager.Inst().lpChallengeFiftyView.requestFocus();
	 * ViewManager.Inst().lpChallengeFiftyView.setFocusable(true);
	 * //ViewManager.Inst().lpChallengeFiftyView.setFocusableInTouchMode(true);
	 * }
	 */

	/**
	 * 메인메뉴
	 */
	private void addMainMenu()
	{

		Log.d("ke", "addMainMenu in");
		/**
    	 * 
    	 */
		// ppp edit= (EditText)findViewById(R.id.edit);
		// ppp edit.setEnabled(false);
		// edit.setVisibility(INVISIBLE);

		/**
   		 * 
   		 */
		LinearLayout frameMainMenu = (LinearLayout) View.inflate(this, R.layout.mainmenu, null);		// layout xml load

		Log.d("ke", "addMainMenu in 2");
		// p.gravity = Gravity.CENTER| Gravity.BOTTOM;
		layoutWork.addView(frameMainMenu, layout_params);
		Log.d("ke", "addMainMenu in 3");

		ViewManager.Inst().lpMenuView = (MainMenuView) findViewById(R.id.menuView);
		ViewManager.Inst().lpAdView = (AdView) findViewById(R.id.adView);
		ViewManager.Inst().lpMenuView.setSpot();
		Log.d("ke", "aadMainMenu()");
	}

	// 최신곡 HOT100
	private void addHot100()
	{

		// LinearLayout frame = (LinearLayout) View.inflate(this, R.layout.challengefifty, null); //layout xml load
		// layoutWork.addView( frame, layout_params );

		LinearLayout frame = (LinearLayout) View.inflate(this, R.layout.list_newsong_hot100, null);		// layout xml load
		layoutWork.addView(frame, layout_params);

		SongListView lp = (SongListView) findViewById(R.id.songsearchView1);

		lp.setTitleBarView((TitleBarView) findViewById(R.id.titleBarView1));
		lp.setSpot();

		// kkk layoutWork.requestFocus();
		// layoutWork.setFocusable(true);
		// ViewManager.Inst().lpSongSearchHeaderView = (SongSearchHeaderView) findViewById(R.id.songsearchheaderView1 );
		// ViewManager.Inst().lpSongSearchView = ( SongListView ) findViewById( R.id.songsearchView1 );
		/*
		 * ViewManager.Inst().lpMenuView = null;
		 * ViewManager.Inst().lpSongSearchHeaderView.requestFocus();
		 * ViewManager.Inst().lpSongSearchHeaderView.setFocusableInTouchMode(true);
		 */
	}

	// 인기곡 TOP100
	private void addTop100()
	{

		LinearLayout frame = (LinearLayout) View.inflate(this, R.layout.list_favsong_top100, null);		// layout xml load
		layoutWork.addView(frame, layout_params);

		SongListView lv = (SongListView) findViewById(R.id.songsearchView1);
		// lv.setMode( 1 );

		lv.setTitleBarView((TitleBarView) findViewById(R.id.titleBarView1));
		lv.setSpot();
	}

	private void addGenreList(int mode)
	{

		// R.layout.list_favsong_top100

		int id = 0;
		switch (mode)
		{

		case KMsg.MSG_KBD_GENRE_TROT_SONGLIST:
			id = R.layout.list_genre_trot;
			break;
		case KMsg.MSG_KBD_GENRE_CHILD_SONGLIST:
			id = R.layout.list_genre_child;
			break;
		case KMsg.MSG_KBD_GENRE_ANIM_SONGLIST:
			id = R.layout.list_genre_anim;
			break;
		case KMsg.MSG_KBD_GENRE_POP_SONGLIST:
			id = R.layout.list_genre_pop;
			break;
		case KMsg.MSG_KBD_GENRE_BALLAD_SONGLIST:
			id = R.layout.list_genre_ballad;
			break;
		case KMsg.MSG_KBD_GENRE_DANCE_SONGLIST:
			id = R.layout.list_genre_dance;
			break;
		case KMsg.MSG_KBD_GENRE_RNB_SONGLIST:
			id = R.layout.list_genre_rnb;
			break;
		case KMsg.MSG_KBD_GENRE_ROCK_SONGLIST:
			id = R.layout.list_genre_rock;
			break;
		case KMsg.MSG_KBD_GENRE_7080_SONGLIST:
			id = R.layout.list_genre_7080;
			break;
		case KMsg.MSG_KBD_GENRE_MEDLEY_SONGLIST:
			id = R.layout.list_genre_medley;
			break;

		case KMsg.MSG_KBD_GENRE_THEME1_SONGLIST:
			id = R.layout.list_genre_theme1;
			break;
		case KMsg.MSG_KBD_GENRE_THEME2_SONGLIST:
			id = R.layout.list_genre_theme2;
			break;
		case KMsg.MSG_KBD_GENRE_FREESONG_SONGLIST:
			id = R.layout.list_genre_freesong;
			break;

		default:
			id = R.layout.list_favsong_top100;
		}

		LinearLayout frame = (LinearLayout) View.inflate(this, id, null);		// layout xml load
		layoutWork.addView(frame, layout_params);

		SongListView lv = (SongListView) findViewById(R.id.songsearchView1);

		lv.setTitleBarView((TitleBarView) findViewById(R.id.titleBarView1));
		lv.setSpot();
	}

	// 최근 부른 곡
	private void addLatelySong()
	{

		LinearLayout frame = (LinearLayout) View.inflate(this, R.layout.list_lately, null);		// layout xml load
		layoutWork.addView(frame, layout_params);

		SongListView lp = (SongListView) findViewById(R.id.songsearchView1);

		lp.setTitleBarView((TitleBarView) findViewById(R.id.titleBarView1));
		lp.setSpot();

	}

	/**     */
	public void addSongSearch()
	{
		LinearLayout frameSongSearch = (LinearLayout) View.inflate(this, R.layout.songsearch, null);		// layout xml load
		layoutWork.addView(frameSongSearch, layout_params);

		// edit= (EditText)findViewById(R.id.edit);
		// edit.setRawInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_NORMAL );

		ViewManager.Inst().lpSongSearchHeaderView = (SongSearchHeaderView) findViewById(R.id.songsearchheaderView1);
		ViewManager.Inst().lpSongSearchListView = (SongListView) findViewById(R.id.songsearchView1);

		/**/
		SongListView lv = (SongListView) findViewById(R.id.songsearchView1);
		lv.setTitleBarView((TitleBarView) findViewById(R.id.titleBarView1));
		lv.setHeaderView(ViewManager.Inst().lpSongSearchHeaderView);	// header view setting

		// ** search view */
		ATVKaraokeActivity.edit = (EditText) findViewById(R.id.editSearch);
		ATVKaraokeActivity.edit.setImeOptions(EditorInfo.IME_ACTION_SEARCH | EditorInfo.IME_FLAG_NO_EXTRACT_UI);

		ATVKaraokeActivity.edit.setEnabled(true);
		ATVKaraokeActivity.edit.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event)
			{

				if (key_accept == false)
					return true;

				if (keyCode == event.KEYCODE_DPAD_UP)
				{
					Log.d("ke", "edit keycode_dpad_up");
					return true;		// 키를 라우틴하지 않음으로써 키를 막았다.
				}
				if (keyCode == event.KEYCODE_DPAD_DOWN)
				{
					Log.d("ke", "edit keycode_dpad_down");
					ViewManager.Inst().lpSongSearchListView.setFocusable(true); // 이동할수 있게 풀어준다. 풀어준다.kkjjjjjjjjj
					return false;
				}
				if (keyCode == event.KEYCODE_ENTER || keyCode == event.KEYCODE_DPAD_CENTER)
				{
					String strText = ATVKaraokeActivity.edit.getText().toString();
					ViewManager.Inst().lpSongSearchListView.doRequestData(ViewManager.Inst().curItem, strText);
					ViewManager.Inst().lpSongSearchListView.updateList();
					// ViewManager.Inst().imm.hideSoftInputFromWindow( v.getWindowToken(), 0 );
					// ViewManager.Inst().lpSongSearchHeaderView.setFocusable(false);
					// ViewManager.Inst().lpSongSearchView.setFocusable(true);
					// ViewManager.Inst().lpSongSearchView.setSpot();
					ViewManager.Inst().lpSongSearchListView.setFocusable(true); // 이동할수 있게 풀어준다. 풀어준다.
					return true;
				}
				return false;
			}
		});

		// frameSongSearch.addView( frameSongSearch, null);
		// frameTitle.addView( lpTitle, p );
		// layoutWork.addView( lpTitle, p );

		ViewManager.Inst().lpMenuView = null;
		ViewManager.Inst().lpSongSearchHeaderView.setSpot();
		ViewManager.Inst().lpSongSearchHeaderView.setFocusable(true);

		/*
		 * ATVKaraokeActivity.edit.setOnKeyListener(new OnKeyListener() {
		 * 
		 * @Override
		 * public boolean onKey(View v, int keyCode, KeyEvent event)
		 * {
		 * 
		 * // if(editText1.getText().length() == 5)
		 * // editText2.requestFocus();
		 * Log.d("ke", "key "+ keyCode + "imm" + ViewManager.Inst().imm.isAcceptingText() + "act"+ ViewManager.Inst().imm.isActive() );
		 * 
		 * 
		 * // ViewManager.Inst().imm.get
		 * if ( keyCode == 20 && ViewManager.Inst().imm.isActive() != true ) // 키보드가 떠 있는가?
		 * {
		 * 
		 * ViewManager.Inst().lpSongSearchView.setSpot();
		 * }
		 * 
		 * return true;
		 * }
		 * });
		 * ATVKaraokeActivity.edit.setOnFocusChangeListener(new OnFocusChangeListener()
		 * {
		 * 
		 * @Override
		 * public void onFocusChange(View v, boolean hasFocus)
		 * {
		 * String strValue = ATVKaraokeActivity.edit.getText().toString();
		 * Log.d("ke", "User set EditText value to " + strValue);
		 * 
		 * //if ( hasFocus == true )
		 * {
		 * // ViewManager.Inst().imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		 * // ViewManager.Inst().imm.hideSoftInputFromWindow( v.getWindowToken(), 0 );
		 * }
		 * 
		 * }
		 * });
		 */
		ATVKaraokeActivity.edit.setOnEditorActionListener(new TextView.OnEditorActionListener()
		{
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

				// if ( ViewManager.Inst().imm.isAcceptingText() == true ) // 키보드가 떠 있는가?
				if (actionId == EditorInfo.IME_ACTION_SEARCH)
				{
					ViewManager.Inst().imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

					{

						String strText = ATVKaraokeActivity.edit.getText().toString();
						ViewManager.Inst().lpSongSearchListView.doRequestData(ViewManager.Inst().curItem, strText);
						ViewManager.Inst().lpSongSearchListView.updateList();
						ViewManager.Inst().imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

						// ViewManager.Inst().lpSongSearchHeaderView.setFocusable(false);
						// ViewManager.Inst().lpSongSearchView.setFocusable(true);
						// ViewManager.Inst().lpSongSearchView.setSpot();

						ViewManager.Inst().lpSongSearchListView.setFocusable(true); // 이동할수 있게 풀어준다. 풀어준다.

					}
					return true;
				}
				// if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
				//
				// ViewManager.Inst().imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				// ViewManager.Inst().imm.hideSoftInputFromWindow( v.getWindowToken(), 0 );
				// return true;
				// }
				return false;
			}
		});
	}

	/** 애창곡 리스트 */
	private void addFavriteSongList()
	{
		LinearLayout frame = (LinearLayout) View.inflate(this, R.layout.favoritesonglist, null);		// layout xml load
		layoutWork.addView(frame, layout_params);

		// FavoriteSongListView lv = (FavoriteSongListView) findViewById( R.id.favoritesongView1);

		SongListView lp = (SongListView) findViewById(R.id.songsearchView1);
		lp.setTitleBarView((TitleBarView) findViewById(R.id.titleBarView1));
		lp.setSpot();

	}

	// 이용안내
	private void addHelp(int selItem)
	{
		LinearLayout frame = (LinearLayout) View.inflate(this, R.layout.list_guide, null);		// layout xml load
		layoutWork.addView(frame, layout_params);

		GuideListView lp = (GuideListView) findViewById(R.id.songsearchView1);

		lp.setSpot();

		if (selItem >= 0)
			lp.setNoticeItem(selItem);

		/*
		 * ViewManager.Inst().lpSongSearchHeaderView = (SongSearchHeaderView) findViewById(R.id.songsearchheaderView1 );
		 * ViewManager.Inst().lpSongSearchView = ( SongListView ) findViewById( R.id.songsearchView1 );
		 * 
		 * // frameSongSearch.addView( frameSongSearch, null);
		 * // frameTitle.addView( lpTitle, p );
		 * // layoutWork.addView( lpTitle, p );
		 * 
		 * ViewManager.Inst().lpMenuView = null;
		 * ViewManager.Inst().lpSongSearchHeaderView.setFocusable(true);
		 * ViewManager.Inst().lpSongSearchHeaderView.requestFocus();
		 * ViewManager.Inst().lpSongSearchHeaderView.setFocusableInTouchMode(true);
		 */

	}

	/**
	 * Context Switch
	 */
	public Handler msgHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what)
			{
			case 0:
				progress_dialog.dismiss();
				return;
			case 1:
				progress_dialog = ProgressDialog.show(ATVKaraokeActivity.this, "", "데이타 수신중입니다.");
				return;

			case 2:

				doInternet();
				Global.Inst().app.doMenu(KMsg.MSG_MAIN_MENU);
				return;

			case 10:
				onBackQuit();
				return;

			default:
				break;
			}

			{
				Log.d("ke", "removeAllViews");

				layoutWork.removeAllViews();
				ViewManager.Inst().lpScoreView = null;
			}

			// ChatItem item = (ChatItem)msg.obj;
			// adapter.add(item);
			// adapter.notifyDataSetChanged();
			switch (msg.what)
			{
			case KMsg.MSG_KBD_NEWSONG_SONGLIST:
				addHot100();
				break;	// 최신곡 HOT100
			case KMsg.MSG_KBD_TOP100_SONGLIST:
				addTop100();
				break;	// 인기곡 TOP100
			case KMsg.MSG_KBD_LATELY_SONGLIST:
				addLatelySong();
				break;	// 최근 부른 곡

			case KMsg.MSG_KBD_GENRE_SONGLIST:			// 장르선택
			case KMsg.MSG_KBD_THEME1_SONGLIST:			// 테마 1
			case KMsg.MSG_KBD_THEME2_SONGLIST:			// 테마 2
			case KMsg.MSG_KBD_SEARCH_SONGLIST:
				addSongSearch();
				break;

			case KMsg.MSG_KBD_FAVORITE_SONGLIST:
				addFavriteSongList();
				break;
			case KMsg.MSG_KBD_HELP:
				addHelp(-1);
				break;	// 이용안내

			case KMsg.MSG_KBD_GENRE_TROT_SONGLIST:
			case KMsg.MSG_KBD_GENRE_CHILD_SONGLIST:
			case KMsg.MSG_KBD_GENRE_ANIM_SONGLIST:
			case KMsg.MSG_KBD_GENRE_POP_SONGLIST:
			case KMsg.MSG_KBD_GENRE_BALLAD_SONGLIST:
			case KMsg.MSG_KBD_GENRE_DANCE_SONGLIST:
			case KMsg.MSG_KBD_GENRE_RNB_SONGLIST:
			case KMsg.MSG_KBD_GENRE_ROCK_SONGLIST:
			case KMsg.MSG_KBD_GENRE_7080_SONGLIST:
			case KMsg.MSG_KBD_GENRE_MEDLEY_SONGLIST:
			case KMsg.MSG_KBD_GENRE_THEME1_SONGLIST:		// theme1
			case KMsg.MSG_KBD_GENRE_THEME2_SONGLIST:
			case KMsg.MSG_KBD_GENRE_FREESONG_SONGLIST:			// freesong
				addGenreList(msg.what);
				break;

			case KMsg.MSG_KBD_NOTICE_HELPLIST:
				addHelp(0);
				break;

			case KMsg.MSG_MAIN_SCORE:
				addScore();
				break;

			case KMsg.MSG_MAIN_TITLE:
				addTitle();
				break;

			case KMsg.MSG_MAIN_MENU:
				addMainMenu();
				break;

			case KMsg.MSG_BUYTICKET_MENU:
				callback_buyticket();
				break;
			default:
				Log.d(TAG, "dsifjsd");
				break;

			case 8787:
				removeScore();
				break;
			/*
			 * case 2: addMainMenu(); break;
			 * case 3: addTitle(); break;
			 * case 5: addChallenge(); break;
			 */

			}
		}
	};

	public void doMenu(int what)
	{
		Message msg = new Message();
		msg.what = what;
		this.msgHandler.sendMessage(msg);
	}

	/*
	 * @Override
	 * public void surfaceDestroyed(SurfaceHolder surfaceholder) {
	 * Log.d(TAG, "surfaceDestroyed called");
	 * }
	 * 
	 * @Override
	 * public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3)
	 * {
	 *
	 * }
	 * 
	 * @Override
	 * public void surfaceCreated(SurfaceHolder arg0)
	 * {
	 *
	 * }
	 * 
	 * @Override
	 * public void onPrepared(MediaPlayer arg0)
	 * {
	 *
	 * }
	 * 
	 * @Override
	 * public void onCompletion(MediaPlayer arg0)
	 * {
	 *
	 * }
	 * 
	 * @Override
	 * public void onBufferingUpdate(MediaPlayer arg0, int arg1)
	 * {
	 *
	 * }
	 * 
	 * @Override
	 * public boolean onError(MediaPlayer arg0, int arg1, int arg2)
	 * {
	 * return false;
	 * }
	 */

	/**
     * 
     */
	@Override
	public boolean onError(MediaPlayer mediaPlayer, int what, int extra)
	{
		Log.e(TAG, "onError--->   what:" + what + "    extra:" + extra);
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
		}
		return true;
	}

	@Override
	public void onBackPressed()
	{
		Log.d(TAG, "onBackPressed()");
		Global.Inst().player.AllStop();
	}

	/*
	 * @Override
	 * protected void onHomePressed()
	 * {
	 * Global.Inst().player.AllStop();
	 * }
	 */

	@Override
	protected void onResume()
	{

		Log.d("TAG", "KARAOKE onResume() -----------------------------------------");
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
		DataHandler.setExternalPath(this.getFilesDir().getAbsolutePath());

		// 사용안함
		// createDTVPlayer();
		// startDTV();

		Handler mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0) {
				}

				int sbc_type = SetopHandler.getInstance().getSBC_TYPE();
				if (sbc_type == 0)
					DataHandler.telephone = "고객센터(국번없이 101)로 문의바랍니다";
				else
					DataHandler.telephone = "고객센터(1544-0001)로 문의바랍니다";

				if (Global.isRelease == false) {
					Log.d("service", "mac " + remoteLG.mac);
					Log.d("service", "subScriberNO " + remoteLG.subScriberNo);
					Log.d("service", "secret num : " + SetopHandler.getInstance().getSecretNum());
					// Log.d(TAG, "Config XML \n" + remoteLG.configXml);
				}
				// API 불안정으로 인한 임시 막음.
				Log.d("service", "setKaraokeMode : " + SetopHandler.getInstance().setKaraokeMode(true));

				// if ( Global.Inst().isTestBed )
				// {
				// ISUHandler.isu_hostname = "123.140.17.223" ; //test BED
				// ISUHandler.isu_port = 8900;
				// SIMClientHandlerLGU.dbs_hostname = "123.140.17.223";
				// SIMClientHandlerLGU.dbs_port = 8000;
				// }
				// else
				{
					SharedPreferences pref = getSharedPreferences("Setting", 0);
					ISUHandler.isu_hostname = pref.getString("isu_hostname", "");
					ISUHandler.isu_port = pref.getInt("isu_port", 0);
					SIMClientHandlerLGU.dbs_hostname = pref.getString("dbs_hostname", "");
					SIMClientHandlerLGU.dbs_port = pref.getInt("dbs_port", 0);
				}

				SIMClientHandlerLGU.cont_no = remoteLG.subScriberNo;   // KY Ref. 208092830113
				SIMClientHandlerLGU.stb_mac_addr = remoteLG.mac;       // KY Ref. 0002.141c.eae7
				Boolean bCatchError = false;

				if (Global.Inst().isRelease)
				{

					if (remoteLG.subScriberNo == null || remoteLG.subScriberNo.length() < 5) {
						bCatchError = true;
						doMessage(" 가입자 정보를 얻을수 없습니다.\n  더 이상 진행 할수 없습니다. \n" + remoteLG.subScriberNo, true);
					}

					// 상품리스트
					ISUHandler.getInstance();
					// ISUHandler.getInstance().getProductList(SIMClientHandlerLGU.cont_no, SIMClientHandlerLGU.stb_mac_addr);

					// 월정액에 가입되어있습니다. 혹은 노래방이용권을 구입해주세요
					// SIMClientHandler.user_regist_sendRequestData(); 1.1 가입 여부를 알아낸다.

					if (false == SIMClientHandlerLGU.user_regist_sendRequestData()) {       // 가입주문내역조회 200.21 .
						doAlert(SIMClientHandlerLGU.return_code, SIMClientHandlerLGU.return_message,
								DataHandler.telephone // SIMClientHandlerLGU.svc_lgu
						);
					}
					if (SIMClientHandlerLGU.register) {
						// 서비스 사용자 인증 요청 100,11
						if (SIMClientHandlerLGU.sendRequestServiceUserData() == false) {
							bCatchError = true;
							doAlert(SIMClientHandlerLGU.return_code,
									SIMClientHandlerLGU.return_message,
									DataHandler.telephone
							// SIMClientHandlerLGU.svc_lgu
							);
						}
						if (bCatchError == false) {
							// feedback
							DataHandler.GetUserInfoFromKY(SIMClientHandlerLGU.return_itemcode); // 정액 가입유무, 유저 정보에 대한 환경파일을 금영서버에서 갖어온다.
						}
						mVideoView.startProc();

						if (bCatchError == false && DataHandler.getInstance().IsServiceDate())
							doMessage("\n\n무료 이용기간 입니다.\n금영노래방을 마음껏 이용해보세요.\n", false);

						if (bCatchError == false)
							DataHandler.PollWatch(SIMClientHandlerLGU.stb_mac_addr);  // 시청률 폴.

						if (bCatchError == false)
						{
							// if ( DataHandler.getInstance().NeedUpdateVersionA( ATVKaraokeActivity.this ) == true )
							// OMA doSelectMessage( "새로운 버전이 발견 \n 지금 업데이트 할까요? " );
							// else
							{
								if (DataHandler.ADPOPUP_use)
									doPopupAD();
							}
						}
					}
					else // SIMClientHandlerLGU.register == false
					{
						doAlert(SIMClientHandlerLGU.return_code, SIMClientHandlerLGU.return_message,
								DataHandler.telephone // SIMClientHandlerLGU.svc_lgu;
						);
					} // SIMClientHandlerLGU.register

				} // Global.Inst().isRelease
			} // HandleMessate()
		}; // Handler()

		if (remoteLG == null) {

			if (Global.Inst().isDemo == true)
			{
				DataHandler.GetUserInfoFromKY(SIMClientHandlerLGU.item_id_m);
				mVideoView.startProc();
			}
			remoteLG = new GetILgIptvShmRemote(this.getApplicationContext(), mHandler);
			remoteLG.onBindIptvRemote();       // IPTV 정보를 연결한다.
		}
		else
		{
			SetopHandler.getInstance().setKaraokeMode(true);
		}
		// Upload
		DataHandler.isReachable = isReachable(getApplicationContext());
		DataHandler.UpdateSongList();

		// 1.0.44
		mVideoView.startProc();  // 예외.. 다른곳에 갔다오면 무조건 동영상 나오게 하려고

		// 1.0.45 version
		/*
		 * onResume 되었을시에 곡을 자동으로 시작되는 것 막음( 다른앱에갔다 돌아올때 문제됨)
		 * if ( isBreak == true) {
		 * KPlay.Inst().play_send( KMsg.MSG_KBD_START );
		 * isBreak = false;
		 * }
		 */

		// 1.1.14 version ( 키 락 현상 ANR )
		Handler mKeyHandler = new Handler();
		mKeyHandler.postDelayed(mMyTask, 2000); // 2초후에 실행

		super.onResume();

		// DataHandler.getInstance().RegistService(); // regist broadcast

	}

	private final Runnable mMyTask = new Runnable() {
		@Override
		public void run() {
			// KEY OPEN
			key_accept = true;

		}
	};

	@Override
	protected void onPause()
	{
		Log.d("TAG", "onPause()");
		try
		{
			Database.Inst().writeRecent(getCacheDir() + "/lately.dat");
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		// 1.0.46 버전 수정 : LGU 유튜브랑 동시에 음이 재생되는 문제
		if (Global.Inst().in_play == false) 		// ppp
			isBreak = false;
		else
		{
			KPlay.Inst().play_send(KMsg.MSG_KBD_STOP);
			isBreak = true;
		}

		// 2.27 독산동 ( 폰2티비 )
		mVideoView.doStop();

		// background
		// KPlay.Inst().play_send( KMsg.MSG_KBD_STOP );
		// ppp destoryDTVPlayer();

		super.onPause();

	}

	@Override
	protected void onStop()
	{
		Log.d(TAG, "onStop()");
		// API 불안정으로 인한 임시 막음.
		Log.d("service", "setKaraokeMode : " + SetopHandler.getInstance().setKaraokeMode(false));

		try
		{
			Database.Inst().writeRecent(getCacheDir() + "/lately.dat");
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		// 1.0.46 버전 수정 : LGU 유튜브랑 동시에 음이 재생되는 문제
		if (Global.Inst().in_play == false) 		// ppp
			isBreak = false;
		else
		{
			KPlay.Inst().play_send(KMsg.MSG_KBD_STOP);
			isBreak = true;
		}

		// 2/27 독산동 ( 폰2티비
		mVideoView.doStop();
		super.onStop();

		/*
		 * // app 종료유도
		 * ActivityManager am = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
		 * am.killBackgroundProcesses(getPackageName());
		 * System.exit(0);
		 */
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");

		// bgkim 150122 앱 종료될 때 마이크도 Disable
		SetopHandler.getInstance().setKaraokeMode(false);

		// bgkim 150122 앱 종료될 때 mp3 플레이어 처리
		Global.Inst().streamMp3player.exitMp3Player();

		// bgkim 150122 앱 종료될 때 배경영상 처리
		mVideoView.doStop();

		// bgkim 150122 APP KILL 리시버 제거
		if (receiverAppKill != null) {
			unregisterReceiver(receiverAppKill);
		}

		// app 종료유도
		ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		am.killBackgroundProcesses(getPackageName());
		System.exit(0);

	}

	/*
	 * void createDTVPlayer()
	 * {
	 * dtvPlayer = new MediaPlayer();
	 * dtvPlayer.setOnErrorListener(mErrorListener);
	 * dtvPlayer.setOnInfoListener(mInfoListener);
	 * }
	 * 
	 * void destoryDTVPlayer()
	 * {
	 * dtvPlayer.stop();
	 * dtvPlayer.reset();
	 * //ppp dtvPlayer.finalize();
	 * }
	 */
	/**
	 * @param channel
	 */
	/*
	 * void channelDTV( String channel )
	 * {
	 * ContentResolver cr = getContentResolver();
	 * Uri nUri = Uri.parse("content://tvguides");
	 * nUri = Uri.parse("content://tvguides");
	 * nUri = Uri.withAppendedPath(nUri, "services" );
	 * 
	 * Cursor nCursor = cr.query(nUri, null, null, null, null);
	 * while(nCursor!=null && nCursor.moveToNext()){
	 * Log.d(TAG, "___________________________________________________" );
	 * Log.d(TAG, "Source Id  :"+nCursor.getString(0) );
	 * Log.d(TAG, "Service Name :"+nCursor.getString(1) );
	 * Log.d(TAG, "hasMultiples :"+nCursor.getString(2) );
	 * Log.d(TAG, "serviceType :"+nCursor.getString(3) );
	 * Log.d(TAG, "serviceInfoType :"+nCursor.getString(4) );
	 * Log.d(TAG, "genreCode  :"+nCursor.getString(5) );
	 * Log.d(TAG, "CategoryCode :"+nCursor.getString(6) );
	 * Log.d(TAG, "Service Number :"+nCursor.getString(7) );
	 * Log.d(TAG, "minorNumber  :"+nCursor.getString(8) );
	 * Log.d(TAG, "___________________________________________________" );
	 * }
	 * 
	 * String path = "DTV://" + channel;
	 * try
	 * {
	 * if ( dtvPlayer != null)
	 * {
	 * dtvPlayer.setDataSource(path);
	 * dtvPlayer.setVolume(0, 0); // left, right
	 * 
	 * dtvPlayer.prepare();
	 * dtvPlayer.start(); // need binder
	 * // dtvPlayer.setAudioMuteStatusDTV(1); // mute
	 * }
	 * } catch (IOException e) {
	 * Log.e(TAG, "IOException");
	 * e.printStackTrace();
	 * }
	 * }
	 */
	/**
	 * M-net HD : 191
	 */
	/*
	 * void startDTV()
	 * {
	 * // Global.Inst().player.AllStop();
	 * // String path = "DTV://" + "41"; //M-net
	 * //y String path = "DTV://" + "242"; //km
	 * String path = "DTV://" + "71"; //km
	 * // DTV Player 초기화.
	 * dtvPlayer.reset();
	 * try {
	 * //DTV Service number set & play
	 * dtvPlayer.setDataSource(path);
	 * 
	 * Log.d(TAG, "#### Karaoke call method : setVolume(-1,0) ###");
	 * dtvPlayer.setVolume(0.0f, 0.0f); // left, right 1/5
	 * dtvPlayer.prepare();
	 * dtvPlayer.start(); // need binder
	 * // dtvPlayer.setAudioMuteStatusDTV(1); // mute
	 * //1/5 dtvPlayer.setVolume(-1, 0); // left, right
	 * // dtvPlayer.setDisplay( 0 );
	 * } catch (IOException e) {
	 * Log.e(TAG, "IOException");
	 * e.printStackTrace();
	 * }
	 * }
	 */
	private final MediaPlayer.OnErrorListener mErrorListener = new MediaPlayer.OnErrorListener()
	{
		@Override
		public boolean onError(MediaPlayer mp, int what, int extra)
		{
			return false;
		}
	};

	private final MediaPlayer.OnInfoListener mInfoListener = new MediaPlayer.OnInfoListener()
	{
		@Override
		public boolean onInfo(MediaPlayer mp, int what, int extra)
		{
			/*
			 * if ( what == MEDIA_INFO_UNKNOWN )
			 * {
			 * }
			 */
			return false;
		}
	};

	public void callback_osd_reset()
	{
		Log.d(TAG, "callback_osd_reset");
		// ViewManager.Inst().lpMenuView.setSpot();
	}

	public void callback_buyticket()
	{
		addMainMenu();
		//
		BuyTicketView popupview = new BuyTicketView(this);
		// popup = new PopupWindow( popupview, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);

		popup = new PopupWindow(popupview,
				Global.DPFromPixel(Constants.width),
				Global.DPFromPixel(Constants.height),
				true);		// full 화면을 채워 마우스 포인터를 못가게 한다.

		// popup = new PopupWindow( popupview, Constants.widthReal, Constants.heightReal, true);
		// popup = new ModalWindow( popupview , 960, 540, true); // full 화면을 채워 마우스 포인터를 못가게 한다.
		// popup.showAsDropDown(this, 0, 0);
		popup.showAtLocation(layoutWork, Gravity.CENTER, 0, 0);
		// popup.setOutsideTouchable(false); //윈도우 밖에 터치를 받아들일때 사용한다.
		popupview.setSpot();
		popupview.setParent(popup);		// 종료하기위하여
		// this.setFocusable(false);
	}

	/**
	 * booking mode 일때 불린다.
	 * 
	 * @param b
	 */
	public void setBookMode(Boolean b)
	{
		Message msg = new Message();
		if (b == true)
			msg.what = 1;
		else
			msg.what = 0;

		Log.e(TAG, "setBookMode : " + b);
		this.msgSetBookModeFocusHandler.sendMessage(msg);

	}

	// / chage osd mode
	public Handler msgSetBookModeFocusHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			if (ViewManager.Inst().lpBottomMenu != null)
			{
				ViewManager.Inst().lpBottomMenu.UpdateBottomStatus();
			}
			if (msg.what == 1)
			{
				Log.e(TAG, "lpOsdView.setSpot()");
				ViewManager.Inst().lpOsdView.setSpot();
			}
			else
			{
				if (ViewManager.Inst().lpAgo != null)
				{
					Log.e(TAG, "lpAgo.setSpot() : " + ViewManager.Inst().lpAgo.className());
					ViewManager.Inst().lpAgo.setSpot();
				}
				else
				{
					Log.e(TAG, "lpAgo is NULL");
				}
				// if ( ViewManager.Inst().lpCur != null)
				// ViewManager.Inst().lpCur.setSpot();
			}
		}
	};

}
