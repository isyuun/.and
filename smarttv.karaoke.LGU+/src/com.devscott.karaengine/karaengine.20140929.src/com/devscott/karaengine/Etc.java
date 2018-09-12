// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Global.java

package com.devscott.karaengine;

import android.util.Log;
import java.util.concurrent.locks.Lock;

// Referenced classes of package com.devscott.karaengine:
//            Global, song_t, KOS

class Etc
{

    Etc()
    {
    }

    static void usleep(int delay)
    {
        try
        {
            Thread.sleep(delay);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    static void mode_del_interval()
    {
    }

    static void debug_geol(int i)
    {
    }

    static void set_play_timer()
    {
        if(Global.Inst().fp.tempo_us <= 0L || Global.Inst().fp.timebase <= 0)
        {
            return;
        } else
        {
            long rate = 0x3938700L / (Global.Inst().fp.tempo_us / (long)Global.Inst().fp.timebase);
            Global.Inst().std_tick = (int)(KOS.TIMER_FREQ * 60L) / (int)rate;
            Log.d("ke", (new StringBuilder("TIME TICK : ")).append(0x3938700L / rate).append(" uS (TIMEBASE ").append(Global.Inst().fp.timebase).append(" TEMPO ").append(0x3938700L / Global.Inst().fp.tempo_us).append(" RATE ").append(rate).append(" )").toString());
            return;
        }
    }

    static void SetupSendSongOpen(long l)
    {
    }

    static void lock_init()
    {
    }

    static void disable()
    {
    }

    static void enable()
    {
    }

    static void OSTIMEDLY(long l)
    {
    }

    static void mode_add_interval()
    {
    }

    static Lock lock;
}
