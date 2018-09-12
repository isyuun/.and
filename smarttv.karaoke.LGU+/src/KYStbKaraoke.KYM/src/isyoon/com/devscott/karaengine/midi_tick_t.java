// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   song_t.java

package isyoon.com.devscott.karaengine;

class midi_tick_t
		implements Cloneable
{

	public midi_tick_t()
	{
		tick = 0L;
	}

	public midi_tick_t(long _tick)
	{
		tick = _tick;
	}

	long tick()
	{
		return tick;
	}

	void reset()
	{
		tick = 0L;
	}

	void inc(long n)
	{
		tick += n;
	}

	void inc(midi_tick_t t)
	{
		tick += t.tick();
	}

	void inc()
	{
		tick++;
	}

	void dec()
	{
		tick--;
	}

	void dec(long n)
	{
		tick -= n;
	}

	void set(long n)
	{
		tick = n;
	}

	boolean valid()
	{
		return tick > 0L;
	}

	@Override
	public Object clone()
			throws CloneNotSupportedException
	{
		midi_tick_t myObj = (midi_tick_t) super.clone();
		return myObj;
	}

	private long tick;
}
