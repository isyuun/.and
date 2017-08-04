// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   song_t.java

package isyoon.com.devscott.karaengine;

// Referenced classes of package com.devscott.karaengine:
//            midi_pos_t, midi_tick_t

class mtrk_t
		implements Cloneable
{

	public mtrk_t()
	{
		last_note = new char[12];
		delay_note = new char[12];
		mode = 0;
		speed = '\001';
		para = '\377';
		patch = '\377';
		rpn = '\377';
		vol = 'd';
		veloc = '\0';
		note = '\0';
		nrpn = '\377';
		cmd = '\0';
		type = '\uFFFF';
		addr = new midi_pos_t();
		tick = new midi_tick_t();
		last_cnt = '\0';
		delay_tick = 0L;
		delay_cnt = '\0';
	}

	@Override
	public Object clone()
			throws CloneNotSupportedException
	{
		mtrk_t a = (mtrk_t) super.clone();
		a.last_note = last_note.clone();
		a.delay_note = delay_note.clone();
		a.addr = (midi_pos_t) addr.clone();
		a.tick = (midi_tick_t) tick.clone();
		return a;
	}

	private final short MAX_LAST_NOTE = 12;
	public short mode;
	public char speed;
	public char para;
	public char patch;
	public char rpn;
	public char vol;
	public char veloc;
	public char note;
	public char nrpn;
	public char old_rpn;
	public char old_nrpn;
	public char cmd;
	public char type;
	public midi_pos_t addr;
	public long length;
	public midi_tick_t tick;
	public char last_cmd;
	public char last_cnt;
	public char last_note[];
	public long delay_tick;
	public char delay_cnt;
	public char delay_note[];
}
