// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   song_t.java

package com.devscott.karaengine;


// Referenced classes of package com.devscott.karaengine:
//            LY

class make_t
{

    public make_t()
    {
        step = new char[56];
        lyric = new LY();
        rubi1 = new LY();
        rubi2 = new LY();
    }

    boolean ready;
    int color_inx;
    char step[];
    LY lyric;
    LY rubi1;
    LY rubi2;
    int xz;
    int yz;
    long lyric_pos;
}
