// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   def.java

package com.devscott.karaengine;


// Referenced classes of package com.devscott.karaengine:
//            Global, song_t, mtrk_t, midi_pos_t, 
//            midi_tick_t

public class def
{

    public static boolean SONG_HIGH_OVER(int sno)
    {
        return sno >= 0x186a0;
    }

    public static boolean _SAMSC55()
    {
        return true;
    }

    public static int RHYTHM_START_TICK()
    {
        return _SAMSC55() ? 39 : 19;
    }

    public static int RHYTHM_PLAY_TICK()
    {
        return RHYTHM_START_TICK() - 2;
    }

    public def()
    {
        throw new Error("Unresolved compilation problem: \n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n");
    }

    static long SAVE_RS_TICK()
    {
        if(Global.Inst().fp.save_trk[Global.Inst().fp.rs1trk].addr.valid())
            return Global.Inst().fp.save_trk[Global.Inst().fp.rs1trk].tick.tick();
        else
            return Global.Inst().fp.save_trk[Global.Inst().fp.rs2trk].tick.tick();
    }

    static long CUR_RS_TICK()
    {
        if(Global.Inst().fp.imidi[Global.Inst().fp.rs1trk].addr.valid())
            return Global.Inst().fp.imidi[Global.Inst().fp.rs1trk].tick.tick();
        else
            return Global.Inst().fp.imidi[Global.Inst().fp.rs2trk].tick.tick();
    }

    static char CUR_RS_PARA()
    {
        if(Global.Inst().fp.imidi[Global.Inst().fp.rs1trk].addr.valid())
            return Global.Inst().fp.imidi[Global.Inst().fp.rs1trk].para;
        else
            return Global.Inst().fp.imidi[Global.Inst().fp.rs2trk].para;
    }

    static char get_octave(int n)
    {
        return 'A';
    }

    public static final int RPT_SINGLE = 0;
    public static final int RPT_REPEAT = 1;
    public static final int RPT_CONTINUE = 2;
    public static final int RPT_RANDOM = 3;
    public static final int RPT_1GEOL = 4;
    public static final int MAX_SONG = 0x186a0;
    public static final boolean NOTE_ALL_SAVE = true;
    public static final short MAX_NOTE = 128;
    public static final short MAX_FILENAME = 255;
    public static final short MAX_STRING = 255;
    public static final short MAX_LIVE_CHANNEL = 6;
    public static final short NONE_MIDI = -1;
    public static final short MAX_ACCEPT_SONG = 170;
    public static final short MAX_SEL_SONG = 170;
    public static final short MAX_NOKEY_NEW_SONG = 100;
    public static final short MAX_NOKEY_ACCEPT_SONG = 500;
    public static final long NEW_FILE_ID = 0xffffff03L;
    public static final char MAX_COUNTRY = 18;
    public static final char CC_KOREA = 0;
    public static final char CC_USA = 1;
    public static final char CC_SPAIN = 2;
    public static final char CC_CHINA = 3;
    public static final char CC_JAPAN = 4;
    public static final char CC_VIETNAM = 5;
    public static final char CC_ARAB = 6;
    public static final char CC_ITALY = 7;
    public static final char CC_FRANCE = 8;
    public static final char CC_GERMAN = 9;
    public static final char CC_PORTUGAL = 10;
    public static final char CC_TAIWAN = 11;
    public static final char CC_LATIN = 12;
    public static final char CC_PHILIPPINE = 13;
    public static final char CC_THAILAND = 14;
    public static final char CC_INDO = 15;
    public static final char CC_RUSSIA = 16;
    public static final char CC_CHINU = 17;
    public static final int MIDI_BUF_SIZE = 0x80000;
    public static final short RHYTHM_BUF_SIZE = 8192;
    public static final short EFFECT_BUF_SIZE = 4096;
    public static final int RHYTHM_BUF1_ADDR = 0x7b000;
    public static final int RHYTHM_BUF2_ADDR = 0x7d000;
    public static final int EFFECT_BUF_ADDR = 0x7f000;
    public static final int SONG_BUF_SIZE = 0x3d800;
    public static final int STEP_MIDI_BUF_SIZE = 65280;
    public static final int STEP_BUF_ADDR = 256;
    public static final int MIN_PLAY_SONG = 101;
    public static final int KOREA_SNO_SRT = 101;
    public static final int KOREA_SNO_END = 10000;
    public static final int CHINA_SNO_SRT = 10000;
    public static final int CHINA_SNO_END = 0x15f90;
    public static final int USA_SNO_SRT = 20000;
    public static final int USA_SNO_END = 30000;
    public static final int OTHER_SNO_SRT = 0;
    public static final int OTHER_SNO_END = 0;
    public static final int JAPAN_SNO_SRT = 40000;
    public static final int JAPAN_SNO_END = 45000;
    public static final int DDK_SNO_SRT = 50000;
    public static final int DDK_SNO_END = 60000;
    public static final int CHINU_SNO_SRT = 50000;
    public static final int CHINU_SNO_END = 60000;
    public static final int MEDLEY_SNO_SRT = 0x15f90;
    public static final int MEDLEY_SNO_END = 0x186a0;
    public static final int TMEDLEY_SNO_START = 0x17ae8;
    public static final int TMEDLEY_SNO_END = 0x17ecf;
    public static final int SMEDLEY_SNO_SRT = 9901;
    public static final int SMEDLEY_SNO_END = 10000;
    public static final int SMEDLEY_SNO_SRT_EXT = 0x182b9;
    public static final int SMEDLEY_SNO_END_EXT = 0x186a0;
    public static final char NO_PORT = 2;
    public static final char NO_TRACK = 71;
    public static final char NO_INPUT_TRACK = 62;
    public static final char MELODY_CH1 = 3;
    public static final char MELODY_CH2 = 19;
    public static final char MELODY_NONE = 65534;
    public static final char BASS_CH = 1;
    public static final char DRUM_CH1 = 9;
    public static final char DRUM_CH2 = 10;
    public static final char MAX_END_TRACK = 45;
    public static final char MAX_RHY_TYPE = 4;
    public static final char MAX_RHY_TRACK = 6;
    public static final char MAX_ORG_RHY_TRACK = 13;
    public static final char MAX_HAR_CH = 4;
    public static final char MAX_DRUM_NRPN = 10;
    public static final char MAX_DRUM_CH = 8;
    public static final char MAX_CTRL_CMD = 100;
    public static final char RHYTHM_PORT = 32;
    public static final char HARMONY_PORT = 64;
    public static final char MUTE_PORT = 128;
    public static final char WAVE_PORT = 256;
    public static final char CTRL_PORT = 272;
    public static final char STEP_PORT = 288;
    public static final char EFFECT1_TRACK = 0;
    public static final char EFFECT2_TRACK = 16;
    public static final char TRACK_HAR_MAN = 65;
    public static final char TRACK_HAR_WOMAN = 66;
    public static final char TRACK_HAR_MELODY = 67;
    public static final char TRACK_HAR_HARMONY = 68;
    public static final char TRACK_CHORUS = 258;
    public static final char TRACK_DAESA = 259;
    public static final char TRACK_GASU = 260;
    public static final char TRACK_EFFECT = 261;
    public static final char TRACK_INST = 262;
    public static final char TRACK_3D = 263;
    public static final char TRACK_CODE = 296;
    public static final char TRACK_SW = 272;
    public static final char TRACK_MW = 273;
    public static final char TRACK_GS = 274;
    public static final char TRACK_BS = 275;
    public static final char TRACK_RS1 = 276;
    public static final char TRACK_RS2 = 277;
    public static final char TRACK_FS = 278;
    public static final char TRACK_VR = 279;
    public static final char TRACK_STEP_DATA = 288;
    public static final char TRACK_STEP_CMD = 289;
    public static final char TRACK_BAD = 65535;
    public static final byte GEOL1_END = 33;
    public static final byte GEOL2_END = 35;
    public static final byte GEOL3_END = 24;
    public static final byte GEOL4_END = 26;
    public static final byte SONG_END = 45;
    public static final byte FILL_1 = 17;
    public static final byte FILL_2 = 29;
    public static final byte MAX_GENRE_TYPE = 5;
    public static final byte GENRE_GAYO = 0;
    public static final byte GENRE_GAGOK = 1;
    public static final byte GENRE_MINYO = 2;
    public static final byte GENRE_POP = 3;
    public static final byte GENRE_CHILD = 4;
    public static final byte SONG_MAN = 0;
    public static final byte SONG_WOMAN = 1;
    public static final byte LYRIC_START = 0;
    public static final byte LYRIC_ERASE1 = 1;
    public static final byte LYRIC_ERASE2 = 2;
    public static final byte LYRIC_END = 3;
    public static final byte LYRIC_TITLE_END = 4;
    public static final byte LYRIC_DUAL_ERASE = 5;
    public static final byte STEP_INSERT = 1;
    public static final byte STEP_BEAT16_START = 2;
    public static final byte STEP_BEAT24_START = 3;
    public static final byte STEP_PLAY_SINGLE = 0;
    public static final byte STEP_PLAY_SOLO = 1;
    public static final byte STEP_PLAY_COUPLE = 2;
    public static final byte STEP_PLAY_ENTRY = 3;
    public static final short TTYPE_DRUM = 1;
    public static final short TTYPE_BASS = 2;
    public static final short TTYPE_STOP = 4;
    public static final short TTYPE_IVL = 8;
    public static final byte RHY_RHY_0 = 0;
    public static final byte RHY_RHY_1 = 1;
    public static final byte RHY_RHY_2 = 2;
    public static final byte RHY_RHY_ENDING = 3;
    public static final byte RHY_ORG = 4;
    public static final byte RHY_ORG_ENDING = 5;
    public static final byte RHY_ORG_END = 6;
    public static final byte MAX_RHYTHM_CHANGE = 18;
    public static final byte RHYTHM_ORG = 0;
    public static final byte RHYTHM_DISCO1 = 1;
    public static final byte RHYTHM_DISCO2 = 2;
    public static final byte RHYTHM_DISCO3 = 3;
    public static final byte RHYTHM_DISCO4 = 4;
    public static final byte RHYTHM_GOGO1 = 5;
    public static final byte RHYTHM_GOGO2 = 6;
    public static final byte RHYTHM_TWIST = 7;
    public static final byte RHYTHM_MACARENA = 8;
    public static final byte RHYTHM_SWING = 9;
    public static final byte RHYTHM_JAZZ = 10;
    public static final byte RHYTHM_SHUFFLE = 11;
    public static final byte RHYTHM_HONKYTONK = 12;
    public static final byte RHYTHM_CHACHACHA = 13;
    public static final byte RHYTHM_SAMBA = 14;
    public static final byte RHYTHM_RUMBA = 15;
    public static final byte RHYTHM_BEGUINE = 16;
    public static final byte RHYTHM_DISCO7 = 17;
    public static final byte RHYTHM_DISCO8 = 18;
    public static final byte MAX_PATCH_TYPE = 10;
    public static final byte PATCH_REED = 0;
    public static final byte PATCH_BRASS = 1;
    public static final byte PATCH_PIANO = 2;
    public static final byte PATCH_SYNTH = 3;
    public static final byte PATCH_GUITER = 4;
    public static final byte PATCH_STRINGS = 5;
    public static final byte PATCH_SFX = 6;
    public static final byte PATCH_MELODY = 7;
    public static final byte PATCH_BASS = 8;
    public static final byte PATCH_DRUM = 9;
    public static final byte MAX_DRUM_TYPE = 6;
    public static final byte DRUM_BD = 0;
    public static final byte DRUM_SD = 1;
    public static final byte DRUM_RIM = 2;
    public static final byte DRUM_HH = 3;
    public static final byte DRUM_CB = 4;
    public static final byte DRUM_PS = 5;
    public static final byte PATCH_STANDARD = 0;
    public static final byte PATCH_ORCHESTRA = 48;
    public static final byte PATCH_KICK = 50;
    public static final byte PATCH_PS1 = 56;
    public static final byte PATCH_PS2 = 57;
    public static final byte PATCH_USER1 = 64;
    public static final byte PATCH_USER2 = 65;
}
