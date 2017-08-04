// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Global.java

package com.devscott.karaengine;

import android.content.res.AssetManager;
import com.joyul.streaming.StreamingMp3Player;
import com.kumyoung.gtvkaraoke.ATVKaraokeActivity;

// Referenced classes of package com.devscott.karaengine:
//            song_t, KMidiFS, Player, Setup, 
//            Disp

public class Global
{

    private Global()
    {
        midi_tick_count = 0L;
        mmedley_mode = false;
        fp = null;
        ctrl_status = 0;
        midi_key = 0;
        madi_count = 0;
        midi_speed = 0;
        midi_sex = 0;
        midi_new = false;
        std_tick = 16500;
        in_play = false;
        is_pause = false;
        mlyric_mode = false;
        key_lock = true;
        fire_mode = false;
        sio_coin_present = false;
        bonus_flag = false;
        kio_holdplay = false;
        pcm_cur_tick = 0;
        decomp_stop = false;
        wait_any_key = false;
        game_mode = false;
        game_back_image = false;
        quiz_mode = false;
        max_quiz_entry = 0;
        quiz_back_image = false;
        return_to_quiz = false;
        app = null;
        midifs = null;
        player = null;
        setup = null;
        disp = null;
        streamMp3player = null;
        fp = new song_t();
        rawMIDI = new byte[0x20000];
        rawSOK = new byte[8192];
    }

    public static Global Inst()
    {
        return _instance;
    }

    public boolean IsCtrl(int val)
    {
        return (ctrl_status & val) == val;
    }

    public static int DPFromPixel(int pixel)
    {
        return (int)(((float)pixel / 2.0F) * scale);
    }

    public static int PixelFromDP(int DP)
    {
        return (int)(((float)DP / scale) * 2.0F);
    }

    public static final Boolean isDemo = Boolean.valueOf(false);
    public static final Boolean isRelease = Boolean.valueOf(true);
    public static final Boolean isGTV = Boolean.valueOf(false);
    public static final Boolean isTestBed = Boolean.valueOf(false);
    public static final Boolean isTestPayment = Boolean.valueOf(false);
    public static final Boolean isDebugGrid = Boolean.valueOf(false);
    public static final Boolean isDebugLog = Boolean.valueOf(false);
    public static Boolean isMp3 = Boolean.valueOf(true);
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
    private static Global _instance = new Global();
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
