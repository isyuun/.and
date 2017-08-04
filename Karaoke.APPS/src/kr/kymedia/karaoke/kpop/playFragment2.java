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
 * 2012 All rights (c)KYmedia Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.KPOP
 * filename	:	PlayFragment2.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop.app
 *    |_ PlayFragment2.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.KPnnnn;
import kr.kymedia.karaoke.api.KPnnnn.MEDIAERROR.LEVEL;
import kr.kymedia.karaoke.api.KPnnnn.MEDIAERROR.TYPE;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.record.CameraPreview2;
import kr.kymedia.karaoke.record.SongRecorder5;
import kr.kymedia.karaoke.widget.util.WidgetUtils;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * 
 * TODO<br>
 * NOTE:<br>
 * 녹화기능(보류)
 * 
 * @author isyoon
 * @since 2013. 8. 19.
 * @version 1.0
 */
@Deprecated
class playFragment2 extends playFragment implements AdapterView.OnItemSelectedListener {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	private RelativeLayout mLayout;
	private CameraPreview2 mPreview;
	private ArrayAdapter<String> mAdapter;
	private RelativeLayout view1;

	Spinner mCameraSizing;
	private CheckBox isCamera;
	private CheckBox isCameraRecording;
	private int mFacing = Camera.CameraInfo.CAMERA_FACING_FRONT;

	@Override
	protected void onActivityCreated() {
		if (_IKaraoke.DEBUG) Log2.d("Camera", getMethodName());


		super.onActivityCreated();

		RelativeLayout.LayoutParams params = null;

		RelativeLayout adbanner = (RelativeLayout) getBaseActivity().findViewById(R.id.adbase);
		params = (RelativeLayout.LayoutParams) adbanner.getLayoutParams();
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, -1);
		params.setMargins(0, 0, 0, (int) getResources().getDimension(R.dimen.height_bottom_play_bar));
		adbanner.setLayoutParams(params);

		FrameLayout fragment1 = (FrameLayout) getBaseActivity().findViewById(R.id.fragment1);
		params = (RelativeLayout.LayoutParams) fragment1.getLayoutParams();
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, -1);
		params.addRule(RelativeLayout.ABOVE, 0);
		fragment1.setLayoutParams(params);

		view1 = (RelativeLayout) findViewById(R.id.view1);

		isCamera = (CheckBox) findViewById(R.id.btn_camera_switch);
		isCameraRecording = (CheckBox) findViewById(R.id.btn_camera_recording);
		mLayout = (RelativeLayout) findViewById(R.id.camera);

		mCameraSizing = (Spinner) findViewById(R.id.spn_camera_sizing);
	}

	private void setPlayerTouch() {
		if (view1 != null) {
			WidgetUtils.setOnClickListener(mContext, view1, null, false);
			WidgetUtils.setOnTouchListener(mContext, view1, new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {

					String vn = getResourceEntryName(v.getId());
					String cn = v.getClass().getSimpleName();
					if (_IKaraoke.DEBUG)
						Log2.e(__CLASSNAME__, getMethodName() + vn + ", " + cn + "\n" + event);
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						showPlayerBar(!isShowPlayerBar);
						break;

					default:
						break;
					}
					return false;
				}
			});
		}
	}

	@Override
	protected void player() {

		super.player();

		setPlayerTouch();

	}

	private void createCameraPreview() {
		if (_IKaraoke.DEBUG) Log2.e("Camera", getMethodName() + "[START]");

		// Create an instance of Camera
		try {
			if (!CameraPreview2.checkCameraHardware(mContext)) {
				isCamera.setVisibility(View.GONE);
				isCameraRecording.setVisibility(View.GONE);
				mLayout.setVisibility(View.GONE);
				throw new Exception("NO camera");
			}

			// Create our Preview view and set it as the content of our activity.
			mPreview = new CameraPreview2(mContext, CameraPreview2.getCameraId(mFacing),
					CameraPreview2.LayoutMode.NoBlank);

			mLayout.addView(mPreview);

			initCameraSizing();

			setPlayerTouch();

			setCameraSwitch(isCamera.isChecked());

			// showPlayerBar(true);
			post(new Runnable() {

				@Override
				public void run() {

					showPlayerBar(isShowPlayerBar);
				}
			});

		} catch (Exception e) {

			e.printStackTrace();
			getApp().popupToast(Log2.getStackTraceString(e), Toast.LENGTH_LONG);
			Log2.e("Camera", Log2.getStackTraceString(e));
		}

		if (_IKaraoke.DEBUG) Log2.e("Camera", getMethodName() + "[END]");
	}

	private void removeCameraPreview() {
		if (_IKaraoke.DEBUG) Log2.e("Camera", getMethodName());

		if (mPreview == null) {
			return;
		}

		mPreview.stop();
		mLayout.removeView(mPreview);
		mPreview = null;
	}

	@Override
	public void onResume() {
		if (_IKaraoke.DEBUG) Log2.d("Camera", getMethodName());


		super.onResume();

		if (mPreview == null) {
			createCameraPreview();
		}

	}

	@Override
	public void onPause() {

		super.onPause();

		if (isCameraRecording.isChecked()) {
			stopRecord(true);
		}

		if (mPreview != null) {
			if (isRecording() && isCameraRecording.isChecked()) {
				removeCameraPreview();
			}
		}
	}

	@Override
	protected void prepareRecord() throws Exception {


		if (isCamera.isChecked()) {
			boolean compressed = true;
			// Bundle bundle = getBaseActivity().getIntent().getExtras();
			// if (bundle != null) {
			// compressed = bundle.getBoolean(SONGPLAYER_RECORDCOMPRESS);
			// }

			String id = getList().getValue("song_id");
			mRecord = new SongRecorder5(id, compressed, mPreview);

			mRecord.setOnErrorListener(this);
			mRecord.setOnInfoListener(this);
		} else {
			super.prepareRecord();
		}
	}

	@Override
	public void setRecord(boolean record, boolean toast) {
		if (_IKaraoke.DEBUG) Log2.e("Camera", getMethodName());


		super.setRecord(record, toast);

		if (record && isCamera.isChecked()) {
			isCameraRecording.setChecked(record);
		} else {
			isCameraRecording.setChecked(false);
		}

		setCameraSwitch(isCamera.isChecked());

	}

	@Override
	public void onClick(View v) {
		String vn = getResourceEntryName(v.getId());
		String cn = v.getClass().getSimpleName();
		if (_IKaraoke.DEBUG) Log2.d("Camera", getMethodName() + vn + ", " + cn);


		super.onClick(v);

		int id = v.getId();
		if (id == R.id.btn_camera_switch) {
			if (isCameraRecording.isChecked()) {
				getApp().popupToast(R.string.warning_onrecord_slide, Toast.LENGTH_SHORT);
			} else {
				setCameraSwitch(((CheckBox) v).isChecked());
			}
		} else if (id == R.id.btn_camera_rotate) {
			if (isCameraRecording.isChecked()) {
				getApp().popupToast(R.string.warning_onrecord_slide, Toast.LENGTH_SHORT);
			} else {
				setCameraRotate(((CheckBox) v).isChecked());
			}
		} else if (id == R.id.btn_camera_facing) {
			if (isCameraRecording.isChecked()) {
				getApp().popupToast(R.string.warning_onrecord_slide, Toast.LENGTH_SHORT);
			} else {
				setCameraFacing(((CheckBox) v).isChecked());
			}
		} else if (id == R.id.btn_camera_sizing) {
			if (isCameraRecording.isChecked()) {
				getApp().popupToast(R.string.warning_onrecord_slide, Toast.LENGTH_SHORT);
			} else {
				// openCameraSizing();
			}
		} else if (id == R.id.btn_camera_recording) {
			if (((CheckBox) v).isChecked()) {
				restart();
			} else {
				stopRecord(true);
			}
		} else {
		}
	}

	@Override
	public boolean restart() {

		boolean ret = false;

		// startLoading(__CLASSNAME__, getMethodName());

		ret = super.restart();

		postDelayed(new Runnable() {

			@Override
			public void run() {

				stopLoading(__CLASSNAME__, getMethodName());
			}
		}, 100);

		return ret;
	}

	public void setCameraSwitch(boolean camera) {
		if (_IKaraoke.DEBUG) Log2.e("Camera", getMethodName() + camera);

		if (camera && mPreview == null) {
			createCameraPreview();
		}

		if (mPreview == null) {
			return;
		}

		if (camera) {
			mLayout.setVisibility(View.VISIBLE);
			findViewById(R.id.btn_camera_rotate).setVisibility(View.VISIBLE);
			if (Camera.getNumberOfCameras() > 1) {
				findViewById(R.id.btn_camera_facing).setVisibility(View.VISIBLE);
			}
			findViewById(R.id.btn_camera_sizing).setVisibility(View.VISIBLE);
			mCameraSizing.setVisibility(View.VISIBLE);
			isCameraRecording.setVisibility(View.VISIBLE);
			// if (getPlayer() != null) {
			// getPlayer().setBackgroundTransparent(true);
			// }
		} else {
			mLayout.setVisibility(View.INVISIBLE);
			findViewById(R.id.btn_camera_rotate).setVisibility(View.GONE);
			findViewById(R.id.btn_camera_facing).setVisibility(View.GONE);
			findViewById(R.id.btn_camera_sizing).setVisibility(View.GONE);
			mCameraSizing.setVisibility(View.GONE);
			isCameraRecording.setVisibility(View.GONE);
			// if (getPlayer() != null) {
			// getPlayer().setBackgroundTransparent(false);
			// }
		}

		// 녹화중카메라조작금지
		if (isCameraRecording.isChecked()) {
			findViewById(R.id.btn_camera_rotate).setVisibility(View.GONE);
			findViewById(R.id.btn_camera_facing).setVisibility(View.GONE);
			findViewById(R.id.btn_camera_sizing).setVisibility(View.GONE);
			mCameraSizing.setVisibility(View.GONE);
			isCameraRecording.setVisibility(View.VISIBLE);
		}

		showCameraPreview(camera);

		if (mCameraSizing != null) {
			CamcorderProfile profile = mPreview.getCamorderProfile();
			ArrayList<CamcorderProfile> profiles = mPreview.getCamorderProfiles();
			if (profiles != null && profile != null) {
				int position = profiles.indexOf(profile);
				mCameraSizing.setSelection(position);
			}
		}
	}

	public void showCameraPreview(boolean camera) {
		if (_IKaraoke.DEBUG) Log2.e("Camera", getMethodName() + camera);

		if (mPreview == null) {
			return;
		}

		int w = mLayout.getWidth();

		if (_IKaraoke.IS_ABOVE_HONEYCOMB) {
			if (camera) {
				mPreview.setX(0.0f);
			} else {
				mPreview.setX(-1.0f * w);
			}
		} else {
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mPreview.getLayoutParams();
			if (camera) {
				params.leftMargin = 0;
			} else {
				params.leftMargin = -1 * w;
			}
			mPreview.setLayoutParams(params);
		}
	}

	/**
	 * 
	 * <pre>
	 * TODO
	 * </pre>
	 *
	 * @see #onConfigurationChanged(Configuration)
	 */
	public void setCameraRotate(boolean landscape) {

		int lock = -99999;
		try {
			lock = Settings.System.getInt(mContext.getContentResolver(),
					Settings.System.ACCELEROMETER_ROTATION);
			if (lock == 1) {
				// /rotation is Locked
			} else {
				// /rotation is Locked
			}
		} catch (SettingNotFoundException e) {

			e.printStackTrace();
		}

		int orientation = getResources().getConfiguration().orientation;
		if (_IKaraoke.DEBUG)
			Log2.e("Camera", getMethodName() + ", orientation:" + orientation + ", lock:" + lock
					+ ", landscape:" + landscape);

		int requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

		if (landscape) {
			requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
		} else {
			requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
		}

		showCameraPreview(false);
		setRedraw(true);

		getBaseActivity().setRequestedOrientation(requestedOrientation);

		// if (getBaseActivity() instanceof BaseAdActivity) {
		// ((BaseAdActivity) getBaseActivity()).showAdBanner(false);
		// }

		if (mTimerTick == null) {
			mTimerTick = new Timer();
			mTimerTick.schedule(new TimerTask() {

				@Override
				public void run() {

					post(new Runnable() {

						@Override
						public void run() {

							showCameraPreview(isCamera.isChecked());
							setRedraw(false);
							if (mTimerTick != null) {
								mTimerTick.cancel();
								mTimerTick.purge();
								mTimerTick = null;
							}
						}
					});
				}
			}, 1000);

		}

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		if (_IKaraoke.DEBUG) Log2.d("Camera", getMethodName() + newConfig);

		if (mTimerTick != null) {
			mTimerTick.cancel();
			mTimerTick.purge();
			mTimerTick = null;
		}


		super.onConfigurationChanged(newConfig);

		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// Toast.makeText(getApp(), "landscape", Toast.LENGTH_SHORT).show();
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			// Toast.makeText(getApp(), "portrait", Toast.LENGTH_SHORT).show();
		}

		onConfigurationChanged(newConfig.orientation);

		showCameraPreview(isCamera.isChecked());
		setRedraw(false);

		setCameraSize(mCameraSizing.getSelectedItemPosition());

		// if (getBaseActivity() instanceof BaseAdActivity) {
		// postDelayed(new Runnable() {
		//
		// @Override
		// public void run() {
		//
		// ((BaseAdActivity) getBaseActivity()).showAdBanner(true);
		// }
		// }, 1000);
		// }
	}

	public void onConfigurationChanged(int orientation) {

		if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// Toast.makeText(getApp(), "landscape", Toast.LENGTH_SHORT).show();
			getBaseActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
			getBaseActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
			getBaseActivity().getSupportActionBar().hide();
		} else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
			// Toast.makeText(getApp(), "portrait", Toast.LENGTH_SHORT).show();
			getBaseActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
			getBaseActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
			getBaseActivity().getSupportActionBar().show();
		}

	}

	public void setCameraFacing(boolean front) {
		if (_IKaraoke.DEBUG) Log2.e("Camera", getMethodName() + front);

		if (mPreview == null) {
			return;
		}

		removeCameraPreview();

		if (front) {
			mFacing = Camera.CameraInfo.CAMERA_FACING_FRONT;
		} else {
			mFacing = Camera.CameraInfo.CAMERA_FACING_BACK;
		}

		post(new Runnable() {

			@Override
			public void run() {

				createCameraPreview();
			}
		});
	}

	private void initCameraSizing() {

		// Size[] videos = {
		// mPreview.getCamera().new Size(720, 480),
		// mPreview.getCamera().new Size(640, 480),
		// mPreview.getCamera().new Size(320, 240),
		// //mPreview.getCamera().new Size(176, 144),
		// };

		mAdapter = new ArrayAdapter<String>(mContext, R.layout.simple_spinner_item_camera);
		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		mAdapter.clear();
		// mAdapter.add("Auto");
		List<Camera.Size> sizes = mPreview.getSupportedPreivewSizes();

		if (sizes != null && sizes.size() > 0) {
			int index = 0;
			for (Camera.Size size : sizes) {
				// for (Camera.Size video: videos) {
				// if (size.width == video.width && size.height == video.height) {
				// mAdapter.add(size.width + " x " + size.height);
				// }
				// }
				CamcorderProfile profile = mPreview.getCamorderProfile(index);
				mAdapter.add(CameraPreview2.getQuality(profile.quality) + " : " + size.width + "x"
						+ size.height);
				// mAdapter.add(size.width + " x " + size.height);
				index++;
			}
		}

		mCameraSizing.setAdapter(mAdapter);
		mCameraSizing.setOnItemSelectedListener(this);
	}

	public void setCameraSize(int position) {
		if (_IKaraoke.DEBUG) Log2.e("Camera", getMethodName() + position);

		if (mPreview == null) {
			createCameraPreview();
		}

		if (mPreview == null) {
			return;
		}

		if (mPreview != null) {
			Rect rect = new Rect();
			mLayout.getDrawingRect(rect);
			// if (0 == position) { // "Auto" selected
			// mPreview.surfaceChanged(null, 0, rect.width(), rect.height());
			// } else {
			// mPreview.setPreviewSize(position - 1, rect.width(), rect.height());
			// }
			mPreview.setCameraSize(position, rect.width(), rect.height());
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		String name = getResourceEntryName(view.getId());
		if (_IKaraoke.DEBUG) Log2.d("Camera", getMethodName() + name + "," + position + "," + id);


		if (parent.getId() == R.id.spn_camera_sizing) {
			setCameraSize(position);
		} else {
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

		if (_IKaraoke.DEBUG) Log2.d("Camera", getMethodName());

	}

	@Override
	public void showPlayerBar(boolean show) {

		super.showPlayerBar(show);

		// Animation ani = null;

		int bottom = 0;
		if (show) {
			// ani = AdPOPcornAnimationFactory.CreateBouncingAnimation(0, 0, 200, 0);
			showPlayerBar();
			// 세로일경우만
			if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				getBaseActivity().getSupportActionBar().show();
			}
			bottom = (int) getResources().getDimension(R.dimen.height_bottom_play_bar);
		} else {
			// ani = AdPOPcornAnimationFactory.CreateBouncingAnimation(0, 0, 200, 0);
			hidePlayerBar();
			// 가로일경우만
			if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
				getBaseActivity().getSupportActionBar().hide();
			}
			bottom = 0;
		}

		RelativeLayout.LayoutParams params = null;

		RelativeLayout adbanner = (RelativeLayout) getBaseActivity().findViewById(R.id.adbase);
		params = (RelativeLayout.LayoutParams) adbanner.getLayoutParams();
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, -1);
		params.setMargins(0, 0, 0, bottom);
		adbanner.setLayoutParams(params);

		// final Animation a = ani;
		// post(new Runnable() {
		//
		// @Override
		// public void run() {
		//
		// player_bar.setAnimation(a);
		// }
		// });
	}

	@Override
	protected void openListenPost(KPnnnn KP_xxxx, int index) {

		KPItem list = KP_xxxx.getList(index);
		list.putValue("record_path", mRecord.getPath());

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "\nlist - " + list.toString(2));
		super.openListenPost(KP_xxxx, index);
	}

	@Override
	protected void openListenPost2(KPnnnn KP_xxxx, int index) {

		KPItem list = KP_xxxx.getList(index);
		list.putValue("record_path", mRecord.getPath());

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "\nlist - " + list.toString(2));
		super.openListenPost2(KP_xxxx, index);
	}

	@Override
	protected void shutdownMediaError(TYPE type, LEVEL level) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + type + ", " + level);


		if (getPlayer() != null) {
			getPlayer().event(null, null, null);
		}

		if (mRecord != null) {
			mRecord.setOnErrorListener(null);
			mRecord.setOnInfoListener(null);
		}

		super.shutdownMediaError(type, level);
	}

	@Override
	public void stopRecord(boolean toast) {

		super.stopRecord(toast);
	}

}
