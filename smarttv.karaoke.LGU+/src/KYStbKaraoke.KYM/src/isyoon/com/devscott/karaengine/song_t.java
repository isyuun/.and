// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   song_t.java

package isyoon.com.devscott.karaengine;

// Referenced classes of package com.devscott.karaengine:
//            FH, sng_t, chors_t, geol_t, 
//            chrd_t, mtrk_t, ctrl_t, make_t, 
//            dump_t, note_t, sdrum_t, midi_tick_t, 
//            midi_pos_t, end_t

public class song_t
{

	public song_t()
	{
		file_handle = new FH();
		fhReserved = new FH();
		try
		{
			song = new sng_t();
		} catch (Exception exception) {
		}
		chorus_cmd = new chors_t[128];
		for (int i = 0; i < 128; i++)
			chorus_cmd[i] = new chors_t();

		gasu_cmd = new chors_t[128];
		for (int i = 0; i < 128; i++)
			gasu_cmd[i] = new chors_t();

		inst_cmd = new chors_t[128];
		for (int i = 0; i < 128; i++)
			inst_cmd[i] = new chors_t();

		geol_cmd = new geol_t[128];
		for (int i = 0; i < 128; i++)
			geol_cmd[i] = new geol_t();

		chord_cmd = new chrd_t[128];
		for (int i = 0; i < 128; i++)
			chord_cmd[i] = new chrd_t();

		imidi = new mtrk_t[71];
		for (int i = 0; i < 71; i++)
			imidi[i] = new mtrk_t();

		ctrl = new ctrl_t[100];
		for (int i = 0; i < 100; i++)
			ctrl[i] = new ctrl_t();

		org_ctrl = new ctrl_t();
		cur_ctrl = new ctrl_t();
		org_rmidi = new mtrk_t[6];
		mpeg_fname = new byte[255];
		mlyric = new make_t();
		dlyric = new dump_t[9];
		for (int i = 0; i < 9; i++)
			dlyric[i] = new dump_t();

		step = new int[56];
		medley_title = new byte[2][56];
		save_trk = new mtrk_t[71];
		for (int i = 0; i < 71; i++)
			save_trk[i] = new mtrk_t();

		har_chlist = new int[4];
		har_tlist = new int[4];
		note = new note_t[71][128];
		for (int i = 0; i < 71; i++)
		{
			for (int j = 0; j < 128; j++)
				note[i][j] = new note_t();

		}

		cur_drum_vol = new sdrum_t[8][10];
		old_drum_vol = new sdrum_t[8][10];
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				cur_drum_vol[i][j] = new sdrum_t();
				old_drum_vol[i][j] = new sdrum_t();
			}

		}

		stepmidi = new long[3][2];
		org_rtinx = new int[13];
		new_rtinx = new int[6];
		live_loc = new long[6];
		dump_tick = new midi_tick_t();
	}

	public final short FLAG_MINER = 1;
	public final short FLAG_MP3 = 2;
	public final short FLAG_MVIDEO = 4;
	public final short FLAG_MP3_STEREO = 8;
	public final short FLAG_LIVE = 128;
	public sng_t song;
	long hsong_no;
	int status;
	FH file_handle;
	FH fhReserved;
	int format;
	int timebase;
	boolean port1;
	boolean channel4;
	int chorus_inx;
	long chorus_loc;
	boolean chorus_present;
	boolean gasu_present;
	boolean inst_present;
	int chorus_cnt;
	int gasu_cnt;
	int inst_cnt;
	int geol_cnt;
	int geol_current;
	chors_t chorus_cmd[];
	chors_t gasu_cmd[];
	chors_t inst_cmd[];
	geol_t geol_cmd[];
	chrd_t chord_cmd[];
	long buf_base;
	int mtrks;
	public mtrk_t imidi[];
	int ctrl_cnt;
	boolean rhythm_present;
	ctrl_t ctrl[];
	mtrk_t org_rmidi[];
	byte mpeg_fname[];
	int mpeg_num_singer;
	int mpeg_delay;
	int ftype;
	int fsize;
	int effect;
	midi_pos_t lyric_ptr;
	midi_pos_t lyric_pos;
	midi_pos_t lyric_fptr;
	midi_pos_t lyric_mptr;
	boolean current_lyric_pos_update;
	mtrk_t midi_mptr;
	boolean multi_interval;
	make_t mlyric;
	dump_t dlyric[];
	int max_line;
	int dump_line;
	int erase_line;
	int merase_line;
	char para;
	long dump_timer;
	long interval_timer;
	long intv_logo_timer;
	int intv_logo_counter;
	long erase_timer;
	long note4_timer;
	int note4_x;
	int note4_y;
	int step[];
	int step_inx;
	int x;
	int y;
	int xz;
	int yz;
	int char_width;
	long char_tick;
	long cur_tick;
	midi_tick_t dump_tick;
	boolean stop_token;
	int erase4_timer;
	long gstick;
	byte medley_title[][];
	byte medley_country;
	public midi_tick_t madi_cur_tick;
	public midi_tick_t madi_tick;
	mtrk_t save_trk[];
	int madi_delay_tick;
	long ending_tick;
	end_t ending;
	int ch_type;
	int har_chlist[];
	int har_tlist[];
	long tempo_us;
	int bass_code;
	sdrum_t cur_drum_vol[][];
	sdrum_t old_drum_vol[][];
	note_t note[][];
	int melody_type1;
	int melody_type2;
	int gstrk;
	int rs1trk;
	int rs2trk;
	int effect1trk;
	int effect2trk;
	int chordtrk;
	int steptrk;
	int step_beat_cmd;
	int step_madi;
	long gs_total_tick;
	int step_data_present;
	int step_file_no;
	long stepmidi[][];
	int step_arrow_count;
	int new_rhythm_mode;
	int old_rhythm_mode;
	int rbuf_usage;
	int org_rtinx[];
	int new_rtinx[];
	int ctrl_inx;
	ctrl_t cur_ctrl;
	ctrl_t org_ctrl;
	long mp3_loc;
	long live_loc[];
	int mtv_no;
	boolean midi_4port_on;
	int drum_beat1;
	int drum_beat2;
	int lighton_timer;
	int lightoff_timer;
}
