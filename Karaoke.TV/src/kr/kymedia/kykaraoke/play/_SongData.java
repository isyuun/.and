package kr.kymedia.kykaraoke.play;

import kr.kymedia.kykaraoke.tv.BuildConfig;
import kr.kymedia.kykaraoke.tv.api.IKaraokeTV;
import android.util.Log;

public class _SongData extends SongData {
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

	public _SongData() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean load(String name) {

		boolean ret = false;
		Log.w(_toString(), "load() " + "[ST]" + ret);
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + name);
		ret = super.load(name);
		Log.w(_toString(), "load() " + "[ED]" + ret);
		return ret;
	}

}
