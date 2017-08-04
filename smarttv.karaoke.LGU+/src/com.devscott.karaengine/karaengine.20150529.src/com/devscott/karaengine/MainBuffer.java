// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   song_t.java

package com.devscott.karaengine;


// Referenced classes of package com.devscott.karaengine:
//            midi_pos_t

class MainBuffer
{

    private MainBuffer()
    {
        internal_mem = new byte[0x20000];
    }

    static MainBuffer getInstance()
    {
        return _instance;
    }

    static byte[] getPointer()
    {
        return internal_mem;
    }

    static char get_midi_byte(midi_pos_t pos)
    {
        byte c = internal_mem[(int)pos.pos()];
        pos.inc();
        return (char)(c & 0xff);
    }

    static char read_midi_byte(midi_pos_t pos)
    {
        byte c = internal_mem[(int)pos.pos()];
        return (char)(c & 0xff);
    }

    static void set_midi_byte(char c, midi_pos_t midi_pos_t1)
    {
    }

    private static MainBuffer _instance = new MainBuffer();
    private static byte internal_mem[];

}
