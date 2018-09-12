// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   KTrack.java

package isyoon.com.devscott.karaengine;

import android.util.Log;

// Referenced classes of package com.devscott.karaengine:
//            song_t, mtrk_t, midi_pos_t, sng_t, 
//            ctrl_t, def, chors_t, geol_t, 
//            chrd_t, KMemory, MainBuffer, Data, 
//            KString

public class KTrack
{

	private boolean RHY_RES_TRACK(song_t table, int i)
	{
		return (table.imidi[i].type & 0xf) == 1 || (table.imidi[i].type & 0xf) == 9 || (table.imidi[i].type & 0xf) == 10;
	}

	public KTrack()
	{
		SC88 = false;
		PORT1 = false;
		CHANNEL4 = false;
		step_beat_type = 0;
		step_madi_count = 0;
		tempo = 0;
		geol_cnt = 0;
		chorus_cnt = 0;
		gasu_cnt = 0;
		chord_cnt = 0;
		MAX_TEMPO_LIST = 2048;
		tempo_list = new long[MAX_TEMPO_LIST];
	}

	String GetTypeName(char n)
	{
		switch (n)
		{
		case 0: // '\0'
			return "effect1";

		case 16: // '\020'
			return "effect2";

		case 65: // 'A'
			return "track_man";

		case 66: // 'B'
			return "track_woman";

		case 67: // 'C'
			return "track_har_mel";

		case 68: // 'D'
			return "track_har";

		case 258:
			return "track_chorus";

		case 259:
			return "track_daesa";

		case 260:
			return "track_gasu";

		case 261:
			return "track_effect";

		case 262:
			return "track_inst";

		case 263:
			return "track_3d";

		case 272:
			return "track_sw";

		case 273:
			return "track_mw";

		case 274:
			return "track_gs";

		case 275:
			return "track_bs";

		case 276:
			return "track_rs1";

		case 277:
			return "track_rs2";

		case 278:
			return "track_fs";

		case 279:
			return "track_vr";

		case 288:
			return "track_step_data";

		case 289:
			return "track_step_cmd";

		case 296:
			return "track_code";

		case 65535:
			return "track_bad";
		}
		return "unknown";
	}

	void midi_track_parsing(song_t table, long song_no)
	{
		boolean type = false;
		long tick = 0L;
		for (int i = 0; i < 71; i++)
			table.imidi[i].type = '\uFFFF';

		SC88 = false;
		PORT1 = false;
		CHANNEL4 = false;
		step_beat_type = 0;
		step_madi_count = 0;
		tempo = 0;
		for (int i = 0; i < table.mtrks; i++)
		{
			Log.d("ke", (new StringBuilder("no ")).append(i).append(" track pos ").append(table.imidi[i].addr.pos()).toString());
			table.imidi[i].type = get_track_type(i, table.imidi[i].addr.pos(), table.song.country);
		}

		Log.i("ke", "Track type_______________________________________");
		for (int i = 0; i < table.mtrks; i++)
			Log.i(
					"ke",
					(new StringBuilder("   ")).append(i).append(" : Addr=").append(table.imidi[i].addr.pos()).append(" Mode=").append(table.imidi[i].mode).append(" Type=").append(table.imidi[i].type).append("(")
							.append(GetTypeName(table.imidi[i].type)).append(")").toString());

		Log.i("ke", "Search rhythm command");
		for (int i = 0; i < table.mtrks; i++)
			switch (table.imidi[i].type)
			{
			case 275:
				table.rhythm_present = true;
				// fall through

			case 276:
			case 277:
				get_ctrl_cmd(table.imidi[i].addr.pos(), table.imidi[i].type, table);
				break;

			case 272:
			case 273:
			case 278:
			case 279:
				get_ctrl_cmd(table.imidi[i].addr.pos(), table.imidi[i].type, table);
				table.imidi[i].type = '\uFFFF';
				break;

			case 262:
				table.imidi[i].type = '\uFFFF';
				break;
			}

		int j;
		if (table.ctrl_cnt > 1)
		{
			for (int i = 0; i < table.ctrl_cnt - 1; i++)
			{
				boolean end = true;
				for (j = 0; j < table.ctrl_cnt - 1; j++)
					if (table.ctrl[j].tick > table.ctrl[j + 1].tick)
					{
						ctrl_t tctrl = table.ctrl[j];
						table.ctrl[j] = table.ctrl[j + 1];
						table.ctrl[j + 1] = tctrl;
						end = false;
					}

				if (end)
					break;
			}

		}
		for (int i = 0; i < table.ctrl_cnt; i++)
			if (table.ctrl[i].tick < def.RHYTHM_START_TICK())
				table.ctrl[i].tick = 0L;
			else
				table.ctrl[i].tick -= def.RHYTHM_START_TICK();

		byte spd = 1;
		type = false;
		for (int i = 0; i < table.ctrl_cnt; i++)
		{
			if (table.ctrl[i].speed == 0)
				table.ctrl[i].speed = spd;
			else
				spd = table.ctrl[i].speed;
			if (table.ctrl[i].file_no == 4)
				type = true;
			else if (table.ctrl[i].file_no == 6)
			{
				type = false;
				table.ctrl[i].file_no = 0;
			}
			if (type && table.ctrl[i].file_no <= 2)
				table.ctrl[i].file_no = 4;
			if (i > 0)
			{
				long pos = table.ctrl[i].tick;
				table.ctrl[i].tick -= tick;
				tick = pos;
			} else
			{
				tick = table.ctrl[i].tick;
			}
		}

		for (int i = 0; i < table.mtrks; i++)
		{
			int port = table.imidi[i].type >> 4;
			if (port >= 2 && port < 4 && RHY_RES_TRACK(table, i))
				table.imidi[i].type = '\uFFFF';
		}

		if (!SC88)
		{
			for (int i = 0; i < table.mtrks; i++)
			{
				if (table.imidi[i].type != '\u0100' && table.imidi[i].type != '\u0101')
					continue;
				SC88 = true;
				break;
			}

		}
		if (SC88)
		{
			for (int i = 0; i < table.mtrks; i++)
				if ((table.imidi[i].type & 0xfff0) == 256)
					if ((table.imidi[i].type & 0xf) >= 4)
					{
						Log.e("ke", (new StringBuilder("invalid wave track(")).append(table.imidi[i].type).append(")").toString());
						table.imidi[i].type = '\uFFFF';
					} else
					{
						table.imidi[i].type += '\002';
					}

		} else
		{
			for (int i = 0; i < table.mtrks; i++)
				if ((table.imidi[i].type & 0xfff0) == 256 && (table.imidi[i].type < '\u0102' || table.imidi[i].type > '\u0105'))
				{
					Log.e("ke", (new StringBuilder("invalid wave track(")).append(table.imidi[i].type).append(")").toString());
					table.imidi[i].type = '\uFFFF';
				}

		}
		table.port1 = PORT1;
		table.channel4 = CHANNEL4;
		geol_cnt = 0;
		chorus_cnt = gasu_cnt = 0;
		chord_cnt = 0;
		for (int i = 0; i < 128; i++)
		{
			table.chorus_cmd[i].start_tick = 0x7fffffffL;
			table.gasu_cmd[i].start_tick = 0x7fffffffL;
			table.geol_cmd[i].start_tick = 0x7fffffffL;
			table.chord_cmd[i].start_tick = 0x7fffffffL;
		}

		for (int i = 0; i < 5; i++)
		{
			for (j = 0; j < table.mtrks; j++)
				if (table.imidi[j].type == wave_track[i])
					break;

			if (j < table.mtrks)
			{
				get_wave_cmd(table.imidi[j].type, table.imidi[j].addr.pos(), table);
				table.imidi[j].type = '\uFFFF';
			}
		}

		for (j = 0; j < table.mtrks; j++)
			if (table.imidi[j].type == '\u0114' || table.imidi[j].type == '\u0115')
				break;

		if (j < table.mtrks)
			get_wave_cmd(table.imidi[j].type, table.imidi[j].addr.pos(), table);
		for (int i = 0; i < chorus_cnt; i++)
		{
			j = table.chorus_cmd[i].file - 1;
			if (!table.chorus_cmd[i].effect && (table.chorus_cmd[i].effect || table.song.chorus_pos[j] == 0L))
				continue;
			table.chorus_present = true;
			break;
		}

		if (!table.chorus_present)
			chorus_cnt = 0;
		for (int i = 0; i < gasu_cnt; i++)
		{
			j = table.gasu_cmd[i].file - 1;
			if (table.gasu_cmd[i].effect_track || !table.gasu_cmd[i].effect && (table.gasu_cmd[i].effect || table.song.gasu_pos[j] == 0L))
				continue;
			table.gasu_present = true;
			break;
		}

		if (!table.gasu_present)
			gasu_cnt = 0;
		if (chorus_cnt > 1)
		{
			for (int i = 0; i < chorus_cnt - 1; i++)
			{
				boolean end = true;
				for (j = 0; j < chorus_cnt - 1; j++)
					if (table.chorus_cmd[j].start_tick > table.chorus_cmd[j + 1].start_tick)
					{
						chors_t tchorus = table.chorus_cmd[j];
						table.chorus_cmd[j] = table.chorus_cmd[j + 1];
						table.chorus_cmd[j + 1] = tchorus;
						end = false;
					}

				if (end)
					break;
			}

		}
		if (gasu_cnt > 1)
		{
			for (int i = 0; i < gasu_cnt - 1; i++)
			{
				boolean end = true;
				for (j = 0; j < gasu_cnt - 1; j++)
					if (table.gasu_cmd[j].start_tick > table.gasu_cmd[j + 1].start_tick)
					{
						chors_t tchorus = table.gasu_cmd[j];
						table.gasu_cmd[j] = table.gasu_cmd[j + 1];
						table.gasu_cmd[j + 1] = tchorus;
						end = false;
					}

				if (end)
					break;
			}

		}
		if (geol_cnt > 1)
		{
			for (int i = 0; i < geol_cnt - 1; i++)
			{
				boolean end = true;
				for (j = 0; j < geol_cnt - 1; j++)
					if (table.geol_cmd[j].start_tick > table.geol_cmd[j + 1].start_tick)
					{
						geol_t tgeol = table.geol_cmd[j];
						table.geol_cmd[j] = table.geol_cmd[j + 1];
						table.geol_cmd[j + 1] = tgeol;
						end = false;
					}

				if (end)
					break;
			}

		}
		table.chorus_cnt = chorus_cnt;
		for (int i = 0; i < chorus_cnt; i++)
			if (table.chorus_cmd[i].stop_tick > table.chorus_cmd[i + 1].start_tick)
				table.chorus_cmd[i].stop_tick = table.chorus_cmd[i + 1].start_tick;

		table.gasu_cnt = gasu_cnt;
		for (int i = 0; i < gasu_cnt; i++)
			if (table.gasu_cmd[i].stop_tick > table.gasu_cmd[i + 1].start_tick)
				table.gasu_cmd[i].stop_tick = table.gasu_cmd[i + 1].start_tick;

		table.geol_cnt = geol_cnt;
		for (int i = 0; i < geol_cnt; i++)
			if (table.geol_cmd[i].stop_tick > table.geol_cmd[i + 1].start_tick)
				table.geol_cmd[i].stop_tick = table.geol_cmd[i + 1].start_tick;

		get_wave_time(table.imidi[0].addr.pos(), table);
	}

	public char get_track_type(int tno, long _pos, short country)
	{
		char old_cmd = '\0';
		long delta_time = 0L;
		char xcmd[] = new char[8];
		midi_pos_t pos = new midi_pos_t(_pos);
		boolean note = false;
		char port = '\0';
		char type = '\uFFFF';
		char ctrl;
		char channel = ctrl = '\377';
		if (country == 14)
			port = '\0';
		int icount = 0;
		long abs_time = 0L;
		while (pos.valid())
		{
			if (icount++ > 50)
			{
				Log.e("ke", "check function - error \uBC1C\uC0DD\uD574\uC11C \uD68C\uD53C\uCF54\uB4DC \uB123\uC5C8\uB2E4. ");
				break;
			}
			delta_time = KMemory.get_midi_tick(pos);
			abs_time += delta_time;
			char cmd = MainBuffer.read_midi_byte(pos);
			if ((cmd & 0xff & 0x80) == 128)
			{
				cmd = MainBuffer.get_midi_byte(pos);
				old_cmd = cmd;
			} else
			{
				cmd = old_cmd;
			}
			switch (cmd & 0xf0)
			{
			case 128:
			case 144:
				note = true;
				// fall through

			case 160:
			case 224:
				pos.inc();
				// fall through

			case 192:
			case 208:
				pos.inc();
				channel = (char) (cmd & 0xf);
				break;

			case 176:
				char para1 = MainBuffer.get_midi_byte(pos);
				char para2 = MainBuffer.get_midi_byte(pos);
				channel = (char) (cmd & 0xf);
				if (para1 == '!' && para2 < '\b')
					ctrl = para2;
				break;

			default:
				switch (cmd & 0xff)
				{
				case 246:
				case 248:
				case 250:
				case 251:
				case 252:
				case 254:
					break;

				case 255:
					cmd = MainBuffer.get_midi_byte(pos);
					switch (cmd)
					{
					case 47: // '/'
					{
						pos.disable();
						break;
					}

					case 33: // '!'
					{
						long length = KMemory.get_midi_tick(pos);
						cmd = MainBuffer.get_midi_byte(pos);
						pos.inc(length - 1L);
						port = cmd;
						break;
					}

					default: {
						long length = KMemory.get_midi_tick(pos);
						pos.inc(length);
						break;
					}
					}
					break;

				case 240:
				case 247:
					long length = KMemory.get_midi_tick(pos);
					if (tno == 0)
					{
						xcmd = new char[8];
						midi_pos_t ptr = pos;
						for (int i = 0; i < 8; i++)
						{
							cmd = MainBuffer.get_midi_byte(ptr);
							xcmd[i] = (char) (cmd & 0xff);
						}

						if (!KString.memcmp(xcmd, Data.mode1_cmd, 8))
							PORT1 = true;
						else if (!KString.memcmp(xcmd, Data.channel4_cmd, 8))
							CHANNEL4 = true;
					}
					pos.inc(length);
					break;

				case 241:
				case 243:
					pos.inc();
					break;

				case 242:
					pos.inc(2);
					break;

				case 244:
				case 245:
				case 249:
				case 253:
				default:
					Log.d("ke", (new StringBuilder("TRACK ")).append(tno).append(" : invalid MIDI data").toString());
					return '\uFFFF';
				}
				break;
			}
			if (channel == '\017' && port >= '\002' && port <= '\003')
			{
				if (note)
				{
					type = (char) (256 + port);
					SC88 = true;
				}
			} else if (channel == 0 && port == '\f')
			{
				type = '\0';
				Log.d("ke", "DREAM 9708 EFFECT CONTROL track");
			} else if (channel == 0 && port == '\r')
			{
				type = '\020';
				Log.d("ke", "DREAM 9708 PORT CONTROL track");
			} else if (country == 14 && channel == '\017')
				type = '\u0112';
			else if (channel == '\017' && port < '\006')
			{
				if (note && port == 0)
					type = (char) (port * 16 + channel);
			} else if (channel == 0 && port == 0)
			{
				if (note)
					type = '\0';
			} else if (channel == '\017' && port == '\017' && ctrl < '\b' && ctrl != '\002')
				type = (char) (272 + ctrl);
			else if (channel >= '\001' && channel < '\005' && port == '\004')
				type = (char) (64 + channel);
			else if (channel < '\020' && port < '\004')
				type = (char) (port * 16 + channel);
			if (type != '\uFFFF')
				break;
		}
		if (type == '\uFFFF')
			if (channel == '\017' && port < '\006')
				type = (char) (256 + port);
			else
			if (channel == 0 && port == 0)
				type = '\u0112';
			else
			if ((country == 3 || country == 17 || country == 1 || country == 5) && channel == 0)
				type = '\u0112';
			else
			if (tno != 0 && channel == '\377' && port == 0)
				type = '\u0128';
			else
			if (channel == '\377')
			{
				type = '\0';
			} else
			{
				type = '\uFFFF';
				Log.e("ke", (new StringBuilder("TRACK ")).append(tno).append(" check plz ------------->  (channel ").append(channel).append(", port ").append(port).toString());
				return type;
			}
		return type;
	}

	private long TickToMS(int cnt, long start_tick, long stop_tick, long _tempo_list[])
	{
		long play_time = 0L;
		long tick = 0L;
		int j;
		for (j = 0; j < cnt; j++)
			if (start_tick < _tempo_list[2 * (j + 1) + 0])
				break;

		if (stop_tick < _tempo_list[2 * (j + 1) + 0])
		{
			tick = stop_tick - start_tick;
			play_time += (_tempo_list[2 * j + 1] * tick) / 1000L;
		} else
		{
			tick = _tempo_list[2 * (j + 1) + 0] - start_tick;
			play_time += (_tempo_list[2 * j + 1] * tick) / 1000L;
			for (j++; j < cnt; j++)
			{
				if (stop_tick < _tempo_list[2 * (j + 1) + 0])
					break;
				tick = _tempo_list[2 * (j + 1) + 0] - _tempo_list[2 * j + 0];
				play_time += (_tempo_list[2 * j + 1] * tick) / 1000L;
			}

			tick = stop_tick - _tempo_list[2 * j + 0];
			play_time += (_tempo_list[2 * j + 1] * tick) / 1000L;
		}
		return play_time;
	}

	private void get_wave_cmd(int ttype, long _pos, song_t table)
	{
		char old_cmd = '\0';
		long delta_time = 0L;
		midi_pos_t pos = new midi_pos_t(_pos);
		long abs_time = 0L;
		while (pos.valid())
		{
			delta_time = KMemory.get_midi_tick(pos);
			abs_time += delta_time;
			char cmd = MainBuffer.read_midi_byte(pos);
			if ((cmd & 0xff & 0x80) == 128)
			{
				cmd = MainBuffer.get_midi_byte(pos);
				old_cmd = cmd;
			} else
			{
				cmd = old_cmd;
			}
			label0: switch (cmd & 0xff & 0xf0)
			{
			case 128:
			case 144: {
				char para1 = MainBuffer.get_midi_byte(pos);
				char para2 = MainBuffer.get_midi_byte(pos);
				pos.dec();
				pos.dec();
				if ((cmd & 0xf0) == 144 && para2 != 0)
					switch (ttype)
					{
					case 276:
					case 277:
						Log.d("ke", (new StringBuilder("\tTRACK_RS : ")).append(abs_time).append(" tick").toString());
						if (geol_cnt < 128)
						{
							table.geol_cmd[geol_cnt].cmd = para1;
							table.geol_cmd[geol_cnt].start_tick = abs_time;
							table.geol_cmd[geol_cnt].stop_tick = 0x7fffffffL;
							geol_cnt++;
						} else
						{
							Log.e("ke", "geol buffer over(128)");
						}
						break;
					}
				// fall through
			}

			case 160:
			case 176: {
				pos.inc();
				// fall through
			}

			case 208: {
				pos.inc();
				break;
			}

			case 224: {
				char para1 = MainBuffer.get_midi_byte(pos);
				char para2 = MainBuffer.get_midi_byte(pos);
				int eno = ((para2 & 0x3f) << 7) + para1;
				switch (ttype)
				{
				default:
					break;

				case 258:
				case 261:
					Log.d("ke", (new StringBuilder("\tCHORUS EFFECT : ")).append(abs_time).append(" tick").toString());
					if (chorus_cnt < 128)
					{
						table.chorus_cmd[chorus_cnt].start_tick = abs_time;
						table.chorus_cmd[chorus_cnt].stop_tick = 0x7fffffffL;
						table.chorus_cmd[chorus_cnt].file = eno + 1;
						table.chorus_cmd[chorus_cnt].effect = true;
						table.chorus_cmd[chorus_cnt].lock = false;
						table.chorus_cmd[chorus_cnt].effect_track = false;
						chorus_cnt++;
					} else
					{
						Log.e("ke", "chorus buffer over(128)");
					}
					break label0;

				case 259:
					Log.d("ke", "\tDAESA track = effect command");
					break label0;

				case 260:
					Log.d("ke", (new StringBuilder("\tGASU EFFECT : ")).append(abs_time).append(" tick").toString());
					if (gasu_cnt < 128)
					{
						table.gasu_cmd[gasu_cnt].start_tick = abs_time;
						table.gasu_cmd[gasu_cnt].stop_tick = 0x7fffffffL;
						table.gasu_cmd[gasu_cnt].file = eno + 1;
						table.gasu_cmd[gasu_cnt].effect = true;
						table.gasu_cmd[gasu_cnt].lock = false;
						table.gasu_cmd[gasu_cnt].effect_track = false;
						gasu_cnt++;
					} else
					{
						Log.e("ke", "gasu buffer over(128)");
					}
					break;
				}
				break;
			}

			case 192: {
				char para1 = MainBuffer.get_midi_byte(pos);
				if (para1 < '\177')
				{
					switch (ttype)
					{
					default:
						break;

					case 258:
						Log.d("ke", (new StringBuilder("\tCHORUS(cmd=")).append(para1).append(") : ").append(abs_time).append(" tick").toString());
						if (chorus_cnt < 128 && para1 < '@')
						{
							table.chorus_cmd[chorus_cnt].start_tick = abs_time;
							table.chorus_cmd[chorus_cnt].stop_tick = 0x7fffffffL;
							table.chorus_cmd[chorus_cnt].file = para1 + 1;
							table.chorus_cmd[chorus_cnt].effect = false;
							table.chorus_cmd[chorus_cnt].lock = false;
							table.chorus_cmd[chorus_cnt].effect_track = false;
							chorus_cnt++;
						} else
						{
							Log.e("ke", "chorus buffer over(128)");
						}
						break label0;

					case 259:
						Log.d("ke", (new StringBuilder("\tDAESA : ")).append(abs_time).append(" tick").toString());
						int i;
						for (i = 0; i < chorus_cnt; i++)
							if (table.chorus_cmd[i].start_tick == abs_time)
								break;

						if (i < chorus_cnt)
							table.chorus_cmd[i].lock = true;
						else
							Log.e("ke", "daesa command");
						break label0;

					case 260:
						Log.d("ke", (new StringBuilder("\tGASU(cmd=")).append(para1).append(") : ").append(abs_time).append(" tick").toString());
						if (gasu_cnt < 128 && para1 < '@')
						{
							table.gasu_cmd[gasu_cnt].start_tick = abs_time;
							table.gasu_cmd[gasu_cnt].stop_tick = 0x7fffffffL;
							table.gasu_cmd[gasu_cnt].file = para1 + 1;
							table.gasu_cmd[gasu_cnt].effect = false;
							table.gasu_cmd[gasu_cnt].lock = false;
							table.gasu_cmd[gasu_cnt].effect_track = false;
							gasu_cnt++;
						} else
						{
							Log.e("ke", "gasu buffer over(128)");
						}
						break;

					case 261:
						Log.e("ke", "Effect track = chorus command");
						break;
					}
					break;
				}
				if (para1 != '\177')
					break;
				switch (ttype)
				{
				default:
					break;

				case 258:
					if (chorus_cnt > 0)
						table.chorus_cmd[chorus_cnt - 1].stop_tick = abs_time;
					break label0;

				case 259:
					Log.e("ke", "DAESA track = stop command");
					break label0;

				case 260:
					if (gasu_cnt > 0)
						table.gasu_cmd[gasu_cnt - 1].stop_tick = abs_time;
					break label0;

				case 261:
					if (chorus_cnt > 0)
						table.chorus_cmd[chorus_cnt - 1].stop_tick = abs_time;
					break;
				}
				break;
			}

			default: {
				switch (cmd & 0xff)
				{
				default:
					break;

				case 255:
					cmd = MainBuffer.get_midi_byte(pos);
					switch (cmd & 0xff)
					{
					case 47: // '/'
					{
						pos.disable();
						break label0;
					}

					case 3: // '\003'
					case 5: // '\005'
					{
						long length = KMemory.get_midi_tick(pos);
						char code[] = new char[32];
						for (int i = 0; i < length; i++)
						{
							char para1 = MainBuffer.get_midi_byte(pos);
							code[i] = para1;
						}

						code[(int) length] = '\0';
						if (ttype == 296 && chord_cnt < 128)
						{
							table.chord_cmd[chord_cnt].start_tick = abs_time;
							table.chord_cmd[chorus_cnt].stop_tick = 0x7fffffffL;
							chord_cnt++;
						}
						break;
					}

					default: {
						long length = KMemory.get_midi_tick(pos);
						pos.inc(length);
						break;
					}
					}
					break;

				case 240:
				case 247:
					long length = KMemory.get_midi_tick(pos);
					pos.inc(length);
					break;

				case 241:
				case 243:
					pos.inc();
					break;

				case 242:
					pos.inc(2);
					break;
				}
				break;
			}
			}
		}
	}

	private void get_wave_time(long _pos, song_t table)
	{
		char old_cmd = '\0';
		midi_pos_t pos = new midi_pos_t(_pos);
		int cnt = 0;
		long tick_us = 0L;
		long abs_time = 0L;
		while (pos.valid())
		{
			long delta_time = KMemory.get_midi_tick(pos);
			abs_time += delta_time;
			char cmd = MainBuffer.read_midi_byte(pos);
			if ((cmd & 0xff & 0x80) == 128)
			{
				cmd = MainBuffer.get_midi_byte(pos);
				old_cmd = cmd;
			} else
			{
				cmd = old_cmd;
			}
			label0: switch (cmd & 0xf0)
			{
			case 128:
			case 144:
			case 160:
			case 176:
			case 224:
				pos.inc();
				// fall through

			case 192:
			case 208:
				pos.inc();
				break;

			default:
				switch (cmd & 0xff)
				{
				default:
					break;

				case 255:
					cmd = MainBuffer.get_midi_byte(pos);
					switch (cmd)
					{
					case 47: // '/'
					{
						pos.disable();
						break label0;
					}

					case 81: // 'Q'
					{
						long length = KMemory.get_midi_tick(pos);
						long tempo_us = 0L;
						for (int i = 0; i < 3; i++)
						{
							tempo_us <<= 8;
							char para1 = MainBuffer.get_midi_byte(pos);
							tempo_us += para1;
						}

						pos.inc(length - 3L);
						tick_us = tempo_us / table.timebase;
						if (cnt < MAX_TEMPO_LIST - 1)
						{
							tempo_list[cnt * 2 + 0] = abs_time;
							tempo_list[cnt * 2 + 1] = tick_us;
							cnt++;
						}
						break;
					}

					default: {
						long length = KMemory.get_midi_tick(pos);
						pos.inc(length);
						break;
					}
					}
					break;

				case 240:
				case 247:
					long length = KMemory.get_midi_tick(pos);
					pos.inc(length);
					break;

				case 241:
				case 243:
					pos.inc();
					break;

				case 242:
					pos.inc(2);
					break;
				}
				break;
			}
		}
		tempo_list[cnt * 2 + 0] = 0x7fffffffL;
		tempo_list[cnt * 2 + 1] = tick_us;
		cnt++;
		for (int i = 0; i < table.chorus_cnt; i++)
		{
			if (table.chorus_cmd[i].stop_tick == 0x7fffffffL)
			{
				table.chorus_cmd[i].play_time = 0x7fffffffL;
				break;
			}
			int j;
			for (j = 0; j < cnt; j++)
				if (table.chorus_cmd[i].start_tick < tempo_list[2 * (j + 1) + 0])
					break;

			if (table.chorus_cmd[i].stop_tick < tempo_list[2 * (j + 1) + 0])
			{
				long tick = table.chorus_cmd[i].stop_tick - table.chorus_cmd[i].start_tick;
				table.chorus_cmd[i].play_time = (tempo_list[2 * j + 1] * tick) / 1000L;
			} else
			{
				long tick = tempo_list[2 * (j + 1) + 0] - table.chorus_cmd[i].start_tick;
				table.chorus_cmd[i].play_time = (tempo_list[2 * j + 1] * tick) / 1000L;
				for (j++; j < cnt; j++)
				{
					if (table.chorus_cmd[i].stop_tick < tempo_list[2 * (j + 1) + 0])
						break;
					tick = tempo_list[2 * (j + 1) + 0] - tempo_list[2 * j + 0];
					table.chorus_cmd[i].play_time += (tempo_list[2 * j + 1] * tick) / 1000L;
				}

				tick = table.chorus_cmd[i].stop_tick - tempo_list[2 * j + 0];
				table.chorus_cmd[i].play_time += (tempo_list[2 * j + 1] * tick) / 1000L;
			}
		}

		for (int i = 0; i < table.gasu_cnt; i++)
		{
			if (table.gasu_cmd[i].stop_tick == 0x7fffffffL)
			{
				table.gasu_cmd[i].play_time = 0x7fffffffL;
				break;
			}
			int j;
			for (j = 0; j < cnt; j++)
				if (table.gasu_cmd[i].start_tick < tempo_list[2 * (j + 1) + 0])
					break;

			if (table.gasu_cmd[i].stop_tick < tempo_list[2 * (j + 1) + 0])
			{
				long tick = table.gasu_cmd[i].stop_tick - table.gasu_cmd[i].start_tick;
				table.gasu_cmd[i].play_time += (tempo_list[2 * j + 1] * tick) / 1000L;
			} else
			{
				long tick = tempo_list[2 * (j + 1) + 0] - table.gasu_cmd[i].start_tick;
				table.gasu_cmd[i].play_time += (tempo_list[2 * j + 1] * tick) / 1000L;
				for (j++; j < cnt; j++)
				{
					if (table.gasu_cmd[i].stop_tick < tempo_list[2 * (j + 1) + 0])
						break;
					tick = tempo_list[2 * (j + 1) + 0] - tempo_list[2 * j + 0];
					table.gasu_cmd[i].play_time += (tempo_list[2 * j + 1] * tick) / 1000L;
				}

				tick = table.gasu_cmd[i].stop_tick - tempo_list[2 * j + 0];
				table.gasu_cmd[i].play_time += (tempo_list[2 * j + 1] * tick) / 1000L;
			}
		}

		for (int i = 0; i < table.geol_cnt; i++)
		{
			if (table.geol_cmd[i].start_time == 0x7fffffffL)
				break;
			table.geol_cmd[i].start_time = TickToMS(cnt, 0L, table.geol_cmd[i].start_tick, tempo_list);
		}

	}

	private int get_ctrl_cmd(long _pos, int type, song_t table)
	{
		char old_cmd = '\0';
		boolean bs_found = false;
		int count = 0;
		midi_pos_t pos = new midi_pos_t(_pos);
		count = 0;
		long abs_time = 0L;
		while (pos.valid())
		{
			long delta_time = KMemory.get_midi_tick(pos);
			abs_time += delta_time;
			char cmd = MainBuffer.read_midi_byte(pos);
			if ((cmd & 0xff & 0x80) == 128)
			{
				cmd = MainBuffer.get_midi_byte(pos);
				old_cmd = cmd;
			} else
			{
				cmd = old_cmd;
			}
			switch (cmd & 0xff & 0xf0)
			{
			case 128:
			case 160:
			case 224: {
				pos.inc();
				// fall through
			}

			case 208: {
				pos.inc();
				break;
			}

			case 144: {
				char para1 = MainBuffer.get_midi_byte(pos);
				char para2 = MainBuffer.get_midi_byte(pos);
				if (para2 == 0)
					break;
				if (type == 278)
				{
					if (para1 == '\021')
					{
						save_ctrl_fileno(abs_time, (byte) 1, table);
						break;
					}
					if (para1 == '\035')
						save_ctrl_fileno(abs_time, (byte) 2, table);
					else
						save_ctrl_fileno(abs_time, (byte) 0, table);
					break;
				}
				if (type == 275)
				{
					if (para2 == '\003')
					{
						bs_found = true;
						save_ctrl_fileno(abs_time, (byte) 4, table);
						break;
					}
					if (bs_found && (para2 == '\001' || para2 == '\002'))
					{
						bs_found = false;
						save_ctrl_fileno(abs_time, (byte) 6, table);
					}
					break;
				}
				if (type == 276 && para1 == '\032')
				{
					save_ctrl_fileno(abs_time, (byte) 5, table);
					break;
				}
				if (type == 277 && para1 == '\032')
					save_ctrl_fileno(abs_time, (byte) 3, table);
				break;
			}

			case 176: {
				char para1 = MainBuffer.get_midi_byte(pos);
				char para2 = MainBuffer.get_midi_byte(pos);
				if (type == 272)
				{
					save_ctrl_speed(abs_time, (byte) 2, table);
					break;
				}
				if (type == 273)
					save_ctrl_speed(abs_time, (byte) 1, table);
				break;
			}

			case 192: {
				char para1 = MainBuffer.get_midi_byte(pos);
				if (type == 279 && para1 == '\n')
					save_ctrl_restart(abs_time, table);
				break;
			}

			default: {
				switch (cmd & 0xff)
				{
				default:
					break;

				case 255:
					cmd = MainBuffer.get_midi_byte(pos);
					switch (cmd)
					{
					case 47: // '/'
						pos.disable();
						break;

					default:
						long length = KMemory.get_midi_tick(pos);
						pos.inc(length);
						break;
					}
					break;

				case 240:
				case 247:
					long length = KMemory.get_midi_tick(pos);
					pos.inc(length);
					break;

				case 241:
				case 243:
					pos.inc();
					break;

				case 242:
					pos.inc(2);
					break;
				}
				break;
			}
			}
		}
		return count;
	}

	private long get_gs_tick(long _pos)
	{
		char old_cmd = '\0';
		midi_pos_t pos = new midi_pos_t(_pos);
		long abs_time = 0L;
		while (pos.valid())
		{
			long delta_time = KMemory.get_midi_tick(pos);
			abs_time += delta_time;
			char cmd = MainBuffer.read_midi_byte(pos);
			if ((cmd & 0xff & 0x80) == 128)
			{
				cmd = MainBuffer.get_midi_byte(pos);
				old_cmd = cmd;
			} else
			{
				cmd = old_cmd;
			}
			switch (cmd & 0xf0)
			{
			case 128:
			case 144:
			case 160:
			case 176:
			case 224:
				pos.inc();
				// fall through

			case 192:
			case 208:
				pos.inc();
				break;

			default:
				switch (cmd & 0xff)
				{
				default:
					break;

				case 255:
					cmd = MainBuffer.get_midi_byte(pos);
					switch (cmd)
					{
					case 47: // '/'
						pos.disable();
						break;

					default:
						long length = KMemory.get_midi_tick(pos);
						pos.inc(length);
						break;
					}
					break;

				case 240:
				case 247:
					long length = KMemory.get_midi_tick(pos);
					pos.inc(length);
					break;

				case 241:
				case 243:
					pos.inc();
					break;

				case 242:
					pos.inc(2);
					break;
				}
				break;
			}
		}
		return abs_time;
	}

	private void save_ctrl_fileno(long tick, byte file_no, song_t table)
	{
		int i;
		for (i = 0; i < table.ctrl_cnt; i++)
			if (table.ctrl[i].tick == tick)
				break;

		if (i < table.ctrl_cnt)
			table.ctrl[i].file_no = file_no;
		else if (table.ctrl_cnt < 100)
		{
			table.ctrl[table.ctrl_cnt].tick = tick;
			table.ctrl[table.ctrl_cnt].file_no = file_no;
			table.ctrl_cnt++;
		}
	}

	private void save_ctrl_speed(long tick, byte speed, song_t table)
	{
		int i;
		for (i = 0; i < table.ctrl_cnt; i++)
			if (table.ctrl[i].tick == tick)
				break;

		if (i < table.ctrl_cnt)
			table.ctrl[i].speed = speed;
		else if (table.ctrl_cnt < 100)
		{
			table.ctrl[table.ctrl_cnt].tick = tick;
			table.ctrl[table.ctrl_cnt].speed = speed;
			table.ctrl_cnt++;
		}
	}

	private void save_ctrl_restart(long tick, song_t table)
	{
		int i;
		for (i = 0; i < table.ctrl_cnt; i++)
			if (table.ctrl[i].tick == tick)
				break;

		if (i < table.ctrl_cnt)
			table.ctrl[i].restart = true;
		else if (table.ctrl_cnt < 100)
		{
			table.ctrl[table.ctrl_cnt].tick = tick;
			table.ctrl[table.ctrl_cnt].restart = true;
			table.ctrl_cnt++;
		}
	}

	private final short MAX_WAVE_CNT = 5;
	private short wave_track[] = {
			258, 259, 260, 261, 296
	};
	private boolean SC88;
	private boolean PORT1;
	private boolean CHANNEL4;
	private int step_beat_type;
	private int step_madi_count;
	private int tempo;
	private int geol_cnt;
	private int chorus_cnt;
	private int gasu_cnt;
	private int chord_cnt;
	private int MAX_TEMPO_LIST;
	private long tempo_list[];
}
