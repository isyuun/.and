// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   KTask.java

package isyoon.com.devscott.karaengine;

public class KTask
{

	public KTask()
	{
	}

	public static final int NET_TASK = 0x10000;
	public static final int TIMER_TASK = 32768;
	public static final int COMM_TASK = 16384;
	public static final int PLAY_TASK = 1024;
	public static final int PCM_TASK = 512;
	public static final int DECODE_TASK = 256;
	public static final int MPEG_TASK = 128;
	public static final int DISP_TASK = 64;
	public static final int FONT_TASK = 32;
	public static final int OSD_TASK = 8;
	public static final int SETUP_TASK = 2;
	public static final int START_TASK = 1;
	public static int task_status = 0;

}
