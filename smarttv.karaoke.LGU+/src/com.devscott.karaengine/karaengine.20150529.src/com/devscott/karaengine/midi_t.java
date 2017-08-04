// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   song_t.java

package com.devscott.karaengine;


// Referenced classes of package com.devscott.karaengine:
//            KMemory

class midi_t
{

    midi_t()
    {
        hdr = 0L;
        lng = 0L;
    }

    void read(long pos)
    {
        hdr = KMemory.get_midi_int((int)pos);
        pos += 4L;
        lng = KMemory.get_midi_int((int)pos);
        pos += 4L;
    }

    int sizeof()
    {
        return 8;
    }

    public long hdr;
    public long lng;
}
