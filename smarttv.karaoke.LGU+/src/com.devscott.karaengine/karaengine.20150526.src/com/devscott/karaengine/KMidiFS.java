// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   KMidiFS.java

package com.devscott.karaengine;

import Boolean;

// Referenced classes of package com.devscott.karaengine:
//            song_t, new_t, KTrack, sng_t, 
//            reg_t, key_t, midi_pos_t, mtrk_t

public class KMidiFS
{
    class keytable_t
    {

        String name;
        int id;
        final KMidiFS this$0;

        public keytable_t()
        {
            throw new Error("Unresolved compilation problems: \n\tThe import java.io.DataInputStream cannot be resolved\n\tThe import java.io.FileNotFoundException cannot be resolved\n\tThe import java.io.IOException cannot be resolved\n\tThe import java.io.InputStream cannot be resolved\n\tThe import java.io.RandomAccessFile cannot be resolved\n\tThe import android.util cannot be resolved\n\tBoolean cannot be resolved to a type\n\tString cannot be resolved to a type\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tString cannot be resolved to a type\n\tString cannot be resolved to a type\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tString cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n\tLog cannot be resolved\n\tBoolean cannot be resolved to a type\n\tInputStream cannot be resolved to a type\n\tAssetManager cannot be resolved to a type\n\tIOException cannot be resolved to a type\n\tDataInputStream cannot be resolved to a type\n\tDataInputStream cannot be resolved to a type\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tRandomAccessFile cannot be resolved to a type\n\tLog cannot be resolved\n\tStringBuffer cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tBoolean cannot be resolved to a type\n\tInputStream cannot be resolved to a type\n\tAssetManager cannot be resolved to a type\n\tIOException cannot be resolved to a type\n\tDataInputStream cannot be resolved to a type\n\tDataInputStream cannot be resolved to a type\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tRandomAccessFile cannot be resolved to a type\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tBoolean cannot be resolved to a type\n\tSystem cannot be resolved\n\tSystem cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tThe method debug_string() from the type LY refers to the missing type String\n\tLog cannot be resolved\n\tString cannot be resolved to a type\n\tString cannot be resolved to a type\n\tLog cannot be resolved\n\tCannot invoke clone() on the array type mtrk_t[]\n\tThe method clone() from the type mtrk_t refers to the missing type Object\n\tCloneNotSupportedException cannot be resolved to a type\n\tThe method clone() from the type mtrk_t refers to the missing type Object\n\tCloneNotSupportedException cannot be resolved to a type\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tThe method clone() from the type mtrk_t refers to the missing type Object\n\tCloneNotSupportedException cannot be resolved to a type\n\tThe method clone() from the type mtrk_t refers to the missing type Object\n\tCloneNotSupportedException cannot be resolved to a type\n\tThe method clone() from the type mtrk_t refers to the missing type Object\n\tCloneNotSupportedException cannot be resolved to a type\n\tRandomAccessFile cannot be resolved to a type\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tBoolean cannot be resolved to a type\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n");
        }

        public keytable_t(String s, int i)
        {
            throw new Error("Unresolved compilation problems: \n\tThe import java.io.DataInputStream cannot be resolved\n\tThe import java.io.FileNotFoundException cannot be resolved\n\tThe import java.io.IOException cannot be resolved\n\tThe import java.io.InputStream cannot be resolved\n\tThe import java.io.RandomAccessFile cannot be resolved\n\tThe import android.util cannot be resolved\n\tBoolean cannot be resolved to a type\n\tString cannot be resolved to a type\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tString cannot be resolved to a type\n\tString cannot be resolved to a type\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tString cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n\tLog cannot be resolved\n\tBoolean cannot be resolved to a type\n\tInputStream cannot be resolved to a type\n\tAssetManager cannot be resolved to a type\n\tIOException cannot be resolved to a type\n\tDataInputStream cannot be resolved to a type\n\tDataInputStream cannot be resolved to a type\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tRandomAccessFile cannot be resolved to a type\n\tLog cannot be resolved\n\tStringBuffer cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tBoolean cannot be resolved to a type\n\tInputStream cannot be resolved to a type\n\tAssetManager cannot be resolved to a type\n\tIOException cannot be resolved to a type\n\tDataInputStream cannot be resolved to a type\n\tDataInputStream cannot be resolved to a type\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tRandomAccessFile cannot be resolved to a type\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tBoolean cannot be resolved to a type\n\tSystem cannot be resolved\n\tSystem cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tThe method debug_string() from the type LY refers to the missing type String\n\tLog cannot be resolved\n\tString cannot be resolved to a type\n\tString cannot be resolved to a type\n\tLog cannot be resolved\n\tCannot invoke clone() on the array type mtrk_t[]\n\tThe method clone() from the type mtrk_t refers to the missing type Object\n\tCloneNotSupportedException cannot be resolved to a type\n\tThe method clone() from the type mtrk_t refers to the missing type Object\n\tCloneNotSupportedException cannot be resolved to a type\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tThe method clone() from the type mtrk_t refers to the missing type Object\n\tCloneNotSupportedException cannot be resolved to a type\n\tThe method clone() from the type mtrk_t refers to the missing type Object\n\tCloneNotSupportedException cannot be resolved to a type\n\tThe method clone() from the type mtrk_t refers to the missing type Object\n\tCloneNotSupportedException cannot be resolved to a type\n\tRandomAccessFile cannot be resolved to a type\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tBoolean cannot be resolved to a type\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n");
        }
    }


    public KMidiFS()
    {
        throw new Error("Unresolved compilation problems: \n\tThe import java.io.DataInputStream cannot be resolved\n\tThe import java.io.FileNotFoundException cannot be resolved\n\tThe import java.io.IOException cannot be resolved\n\tThe import java.io.InputStream cannot be resolved\n\tThe import java.io.RandomAccessFile cannot be resolved\n\tThe import android.util cannot be resolved\n\tBoolean cannot be resolved to a type\n\tString cannot be resolved to a type\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tString cannot be resolved to a type\n\tString cannot be resolved to a type\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tString cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n\tLog cannot be resolved\n\tBoolean cannot be resolved to a type\n\tInputStream cannot be resolved to a type\n\tAssetManager cannot be resolved to a type\n\tIOException cannot be resolved to a type\n\tDataInputStream cannot be resolved to a type\n\tDataInputStream cannot be resolved to a type\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tRandomAccessFile cannot be resolved to a type\n\tLog cannot be resolved\n\tStringBuffer cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tBoolean cannot be resolved to a type\n\tInputStream cannot be resolved to a type\n\tAssetManager cannot be resolved to a type\n\tIOException cannot be resolved to a type\n\tDataInputStream cannot be resolved to a type\n\tDataInputStream cannot be resolved to a type\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tRandomAccessFile cannot be resolved to a type\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tBoolean cannot be resolved to a type\n\tSystem cannot be resolved\n\tSystem cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tThe method debug_string() from the type LY refers to the missing type String\n\tLog cannot be resolved\n\tString cannot be resolved to a type\n\tString cannot be resolved to a type\n\tLog cannot be resolved\n\tCannot invoke clone() on the array type mtrk_t[]\n\tThe method clone() from the type mtrk_t refers to the missing type Object\n\tCloneNotSupportedException cannot be resolved to a type\n\tThe method clone() from the type mtrk_t refers to the missing type Object\n\tCloneNotSupportedException cannot be resolved to a type\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tThe method clone() from the type mtrk_t refers to the missing type Object\n\tCloneNotSupportedException cannot be resolved to a type\n\tThe method clone() from the type mtrk_t refers to the missing type Object\n\tCloneNotSupportedException cannot be resolved to a type\n\tThe method clone() from the type mtrk_t refers to the missing type Object\n\tCloneNotSupportedException cannot be resolved to a type\n\tRandomAccessFile cannot be resolved to a type\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tBoolean cannot be resolved to a type\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n");
    }

    public void Huffman_init()
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public long Huffman_DSR(long l, byte abyte0[], long l1, long l2)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public boolean mfs_init()
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public void mfs_all_close()
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public void mfs_song_close(int i)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public static boolean mfs_exist(long l)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public boolean mfs_new_song(long l)
    {
        throw new Error("Unresolved compilation problems: \n\tStringBuffer cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n");
    }

    public boolean mfs_new_exist(long l)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public int mfs_next(int i)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public static int mfs_random()
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public long mfs_new_next(long l)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public boolean mfs_new_regist(long l)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public static boolean mfs_header(long l, sng_t sng_t)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public static boolean mfs_header(long l, sng_t sng_t, boolean flag, boolean flag1)
    {
        throw new Error("Unresolved compilation problems: \n\tStringBuffer cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n\tLog cannot be resolved\n\tBoolean cannot be resolved to a type\n\tInputStream cannot be resolved to a type\n\tAssetManager cannot be resolved to a type\n\tIOException cannot be resolved to a type\n\tDataInputStream cannot be resolved to a type\n\tDataInputStream cannot be resolved to a type\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tRandomAccessFile cannot be resolved to a type\n\tLog cannot be resolved\n");
    }

    public int mfs_reg(long l, reg_t reg_t)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public int mfs_open(long l, boolean flag)
    {
        throw new Error("Unresolved compilation problems: \n\tStringBuffer cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tBoolean cannot be resolved to a type\n\tInputStream cannot be resolved to a type\n\tAssetManager cannot be resolved to a type\n\tIOException cannot be resolved to a type\n\tDataInputStream cannot be resolved to a type\n\tDataInputStream cannot be resolved to a type\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tRandomAccessFile cannot be resolved to a type\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tBoolean cannot be resolved to a type\n\tSystem cannot be resolved\n\tSystem cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tThe method debug_string() from the type LY refers to the missing type String\n\tLog cannot be resolved\n\tString cannot be resolved to a type\n\tString cannot be resolved to a type\n\tLog cannot be resolved\n\tCannot invoke clone() on the array type mtrk_t[]\n\tThe method clone() from the type mtrk_t refers to the missing type Object\n\tCloneNotSupportedException cannot be resolved to a type\n\tThe method clone() from the type mtrk_t refers to the missing type Object\n\tCloneNotSupportedException cannot be resolved to a type\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tThe method clone() from the type mtrk_t refers to the missing type Object\n\tCloneNotSupportedException cannot be resolved to a type\n\tThe method clone() from the type mtrk_t refers to the missing type Object\n\tCloneNotSupportedException cannot be resolved to a type\n\tThe method clone() from the type mtrk_t refers to the missing type Object\n\tCloneNotSupportedException cannot be resolved to a type\n");
    }

    public static boolean mfs_key(long l, key_t key_t)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public long mfs_new_gasu(long l)
    {
        throw new Error("Unresolved compilation problem: \n\tRandomAccessFile cannot be resolved to a type\n");
    }

    public boolean mfs_title(long l, char ac[], int i)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public boolean mfs_singer(long l, char ac[], int i)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public boolean mfs_medley(long l)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public song_t mfs_table(int i)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public void mfs_close(int i)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public boolean mfs_chorus_init(int i, int j)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public void song_close(int i)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public void gs_track_setup(midi_pos_t midi_pos_t, int i, mtrk_t mtrk_t)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public static boolean mfs_header_check(sng_t sng_t, long l, long l1)
    {
        throw new Error("Unresolved compilation problems: \n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n");
    }

    private boolean Is9703Range(long l)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    void MidiPrepare(song_t song_t, long l)
    {
        throw new Error("Unresolved compilation problems: \n\tBoolean cannot be resolved to a type\n\tLog cannot be resolved\n\tLog cannot be resolved\n\tLog cannot be resolved\n");
    }

    private static final Boolean bMemory;
    private final short SONG_READY = 1;
    private final short SONG_BUSY = 2;
    private final short SONG_ERROR = 4;
    private int m_decomp_retry;
    private song_t m_song_handle[];
    private int m_nesting;
    private int m_start_sno;
    private int m_end_sno;
    private new_t new_song;
    final char rhythm_tlist[];
    static final int org_tlist[];
    private final short MAX_SEX_KEY = 42;
    private final short MINER = 128;
    private keytable_t midikey[];
    private KTrack Track;
}
