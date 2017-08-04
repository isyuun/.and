// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Player.java

package isyoon.com.devscott.karaengine;

import android.util.Log;

import com.kumyoung.gtvkaraoke.DataHandler;
import com.kumyoung.stbui.ViewManager;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Referenced classes of package com.devscott.karaengine:
//            Global, Disp, song_t, make_t, 
//            LY, sng_t, midi_pos_t, KMemory, 
//            KString, State, dump_t, mtrk_t, 
//            KMidi, midi_tick_t, Etc, end_t, 
//            KMadi, def, KPlay, KRhythm, 
//            Serial, chrd_t, KMidiFS, KEnvr, 
//            KBook, key_t, KOS, KScore

public class Player
{
	class PlayerThread extends Thread
	{

		boolean isRunning = false;

		public PlayerThread(String str)
		{
			super();
		}

		public void threadStop()
		{
			Global.Inst();
			if (Global.isGTV.booleanValue())
				stop();
			isRunning = false;
		}

		@Override
		public void run()
		{
			Global gInst = Global.Inst();
			isRunning = true;
			while (isRunning)
			{
				try
				{
					Thread.sleep(10L);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				while (gInst.midi_tick_count > 0L)
				{
					play_madi();
					if (play_track() == 0)
					{
						Log.i("PlayerThread", "End Of MIDI");
						isRunning = false;
						gInst.midi_tick_count = 0L;
					} else
					{
						madi_tick_update();
						gInst.midi_tick_count--;
						KScore.Inst().score_count();
					}
				}
			}
			Log.i("PlayerThread", "Player  thread stooped ");
		}

	}

	public Player()
	{
		buf_chod = new char[255];
		lock = new ReentrantLock();
	}

	public void prepare_title()
	{
		Global gInst = Global.Inst();
		gInst.disp.disp_send(1026, 0L, 0L);
		gInst.disp.disp_send(1027, 0L, 0L);
	}

	boolean THAILAND_HALF_CODE(byte a)
	{
		return a == 209 || a >= 212 && a <= 222 || a >= 231 && a <= 238;
	}

	public void play_notask_init()
	{
	}

	public void AllPlay()
	{
		Log.d(TAG, "AllPlay()");
		String str = String.format("%s/%s", new Object[] {
				DataHandler.serverKYIP, DataHandler.mp3Path
		});
		if (Global.Inst().streamMp3player == null)
			Log.e("ke", "check streamplayer is null");
		else
			Global.Inst().streamMp3player.doPlay(str, 2);
		doPlay();
	}

	public void AllStop()
	{
		Log.d(TAG, "AllStop()");
		Log.d(TAG, "AllStop()");
		Log.d(TAG, "AllStop()");
		if (Global.Inst().streamMp3player == null)
			Log.e("ke", "check streamplayer is null");
		else
			Global.Inst().streamMp3player.doStop();
		doStop();
	}

	private void doPlay()
	{
		mThread = new PlayerThread("player_thread");
		mThread.start();
	}

	private void doStop()
	{
		if (mThread != null && mThread.isAlive())
			mThread.threadStop();
	}

	void play_task_init()
	{
	}

	void end_task()
	{
	}

	void play_task(int i)
	{
	}

	void next_lyric_make()
	{
		Global gInst = Global.Inst();
		gInst.fp.mlyric.lyric.repose();
		gInst.fp.mlyric.rubi1.repose();
		gInst.fp.mlyric.rubi2.repose();
		make_t make = gInst.fp.mlyric;
		int country = gInst.fp.song.country;
		do
		{
			if (!gInst.fp.lyric_ptr.valid())
				return;
			if (country == 4)
			{
				KMemory.get_midi_line(make.rubi1, gInst.fp.lyric_ptr);
				KMemory.get_midi_line(make.rubi2, gInst.fp.lyric_ptr);
			}
			KMemory.get_midi_line(make.lyric, gInst.fp.lyric_ptr);
			int eno = KString.get_erase_cnt(make.lyric, country);
			if (eno > 0)
				break;
			if (gInst.fp.song.is_medley_song)
			{
				for (int title_n = 0; title_n < 2; title_n++)
				{
					for (int i = 0; i < 56; i++)
						gInst.fp.medley_title[title_n][i] = 0;

				}

				for (int title_ln = 0; title_ln < 5; title_ln++)
				{
					if (!gInst.fp.lyric_ptr.valid())
						return;
					gInst.fp.lyric_ptr = KMemory.get_midi_line(make.lyric, gInst.fp.lyric_ptr);
					if (KString.get_erase_cnt(make.lyric, country) == 0)
						break;
					if (title_ln < 2)
						KString.strip_space(make.lyric);
				}

				gInst.disp.disp_send(1036, 0L, 0L);
				gInst.fp.medley_country = (byte) country;
			}
		} while (true);
		KString.strip_space(make.lyric);
		make.yz = 106;
		int ci = 14;
		int co = 13;
		int rci = 6;
		int rco = 13;
		for (int i = 0; i < 56; i++)
			make.step[i] = '\0';

		make.xz = make_banner(ci, co, rci, rco, gInst.fp.fsize, gInst.fp.ftype, country, make.lyric, make.rubi1, make.rubi2, make.step, (gInst.ctrl_status & 1) == 1, State.korea_rubi, State.japan_rubi);
		tmp_uLyric = make.lyric.toUnicode();
		Log.d("ke", (new StringBuilder("LYRIC : ")).append(tmp_uLyric).toString());
		make.lyric_pos = gInst.fp.lyric_ptr.pos();
		make.ready = true;
		Log.i("ke", "play_tname = next lyrics is ready!!");
	}

	void next_lyric_dump()
	{
		Global gInst = Global.Inst();
		make_t make = gInst.fp.mlyric;
		if (!make.ready)
		{
			Log.i("ke", "......end of lyric");
			return;
		}
		dump_t dump = new dump_t();
		dump.step = make.step.clone();
		dump.xz = make.xz;
		dump.yz = make.yz;
		dump.lyric_pos = make.lyric_pos;
		dump.line_no = gInst.fp.dump_line;
		if ((gInst.ctrl_status & 0x100) == 256 || gInst.mlyric_mode)
			dump.x = 160;
		else if (gInst.fp.dump_line == 0)
			dump.x = 1;
		else
			dump.x = 1;
		if (gInst.mlyric_mode)
			dump.y = gInst.fp.dump_line * 38;
		if (gInst.fp.dump_line == 0)
			dump.y = 1;
		else
			dump.y = 2;
		dump.ready = true;
		lock.lock();
		int i;
		for (i = 0; i < 9; i++)
			if (!gInst.fp.dlyric[i].ready)
				break;

		if (i < 9)
			gInst.fp.dlyric[i] = dump;
		lock.unlock();
		if (!gInst.mlyric_mode)
			if (dump.y == 1)
			{
				if (ViewManager.Inst().lpLyric1View != null)
					ViewManager.Inst().lpLyric1View.setText(tmp_uLyric);
			} else
			if (ViewManager.Inst().lpLyric2View != null)
				ViewManager.Inst().lpLyric2View.setText(tmp_uLyric);
		make.ready = false;
		if (++make.color_inx >= 2)
			make.color_inx = 0;
		if (++gInst.fp.dump_line >= gInst.fp.max_line)
			gInst.fp.dump_line = 0;
	}

	void next_lyric_erase()
	{
	}

	void skip_mlyric_interval()
	{
		Global gInst = Global.Inst();
		if (gInst.fp.multi_interval)
			gInst.fp.multi_interval = false;
	}

	boolean skip_mlyric_line()
	{
		Global gInst = Global.Inst();
		LY s = new LY();
		if (!gInst.fp.multi_interval)
		{
			int country = gInst.fp.song.country;
			int eno;
			do
			{
				if (!gInst.fp.lyric_mptr.valid())
					return false;
				if (country == 4)
				{
					KMemory.get_midi_line(s, gInst.fp.lyric_mptr);
					KMemory.get_midi_line(s, gInst.fp.lyric_mptr);
				}
				KMemory.get_midi_line(s, gInst.fp.lyric_mptr);
				eno = KString.get_erase_cnt(s, country);
				if (eno > 0)
					break;
				if (gInst.fp.song.is_medley_song)
				{
					for (int i = 0; i < 5; i++)
					{
						if (!gInst.fp.lyric_mptr.valid())
							break;
						KMemory.get_midi_line(s, gInst.fp.lyric_mptr);
						if (KString.get_erase_cnt(s, country) <= 0)
							break;
					}

				}
			} while (true);
			mtrk_t mp = gInst.fp.midi_mptr;
			mp.para = '\377';
			while (eno > 0)
			{
				if (!mp.addr.valid())
					return false;
				mp.addr = KMidi.skip_midi(mp.addr, mp.type, 70, mp);
				if (!mp.addr.valid())
					return false;
				mp.tick.set(KMemory.get_midi_tick(mp.addr));
				if (mp.para > '\004')
					continue;
				if (mp.para != 0)
					eno--;
				if (mp.para >= '\003')
				{
					gInst.fp.multi_interval = true;
					break;
				}
				mp.para = '\377';
			}
			return true;
		} else
		{
			return false;
		}
	}

	void erase_line_setup()
	{
		Global gInst = Global.Inst();
		if (!gInst.fp.imidi[gInst.fp.gstrk].addr.valid())
		{
			Log.i("ke", "end of gasa.");
			return;
		}
		int i;
		if (!gInst.fp.dlyric[0].ready)
		{
			if ((gInst.ctrl_status & 0x20) == 32)
				return;
			if (!gInst.fp.mlyric.ready && !gInst.fp.lyric_ptr.valid())
			{
				gInst.fp.imidi[gInst.fp.gstrk].addr.disable();
				gInst.fp.imidi[gInst.fp.gstrk].tick.reset();
				Log.e("ke", "end of lyric(lyric track stop)");
				return;
			}
			Boolean bfill = Boolean.valueOf(false);
			for (i = 0; i < 10; i++)
			{
				if (gInst.fp.dlyric[0].ready)
				{
					bfill = Boolean.valueOf(true);
					break;
				}
				Log.w("ke", "font not ready.. 1sec delay");
				Etc.usleep(10);
			}

			if (!bfill.booleanValue())
			{
				Log.e("ke", (new StringBuilder("Erase not ready(")).append(i).append(" tick)").toString());
				Log.e("ke", (new StringBuilder("debug gInst.fp.dlyric[x].ready   is 0 : ")).append(gInst.fp.dlyric[0].ready).append(" is 1 :").append(gInst.fp.dlyric[1].ready).toString());
				return;
			}
		}
		lock.lock();
		dump_t dump = gInst.fp.dlyric[0];
		for (i = 0; i < 8; i++)
			gInst.fp.dlyric[i] = gInst.fp.dlyric[i + 1];

		gInst.fp.dlyric[i].ready = false;
		lock.unlock();
		for (i = 0; i < 56; i++)
			gInst.fp.step[i] = dump.step[i];

		gInst.fp.step_inx = 0;
		gInst.fp.x = dump.x;
		gInst.fp.y = dump.y;
		gInst.fp.xz = dump.xz;
		gInst.fp.yz = dump.yz;
		gInst.fp.erase_line = dump.line_no;
		gInst.fp.lyric_pos = new midi_pos_t(dump.lyric_pos);
		gInst.fp.cur_tick = 0L;
		gInst.fp.current_lyric_pos_update = true;
	}

	void erase_char_setup(Global gInst)
	{
		mtrk_t mp = gInst.fp.imidi[gInst.fp.gstrk];
		if (gInst.fp.para >= '\003' && mp.tick.tick() < gInst.fp.madi_tick.tick())
		{
			mp.tick.set(gInst.fp.madi_tick.tick());
			gInst.fp.char_tick = gInst.fp.madi_tick.tick();
		} else if (mp.tick.tick() > gInst.fp.madi_tick.tick())
			gInst.fp.char_tick = gInst.fp.madi_tick.tick();
		else
			gInst.fp.char_tick = mp.tick.tick();
		if (--gInst.fp.char_tick == 0L)
		{
			gInst.fp.char_tick++;
			mp.tick.inc();
		}
		for (; (gInst.fp.step[gInst.fp.step_inx] & 0x8000) == 32768; gInst.fp.step_inx++)
			gInst.fp.x += gInst.fp.step[gInst.fp.step_inx] & 0x7fff;

		gInst.fp.char_width = gInst.fp.step[gInst.fp.step_inx];
		gInst.fp.step_inx++;
		gInst.fp.cur_tick = 0L;
	}

	void play_madi()
	{
		long tick = 0L;
		Global gInst = Global.Inst();
		if (gInst.fp.madi_cur_tick.tick() == 0L)
		{
			if ((gInst.ctrl_status & 0x40) == 64)
			{
				gInst.fp.dump_timer = 0L;
				gInst.fp.interval_timer = 0L;
				gInst.fp.intv_logo_timer = 0L;
				gInst.fp.intv_logo_counter = 0;
				gInst.fp.note4_timer = 0L;
				gInst.fp.dump_line = 0;
				gInst.fp.merase_line = 0;
				gInst.fp.char_tick = 0L;
				gInst.fp.step_inx = 0;
				gInst.fp.step[0] = 0;
				gInst.fp.mlyric.ready = false;
				gInst.fp.mlyric.color_inx = 0;
				for (int i = 0; i < 9; i++)
					gInst.fp.dlyric[i].ready = false;

				gInst.fp.erase4_timer = 0;
				if (gInst.fp.ending.tempo_us != gInst.fp.tempo_us)
				{
					gInst.fp.tempo_us = gInst.fp.ending.tempo_us;
					Etc.set_play_timer();
				}
				gInst.fp.lyric_ptr = gInst.fp.ending.lyric_ptr;
				gInst.fp.lyric_fptr = gInst.fp.ending.lyric_ptr;
				gInst.fp.madi_tick = new midi_tick_t(gInst.fp.ending.madi_tick.tick());
				gInst.fp.bass_code = gInst.fp.ending.bass_code;
				if (!gInst.fp.lyric_ptr.valid())
				{
					gInst.fp.save_trk[gInst.fp.gstrk].addr.disable();
					gInst.fp.save_trk[gInst.fp.gstrk].tick.reset();
				}
				gInst.fp.lyric_mptr = gInst.fp.ending.lyric_ptr;
				gInst.fp.midi_mptr = gInst.fp.save_trk[gInst.fp.gstrk];
				gInst.fp.multi_interval = false;
				gInst.ctrl_status &= 0xffffe9ff;
				if (gInst.fp.save_trk[gInst.fp.rs2trk].para == '\032' && gInst.fp.old_rhythm_mode != 0)
				{
					gInst.ctrl_status |= 0x400;
					gInst.fp.new_rhythm_mode = gInst.fp.old_rhythm_mode;
				}
				if (gInst.fp.save_trk[gInst.fp.gstrk].addr.valid())
				{
					gInst.disp.disp_send(1029, 0L, 0L);
					gInst.disp.disp_send(1030, 0L, 0L);
					mtrk_t tmp = gInst.fp.save_trk[gInst.fp.gstrk];
					for (tmp.para = '\377'; tmp.para > '\004';)
					{
						tmp.addr = KMidi.play_midi(tmp.addr, tmp.type, gInst.fp.gstrk, tmp);
						if (!tmp.addr.valid())
							break;
						tick = KMemory.get_midi_tick(tmp.addr);
					}

					if (tmp.addr.valid() && tmp.para != 0)
						gInst.fp.save_trk[gInst.fp.gstrk].para = '\0';
				} else
				{
					gInst.disp.disp_send(1029, 0L, 0L);
				}
				KMidi.midi_note_off();
				KMadi.madi_midi_back();
				gInst.ctrl_status &= 0xffffff9f;
			}
			if ((gInst.ctrl_status & 0x20) != 32)
				KMadi.madi_midi_init();
		}
	}

	int lyrictask(Global gInst, int no, long gsaddr)
	{
		if (gInst.fp.char_tick > 0L && gInst.fp.char_width > 0)
		{
			no++;
			scrolling_lyric(gInst);
			if (gInst.fp.char_tick <= 0L && gInst.fp.step[gInst.fp.step_inx] <= 0)
				if (gInst.fp.para == '\003' || gInst.fp.para == '\004')
				{
					Etc.mode_add_interval();
					gInst.ctrl_status |= 0x80;
					if (gInst.fp.imidi[gInst.fp.gstrk].addr.valid())
					{
						gInst.fp.interval_timer = gInst.fp.imidi[gInst.fp.gstrk].tick.tick();
						if (gInst.fp.interval_timer > gInst.fp.madi_tick.tick() * 5L)
							gInst.fp.interval_timer = gInst.fp.madi_tick.tick();
						else
						if (gInst.fp.song.is_medley_song)
						{
							if (gInst.fp.interval_timer > gInst.fp.madi_tick.tick())
								gInst.fp.interval_timer = gInst.fp.madi_tick.tick();
						} else
						{
							if (gInst.fp.interval_timer > gInst.fp.madi_tick.tick())
								gInst.fp.erase_timer = gInst.fp.madi_tick.tick();
							gInst.fp.interval_timer = 0L;
						}
						if (def.CUR_RS_TICK() > 0L)
						{
							KPlay.Inst().play_send(518, 0L);
							gInst.fp.interval_timer = 0L;
						} else
						if (def.CUR_RS_TICK() > 0L)
						{
							KPlay.Inst().play_send(518, 0L);
							gInst.fp.interval_timer = 0L;
						}
					}
				} else
				if (gInst.fp.para != 0 && gInst.fp.imidi[gInst.fp.gstrk].addr.valid())
				{
					gInst.fp.dump_timer = gInst.fp.imidi[gInst.fp.gstrk].tick.tick();
					if (gInst.mlyric_mode && ++gInst.fp.merase_line >= gInst.fp.max_line)
						gInst.fp.merase_line = 0;
				}
		}
		if ((gInst.fp.imidi[gInst.fp.gstrk].para & 0xff) <= 4)
		{
			gInst.fp.para = gInst.fp.imidi[gInst.fp.gstrk].para;
			gInst.fp.imidi[gInst.fp.gstrk].para = '\377';
			if ((gInst.fp.para & 0xff) == 0)
			{
				if ((gInst.ctrl_status & 0x20) == 32)
					return no;
				gInst.fp.dump_line = 0;
				gInst.fp.merase_line = 0;
				gInst.fp.dump_tick.reset();
				gInst.disp.disp_send(1029, 0L, 0L);
				skip_mlyric_interval();
				for (int i = 0; i < gInst.fp.max_line; i++)
					if (skip_mlyric_line())
					{
						gInst.fp.dump_tick.set(gInst.fp.imidi[gInst.fp.gstrk].tick.tick());
						gInst.disp.disp_send(1031, 0L, 0L);
						gInst.disp.disp_send(1030, 0L, 0L);
					} else
					{
						gInst.disp.disp_send(1032, 0L, 0L);
					}

				if (!gInst.mlyric_mode)
					gInst.fp.note4_timer = gInst.fp.imidi[gInst.fp.gstrk].tick.tick();
			} else
			{
				if (gInst.fp.step[gInst.fp.step_inx] == 0 && gInst.fp.imidi[gInst.fp.gstrk].addr.valid())
					erase_line_setup();
				if (gInst.fp.step[gInst.fp.step_inx] > 0)
					erase_char_setup(gInst);
			}
		}
		if (gsaddr > 0L && !gInst.fp.imidi[gInst.fp.gstrk].addr.valid())
			gInst.fp.dump_timer = gInst.fp.interval_timer = 0L;
		if ((gInst.ctrl_status & 0x20) == 32)
			return no;
		if (gInst.fp.dump_timer > 0L && --gInst.fp.dump_timer == 0L)
		{
			if (gInst.fp.imidi[gInst.fp.gstrk].addr.valid())
				calc_dump_tick(gInst);
			if (skip_mlyric_line())
			{
				gInst.disp.disp_send(1031, 0L, 0L);
				gInst.disp.disp_send(1030, 0L, 0L);
			} else
			{
				gInst.disp.disp_send(1032, 0L, 0L);
			}
		}
		if (gInst.fp.interval_timer > 0L && --gInst.fp.interval_timer == 0L)
		{
			if (gInst.fp.imidi[gInst.fp.gstrk].addr.valid() && gInst.fp.lyric_ptr.valid())
			{
				gInst.disp.disp_send(1033, 0L, 0L);
				gInst.fp.intv_logo_timer = gInst.fp.imidi[gInst.fp.gstrk].tick.tick();
				if (State.maker_logo && gInst.fp.intv_logo_timer > gInst.fp.madi_tick.tick() * 2L)
				{
					gInst.disp.disp_send(1034, 0L, 0L);
					gInst.fp.intv_logo_counter = 0;
				} else
				{
					gInst.fp.intv_logo_timer = 0L;
				}
			} else
			{
				gInst.disp.disp_send(1029, 0L, 0L);
			}
		} else if (gInst.fp.erase_timer > 0L && --gInst.fp.erase_timer == 0L)
			gInst.disp.disp_send(1029, 0L, 0L);
		if (gInst.fp.intv_logo_timer > 0L)
		{
			gInst.fp.intv_logo_timer--;
			if (gInst.fp.intv_logo_timer > gInst.fp.madi_tick.tick() * 2L)
			{
				if ((++gInst.fp.intv_logo_counter) >= gInst.fp.madi_tick.tick() * 2L)
				{
					gInst.disp.disp_send(1034, 0L, 0L);
					gInst.fp.intv_logo_counter = 0;
				}
			} else
			{
				gInst.fp.intv_logo_timer = 0L;
			}
		}
		if (gInst.fp.note4_timer > 0L)
		{
			gInst.fp.note4_timer--;
			if (gInst.fp.note4_timer % gInst.fp.timebase == 0L && gInst.fp.dlyric[0].x > 0)
			{
				gInst.fp.note4_x = gInst.fp.dlyric[0].x;
				gInst.fp.note4_y = gInst.fp.dlyric[0].y - 38;
				if ((gInst.ctrl_status & 0x10) != 16)
					gInst.disp.disp_send(1037, 0L, (int) (gInst.fp.note4_timer / gInst.fp.timebase));
			}
			if (gInst.fp.note4_timer == 0L)
				gInst.fp.erase4_timer = gInst.fp.timebase;
		}
		if (gInst.fp.erase4_timer > 0 && --gInst.fp.erase4_timer == 0)
			gInst.disp.disp_send(1037, 0L, 65535L);
		if (gInst.fp.dump_tick.valid())
			gInst.fp.dump_tick.dec();
		if (gInst.fp.interval_timer > 0L || gInst.fp.erase_timer > 0L)
			no++;
		return no;
	}

	int play_track()
	{
		Global gInst = Global.Inst();
		if (gInst.fp.ending_tick > 0L)
			gInst.fp.ending_tick--;
		if (gInst.fp.madi_delay_tick > 0 && --gInst.fp.madi_delay_tick == 0)
			gInst.fp.imidi[gInst.fp.gstrk].para = '\377';
		if (gInst.fp.madi_delay_tick == 0)
			KRhythm.rhythm_play();
		KMidi.note_check();
		long gsaddr = gInst.fp.imidi[gInst.fp.gstrk].addr.pos();
		int no;
		for (int i = no = 0; i < gInst.fp.mtrks; i++)
		{
			mtrk_t mp = gInst.fp.imidi[i];
			if (mp.addr.valid() && (mp.mode & 4) != 4)
			{
				if (mp.delay_tick > 0L && --mp.delay_tick == 0L)
				{
					for (int j = 0; j < mp.delay_cnt; j++)
						Serial.MIDI_SEND3(mp.type, mp.last_cmd, mp.delay_note[j], 0);

					mp.delay_cnt = '\0';
				}
				mp.last_cnt = '\0';
				mp.tick.dec(mp.speed);
				while (!mp.tick.valid() || (mp.tick.tick() & 0xffffffff80000000L) == 0xffffffff80000000L)
				{
					mp.addr = KMidi.play_midi(mp.addr, mp.type, i, mp);
					if (!mp.addr.valid())
						break;
					long tick = KMemory.get_midi_tick(mp.addr);
					mp.tick.inc(tick);
					if (i == gInst.fp.gstrk && !mp.tick.valid())
						mp.tick.set(1L);
				}
				if ((mp.type & 0xfff0) != 32 && (mp.type & 0xfff0) != 48)
					no++;
			}
		}

		if ((gInst.ctrl_status & 0x800) == 2048)
			no++;
		if (gInst.fp.madi_delay_tick > 0)
		{
			return no;
		} else
		{
			no = lyrictask(gInst, no, gsaddr);
			return no;
		}
	}

	void madi_tick_update()
	{
		Global gInst = Global.Inst();
		gInst.fp.madi_cur_tick.inc();
		if (gInst.fp.madi_cur_tick.tick() >= gInst.fp.madi_tick.tick())
		{
			gInst.fp.madi_cur_tick.reset();
			gInst.madi_count++;
			Log.d("ke", (new StringBuilder("MADI UPDATE ")).append(gInst.madi_count).toString());
			int spand_tick;
			for (spand_tick = (int) (gInst.madi_count * gInst.fp.madi_tick.tick()); spand_tick > gInst.fp.chord_cmd[chord_progress].start_tick; chord_progress++)
				;
			int i = 0;
			for (; spand_tick + gInst.fp.madi_tick.tick() > gInst.fp.chord_cmd[chord_progress].start_tick; chord_progress++)
				;
		}
	}

	public void reset_song(int envr)
	{
		Global.Inst().midifs.mfs_all_close();
		int save_envr = KEnvr.envr_get();
		if (save_envr != 256 && save_envr != 257)
			KEnvr.envr_set(260);
		Global gInst = Global.Inst();
		gInst.in_play = false;
		gInst.is_pause = false;
		gInst.madi_count = 0;
		gInst.play_status &= 0xfffffe00;
		gInst.ctrl_status &= 0xffff811e;
		gInst.midi_key = 0;
		gInst.midi_sex = 32767;
		gInst.midi_new = false;
		gInst.midi_speed = 0;
		gInst.mlyric_mode = false;
		KRhythm.Set(0);
		gInst.fp.step_beat_cmd = 0;
		KBook.disp_led_sno();
		if (gInst.cur_fh > 0)
		{
			gInst.midifs.mfs_close(gInst.cur_fh);
			gInst.cur_fh = 0;
		}
		chord_progress = 0;
		buf_chod[0] = '\0';
		if (save_envr != 256 && save_envr != 257)
			KEnvr.envr_set(envr);
		if (ViewManager.Inst().lpBottomMenu != null)
			ViewManager.Inst().lpBottomMenu.UpdateBottomStatus();
	}

	void scrolling_lyric(Global gInst)
	{
		gInst.fp.cur_tick++;
		int w;
		if (gInst.fp.char_tick > gInst.fp.char_width)
		{
			int t = (int) (gInst.fp.char_tick / gInst.fp.char_width);
			if (gInst.fp.char_tick % gInst.fp.char_width > 0L)
				t++;
			if (gInst.fp.cur_tick < t)
				return;
			w = 1;
		} else
		{
			w = (int) (gInst.fp.char_width / gInst.fp.char_tick);
			if (gInst.fp.char_width % gInst.fp.char_tick > 0L)
				w++;
		}
		gInst.fp.char_tick -= gInst.fp.cur_tick;
		gInst.fp.cur_tick = 0L;
		gInst.fp.char_width -= w;
		if ((gInst.ctrl_status & 2) != 2)
		{
			if (gInst.fp.y == 1)
			{
				if (ViewManager.Inst().lpLyric1View != null)
					ViewManager.Inst().lpLyric1View.setScrolling(gInst.fp.x);
			} else if (ViewManager.Inst().lpLyric2View != null)
				ViewManager.Inst().lpLyric2View.setScrolling(gInst.fp.x);
			gInst.fp.x += w;
		}
	}

	public void infor_song()
	{
		Global gInst = Global.Inst();
		int ftype = gInst.fp.ftype;
		gInst.fp = gInst.midifs.mfs_table(gInst.cur_fh);
		Log.i("ke", (new StringBuilder("gInst.fp.ending_tick = ")).append(gInst.fp.ending_tick).toString());
		gInst.fp.ftype = ftype;
		gInst.fp.geol_current = 0;
		gInst.fp.current_lyric_pos_update = true;
	}

	public void start_song(int msg)
	{
		if (!DataHandler.isReachable)
		{
			Global.Inst().app.doMenu(2);
			return;
		}
		Log.i("ke", "-------------------- start_song() -------------------");
		Global gInst = Global.Inst();
		if (gInst.midi_sex == 32767)
			gInst.midi_sex = gInst.fp.song.key.sex;
		gInst.ctrl_status &= 0xfffffff3;
		KMadi.madi_tick_init(gInst.fp.song.numerator, gInst.fp.song.denominator);
		KMadi.madi_midi_init();
		if (KEnvr.is(258))
		{
			gInst.disp.disp_send(1028, 0L, 0L);
			if (gInst.fp.imidi[gInst.fp.gstrk].addr.valid())
				gInst.disp.disp_send(1030, 0L, 0L);
		} else
		{
			gInst.fp.imidi[gInst.fp.gstrk].addr = new midi_pos_t(0L);
			gInst.fp.imidi[gInst.fp.gstrk].tick = new midi_tick_t(0L);
			gInst.fp.lyric_ptr = new midi_pos_t(0L);
			gInst.fp.lyric_pos = new midi_pos_t(0L);
			gInst.fp.lyric_fptr = new midi_pos_t(0L);
			gInst.fp.lyric_mptr = new midi_pos_t(0L);
		}
		gInst.is_pause = false;
		gInst.in_play = true;
		Etc.mode_add_interval();
		if (ViewManager.Inst().lpBottomMenu != null)
			ViewManager.Inst().lpBottomMenu.UpdateBottomStatus();
	}

	void calc_dump_tick(Global gInst)
	{
		mtrk_t tmp = null;
		gInst.fp.dump_tick.set(gInst.fp.imidi[gInst.fp.gstrk].tick.tick());
		try
		{
			tmp = (mtrk_t) gInst.fp.imidi[gInst.fp.gstrk].clone();
		} catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		for (int i = 1; gInst.fp.step[i] > 0; i++)
		{
			if ((gInst.fp.step[i] & 0x8000) == 32768)
				continue;
			tmp.addr = KMidi.play_midi(tmp.addr, tmp.type, gInst.fp.gstrk, tmp);
			if (!tmp.addr.valid())
				break;
			long tick = KMemory.get_midi_tick(tmp.addr);
			gInst.fp.dump_tick.inc(tick);
		}

	}

	int get_text_width(long _cc, int fsize, int country)
	{
		long cc = _cc & 0xffffffffL;
		if (cc == 32L)
			return 40;
		if (cc == 45L)
			return 40;
		if (cc == 105L || cc == 108L)
			return 26;
		if (cc == 73L)
			return 28;
		if (cc == 39L)
			return 20;
		if (cc == 87L)
			return 58;
		if (cc <= 90L)
			return 48;
		if (cc < 128L)
			return 48;
		return country != 1 ? 96 : 48;
	}

	int make_banner(int ic, int oc, int ric, int roc, int fsize, int ftype, int country,
			LY _ls, LY _rs1, LY _rs2, char step[], boolean rubi_present, boolean korea_rubi, boolean japan_rubi)
	{
		int step_inx = 0;
		LY ls = _ls;
		LY rs1 = _rs1;
		LY rs2 = _rs2;
		ls.repose();
		rs1.repose();
		rs2.repose();
		if (rs1.valid())
			rs1.inc();
		if (rs2.valid())
			rs2.inc();
		if (rubi_present && (korea_rubi || japan_rubi))
		{
			if (korea_rubi && japan_rubi)
			{
				int rpos2 = 24;
				int lpos = 46;
			} else if (korea_rubi)
			{
				int rpos2 = 0;
				int lpos = 24;
				rs2.set((byte) 0);
			} else if (japan_rubi)
			{
				int rpos2 = 0;
				int lpos = 24;
				rs1.set((byte) 0);
			}
		} else
		{
			int lpos = 0;
			rs1.set((byte) 0);
			rs2.set((byte) 0);
		}
		int x = 0;
		int rz = get_text_width(32896L, 3, 4);
		int krx = x;
		rz = get_text_width(32896L, fsize, country) - get_text_width(32896L, 3, 4) * 3;
		boolean flag;
		if (rz > KOS.TCOLS)
			flag = false;
		while (ls.valid())
			if ((ls.at() & 0x80) == 128 && (country == 0 || country == 3 || country == 17 || country == 11 || country == 4))
			{
				int rx = x;
				long cc = ls.at();
				ls.inc();
				cc <<= 8;
				cc += ls.at();
				ls.inc();
				int xz = 64;
				int yz = 32;
				if (country != 14 || !THAILAND_HALF_CODE(ls.at()))
				{
					int wid = get_text_width(cc, fsize, country);
					step[step_inx] = (char) wid;
					x += wid;
					if (ls.at() == 32 || ls.at() == 0)
						step[step_inx] += '\002';
					step_inx++;
				}
			} else if (ls.at() >= 65 && ls.at() <= 90 || ls.at() >= 97 && ls.at() <= 122)
			{
				int rx = x;
				for (int n = 0; ls.at() >= 65 && ls.at() <= 90 || ls.at() >= 97 && ls.at() <= 122; n++)
				{
					long cc = ls.at();
					ls.inc();
					int xz = 64;
					int yz = 32;
					if (country != 14 || !THAILAND_HALF_CODE(ls.at()))
						x += get_text_width(cc, fsize, country);
				}

				step[step_inx] = (char) (x - rx);
				if (ls.at() == 32 || ls.at() == 0)
					step[step_inx] += '\002';
				step_inx++;
			} else
			{
				int rx = x;
				long cc = ls.at();
				ls.inc();
				if (cc != 32L)
				{
					int xz = 64;
					byte byte0 = 32;
				}
				if (country != 14 || !THAILAND_HALF_CODE(ls.at()))
				{
					int wid = get_text_width(cc, fsize, country);
					step[step_inx] = (char) wid;
					x += wid;
					if (cc == 32L)
						step[step_inx] += '\u7FFE';
					if (ls.at() == 32 || ls.at() == 0)
						step[step_inx] += '\002';
					step_inx++;
				}
				rs2.valid();
			}
		step[step_inx] = '\0';
		if (krx > x)
			return krx;
		else
			return x;
	}

	private static final String TAG = Player.class.getSimpleName();
	private Lock lock;
	private String tmp_uLyric;
	int chord_progress;
	char buf_chod[];
	final int ADD_X = 2;
	PlayerThread mThread;

}
