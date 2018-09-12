// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   song_t.java

package com.devscott.karaengine;


// Referenced classes of package com.devscott.karaengine:
//            sng_t, FH, chors_t, geol_t, 
//            chrd_t, mtrk_t, ctrl_t, midi_pos_t, 
//            make_t, dump_t, midi_tick_t, end_t, 
//            sdrum_t, note_t

public class song_t
{

    public song_t()
    {
        throw new Error("Unresolved compilation problems: \n\tThe type java.io.Serializable cannot be resolved. It is indirectly referenced from required .class files\n\tThe type java.io.ObjectInputStream cannot be resolved. It is indirectly referenced from required .class files\n\tThe import java.io.DataInputStream cannot be resolved\n\tThe import java.io.File cannot be resolved\n\tThe import java.io.FileInputStream cannot be resolved\n\tThe import java.io.FileNotFoundException cannot be resolved\n\tThe import java.io.IOException cannot be resolved\n\tThe import java.io.ObjectInputStream cannot be resolved\n\tThe import java.io.RandomAccessFile cannot be resolved\n\tThe import java.io.Serializable cannot be resolved\n\tThe import java.io.StreamCorruptedException cannot be resolved\n\tThe import java.io.UnsupportedEncodingException cannot be resolved\n\tThe import java.nio cannot be resolved\n\tThe import java.nio cannot be resolved\n\tThe import android.util cannot be resolved\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tFile cannot be resolved to a type\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tFile cannot be resolved to a type\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tFile cannot be resolved to a type\n\tFile cannot be resolved to a type\n\tString cannot be resolved to a type\n\tFile cannot be resolved to a type\n\tFile cannot be resolved to a type\n\tRandomAccessFile cannot be resolved to a type\n\tRandomAccessFile cannot be resolved to a type\n\tFileNotFoundException cannot be resolved to a type\n\tFile cannot be resolved to a type\n\tRandomAccessFile cannot be resolved to a type\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tString cannot be resolved to a type\n\tThe method toUnicode() from the type LY refers to the missing type String\n\tString cannot be resolved to a type\n\tString cannot be resolved to a type\n\tString cannot be resolved to a type\n\tUnsupportedEncodingException cannot be resolved to a type\n\tCloneable cannot be resolved to a type\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tObject cannot be resolved to a type\n\tCloneNotSupportedException cannot be resolved to a type\n\tObject cannot be resolved to a type\n\tCloneable cannot be resolved to a type\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tObject cannot be resolved to a type\n\tCloneNotSupportedException cannot be resolved to a type\n\tObject cannot be resolved to a type\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tCloneable cannot be resolved to a type\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tObject cannot be resolved to a type\n\tCloneNotSupportedException cannot be resolved to a type\n\tObject cannot be resolved to a type\n\tCannot invoke clone() on the array type char[]\n\tCannot invoke clone() on the array type char[]\n\tThe method clone() from the type midi_pos_t refers to the missing type Object\n\tThe method clone() from the type midi_tick_t refers to the missing type Object\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tDataInputStream cannot be resolved to a type\n\tIOException cannot be resolved to a type\n\tRandomAccessFile cannot be resolved to a type\n\tIOException cannot be resolved to a type\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tException cannot be resolved to a type\n");
    }

    public final short FLAG_MINER = 1;
    public final short FLAG_MP3 = 2;
    public final short FLAG_MVIDEO = 4;
    public final short FLAG_MP3_STEREO = 8;
    public final short FLAG_LIVE = 128;
    public sng_t song;
    long hsong_no;
    int status;
    FH file_handle;
    FH fhReserved;
    int format;
    int timebase;
    boolean port1;
    boolean channel4;
    int chorus_inx;
    long chorus_loc;
    boolean chorus_present;
    boolean gasu_present;
    boolean inst_present;
    int chorus_cnt;
    int gasu_cnt;
    int inst_cnt;
    int geol_cnt;
    int geol_current;
    chors_t chorus_cmd[];
    chors_t gasu_cmd[];
    chors_t inst_cmd[];
    geol_t geol_cmd[];
    chrd_t chord_cmd[];
    long buf_base;
    int mtrks;
    public mtrk_t imidi[];
    int ctrl_cnt;
    boolean rhythm_present;
    ctrl_t ctrl[];
    mtrk_t org_rmidi[];
    byte mpeg_fname[];
    int mpeg_num_singer;
    int mpeg_delay;
    int ftype;
    int fsize;
    int effect;
    midi_pos_t lyric_ptr;
    midi_pos_t lyric_pos;
    midi_pos_t lyric_fptr;
    midi_pos_t lyric_mptr;
    boolean current_lyric_pos_update;
    mtrk_t midi_mptr;
    boolean multi_interval;
    make_t mlyric;
    dump_t dlyric[];
    int max_line;
    int dump_line;
    int erase_line;
    int merase_line;
    char para;
    long dump_timer;
    long interval_timer;
    long intv_logo_timer;
    int intv_logo_counter;
    long erase_timer;
    long note4_timer;
    int note4_x;
    int note4_y;
    int step[];
    int step_inx;
    int x;
    int y;
    int xz;
    int yz;
    int char_width;
    long char_tick;
    long cur_tick;
    midi_tick_t dump_tick;
    boolean stop_token;
    int erase4_timer;
    long gstick;
    byte medley_title[][];
    byte medley_country;
    public midi_tick_t madi_cur_tick;
    public midi_tick_t madi_tick;
    mtrk_t save_trk[];
    int madi_delay_tick;
    long ending_tick;
    end_t ending;
    int ch_type;
    int har_chlist[];
    int har_tlist[];
    long tempo_us;
    int bass_code;
    sdrum_t cur_drum_vol[][];
    sdrum_t old_drum_vol[][];
    note_t note[][];
    int melody_type1;
    int melody_type2;
    int gstrk;
    int rs1trk;
    int rs2trk;
    int effect1trk;
    int effect2trk;
    int chordtrk;
    int steptrk;
    int step_beat_cmd;
    int step_madi;
    long gs_total_tick;
    int step_data_present;
    int step_file_no;
    long stepmidi[][];
    int step_arrow_count;
    int new_rhythm_mode;
    int old_rhythm_mode;
    int rbuf_usage;
    int org_rtinx[];
    int new_rtinx[];
    int ctrl_inx;
    ctrl_t cur_ctrl;
    ctrl_t org_ctrl;
    long mp3_loc;
    long live_loc[];
    int mtv_no;
    boolean midi_4port_on;
    int drum_beat1;
    int drum_beat2;
    int lighton_timer;
    int lightoff_timer;
}
