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
 * filename	:	BaseFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop.app
 *    |_ BaseFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke._IKaraoke.LOGIN;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.app.html.HtmlBubble;
import kr.kymedia.karaoke.app.html.HtmlUtil;
import kr.kymedia.karaoke.apps.impl.IBaseDialog;
import kr.kymedia.karaoke.util.EnvironmentUtils;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget._ImageView;

/**
 * 
 * TODO NOTE:<br>
 * 전문(KPnnnn) 추가 전 기본프래그먼트<br>
 * 절~대 직접비지니스로직에서직접꼬라박지않는다.
 * 
 * @author isyoon
 * @since 2012. 12. 4.
 * @version 1.0
 */

class BaseFragment extends Fragment implements IBaseDialog, OnTouchListener, OnClickListener, OnLongClickListener {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	public BaseFragment() {
	}

	protected String getMethodName() {
		String name = Thread.currentThread().getStackTrace()[3].getMethodName();
		String text = String.format("%s()", name);
		// int line = Thread.currentThread().getStackTrace()[3].getLineNumber();
		// text = String.format("line:%d - %s() ", line, name);
		return text;
	}

	// protected String getMethodFullName() {
	// String name = Thread.currentThread().getStackTrace()[3].getMethodName();
	// //int line = Thread.currentThread().getStackTrace()[3].getLineNumber();
	// //text = String.format("line:%d - %s() ", line, name);
	// String text = __CLASSNAME__ + "::" + name + "() ";
	// return text;
	// }

	public boolean isStartLoading() {
		if (isAttached()) {
			return getBaseActivity().isStart();
		} else {
			return false;
		}
	}

	final protected void startLoading(String name, String method, boolean start) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + name + "::" + method);
		if (isAttached() && getBaseActivity() != null && this == getBaseActivity().getCurrentFragment()) {
			getBaseActivity().startLoading(name, method, start);
		}
	}

	final protected void startLoading(String name, String method) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + name + "::" + method);
		if (isAttached() && getBaseActivity() != null && this == getBaseActivity().getCurrentFragment()) {
			getBaseActivity().startLoading(name, method);
		}
	}

	final protected void stopLoading(String name, String method) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + name + "::" + method + "-" + isStartLoading());
		if (isAttached() && getBaseActivity() != null && this == getBaseActivity().getCurrentFragment()) {
			getBaseActivity().stopLoading(name, method);
		}
	}

	final protected void startLoadingDialog(final String msg) {
		if (isAttached()) {
			getBaseActivity().startLoadingDialog(msg);
		}
	}

	final protected void stopLoadingDialog(final String msg) {
		if (isAttached()) {
			getBaseActivity().stopLoadingDialog(msg);
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		// String vn = v.getContext().getResources().getResourceEntryName(v.getId());
		// String cn = v.getClass().getSimpleName();
		// if (_IKaraoke.DEBUG)Log.d(__CLASSNAME__, getMethodName() + vn + ", " + cn + "\n" + event);
		return false;
	}

	/**
	 * 플래그먼트 뷰그룹 레이아웃 뷰클릭시
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		String vn = getResourceEntryName(v.getId());
		String cn = v.getClass().getSimpleName();
		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName() + vn + ", " + cn);
	}

	@Override
	public boolean onLongClick(View v) {

		String vn = getResourceEntryName(v.getId());
		String cn = v.getClass().getSimpleName();
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + vn + ", " + cn);
		if (!TextUtil.isEmpty(v.getContentDescription())) {
			getApp().popupToast(v.getContentDescription(), Toast.LENGTH_SHORT);
			return true;
		}
		return false;
	}

	protected Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[START]" + getTag() + ":" + savedInstanceState);

		super.onCreate(savedInstanceState);

		mContext = getApp().getApplicationContext();

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[END]" + getTag() + ":" + savedInstanceState);
	}

	/**
	 * @see android.support.v4.app.DialogFragment#onStart()
	 */
	@Override
	public void onStart() {
		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName());

		super.onStart();
	}

	/**
	 * 환경설정확인(SD카드유무, 네트워크상태, ...)
	 */
	private boolean isCheckEvironment = false;

	/**
	 * 환경설정확인(SD카드유무, 네트워크상태, ...)
	 */
	protected void checkEvironment() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		if (getBaseActivity().checkSDCardExist()) {
			checkMobileNetwork();
		}
	}

	/**
	 * 모바일데이터 사용여부확인
	 */
	private void checkMobileNetwork() {
		getApp().getSharedPreferences();

		if (getApp().mSharedPreferences == null) {
			return;
		}

		boolean isMobile = false;
		boolean isWifi = false;
		String ncode = null;

		if (getApp().mLocale != null) {
			ncode = getApp().mLocale.getCountry();
		}

		getApp().network_usemobile = getApp().mSharedPreferences.getBoolean(LOGIN.KEY_NETWORK_USEMOBILE, false);
		// kr이 아닌 곳은 3g설정 기본 체크로
		if (!("KR").equalsIgnoreCase(ncode)) {
			getApp().network_usemobile = getApp().mSharedPreferences.getBoolean(LOGIN.KEY_NETWORK_USEMOBILE, true);
		}

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + getApp().network_usemobile + "," + isMobile + "," + isWifi);

		try {
			// 안드로이드 네트워크 연결상태 확인 (Mobile/Wifi)
			ConnectivityManager manager = (ConnectivityManager) getBaseActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

			NetworkInfo info = null;

			// 모바일 네트워크 연결 상태
			info = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (info != null) {
				isMobile = info.isConnectedOrConnecting();
			}

			// Wifi 네트워크 연결 상태
			info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (info != null) {
				isWifi = info.isConnectedOrConnecting();
			}

			if (!isMobile && !isWifi) {
				getBaseActivity().showAlertDialog(getString(R.string.alert_title_error), getString(R.string.errmsg_network), getString(R.string.btn_title_finish),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int arg1) {
								close();
							}
						}, null, null, true, new OnCancelListener() {
							@Override
							public void onCancel(DialogInterface dialog) {
								close();
							}
						});
				return;
			}

			if (!getApp().network_usemobile && isMobile) {
				getBaseActivity().showAlertDialog(getString(R.string.alert_title_confirm), getString(R.string.warning_alert_mobile_use), getString(R.string.btn_title_confirm), null, null,
						null, true, null);
				return;
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + getApp().network_usemobile + "," + isMobile + "," + isWifi);
	}

	public void showCheckEvironment() {

		try {
			// 메인화면처리
			if (getBaseActivity().isACTIONMAIN()) {
				if (!isCheckEvironment) {
					if (_IKaraoke.DEBUG) {
						// 디버그모드표시
						getBaseActivity().showAlertDialog(getString(R.string.alert_title_confirm), getString(R.string.debug_mode_message), getString(R.string.btn_title_confirm),
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {

										checkEvironment();
									}
								}, null, null, true, new DialogInterface.OnCancelListener() {

									@Override
									public void onCancel(DialogInterface dialog) {

										checkEvironment();
									}
								});
					} else {
						checkEvironment();
					}

					EnvironmentUtils.getSystemAvailableFeatures(getApp());

					isCheckEvironment = true;
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	/**
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName());

		super.onResume();

		showCheckEvironment();
	}

	/**
	 * 컨텍스트메뉴오픈시 뷰를기억한다. 이건아니다... onContextItemSelected시 좃된다. onCreateContextMenu에서
	 */
	// private View mContextView = null;
	/**
	 * <pre>
	 * 최상위레이어뷰그룹
	 * onCreateView에서만 사용한다.
	 * </pre>
	 * 
	 * @see #onCreateView(LayoutInflater, ViewGroup, Bundle)
	 */
	protected ViewGroup mRootView = null;

	public ViewGroup getRootView() {
		return mRootView;
	}

	// /**
	// * 탭프래그먼트여부확인/번들데이터유무확인 getArguments() 통해서 넘어오면
	// */
	// protected boolean isArgument = false;

	/**
	 * 액티비티확인
	 */
	protected _BaseActivity getBaseActivity() {
		return ((_BaseActivity) super.getActivity());
	}

	/**
	 * 액티비티 연결여부확인 비동기작업(AsyncTask, Runnable,...)오류방지
	 * 
	 * @return
	 */
	protected boolean isAttached() {
		if (getBaseActivity() == null || isDetached() || isRemoving()) {
			return false;
		}
		return true;
	}

	public void popupToast(Context context, int resId, int dur) {
		getApp().popupToast(resId, dur);
	}

	PackageManager getPackageManager() {
		try {
			return getBaseActivity().getPackageManager();
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}

	protected String getPackageName() {
		try {
			return getApp().getPackageName();
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}

	PackageInfo getPackageInfo() {
		try {
			return getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 어플리케이션확인
	 */
	protected _BaseApplication getApp() {
		// return ((_Application) getApp());
		try {
			if (getBaseActivity() != null) {
				return getBaseActivity().getApp();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	protected int getResource(String name, String defType) {
		try {
			return getResources().getIdentifier(name, defType, getBaseActivity().getPackageName());
		} catch (Exception e) {

			// e.printStackTrace();
			return 0;
		}
	}

	// /**
	// * @see <a href="http://android-developers.blogspot.kr/2009_01_01_archive.html">Avoiding memory leaks</a>
	// */
	// protected Drawable getDrawable(String name) {
	// Drawable d = null;
	// try {
	// int res = getResource(name, "drawable");
	// if (res == 0) {
	// return null;
	// }
	// d = getResources().getDrawable(res);
	// d.setBounds(new Rect(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight()));
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	// return d;
	// }
	//
	// /**
	// * @see <a href="http://android-developers.blogspot.kr/2009_01_01_archive.html">Avoiding memory leaks</a>
	// */
	// protected Drawable getDrawable(int res) {
	// Drawable d = null;
	// try {
	// d = getResources().getDrawable(res);
	// d.setBounds(new Rect(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight()));
	// return d;
	// } catch (Exception e) {
	//
	// //e.printStackTrace();
	// return null;
	// }
	// }

	// /**
	// * @author isyoon
	// * @param resId
	// * @return
	// *
	// * 스트링으로정의된 리소스의이름ID를 가져온다
	// */
	// protected String getResourceStringName(int resId) {
	// String name = "";
	//
	// try {
	// name = getResources().getResourceEntryName(resId);
	// // text += ", " + getResources().getResourceName(resid);
	// // text += ", " + getResources().getResourcePackageName(resid);
	// // text += ", " + getResources().getResourceTypeName(resid);
	// } catch (Exception e) {
	//
	// //e.printStackTrace();
	// }
	//
	// return getString(name);
	// }

	// private final String getString(String name) {
	// try {
	// return getString(getResource(name, "string"));
	// } catch (Exception e) {
	//
	// //e.printStackTrace();
	// return "";
	// }
	// }

	/**
	 * @author isyoon
	 * @param resId
	 * @return
	 * 
	 *         리소스의이름ID를 스트링으로 가져온다
	 */
	protected String getResourceEntryName(int resId) {
		String name = "";

		try {
			name = getResources().getResourceEntryName(resId);
			// name += ", " + getResources().getResourceName(resid);
			// name += ", " + getResources().getResourcePackageName(resid);
			// name += ", " + getResources().getResourceTypeName(resid);
		} catch (Exception e) {

			// //e.printStackTrace();
		}

		return name;
	}

	protected String getResourceEntryName(Context context, int resId) {
		String name = "";

		try {
			name = context.getResources().getResourceEntryName(resId);
			// name += ", " + getResources().getResourceName(resid);
			// name += ", " + getResources().getResourcePackageName(resid);
			// name += ", " + getResources().getResourceTypeName(resid);
		} catch (Exception e) {

			// //e.printStackTrace();
		}

		return name;
	}

	//public LayoutInflater getLayoutInflater() {
	//	return getBaseActivity().getLayoutInflater();
	//}

	public View inflate(int resource, ViewGroup root) {
		return getLayoutInflater().inflate(resource, root);
	}

	public View inflate(int resource, ViewGroup root, boolean attachToRoot) {
		return getLayoutInflater().inflate(resource, root, attachToRoot);
	}

	public View findViewById(int id) {
		try {
			return getView().findViewById(id);
		} catch (Exception e) {

			// e.printStackTrace();
			return null;
		}
	}

	protected void putValue(View v, CharSequence value) {
		if (v == null) {
			return;
		}

		if (v instanceof TextView) {
			putText((TextView) v, value);
		} else if (v instanceof _ImageView) {
		}
	}

	protected void putText(TextView v, CharSequence value) {
		if (v == null) {
			return;
		}
		if (!TextUtil.isEmpty(value)) {
			v.setText(value);
			v.setVisibility(View.VISIBLE);
		} else {
			v.setVisibility(View.GONE);
		}
	}

	protected void putHtml(TextView v, CharSequence value) {
		if (v == null) {
			return;
		}
		if (!TextUtil.isEmpty(value)) {
			String text = value.toString();
			if (TextUtil.retrieveLinks(text).size() > 0) {
				v.setMovementMethod(LinkMovementMethod.getInstance());
				v.setText(Html.fromHtml(text));
			} else {
				v.setText(text);
			}
			v.setVisibility(View.VISIBLE);
		} else {
			v.setVisibility(View.GONE);
		}
	}

	String getMetaTag(Document document, String attr) {
		for (Element element : document.select("meta[name=" + attr + "]")) {
			final String s = element.attr("content");
			if (s != null) return s;
		}

		for (Element element : document.select("meta[property=" + attr + "]")) {
			final String s = element.attr("content");
			if (s != null) return s;
		}
		return null;
	}

	/**
	 * HTML
	 */
	public class parseHtml extends AsyncTask<String, String, String> {

		String url = "";
		String type = "";
		String charset = "";

		@Override
		protected String doInBackground(String... uri) {

			String body = "";
			HttpEntity entity = null;

			try {
				// HttpPost post = new HttpPost(new URI(uri[0]));
				// HttpClient client = new DefaultHttpClient();
				// post.setEntity(new StringEntity(BODY));
				// HttpResponse result = client.execute(post);
				//
				// SchemeRegistry schemeRegistry = new SchemeRegistry();
				// schemeRegistry.register(new Scheme("https",
				// SSLSocketFactory.getSocketFactory(), 443));
				// HttpParams params = new BasicHttpParams();
				// SingleClientConnManager mgr = new SingleClientConnManager(params, schemeRegistry);
				// HttpClient client = new DefaultHttpClient(mgr, params);

				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse res = httpclient.execute(new HttpGet(uri[0]));
				StatusLine statusLine = res.getStatusLine();

				if (statusLine.getStatusCode() == HttpStatus.SC_OK) {

					if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "[headers]" + res.getAllHeaders());

					for (Header header : res.getAllHeaders()) {
						if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "[header]" + header);
						if (("content-type").equalsIgnoreCase(header.getName().toLowerCase().trim()) || header.getName().toLowerCase().trim().contains("content-type")) {
							if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "[content-type][" + header.getName() + "][" + header.getValue() + "]");
							type = header.getValue();
						}
					}

					entity = res.getEntity();
					if (entity == null) {
						stopLoading(__CLASSNAME__, "retriveHtmls()");
						return body;
					}

					charset = EntityUtils.getContentCharSet(entity);
					type = entity.getContentType().getValue();

					if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "[content-type]content-type : " + type + "; charset=" + charset);

					if (("text/html").equalsIgnoreCase(type) || type.toLowerCase().trim().contains("text")) {
						if (TextUtil.isEmpty(charset)) {
							Document doc = Jsoup.parse(EntityUtils.toString(entity));

							for (Element meta : doc.select("meta[http-equiv]")) {
								if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "[meta:http-equiv]http-equiv=" + meta.attr("http-equiv") + " content=" + meta.attr("content"));

								if (("content-type").equalsIgnoreCase(meta.attr("http-equiv"))) {
									if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "[meta:http-equiv]content=" + meta.attr("content"));

									String[] values = meta.attr("content").trim().replace(" ", "").split(";");

									for (String string : values) {
										if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "[meta:http-equiv]string:" + string);
										String[] vals = string.split("=");
										if (vals.length == 2 && ("charset").equalsIgnoreCase(vals[0].toLowerCase().trim()) && !TextUtil.isEmpty(vals[1])) {
											charset = vals[1].toLowerCase().trim();
											break;
										}
									}

								}

								if (!TextUtil.isEmpty(charset)) {
									break;
								}
							}

							if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "charset=" + charset);

							res = httpclient.execute(new HttpGet(uri[0]));
							statusLine = res.getStatusLine();

							if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
								entity = res.getEntity();
							}

						}
						// ByteArrayOutputStream out = new ByteArrayOutputStream();
						// response.getEntity().writeTo(out);
						// out.close();
						// responseString = out.toString();
						if (!TextUtil.isEmpty(charset)) {
							body = EntityUtils.toString(entity, charset);
						} else {
							body = EntityUtils.toString(entity, HTTP.UTF_8);
						}
					} else {

					}

					// resEntity.getContent().close();

					this.url = uri[0];
				} else {
					throw new IOException(statusLine.getReasonPhrase());
				}
			} catch (Exception e) {
				// TODO Handle problems..
				e.printStackTrace();
			}
			return body;
		}

		@Override
		protected void onPostExecute(String source) {
			super.onPostExecute(source);
			// Do anything with response..
			// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, result);
			try {
				parseHtml(source, this.url, this.type);
				if (getBaseActivity() != null) {
					stopLoading(__CLASSNAME__, getMethodName());
				}
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * <pre>
	 * TODO
	 * </pre>
	 * 
	 * <a href="http://ogp.me/">The Open Graph protocol</a>
	 * 
	 * @param vg
	 * @param source
	 * @param url
	 * @param type
	 *          Content-Type확인
	 * @return
	 */
	protected HtmlBubble parseHtml(String source, String url, String type) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + url);

		HtmlBubble html = HtmlUtil.parseHtml(source, url, type);

		if (html != null) {
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "[html:title]" + html.title);
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "[html:description]" + html.description);
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "[html:image]" + html.image);
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "[html:body]" + html.body);
		}

		return html;
	}

	// @Deprecated
	// public Bitmap getFlag(Context context, String ncode) {
	// AssetManager am = context.getResources().getAssets();
	// InputStream is = null;
	// Bitmap bmp = null;
	// try {
	// is = am.open("flags/img_flag_" + ncode.toLowerCase() + ".png");
	// bmp = BitmapFactory.decodeStream(new BufferedInputStream(is));
	// } catch (IOException e) {
	//
	// e.printStackTrace();
	// }
	// return bmp;
	// }
	//
	// /**
	// * <pre>
	// * 절라느리네...
	// * </pre>
	// *
	// */
	// @Deprecated
	// public ArrayList<String> getFlagsNcodes(Context context) {
	// ArrayList<String> flags = new ArrayList<String>();
	// AssetManager am = context.getResources().getAssets();
	//
	// try {
	// for (int i = 0; i < am.list("flags").length; i++) {
	// String name = am.list("flags")[i];
	// name = name.replace("img_flag_", "");
	// name = name.replace(".png", "");
	// flags.add(name);
	// }
	// } catch (IOException e1) {
	//
	// e1.printStackTrace();
	// }
	//
	// return flags;
	// }

	/**
	 * 액티비티종료를 호출하나~ 메인제외
	 */
	public void close() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());


		// 메인화면은제외
		if (getBaseActivity().isACTIONMAIN()) {
			return;
		}

		getBaseActivity().close();
	}

	public void openUpdate(final String url) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + url);
		try {
			String udateUrl = "market://details?id=" + getBaseActivity().getPackageName();
			if (!TextUtil.isEmpty(url)/* TextUtil.isNetworkUrl(url) || TextUtil.isMarketUrl(url) */) {
				udateUrl = url;
			}
			final Uri updateUri = Uri.parse(udateUrl);
			Intent intent = new Intent(Intent.ACTION_VIEW, updateUri);
			startActivity(intent);
			close();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// @Deprecated
	// DialogInterface.OnClickListener mUpdatePListener = new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int arg1) {
	// openUpdate("");
	// }
	// };
	//
	// @Deprecated
	// DialogInterface.OnClickListener mUpdateNListener = new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int arg1) {
	// openUpdate("");
	// }
	// };
	//
	// @Deprecated
	// OnCancelListener mUpdateCListener = new OnCancelListener() {
	// public void onCancel(DialogInterface dialog) {
	// close();
	// }
	// };
	//
	// @Deprecated
	// protected void showUpdateDialog(String r_message) {
	// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, getMethodName());
	//
	// showDialogAlert(getString("alert_title_confirm"), r_message, getString("btn_title_update"),
	// mUpdatePListener, null, null, true, null);
	//
	// }

	/**
	 * 액티비티 백버튼차단
	 */
	public boolean onBackPressed() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());
		boolean ret = true;
		return ret;
	}

	/**
	 * 액티비티 터치차단<br>
	 * 로딩등... 필요에 액티비티의 dispatchTouchEvent를 차단한다.<br>
	 * <br>
	 * 
	 * @return true : 터치차단 false : 터치유지
	 * @see BaseActivity2#dispatchTouchEvent(MotionEvent)
	 */
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return false;
	}

	/**
	 * @see IBaseDialog#cancelDownloadFile()
	 */
	@Override
	public void cancelDownloadFile() {


	}

	/**
	 * @see IBaseDialog#onDownloadFileComplete(java.lang.String)
	 */
	@Override
	public void onDownloadFileComplete(String path) {


	}

	/**
	 * @see IBaseDialog#uploadFile()
	 */
	@Override
	public void uploadFile() {


	}

	/**
	 * @see IBaseDialog#cancelUploadFile()
	 */
	@Override
	public void cancelUploadFile() {


	}

	/**
	 * @see IBaseDialog#onUploadFileComplete(java.lang.String)
	 */
	@Override
	public void onUploadFileComplete(String path) {


	}

	/**
	 * @see IBaseDialog#onUploadFileCancel(java.lang.String)
	 */
	@Override
	public void onUploadFileCancel(String path) {


	}

}
