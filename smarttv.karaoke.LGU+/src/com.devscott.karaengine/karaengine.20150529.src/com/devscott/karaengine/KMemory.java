// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   KMemory.java

package com.devscott.karaengine;


// Referenced classes of package com.devscott.karaengine:
//            MainBuffer, LY, midi_pos_t

public class KMemory
{

    public KMemory()
    {
    }

    private static int getInt(int pos, byte bytes[])
    {
        int newValue = 0;
        newValue |= bytes[pos + 0] << 24 & 0xff000000;
        newValue |= bytes[pos + 1] << 16 & 0xff0000;
        newValue |= bytes[pos + 2] << 8 & 0xff00;
        newValue |= bytes[pos + 3] & 0xff;
        return newValue;
    }

    private static short getShort(int pos, byte bytes[])
    {
        short newValue = 0;
        newValue |= bytes[pos + 0] << 8 & 0xff00;
        newValue |= bytes[pos + 1] & 0xff;
        return newValue;
    }

    static int get_midi_int(long src)
    {
        byte p[] = MainBuffer.getPointer();
        return getInt((int)src, p);
    }

    static short get_midi_short(long src)
    {
        byte p[] = MainBuffer.getPointer();
        return getShort((int)src, p);
    }

    static midi_pos_t get_midi_line(LY buf, midi_pos_t pos)
    {
        buf.repose();
        buf.set((byte)0);
        if(!pos.valid())
            return pos;
        int p = buf.pos();
        for(int i = 0; i < 254; i++)
        {
            byte code = (byte)MainBuffer.get_midi_byte(pos);
            if(code == 26)
            {
                buf.set((byte)0);
                pos.disable();
                return pos;
            }
            if(code == 13 || code == 10)
            {
                pos.dec();
                break;
            }
            if(code != 32)
                p = buf.pos();
            buf.add(code);
        }

        buf.set((byte)0);
        p++;
        buf.set(p, (byte)0);
        do
        {
            byte code = (byte)MainBuffer.get_midi_byte(pos);
            if(code == 26)
            {
                pos.disable();
                return pos;
            }
            if(code == 13)
            {
                code = (byte)MainBuffer.get_midi_byte(pos);
                if(code == 26)
                {
                    pos.disable();
                    return pos;
                }
                if(code != 10)
                    pos.dec();
                break;
            }
            if(code != 10)
                continue;
            code = (byte)MainBuffer.get_midi_byte(pos);
            if(code == 26)
            {
                pos.disable();
                return pos;
            }
            if(code != 13)
                pos.dec();
            break;
        } while(true);
        return pos;
    }

    public static long get_midi_tick(midi_pos_t pos)
    {
        long val = 0L;
        char c = MainBuffer.get_midi_byte(pos);
        val = c & 0xff;
        if((c & 0xff & 0x80) == 128)
        {
            val &= 127L;
            do
            {
                c = MainBuffer.get_midi_byte(pos);
                val = (val << 7) + (long)(c & 0xff & 0x7f);
            } while((c & 0xff & 0x80) == 128);
        }
        return val;
    }

    private void set_midi_tick(long l, midi_pos_t midi_pos_t1)
    {
    }
}
