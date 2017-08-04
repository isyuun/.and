// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   KPlay.java

package com.devscott.karaengine;

import android.util.Log;
import com.kumyoung.gtvkaraoke.*;
import com.kumyoung.stbui.MyVideoView;
import com.kumyoung.stbui.ViewManager;
import java.util.ArrayList;

// Referenced classes of package com.devscott.karaengine:
//            Global, Player, KOSD, song_t, 
//            KMidiFS, KMenu, KScore, KOS, 
//            KEnvr, KTask

public class KPlay
{
    class PlayThread extends Thread
    {

        public void run()
        {
            int key = 767;
            int temp_sno = 0;
            SetPlaySongNumber(0);
            SetCurrentSongNumber(0);
            reset_song(KEnvr.envr_get());
            do
            {
                KTask.task_status &= 0xfffffbff;
                if(KEnvr.is(260))
                    try
                    {
                        Thread.sleep(10L);
                    }
                    catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                play_t msg = pend();
                KTask.task_status |= 0x400;
                if(msg == null)
                    try
                    {
                        Thread.sleep(10L);
                    }
                    catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                else
                if(!KEnvr.is(256) && !KEnvr.is(260))
label0:
                    switch(KEnvr.envr_get())
                    {
                    case 257: 
                        switch(msg.msg)
                        {
                        case 85: // 'U'
                            temp_sno = KOSD.Inst().read_book(0);
                            if(!KMenu.IsOSD(0) || !KMenu.IsWORK(0))
                            {
                                KOSD.Inst().osd_send(msg.msg);
                                break label0;
                            }
                            if(temp_sno >= 101)
                            {
                                KEnvr.envr_set(258);
                                play_send(769);
                            }
                            break label0;
                        }
                        if(msg.msg == 86)
                            reset_song(KEnvr.envr_get());
                        if(msg.msg != 767)
                            KOSD.Inst().osd_send(msg.msg);
                        break;

                    case 258: 
                        task_envr_song(msg.msg);
                        break;

                    case 259: 
                        switch(msg.msg)
                        {
                        default:
                            break;

                        case 772: 
                            Log.d(KPlay.TAG, "KMsg.MSG_SCORE_START");
                            for(int i = 100; i > 0; i--)
                            {
                                Log.d(KPlay.TAG, "wait score dley...");
                                if((Global.Inst().play_status & 0x100) == 256)
                                    break;
                                try
                                {
                                    Thread.sleep(1000L);
                                }
                                catch(InterruptedException e)
                                {
                                    e.printStackTrace();
                                }
                            }

                            KPlay.Inst().play_send(773, 0L);
                            break label0;

                        case 86: // 'V'
                        case 773: 
                            Log.d(KPlay.TAG, "MSG_KBD_STOP or MSG_SCORE_END");
                            while((Global.Inst().play_status & 0x100) != 256) 
                            {
                                Log.d(KPlay.TAG, "wait score dley...");
                                try
                                {
                                    Thread.sleep(1000L);
                                }
                                catch(InterruptedException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                            reset_song(257);
                            KScore.Inst().exitScoreMode();
                            if(KOSD.Inst().GetBookCnt() > 0 && DataHandler.isReachable)
                            {
                                KPlay.Inst().play_send(85, 0L);
                                break label0;
                            }
                            ViewManager.Inst().lpScoreView = null;
                            if(Global.Inst().app != null)
                                Global.Inst().app.doMenu(352);
                            break;
                        }
                        break;

                    default:
                        Log.i("ke", (new StringBuilder("kplay default key = ")).append(key).toString());
                        break;
                    }
            } while(true);
        }

        final KPlay this$0;

        public PlayThread()
        {
            this$0 = KPlay.this;
            super();
        }
    }

    private class play_t
    {

        int msg;
        long no;
        final KPlay this$0;

        private play_t()
        {
            this$0 = KPlay.this;
            super();
        }

        play_t(play_t play_t1)
        {
            this();
        }
    }


    public static KPlay Inst()
    {
        return _instance;
    }

    private KPlay()
    {
        score_count = 0;
        play_q_box = null;
        play_q_inx = 0;
        play_q_read_inx = 0;
        play_q = new int[100];
        play_q_msg = new play_t[100];
        for(int i = 0; i < 100; i++)
            play_q_msg[i] = new play_t(null);

    }

    public void play_task_init()
    {
        mThread = new PlayThread();
        mThread.start();
    }

    play_t pend()
    {
        if(play_q_inx == play_q_read_inx)
            return null;
        if(++play_q_read_inx >= 100)
            play_q_read_inx = 0;
        return play_q_msg[play_q_read_inx];
    }

    public void play_send(int msg)
    {
        play_send(msg, 0L);
    }

    public void play_send(int msg, long no)
    {
        int inx = play_q_inx;
        if(++play_q_inx >= 100)
            play_q_inx = 0;
        play_q_msg[play_q_inx].msg = msg;
        play_q_msg[play_q_inx].no = no;
    }

    private void reset_song(int envr)
    {
        Global.Inst().player.reset_song(envr);
    }

    private void task_envr_song(int msg)
    {
        switch(msg)
        {
        case 85: // 'U'
            break;

        case 90: // 'Z'
            if(KOSD.Inst().GetBookRealCnt() > 0 && DataHandler.isReachable)
            {
                Global.Inst().player.AllStop();
                Global.Inst().fp.stop_token = false;
                reset_song(257);
                play_send(85, 0L);
            }
            break;

        case 769: 
            Log.i("ke", "MSG_SONG_START ------------");
            Log.i("ke", (new StringBuilder("cur_song_no : ")).append(GetCurrentSongNumber()).toString());
            KOSD.Inst().set_book_cont(GetCurrentSongNumber());
            boolean i = true;
            if(KOSD.Inst().GetBookCnt() == 0)
            {
                Log.w("ke", "not booking");
                play_send(770, 0L);
                break;
            }
            int sno = KOSD.Inst().read_book(0);
            if(DataHandler.getInstance().IsServiceDate().booleanValue())
                Log.d(TAG, "free use.");
            else
            if(DataHandler.GetSongInfoFromKY(sno) == 1 || Global.isTestPayment.booleanValue())
            {
                Log.d("ke", "check point BuyTicket ------------");
                Global.Inst().app.doMenu(355);
                play_send(770, 0L);
                break;
            }
            Log.i("ke", "Download Midi ------------");
            ContentsDownloader.DownloaderMIDI(sno);
            Log.d("ke", "mfs_header in....");
            Global inst = Global.Inst();
            KMidiFS _tmp = inst.midifs;
            if(!KMidiFS.mfs_header(sno, inst.fp.song))
            {
                Log.e("ke", (new StringBuilder("MIDI file open sno: ")).append(sno).toString());
                if(i)
                    KOSD.Inst().get_book();
                play_send(770, 0L);
                break;
            }
            Log.d("ke", "mfs_header out...");
            inst.midi_key = KOSD.Inst().read_book_key(0);
            inst.midi_sex = KOSD.Inst().read_book_sex(0);
            SetPlaySongNumber(sno);
            KOSD.Inst().get_book();
            setup_song();
            inst.player.prepare_title();
            inst.cur_fh = inst.midifs.mfs_open(GetPlaySongNumber(), true);
            if(inst.cur_fh == 0)
            {
                Log.e("ke", (new StringBuilder("MIDI file open ")).append(GetPlaySongNumber()).toString());
                play_send(770, 0L);
            } else
            {
                inst.player.infor_song();
                inst.player.start_song(msg);
                SetCurrentSongNumber(GetPlaySongNumber());
                Global.Inst().player.AllPlay();
                Database.Inst().snoLately.add(0, Integer.valueOf(GetPlaySongNumber()));
                Global.Inst().app.mVideoView.changeVideo();
            }
            break;

        case 86: // 'V'
            if(KMenu.IsOSD(1))
            {
                KOSD.Inst().osd_send(msg);
                break;
            }
            if(Global.Inst().fp.stop_token)
                Global.Inst().fp.stop_token = false;
            play_send(770, 0L);
            break;

        case 770: 
            SetCurrentSongNumber(0);
            // fall through

        case 771: 
            Global.Inst().player.AllStop();
            Log.i("ke", "----------------END SONG-----------------------");
            Global.Inst().fp.stop_token = false;
            if(!KMenu.IsWORK(0))
                Global.Inst().in_play = false;
            else
                song_end_proc(msg);
            break;

        default:
            if(msg != 767 && msg != 767)
                KOSD.Inst().osd_send(msg);
            break;
        }
    }

    private void song_end_proc(int msg)
    {
        KMenu.IsWORK(36);
        if(msg == 770)
        {
            if(Global.Inst().app != null)
                Global.Inst().app.doMenu(352);
            reset_song(257);
        } else
        if(!KScore.Inst().setScoreMode().booleanValue())
        {
            if(Global.Inst().app != null)
                Global.Inst().app.doMenu(352);
            reset_song(257);
        }
    }

    public int GetCurrentSongNumber()
    {
        return m_cur_song_no;
    }

    public void SetCurrentSongNumber(int sno)
    {
        m_cur_song_no = sno;
    }

    public int GetPlaySongNumber()
    {
        return m_play_song;
    }

    public void SetPlaySongNumber(int sno)
    {
        m_play_song = sno;
    }

    public void setup_song()
    {
        Log.i("ke", "setup_song()");
    }

    private int score_count;
    private final int PLAY_Q_SIZE = 100;
    private KOS.OS_EVENT play_q_box;
    private int play_q_inx;
    private int play_q_read_inx;
    private int play_q[];
    private play_t play_q_msg[];
    private int m_play_song;
    private int m_cur_song_no;
    private static final String TAG = com/devscott/karaengine/KPlay.getSimpleName();
    PlayThread mThread;
    private static KPlay _instance = new KPlay();




}
