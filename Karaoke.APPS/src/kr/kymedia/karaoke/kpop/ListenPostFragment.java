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
 * filename	:	ListenPostFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ ListenPostFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke._IKaraoke.LOGIN;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.KPnnnn;
import kr.kymedia.karaoke.api.KPnnnn.MEDIAERROR;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.BaseImageFragment;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.util.TextUtil;

/**
 * 
 * TODO NOTE:<br>
 * 녹음곡업로드
 * 
 * @author isyoon
 * @since 2012. 7. 30.
 * @version 1.0
 */

public class ListenPostFragment extends BaseImageFragment {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	/**
	 * KP_2018 댓글/신고 등록/수정/삭제 FEEL 게시물/녹음곡 통합 댓글/신고 등록 KP_2015 대체
	 */
	KPnnnn KP_2018;

	/**
	 * KP_1014 1일 녹음곡 최대 업로드 확인 <br>
	 */
	KPnnnn KP_1014;

	String mRecordPath = "";
	MediaPlayer mp = new MediaPlayer();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_listen_post, container, false);

		onCreateView();

		return mRootView;
	}

	@Override
	protected void start() {

		super.start();

		if (!isLoginUser()) {
			Bundle args = new Bundle();
			args.putString("message", getString(R.string.errmsg_login));
			getBaseActivity().showDialog2(DIALOG_ALERT_NOLOGIN_YESNO, args);
		}

		View v = null;

		v = findViewById(R.id.rec);
		if (v != null) {
			v.setVisibility(View.GONE);
		}

		v = findViewById(R.id.up);
		if (v != null) {
			v.setVisibility(View.GONE);
		}

		v = findViewById(R.id.show);
		if (v != null) {
			v.setVisibility(View.GONE);
		}

		KPItem list = getList();
		KPItem info = getInfo();

		prepareRecordFile(info, list);
	}

	private boolean record_local = false;

	/**
	 * 파일업로드준비<br>
	 * 녹음파일 -> "/sdcard/karaoke"폴더로 이동<br>
	 * 녹음곡오류의심 - 일시보류<br>
	 */
	void prepareRecordFile(KPItem info, KPItem list) {
		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName());

		// startLoading(__CLASSNAME__, getMethodName());

		// 데이터확인
		if (list == null) {
			stopLoading(__CLASSNAME__, getMethodName());
			String message = getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			getBaseActivity().showAlertDialog(getString(R.string.alert_title_confirm), message, getString(R.string.btn_title_confirm), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					close();
				}
			}, null, null, true, new DialogInterface.OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {

					close();
				}
			});
			return;
		}

		String song_id = list.getValue("song_id");
		String record_id = list.getValue("record_id");
		mRecordPath = list.getValue("record_path");

		if (TextUtil.isEmpty(mRecordPath)) {
			mRecordPath = _IKaraoke.RECORD_PATH + "/" + song_id + "." + _IKaraoke.RECFILE_EXT;
		}

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + mRecordPath);

		String title = "";
		String nickname = "";
		String feel_title = "";

		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "info - " + info.toString(2));
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "list - " + list.toString(2));

		View v = null;

		v = findViewById(R.id.upload_title);
		title = list.getValue("title");
		putValue(v, title);

		v = findViewById(R.id.name);
		nickname = list.getValue(LOGIN.KEY_NICKNAME);
		putValue(v, nickname);

		v = findViewById(R.id.title);
		feel_title = list.getValue("feel_title");
		// 곡번호삭제처리
		if (feel_title != null) {
			feel_title = feel_title.replace((new String("[" + song_id + "]")), "");
		}
		putValue(v, feel_title);

		// 곡번호확인
		if (TextUtil.isEmpty(song_id) || !TextUtils.isDigitsOnly(song_id)) {
			stopLoading(__CLASSNAME__, getMethodName());
			String message = getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			getBaseActivity().showAlertDialog(getString(R.string.alert_title_confirm), message, getString(R.string.btn_title_confirm), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					close();
				}
			}, null, null, true, new DialogInterface.OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {

					close();
				}
			});
			return;
		}

		// 파일사이즈확인
		// 파일이동보류
		try {

			long len = -1;
			boolean exist = false;
			File file = new File(mRecordPath);

			if (file != null) {
				exist = file.exists();
			}

			if (file != null && exist) {
				len = file.length();
			}

			if (!exist) {
				throw new Exception("File Not Exists Error");
			}

			if (len == 0) {
				throw new Exception("File Not Record Error");
			}

			if (record_local) {
				// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
				// Date date = new Date(System.currentTimeMillis());
				// String Time = dateFormat.format(date);
				//
				// String message = getMethodName() + "\n" + mRecordPath;
				// if (_IKaraoke.DEBUG)
				// Log.e(__CLASSNAME__, message);
				//
				// String src = mRecordPath;
				// File fsrc = new File(src);
				//
				// String dst = KARAOKE_RECORD_PATH + File.separator + feel + "-" + nickname + "-" + Time + "."
				// + RECFILE_EXT;
				// if (_IKaraoke.DEBUG)
				// Log.e(__CLASSNAME__, dst);
				// File fdst = new File(dst);
				//
				// //startLoading(__CLASSNAME__, getMethodName());
				//
				// if (fdst.exists()) {
				// fdst.delete();
				// fdst = new File(dst);
				// }
				//
				// long size = 0;
				// if (size == EnvironmentUtils.moveFile(fsrc, fdst)) {
				// mRecordPath = dst;
				// } else {
				// throw new Exception("Moving Record File Size Errir");
				// }
				//
				// stopLoading(__CLASSNAME__, getMethodName());
				//
				// message = getMethodName();
				// message += "\n" + "[!!!녹윽곡파일확인!!!]";
				// message += "\n" + size;
				// message += "\n" + src;
				// message += "\n" + dst;
				// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, message);
				//
				// mRecordPath = dst;
				//
				// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, mRecordPath);
			}
		} catch (Exception e) {

			stopLoading(__CLASSNAME__, getMethodName());
			e.printStackTrace();
			String text = getString(R.string.error_alert_record) + " :" + Log2.getStackTraceString(e);
			getApp().popupToast(text, Toast.LENGTH_SHORT);
			String message = getString(R.string.warning_record_error) + "\n" + Log2.getStackTraceString(e);
			getBaseActivity().showAlertDialog(getString(R.string.alert_title_confirm), message, getString(R.string.btn_title_confirm), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					close();
				}
			}, null, null, true, new DialogInterface.OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {

					close();
				}
			});
			String where = __CLASSNAME__ + ".prepareRecordFile(...)";

			// 재생/녹음/업로드-오류보고
			KP_9999(getApp().p_mid, p_m1, p_m2, MEDIAERROR.TYPE.MEDIAUPLOAD, MEDIAERROR.LEVEL.E, song_id, record_id, where, -1, -1, "null", Log2.getStackTraceString(e), info, list,
					message);
		}

	}

	@Override
	public void onClick(View v) {
		String vn = getResourceEntryName(v.getId());
		String cn = v.getClass().getSimpleName();
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, getMethodName() + vn + ", " + cn);

		super.onClick(v);

		KPItem list = getList();

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		if (v.getId() == R.id.up || v.getId() == R.id.upload_save) {
			// uploadRecord();
			getBaseActivity().showAlertDialog(getString(R.string.alert_title_confirm), getString(R.string.warning_record_upload_confirm), getString(R.string.btn_title_yes),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialoginterface, int i) {

							uploadRecord();
						}
					}, getString(R.string.btn_title_no), new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialoginterface, int i) {

							close();
						}
					}, true, new DialogInterface.OnCancelListener() {

						@Override
						public void onCancel(DialogInterface dialog) {

							close();
						}
					});
		} else if (v.getId() == R.id.upload_close || v.getId() == R.id.upload_cancel) {
			close();
		} else if (v.getId() == R.id.upload_play) {
			playRecord();
		} else {
		}
	}

	@Override
	public void KP_init() {

		super.KP_init();

		KP_2018 = KP_init(mContext, KP_2018);

		KP_1014 = KP_init(mContext, KP_1014);

	}

	@Override
	public void KP_nnnn() {

		super.KP_nnnn();

		// startLoading(__CLASSNAME__, getMethodName());
		KP_1014.KP_1014(getApp().p_mid, "", "");
	}

	/**
	 * 업로드중복차단
	 */
	boolean mUploadRecord = false;

	/**
	 * 업로드재시도
	 */
	void retryUploadRecord(String message) {
		try {
			getBaseActivity().showAlertDialog(getString(R.string.alert_title_upload_error), message, getString(R.string.btn_title_retry), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					mUploadRecord = false;
					uploadRecord();
				}
			}, getString(R.string.btn_title_cancel), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					close();
				}
			}, false, null);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 중복차단기능추가
	 */
	void uploadRecord() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + mUploadRecord);

		// 업로드차단
		if (mUploadRecord) {
			return;
		}
		mUploadRecord = true;

		KPItem info = getInfo();
		KPItem list = getList();

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		if (info != null) {
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "info - " + info.toString(2));
		}
		if (list != null) {
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "list - " + list.toString(2));
		}

		String song_id = getList().getValue("song_id");
		String audition_id = getList().getValue("audition_id");
		if (TextUtil.isEmpty(audition_id)) {
			audition_id = getList().getValue("id");
		}

		File file = new File(mRecordPath);

		if (!file.exists()) {
			getApp().popupToast(R.string.alert_title_upload_error, Toast.LENGTH_SHORT);
			return;
		}

		// startLoading(__CLASSNAME__, getMethodName());

		getApp().popupToast(R.string.alert_title_upload_running, Toast.LENGTH_SHORT);

		getBaseActivity().showDialog2(DIALOG_PROGRESS_SONG_UPLOAD, null);

		try {
			long size = file.length();
			// KP_1011 파일업로드 (폐기->유지)
			// KP_nnnn.KP_1011(getApp().p_mid, p_m1, p_m1, song_id, "RECORD", audition_id, mRecordPath);
			KP_nnnn.KP_1015(getApp().p_mid, p_m1, p_m1, song_id, "RECORD", audition_id, mRecordPath, size);
		} catch (Exception e) {

			e.printStackTrace();
			String text = getString(R.string.error_alert_upload) + " :" + Log2.getStackTraceString(e);
			getApp().popupToast(text, Toast.LENGTH_SHORT);
			String message = getString(R.string.errmsg_network_data) + "\n" + text;
			retryUploadRecord(message);
			return;
		}
	}

	public void playRecord() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + mRecordPath);
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + TextUtil.getMimeTypeFromFile(mRecordPath));

		Intent intent = new Intent();
		intent.setAction(android.content.Intent.ACTION_VIEW);
		// String fileExtend = TextUtil.getFileExtension(mRecordPath);
		// if (fileExtend.equalsIgnoreCase("mp3") || fileExtend.equalsIgnoreCase("m4a")) {
		// intent.setDataAndType(Uri.parse("file://" + mRecordPath), "audio/*");
		// } else if (fileExtend.equalsIgnoreCase("mp4") || fileExtend.equalsIgnoreCase("3gp")) {
		// intent.setDataAndType(Uri.parse("file://" + mRecordPath), "video/*");
		// }
		intent.setDataAndType(Uri.parse("file://" + mRecordPath), TextUtil.getMimeTypeFromFile(mRecordPath));
		if (intent != null) {
			startActivity(intent);
		}

	}

	@Override
	public void onKPnnnnResult(int what, String p_opcode, String r_code, String r_message, String r_info) {

		super.onKPnnnnResult(what, p_opcode, r_code, r_message, r_info);

		try {
			if (what == _IKaraoke.STATE_DATA_QUERY_START) {
				return;
			}

			if (("KP_1015").equalsIgnoreCase(p_opcode)) {

				try {
					getBaseActivity().dismissDialog2(DIALOG_PROGRESS_SONG_UPLOAD);
					getBaseActivity().removeDialog2(DIALOG_PROGRESS_SONG_UPLOAD);
				} catch (Exception e) {

					// e.printStackTrace();
				}

				// KPnnnnError(what, p_opcode, r_code, r_message, r_info);

				if (("00000").equalsIgnoreCase(r_code)) {
					// getApp().popupToast(R.string.alert_title_upload_complete, Toast.LENGTH_SHORT);

					// KP_1011 업로드결과확인
					File file = new File(mRecordPath);
					if (file != null && file.exists()) {
						file.delete();
					}

					KPItem info = KP_nnnn.getInfo();
					if (info != null) {
						String record_id = info.getValue("record_id");
						KP_2018(record_id);
					}
					// 녹음곡 업로드완료
					openMyRecord(info);
					// 공유하기
					// openACTIONSHARE(KP_nnnn.getInfo(), getList(), true);
				} else {
					// 녹음곡 업로드실패
					// close();
					// String title = getString(R.string.alert_title_error);
					String message = getString(R.string.errmsg_network_data);
					message += "\n(" + p_opcode + ":" + r_code + ")";
					if (_IKaraoke.DEBUG) {
						message += "\n\n[디버그정보]\n" + r_message;
					}
					retryUploadRecord(message);

					String song_id = getList().getValue("song_id");
					String record_id = getList().getValue("record_id");
					KPItem info = KP_nnnn.getInfo();
					KPItem list = KP_nnnn.getList(0);

					// 재생/녹음/업로드-오류보고
					KP_9999(getApp().p_mid, p_m1, p_m2, MEDIAERROR.TYPE.MEDIAUPLOAD, MEDIAERROR.LEVEL.E, song_id, record_id, __CLASSNAME__ + ":" + p_opcode, what, Integer.parseInt(r_code),
							"null", r_message, info, list, message);
				}

				return;
			} else if (("KP_1014").equalsIgnoreCase(p_opcode)) {
				// KP_1014 1일 녹음곡 최대 업로드 확인
				KPItem list = KP_1014.getList(0);
				String is_upload = list.getValue("is_upload");
				View upload = findViewById(R.id.upload_buttons);
				View close = findViewById(R.id.upload_close);
				if (("Y").equalsIgnoreCase(is_upload)) {
					if (upload != null) {
						upload.setVisibility(View.VISIBLE);
					}
					if (close != null) {
						close.setVisibility(View.GONE);
					}
				} else {
					if (upload != null) {
						upload.setVisibility(View.GONE);
					}
					if (close != null) {
						close.setVisibility(View.VISIBLE);
					}
				}
			} else if (("KP_1012").equalsIgnoreCase(p_opcode)) {
				// KP_1012 재생시간 기록
				return;
			}
		} catch (Exception e) {

			e.printStackTrace();
			// 재생/녹음/업로드-오류보고
			KP_9999(getApp().p_mid, p_m1, p_m2, MEDIAERROR.TYPE.MEDIAUPLOAD, MEDIAERROR.LEVEL.E, "", "", __CLASSNAME__ + ":" + p_opcode, what, Integer.parseInt(r_code), "null",
					r_message, null, null, Log2.getStackTraceString(e));
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		close();
	}

	/**
	 * KP_2018 댓글/신고 등록/수정/삭제 FEEL 게시물/녹음곡 통합 댓글/신고 등록 KP_2015 대체
	 */
	protected void KP_2018(String record_id) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + record_id);

		if (TextUtil.isEmpty(record_id)) {
			return;
		}

		EditText t = (EditText) findViewById(R.id.upload_text);
		String comment = t.getText().toString();

		if (TextUtil.isEmpty(comment)) {
			return;
		}

		getApp().hideSoftInput(t);

		KP_2018.KP_2018(getApp().p_mid, p_m1, p_m2, record_id, "ADD", "", "", comment, "");
	}

	@Override
	public void close() {

		super.close();

		boolean ret = false;
		// 녹음곡저장파일삭제
		try {
			File file = new File(mRecordPath);
			if (file.exists()) {
				ret = file.delete();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + ":" + ret + " - " + mRecordPath);
	}

}
