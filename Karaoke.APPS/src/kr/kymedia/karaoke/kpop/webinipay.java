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
 * filename	:	webinipay.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ webinipay.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.apps._BaseActivity;
import kr.kymedia.karaoke.apps.BaseAdActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 *
 * TODO<br>
 * NOTE:<br>
 *
 * @author isyoon
 * @since 2013. 3. 28.
 * @version 1.0
 * @see
 */
public class webinipay extends _BaseActivity implements OnClickListener {

	protected String getMethodName() {
		String name = Thread.currentThread().getStackTrace()[3].getMethodName();
		String text = String.format("%s()", name);
		// int line = Thread.currentThread().getStackTrace()[3].getLineNumber();
		// text = String.format("line:%d - %s() ", line, name);
		return text;
	}

	FrameLayout mWebContainer;
	WebView mWebView = null;
	private String mURL;
	// private String mMethod;

	private static final int DIALOG_PROGRESS_WEBVIEW = 0;
	private static final int DIALOG_PROGRESS_MESSAGE = 1;
	private static final int DIALOG_ISP = 2;

	private FileDownloadTask downloadTask;

	private class ChromeClient extends WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {

			webinipay.this.setProgress(newProgress * 1000);
		}
	}

	public String getWebURL() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		String URL = "";
		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			// mMethod = extras.getString("method");
			// 최초조회시
			URL = extras.getString("url");
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, mURL);
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, URL);
			if (URL != null && !URL.equalsIgnoreCase(mURL)) {
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, mURL);
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, URL);
				mURL = URL;
			}
		}

		return URL;
	}

	protected void onCreate(Bundle savedInstanceState) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());
		super.onCreate(savedInstanceState);
		// setTitle("");
	}

	@Override
	public void onClick(View v) {


	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		super.onPostCreate(savedInstanceState);
		process();
	}

	public void onConfigurationChanged(Configuration newConfig) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());
		super.onConfigurationChanged(newConfig);
	}

	/**
	 * @see BaseAdActivity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		super.onBackPressed();
	}

	@Override
	protected void onResume() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		super.onResume();
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();

		mWebContainer.removeAllViews();
		mWebView.destroy();
	}

	/**
	 * 진저브레드이하(2.3.x) HTML인풋테크 키보드보기 오류 (소액결제, 쪽지보내기,...)
	 * <a href="http://stackoverflow.com/questions/9262092/webview-html-input-form-not-showing-allowing-keyboard">Webview html input form not showing/allowing keyboard</a>
	 */
	private void process() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		// mWebView = (WebView) findViewById(R.id.webview);
		mWebContainer = (FrameLayout) findViewById(R.id.fragment1);
		mWebView = new WebView(this);
		mWebContainer.addView(mWebView);

		mWebView.setVisibility(View.VISIBLE);

		mWebView.setWebChromeClient(new ChromeClient());
		mWebView.setWebViewClient(new SampleWebViewClient());
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setSavePassword(false);
		mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		mWebView.getSettings().setAppCacheEnabled(false);

		mWebView.setDownloadListener(new DownloadListener() {

			@Override
			public void onDownloadStart(String url, String userAgent,
					String contentDisposition, String mimeType, long contentLength) {
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, url);

				// URL에 apk파일이 링크되었을 경우
				if (url.endsWith(".apk"))
				{
					// 재실행에 대한 처리
					if (downloadTask == null)
					{
						downloadTask = new FileDownloadTask(url, contentLength);

					}
					else
					{
						downloadTask.cancel(true);
						downloadTask = null;
						downloadTask = new FileDownloadTask(url, contentLength);
					}

					// downloadTask.execute(null);
					downloadTask.execute((Object) null);
				}
			}
		});

		mWebView.loadUrl(getWebURL());

		mWebView.requestFocus(View.FOCUS_DOWN);

	}

	private class SampleWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "shouldOverrideUrlLoading()");
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, url);

			if (_IKaraoke.DEBUG) Log2.d("<sonnbk>", "####URL:" + url);
			/*
			 * URL별로 분기가 필요합니다. 어플리케이션을 로딩하는것과 WEB PAGE를 로딩하는것을 분리 하여 처리해야 합니다.
			 * 만일 가맹점 특정 어플 URL이 들어온다면 조건을 더 추가하여 처리해 주십시요.
			 */
			if (!url.startsWith("http://")
					&& !url.startsWith("https://")
					&& !url.startsWith("javascript:"))
			{

				Uri uri = Uri.parse(url);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				try {
					if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "shouldOverrideUrlLoading::startActivity()");
					startActivity(intent);

					/*
					 * 가맹점의 사정에 따라 현재 화면을 종료하지 않아도 됩니다.
					 * 이니시스 데모(INIpayMobile)어플리케이션에서는
					 * ISP일경우 이니시스 프로세스는 모두 끝이나기 때문에 종료합니다.
					 * (샘플에서는 웹뷰하나만 사용하기 때문에 종료하지 않음)
					 * 삼성카드 기타 안심클릭에서는 종료되면 안되기때문에 조건을 걸어 종료하도록 하였습니다.
					 * if( url.startsWith("ispmobile://"))
					 * {
					 * finish();
					 * }
					 */
				} catch (ActivityNotFoundException e)
				{
					if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "shouldOverrideUrlLoading::ispmobile://");
					e.printStackTrace();
					/*
					 * ISP어플이 현재 폰에 없다면 아래 처리에서 알림을 통해 처리하도록 하였습니다.
					 * 삼성카드 및 기타 안심클릭에서는 카드사 웹페이지에서 알아서 처리하기때문에
					 * WEBVIEW에서는 별다른 처리를 하지 않아도 처리됩니다.
					 */
					if (url.startsWith("ispmobile://"))
					{

						view.loadData("<html><body></body></html>", "text/html", "euc-kr");
						// onCreateDialog에서 정의한 ISP 어플리케이션 알럿을 띄워줍니다.(ISP 어플리케이션이 없을경우)
						showDialog(DIALOG_ISP);
						return false;
					}
				}

			}
			else
			{
				view.loadUrl(url);
				return false;
			}

			return true;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "onPageStarted() ");
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, url);
			try {
				stopLoading(__CLASSNAME__, getMethodName());
				mURL = url;
				mWebView.clearHistory();
			} catch (Exception e)
			{
				// ignore;
			}
		}

		public void onPageFinished(WebView view, String url) {
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "onPageFinished() ");
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, url);
			try {
				stopLoading(__CLASSNAME__, getMethodName());
				mURL = url;
				mWebView.clearHistory();
			} catch (Exception e)
			{
				// ignore;
			}
		}

		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			// view.loadData("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>" +
			// "</head><body>"+"요청실패 : ("+errorCode+")" + description+"</body></html>", "text/html", "utf-8");
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "onReceivedError() ");
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, failingUrl);
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "" + errorCode);
			Log2.e(__CLASSNAME__, description);
			stopLoading(__CLASSNAME__, getMethodName());
			Toast.makeText(getApp(), description, Toast.LENGTH_SHORT).show();
			mWebView.clearHistory();
		}
	}

	private ProgressDialog mProgressDialog;
	private AlertDialog alertIsp;

	protected Dialog onCreateDialog(int id) {// ShowDialog에서 인자로 넘긴 아이디값이랑 동일
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + id);

		switch (id) {

		// 웹페이지를 표시할때 보여줄 프로그레스 알럿
		case DIALOG_PROGRESS_WEBVIEW:
			ProgressDialog dialog = new ProgressDialog(this);
			dialog.setMessage(getString(R.string.msg_text_loading));
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
			return dialog;

			// ISP V3백신을 다운로드 받을때 띄워줄 프로그레스 알럿
		case DIALOG_PROGRESS_MESSAGE:
			// ProgressBar를 함께 표현한 다이얼로그입니다. AlertDialog를 구현하는 것과 비슷하게 ProgressDialog 객체를 생성한 후 title, message, progressStyle 등을 지정하여 다이얼로그를 생성합니다. ProgressBar의 속성을 지정하는 것과 같이 ProgressDialog에서도 Progress 속성을 지정할 수 있습니다.

			mProgressDialog = new ProgressDialog(webinipay.this);
			mProgressDialog.setIcon(R.drawable.ic_launcher);
			mProgressDialog.setTitle("다운로드");
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mProgressDialog.setMax(100);
			mProgressDialog.setButton("취소", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					downloadTask.cancel(true);
				}
			});

			mProgressDialog.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {
					downloadTask.cancel(false);
				}

			});

			return mProgressDialog;

			// ISP 어플이 없을경우 띄워줄 알럿
		case DIALOG_ISP:
			alertIsp = new AlertDialog.Builder(webinipay.this)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle("알림")
					.setMessage("모바일 ISP 어플리케이션이 설치되어 있지 않습니다. \n설치를 눌러 진행 해 주십시요.\n취소를 누르면 결제가 취소 됩니다.")
					.setPositiveButton("설치", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							String ispUrl = "http://mobile.vpay.co.kr/jsp/MISP/andown.jsp";

							mWebView.loadUrl(ispUrl);
							finish();
						}

					})
					.setNegativeButton("취소", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							Toast.makeText(getApp(), "(-1)결제를 취소 하셨습니다.", Toast.LENGTH_SHORT).show();
							finish();
						}

					}).create();

			return alertIsp;
		}

		return super.onCreateDialog(id);
	}

	/**
	 * 
	 * 프로그레스 알럿을 통해 다운로드를 하기 위한 비동기 태스크 입니다.
	 * 웹페이지 상에 다운로드 링크를 클릭했을 경우(APK 파일) 실행됩니다.
	 *
	 */
	private class FileDownloadTask extends AsyncTask<Object, Integer, Long> {

		private String fileUrl = null;
		private long contentLength = 0;
		private long downloadBytes = 0;
		private String fileName = "";

		private FileDownloadTask(String fileUrl, long contentLength) {
			this.fileUrl = fileUrl;
			this.contentLength = contentLength;
			this.fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
		}

		private File getApkFile() {
			String mPath = Environment.getExternalStorageDirectory() + "/download/" + fileName;
			File f = new File(mPath);
			return f;
		}

		@Override
		protected void onPreExecute() {

			File f = getApkFile();

			if (f.exists() && f.length() < contentLength)
			{
				f.delete();
			}

			if (f.length() == contentLength)
			{
				downloadBytes = contentLength;
			}
			else
			{
				downloadBytes = 0;
				showDialog(DIALOG_PROGRESS_MESSAGE);

				mProgressDialog.setProgress(0);// progress의 초기값을 0으로
			}

			super.onPreExecute();
		}

		@Override
		protected Long doInBackground(Object... params) {

			URL myFileUrl = null;
			try {
				myFileUrl = new URL(fileUrl);
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return 0L;
			}
			try {
				HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
				conn.setDoInput(true);
				conn.connect();
				InputStream is = conn.getInputStream();

				/*
				 * 다운 받는 파일의 경로는 sdcard/download 아래 이다.
				 * 단, sdcard에 접근하려면 uses-permission에 android.permission.WRITE_EXTERNAL_STORAGE을 추가해야한다.
				 */

				FileOutputStream fos;

				File f = getApkFile();

				if (downloadBytes == 0 && f.createNewFile()) {
					fos = new FileOutputStream(f);

					int read;
					while (!isCancelled() && (read = is.read()) != -1) {

						fos.write(read);
						downloadBytes++;

						if (downloadBytes > 0
								&& ((downloadBytes % (contentLength / 100) == 0)
								||
								(downloadBytes == contentLength)
								))
						{
							publishProgress(0);
						}
					}
					fos.close();
				}
				else
				{
					downloadBytes = contentLength;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (downloadBytes >= contentLength)
			{

				// 안드로이드 패키지 매니저를 통해 다운 받은 apk 파일을 처리하도록 한다.
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(getApkFile()), "application/vnd.android.package-archive");
				startActivity(intent);
			}

			return downloadBytes;
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {

			if (mProgressDialog != null)
			{
				if (downloadBytes >= contentLength) {
					mProgressDialog.dismiss();
				} else {
					mProgressDialog.incrementProgressBy(1);// progressbar를 1씩 증가시킴
				}
			}
		}

		@Override
		protected void onPostExecute(Long result) {
			if (contentLength == result.longValue())
				Toast.makeText(getApp(), "다운로드가 완료되었습니다.", 0).show();
		}

		protected void onCancelled() {

			super.onCancelled();
		}
	}

}
