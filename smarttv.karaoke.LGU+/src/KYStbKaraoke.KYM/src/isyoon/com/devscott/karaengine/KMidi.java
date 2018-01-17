// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   KMidi.java

package isyoon.com.devscott.karaengine;

import android.util.Log;

import kr.kumyoung.gtvkaraoke.BuildConfig;

// Referenced classes of package com.devscott.karaengine:
//            MainBuffer, mtrk_t, Global, song_t, 
//            note_t, Serial, Etc, KEnvr, 
//            State, sdrum_t, midi_pos_t, KMemory, 
//            KMadi, end_t, midi_tick_t

public class KMidi
{

	public KMidi()
	{
	}

	public static midi_pos_t play_midi(midi_pos_t p, char type, int inx, mtrk_t mp)
	{
		return play_midi(p, type, inx, mp, false);
	}

	public static midi_pos_t play_midi(midi_pos_t p, char type, int inx, mtrk_t mp, boolean nomsg)
	{
		char para1 = '\0';
		char para2 = '\0';
		char cmd = MainBuffer.read_midi_byte(p);
		if (cmd < '\200')
		{
			cmd = mp.cmd;
		} else
		{
			cmd = MainBuffer.get_midi_byte(p);
			mp.cmd = cmd;
		}
		if ((cmd & 0xf0) == 128 || (cmd & 0xf0) == 144)
		{
			para1 = MainBuffer.get_midi_byte(p);
			para2 = MainBuffer.get_midi_byte(p);
			if (para2 == 0)
				cmd = '\200';
		}
		label0: switch (cmd & 0xf0)
		{
		case 128: {
			if ((Global.Inst().ctrl_status & 0x10) == 16 || Global.Inst().IsCtrl(2048) && (mp.mode & 3) > 0)
				break;
			char spara1 = para1;
			if (!Global.Inst().fp.note[inx][spara1].is((short) 255))
			{
				para1 = (char) Global.Inst().fp.note[inx][spara1].get();
				Global.Inst().fp.note[inx][spara1].set((short) 255);
			}
			Serial.MIDI_SEND3(type, cmd, para1, para2);
			break;
		}

		case 144: {
			if (type >= '\u0110')
			{
				switch (type)
				{
				case 278:
				default:
					break;

				case 276:
				case 277:
					mp.para = para1;
					if (!nomsg)
					{
						Etc.mode_del_interval();
						Etc.debug_geol(para1);
					}
					break;

				case 275:
					Global.Inst().fp.bass_code = para1;
					break;
				}
				break;
			}
			if (Global.Inst().IsCtrl(16) || Global.Inst().IsCtrl(2048) && (mp.mode & 3) > 0)
				break;
			char spara1 = para1;
			if (type == '!' || type == '1')
				para1 += bass_code_table[(Global.Inst().midi_key + Global.Inst().fp.bass_code + 12) % 12];
			else if (KEnvr.is(258) && (mp.mode & 1) != 1)
				para1 += Global.Inst().midi_key;
			if (para1 > '\177')
				para1 = '\177';
			if (!Global.Inst().fp.note[inx][spara1].is((short) 255))
				Serial.MIDI_SEND3(type, cmd, Global.Inst().fp.note[inx][spara1].get(), 0);
			Global.Inst().fp.note[inx][spara1].set((short) para1);
			if ((type == Global.Inst().fp.melody_type1 || type == Global.Inst().fp.melody_type2) && KEnvr.is(258))
			{
				switch (State.midi_melody)
				{
				case 0: // '\0'
					para2 = '\0';
					break;

				case 2: // '\002'
					para2 = (char) ((para2 * 125) / 100);
					break;

				case 3: // '\003'
					para2 = (char) ((para2 * 75) / 100);
					break;
				}
				if (para2 > '\177')
					para2 = '\177';
			}
			if (!Global.Inst().mmedley_mode)
			{
				char val = (char) (para2 + mp.veloc);
				val += patch_drum_volume_get(type, mp, para1);
				if (val < 0)
					para2 = '\0';
				else if (val > '\177')
					para2 = '\177';
				else
					para2 = val;
			}
			if (type != Global.Inst().fp.melody_type1 && type != Global.Inst().fp.melody_type2 && type != '\001' && type != '!')
				if (type == '\t')
				;
			Serial.MIDI_SEND3(type, cmd, para1, para2);
			break;
		}

		case 160: {
			para1 = MainBuffer.get_midi_byte(p);
			para2 = MainBuffer.get_midi_byte(p);
			Serial.MIDI_SEND3(type, cmd, para1, para2);
			break;
		}

		case 176: {
			para1 = MainBuffer.get_midi_byte(p);
			para2 = MainBuffer.get_midi_byte(p);
			if (type >= '\u0110' || type == 'C' || type == 'D')
				break;
			Serial.MIDI_SEND3(type, cmd, para1, para2);
			if ((mp.mode & 8) == 8)
				if (para1 == '\025')
					mp.para = para2;
				else
				if (para1 == '\026')
					mp.patch = para2;
			if (para1 == '\007')
			{
				mp.vol = para2;
				if (!Global.Inst().mmedley_mode && mp.patch != '\377')
					patch_volume_change(type, mp);
			}
			if ((mp.mode & 1) == 1)
				if (para1 == 'c')
					mp.old_nrpn = para2;
				else
				if (mp.old_nrpn == '\032')
				{
					if (para1 == 'b')
					{
						mp.nrpn = '\201';
						mp.note = para2;
					} else
					{
						mp.nrpn = '\377';
					}
				} else
				if (mp.nrpn == '\201')
				{
					mp.nrpn = '\377';
					if (para1 == '\006')
						patch_drum_volume_save(type, mp, para2, Global.Inst().fp.cur_drum_vol);
				}
			if (para1 == 'e')
			{
				mp.old_rpn = para2;
				break;
			}
			if (mp.old_rpn == 0)
			{
				if (para1 == 'd' && para2 == 0)
					mp.rpn = '\201';
				else
					mp.rpn = '\377';
				break;
			}
			if (mp.rpn != '\201')
				break;
			if (para1 == '\006')
				mp.rpn = para2;
			else
				mp.rpn = '\377';
			break;
		}

		case 192: {
			para1 = MainBuffer.get_midi_byte(p);
			if (type >= '\u0110')
			{
				switch (type)
				{
				case 279:
				default:
					break;

				case 274:
					if (para1 <= '\004')
						mp.para = para1;
					break;
				}
				break;
			}
			Serial.MIDI_SEND3(type, cmd, para1, -1);
			mp.patch = para1;
			if (type == Global.Inst().fp.melody_type1 || type == Global.Inst().fp.melody_type2)
			{
				mp.para = State.Inst().patch_volume[7];
				patch_volume_change(type, mp);
				break;
			}
			if ((mp.mode & 2) == 2)
			{
				mp.para = State.Inst().patch_volume[8];
				patch_volume_change(type, mp);
				break;
			}
			if ((mp.mode & 1) == 1)
			{
				mp.para = State.Inst().patch_volume[9];
				patch_volume_change(type, mp);
				for (int i = 0; i < 8; i++)
				{
					for (int j = 0; j < 10; j++)
						Global.Inst().fp.cur_drum_vol[i][j].reset();

				}

			} else
			{
				mp.para = State.Inst().patch_volume[patch_type[para1]];
				patch_volume_change(type, mp);
			}
			break;
		}

		case 208: {
			para1 = MainBuffer.get_midi_byte(p);
			Serial.MIDI_SEND3(type, cmd, para1, -1);
			break;
		}

		case 224: {
			para1 = MainBuffer.get_midi_byte(p);
			para2 = MainBuffer.get_midi_byte(p);
			Serial.MIDI_SEND3(type, cmd, para1, para2);
			break;
		}

		default: {
			switch (cmd)
			{
			case 255:
				char cmdx = MainBuffer.get_midi_byte(p);
				switch (cmdx)
				{
				case 47: // '/'
				{
					p.disable();
					break label0;
				}

				case 88: // 'X'
				{
					long length = KMemory.get_midi_tick(p);
					char numerator = MainBuffer.get_midi_byte(p);
					char denominator = MainBuffer.get_midi_byte(p);
					p.inc(length - 2L);
					denominator = (char) (1 << denominator);
					KMadi.madi_tick_init((short) numerator, (short) denominator);
					break label0;
				}

				case 81: // 'Q'
				{
					long length = KMemory.get_midi_tick(p);
					Global.Inst().fp.tempo_us = 0L;
					for (int i = 0; i < 3; i++)
					{
						Global.Inst().fp.tempo_us <<= 8;
						para1 = MainBuffer.get_midi_byte(p);
						Global.Inst().fp.tempo_us += para1;
					}

					p.inc(length - 3L);
					Etc.set_play_timer();
					break;
				}

				case 33: // '!'
				{
					long length = KMemory.get_midi_tick(p);
					p.inc(length);
					break;
				}

				default: {
					long length = KMemory.get_midi_tick(p);
					p.inc(length);
					break;
				}
				}
				break;

			case 240:
			case 247:
				p = midi_send_var(type, cmd, p);
				break;

			case 241:
			case 243:
				p.inc();
				break;

			case 242:
				p.inc(2);
				break;

			default:
				Log.e("ke", (new StringBuilder("invalid MIDI event : type=")).append(type).append(" cmd=").append(cmd).toString());
				break;
			}
			break;
		}
		}
		return p;
	}

	public static void patch_volume_change(int type, mtrk_t mp)
	{
		int user;
		if (mp.para > '\177')
			user = mp.para - 256;
		else
			user = mp.para;
		char val = (char) (user + mp.vol);
		if (Global.Inst().fp.midi_4port_on && type > 0 && type < 32)
			val = (char) ((val * State.ch4_volume[type]) / 100);
		else if (Global.Inst().fp.midi_4port_on && ((type & 0xfff0) == 32 || (type & 0xfff0) == 48))
			val = (char) ((val * State.ch4_volume[type - 32]) / 100);
		char para;
		if (val < 0)
		{
			mp.veloc = val;
			para = '\0';
		} else if (val > '\177')
		{
			mp.veloc = (char) (val - 127);
			para = '\177';
		} else
		{
			mp.veloc = '\0';
			para = val;
		}
		Serial.MIDI_SEND3(type, 176 + type % 16, 7, para);
	}

	public static void patch_drum_volume_save(char type, mtrk_t mp, char volume, sdrum_t save[][])
	{
		int n = patch_drum_inx_get(type);
		int i;
		for (i = 0; i < 10; i++)
			if (save[n][i].note == mp.note || save[n][i].note == '\377')
				break;

		if (i >= 10)
			return;
		sdrum_t sp = save[n][i];
		sp.vol = volume;
		sp.note = mp.note;
		switch (mp.patch)
		{
		case 56: // '8'
		case 57: // '9'
			i = 5;
			break;

		case 48: // '0'
			i = drum_orchestra[mp.note];
			break;

		case 50: // '2'
			i = drum_kick[mp.note];
			break;

		case 64: // '@'
			i = drum_user1[mp.note];
			break;

		case 65: // 'A'
			i = drum_user2[mp.note];
			break;

		default:
			i = drum_standard[mp.note];
			break;
		}
		char val;
		if (Global.Inst().mmedley_mode)
			val = '\0';
		else
			val = State.Inst().patch_drum_volume[i];
		val += sp.vol;
		char para;
		if (val < 0)
		{
			sp.veloc = val;
			para = '\0';
		} else if (val > '\177')
		{
			sp.veloc = (char) (val - 127);
			para = '\177';
		} else
		{
			sp.veloc = '\0';
			para = val;
		}
		if (save == Global.Inst().fp.cur_drum_vol)
		{
			Serial.MIDI_SEND3(type, 176 + type % 16, 98, sp.note);
			Serial.MIDI_SEND3(type, 176 + type % 16, 6, para);
		}
	}

	public static void patch_drum_volume_change(char type, mtrk_t mp)
	{
		int n = patch_drum_inx_get(type);
		for (int i = 0; i < 10; i++)
			if (Global.Inst().fp.cur_drum_vol[n][i].note < '\200')
			{
				sdrum_t sp = Global.Inst().fp.cur_drum_vol[n][i];
				int j;
				switch (mp.patch)
				{
				case 56: // '8'
				case 57: // '9'
					j = 5;
					break;

				case 48: // '0'
					j = drum_orchestra[sp.note];
					break;

				case 50: // '2'
					j = drum_kick[sp.note];
					break;

				case 64: // '@'
					j = drum_user1[sp.note];
					break;

				case 65: // 'A'
					j = drum_user2[sp.note];
					break;

				default:
					j = drum_standard[sp.note];
					break;
				}
				char val;
				if (Global.Inst().mmedley_mode)
					val = '\0';
				else
					val = State.Inst().patch_drum_volume[j];
				val += sp.vol;
				char para;
				if (val < 0)
				{
					sp.veloc = val;
					para = '\0';
				} else if (val > '\177')
				{
					sp.veloc = (char) (val - 127);
					para = '\177';
				} else
				{
					sp.veloc = '\0';
					para = val;
				}
				Serial.MIDI_SEND3(type, 176 + type % 16, 99, 26);
				Serial.MIDI_SEND3(type, 176 + type % 16, 98, sp.note);
				Serial.MIDI_SEND3(type, 176 + type % 16, 6, para);
			}

	}

	/**
	 * isyoon:기본도안되는게병신지랄을하고자빠졌네.테스트나똑바로해.이병신아.
	 */
	public static midi_pos_t skip_midi(midi_pos_t p, char type, int inx, mtrk_t mp)
	{
		char cmd = MainBuffer.read_midi_byte(p);
		if (BuildConfig.DEBUG) Log.d("[KP]" + "KMidi", "[skip_midi](cmd & 0xff)" + cmd + ":" + mp.cmd + "-" + (cmd & 0xff));
		if ((cmd & 0xff) < 128)
		{
			cmd = mp.cmd;
		} else
		{
			cmd = MainBuffer.get_midi_byte(p);
			mp.cmd = cmd;
		}
		if (BuildConfig.DEBUG) Log.wtf("[KP]" + "KMidi", "[skip_midi](cmd & 0xff & 0xf0)" + cmd + ":" + mp.cmd + "-" + (cmd & 0xff & 0xf0));
		label0: switch (cmd & 0xff & 0xf0)
		{
		case 128: {
			p.inc(2);
			break;
		}

		case 144: {
			char para1 = MainBuffer.get_midi_byte(p);
			char para2 = MainBuffer.get_midi_byte(p);
			if (para2 <= 0 || type < '\u0110')
				break;
			switch (type)
			{
			case 276:
			case 277:
				mp.para = para1;
				Etc.mode_del_interval();
				Etc.debug_geol(para1);
				break;

			case 275:
				Global.Inst().fp.ending.bass_code = para1;
				break;
			}
			break;
		}

		case 160: {
			p.inc();
			p.inc();
			break;
		}

		case 176: {
			char para1 = MainBuffer.get_midi_byte(p);
			char para2 = MainBuffer.get_midi_byte(p);
			if (type >= '\u0110')
				break;
			if (para1 == '\007')
				mp.vol = para1;
			if ((mp.mode & 1) == 1)
				if (para1 == 'c')
					mp.old_nrpn = para2;
				else
				if (mp.old_nrpn == '\032')
				{
					if (para1 == 'b')
					{
						mp.nrpn = '\201';
						mp.note = para2;
					} else
					{
						mp.nrpn = '\377';
					}
				} else
				if (mp.nrpn == '\201')
				{
					mp.nrpn = '\377';
					if (para1 == '\006')
					{
						Log.d("ke", (new StringBuilder("DRUM note(NRPN)(0x")).append(type).append(") : ").append(mp.note).append(" vol=").append(para2).toString());
						patch_drum_volume_save(type, mp, para2, Global.Inst().fp.old_drum_vol);
					}
				}
			if (para1 == 'e')
			{
				mp.old_rpn = para2;
				break;
			}
			if (mp.old_rpn == 0)
			{
				if (para1 == 'd' && para2 == 0)
					mp.rpn = '\201';
				else
					mp.rpn = '\377';
				break;
			}
			if (mp.rpn != '\201')
				break;
			if (para1 == '\006')
			{
				mp.rpn = para2;
				Log.d("ke", (new StringBuilder("RPN : ")).append(para2).toString());
			} else
			{
				mp.rpn = '\377';
			}
			break;
		}

		case 192: {
			char para1 = MainBuffer.get_midi_byte(p);
			if (type >= '\u0110')
			{
				switch (type)
				{
				case 279:
				default:
					break;

				case 274:
					if (para1 <= '\004')
						mp.para = para1;
					break;
				}
				break;
			}
			mp.patch = para1;
			if (type == Global.Inst().fp.melody_type1 || type == Global.Inst().fp.melody_type2)
			{
				mp.para = State.Inst().patch_volume[7];
				break;
			}
			if ((mp.mode & 2) == 2)
			{
				mp.para = State.Inst().patch_volume[8];
				break;
			}
			if ((mp.mode & 1) == 1)
			{
				mp.para = State.Inst().patch_volume[9];
				for (int i = 0; i < 8; i++)
				{
					for (int j = 0; j < 10; j++)
						Global.Inst().fp.cur_drum_vol[i][j].reset();

				}

			} else
			{
				mp.para = State.Inst().patch_volume[patch_type[para1]];
			}
			break;
		}

		case 208: {
			p.inc();
			break;
		}

		case 224: {
			p.inc(2);
			break;
		}

		default: {
			switch (cmd) {
				case 255:
					char cmdx = MainBuffer.get_midi_byte(p);
					/**
					 * isyoon:기본도안되는게병신지랄을하고자빠졌네.테스트나똑바로해.이병신아.
					 */
					switch (cmdx) {
						case 47: // '/'
						{
							p.disable();
							break label0;
						}
						//case 88: // 'X'
						//	{
						//		/**
						//		 * isyoon:기본도안되는게병신지랄을하고자빠졌네.테스트나똑바로해.이병신아.
						//		 */
						//		long length = KMemory.get_midi_tick(p);
						//		char numerator = MainBuffer.get_midi_byte(p);
						//		char denominator = MainBuffer.get_midi_byte(p);
						//		try {
						//			Global.Inst().fp.ending.madi_tick.set((numerator * 4 * Global.Inst().fp.timebase) / (1 << denominator));
						//		} catch (Exception e) {
						//			if (BuildConfig.DEBUG) Log.wtf("[KP]" + "KMidi", "[ERROR][88]" + Global.Inst().fp + ":" + Global.Inst().fp.ending);
						//			e.printStackTrace();
						//		}
						//		p.inc(length - 2L);
						//		break;
						//	}
						//case 81: // 'Q'
						//	{
						//		/**
						//		 * isyoon:기본도안되는게병신지랄을하고자빠졌네.테스트나똑바로해.이병신아.
						//		 */
						//		long length = KMemory.get_midi_tick(p);
						//		try {
						//			Global.Inst().fp.ending.tempo_us = 0L;
						//			for (int i = 0; i < 3; i++)
						//			{
						//				Global.Inst().fp.ending.tempo_us <<= 8;
						//				char para1 = MainBuffer.get_midi_byte(p);
						//				Global.Inst().fp.ending.tempo_us += para1;
						//			}
						//		} catch (Exception e) {
						//			if (BuildConfig.DEBUG) Log.wtf("[KP]" + "KMidi", "[ERROR][81]" + Global.Inst().fp + ":" + Global.Inst().fp.ending);
						//			e.printStackTrace();
						//		}
						//		p.inc(length - 3L);
						//		break;
						//	}
						default: {
							long length = KMemory.get_midi_tick(p);
							p.inc(length);
							break;
						}
					}
					break;

				case 240:
				case 247:
					long length = KMemory.get_midi_tick(p);
					p.inc(length);
					break;

				case 241:
				case 243:
					p.inc();
					break;

				case 242:
					p.inc(2);
					break;

				default:
					p.disable();
					break;
			}
			break;
		}
		}
		return p;
	}

	private static int patch_drum_volume_get(char type, mtrk_t mp, char note)
	{
		if (Global.Inst().mmedley_mode)
			return 0;
		if ((mp.mode & 1) != 1)
			return 0;
		int n = patch_drum_inx_get(type);
		int i;
		for (i = 0; i < 10; i++)
			if (Global.Inst().fp.cur_drum_vol[n][i].note == note)
				break;

		if (i < 10)
			return Global.Inst().fp.cur_drum_vol[n][i].veloc;
		switch (mp.patch)
		{
		case 56: // '8'
		case 57: // '9'
			i = 5;
			break;

		case 48: // '0'
			i = drum_orchestra[note];
			break;

		case 50: // '2'
			i = drum_kick[note];
			break;

		case 64: // '@'
			i = drum_user1[note];
			break;

		case 65: // 'A'
			i = drum_user2[note];
			break;

		default:
			i = drum_standard[note];
			break;
		}
		return State.Inst().patch_drum_volume[i];
	}

	static boolean memcmp(char a[], char b[], int skip_byte, int n)
	{
		for (int i = 0; i < n; i++)
			if (a[i] != b[i + skip_byte])
				return false;

		return true;
	}

	private static midi_pos_t midi_send_var(char type, char cmd, midi_pos_t pos)
	{
		char xcmd[] = new char[8];
		long length = KMemory.get_midi_tick(pos);
		midi_pos_t ptr = new midi_pos_t();
		ptr.set(pos.pos());
		for (int i = 0; i < 8; i++)
		{
			char para = MainBuffer.get_midi_byte(ptr);
			xcmd[i] = para;
		}

		if (!memcmp(xcmd, mode2_cmd, 1, 8) || !memcmp(xcmd, mode1_cmd, 1, 8) || !memcmp(xcmd, channel4_cmd, 1, 8))
		{
			pos.inc(length);
		} else
		{
			Serial.MIDI_SEND3(type, cmd, -1, -1);
			for (int i = 0; i < length; i++)
			{
				char para = MainBuffer.get_midi_byte(pos);
				Serial.MIDI_SEND3(type, para, -1, -1);
			}

		}
		return pos;
	}

	public void midi_sound_off()
	{
	}

	public static void midi_note_off()
	{
	}

	public void midi_hw_reset()
	{
		Log.d("ke", "CMidi::midi_reset()");
	}

	public static void note_check()
	{
	}

	public void midi_mode1()
	{
	}

	public void midi_4channel()
	{
	}

	public void midi_volume(int i)
	{
	}

	public void ment_volume()
	{
	}

	public void bgm_volume()
	{
		midi_volume(State.bgm_volume);
	}

	public void ment_pcm_volume()
	{
	}

	public static short patch_drum_inx_get(char type)
	{
		short n = 0;
		switch (type)
		{
		case 9: // '\t'
		case 10: // '\n'
			n++;
			// fall through

		case 25: // '\031'
			n++;
			// fall through

		case 26: // '\032'
			n++;
			// fall through

		case 41: // ')'
			n++;
			// fall through

		case 42: // '*'
			n++;
			// fall through

		case 57: // '9'
			n++;
			// fall through

		case 58: // ':'
			n++;
			// fall through

		default:
			return n;
		}
	}

	public void sc88_userdrum()
	{
	}

	private static char mode2_cmd[] = {
			'\360', 'A', '\020', 'B', '\022', '\0', '\0', '\177', '\001', '\0',
			'\367'
	};
	private static char channel4_cmd[] = {
			'\360', 'A', '\020', 'B', '\022', '\0', '\0', '\005', '\001', 'z',
			'\367'
	};
	private static char mode1_cmd[] = {
			'\360', 'A', '\020', 'B', '\022', '\0', '\0', '\177', '\0', '\001',
			'\367'
	};
	private static char gs_reset_cmd[] = {
			'\360', 'A', '\020', 'B', '\022', '@', '\0', '\177', '\0', 'A',
			'\367'
	};
	private static char port1drum2_cmd[] = {
			'\360', 'A', '\020', 'B', '\022', '@', '\032', '\025', '\002', '\017',
			'\367'
	};
	private static char port2drum2_cmd[] = {
			'\360', 'A', '\020', 'B', '\022', 'P', '\032', '\025', '\002', '\177',
			'\367'
	};
	private static char master_volume_cmd[] = {
			'\360', 'A', '\020', 'B', '\022', '@', '\0', '\004', '\177', '\0',
			'\367'
	};
	private static short patch_type[] = {
			2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
			2, 2, 2, 2, 2, 2, 3, 3, 3, 3,
			3, 3, 3, 3, 4, 4, 4, 4, 4, 4,
			4, 4, 7, 7, 7, 7, 7, 7, 7, 7,
			5, 5, 5, 5, 5, 5, 4, 6, 5, 5,
			5, 5, 3, 3, 3, 3, 1, 1, 1, 1,
			1, 1, 1, 1, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
			3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
			3, 3, 3, 3, 4, 4, 4, 4, 2, 0,
			5, 0, 2, 6, 6, 6, 6, 6, 6, 6,
			6, 6, 6, 6, 6, 6, 6, 6
	};
	private static short drum_standard[] = {
			6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
			6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
			6, 6, 6, 6, 6, 5, 5, 5, 5, 5,
			5, 5, 5, 5, 5, 0, 0, 2, 1, 5,
			1, 5, 3, 5, 3, 5, 3, 5, 5, 4,
			5, 3, 4, 3, 5, 4, 5, 4, 5, 3,
			5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
			5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
			5, 5, 5, 5, 5, 5, 5, 5, 6, 6,
			6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
			6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
			6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
			6, 6, 6, 6, 6, 6, 6, 6
	};
	private static short drum_orchestra[] = {
			6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
			6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
			6, 6, 6, 6, 6, 5, 5, 3, 3, 3,
			3, 5, 5, 5, 5, 0, 0, 2, 1, 5,
			1, 5, 5, 5, 5, 5, 5, 5, 5, 5,
			5, 5, 5, 5, 5, 4, 5, 4, 5, 4,
			5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
			5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
			5, 5, 5, 5, 5, 5, 5, 5, 5, 6,
			6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
			6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
			6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
			6, 6, 6, 6, 6, 6, 6, 6
	};
	private static short drum_kick[] = {
			6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
			6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
			6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
			6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			1, 1, 6, 6, 6, 6, 6, 6, 6, 6,
			6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
			6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
			6, 6, 6, 6, 6, 6, 6, 6
	};
	private static short drum_user1[] = {
			6, 6, 6, 6, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 6, 5, 5, 5, 5, 5,
			5, 5, 5, 5, 5, 0, 0, 2, 1, 5,
			1, 5, 3, 5, 3, 5, 3, 5, 5, 4,
			5, 3, 4, 3, 5, 4, 5, 4, 5, 3,
			5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
			5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
			5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
			5, 5, 5, 5, 5, 5, 1, 1, 1, 1,
			1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			1, 1, 1, 1, 1, 1, 1, 1
	};
	private static short drum_user2[] = {
			5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
			5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
			5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
			5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
			5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
			5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
			5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
			5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
			5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
			5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
			5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
			5, 5, 5, 5, 5, 5, 1, 1, 1, 1,
			3, 3, 5, 5, 1, 1, 1, 4
	};
	private static short bass_code_table[] = {
			0, 1, 2, 3, 4, 5, 6, -5, -4, -3,
			-2, -1
	};

}
