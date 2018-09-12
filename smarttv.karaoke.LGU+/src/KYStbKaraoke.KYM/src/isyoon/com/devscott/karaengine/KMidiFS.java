// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   KMidiFS.java

package isyoon.com.devscott.karaengine;

import android.content.res.AssetManager;
import android.util.Log;
import java.io.*;

// Referenced classes of package com.devscott.karaengine:
//            KTrack, new_t, song_t, FH, 
//            KFileIO, Global, sng_t, KString, 
//            LY, Setup, Etc, midi_pos_t, 
//            MainBuffer, mtrk_t, note_t, sdrum_t, 
//            KMemory, key_t, ctrl_t, midi_tick_t, 
//            midi_h, midi_t, reg_t

public class KMidiFS
{
	class keytable_t
	{
		String name;
		int id;

		public keytable_t() {
		}

		public keytable_t(String n, int nn)
		{
			this.name = n;
			this.id = nn;
		}
	}

	public KMidiFS()
	{
		m_decomp_retry = 0;
		midikey = null;
		Track = null;
		m_decomp_retry = 0;
		m_nesting = 0;
		Track = new KTrack();
		new_song = new new_t();
		midikey = new keytable_t[42];
		int pitch = 11;
		String KEY[] = {
				"A", "B", "C", "D", "E", "F", "G"
		};
		for (int idx = 0; idx <= 6; idx++)
		{
			midikey[idx * 6 + 0] = new keytable_t((new StringBuilder(String.valueOf(KEY[idx]))).append("b").toString(), pitch % 12);
			midikey[idx * 6 + 1] = new keytable_t((new StringBuilder(String.valueOf(KEY[idx]))).toString(), (pitch + 1) % 12);
			midikey[idx * 6 + 2] = new keytable_t((new StringBuilder(String.valueOf(KEY[idx]))).append("#").toString(), (pitch + 2) % 12);
			midikey[idx * 6 + 3] = new keytable_t((new StringBuilder(String.valueOf(KEY[idx]))).append("bm").toString(), pitch % 12 + 128);
			midikey[idx * 6 + 4] = new keytable_t((new StringBuilder(String.valueOf(KEY[idx]))).append("m").toString(), (pitch + 1) % 12 + 128);
			midikey[idx * 6 + 5] = new keytable_t((new StringBuilder(String.valueOf(KEY[idx]))).append("#m").toString(), (pitch + 2) % 12 + 128);
			pitch += 2;
		}

	}

	public void Huffman_init()
	{
	}

	public long Huffman_DSR(long song_no, byte p[], long l1, long l2)
	{
		return 0L;
	}

	public boolean mfs_init()
	{
		m_song_handle = new song_t[2];
		for (int i = 0; i < 2; i++)
			m_song_handle[i] = new song_t();

		Huffman_init();
		m_start_sno = 101;
		m_end_sno = 0x15f90;
		return true;
	}

	public void mfs_all_close()
	{
		for (int mh = 0; mh < 2; mh++)
			if ((m_song_handle[mh].status & 1) == 1)
			{
				if (m_song_handle[mh].file_handle.valid())
					KFileIO.dclose(m_song_handle[mh].file_handle);
				m_song_handle[mh] = new song_t();
			}

	}

	public void mfs_song_close(int sno)
	{
		for (int mh = 0; mh < 2; mh++)
			if (m_song_handle[mh].hsong_no == sno && (m_song_handle[mh].status & 1) == 1)
			{
				if (m_song_handle[mh].file_handle.valid())
					KFileIO.dclose(m_song_handle[mh].file_handle);
				m_song_handle[mh] = new song_t();
			}

	}

	public static boolean mfs_exist(long song_no)
	{
		return song_no > 0L && song_no < 0x186a0L;
	}

	public boolean mfs_new_song(long song_no)
	{
		StringBuffer fname = new StringBuffer();
		KFileIO.make_song_fname(fname, song_no);
		FH file_handle = KFileIO.dopen(65535, fname, false);
		if (file_handle.valid())
		{
			KFileIO.dclose(file_handle);
			return false;
		} else
		{
			return true;
		}
	}

	public boolean mfs_new_exist(long song_no)
	{
		for (int i = 0; i < new_song.new_song_count; i++)
			if (new_song.new_song_list[i] == song_no)
				return true;

		return false;
	}

	public int mfs_next(int song_no)
	{
		if (song_no < m_start_sno)
			song_no = m_start_sno - 1;
		long i;
		for (i = m_start_sno; i < m_end_sno; i++)
		{
			if ((++song_no) == 8888L)
				song_no++;
			if (song_no >= m_end_sno)
				song_no = m_start_sno;
			if (mfs_exist(song_no))
				break;
		}

		if (i >= m_end_sno)
			song_no = 0;
		return song_no;
	}

	public static int mfs_random()
	{
		return 0;
	}

	public long mfs_new_next(long song_no)
	{
		return 0L;
	}

	public boolean mfs_new_regist(long song_no)
	{
		return false;
	}

	public static boolean mfs_header(long song_no, sng_t buf)
	{
		return mfs_header(song_no, buf, false, false);
	}

	public static boolean mfs_header(long song_no, sng_t buf, boolean bnewsong, boolean any_midi)
	{
		StringBuffer fname = new StringBuffer();
		if (!mfs_exist(song_no))
		{
			Log.e("ke", "Not mfs_exist");
			return false;
		}
		long size;
		if (bMemory.booleanValue())
		{
			InputStream is = null;
			try
			{
				is = Global.Inst().as.open("00100.KYC");
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			DataInputStream dis = new DataInputStream(is);
			buf.readData(dis);
			size = 8453L;
			if (song_no >= 0x15f90L && song_no < 0x186a0L)
				buf.is_medley_song = true;
			if (song_no >= 9901L && song_no < 10000L)
				buf.is_medley_song = true;
			if (song_no >= 0x15ba8L && song_no < 0x15f8fL)
				buf.is_medley_song = true;
			return true;
		}
		KFileIO.make_new_fname(fname, song_no);
		FH file_handle = KFileIO.dopen(65535, fname, false);
		Log.d("ke", (new StringBuilder("make_song_fname = ")).append(fname).toString());
		if (!file_handle.valid())
		{
			Log.d("ke", "mfs_header open fail");
			if (bnewsong)
			{
				KFileIO.make_new_fname(fname, song_no);
				file_handle = KFileIO.dopen(65535, fname, false);
				if (!file_handle.valid())
					return false;
			} else
			{
				return false;
			}
		}
		buf.read(file_handle.in);
		if (song_no >= 0x15f90L && song_no < 0x186a0L)
			buf.is_medley_song = true;
		if (song_no >= 9901L && song_no < 10000L)
			buf.is_medley_song = true;
		if (song_no >= 0x15ba8L && song_no < 0x15f8fL)
			buf.is_medley_song = true;
		size = KFileIO.dsize(file_handle);
		KFileIO.dclose(file_handle);
		if (!mfs_header_check(buf, song_no, size))
		{
			Log.e("ke", (new StringBuilder(String.valueOf(song_no))).append(" bad MIDI file format(delete)").toString());
			return false;
		}
		if (buf.revision == 1281L)
		{
			buf.gasu_cnt = buf.chorus_cnt;
			for (int i = 0; i < 64; i++)
				buf.gasu_pos[i] = buf.chorus_pos[i];

		}
		KString.strip_china_special(buf);
		return true;
	}

	public int mfs_reg(long song_no, reg_t r)
	{
		return 0;
	}

	public int mfs_open(long song_no, boolean mode)
	{
		StringBuffer fname = new StringBuffer();
		LY lyric = new LY();
		song_t table = null;
		long file_length = 0L;
		if (!mfs_exist(song_no))
			return 0;
		if (mode)
		{
			Setup _tmp = Global.Inst().setup;
			Setup.setup_send((int) song_no, 0, 0);
		}
		int mh;
		do
		{
			Etc.disable();
			for (mh = 0; mh < 2; mh++)
				if (m_song_handle[mh].hsong_no == song_no)
					break;

			if (mh < 2)
			{
				if ((m_song_handle[mh].status & 4) == 4)
				{
					Log.e("ke", (new StringBuilder(String.valueOf(song_no))).append(" song open").toString());
					if (mode)
					{
						m_song_handle[mh].status = 0;
						m_song_handle[mh].hsong_no = 0L;
					}
					Etc.enable();
					return 0;
				}
				if ((m_song_handle[mh].status & 1) == 1)
				{
					if (mode)
					{
						m_song_handle[mh].status |= 2;
						Log.d("ke", (new StringBuilder("Song open : ")).append(song_no).toString());
					}
					Etc.enable();
					return mh + 1;
				}
			} else if (!mode && m_nesting == 0)
			{
				for (mh = 0; mh < 2; mh++)
					if ((m_song_handle[mh].status & 2) != 2)
						break;

				if (mh < 2)
				{
					m_nesting++;
					table = m_song_handle[mh];
					if (table.file_handle.valid())
						KFileIO.dclose(table.file_handle);
					m_song_handle[mh] = new song_t();
					table = m_song_handle[mh];
					table.status = 0;
					table.hsong_no = song_no;
					table.buf_base = mh * 0x3d800L + 1L;
					Etc.enable();
					mh++;
				} else
				{
					Etc.enable();
					Log.e("ke", "Not closed song handle.");
					Log.e("ke", "Not closed song handle.");
					Log.e("ke", "Not closed song handle.");
					Log.e("ke", "Not closed song handle.");
					return 0;
				}
				break;
			}
			Etc.enable();
			Etc.OSTIMEDLY(2L);
			Log.d("ke", "mfs_open wait for setup opened message ");
		} while (true);
		if (bMemory.booleanValue())
		{
			InputStream is = null;
			try
			{
				is = Global.Inst().as.open("00100.KYC");
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			DataInputStream dis = new DataInputStream(is);
			table.song.readData(dis);
			file_length = 8453L;
			if (song_no >= 0x15f90L && song_no < 0x186a0L)
				table.song.is_medley_song = true;
			if (song_no >= 9901L && song_no < 10000L)
				table.song.is_medley_song = true;
			if (song_no >= 0x15ba8L && song_no < 0x15f8fL)
				table.song.is_medley_song = true;
		} else
		{
			KFileIO.make_song_fname(fname, song_no);
			FH file_handle = KFileIO.dopen(65535, fname, false);
			long jmp_pos = 0L;
			if (!file_handle.valid())
				Log.d("ke", "FILE_HANDEL 0");
			else
				table.file_handle = file_handle;
			Log.d("ke", "normal pointer");
			if (file_handle.valid())
				KFileIO.dclose(file_handle);
			KFileIO.make_song_fname(fname, song_no);
			file_handle = KFileIO.dopen(65535, fname, false);
			table.file_handle = file_handle;
			if (!file_handle.valid())
			{
				KFileIO.make_new_fname(fname, song_no);
				file_handle = KFileIO.dopen(65535, fname, false);
				table.file_handle = file_handle;
				if (!file_handle.valid())
				{
					Log.e("ke", (new StringBuilder(String.valueOf(song_no))).append(" song open").toString());
					song_close(mh);
					return 0;
				}
			}
			table.song.read(file_handle.in);
			file_length = KFileIO.dsize(file_handle);
			if (!mfs_header_check(table.song, song_no, file_length))
			{
				song_close(mh);
				Log.e("ke", (new StringBuilder(String.valueOf(song_no))).append(" bad MIDI file format(delete)").toString());
				return 0;
			}
			if (song_no >= 0x15f90L && song_no < 0x186a0L)
				table.song.is_medley_song = true;
			if (song_no >= 9901L && song_no < 10000L)
				table.song.is_medley_song = true;
			if (song_no >= 0x15ba8L && song_no < 0x15f8fL)
				table.song.is_medley_song = true;
		}
		Log.d("ke", (new StringBuilder("OPEN MAGIC NUMBER ")).append(table.song.magic_number).toString());
		if (table.song.revision == 1281L)
		{
			if (table.song.revision == 1281L)
				table.song.gasu_cnt = table.song.chorus_cnt;
			for (int i = 0; i < 64; i++)
				table.song.gasu_pos[i] = table.song.chorus_pos[i];

		}
		if (bMemory.booleanValue())
		{
			table.song.lyric_pos = new midi_pos_t(0L);
			table.song.lyric_len = 8192L;
			table.song.midi_pos = new midi_pos_t(8192L);
			table.song.midi_len = 0x19000L;
			System.arraycopy(Global.Inst().rawMIDI, 0, MainBuffer.getPointer(), (int) table.song.midi_pos.pos(), (int) table.song.midi_len);
			System.arraycopy(Global.Inst().rawSOK, 0, MainBuffer.getPointer(), (int) table.song.lyric_pos.pos(), (int) table.song.lyric_len);
			table.song.lyric_pos.reset_org();
			table.song.midi_pos.reset_org();
			table.song.lyric_pos.inc(table.buf_base);
			table.song.midi_pos.inc(table.buf_base);
			if (song_no >= 0x15f90L && song_no < 0x186a0L)
				table.song.is_medley_song = true;
			if (song_no >= 9901L && song_no < 10000L)
				table.song.is_medley_song = true;
			if (song_no >= 0x15ba8L && song_no < 0x15f8fL)
				table.song.is_medley_song = true;
		} else
		{
			long len = Huffman_DSR(song_no, MainBuffer.getPointer(), table.song.cmp_pos.pos(), table.buf_base);
			Log.d("ke", "OSTIMEDLY 1");
			Etc.OSTIMEDLY(1L);
			Log.d("ke", (new StringBuilder("Compression file size = ")).append(len).toString());
			if (len == 0L || len >= 0x3d800L)
			{
				if (len == 0L)
					Log.e("ke", (new StringBuilder(String.valueOf(song_no))).append("song decompression").toString());
				if (len >= 0x3d800L)
					Log.e("ke", (new StringBuilder(String.valueOf(song_no))).append("song size over ").append(len).toString());
				song_close(mh);
				return 0;
			}
			table.song.lyric_pos.reset_org();
			table.song.midi_pos.reset_org();
			table.song.lyric_pos.inc(table.buf_base);
			table.song.midi_pos.inc(table.buf_base);
			if (song_no >= 0x15f90L && song_no < 0x186a0L)
				table.song.is_medley_song = true;
			if (song_no >= 9901L && song_no < 10000L)
				table.song.is_medley_song = true;
			if (song_no >= 0x15ba8L && song_no < 0x15f8fL)
				table.song.is_medley_song = true;
		}
		for (int i = 0; i < 71; i++)
			table.imidi[i] = new mtrk_t();

		table.bass_code = 0;
		int j;
		for (int i = 0; i < 71; i++)
			for (j = 0; j < 128; j++)
				table.note[i][j].reset();

		for (int i = 0; i < 8; i++)
			for (j = 0; j < 10; j++)
			{
				table.cur_drum_vol[i][j].reset();
				table.old_drum_vol[i][j].reset();
			}

		table.madi_delay_tick = 0;
		table.erase4_timer = 0;
		table.stop_token = true;
		table.chorus_inx = 65535;
		if ((table.song.country == 3 || table.song.country == 17) && table.song.lyric_len > 0L)
		{
			KString.strip_china_special(table.song);
			midi_pos_t pos = new midi_pos_t(table.song.lyric_pos.pos());
			midi_pos_t spos = table.song.lyric_pos;
			for (int i = j = 0; i < table.song.lyric_len;)
			{
				char code = MainBuffer.get_midi_byte(pos);
				i++;
				if (code == '*')
					do
					{
						code = MainBuffer.get_midi_byte(pos);
						i++;
					} while (code != '0' && code != '\r' && code != '\n' && code != '\032' && (code & 0x80) != 128);
				MainBuffer.set_midi_byte(code, spos);
				j++;
			}

			table.song.lyric_len = j;
		}
		if (table.song.lyric_len > 0L)
		{
			int n = 6;
			midi_pos_t temp_pos = new midi_pos_t(table.song.lyric_pos.pos());
			KMemory.get_midi_line(lyric, temp_pos);
			KString.strip_space(lyric);
			Log.i("ke", (new StringBuilder("Lyric 1st line : '")).append(lyric.debug_string()).append("'").toString());
			Log.i("ke", (new StringBuilder("Lyric key(old) : man=")).append(table.song.key.man_key).append(", woman=").append(table.song.key.woman_key).toString());
			int i;
			for (i = 0; lyric.at(i) > 0; i++)
				if (lyric.at(i) == 47)
					break;

			int backup_idx = ++i;
			for (; lyric.at(i) > 0; i++)
				if (lyric.at(i) == 47)
					break;

			lyric.set(i, (byte) 0);
			for (j = 0; j < 42; j++)
				if (midikey[j].name.getBytes()[0] == lyric.at(backup_idx))
					break;

			backup_idx = ++i;
			for (; lyric.at(i) > 0; i++)
				if (lyric.at(i) == 47)
					break;

			lyric.set(i, (byte) 0);
			for (j = 0; j < 42; j++)
				if (midikey[j].name.getBytes()[0] == lyric.at(backup_idx))
					break;

			Log.i("ke", (new StringBuilder("Lyric key(new) : man=")).append(table.song.key.man_key).append(", woman=").append(table.song.key.woman_key).toString());
			for (i = 0; i < n; i++)
				table.song.lyric_pos = KMemory.get_midi_line(lyric, table.song.lyric_pos);

			for (midi_pos_t pos = new midi_pos_t(table.song.lyric_pos.pos()); pos.valid(); lyric.repose())
			{
				KMemory.get_midi_line(lyric, pos);
				lyric.repose();
				n = KString.get_erase_cnt(lyric, table.song.country);
				if (n > 1)
					break;
			}

			if (n <= 1)
				table.song.lyric_len = 0L;
		} else
		{
			table.song.lyric_len = 0L;
		}
		MidiPrepare(table, song_no);
		Track.midi_track_parsing(table, song_no);
		table.save_trk = table.imidi.clone();
		for (int i = 0; i < 71; i++)
			try
			{
				table.save_trk[i] = (mtrk_t) table.imidi[i].clone();
			} catch (CloneNotSupportedException e)
			{
				e.printStackTrace();
			}

		for (int i = 0; i < 71; i++)
			table.imidi[i] = new mtrk_t();

		for (int i = j = 0; i < table.mtrks; i++)
			if (table.save_trk[i].type >= '\u0110')
			{
				try
				{
					table.imidi[j] = (mtrk_t) table.save_trk[i].clone();
				} catch (CloneNotSupportedException e)
				{
					e.printStackTrace();
				}
				table.save_trk[i].type = '\uFFFF';
				j++;
			}

		if (table.rhythm_present)
		{
			int ch;
			for (int i = ch = 0; i < table.mtrks; i++)
				if (table.save_trk[i].type != '\uFFFF')
				{
					table.imidi[j] = table.save_trk[i];
					j++;
					if ((ch & 1) != 1 && table.save_trk[i].type == '\001')
					{
						table.imidi[j].type = '!';
						j++;
						ch |= 1;
					} else if ((ch & 2) != 2 && table.save_trk[i].type == '\t')
					{
						table.imidi[j].type = ')';
						j++;
						ch |= 2;
					} else if ((ch & 4) != 4 && table.save_trk[i].type == '\n')
					{
						table.imidi[j].type = '*';
						j++;
						ch |= 4;
					} else if ((ch & 8) != 8 && table.save_trk[i].type == '\021')
					{
						table.imidi[j].type = '1';
						j++;
						ch |= 8;
					} else if ((ch & 0x10) != 16 && table.save_trk[i].type == '\031')
					{
						table.imidi[j].type = '9';
						j++;
						ch |= 0x10;
					} else if ((ch & 0x20) != 32 && table.save_trk[i].type == '\032')
					{
						table.imidi[j].type = ':';
						j++;
						ch |= 0x20;
					}
				}

		} else
		{
			for (int i = 0; i < table.mtrks; i++)
				if (table.save_trk[i].type != '\uFFFF')
				{
					table.imidi[j] = table.save_trk[i];
					j++;
				}

		}
		table.imidi[j].type = '\0';
		table.effect1trk = j;
		j++;
		table.imidi[j].type = '\020';
		table.effect2trk = j;
		j++;
		table.mtrks = j;
		table.melody_type1 = 65534;
		table.melody_type2 = 65534;
		table.gstrk = 70;
		table.rs1trk = 69;
		table.rs2trk = 68;
		table.steptrk = 67;
		table.chordtrk = 66;
		for (int i = 0; i < table.mtrks; i++)
		{
			mtrk_t mp = table.imidi[i];
			if (mp.type < ' ')
			{
				if (mp.type % 16 == 9 || mp.type % 16 == 10)
					mp.mode |= 1;
				else if (mp.type % 16 == 1)
					mp.mode |= 2;
				else if (table.song.country == 14)
				{
					if (mp.type == 0 && table.melody_type1 == 65534)
						table.melody_type1 = mp.type;
					else if (mp.type == '\020' && table.melody_type2 == 65534)
						table.melody_type2 = mp.type;
				} else if (mp.type == '\003' && table.melody_type1 == 65534)
					table.melody_type1 = mp.type;
				else if (mp.type == '\023' && table.melody_type2 == 65534)
					table.melody_type2 = mp.type;
			} else if ((mp.type & 0xfff0) == 64)
			{
				mp.mode |= 8;
				mp.para = '\0';
				mp.patch = '2';
			} else if (mp.type == '\u0112')
				table.gstrk = i;
			else if (mp.type == '\u0114')
				table.rs1trk = i;
			else if (mp.type == '\u0115')
				table.rs2trk = i;
			else if (mp.type == '\u0121')
			{
				table.steptrk = i;
				mp.para = '\0';
			} else if (mp.type == '\u0128')
				table.chordtrk = i;
		}

		table.rbuf_usage = 0;
		table.new_rhythm_mode = table.old_rhythm_mode = 0;
		table.ctrl_inx = 0;
		table.org_ctrl.tick = table.ctrl[0].tick + 1L;
		table.org_ctrl.file_no = 4;
		table.org_ctrl.speed = table.ctrl[0].speed;
		table.org_ctrl.restart = false;
		table.cur_ctrl = table.org_ctrl;
		table.org_rtinx = new int[13];
		table.new_rtinx = new int[6];
		if (table.rhythm_present)
		{
			Log.d("ke", "Rhythm command found");
			for (int i = 0; i < 6; i++)
			{
				for (j = 0; j < table.mtrks; j++)
				{
					mtrk_t mp = table.imidi[j];
					if (mp.type == rhythm_tlist[i])
						break;
				}

				if (j < table.mtrks)
				{
					table.new_rtinx[i] = j;
				} else
				{
					table.new_rtinx[i] = table.mtrks;
					table.mtrks++;
				}
			}

			for (int i = j = 0; i < table.mtrks; i++)
			{
				mtrk_t mp = table.imidi[i];
				if (mp.type >= ' ' || mp.type % 16 != 1 && mp.type % 16 != 9 && mp.type % 16 != 10)
					continue;
				table.org_rtinx[j] = i;
				if (++j == 12)
					break;
			}

		} else
		{
			Log.d("ke", "Rhythm command not found");
		}
		for (int i = 0; i < table.mtrks; i++)
		{
			mtrk_t mp = table.imidi[i];
			if (mp.addr.valid())
			{
				mp.tick.set(KMemory.get_midi_tick(mp.addr));
				if (mp.type != '\u0113')
					mp.tick.inc();
			}
		}

		for (int i = 0; i < 6; i++)
			for (j = 0; j < table.mtrks; j++)
			{
				mtrk_t mp = table.imidi[j];
				if (mp.type == org_tlist[i])
					break;
				if (j < table.mtrks)
				{
					try
					{
						table.org_rmidi[i] = (mtrk_t) mp.clone();
					} catch (CloneNotSupportedException e)
					{
						e.printStackTrace();
					}
					table.org_rmidi[i].type = rhythm_tlist[i];
				} else
				{
					table.org_rmidi[i] = new mtrk_t();
				}
			}

		if (table.imidi[table.gstrk].addr.valid() && table.song.lyric_len > 0L)
		{
			table.max_line = 2;
		} else
		{
			table.imidi[table.gstrk].addr.disable();
			table.imidi[table.gstrk].tick.reset();
			table.song.lyric_pos.disable();
		}
		if (table.imidi[table.gstrk].addr.valid())
		{
			mtrk_t tmp = null;
			try
			{
				tmp = (mtrk_t) table.imidi[table.gstrk].clone();
			} catch (CloneNotSupportedException e)
			{
				e.printStackTrace();
			}
			table.gstick = tmp.tick.tick();
			do
			{
				gs_track_setup(tmp.addr, tmp.type, tmp);
				if (!tmp.addr.valid() || tmp.para <= '\004')
					break;
				long tick = KMemory.get_midi_tick(tmp.addr);
				table.gstick += tick;
			} while (true);
			if (tmp.addr.valid() && tmp.para != 0)
				table.imidi[table.gstrk].para = '\0';
		}
		table.lyric_ptr = new midi_pos_t(table.song.lyric_pos.pos());
		table.lyric_pos = new midi_pos_t(table.song.lyric_pos.pos());
		table.lyric_fptr = new midi_pos_t(table.song.lyric_pos.pos());
		table.lyric_mptr = new midi_pos_t(table.song.lyric_pos.pos());
		try
		{
			table.midi_mptr = (mtrk_t) table.imidi[table.gstrk].clone();
		} catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		if (table.hsong_no > 0L)
			table.status = 1;
		else
			table.status = 0;
		m_nesting--;
		return mh;
	}

	public static boolean mfs_key(long song_no, key_t key)
	{
		sng_t song = new sng_t();
		if (!mfs_header(song_no, song))
		{
			return false;
		} else
		{
			key = song.key;
			return true;
		}
	}

	public long mfs_new_gasu(long song_no)
	{
		sng_t song = new sng_t();
		char fname[] = new char[255];
		sng_t buf = song;
		KFileIO.make_new_fname(fname, song_no);
		FH file_handle = KFileIO.dopen(65535, fname, false);
		if (!file_handle.valid())
		{
			return 0L;
		} else
		{
			buf.read(file_handle.in);
			long size = KFileIO.dsize(file_handle);
			KFileIO.dclose(file_handle);
			return buf.gasu_cnt;
		}
	}

	public boolean mfs_title(long song_no, char title[], int country)
	{
		sng_t song = new sng_t();
		if (!mfs_header(song_no, song))
		{
			return false;
		} else
		{
			KString.make_title(title, song);
			country = song.country;
			return true;
		}
	}

	public boolean mfs_singer(long song_no, char singer[], int country)
	{
		sng_t song = new sng_t();
		if (!mfs_header(song_no, song))
		{
			return false;
		} else
		{
			KString.make_singer(singer, song);
			country = song.country;
			return true;
		}
	}

	public boolean mfs_medley(long song_no)
	{
		sng_t song = new sng_t();
		if (!mfs_header(song_no, song))
			return false;
		else
			return song.is_medley_song;
	}

	public song_t mfs_table(int mh)
	{
		if (--mh < 2 && m_song_handle[mh].hsong_no > 0L)
			return m_song_handle[mh];
		else
			return null;
	}

	public void mfs_close(int mh)
	{
		if (--mh < 2 && (m_song_handle[mh].status & 2) == 2)
			m_song_handle[mh].status = 1;
	}

	public boolean mfs_chorus_init(int mh, int index)
	{
		mh--;
		index--;
		song_t table = m_song_handle[mh];
		if (mh < 2 && table.hsong_no > 0L && index < 64 && table.song.chorus_pos[index] > 0L)
		{
			table.chorus_inx = index;
			table.chorus_loc = table.song.chorus_pos[index];
			table.chorus_loc += 4L;
			KFileIO.dseek(table.file_handle, table.chorus_loc);
			return true;
		}
		if (mh < 2 && table.hsong_no > 0L && index < 64 && table.song.gasu_pos[index] > 0L)
		{
			table.chorus_inx = index;
			table.chorus_loc = table.song.gasu_pos[index];
			table.chorus_loc += 4L;
			KFileIO.dseek(table.file_handle, table.chorus_loc);
			return true;
		} else
		{
			table.chorus_inx = 65535;
			return false;
		}
	}

	public void song_close(int mh)
	{
		mh--;
		if (m_song_handle[mh].hsong_no > 0L)
		{
			if (m_song_handle[mh].file_handle.valid())
				KFileIO.dclose(m_song_handle[mh].file_handle);
			m_song_handle[mh].file_handle.reset();
			m_song_handle[mh].status = 4;
		}
		m_nesting--;
	}

	public void gs_track_setup(midi_pos_t pos, int type, mtrk_t mp)
	{
		char cmd = MainBuffer.read_midi_byte(pos);
		if (cmd < '\200')
		{
			cmd = mp.cmd;
		} else
		{
			cmd = MainBuffer.get_midi_byte(pos);
			mp.cmd = cmd;
		}
		char c;
		switch (cmd & 0xf0)
		{
		case 128:
		case 144:
		case 160:
		case 176:
		case 224:
			c = MainBuffer.get_midi_byte(pos);
			// fall through

		case 208:
			char para2 = MainBuffer.get_midi_byte(pos);
			break;

		case 192:
			char para1 = MainBuffer.get_midi_byte(pos);
			if (type == 274)
				mp.para = para1;
			break;

		default:
			switch (cmd)
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

	public static boolean mfs_header_check(sng_t p, long song_no, long size)
	{
		if (p.magic_number != 0x414e4148L || song_no >= 0x186a0L || p.rsong_no != song_no || p.midi_len <= 0L || p.cmp_len <= 0L)
		{
			Log.e("ke", "error midi header");
			return false;
		}
		for (int i = 0; i < 64; i++)
			if (p.chorus_pos[i] >= size)
			{
				Log.e("ke", (new StringBuilder("MIDI ")).append(song_no).append(" header, bad chorus pos").toString());
				return false;
			}

		for (int i = 0; i < 64; i++)
			if (p.gasu_pos[i] >= size)
			{
				Log.e("ke", (new StringBuilder("MIDI ")).append(song_no).append(" header, bad gasu pos").toString());
				return false;
			}

		return true;
	}

	private boolean Is9703Range(long song_no)
	{
		if (song_no > 10000L && song_no < 20000L)
			return true;
		if (song_no >= 20000L && song_no < 35000L)
			return true;
		if (song_no >= 0x11170L && song_no < 0x11963L)
			return true;
		return song_no > 0xfffffffffffedf77L && song_no < 0x124f8L;
	}

	void MidiPrepare(song_t table, long song_no)
	{
		midi_h midih = new midi_h();
		midi_t midit = new midi_t();
		if (bMemory.booleanValue())
			table.song.midi_pos.reset_org();
		Log.i("ke", (new StringBuilder("======= SONG ")).append(song_no).append(" MIDI track =======").toString());
		midih.read(table.song.midi_pos.pos());
		long pos = table.song.midi_pos.pos() + midih.sizeof();
		table.format = midih.format;
		table.mtrks = midih.notrk;
		table.timebase = KMemory.get_midi_short(pos);
		pos += 2L;
		Log.i("ke", (new StringBuilder("MIDI format=")).append(table.format).append(" tracks=").append(table.mtrks).append(" timebase=").append(table.timebase).toString());
		if (table.mtrks > 62)
		{
			Log.e("ke", (new StringBuilder("too many tracks(")).append(table.mtrks).append(")").toString());
			table.mtrks = 62;
		}
		for (int i = 0; i < table.mtrks; i++)
		{
			midit.read(pos);
			pos += midit.sizeof();
			table.imidi[i].addr = new midi_pos_t(pos);
			table.imidi[i].length = midit.lng;
			pos += midit.lng;
		}

	}

	private static final Boolean bMemory = Boolean.valueOf(true);
	private final short SONG_READY = 1;
	private final short SONG_BUSY = 2;
	private final short SONG_ERROR = 4;
	private int m_decomp_retry;
	private song_t m_song_handle[];
	private int m_nesting;
	private int m_start_sno;
	private int m_end_sno;
	private new_t new_song;
	final char rhythm_tlist[] = {
			'!', ')', '*', '1', '9', ':'
	};
	static final int org_tlist[] = {
			1, 9, 10, 17, 25, 26
	};
	private final short MAX_SEX_KEY = 42;
	private final short MINER = 128;
	private keytable_t midikey[];
	private KTrack Track;

}
