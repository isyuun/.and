// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   song_t.java

package isyoon.com.devscott.karaengine;

import java.io.UnsupportedEncodingException;

class LY
{

	public LY()
	{
		pos = 0;
		s = new byte[255];
		pos = 0;
	}

	byte at(int n)
	{
		return s[n];
	}

	byte at()
	{
		return at(pos);
	}

	/**
	 * isyoon:기본도안되는게병신지랄을하고자빠졌네.테스트나똑바로해.이병신아.
	 */
	boolean valid(int inx)
	{
		try {
			return (s[inx] & 0xffffffffL) > 0L;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	boolean valid()
	{
		return valid(pos);
	}

	void inc()
	{
		pos++;
	}

	void set(int n, byte val)
	{
		s[n] = val;
		if (val == 0)
			pos = (short) n;
	}

	void set(byte val)
	{
		set(pos, val);
	}

	short pos()
	{
		return pos;
	}

	public void repose()
	{
		pos = 0;
	}

	public void reset()
	{
		pos = 0;
	}

	public void add(byte c)
	{
		s[pos++] = c;
	}

	public byte[] get_buffer()
	{
		return s;
	}

	public String debug_string()
	{
		return toUnicode();
	}

	public String toUnicode()
	{
		int len = 0;
		for (int i = 0; i < 255 && s[i] != 0; i++)
			len++;

		byte tmp[] = new byte[len];
		for (int i = 0; i < len; i++)
			tmp[i] = s[i];

		String uni = null;
		try
		{
			uni = new String(tmp, "KSC5601");
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return uni;
	}

	private byte s[];
	private short pos;
}
