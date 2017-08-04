package com.kumyoung.gtvkaraoke;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import isyoon.com.devscott.karaengine.Global;
import kr.kumyoung.gtvkaraoke.R;

import com.kumyoung.common.Constants;
import com.kumyoung.main.app.BaseActivity3;
import com.kumyoung.stbcomm.SIMClientHandlerLGU;
import com.lguplus.iptv3.base.settingmanager.GetILgIptvShmRemote;

public class SplashActivity extends BaseActivity3 {
	private static final String TAG = SplashActivity.class.getSimpleName();

	private static GetILgIptvShmRemote remoteLG = null;
	public TextView tvVersion = null;

	public static String getOption() {
		String strRet = "Microphone ";
		strRet += (Global.isRelease ? "RELEASE" : "DEBUG");
		if (Global.isTestBed) strRet += "(TESTBED)";
		if (Global.isTestPayment) strRet += "(TestPAYMENT)";
		if (Global.isDemo) strRet += "(DEMO)";
		return strRet;
	}

	public static String getVersionName(Context context) {
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return pi.versionName;
		} catch (NameNotFoundException e) {
			return null;
		}
	}

	public static int getVersionCode(Context context) {
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			return pi.versionCode;
		} catch (NameNotFoundException e) {
			return 0;
		}
	}

	public static boolean isReachable(Context context) {
		// First, check we have any sort of connectivity
		final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo netInfo = connMgr.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected())
		{
			// Some sort of connection is open, check if server is reachable
			try {
				URL url = new URL("http://www.google.com");
				HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
				urlc.setRequestProperty("User-Agent", "Android Application");
				urlc.setRequestProperty("Connection", "close");
				urlc.setConnectTimeout(3 * 1000); // Thirty seconds timeout in milliseconds
				urlc.connect();
				if (urlc.getResponseCode() == 200)  // Good response
					return true;
				else
					// Anything else is unwanted
					return false;

			} catch (IOException e) {
				Log.e(TAG, e.getMessage());
				return false;
			}
		} else
			return false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_activity);

		/**
         * 
         */
		final Handler mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0) {
				}

				// Log.d("service", "mac " + remoteLG.mac );
				// Log.d("service", "subScriberNO " + remoteLG.subScriberNo );
				// Log.d("service", "secret num : " + SetopHandler.getInstance().getSecretNum() );
				SIMClientHandlerLGU.cont_no = remoteLG.subScriberNo;   // KY Ref. 208092830113
				SIMClientHandlerLGU.stb_mac_addr = remoteLG.mac;       // KY Ref. 0002.141c.eae7

				if (remoteLG.configXml != null)
				{
					XmlFinder xf = new XmlFinder();
					// xf.setXml( remoteLG.configXml);
					SharedPreferences pref = getSharedPreferences("Setting", 0);
					SharedPreferences.Editor edit = pref.edit();
					if (xf.find(remoteLG.configXml, "dbs")) {
						// SIMClientHandlerLGU.dbs_hostname = xf.getAttribute("address");
						// SIMClientHandlerLGU.dbs_port = Integer.parseInt(xf.getAttribute("port"));
						edit.putString("dbs_hostname", xf.getAttribute("address"));
						edit.putInt("dbs_port", Integer.parseInt(xf.getAttribute("port")));
					}
					if (xf.find(remoteLG.configXml, "isu")) {
						// ISUHandler.isu_hostname = xf.getAttribute("address");
						// ISUHandler.isu_port = Integer.parseInt(xf.getAttribute("port"));
						edit.putString("isu_hostname", xf.getAttribute("address"));
						edit.putInt("isu_port", Integer.parseInt(xf.getAttribute("port")));
					}
					edit.commit();
				}
				// if ( false == SIMClientHandlerLGU.user_regist_sendRequestData () ) // 가입주문내역조회 200.21 .
				// DataHandler.GetUserInfoFromKY( SIMClientHandlerLGU.return_itemcode ); // 정액 가입유무, 유저 정보에 대한 환경파일을 금영서버에서 갖어온다.

				// LGU 독산동 문제점
				/*
				 * for ( int i = 0; i < 10; i++)
				 * {
				 * if ( DataHandler.isReachable == false )
				 * {
				 * try {
				 * Thread.sleep(1000);
				 * } catch (InterruptedException e) {
				 * e.printStackTrace();
				 * }
				 * }
				 * }
				 */
				if (DataHandler.isReachable == true)
				{
					DataHandler.GetBannerInfoFromKY();

					// 141111 bgkim 실행하고 랜선 빼면 호스트/null 되서 튕기는 경우
					if (DataHandler.AD_image == null || DataHandler.AD_image.equals("null")) {
					} else {
						ContentsDownloader.downloadFile(DataHandler.serverKYIP + DataHandler.AD_image, DataHandler.getExternalPath() + "adimage.png");
						ContentsDownloader.downloadFile(DataHandler.serverKYIP + DataHandler.theme1_image, DataHandler.getExternalPath() + "theme1.png");
						ContentsDownloader.downloadFile(DataHandler.serverKYIP + DataHandler.theme2_image, DataHandler.getExternalPath() + "theme2.png");
					}
				}

			}
		}; // Handler()

		tvVersion = (TextView) findViewById(R.id.textViewStatus);
		startVersion();

		postDelayed(new Runnable() {
			@Override
			public void run() {
				DataHandler.setExternalPath(getFilesDir().getAbsolutePath());
				StrictMode.setThreadPolicy(
						new StrictMode.ThreadPolicy.Builder().permitNetwork().build()
						);

				SplashDataThread thread = new SplashDataThread(mainHandler);
				thread.start();

				if (remoteLG == null)
				{
					/*
					 * if (Global.Inst().isDemo == true)
					 * {
					 * DataHandler.GetUserInfoFromKY( SIMClientHandlerLGU.item_id_m);
					 * // mVideoView.startProc();
					 * }
					 */
					remoteLG = new GetILgIptvShmRemote(getApplicationContext(), mHandler);
					remoteLG.onBindIptvRemote();       // IPTV 정보를 연결한다.

				}
			}
		}, 2000);

	}

	@Override
	protected void recieveMessage(Message msg) {
		super.recieveMessage(msg);
		switch (msg.what) {
		case Constants.MSG_SPLASH_NET_ERROR:
			finish();
			android.os.Process.killProcess(android.os.Process.myPid());
			break;
		case Constants.MSG_SPLASH_COMPLETE:
			// 141111 bgkim 실행하고 랜선 빼면 호스트/null 되서 튕기는 경우
			if (DataHandler.AD_image == null || DataHandler.AD_image.equals("null")) {
				Toast.makeText(getApplicationContext(), "네트워크 연결이 불안정하여 앱이 종료됩니다.\r\n앱을 다시 실행해주세요.", Toast.LENGTH_LONG).show();
				mainHandler.sendEmptyMessageDelayed(Constants.MSG_SPLASH_NET_ERROR, 3000);
				return;
			}

			Intent intent = new Intent(this, _ATVKaraokeActivity.class);

			/*
			 * 
			 * if (Global.Inst().isDemo == true)
			 * {
			 * try {
			 * URL myFileUrl = new URL("http://joyul.iptime.org/port.ini");
			 * // Read all the text returned by the server
			 * BufferedReader in = new BufferedReader(new InputStreamReader(myFileUrl.openStream()));
			 * String str;
			 * while ((str = in.readLine()) != null) {
			 * // str is one line of text; readLine() strips the newline character(s)
			 * startActivity(intent);
			 * finish();
			 * return;
			 * 
			 * }
			 * in.close();
			 * } catch (MalformedURLException e) {
			 * } catch (IOException e) {
			 * }
			 * 
			 * 
			 * finish();
			 * return;
			 * }
			 */
			startActivity(intent);
			finish();
			break;

		}
	}

	/**
	 * 로컬 디비 검색후 없을시 다운로드 진행하는 쓰레드
	 * 
	 * @author gs.won
	 */
	class SplashDataThread extends Thread
	{
		private Handler handler = null;

		public SplashDataThread(Handler handler)
		{
			this.handler = handler;
		}

		@Override
		public void run() {

			super.run();

			DataHandler.isReachable = isReachable(getApplicationContext());

			try {
				// bgkim lately.dat 경로를 찾지 못할 때 토스트 뿌리고 종료
				String path = getCacheDir() + "/lately.dat";

				if (!path.equals("/data/data/com.kumyoung.gtvkaraoke/cache/lately.dat"))
				{

					post(new Runnable() {
						@Override
						public void run() {
							// Toast.makeText(getApplicationContext(), "네트워크 연결이 불안정하여 앱이 종료됩니다.\r\n앱을 다시 실행해주세요.", Toast.LENGTH_LONG).show();
							Toast.makeText(getApplicationContext(), "기기정보데이터를 가져오지 못하여 앱이 종료됩니다.\r\n앱을 다시 실행해주세요.", Toast.LENGTH_LONG).show();
						}
					});

					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}

					finish();
					return;
				}

				FileInputStream fis = new FileInputStream(getCacheDir() + "/lately.dat");
				{
					BufferedReader in = new BufferedReader(new InputStreamReader(fis, "euc-kr"));
					StringBuilder builder = new StringBuilder();
					String str;
					while ((str = in.readLine()) != null)
					{
						int sno = Integer.parseInt(str);
						Database.Inst().addRecent(sno, "");
					}
					Log.d(TAG, "update songlist file");
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			/**
			 * help, Delay
			 */
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Message msg = Message.obtain();
			msg.what = Constants.MSG_SPLASH_COMPLETE;
			handler.sendMessage(msg);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.w(TAG, "onKeyDown(" + keyCode + "," + event + ")");

		// return super.onKeyDown(keyCode, event);

		return false;
	}

}