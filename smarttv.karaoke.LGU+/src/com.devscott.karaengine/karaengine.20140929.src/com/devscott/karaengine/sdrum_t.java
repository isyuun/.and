// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   song_t.java

package com.devscott.karaengine;


class sdrum_t
{

    sdrum_t()
    {
        note = '\0';
        vol = '\0';
        veloc = '\0';
    }

    void reset()
    {
        note = '\377';
        vol = '\377';
        veloc = '\377';
    }

    public char note;
    public char vol;
    public char veloc;
}
