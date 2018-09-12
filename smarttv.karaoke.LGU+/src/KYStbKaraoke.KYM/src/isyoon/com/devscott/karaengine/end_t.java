// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   song_t.java

package isyoon.com.devscott.karaengine;

// Referenced classes of package com.devscott.karaengine:
//            midi_pos_t, midi_tick_t

class end_t
{

	public end_t()
	{
	}

	midi_pos_t lyric_ptr;
	short lyric_cnt;
	midi_tick_t madi_tick;
	long tempo_us;
	char rhythm_speed;
	char bass_code;
	midi_tick_t pcm_cur_tick;
}
