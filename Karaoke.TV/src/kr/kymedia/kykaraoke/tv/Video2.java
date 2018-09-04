package kr.kymedia.kykaraoke.tv;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.MediaStore.Video;
import android.util.Log;
import android.view.KeyEvent;

import kr.kymedia.kykaraoke.api._Const;
import kr.kymedia.kykaraoke.tv.app._Activity;
import kr.kymedia.kykaraoke.tv.play.MusicVideoView;

/**
 * TODO<br>
 * <p/>
 * <pre></pre>
 * <p/>
 * Copy of {@link Video}
 *
 * @author isyoon
 * @version 1.0
 * @since 2015. 1. 28.
 */
class Video2 extends _Activity implements _Const {
	final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	private String _toString() {

		return (BuildConfig.DEBUG ? __CLASSNAME__ : getClass().getSimpleName()) + '@' + Integer.toHexString(hashCode());
	}

	@Override
	protected String getMethodName() {
		String name = Thread.currentThread().getStackTrace()[3].getMethodName();
		// int line = Thread.currentThread().getStackTrace()[3].getLineNumber();
		// name = String.format("line:%d - %s() ", line, name);
		name += "() ";
		return name;
	}

	// int m_iDisplayWidth;
	// int m_iDisplayHeight;

	// @Deprecated
	// public MusicVideoView mBlankVideo;
	public MusicVideoView mMusicVideo;

	// LinearLayout mLayoutContainer2 = null;
	// LinearLayout mLayoutBG1 = null;
	// LinearLayout mLayoutBG2 = null;

	// Animation mAnimShow = null;
	// Animation mAnimHide = null;

	// boolean m_bPlayMenuBackground = false;

	boolean m_bIsRedraw = false;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video2);

		// Display display = getWindowManager().getDefaultDisplay();
		// Point displaySize = new Point();
		// display.getRealSize(displaySize);
		// m_iDisplayWidth = displaySize.x;
		// m_iDisplayHeight = displaySize.y;
		// mBackVideo = new BackgroundVideoView(this);
		// mBlankVideo = new BackgroundVideoView(this);
	}

	/**
	 * <pre>
	 * 어플에서 별도처리 구현이 있는경우 호출하지않는다.
	 * </pre>
	 */
	public void start() {
		Log.e(_toString() + TAG_VIDEO, "start()");
		startMainActivity(null);
	}

	protected boolean isFinishing = false;

	@Override
	public void finish() {
		isFinishing = true;
		super.finish();
	}

	@Override
	public boolean isFinishing() {
		return super.isFinishing();
	}

	@Override
	protected void onDestroy() {
		Log.e(_toString() + TAG_VIDEO, "onDestroy()" + " :isFinishing:" + isFinishing + ":isFinishing()" + isFinishing());
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		Log.e(_toString() + TAG_VIDEO, "onResume()" + " :isFinishing:" + isFinishing + ":isFinishing()" + isFinishing());
		super.onResume();
		//if (isFinishing() || isFinishing)
		{
			start();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (isFinishing() || isFinishing) {
			Log.w(_toString() + TAG_VIDEO, "onKeyDown()" + " :isFinishing:" + isFinishing + ":isFinishing()" + isFinishing() + ":" + keyCode + ", " + event);
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	public boolean isPlaying() {
		if (mMusicVideo != null) {
			if (mMusicVideo.isPlaying()) {
				return true;
			}
		}

		return false;
	}

	public void stopMusicVideo(String url, int next) {
		if (mMusicVideo == null) {
			return;
		}
	}

	public void pauseBackgroundVideo() {
		mMusicVideo.pause();
	}

	@Deprecated
	public void stopBlankVideo() {
	}

	public void ShowMessageOk(int type, String title, String message) {
		if (get_Application().getMainActivity() != null) {
			get_Application().getMainActivity().ShowMessageOk(type, title, message);
		} else {
			doAlert(title, message, (type == POPUP_EXIT));
		}
	}
}