// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   song_t.java

package isyoon.com.devscott.karaengine;

class midi_pos_t
		implements Cloneable
{

	public midi_pos_t()
	{
		org_pos = 0L;
		pos = 0L;
		pos = 0L;
	}

	public midi_pos_t(long _pos)
	{
		org_pos = 0L;
		pos = 0L;
		org_pos = _pos;
		pos = _pos;
	}

	long pos()
	{
		return pos;
	}

	int org_pos()
	{
		return (int) org_pos;
	}

	void inc()
	{
		pos++;
	}

	void inc(int n)
	{
		pos += n;
	}

	void inc(long n)
	{
		pos += n;
	}

	void inc(midi_pos_t t)
	{
		pos += t.pos();
	}

	void reset_org()
	{
		pos = org_pos;
	}

	void disable()
	{
		pos = 0L;
	}

	void dec()
	{
		pos--;
	}

	void set(long n)
	{
		pos = n;
	}

	boolean valid()
	{
		return pos > 0L;
	}

	@Override
	public Object clone()
			throws CloneNotSupportedException
	{
		return super.clone();
	}

	private long org_pos;
	private long pos;
}
