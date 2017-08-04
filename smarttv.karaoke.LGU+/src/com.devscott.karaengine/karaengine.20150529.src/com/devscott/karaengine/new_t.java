// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   song_t.java

package com.devscott.karaengine;


class new_t
{

    public new_t()
    {
        new_song_list = new long[500];
        sel_song_list = new long[500];
    }

    private final short MAX_NEW_SIZE = 500;
    public long id;
    public int new_song_count;
    public long new_song_list[];
    public int sel_song_count;
    public long sel_song_list[];
}
