// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Global.java

package com.devscott.karaengine;

import AssetManager;
import Boolean;
import com.joyul.streaming.StreamingMp3Player;
import com.kumyoung.gtvkaraoke.ATVKaraokeActivity;

// Referenced classes of package com.devscott.karaengine:
//            song_t, KMidiFS, Player, Setup, 
//            Disp

public class Global
{

    private Global()
    {
        throw new Error("Unresolved compilation problems: \n\tThe import java.util.concurrent cannot be resolved\n\tThe import android.content cannot be resolved\n\tThe import android.util cannot be resolved\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tBoolean cannot be resolved to a type\n\tBoolean cannot be resolved to a type\n\tBoolean cannot be resolved to a type\n\tBoolean cannot be resolved to a type\n\tBoolean cannot be resolved to a type\n\tBoolean cannot be resolved to a type\n\tBoolean cannot be resolved to a type\n\tBoolean cannot be resolved to a type\n\tAssetManager cannot be resolved to a type\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tThread cannot be resolved\n\tInterruptedException cannot be resolved to a type\n\tLog cannot be resolved\n\tLock cannot be resolved to a type\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n");
    }

    public static Global Inst()
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public boolean IsCtrl(int i)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public static int DPFromPixel(int i)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public static int PixelFromDP(int i)
    {
        throw new Error("Unresolved compilation problem: \n");
    }

    public static final Boolean isDemo;
    public static final Boolean isRelease;
    public static final Boolean isGTV;
    public static final Boolean isTestBed;
    public static final Boolean isTestPayment;
    public static final Boolean isDebugGrid;
    public static final Boolean isDebugLog;
    public static Boolean isMp3;
    public AssetManager as;
    static final int PLAY_TITLE_READY = 1;
    static final int PLAY_TITLE_DUMP_START = 2;
    static final int PLAY_TITLE_DUMP = 4;
    static final int PLAY_PLAY_READY = 8;
    static final int PLAY_INTERVAL = 16;
    static final int PLAY_MTITLE_READY = 32;
    static final int PLAY_NOTE4_DUMP = 64;
    static final int PLAY_SCORE_ANIM = 128;
    static final int PLAY_SCORE_END = 256;
    static final int CTRL_RUBI_PRESENT = 1;
    static final int CTRL_LYRIC_OFF = 2;
    static final int CTRL_GASU_ON = 4;
    static final int CTRL_CHORUS_ON = 8;
    static final int CTRL_NOTE_SKIP = 16;
    static final int CTRL_ENDING = 32;
    static final int CTRL_ENDING_REQ = 64;
    static final int CTRL_1GEOL = 128;
    static final int CTRL_LYRIC_CENTER = 256;
    static final int CTRL_RHYTHM_USER = 512;
    static final int CTRL_RHYTHM_INT = 1024;
    static final int CTRL_RHYTHM_LOCK = 2048;
    static final int CTRL_RHYTHM_NEXT = 4096;
    static final int CTRL_NEXT_KEY_LOCK = 8192;
    static final int CTRL_INTERVAL = 16384;
    public long midi_tick_count;
    public boolean mmedley_mode;
    public int cur_fh;
    public song_t fp;
    public byte rawMIDI[];
    public byte rawSOK[];
    int ctrl_status;
    int midi_key;
    int madi_count;
    int midi_speed;
    int midi_sex;
    boolean midi_new;
    public int std_tick;
    public boolean in_play;
    boolean is_pause;
    boolean mlyric_mode;
    int cur_midi_key;
    int cur_midi_sex;
    int cur_midi_new;
    int cur_midi_step_game;
    int cur_midi_step_gasu;
    int cur_midi_gasu_ctrl;
    int cur_midi_geol_ctrl;
    int play_status;
    boolean key_lock;
    int image_play_check;
    boolean fire_mode;
    boolean sio_coin_present;
    boolean bonus_flag;
    boolean kio_holdplay;
    int pcm_cur_tick;
    int bmp_decomp_lock;
    boolean decomp_stop;
    boolean wait_any_key;
    boolean game_mode;
    int game_stop_key;
    boolean game_back_image;
    boolean quiz_mode;
    int max_quiz_entry;
    boolean quiz_back_image;
    boolean return_to_quiz;
    private static Global _instance;
    private static final float DEFAULT_XHDIP_DENSITY_SCALE = 2F;
    private static final float DEFAULT_HDIP_DENSITY_SCALE = 1.5F;
    private static final float DEFAULT_MDIP_DENSITY_SCALE = 1F;
    private static final float DEFAULT_LDIP_DENSITY_SCALE = 0.75F;
    public static float scale;
    public static float density;
    public ATVKaraokeActivity app;
    public KMidiFS midifs;
    public Player player;
    public Setup setup;
    public Disp disp;
    public StreamingMp3Player streamMp3player;
}
