package kr.kymedia.kykaraoke.tv.play;

import android.content.Context;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;

import kr.kymedia.kykaraoke.data._SongData;
import kr.kymedia.kykaraoke.tv.BuildConfig;
import kr.kymedia.kykaraoke.api.IKaraokeTV;
import kr.kymedia.kykaraoke.api._Const;

/**
 * Copy of {@link SongPlayer}
 */
class PlayView2 extends PlayView1 implements _Const {
	private final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	private String _toString() {

		return (BuildConfig.DEBUG ? __CLASSNAME__ : getClass().getSimpleName()) + '@' + Integer.toHexString(hashCode());
	}

	protected String getMethodName() {
		String name = Thread.currentThread().getStackTrace()[3].getMethodName();
		// int line = Thread.currentThread().getStackTrace()[3].getLineNumber();
		// name = String.format("line:%d - %s() ", line, name);
		name += "() ";
		return name;
	}

	public PlayView2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		// if (isInEditMode()) {
		// 	return;
		// }
	}

	public PlayView2(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// if (isInEditMode()) {
		// 	return;
		// }
	}

	public PlayView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		// if (isInEditMode()) {
		// 	return;
		// }
	}

	public PlayView2(Context context) {
		super(context);
		// if (isInEditMode()) {
		// 	return;
		// }
	}

	private _SongData m_data = null;
	private MediaPlayer m_mp = null;
	/**
	 * 가사(다운경로)
	 */
	private String m_lyric = null;
	//private ArrayList<String> m_lyrics = null;
	private final int m_songIdx = 0;
	private int m_imgIdx = 0;
	// private Bitmap m_srcBack = null;
	// private Bitmap m_dstBack = null;
	private long m_time = 0L;

	private PLAY_ENGAGE m_state = PLAY_ENGAGE.PLAY_STOP;

	public void setPlayState(PLAY_ENGAGE state) {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + state);
		this.m_state = state;
	}

	public PLAY_ENGAGE getPlayState() {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + m_state);
		return m_state;
	}

	/**
	 * 자막플레이어
	 */
	private _LyricsPlay mLyricsPlay = null;

	/**
	 * 자막플레이어
	 */
	public _LyricsPlay getLyricsPlay() {
		return mLyricsPlay;
	}

	public boolean getReady() {
		return mLyricsPlay.getLyricsPlayThread().m_bReady;
	}

	public String getSongTitle() {
		String songTitle = m_data.getLyricsInfoTag().strTitle1 + m_data.getLyricsInfoTag().strTitle2;
		songTitle.replace("\n", "");
		return songTitle;
	}

	//public void setLyric(ArrayList<String> lyrics) {
	//	this.m_lyrics = lyrics;
	//	for (int i = 0; i < m_lyrics.size(); i++) {
	//		if (BuildConfig.DEBUG) Log.wtf(_toString() + TAG_PLAY, getMethodName() + m_lyrics.get(i));
	//	}
	//}

	/**
	 * 가사(다운경로)
	 */
	public void setLyric(String lyric) {
		this.m_lyric = lyric;
	}

	/**
	 * 가사(다운경로)
	 */
	public String getLyric() {
		return this.m_lyric;
	}

	//public String getLyric() {
	//	String ret = null;
	//	if (m_lyrics != null && m_songIdx > -1 && m_songIdx < m_lyrics.size()) {
	//		ret = m_lyrics.get(m_songIdx);
	//	}
	//	if (BuildConfig.DEBUG) Log.wtf(_toString() + TAG_PLAY, getMethodName() + ":" + ret + ":" + m_songIdx + ":" + m_lyrics);
	//	return ret;
	//}

	public void setRedraw(boolean redraw) {
		if (mLyricsPlay != null) {
			mLyricsPlay.setRedraw(redraw);
		}
	}

	public boolean isRedraw() {
		if (mLyricsPlay != null) {
			return mLyricsPlay.isRedraw();
		} else {
			return false;
		}
	}

	public void setTime(long time) {
		this.m_time = time;
		updateTime();
	}

	public long getTime() {
		return m_time;
	}

	public _SongData getSongData() {
		return m_data;
	}

	public void setSongData(_SongData data) {
		this.m_data = data;
		mLyricsPlay.setSongData(data);
	}

	public void setMediaPlayer(MediaPlayer mp) {
		this.m_mp = mp;
		mLyricsPlay.setMediaPlayer(m_mp);
	}

	public MediaPlayer getMediaPlayer() {
		return m_mp;
	}

	public void setTypeface(Typeface typeface) {
		if (mLyricsPlay != null && typeface != null) {
			mLyricsPlay.setTypeface(typeface);
		}
	}

	public void setStrokeSize(int iStrokeSize) {
		if (mLyricsPlay != null) {
			mLyricsPlay.setStrokeSize(iStrokeSize);
		}
	}

	// public void setDstBack(Bitmap dstBack) {
	// this.m_dstBack = dstBack;
	// }
	//
	// public Bitmap getDstBack() {
	// return m_dstBack;
	// }

	public int setImgIdx(int imgIdx) {
		this.m_imgIdx = imgIdx;
		return this.m_imgIdx;
	}

	public int getImgIdx() {
		return this.m_imgIdx;
	}

	// public void setSrcBack(Bitmap srcBack) {
	// this.m_srcBack = srcBack;
	// }
	//
	// public Bitmap getSrcBack() {
	// return m_srcBack;
	// }

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main3XX#setPlayer()
	 * @see kr.kymedia.kykaraoke.tv.Main3XX#setVideo()
	 * @see kr.kymedia.kykaraoke.tv.Main3XX#setListen()
	 */
	private void create() {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName());

		mLyricsPlay = new _LyricsPlay(context);

		setSongData(new _SongData());

		addView(mLyricsPlay, 0, new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		mLyricsPlay.setVisibility(View.INVISIBLE);

		setRedraw(true);
	}

	@Override
	protected void onAttachedToWindow() {

		super.onAttachedToWindow();

		if (!isInEditMode()) {
			create();
			//test
			// String path = "";
			// Bundle bundle = getIntent().getExtras();
			// if (bundle != null) {
			// m_songList = bundle.getStringArrayList(SONGPLAYER_SKYM);
			//
			// if (m_songList == null || m_songList.size() == 0) {
			// path = getApplicationContext().getExternalFilesDir(null) + "/test.skym";
			// } else {
			// path = m_songList.get(m_songIdx).toString();
			// }
			// } else {
			// path = getApplicationContext().getExternalFilesDir(null) + "/test.skym";
			// }
			//
			// open(path);
			// play();
		}
	}

	@Override
	protected void onDetachedFromWindow() {

		super.onDetachedFromWindow();

		if (m_mp != null) {
			m_mp.setOnBufferingUpdateListener(null);
			m_mp.setOnPreparedListener(null);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
				m_mp.setOnTimedTextListener(null);
			}
			m_mp.setOnCompletionListener(null);
			m_mp.setOnInfoListener(null);
			m_mp.setOnErrorListener(null);
			m_mp.reset();
			m_mp.release();
			m_mp = null;
		}

		if (m_data != null) {
			m_data.release();
			m_data = null;
		}

	}

	public void updateTime() {
		Thread thread = new Thread(null, songTime, "songtime");
		thread.start();
	}

	private final Runnable songTime = new Runnable() {
		@Override
		public void run() {
			mLyricsPlay.getLyricsPlayThread().rePaint((int) m_time);
			seek((int) m_time);
			setRedraw(false);
		}
	};

	protected boolean open(String path) throws Exception {
		if (BuildConfig.DEBUG) Log.i(_toString(), "open()" + path + "()");

		try {
			m_data.release();
			m_data.load(path);

			File sourceFile = new File(path);
			if (sourceFile.exists()) {
				FileInputStream fs = new FileInputStream(sourceFile);
				FileDescriptor fd = fs.getFD();
				m_mp.setDataSource(fd);
				fs.close(); // 데이터 소스를 설정한 후 스트림을 닫았다.
				m_mp.prepare();
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) Log.i(_toString(), e.getLocalizedMessage());
			e.printStackTrace();
			close();
			return false;
		}

		return true;
	}

	@Deprecated
	protected void close() {
		// // 반주곡 끝까지 재생 완료, 점수 표시
		// _Main mainActivity = (_Main) _Main.ActivityMain;
		// if (mainActivity != null) {
		// mainActivity.stopPlay(STOP);
		// mainActivity.ShowScore();
		// }
		//
		// //finish();
	}

	@Deprecated
	MediaPlayer.OnCompletionListener onMediaPlayerComplete = new MediaPlayer.OnCompletionListener() {
		@Override
		public void onCompletion(MediaPlayer mp) {
			if (BuildConfig.DEBUG) Log.i(_toString(), "onCompletion()" + mp);
			close();
		}
	};

	@Deprecated
	MediaPlayer.OnErrorListener onMediaPlayerError = new MediaPlayer.OnErrorListener() {
		@Override
		public boolean onError(MediaPlayer mp, int what, int extra) {
			if (BuildConfig.DEBUG) Log.i(_toString(), "onMediaPlayerError()" + m_songIdx + "()" + mp + "" + what + "()" + extra + "()");
			return true;
		}
	};

	public boolean isPlaying() {
		//if (BuildConfig.DEBUG) Log.i(_toString() + "MediaPlayer", getMethodName() + m_mp);
		if (m_mp != null && m_mp.isPlaying()) {
			return true;
		}
		return false;
	}

	public boolean isPause() {
		//if (BuildConfig.DEBUG) Log.i(_toString() + "MediaPlayer", getMethodName() + m_mp);
		if (m_mp != null && !m_mp.isPlaying()) {
			return true;
		}
		return false;
	}

	@Deprecated
	public boolean play() throws Exception {
		// if (BuildConfig.DEBUG) _LOG.i(_toString(), "play()");
		//
		// try {
		// if (m_state == _STATE_STOP) {
		// m_mp.start();
		//
		// m_state = _STATE_PLAY;
		//
		// m_mp.setOnCompletionListener(onMediaPlayerComplete);
		// m_mp.setOnErrorListener(onMediaPlayerError);
		// }
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// if (BuildConfig.DEBUG) _LOG.e(_toString(), e.getLocalizedMessage());
		// close();
		// return false;
		// }
		//
		// return true;
		return true;
	}

	@Deprecated
	public void stop() {
		// try {
		// m_playView.playViewThread.init();
		//
		// if (m_state != _STATE_STOP) {
		// m_mp.stop();
		// m_state = _STATE_STOP;
		//
		// m_data.release();
		// m_mp.reset();
		// }
		// } catch (Exception e) {
		// return false;
		// }
		//
		// return true;
	}

	@Deprecated
	protected void pause() {
		try {
			if (m_state == PLAY_ENGAGE.PLAY_PLAY) {
				m_mp.pause();
				m_state = PLAY_ENGAGE.PLAY_PAUSE;
			}
		} catch (Exception e) {
		}
	}

	@Deprecated
	protected void resume() {
		try {
			if (m_state == PLAY_ENGAGE.PLAY_PAUSE) {
				m_mp.start();
				m_state = PLAY_ENGAGE.PLAY_PLAY;
			}
		} catch (Exception e) {
		}
	}

	protected void seek(int msec) {
		try {
			m_mp.seekTo(msec);
		} catch (Exception e) {
		}

	}

}