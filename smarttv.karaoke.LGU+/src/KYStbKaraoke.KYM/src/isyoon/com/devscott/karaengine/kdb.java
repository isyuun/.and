// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   kdb.java

package isyoon.com.devscott.karaengine;

import java.io.IOException;

public abstract class kdb
{

	public kdb()
	{
	}

	public boolean connect(String dbPath)
	{
		return false;
	}

	public void disconnect()
	{
	}

	public void addRecent(int i, String s)
	{
	}

	public void writeRecent(String s)
			throws IOException
	{
	}

	public boolean check_song(int sno)
	{
		return false;
	}

	public boolean query_song_info(int sno, String columns[])
	{
		return false;
	}

	public int query_title_info(String keyword, String columns[])
	{
		return 0;
	}

	public boolean Rquery_song_info(long sno, String columns[])
	{
		return false;
	}
}
