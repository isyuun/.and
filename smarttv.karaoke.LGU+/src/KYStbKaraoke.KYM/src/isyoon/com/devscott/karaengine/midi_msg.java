// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Global.java

package isyoon.com.devscott.karaengine;

class midi_msg
{

	midi_msg()
	{
	}

	static final short M_NOTE_OFF = 128;
	static final short M_NOTE_ON = 144;
	static final short M_POLY_KEY_PRESS = 160;
	static final short M_CONTROL_CHANGE = 176;
	static final short M_PROGRAM_CHANGE = 192;
	static final short M_CHANNEL_PRESS = 208;
	static final short M_PITCH_BEND_CHANGE = 224;
	static final short M_SYSEX_MSG = 240;
	static final short M_SYS_TIME_CODE = 241;
	static final short M_SYS_SONG_POS = 242;
	static final short M_SYS_SONG_SEL = 243;
	static final short M_SYS_UNDEFINED1 = 244;
	static final short M_SYS_UNDEFINED2 = 245;
	static final short M_SYS_TUNE_REQ = 246;
	static final short M_SYSEX_RMSG = 247;
	static final short M_SYS_REAL_CLOCK = 248;
	static final short M_SYS_UNDEFINED3 = 249;
	static final short M_SYS_REAL_START = 250;
	static final short M_SYS_REAL_CONTINUE = 251;
	static final short M_SYS_REAL_STOP = 252;
	static final short M_SYS_REAL_SENSE = 254;
	static final short M_SYS_REAL_RESET = 255;
	static final short M_META_EVENT = 255;
	static final short MM_SEQUENCE_NUMBER = 0;
	static final short MM_TEXT_EVENT = 1;
	static final short MM_COPYRIGHT_NOTICE = 2;
	static final short MM_TRACK_NAME = 3;
	static final short MM_INSTRUMENT_NAME = 4;
	static final short MM_LYRIC = 5;
	static final short MM_MARKER = 6;
	static final short MM_CUE_POINT = 7;
	static final short MM_CHANNEL_PREFIX = 32;
	static final short MM_PORT_SELECT = 33;
	static final short MM_END_OF_TRACK = 47;
	static final short MM_SET_TEMPO = 81;
	static final short MM_SMPTE_OFFSET = 84;
	static final short MM_TIME_SIGNATURE = 88;
	static final short MM_KEY_SIGNATURE = 89;
	static final short MM_SEQUENCER_SPEC = 127;
	static final byte MIDI_HEADER_ID[] = {
			77, 84, 104, 100
	};
	static final long MIDI_TRACK_ID[] = {
			77L, 84L, 114L, 107L
	};
	static final long NONE_MIDI = 65535L;
	static final short MAX_XOR_SIZE = 11;
	static final short CMP_XOR_SIZE = 8;
	static final int MAX_PATCH_TYPE = 10;
	static final int PATCH_REED = 0;
	static final int PATCH_BRASS = 1;
	static final int PATCH_PIANO = 2;
	static final int PATCH_SYNTH = 3;
	static final int PATCH_GUITER = 4;
	static final int PATCH_STRINGS = 5;
	static final int PATCH_SFX = 6;
	static final int PATCH_MELODY = 7;
	static final int PATCH_BASS = 8;
	static final int PATCH_DRUM = 9;
	static final int MAX_DRUM_TYPE = 6;
	static final int DRUM_BD = 0;
	static final int DRUM_SD = 1;
	static final int DRUM_RIM = 2;
	static final int DRUM_HH = 3;
	static final int DRUM_CB = 4;
	static final int DRUM_PS = 5;
	static final int PATCH_STANDARD = 0;
	static final int PATCH_ORCHESTRA = 48;
	static final int PATCH_KICK = 50;
	static final int PATCH_PS1 = 56;
	static final int PATCH_PS2 = 57;
	static final int PATCH_USER1 = 64;
	static final int PATCH_USER2 = 65;

}
