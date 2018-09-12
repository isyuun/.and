// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   song_t.java

package com.devscott.karaengine;


class chrd_t
{

    public chrd_t()
    {
        chord = new short[32];
    }

    char cmd;
    long start_tick;
    long stop_tick;
    long start_time;
    short chord[];
}
