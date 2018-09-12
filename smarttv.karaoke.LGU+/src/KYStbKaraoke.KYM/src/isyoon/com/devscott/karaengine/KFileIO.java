// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   KFileIO.java

package isyoon.com.devscott.karaengine;

// Referenced classes of package com.devscott.karaengine:
//            FH, midi_pos_t

public class KFileIO
{

	public KFileIO()
	{
		g_file_handle = new int[50];
		g_file_handle_seek = new long[50];
		g_file_handle_name = new char[50][64];
	}

	StringBuffer get_root_path()
	{
		return new StringBuffer("");
	}

	static StringBuffer get_document_path()
	{
		return new StringBuffer("/sdcard/");
	}

	StringBuffer get_rlive_path()
	{
		return new StringBuffer("");
	}

	StringBuffer get_rsong_path()
	{
		return new StringBuffer("");
	}

	StringBuffer get_rnew_path()
	{
		return new StringBuffer("");
	}

	static String get_contents_ext()
	{
		return ".KYC";
	}

	void debug_filehandler(int i)
	{
	}

	public static void make_songky2_fname(char ac[], long l)
	{
	}

	public static void make_song_fname(StringBuffer strPath, long song_no)
	{
		strPath.delete(0, strPath.length());
		strPath.append(get_document_path());
		strPath.append(String.format("%05d", new Object[] {
				Long.valueOf(song_no)
		}));
		strPath.append(get_contents_ext());
	}

	public static void make_song_live_fname(char ac[], long l)
	{
	}

	public static void make_song_dname(char ac[], long l)
	{
	}

	public static void make_new_fname(StringBuffer stringbuffer, long l)
	{
	}

	public static void make_new_fname(char ac[], long l)
	{
	}

	public static FH dopen(int hdrv, StringBuffer path, boolean wmode)
	{
		FH f = new FH();
		f.open(path.toString());
		return f;
	}

	public static FH dopen(int hdrv, char path[], boolean wmode)
	{
		return new FH();
	}

	public static void dclose(FH fh)
	{
	}

	public static long dsize(FH file_handle)
	{
		return file_handle.size();
	}

	public static int dseek(FH file_handle, midi_pos_t loc)
	{
		return 0;
	}

	public static int dseek(FH file_handle, long loc)
	{
		return 0;
	}

	public static long dtell(FH file_handle)
	{
		return 0L;
	}

	public static final int ROOT_DRIVE = 65281;
	public static final int SYS2_DRIVE = 65282;
	public static final int TMRW_DRIVE = 65284;
	public static final int DATA_DRIVE = 65288;
	public static final int ALL_DRIVE = 65535;
	public static final int BAD_DRIVE = 65534;
	public static final int PGM_DRIVE = 0;
	private static final short MAX_OPEN_FILE_HANDLE = 50;
	public int g_file_handle[];
	public long g_file_handle_seek[];
	public char g_file_handle_name[][];
}
