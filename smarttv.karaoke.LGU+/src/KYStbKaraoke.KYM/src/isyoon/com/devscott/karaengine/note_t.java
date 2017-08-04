// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   song_t.java

package isyoon.com.devscott.karaengine;

class note_t
{

	note_t()
	{
		note = 0;
	}

	void set(short v)
	{
		note = v;
	}

	short get()
	{
		return note;
	}

	boolean is(short v)
	{
		return note == v;
	}

	void reset()
	{
		note = 255;
	}

	private short note;
}
