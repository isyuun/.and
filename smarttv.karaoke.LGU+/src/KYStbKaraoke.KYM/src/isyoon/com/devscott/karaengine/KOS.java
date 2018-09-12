// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   KOS.java

package isyoon.com.devscott.karaengine;

public class KOS
{
	public class OS_EVENT
	{
		char OSEventName;
		int OSEventGrp;
		int[] OSEventTbl = new int[8];
		int OSEventID;

		public OS_EVENT() {
		}
	}

	public KOS()
	{
	}

	public static long TIMER_FREQ = 0xf4240L;
	public static int TCOLS = 720;
	static final int MAX_VOLUME = 100;
	static final int MAX_TITLE_ANIM = 16;
	static final int MAX_KEYCON = 10;
	static final int MAX_KEY = 6;
	static final int MAX_SPEED = 4;
	static final int MAX_CNT_BOOKING = 199;
	static final int DISP_SRT_SCORE = 60;
	static final int MUSIC_VIDEO_SRT = 0x13880;
	static final int MAX_LOGO_COLOR = 48;
	static final int MASTER_KEY = 26387;
	static final int SCORE_DELAY = 100;
	static final int MAX_LONG = 0x7fffffff;

}
