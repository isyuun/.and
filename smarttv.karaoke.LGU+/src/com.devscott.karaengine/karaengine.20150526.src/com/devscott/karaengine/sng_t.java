// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   song_t.java

package com.devscott.karaengine;

import DataInputStream;
import RandomAccessFile;

// Referenced classes of package com.devscott.karaengine:
//            key_t, midi_pos_t

class sng_t
{

    public sng_t()
    {
        throw new Error("Unresolved compilation problems: \n\tThe type java.io.Serializable cannot be resolved. It is indirectly referenced from required .class files\n\tThe type java.io.ObjectInputStream cannot be resolved. It is indirectly referenced from required .class files\n\tThe import java.io.DataInputStream cannot be resolved\n\tThe import java.io.File cannot be resolved\n\tThe import java.io.FileInputStream cannot be resolved\n\tThe import java.io.FileNotFoundException cannot be resolved\n\tThe import java.io.IOException cannot be resolved\n\tThe import java.io.ObjectInputStream cannot be resolved\n\tThe import java.io.RandomAccessFile cannot be resolved\n\tThe import java.io.Serializable cannot be resolved\n\tThe import java.io.StreamCorruptedException cannot be resolved\n\tThe import java.io.UnsupportedEncodingException cannot be resolved\n\tThe import java.nio cannot be resolved\n\tThe import java.nio cannot be resolved\n\tThe import android.util cannot be resolved\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tFile cannot be resolved to a type\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tFile cannot be resolved to a type\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tFile cannot be resolved to a type\n\tFile cannot be resolved to a type\n\tString cannot be resolved to a type\n\tFile cannot be resolved to a type\n\tFile cannot be resolved to a type\n\tRandomAccessFile cannot be resolved to a type\n\tRandomAccessFile cannot be resolved to a type\n\tFileNotFoundException cannot be resolved to a type\n\tFile cannot be resolved to a type\n\tRandomAccessFile cannot be resolved to a type\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tString cannot be resolved to a type\n\tThe method toUnicode() from the type LY refers to the missing type String\n\tString cannot be resolved to a type\n\tString cannot be resolved to a type\n\tString cannot be resolved to a type\n\tUnsupportedEncodingException cannot be resolved to a type\n\tCloneable cannot be resolved to a type\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tObject cannot be resolved to a type\n\tCloneNotSupportedException cannot be resolved to a type\n\tObject cannot be resolved to a type\n\tCloneable cannot be resolved to a type\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tObject cannot be resolved to a type\n\tCloneNotSupportedException cannot be resolved to a type\n\tObject cannot be resolved to a type\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tCloneable cannot be resolved to a type\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tObject cannot be resolved to a type\n\tCloneNotSupportedException cannot be resolved to a type\n\tObject cannot be resolved to a type\n\tCannot invoke clone() on the array type char[]\n\tCannot invoke clone() on the array type char[]\n\tThe method clone() from the type midi_pos_t refers to the missing type Object\n\tThe method clone() from the type midi_tick_t refers to the missing type Object\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tDataInputStream cannot be resolved to a type\n\tIOException cannot be resolved to a type\n\tRandomAccessFile cannot be resolved to a type\n\tIOException cannot be resolved to a type\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tException cannot be resolved to a type\n");
    }

    void readData(DataInputStream datainputstream)
    {
        throw new Error("Unresolved compilation problems: \n\tDataInputStream cannot be resolved to a type\n\tIOException cannot be resolved to a type\n");
    }

    void read(RandomAccessFile randomaccessfile)
    {
        throw new Error("Unresolved compilation problems: \n\tRandomAccessFile cannot be resolved to a type\n\tIOException cannot be resolved to a type\n");
    }

    static final long OLD_REVISION = 1281L;
    static final short NO_TITLE = 2;
    static final short MAX_LYRIC_CHAR = 27;
    static final short MKSONG_OBJ_LEN = 28;
    static final short MAX_CHORUS_NO = 64;
    static final short MAX_CHORUS_FILE = 64;
    static final short NO_FILED = 3;
    static final byte MAX_MIDI_HANDLE = 2;
    static final byte NO_LINE = 9;
    static final short OBJECT_LEN = 56;
    static final short MAX_CHORUS_CMD = 128;
    static final short LYRIC_INFOR_LINES = 6;
    static final int LYRIC_START = 0;
    static final int LYRIC_ERASE1 = 1;
    static final int LYRIC_ERASE2 = 2;
    static final int LYRIC_END = 3;
    static final int LYRIC_TITLE_END = 4;
    static final int LYRIC_DUAL_ERASE = 5;
    long magic_number;
    long revision;
    long rsong_no;
    short country;
    short year;
    short rhythm;
    short genre;
    short numerator;
    short denominator;
    key_t key;
    boolean is_medley_song;
    short back_genre;
    byte title[][];
    byte field[][];
    byte lyric[];
    midi_pos_t cmp_pos;
    long cmp_len;
    midi_pos_t lyric_pos;
    long lyric_len;
    midi_pos_t midi_pos;
    long midi_len;
    long chorus_cnt;
    long chorus_pos[];
    long gasu_cnt;
    long gasu_pos[];
}
