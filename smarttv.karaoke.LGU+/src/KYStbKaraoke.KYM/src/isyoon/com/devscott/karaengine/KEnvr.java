// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   KEnvr.java

package isyoon.com.devscott.karaengine;

public class KEnvr
{

	public KEnvr()
	{
	}

	void init()
	{
		m_cur_envr = 256;
		m_sys_stat = 256;
	}

	public static int envr_get()
	{
		int ret = m_cur_envr;
		return ret;
	}

	public static void envr_set(int new_envr)
	{
		int save_envr = m_cur_envr;
		m_cur_envr = 260;
		if (save_envr != new_envr)
			switch (new_envr)
			{
			}
		m_cur_envr = new_envr;
	}

	public static boolean is(int _envr)
	{
		return m_cur_envr == _envr;
	}

	public static final int ENVR_INIT = 256;
	public static final int ENVR_IDLE = 257;
	public static final int ENVR_SONG = 258;
	public static final int ENVR_SCORE = 259;
	public static final int ENVR_LOCK = 260;
	public static final int ENVR_NONE = 261;
	public static final int ENVR_CD = 262;
	public static final int ENVR_MENT = 263;
	public static final int SYS_INIT = 256;
	public static final int SYS_READY = 257;
	private static int m_cur_envr = 256;
	private static int m_sys_stat = 256;
	public static int task_status = 0;

}
