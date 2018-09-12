// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   song_t.java

package com.devscott.karaengine;


// Referenced classes of package com.devscott.karaengine:
//            KMemory, endian

class midi_h
{

    midi_h()
    {
        hdr = 0L;
        lng = 0L;
        format = 0;
        notrk = 0;
    }

    void read(long pos)
    {
        hdr = KMemory.get_midi_int((int)pos);
        pos += 4L;
        lng = KMemory.get_midi_int((int)pos);
        pos += 4L;
        format = KMemory.get_midi_short((int)pos);
        pos += 2L;
        notrk = KMemory.get_midi_short((int)pos);
        pos += 2L;
        hdr = endian.G4LTBP((int)hdr);
        lng = endian.G4LTBP((int)lng);
        format = endian.G2LTBP(format);
        notrk = endian.G2LTBP(notrk);
    }

    int sizeof()
    {
        return 12;
    }

    public long hdr;
    public long lng;
    public short format;
    public short notrk;
}
