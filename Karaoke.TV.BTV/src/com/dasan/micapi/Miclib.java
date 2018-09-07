package com.dasan.micapi;

public class Miclib {
	/**
	 * load Mic
	 */
	public static final native boolean openMic();

	/**
	 * close Mic
	 */
	public static final native boolean closeMic();
}
