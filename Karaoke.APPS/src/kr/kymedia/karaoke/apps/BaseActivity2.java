/**
 * // * 2012 All rights (c)KYmedia Co.,Ltd. reserved.
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * <p/>
 * 프로젝트:	Karaoke.KPOP
 * 파일명:	base.java
 * 작성자:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ base.java
 * </pre>
 */
/**
 *
 */

package kr.kymedia.karaoke.apps;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnCloseListener;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke._IKaraoke.LOGIN;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.KPnnnn;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.impl.IBaseActivity;
import kr.kymedia.karaoke.util.Log;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget._ImageView;

/**
 * <pre>
 * 기본액티비티2<br>
 * 전문(KPnnnn)<br>
 * GCM(푸시서비스)<br>
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2012. 2. 13.
 */
class BaseActivity2 extends BaseActivity1 implements IBaseActivity, SearchManager.OnCancelListener, SearchManager.OnDismissListener {

	/**
	 * 기본KPnnnn 절대정적할당하지 않는다~<br>
	 */
	protected KPnnnn KP_nnnn = null;
	/**
	 * 엑티비티이동간 전달 info 조회후 정보로 사용하지 않는다. 한마디로 이전화면에서 전달되 info정보
	 */
	KPItem mInfo = new KPItem();

	/**
	 * 엑티비티이동간 전달 info 조회후 정보로 사용하지 않는다. 한마디로 이전화면에서 전달되 info정보
	 */
	final public KPItem getInfo() {
		return mInfo;
	}

	// /**
	// * 엑티비티이동간 전달 list배열 조회후 정보로 사용하지 않는다.
	// */
	// @Deprecated
	// private ArrayList<KPItem> mLists = new ArrayList<KPItem>();
	//
	// /**
	// */
	// public void setLists(ArrayList<KPItem> lists) {
	// this.mLists = lists;
	// }

	/**
	 * 엑티비티이동간 전달 list 조회후 정보로 사용하지 않는다. 한마디로 이전화면에서 전달된 list정보
	 */
	KPItem mList = new KPItem();

	/**
	 * 엑티비티이동간 전달 list 조회후 정보로 사용하지 않는다. 한마디로 이전화면에서 전달된 list정보
	 */
	final public KPItem getList() {
		return mList;
	}

	// /**
	// * 엑티비티이동간 전달 list 조회후 정보로 사용하지 않는다.
	// */
	// public void setList(KPItem list) {
	// this.mList = list;
	// }

	/**
	 * 멀티프래그먼트시 현재의 프래그먼트를기억한다. onContextItemSelected시 좃된다. onCreateContextMenu에서
	 */
	public Fragment mContextFragment = null;

	private View mStartLoadInc = null;
	/**
	 * 시작로딩
	 */
	private _ImageView mStartLoading = null;
	/**
	 * 시작로딩
	 */
	AnimationDrawable mStartLoadAni = null;

	/**
	 * 시작로딩
	 */
	private boolean isStart = true;

	/**
	 * @return the isStartLoading
	 */
	final public boolean isStart() {
		return isStart;
	}

	/**
	 * 현재로딩중확인한다.
	 *
	 * @see #dispatchTouchEvent(MotionEvent)
	 */
	private boolean isLoading = true;

	/**
	 * 현재로딩중확인한다.
	 *
	 * @see #dispatchTouchEvent(MotionEvent)
	 */
	final public boolean isLoading() {
		return isLoading;
	}

	/**
	 * 로딩시 터치/클릭 차단<br>
	 * <b>잘못하믄좃된다~~~</b><br>
	 * <br>
	 *
	 * @see #isStart
	 * @see #isLoading
	 * @see #isLoading()
	 */
	@Override
	final public boolean dispatchTouchEvent(MotionEvent ev) {
		try {

			boolean block = false;

			// 시작로딩시차단
			if (isStart || isLoadingDialog) {
				block = true;
			}

			if (mProgressDialog != null && mProgressDialog.getProgress() == 0 && mProgressDialog.isShowing()) {
				block = true;
			}

			if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
				block = true;
			}

			// 프래그먼트에서차단시차단
			if (getCurrentFragment() != null && getCurrentFragment().dispatchTouchEvent(ev)) {
				block = true;
			}

			if (block) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.dispatchTouchEvent(ev);
	}

	/**
	 * 초기로딩용 프레임바이프레임 애니메이션 {@link startLoading}에서 호출한다.
	 *
	 * @see #dispatchTouchEvent(MotionEvent)
	 * @see #startLoading
	 */
	final public void startLoading(String name, String method, boolean isStart) {
		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.i("[" + getMethodName() + "]", name + "::" + method + isStart);

		this.isStart = isStart;
		startLoading(name, method);
	}

	/**
	 * 초기로딩용 프레임바이프레임 애니메이션 {@link startLoading}에서 호출한다.
	 *
	 * @see #dispatchTouchEvent(MotionEvent)
	 * @see #startLoading
	 */
	final public void startLoading(String name, String method) {
		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.i("[" + getMethodName() + "]", name + "::" + method + isStart);

		if (!isStart) {
			if (findViewById(R.id.progress) != null) {
				findViewById(R.id.progress).setVisibility(View.VISIBLE);
			}
			return;
		}

		isStart = false;
		isLoading = true;

		// startLoading();
		try {
			if (mStartLoadInc == null) {
				mStartLoadInc = findViewById(R.id.include_loading);
			}

			if (mStartLoading == null) {
				mStartLoading = (_ImageView) findViewById(R.id.loading);
				if (mStartLoading != null) {
					// mStartLoading.setImageDrawable(getDrawable(R.drawable.ani_loading_character));
					mStartLoading.setImageResource(R.drawable.ani_loading_character);
					mStartLoadAni = (AnimationDrawable) mStartLoading.getDrawable();
				}
			}

			if (_IKaraoke.DEBUG) {
				if (mStartLoading != null) {
					mStartLoading.setBackgroundColor(getResources().getColor(android.R.color.transparent));
				}
				if (mStartLoadInc != null) {
					mStartLoadInc.setBackgroundColor(getResources().getColor(android.R.color.transparent));
				}
			}

			setProgressBarIndeterminateVisibility(true);

			mStartLoadInc.setVisibility(View.VISIBLE);
			mStartLoading.setVisibility(View.VISIBLE);
			mStartLoadAni.start();

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 초기로딩용 프레임바이프레임 애니메이션 {@link stopLoading}에서 호출한다.
	 *
	 * @see #dispatchTouchEvent(MotionEvent)
	 * @see #stopLoading
	 */
	final public void stopLoading(String name, String method) {
		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.i("[" + getMethodName() + "]", name + "::" + method);

		isStart = false;
		isLoading = false;

		try {
			// 초기화면로딩
			setProgressBarIndeterminateVisibility(false);

			if (mStartLoadInc != null) {
				mStartLoadInc.setVisibility(View.GONE);
			}

			if (mStartLoading != null) {
				mStartLoading.setVisibility(View.GONE);
			}

			if (mStartLoadAni != null) {
				mStartLoadAni.stop();
			}

			if (findViewById(R.id.progress) != null) {
				findViewById(R.id.progress).setVisibility(View.GONE);
			}
			stopLoadingDialog(null);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	boolean isLoadingDialog = false;
	AlertDialog mLoadingDialog;

	final public void startLoadingDialog(final String msg) {
		isLoadingDialog = true;

		try {

			if (isFinishing()) {
				return;
			}

			if (isDestroyed()) {
				return;
			}

			String message = "";

			if (TextUtil.isEmpty(msg)) {
				message = getString(R.string.msg_text_loading);
			} else {
				message = msg;
			}

			if (mLoadingDialog == null) {
				if (mLoadingDialog instanceof ProgressDialog) {
					mLoadingDialog = new ProgressDialog(this);
				} else {
					AlertDialog.Builder ab = new AlertDialog.Builder(this);
					mLoadingDialog = ab.create();
				}
			}

			// mLoadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			// mLoadingDialog.setProgressDrawable(null);
			//mLoadingDialog.setMessage(message);
			//mLoadingDialog.setMessage("");
			mLoadingDialog.setCancelable(true);
			mLoadingDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {

					isLoadingDialog = false;
				}
			});
			mLoadingDialog.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface arg0) {

					isLoadingDialog = false;
				}
			});

			if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
				mLoadingDialog.show();
			}
		} catch (Exception e) {

			e.printStackTrace();
			isLoadingDialog = false;
		}
	}

	final public void stopLoadingDialog(final String msg) {
		isLoadingDialog = false;

		try {
			if (isFinishing()) {
				return;
			}

			if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
				mLoadingDialog.dismiss();
			}

			if (!TextUtil.isEmpty(msg)) {
				getApp().popupToast(msg, Toast.LENGTH_LONG);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	final private void startProgress(final String msg) {
		try {
			if (isFinishing()) {
				return;
			}

			String message = "";

			if (TextUtil.isEmpty(msg)) {
				message = getString(R.string.msg_text_loading);
			} else {
				message = msg;
			}

			if (mProgressDialog == null) {
				mProgressDialog = new ProgressDialog(BaseActivity2.this);
			}

			if (mProgressDialog != null) {
				mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				mProgressDialog.setMessage(message);
				mProgressDialog.setCancelable(true);
				mProgressDialog.setOnCancelListener(new OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {

						isLoadingDialog = false;
					}
				});
				mProgressDialog.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss(DialogInterface arg0) {

						isLoadingDialog = false;
					}
				});
				if (!mProgressDialog.isShowing()) {
					mProgressDialog.show();
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	final private void startProgress(final String msg, final long size, final long total) {
		try {
			if (isFinishing()) {
				return;
			}

			if (mProgressDialog == null) {
				return;
			}

			if (!mProgressDialog.isShowing()) {
				mProgressDialog.show();
			}


			String message = "";

			if (TextUtil.isEmpty(msg)) {
				message = getString(R.string.msg_text_loading);
			} else {
				message = msg;
			}

			if (mProgressDialog == null) {
				mProgressDialog = new ProgressDialog(BaseActivity2.this);
			}

			if (mProgressDialog != null) {
				mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				mProgressDialog.setMessage(message);
				mProgressDialog.setCancelable(true);
				mProgressDialog.setOnCancelListener(new OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {

						isLoadingDialog = false;
					}
				});
				mProgressDialog.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss(DialogInterface arg0) {

						isLoadingDialog = false;
					}
				});
				if (!mProgressDialog.isShowing()) {
					mProgressDialog.show();
				}
			}

			int percent = (int) Math.round(100.0 * size / (total == 0 ? 1 : total));
			mProgressDialog.setProgress(percent);

			message = String.format(msg + "\n(%s/%s bytes)", TextUtil.getDecimalFormat("#,##0", size), TextUtil.getDecimalFormat("#,##0", total));

			// 전송진행시
			if (size < total) {
				mProgressDialog.setMessage(message);
				mProgressDialog.setIndeterminate(false);
			} else {
				// 전송종료후
				mProgressDialog.setMessage("Processing...\nPlease Wait.");
				mProgressDialog.setIndeterminate(true);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	final private void stopProgress(final String msg) {
		try {

			if (mProgressDialog != null && mProgressDialog.isShowing()) {
				mProgressDialog.dismiss();
			}
			if (!TextUtil.isEmpty(msg)) {
				getApp().popupToast(msg, Toast.LENGTH_LONG);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * TODO
	 * 로딩종료
	 * </pre>
	 */
	@Override
	protected void onResume() {
		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName());

		super.onResume();

		stopLoadingDialog(null);
	}

	/**
	 * 앱스키마 URI데이터처리
	 */
	final protected Intent getIntentData(Intent intent) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[START]");

		if (intent == null) {
			return null;
		}

		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, "[INTENT]" + intent);
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "[EXTRAS]" + intent.getExtras());
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "[PARAMS]" + intent.getData());

		KPItem info = null;
		KPItem list = null;

		try {
			Uri uri = intent.getData();

			if (uri != null) {
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "[record_id]" + uri.getQueryParameter("record_id"));

				intent.putExtra("index", uri.getQueryParameter("index"));
				intent.putExtra("id", uri.getQueryParameter("id"));
				intent.putExtra("type", uri.getQueryParameter("type"));
				intent.putExtra("ticker", uri.getQueryParameter("ticker"));
				intent.putExtra("title", uri.getQueryParameter("title"));
				intent.putExtra("message", uri.getQueryParameter("message"));
				if (!TextUtil.isEmpty(uri.getQueryParameter("info"))) {
					intent.putExtra("info", URLDecoder.decode(uri.getQueryParameter("info"), "UTF-8"));
				}
				if (!TextUtil.isEmpty(uri.getQueryParameter("list"))) {
					intent.putExtra("list", URLDecoder.decode(uri.getQueryParameter("list"), "UTF-8"));
				}

			}

			// 화면깨우기
			// PushWakeLock.acquireCpuWakeLock(context);

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

			// KPItem데이터
			info = new KPItem(intent.getStringExtra("info"));
			list = new KPItem(intent.getStringExtra("list"));

			String text = "";
			text = "!!!![EXCUTE_URL]!!!!";
			text += "\n[mActivity]" + this;
			text += "\n[index]" + index;
			text += "\n[id]" + id;
			text += "\n[type]" + type;
			text += "\n[ticker]" + ticker;
			text += "\n[title]" + title;
			text += "\n[message]" + message;
			text += "\n[info]\n" + info.toString(2);
			text += "\n[list]\n" + list.toString(2);
			if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, text);

		} catch (Exception e) {

			// e.printStackTrace();
			Log.e(__CLASSNAME__, getMethodName() + Log.getStackTraceString(e));
		}

		if (info == null || list == null) {
			Log.e(__CLASSNAME__, "info - " + info);
			Log.e(__CLASSNAME__, "list - " + list);

			if (info == null) {
				getApp().showDataError(__CLASSNAME__, getMethodName(), "info", info);
				return null;
			}

			if (list == null) {
				getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
				return null;
			}
		}

		return intent;
	}

	@Override
	final public Intent getIntentData() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		Bundle extras = null;
		Intent intent = getIntent();

		if (intent != null) {
			extras = intent.getExtras();
		}

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "[EXTRAS]\n" + extras);

		// 번들데이터생성
		if (extras != null) {
			mInfo = extras.getParcelable(_IKaraoke.KEY_KPOP_INFOITEM);
			// mLists = extras.getParcelableArrayList(_IKaraoke.KEY_KPOP_LISTITEMS);
			mList = extras.getParcelable(_IKaraoke.KEY_KPOP_LISTITEM);
		}

		// 검색 액티비티에서 넘어온 Bundle 데이터를 읽는다.
		extras = intent.getBundleExtra(SearchManager.APP_DATA);
		if (extras != null) {
			mInfo = extras.getParcelable(_IKaraoke.KEY_KPOP_INFOITEM);
			// mLists = extras.getParcelableArrayList(_IKaraoke.KEY_KPOP_LISTITEMS);
			mList = extras.getParcelable(_IKaraoke.KEY_KPOP_LISTITEM);
		}

		return intent;
	}

	final protected KPnnnn KP_init(Context context, KPnnnn KP_xxxx) {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "" + context);
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "" + KP_xxxx);

		if (mHandlerQuery == null) {
			mHandlerQuery = new HandlerQuery(this);
		}

		String m1 = getString(R.string.M1_MAIN);
		String m2 = getString(R.string.M2_MENU);

		if (getCurrentFragment() != null) {
			m1 = getCurrentFragment().p_m1;
			m2 = getCurrentFragment().p_m2;
		}

		if (KP_xxxx == null) {
			KP_xxxx = new KPnnnn(context, mHandlerQuery, getApp().KPparam, getApp().p_mid, getApp().p_passtype, getApp().p_passcnt);
		} else {
			KP_xxxx.KP_nnnn(getApp().KPparam, getApp().p_mid, getApp().p_passtype, getApp().p_passcnt, m1, m2);
		}

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "" + KP_xxxx);

		return KP_xxxx;
	}

	/**
	 * KPnnnn초기화 딴데서 하지마라!
	 */
	@Override
	public void KP_init() {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

		Context context = getApp();

		KP_nnnn = KP_init(context, KP_nnnn);

		getApp().KP_9003 = KP_init(context, getApp().KP_9003);

	}

	/**
	 * 조회스타트!!!
	 */
	@Override
	public void KP_nnnn() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());


		// stopLoading(__CLASSNAME__, getMethodName());
	}

	/**
	 * 조회결과반영 기능: 1. 회원기본정보저장 2. 로그인여부확인 (email유무확인)
	 */
	@Override
	public void KPnnnn() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + KP_nnnn);
	}

	/**
	 * 조회결과오류표시 기능: 1. 로딩화면중지 2. 오류내용팝업
	 */
	protected void KPnnnnResult(int what, String p_opcode, String r_code, String r_message, String r_info) {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[WHAT]" + what + "[OPCODE]" + p_opcode + "[RESULT_CODE]" + r_code);

		if (getCurrentFragment() != null) {
			getCurrentFragment().KPnnnnResult(what, p_opcode, r_code, r_message, r_info);
		}

	}

	@Override
	public void onKPnnnnResult(int what, String opcode, String r_code, String r_message, String r_info) {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + what + ", " + opcode + ", " + r_code + ", " + r_message);

		KPnnnnResult(what, opcode, r_code, r_message, r_info);

		switch (what) {
			case _IKaraoke.STATE_DATA_QUERY_START:
				break;

			case _IKaraoke.STATE_DATA_QUERY_SUCCESS:
				KPnnnn();
				break;

			case _IKaraoke.STATE_DATA_QUERY_FAIL:
				break;

			case _IKaraoke.STATE_DATA_QUERY_ERROR:
				break;

			case _IKaraoke.STATE_DATA_QUERY_CANCEL:
				break;

			default:
				break;
		}

	}

	/**
	 * 전문용핸들러
	 */
	// final protected Handler mHandlerQuery = new Handler() {
	//
	// /**
	// * /**
	// *
	// * @see android.os.Handler#handleMessage(android.os.Message)
	// */
	// @Override
	// public void handleMessage(Message msg) {
	//
	// super.handleMessage(msg);
	//
	// int what = msg.what;
	// String opcode = msg.getData().getString("opcode");
	// String r_code = msg.getData().getString("result_code");
	// String r_message = msg.getData().getString("result_message");
	// String r_info = msg.getData().getString("result_info");
	//
	// // if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, "handleMessage(...) " + what + ", " + r_code
	// // + ", " + r_message);
	//
	// onKPnnnnResult(what, opcode, r_code, r_message, r_info);
	// }
	//
	// };
	protected HandlerQuery mHandlerQuery;

	static class HandlerQuery extends Handler {
		WeakReference<BaseActivity2> m_HandlerObj;

		HandlerQuery(BaseActivity2 handlerobj) {
			m_HandlerObj = new WeakReference<BaseActivity2>(handlerobj);
		}

		@Override
		public void handleMessage(Message msg) {
			BaseActivity2 handlerobj = m_HandlerObj.get();
			if (handlerobj == null) {
				return;
			}
			handlerobj.onKPnnnnResult(msg);
		}
	}

	final private void onKPnnnnResult(Message msg) {
		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName() + msg);


		int what = msg.what;
		String opcode = msg.getData().getString("opcode");
		String r_code = msg.getData().getString("result_code");
		String r_message = msg.getData().getString("result_message");
		String r_info = msg.getData().getString("result_info");

		onKPnnnnResult(what, opcode, r_code, r_message, r_info);
	}

	/**
	 * 푸시 REGID등록(KP_9003)만처리<br>
	 */
	final protected void initGCM() {
		if (!isACTIONMAIN()) {
			return;
		}

		getApp().initGCM(this);

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__ + "[GCM]", getMethodName() + getApp().p_mid + " : " + getApp().getGCM().REGID);
	}

	/**
	 * 옵션메뉴선택시 다음화면에 전달될 인텐트생성 후 화면이동
	 */
	final protected Intent onOptionsItemSelected(int id, String menu_name, boolean open) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + getResourceEntryName(id) + ":" + menu_name);
		Intent nIntent = null;
		_BaseFragment fragment = getCurrentFragment();
		if (fragment != null) {
			nIntent = fragment.onOptionsItemSelected(id, menu_name, open);
		}
		return nIntent;
	}

	/**
	 * 컨텍스트메뉴이동시처리 - 상속
	 */
	final protected Intent onContextItemSelected(Context context, int id, final KPItem info, final KPItem list, final boolean open) {
		Intent nIntent = null;
		_BaseFragment fragment = getCurrentFragment();
		if (fragment != null) {
			nIntent = fragment.onContextItemSelected(context, id, info, list, open);
		}
		return nIntent;
	}

	@Override
	final public boolean showDialog2(int id, Bundle args) {

		stopLoading(__CLASSNAME__, getMethodName());
		stopLoadingDialog(null);

		return super.showDialog2(id, args);
	}

	@Override
	final public void dismissDialog2(int id) {

		stopLoading(__CLASSNAME__, getMethodName());
		stopLoadingDialog(null);

		super.dismissDialog2(id);
	}

	@Override
	final public void removeDialog2(int id) {

		stopLoading(__CLASSNAME__, getMethodName());
		stopLoadingDialog(null);

		super.removeDialog2(id);
	}

	final public void showDatePickerDialog(DatePickerDialog.OnDateSetListener callBack, String date) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

		Calendar c = Calendar.getInstance();

		int cday = c.get(Calendar.DAY_OF_MONTH);
		int cmonth = c.get(Calendar.MONTH);
		int cyear = c.get(Calendar.YEAR);

		String dates[] = TextUtils.split(date, ".");

		if (dates.length == 3) {
			cday = TextUtil.parseInt(dates[0]);
			cmonth = TextUtil.parseInt(dates[1]);
			cyear = TextUtil.parseInt(dates[2]);
		}

		DatePickerDialog alert = new DatePickerDialog(this, callBack, cyear, cmonth, cday);

		alert.show();
	}

	private AlertDialog.Builder mAleartDialogBuilder = null;
	//private AlertDialog mAlertDialogs = null;
	private HashMap<String, AlertDialog> mAlertDialogs = new HashMap<>();
	private ProgressDialog mProgressDialog = null;

	final public void showAlertDialog(String title, String message, CharSequence pText, android.content.DialogInterface.OnClickListener p, CharSequence nText, android.content.DialogInterface.OnClickListener n, boolean canable, android.content.DialogInterface.OnCancelListener c) {
		showAlertDialog(0, title, message, pText, p, nText, n, canable, c);
	}
	/**
	 * <pre>
	 * 도대체컨텍스트를쓰란거야마란거야시팔
	 * </pre>
	 */
	final public void showAlertDialog(int attrId, String title, String message, CharSequence pText, android.content.DialogInterface.OnClickListener p, CharSequence nText, android.content.DialogInterface.OnClickListener n, boolean canable, android.content.DialogInterface.OnCancelListener c) {
		if (_IKaraoke.DEBUG) Log2.e("BaseActivity1", getMethodName() + "\n" + message);

		stopLoading(__CLASSNAME__, getMethodName());
		stopLoadingDialog(null);

		if (isFinishing()) {
			return;
		}

		AlertDialog dialog = null;

		try {
			// 도대체컨텍스트를쓰란거야마란거야시팔
			final AlertDialog.Builder ab = new AlertDialog.Builder(this);

			ab.setIconAttribute(attrId);

			ab.setTitle(title);

			ab.setMessage(message);

			// OK버튼처리
			if (pText != null/* && pListener != null */) {
				ab.setPositiveButton(pText, p);
			}

			// NO버튼처리
			if (nText != null/* && nListener != null */) {
				ab.setNegativeButton(nText, n);
			}

			// 취소버튼처리
			ab.setCancelable(canable);
			if (c != null) {
				ab.setOnCancelListener(c);
			}

			//동일메시지팝업은지운다.
			dismissAlertDialogsAll(message);

			dialog = ab.create();

			putAlertDialogs(message, dialog, true);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (dialog != null) {
			dialog.setOnShowListener(new OnShowListener() {

				@Override
				public void onShow(DialogInterface dialog) {

					stopLoading(__CLASSNAME__, getMethodName());
				}
			});
		}
	}

	final public void dismissAlertDialogsAll() {
		try {
			List<String> keys = new ArrayList<>();
			for (String key : mAlertDialogs.keySet()) {
				keys.add(key);
			}
			for (String key : keys) {
				mAlertDialogs.get(key).dismiss();
				mAlertDialogs.remove(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	final public void dismissAlertDialogsAll(String key) {
		try {
			if (null != mAlertDialogs.get(key)) {
				mAlertDialogs.get(key).dismiss();
				mAlertDialogs.remove(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	final protected void putAlertDialogs(String key, AlertDialog dialog, boolean show) {
		try {
			mAlertDialogs.put(key, dialog);
			if (null != mAlertDialogs.get(key) && show) {
				mAlertDialogs.get(key).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	final protected Dialog createDialog(int id, Bundle args) {
		// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, getMethodName());


		Dialog ret = null;

		String msg = "";
		String err = "";

		if (args != null) {
			msg = args.getString("message");
			err = args.getString("error");
		}

		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + id + "," + err + "," + msg);

		final _BaseFragment fragment = getCurrentFragment();

		if (fragment == null) {
			return null;
		}

		switch (id) {
			case DIALOG_ALERT_NOSDCARD:
				mAleartDialogBuilder = new AlertDialog.Builder(this);

				// 타이틀처리
				mAleartDialogBuilder.setTitle(R.string.alert_title_error);

				// 메시지처리
				mAleartDialogBuilder.setMessage(R.string.errmsg_nosdcard);

				// OK버튼처리
				mAleartDialogBuilder.setPositiveButton(R.string.btn_title_ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int arg1) {
					}
				});

				// 취소버튼처리
				mAleartDialogBuilder.setCancelable(true);
				mAleartDialogBuilder.setOnCancelListener(new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
					}
				});

				ret = mAleartDialogBuilder.create();
				break;

			case DIALOG_ALERT_NOLOGIN_YESNO:
				mAleartDialogBuilder = new AlertDialog.Builder(this);
				try {

					// 타이틀처리
					mAleartDialogBuilder.setTitle(getString(R.string.alert_title_confirm));

					// 메시지처리
					// msg = getString(R.string.warning_nologin_preview);
					// mAleartDialogBuilder.setMessage(msg);
					mAleartDialogBuilder.setMessage(msg);

					// OK버튼처리
					mAleartDialogBuilder.setPositiveButton(getString(R.string.btn_title_login), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int arg1) {
							onOptionsItemSelected(R.id.menu_login, "", true);
						}
					});

					// NO버튼처리
					mAleartDialogBuilder.setNegativeButton(getString(R.string.btn_title_ignore), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int arg1) {
							if (getCurrentFragment() instanceof PlayFragment) {
								((_PlayFragment) getCurrentFragment()).play();
							}
						}
					});

				} catch (Exception e) {

					// e.printStackTrace();
				}

				// 취소버튼처리
				mAleartDialogBuilder.setCancelable(true);
				mAleartDialogBuilder.setOnCancelListener(new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						if (getCurrentFragment() instanceof PlayFragment) {
							((_PlayFragment) getCurrentFragment()).play();
						}
					}
				});

				ret = mAleartDialogBuilder.create();
				break;

			case DIALOG_ALERT_NOPASS_YESNO:
				mAleartDialogBuilder = new AlertDialog.Builder(this);
				try {

					// 타이틀처리
					mAleartDialogBuilder.setTitle(getString(R.string.alert_title_confirm));

					// 메시지처리
					// msg = getString(R.string.warning_nologin_preview);
					// mAleartDialogBuilder.setMessage(msg);
					mAleartDialogBuilder.setMessage(msg);

					// OK버튼처리
					mAleartDialogBuilder.setPositiveButton(getString(R.string.btn_title_pass), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int arg1) {
							// onOptionsItemSelected(R.id.menu_ticket, "", true);
							onOptionsItemSelected(R.id.menu_shop_charge, "", true);
						}
					});

					// NO버튼처리
					mAleartDialogBuilder.setNegativeButton(getString(R.string.btn_title_close), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int arg1) {
							if (getCurrentFragment() instanceof PlayFragment) {
								((_PlayFragment) getCurrentFragment()).play();
							}
						}
					});

				} catch (Exception e) {

					// e.printStackTrace();
				}

				// 취소버튼처리
				mAleartDialogBuilder.setCancelable(true);
				mAleartDialogBuilder.setOnCancelListener(new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						if (getCurrentFragment() instanceof PlayFragment) {
							((_PlayFragment) getCurrentFragment()).play();
						}
					}
				});

				ret = mAleartDialogBuilder.create();
				break;

			case DIALOG_ALERT_LOGIN_DELETE_YESNO:
				mAleartDialogBuilder = new AlertDialog.Builder(this);
				try {

					// 타이틀처리
					mAleartDialogBuilder.setTitle(getString(R.string.alert_title_confirm));

					// 메시지처리
					msg = getString(R.string.summary_setting_login_delete);
					mAleartDialogBuilder.setMessage(msg);

					// OK버튼처리
					mAleartDialogBuilder.setPositiveButton(getString(R.string.btn_title_yes), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int arg1) {
							if (fragment != null) {
								fragment.deleteUserId();
							}
						}
					});

					// NO버튼처리
					mAleartDialogBuilder.setNegativeButton(getString(R.string.btn_title_no), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int arg1) {
						}
					});

				} catch (Resources.NotFoundException e) {

					// e.printStackTrace();
				}

				// 취소버튼처리
				mAleartDialogBuilder.setCancelable(true);
				mAleartDialogBuilder.setOnCancelListener(new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
					}
				});

				ret = mAleartDialogBuilder.create();
				break;

			case DIALOG_ALERT_MESSAGE_CONFIRM:
				mAleartDialogBuilder = new AlertDialog.Builder(this);
				try {

					// 타이틀처리
					mAleartDialogBuilder.setTitle(getString(R.string.alert_title_confirm));

					// 메시지처리
					mAleartDialogBuilder.setMessage(msg);

					// 확인버튼처리
					mAleartDialogBuilder.setPositiveButton(getString(R.string.btn_title_confirm), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int arg1) {
						}
					});

				} catch (Resources.NotFoundException e) {

					// e.printStackTrace();
				}

				// 취소버튼처리
				mAleartDialogBuilder.setCancelable(true);
				mAleartDialogBuilder.setOnCancelListener(new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
					}
				});

				ret = mAleartDialogBuilder.create();
				break;

			case DIALOG_ALERT_MESSAGE_YESNO:
				mAleartDialogBuilder = new AlertDialog.Builder(this);
				try {

					// 타이틀처리
					mAleartDialogBuilder.setTitle(getString(R.string.alert_title_confirm));

					// 메시지처리
					mAleartDialogBuilder.setMessage(msg);

					// OK버튼처리
					mAleartDialogBuilder.setPositiveButton(getString(R.string.btn_title_yes), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int arg1) {
						}
					});

					// NO버튼처리
					mAleartDialogBuilder.setNegativeButton(getString(R.string.btn_title_no), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int arg1) {
						}
					});

				} catch (Resources.NotFoundException e) {

					// e.printStackTrace();
				}

				// 취소버튼처리
				mAleartDialogBuilder.setCancelable(true);
				mAleartDialogBuilder.setOnCancelListener(new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
					}
				});

				ret = mAleartDialogBuilder.create();
				break;

			case DIALOG_PROGRESS_SONG_UPWARN:
				mAleartDialogBuilder = new AlertDialog.Builder(this);
				try {

					// 타이틀처리
					mAleartDialogBuilder.setTitle(getString(R.string.alert_title_confirm));

					// 메시지처리
					mAleartDialogBuilder.setMessage(getString(R.string.warning_record_upload));

					// OK버튼처리
					mAleartDialogBuilder.setPositiveButton(getString(R.string.btn_title_yes), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int arg1) {
							showDialog2(DIALOG_PROGRESS_SONG_UPLOAD, null);
						}
					});

					// NO버튼처리
					mAleartDialogBuilder.setNegativeButton(getString(R.string.btn_title_no), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int arg1) {
							close();
						}
					});

				} catch (Resources.NotFoundException e) {

					// e.printStackTrace();
				}

				// 취소버튼처리
				mAleartDialogBuilder.setCancelable(true);
				mAleartDialogBuilder.setOnCancelListener(new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						close();
					}
				});

				ret = mAleartDialogBuilder.create();
				break;

			case DIALOG_PROGRESS_SONG_UPLOAD:
				mProgressDialog = new ProgressDialog(this);
				mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				try {
					// 메시지처리
					mProgressDialog.setMessage(getString(R.string.msg_data_uploadloading));
				} catch (Resources.NotFoundException e) {

					// e.printStackTrace();
				}
				uploadFile();

				// 취소버튼처리
				mProgressDialog.setCancelable(true);
				mProgressDialog.setOnCancelListener(new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						getApp().popupToast(getString(R.string.msg_data_uploadcancel), Toast.LENGTH_SHORT);
						cancelUploadFile();
						close();
					}
				});

				ret = mProgressDialog;
				break;

			default:
				// ret = super.createDialog(id, args);
				break;
		}

		return ret;
	}

	@Override
	final protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());


		String msg = "";
		if (args != null) {
			msg = args.getString("message");
		}

		switch (id) {
			case DIALOG_ALERT_MESSAGE_CONFIRM:
			case DIALOG_ALERT_MESSAGE_YESNO:
				((AlertDialog) dialog).setMessage(msg);
				break;

			default:
				break;
		}
		super.onPrepareDialog(id, dialog, args);
	}

	@Override
	protected Dialog onCreateDialog(int id) {

		stopLoading(__CLASSNAME__, getMethodName());
		stopLoadingDialog(null);

		Dialog ret = createDialog(id, null);

		if (ret != null) {
			return ret;
		} else {
			return super.onCreateDialog(id);
		}

	}

	@Deprecated
	@Override
	final protected Dialog onCreateDialog(int id, Bundle args) {

		stopLoading(__CLASSNAME__, getMethodName());
		stopLoadingDialog(null);

		Dialog ret = createDialog(id, args);

		if (ret != null) {
			return ret;
		} else {
			return super.onCreateDialog(id, args);
		}
	}

	/**
	 * 푸쉬처리된인텐트를 받은후 화면이동을한다.
	 * <p/>
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
	 * <p/>
	 * <br>
	 *
	 * @see handleGCMMessage(Context, Intent)
	 * @see BaseActivity2#getIntentData()
	 */
	final private Intent handleGCMMessage(Context context, Intent intent, boolean open) {
		Intent nIntent = null;

		KPItem info = null;
		KPItem list = null;

		try {

			if (intent == null) {
				return null;
			}

			if (intent.getExtras() == null) {
				return null;
			}

			if (intent.getExtras().isEmpty()) {
				return null;
			}

			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[START]" + getApp().intentGCM);

			if (getApp().intentGCM != null && intent.getExtras().equals(getApp().intentGCM.getExtras())) {
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[SAME]");
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + intent.getExtras().hashCode());
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + getApp().intentGCM.getExtras().hashCode());
				return null;
			}

			// 메인화면(홈화면)인 경우만 푸시인텐트를 저장한다.
			if (isACTIONMAIN()) {
				getApp().intentGCM = intent;
			}

			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "[INTENT]" + intent);
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "[EXTRAS]" + intent.getExtras());
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "[PARAMS]" + intent.getData());

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

			// KPItem데이터
			info = new KPItem(intent.getStringExtra("info"));
			list = new KPItem(intent.getStringExtra("list"));

			ArrayList<KPItem> lists = new ArrayList<KPItem>();
			lists.add(list);

			String text = "";
			text = "!!!![PUSH]!!!!";
			text += "\n[index]" + index;
			text += "\n[id]" + id;
			text += "\n[type]" + type;
			text += "\n[ticker]" + ticker;
			text += "\n[title]" + title;
			text += "\n[message]" + message;
			text += "\n[info]\n" + info.toString(2);
			text += "\n[list]\n" + list.toString(2);
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, getMethodName() + "\n" + text);

			if (TextUtil.isEmpty(ticker)) {
				ticker = "[NG]ticker";
			}
			if (TextUtil.isEmpty(title)) {
				title = "[NG]title";
			}
			if (TextUtil.isEmpty(message)) {
				message = "[NG]message";
			}

			// 녹음곡공유페이지처리
			Uri uri = intent.getData();
			String record_id = "";
			if (uri != null) {
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "[record_id]" + uri.getQueryParameter("record_id"));
				record_id = uri.getQueryParameter("record_id");
			}

			if (!TextUtil.isEmpty(record_id)) {
				id = "context_play_listen";
				info.putValue("record_id", record_id);
				list.putValue("record_id", record_id);
			}

			if (!TextUtil.isEmpty(id)) {
				stopLoading(__CLASSNAME__, getMethodName());
				stopLoadingDialog(null);
				if (id.contains("menu_")) {
					nIntent = onOptionsItemSelected(getResource(id, "id"), "", open);
				} else if (id.contains("context_")) {
					nIntent = onContextItemSelected(context, getResource(id, "id"), info, list, open);
				}
			}

		} catch (Exception e) {

			// e.printStackTrace();
			Log.e(__CLASSNAME__, getMethodName() + Log.getStackTraceString(e));
		}

		if (info == null || list == null) {
			Log.e(__CLASSNAME__, "info - " + info);
			Log.e(__CLASSNAME__, "list - " + list);

			if (info == null) {
				getApp().showDataError(__CLASSNAME__, getMethodName(), "info", info);
				return null;
			}

			if (list == null) {
				getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
				return null;
			}
		}

		return nIntent;
	}

	public boolean equalBundles(Bundle one, Bundle two) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		if (one == null || two == null) return true;

		if (one.size() != two.size()) return false;

		// if (one.toString().equals(two.toString())) return true;

		Set<String> setOne = one.keySet();
		Object valueOne;
		Object valueTwo;

		for (String key : setOne) {
			valueOne = one.get(key);
			valueTwo = two.get(key);
			if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, "" + key);
			// if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, "" + valueOne);
			// if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, "" + valueTwo);
			if (valueOne instanceof Bundle && valueTwo instanceof Bundle && !equalBundles((Bundle) valueOne, (Bundle) valueTwo)) {
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[NG][1]");
				return false;
			} else if (valueOne == null) {
				if (valueTwo != null || !two.containsKey(key)) {
					if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[NG][2]");
					return false;
				}
			} else if (!valueOne.equals(valueTwo)) {
				if (valueTwo != null && valueOne.toString().equals(valueTwo.toString())) {
					if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + "[OK][4]");
					continue;
				} else {
					if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[NG][3]");
				}
				return false;
			}
		}

		return true;
	}

	final protected String getMenuName() {
		String menu_name = "";

		try {
			if (mList != null) {
				menu_name = mList.getValue("menu_name");
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + menu_name);
		return menu_name;
	}

	/**
	 * 기본레이아웃XML로드
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[START]");

		super.onCreate(savedInstanceState);

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getResourceEntryName(mLayoutResID));

		// JPOP.HOLIC 강제언어설정
		// if (_IKaraoke.APP_PACKAGE_NAME_JPOP.equalsIgnoreCase(getPackageName())) {
		// Resources res = getApplicationContext().getResources();
		// // Change locale settings in the app.
		// DisplayMetrics dm = res.getDisplayMetrics();
		// android.content.res.Configuration conf = res.getConfiguration();
		// conf.locale = new Locale("JA");
		// res.umProgressDialogateConfiguration(conf, dm);
		// }

		// 로딩처리
		startLoading(__CLASSNAME__, getMethodName());

		View v = null;

		// v = findViewById(R.id.fragment1);
		// if (v != null && this instanceof ITabPagerActivity) {
		// v.setVisibility(View.GONE);
		// }
		//
		// v = findViewById(R.id.tab_indicator_pager);
		// if (v != null && !(this instanceof ITabPagerActivity)) {
		// v.setVisibility(View.GONE);
		// }

		v = findViewById(R.id.ad_freezone);
		if (v != null) {
			v.setVisibility(View.GONE);
		}

		v = findViewById(R.id.ad_layouts);
		if (v != null) {
			if (_IKaraoke.DEBUG) {
				v.setVisibility(View.VISIBLE);
			} else {
				v.setVisibility(View.GONE);
			}
		}

		getIntentData();

		// 타이틀처리
		setTitle(getMenuName());

		startLoadingDialog(null);

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[END]");
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {

		super.onPostCreate(savedInstanceState);

		// 푸쉬처리
		Intent newIntent = getIntentData(getIntent());
		handleGCMMessage(getApp(), newIntent, true);
	}

	/**
	 * <pre>
	 * 머지랄이래
	 * <a href="http://stackoverflow.com/questions/6147884/onactivityresult-not-being-called-in-fragment">onActivityResult not being called in Fragment - Stack Overflow</a>
	 * <a href="http://stackoverflow.com/questions/20038880/onactivityresult-for-fragment">onActivityResult For Fragment - Stack Overflow</a>
	 * </pre>
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

	}

	@Override
	protected void onDestroy() {

		if (mAleartDialogBuilder != null) {
		}
		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
		}
		mProgressDialog = null;

		dismissAlertDialogsAll();

		mHandlerQuery = null;

		super.onDestroy();
	}

	@Override
	final public void onCancel() {

		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName());

	}

	@Override
	final public void onDismiss() {

		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName());

	}

	final private Bundle putSearchExtras() {
		_BaseFragment fragment = getCurrentFragment();

		if (fragment == null) {
			return null;
		}

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + fragment);

		String m1 = fragment.p_m1;
		String m2 = fragment.p_m2;
		String menu_name = "";

		KPItem info = new KPItem();
		KPItem list = new KPItem();

		info.putValue(LOGIN.KEY_MID, getApp().p_mid);
		list.putValue("m1", m1);
		list.putValue("m2", m2);
		list.putValue("menu_name", menu_name);
		list.putValue("uid", getApp().p_mid);

		// 다음 액티비티로 넘길 Bundle 데이터를 만든다.
		Bundle extras = new Bundle();
		extras.putParcelable(_IKaraoke.KEY_KPOP_INFOITEM, info);
		extras.putParcelable(_IKaraoke.KEY_KPOP_LISTITEM, list);

		if (m1 != null && m2 != null) {
			if (m1.contains("LISTEN") || m2.contains("LISTEN")) {
				extras.putString("type", "LISTEN");
			} else {
				extras.putString("type", "SING");
			}
		}

		extras.putString("m1", m1);
		extras.putString("m2", m2);

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "\n" + extras);

		return extras;
	}

	@Override
	final public boolean onSearchRequested() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		Bundle extras = putSearchExtras();
		startSearch(null, false, extras, false);

		return true;
	}

	@Override
	final public void startSearch(String initialQuery, boolean selectInitialQuery, Bundle appSearchData, boolean globalSearch) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "\n" + appSearchData);


		super.startSearch(initialQuery, selectInitialQuery, appSearchData, globalSearch);

		// 검색시작시재생화면종료
		if (getCurrentFragment() instanceof _PlayFragment) {
			// ((playFragment) getCurrentFragment()).isOnPauseClose = true;
			// ((playFragment) getCurrentFragment()).isOnPausePause = true;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// return super.onCreateOptionsMenu(menu);

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + menu);


		MenuInflater menuInflater = getMenuInflater();

		menuInflater.inflate(R.menu.menu_actionbar, menu);

		boolean ret = super.onCreateOptionsMenu(menu);

		// 액션버튼/메뉴 보이기/가리기
		setActionMenuVisible(menu);

		// Associate searchable configuration with the SearchView
		try {
			SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
			SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.menu_search));
			searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
			searchView.setQueryRefinementEnabled(true);

			searchView.setOnQueryTextListener(new OnQueryTextListener() {

				@Override
				public boolean onQueryTextSubmit(String newText) {

					// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + newText);

					Bundle extras = putSearchExtras();
					Intent searchIntent = new Intent(getApp(), _SearchResultActivity.class);
					searchIntent.putExtra(SearchManager.QUERY, newText);
					searchIntent.setAction(Intent.ACTION_SEARCH);
					// searchIntent.putExtras(extras);
					searchIntent.putExtra(SearchManager.APP_DATA, extras);
					searchIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

					startActivity(searchIntent);

					int requestCode = _IKaraoke.KARAOKE_INTENT_ACTION_DEFAULT;
					startActivityForResult(searchIntent, requestCode);

					return true;
				}

				@Override
				public boolean onQueryTextChange(String query) {

					// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + query);

					return false;
				}
			});

			searchView.setOnSearchClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "setOnSearchClickListener()::" + getMethodName() + v);
					getSupportActionBar().setDisplayUseLogoEnabled(false);

				}
			});

			searchView.setOnCloseListener(new OnCloseListener() {

				@Override
				public boolean onClose() {

					if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());
					getSupportActionBar().setDisplayUseLogoEnabled(true);

					return false;
				}
			});
		} catch (Exception e) {

			e.printStackTrace();
		}

		return ret;
	}

	/**
	 * 액션버튼/메뉴 보이기/가리기<br>
	 * 홉버튼업표시처리<br>
	 *
	 * @see BaseActivity2#onCreateOptionsMenu(Menu)
	 */
	public void setActionMenuVisible(Menu menu) {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + isACTIONMAIN() + menu);

		try {

			if (getSupportActionBar() != null) {
				getSupportActionBar().setHomeButtonEnabled(true);
				/**
				 * 이게먼...개지랄이야...구글...이븅신들아...
				 */
				if (isACTIONMAIN()) {
					/**
					 * 홈버튼숨기고 - 로고이미지
					 */
					getSupportActionBar().setLogo(R.drawable.tit_logo);
					getSupportActionBar().setIcon(R.drawable.tit_logo);
					getSupportActionBar().setDisplayUseLogoEnabled(true);
					getSupportActionBar().setDisplayShowHomeEnabled(true);
				} else {
					/**
					 * 홈버튼보이고 - 버튼이미지
					 */
					getSupportActionBar().setLogo(R.drawable.btn_home_64x64);
					getSupportActionBar().setIcon(R.drawable.btn_home_64x64);
					getSupportActionBar().setDisplayUseLogoEnabled(false);
					getSupportActionBar().setDisplayShowHomeEnabled(false);
				}
			}

			// 공유기능변경 (> API11)
			// if (menu != null) {
			// MenuItem item = menu.findItem(R.id.menu_share);
			// if (item != null) {
			// Intent intent = getCurrentFragment().openACTIONSHARE(
			// getCurrentFragment().KP_nnnn.getInfo(), getCurrentFragment().getList(), false);
			// ShareCompat.IntentBuilder b = ShareCompat.IntentBuilder.from(this);
			// String subject = intent.getStringExtra(Intent.EXTRA_SUBJECT);
			// String text = intent.getStringExtra(Intent.EXTRA_TEXT);
			// b.setSubject(subject);
			// b.setType("text/plain").setText(text);
			// ShareCompat.configureMenuItem(item, b);
			// MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
			// item.setIcon(R.drawable.ic_share);
			// }
			// }

		} catch (Exception e) {

			e.printStackTrace();
		}

		String m1 = "";
		String m2 = "";

		if (mList != null) {
			m1 = mList.getValue("m1");
			m2 = mList.getValue("m2");
		}

		if (getCurrentFragment() != null) {
			m1 = getCurrentFragment().p_m1;
			m2 = getCurrentFragment().p_m2;
		}

		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "p_m1:" + m1 + "p_m2:" + m2);

		// 새로고침
		setShowAsAction(R.id.menu_refresh, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
		setActionMenuItemVisible(R.id.menu_refresh, false);
		// 검색
		setShowAsAction(R.id.menu_search, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
		setActionMenuItemVisible(R.id.menu_search, true);
		// 오디션(개최)
		setShowAsAction(R.id.menu_audition_open, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
		setActionMenuItemVisible(R.id.menu_audition_open, false);
		// Feel작성
		setShowAsAction(R.id.menu_comment, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
		setActionMenuItemVisible(R.id.menu_comment, getApp().isLoginUser());

		if (!TextUtil.isEmpty(m1) && !TextUtil.isEmpty(m2)) {
			// 오디션화면인경우
			if (m1.contains("AUDITION") || m2.contains("AUDITION")) {
				// 오디션(개최)
				setActionMenuItemVisible(R.id.menu_audition_open, getApp().isLoginUser());
				// Feel작성
				setActionMenuItemVisible(R.id.menu_comment, false);
			}
		}

		if (_IKaraoke.APP_PACKAGE_NAME_ONSPOT.equalsIgnoreCase(getPackageName())) {
			setActionMenuItemVisible(R.id.menu_audition_open, false);
		}

	}

	/**
	 * 각화면별 타이틀 텍스트/이미지 처리
	 */
	@Override
	final public void setTitle(CharSequence title) {


		_BaseFragment fragment = getCurrentFragment();

		if (fragment != null) {
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + title + "[" + fragment.p_m1 + "][" + fragment.p_m2 + "]");
		} else {
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + title);
		}

		if (_IKaraoke.DEBUG) {
			TextView debug = (TextView) findViewById(R.id.debug);

			String value = (String) title;

			if (getApp().KPparam != null) {
				value += "\n" + getApp().KPparam.info;
			}

			if (debug != null) {
				debug.setVisibility(View.VISIBLE);
				debug.setText(value);
				// WidgetUtils.setTypeFaceBold(tv);
				// WidgetUtils.setTextViewMarquee(tv);
			}
		} else {
			// 야도맊는다.
			Log2.setEnable(false);
		}

		String text = "";

		if (fragment != null && !TextUtil.isEmpty(fragment.p_m2)) {
			// 타이틀이미지처리
			if (fragment.p_m2.contains("POPLIST")) {
				// 팝송리스트
				text = getString(R.string.menu_popsong);
			} else if (fragment.p_m2.equalsIgnoreCase(getString(R.string.M2_LOGIN))) {
				// 로그인
				text = getString(R.string.menu_login);
			} else if (fragment.p_m2.equalsIgnoreCase(getString(R.string.M2_MENU_TICKET))) {
				// 이용권
				text = getString(R.string.menu_ticket);
			} else if (fragment.p_m2.equalsIgnoreCase(getString(R.string.M2_SHOP_TICKET))) {
				// Shop(이용권)
				text = getString(R.string.menu_ticket);
			} else if (fragment.p_m2.equalsIgnoreCase(getString(R.string.M2_SHOP_CHARGE))) {
				// Shop(충전)
				text = getString(R.string.menu_ticket);
			} else if (fragment.p_m2.contains("AUDITION")) {
				// 오디션
				text = getString(R.string.menu_audition);
			} else if (fragment.p_m2.equalsIgnoreCase(getString(R.string.M2_MENU_SETTING))) {
				// 설정
				text = getString(R.string.menu_setting);
			} else if (fragment.p_m2.equalsIgnoreCase(getString(R.string.M2_MENU_NOTICE))) {
				// 공지사항
				text = getString(R.string.menu_information);
			} else if (fragment.p_m2.equalsIgnoreCase(getString(R.string.M2_MENU_SIREN))) {
				// 신고하기
				text = getString(R.string.menu_siren);
			} else if (fragment.p_m2.equalsIgnoreCase(getString(R.string.M2_MENU_FEEL))) {
				// FEEL
				text = getString(R.string.menu_feel);
			} else if (fragment.p_m2.equalsIgnoreCase(getString(R.string.M2_FEEL_LIST))) {
				// FEEL
				text = getString(R.string.menu_feel);
			} else if (fragment.p_m2.equalsIgnoreCase(getString(R.string.M2_FEEL_OURLIST))) {
				// FEEL
				text = getString(R.string.menu_feel);
			} else if (fragment.p_m2.equalsIgnoreCase(getString(R.string.M2_MENU_INFO_FAQ))) {
				// FAQ
				text = getString(R.string.menu_faq);
			} else if (fragment.p_m2.equalsIgnoreCase(getString(R.string.M2_MENU_INFO_QNA))) {
				// QnA
				text = getString(R.string.menu_qna);
			} else if (fragment.p_m2.equalsIgnoreCase(getString(R.string.M2_MY_REC))) {
				// 나의녹음곡
				text = getString(R.string.menu_recording);
			} else if (fragment.p_m2.equalsIgnoreCase(getString(R.string.M2_FOLLOWER_LIST))) {
				// 팔로워목록
				text = getString(R.string.menu_follower);
			} else if (fragment.p_m2.equalsIgnoreCase(getString(R.string.M2_FOLLOWING_LIST))) {
				// 팔로잉목록
				text = getString(R.string.menu_following);
			} else if (fragment.p_m2.equalsIgnoreCase(getString(R.string.M2_VISITOR_LIST))) {
				// 방명록목록
				text = getString(R.string.menu_visitor);
			} else if (fragment.p_m2.equalsIgnoreCase(getString(R.string.M2_MESSAGE_LIST))) {
				// 쪽지목록
				text = getString(R.string.menu_massage);
			} else if (fragment.p_m2.equalsIgnoreCase(getString(R.string.M2_MESSAGE_SEND))) {
				// 쪽지보내기
				text = getString(R.string.menu_massage);
			} else if (fragment.p_m2.equalsIgnoreCase(getString(R.string.M2_MESSAGE_SETTING))) {
				// 쪽지설정
				text = getString(R.string.menu_massage);
			} else if (fragment.p_m2.equalsIgnoreCase(getString(R.string.M2_SING_MYSING))) {
				// 애창곡
				text = getString(R.string.menu_favoritesong);
			} else if (fragment.p_m2.equalsIgnoreCase(getString(R.string.M2_SING_PLAY))) {
				// 반주곡재생
				text = getString(R.string.menu_sing);
			} else if (fragment.p_m2.equalsIgnoreCase(getString(R.string.M2_LISTEN_PLAY))) {
				// 녹음곡재생
				text = getString(R.string.menu_listen);
			} else {
			}

		}

		// 타이틀텍스트처리
		if (!TextUtil.isEmpty(title)) {
			text = title.toString();
		}

		if (_IKaraoke.DEBUG || _IKaraoke.APP_API_TEST) {
			String tail = "";
			if (_IKaraoke.DEBUG) {
				tail += " - 디버그";
			}
			if (_IKaraoke.APP_API_TEST) {
				tail += " - API테스트";
			}
			if (_IKaraoke.APP_API_AD_TEST) {
				tail += " - 광고테스트";
			}
			text += tail + " 모드";
		}

		super.setTitle(text);
	}

	/**
	 * 예외종료 - 왜만든거냐~~~
	 */
	@Override
	protected void close() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());


		super.close();
	}

	/**
	 * <pre>
	 * 그냥종료 - 왠만하믄쓰지말자.
	 * 종료시 팝업오픈여부 확인
	 * </pre>
	 * @see BaseAdActivity#finish()
	 */
	@Override
	public void finish() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		super.finish();
	}

	/**
	 * <pre>
	 * TODO
	 * </pre>
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + outState);

		super.onSaveInstanceState(outState);
	}

	/**
	 * <pre>
	 * TODO
	 * </pre>
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + savedInstanceState);

		super.onRestoreInstanceState(savedInstanceState);
	}

	/**
	 * 백버튼처리<br>
	 */
	@Override
	public void onBackPressed() {
		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName());

		try {
			if (getCurrentFragment() != null) {
				if (getCurrentFragment().onBackPressed()) {
					// if (isACTIONMAIN()) {
					// super.onBackPressed();
					// }
					super.onBackPressed();
				}
			} else {
				// if (isACTIONMAIN()) {
				// super.onBackPressed();
				// }
				super.onBackPressed();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public void release() {
		super.release();
		try {
			mContextFragment = null;
			if (KP_nnnn != null) {
				KP_nnnn.release();
			}
			KP_nnnn = null;
			mInfo = null;
			mList = null;
			mStartLoadInc = null;
			mStartLoading = null;
			mStartLoadAni = null;
			mAleartDialogBuilder = null;
			mProgressDialog = null;
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	protected void finalize() throws Throwable {

		super.finalize();
	}

}
