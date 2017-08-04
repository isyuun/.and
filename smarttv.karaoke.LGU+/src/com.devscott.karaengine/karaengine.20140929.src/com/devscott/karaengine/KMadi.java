// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   KMadi.java

package com.devscott.karaengine;

import android.util.Log;

// Referenced classes of package com.devscott.karaengine:
//            Global, midi_tick_t, song_t, mtrk_t, 
//            Serial, KMidi, note_t, LY, 
//            end_t, KRhythm, midi_pos_t, KMemory, 
//            KEnvr, sng_t, KString

public class KMadi
{

    public KMadi()
    {
    }

    public static void madi_tick_init(short numerator, short denominator)
    {
        Global.Inst().fp.madi_tick = new midi_tick_t((Global.Inst().fp.timebase * numerator * 4) / denominator);
        Global.Inst().fp.madi_cur_tick = new midi_tick_t();
    }

    static void madi_midi_init()
    {
        Global.Inst().fp.save_trk = (mtrk_t[])Global.Inst().fp.imidi.clone();
        Global.Inst().fp.old_drum_vol = (sdrum_t[][])Global.Inst().fp.cur_drum_vol.clone();
    }

    static void madi_midi_back()
    {
        Global.Inst().fp.imidi = (mtrk_t[])Global.Inst().fp.save_trk.clone();
        Global.Inst().fp.cur_drum_vol = (sdrum_t[][])Global.Inst().fp.old_drum_vol.clone();
        for(int i = 0; i < Global.Inst().fp.mtrks; i++)
        {
            mtrk_t mp = Global.Inst().fp.imidi[i];
            if((mp.mode & 8) == 8)
            {
                int ch = mp.type % 16;
                if(mp.para != '\377')
                    Serial.MIDI_SEND3(mp.type, 176 + ch, 21, mp.para);
                if(mp.patch != '\377')
                    Serial.MIDI_SEND3(mp.type, 176 + ch, 22, mp.patch);
            } else
            {
                madi_midi_back_track(mp.type, i, mp);
            }
        }

    }

    public static void madi_midi_back_track(char type, int inx, mtrk_t mp)
    {
        if(type < ' ')
        {
            byte ch = (byte)(type % 16);
            Serial.MIDI_SEND3(type, 176 + ch, 1, 0);
            Serial.MIDI_SEND3(type, 224 + ch, 0, 64);
            if((mp.mode & 1) == 1)
                KMidi.patch_drum_volume_change(type, mp);
            if(mp.rpn < '\200')
            {
                Serial.MIDI_SEND3(type, 176 + ch, 100, 0);
                Serial.MIDI_SEND3(type, 176 + ch, 101, 0);
                Serial.MIDI_SEND3(type, 176 + ch, 6, mp.rpn);
            } else
            {
                mp.rpn = '\377';
            }
            Serial.MIDI_SEND3(type, 176 + ch, 123, 0);
            for(int i = 0; i < 128; i++)
                Global.Inst().fp.note[inx][i].reset();

        } else
        {
            Log.e("ke", (new StringBuilder("CMadi::type ")).append(type).toString());
        }
    }

    public boolean ending_process(midi_tick_t tick)
    {
        LY s = new LY();
        int cno = 0;
        mtrk_t mpgs = Global.Inst().fp.save_trk[Global.Inst().fp.gstrk];
        mpgs.para = '\377';
        Global.Inst().fp.ending.pcm_cur_tick.inc(tick);
        for(; tick.valid(); tick.dec())
        {
            KRhythm.rhythm_skip(Global.Inst().fp);
            for(int i = 0; i < Global.Inst().fp.mtrks; i++)
            {
                mtrk_t mp = Global.Inst().fp.save_trk[i];
                if(mp.addr.valid() && (mp.mode & 4) != 4)
                {
                    mp.tick.dec(mp.speed);
                    while(!mp.tick.valid() || (mp.tick.tick() & 0x80000000L) > 0L) 
                    {
                        mp.addr = KMidi.skip_midi(mp.addr, mp.type, i, mp);
                        if(!mp.addr.valid())
                            break;
                        mp.tick.inc(KMemory.get_midi_tick(mp.addr));
                        if(i == Global.Inst().fp.gstrk && !mp.tick.valid())
                            mp.tick.set(1L);
                        if(!KEnvr.is(258))
                            return false;
                        if(!Global.Inst().IsCtrl(32))
                            return false;
                    }
                }
            }

            if(mpgs.para <= '\004')
            {
                if(mpgs.para != 0)
                    cno++;
                mpgs.para = '\377';
            }
        }

        if(Global.Inst().fp.ending.lyric_ptr.valid())
            if(cno >= Global.Inst().fp.ending.lyric_cnt)
            {
                cno -= Global.Inst().fp.ending.lyric_cnt;
                int country = Global.Inst().fp.song.country;
                while(cno > 0) 
                {
                    if(!Global.Inst().fp.ending.lyric_ptr.valid())
                        break;
                    if(country == 4)
                    {
                        Global.Inst().fp.ending.lyric_ptr = KMemory.get_midi_line(s, Global.Inst().fp.ending.lyric_ptr);
                        Global.Inst().fp.ending.lyric_ptr = KMemory.get_midi_line(s, Global.Inst().fp.ending.lyric_ptr);
                    }
                    Global.Inst().fp.ending.lyric_ptr = KMemory.get_midi_line(s, Global.Inst().fp.ending.lyric_ptr);
                    int eno = KString.get_erase_cnt(s, country);
                    if(eno > 0)
                    {
                        if(cno < eno)
                        {
                            Log.e("ke", "ending-lyric counter");
                            cno = 0;
                        } else
                        {
                            cno -= eno;
                        }
                    } else
                    if(Global.Inst().fp.song.is_medley_song)
                    {
                        for(int i = 0; i < 5; i++)
                        {
                            if(!Global.Inst().fp.ending.lyric_ptr.valid())
                                break;
                            Global.Inst().fp.ending.lyric_ptr = KMemory.get_midi_line(s, Global.Inst().fp.ending.lyric_ptr);
                            if(KString.get_erase_cnt(s, country) <= 0)
                                break;
                        }

                    }
                    if(!KEnvr.is(258))
                        return false;
                    if(!Global.Inst().IsCtrl(32))
                        return false;
                }
            } else
            {
                return false;
            }
        Log.d("ke", "Ending stop");
        return true;
    }
}
