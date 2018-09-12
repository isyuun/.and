// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   KFileIO.java

package com.devscott.karaengine;

import StringBuffer;

// Referenced classes of package com.devscott.karaengine:
//            FH, midi_pos_t

public class KFileIO
{

    public KFileIO()
    {
        throw new Error("Unresolved compilation problems: \n\tThe import java.io.File cannot be resolved\n\tThe import android.util cannot be resolved\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tStringBuffer cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n\tString cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n\tThe method get_document_path() from the type KFileIO refers to the missing type StringBuffer\n\tString cannot be resolved\n\tThe method get_contents_ext() from the type KFileIO refers to the missing type String\n\tStringBuffer cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n");
    }

    StringBuffer get_root_path()
    {
        throw new Error("Unresolved compilation problems: \n\tStringBuffer cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n");
    }

    static StringBuffer get_document_path()
    {
        throw new Error("Unresolved compilation problems: \n\tStringBuffer cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n");
    }

    StringBuffer get_rlive_path()
    {
        throw new Error("Unresolved compilation problems: \n\tStringBuffer cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n");
    }

    StringBuffer get_rsong_path()
    {
        throw new Error("Unresolved compilation problems: \n\tStringBuffer cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n");
    }

    StringBuffer get_rnew_path()
    {
        throw new Error("Unresolved compilation problems: \n\tStringBuffer cannot be resolved to a type\n\tStringBuffer cannot be resolved to a type\n");
    }

    static String get_contents_ext()
    {
        throw new Error("Unresolved compilation problem: \n\tString cannot be resolved to a type\n");
    }

    void debug_filehandler(int i)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public static void make_songky2_fname(char ac[], long l)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public static void make_song_fname(StringBuffer stringbuffer, long l)
    {
        throw new Error("Unresolved compilation problems: \n\tStringBuffer cannot be resolved to a type\n\tThe method get_document_path() from the type KFileIO refers to the missing type StringBuffer\n\tString cannot be resolved\n\tThe method get_contents_ext() from the type KFileIO refers to the missing type String\n");
    }

    public static void make_song_live_fname(char ac[], long l)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public static void make_song_dname(char ac[], long l)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public static void make_new_fname(StringBuffer stringbuffer, long l)
    {
        throw new Error("Unresolved compilation problem: \n\tStringBuffer cannot be resolved to a type\n");
    }

    public static void make_new_fname(char ac[], long l)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public static FH dopen(int i, StringBuffer stringbuffer, boolean flag)
    {
        throw new Error("Unresolved compilation problem: \n\tStringBuffer cannot be resolved to a type\n");
    }

    public static FH dopen(int i, char ac[], boolean flag)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public static void dclose(FH fh)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public static long dsize(FH fh)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public static int dseek(FH fh, midi_pos_t midi_pos_t)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public static int dseek(FH fh, long l)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public static long dtell(FH fh)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public static final int ROOT_DRIVE = 65281;
    public static final int SYS2_DRIVE = 65282;
    public static final int TMRW_DRIVE = 65284;
    public static final int DATA_DRIVE = 65288;
    public static final int ALL_DRIVE = 65535;
    public static final int BAD_DRIVE = 65534;
    public static final int PGM_DRIVE = 0;
    private static final short MAX_OPEN_FILE_HANDLE = 50;
    public int g_file_handle[];
    public long g_file_handle_seek[];
    public char g_file_handle_name[][];
}
