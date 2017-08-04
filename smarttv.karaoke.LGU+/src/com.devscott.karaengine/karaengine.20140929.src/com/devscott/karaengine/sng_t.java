// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   song_t.java

package com.devscott.karaengine;

import java.io.*;

// Referenced classes of package com.devscott.karaengine:
//            key_t, endian, midi_pos_t

class sng_t
{

    public sng_t()
    {
        title = new byte[2][28];
        field = new byte[3][28];
        lyric = new byte[28];
        chorus_pos = new long[64];
        gasu_pos = new long[64];
        key = new key_t();
    }

    void readData(DataInputStream in)
    {
        try
        {
            magic_number = endian.G4LTBP(in.readInt());
            revision = endian.G4LTBP(in.readInt());
            rsong_no = endian.G4LTBP(in.readInt());
            country = in.readByte();
            year = in.readByte();
            rhythm = in.readByte();
            genre = in.readByte();
            key.sex = in.readByte();
            key.man_key = in.readByte();
            key.woman_key = in.readByte();
            numerator = in.readByte();
            denominator = in.readByte();
            is_medley_song = in.readByte() > 0;
            key.miner = in.readByte();
            back_genre = in.readByte();
            for(int i = 0; i < 2; i++)
                in.read(title[i], 0, 28);

            for(int i = 0; i < 3; i++)
                in.read(field[i], 0, 28);

            in.read(lyric, 0, 28);
            int pos = endian.G4LTBP(in.readInt());
            cmp_pos = new midi_pos_t(pos);
            cmp_len = endian.G4LTBP(in.readInt());
            pos = endian.G4LTBP(in.readInt());
            lyric_pos = new midi_pos_t();
            lyric_len = endian.G4LTBP(in.readInt());
            pos = endian.G4LTBP(in.readInt());
            midi_pos = new midi_pos_t(pos);
            midi_len = endian.G4LTBP(in.readInt());
            chorus_cnt = endian.G4LTBP(in.readInt());
            for(int i = 0; i < 64; i++)
                chorus_pos[i] = endian.G4LTBP(in.readInt());

            gasu_cnt = endian.G4LTBP(in.readInt());
            for(int i = 0; i < 64; i++)
                gasu_pos[i] = in.readInt();

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    void read(RandomAccessFile in)
    {
        try
        {
            magic_number = endian.G4LTBP(in.readInt());
            revision = endian.G4LTBP(in.readInt());
            rsong_no = endian.G4LTBP(in.readInt());
            country = in.readByte();
            year = in.readByte();
            rhythm = in.readByte();
            genre = in.readByte();
            key.sex = in.readByte();
            key.man_key = in.readByte();
            key.woman_key = in.readByte();
            numerator = in.readByte();
            denominator = in.readByte();
            is_medley_song = in.readByte() > 0;
            key.miner = in.readByte();
            back_genre = in.readByte();
            for(int i = 0; i < 2; i++)
                in.read(title[i], 0, 28);

            for(int i = 0; i < 3; i++)
                in.read(field[i], 0, 28);

            in.read(lyric, 0, 28);
            int pos = endian.G4LTBP(in.readInt());
            cmp_pos = new midi_pos_t(pos);
            cmp_len = endian.G4LTBP(in.readInt());
            pos = endian.G4LTBP(in.readInt());
            lyric_pos = new midi_pos_t();
            lyric_len = endian.G4LTBP(in.readInt());
            pos = endian.G4LTBP(in.readInt());
            midi_pos = new midi_pos_t(pos);
            midi_len = endian.G4LTBP(in.readInt());
            chorus_cnt = endian.G4LTBP(in.readInt());
            for(int i = 0; i < 64; i++)
                chorus_pos[i] = endian.G4LTBP(in.readInt());

            gasu_cnt = endian.G4LTBP(in.readInt());
            for(int i = 0; i < 64; i++)
                gasu_pos[i] = in.readInt();

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
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
