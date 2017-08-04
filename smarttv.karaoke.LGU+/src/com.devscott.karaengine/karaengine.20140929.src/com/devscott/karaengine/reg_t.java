// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   song_t.java

package com.devscott.karaengine;


// Referenced classes of package com.devscott.karaengine:
//            key_t

class reg_t
{

    public reg_t()
    {
        key = new key_t();
        title = new char[48];
        singer = new char[24];
        lyric = new char[16];
    }

    public static final short REG_TITLE_LEN = 48;
    public static final short REG_SINGER_LEN = 24;
    public static final short REG_LYRIC_LEN = 16;
    long sno;
    char country;
    char rhythm;
    char medley;
    char genre;
    key_t key;
    char title[];
    char singer[];
    char lyric[];
}
