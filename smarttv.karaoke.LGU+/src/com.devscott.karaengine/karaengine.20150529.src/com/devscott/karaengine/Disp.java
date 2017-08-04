// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Disp.java

package com.devscott.karaengine;

import android.util.Log;
import com.kumyoung.gtvkaraoke.ATVKaraokeActivity;
import com.kumyoung.gtvkaraoke.Database;
import com.kumyoung.stbui.*;
import java.io.*;

// Referenced classes of package com.devscott.karaengine:
//            KOS, KEnvr, Global, KPlay, 
//            Player

public class Disp
{
    private class QueueThread extends Thread
    {

        private void TASK_SLEEP()
            throws InterruptedException
        {
            Thread.sleep(10L);
        }

        String toUnicode(byte s[])
        {
            int len = 0;
            for(int i = 0; i < 255 && s[i] != 0; i++)
                len++;

            byte tmp[] = new byte[len];
            for(int i = 0; i < len; i++)
                tmp[i] = s[i];

            String uni = null;
            try
            {
                uni = new String(tmp, "KSC5601");
            }
            catch(UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
            return uni;
        }

        public void run()
        {
            int x = 0;
            bThreadLive = true;
            while(bThreadLive) 
            {
                disp_t msg;
                do
                {
                    try
                    {
                        Thread.sleep(50L);
                    }
                    catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    msg = pend();
                } while(msg == null);
label0:
                switch(KEnvr.envr_get())
                {
                default:
                    break;

                case 257: 
                    int _tmp = msg.msg;
                    Log.d("ke", "Disp is ENVR_IDLE");
                    break;

                case 258: 
                    switch(msg.msg)
                    {
                    case 1034: 
                    case 1035: 
                    case 1038: 
                    case 1039: 
                    case 1040: 
                    default:
                        break label0;

                    case 1026: 
                        Global.Inst().play_status |= 1;
                        if(Global.Inst().app == null)
                        {
                            Log.e("ke", "can't dump title, callback is null");
                        } else
                        {
                            ViewManager.Inst();
                            ViewManager._lpTitleView = null;
                            Global.Inst().app.doMenu(353);
                            do
                            {
                                ViewManager.Inst();
                                if(ViewManager._lpTitleView == null)
                                    try
                                    {
                                        Thread.sleep(1000L);
                                    }
                                    catch(InterruptedException e)
                                    {
                                        e.printStackTrace();
                                    }
                                else
                                    break;
                            } while(true);
                        }
                        String columns[] = {
                            "sno", "title", "artist", "singer", "writer"
                        };
                        Boolean ret = Boolean.valueOf(Database.Inst().query_song_info(KPlay.Inst().GetPlaySongNumber(), columns));
                        if(!ret.booleanValue())
                        {
                            Log.e("ke", "not found song infor in db");
                            columns[1] = (new StringBuilder("not found ")).append(KPlay.Inst().GetPlaySongNumber()).toString();
                        } else
                        {
                            Log.d("ke", (new StringBuilder("found songinfor in db :")).append(columns[1]).toString());
                        }
                        for(int n = 0; n < 10; n++)
                            if(ViewManager.Inst() == null)
                            {
                                try
                                {
                                    Thread.sleep(1000L);
                                }
                                catch(InterruptedException e)
                                {
                                    e.printStackTrace();
                                }
                            } else
                            {
                                try
                                {
                                    Thread.sleep(1000L);
                                }
                                catch(InterruptedException e)
                                {
                                    e.printStackTrace();
                                }
                                ViewManager.Inst();
                                if(ViewManager._lpTitleView == null)
                                    break label0;
                                String strAuthor = null;
                                String strWriter = null;
                                String strSinger = null;
                                String strTitle = null;
                                String strDescription = null;
                                InputStream input = new ByteArrayInputStream(Global.Inst().rawSOK);
                                try
                                {
                                    BufferedReader in = new BufferedReader(new InputStreamReader(input, "euc-kr"));
                                    int nLine = 0;
                                    String str;
                                    while((str = in.readLine()) != null) 
                                        switch(nLine++)
                                        {
                                        case 1: // '\001'
                                            strTitle = str;
                                            break;

                                        case 2: // '\002'
                                            strDescription = str;
                                            break;

                                        case 3: // '\003'
                                            strAuthor = str;
                                            break;

                                        case 4: // '\004'
                                            strWriter = str;
                                            break;

                                        case 5: // '\005'
                                            strSinger = str;
                                            break;
                                        }
                                }
                                catch(UnsupportedEncodingException e)
                                {
                                    e.printStackTrace();
                                }
                                catch(NumberFormatException e)
                                {
                                    e.printStackTrace();
                                }
                                catch(IOException e)
                                {
                                    e.printStackTrace();
                                }
                                ViewManager.Inst();
                                ViewManager._lpTitleView.setTitle(strTitle, strDescription, strAuthor, strWriter, strSinger);
                                break label0;
                            }

                        break label0;

                    case 1027: 
                        Global.Inst().play_status |= 2;
                        ViewManager.Inst();
                        if(ViewManager._lpTitleView != null)
                        {
                            ViewManager.Inst();
                            ViewManager._lpTitleView.dump();
                            Log.i("ke", "DISP : MSG_TITLE_DUMP ");
                        } else
                        {
                            Log.e("ke", "lpTitleView is null");
                        }
                        Global.Inst().play_status |= 4;
                        Global.Inst().play_status &= -4;
                        isdumped = true;
                        break label0;

                    case 1028: 
                        Log.i("ke", "DISP : MSG_PLAY_OSD ");
                        break label0;

                    case 1029: 
                        Log.i("ke", "DISP : MSG_PLAY_INIT ");
                        Global.Inst().play_status &= 0xffffffea;
                        if((Global.Inst().play_status & 8) != 8)
                            Global.Inst().play_status |= 8;
                        if(isdumped)
                        {
                            ViewManager.Inst();
                            ViewManager._lpTitleView.delayClear();
                            isdumped = false;
                        }
                        break label0;

                    case 1030: 
                        Log.i("ke", "DISP : MSG_MAKE_LYRIC ");
                        Global.Inst().player.next_lyric_make();
                        break label0;

                    case 1031: 
                        Log.i("ke", "DISP : MSG_DUMP_LYRIC ");
                        Global.Inst().play_status &= 0xffffffb7;
                        Global.Inst().player.next_lyric_dump();
                        break label0;

                    case 1032: 
                        Log.i("ke", "DISP : MSG_ERASE_LYRIC ");
                        Global.Inst().player.next_lyric_erase();
                        break label0;

                    case 1036: 
                        Log.i("ke", "DISP : MSG_MTITLE_MAKE ");
                        Global.Inst().play_status |= 0x20;
                        break label0;

                    case 1033: 
                        Global.Inst().play_status |= 0x10;
                        Global.Inst().play_status &= 0xffffffd7;
                        break label0;

                    case 1037: 
                        x = 0;
                        switch((int)msg.no)
                        {
                        case 4: // '\004'
                            x++;
                            // fall through

                        case 3: // '\003'
                            x++;
                            // fall through

                        case 2: // '\002'
                            x++;
                            // fall through

                        case 1: // '\001'
                            x++;
                            // fall through

                        case 0: // '\0'
                            Log.d("ke", (new StringBuilder("ready ")).append(msg.no).toString());
                            Global.Inst().play_status |= 0x40;
                            ViewManager.Inst();
                            ViewManager._lpTitleView.setReady(x);
                            break;

                        case 65535: 
                            Log.d("ke", "ready okay");
                            Global.Inst().play_status &= 0xffffffbf;
                            ViewManager.Inst();
                            ViewManager._lpTitleView.setReady(999);
                            break;
                        }
                        break label0;
                    }

                case 259: 
                    switch(msg.msg)
                    {
                    default:
                        break;

                    case 1038: 
                        if(Global.Inst().app != null)
                        {
                            Global.Inst().app.doMenu(354);
                            for(; ViewManager.Inst().lpScoreView == null; Log.d("ke", "lpScoreView is null"))
                                try
                                {
                                    Thread.sleep(100L);
                                }
                                catch(InterruptedException e)
                                {
                                    e.printStackTrace();
                                }

                            ViewManager.Inst().lpScoreView.setScore("50", "tests");
                            Log.d("ke", "########## dump socre ");
                            Log.d("ke", "########## dump socre ");
                            ViewManager.Inst().lpScoreView.dump_score();
                            for(int i = 0; i < 3; i++)
                            {
                                try
                                {
                                    Thread.sleep(1000L);
                                }
                                catch(InterruptedException e)
                                {
                                    e.printStackTrace();
                                }
                                Log.d("ke", "wait score animation... 6Sec");
                            }

                            ViewManager.Inst().lpScoreView.score_numdisp(1);
                            for(int i = 0; i < 2; i++)
                            {
                                try
                                {
                                    Thread.sleep(1000L);
                                }
                                catch(InterruptedException e)
                                {
                                    e.printStackTrace();
                                }
                                Log.d("ke", "disp  Wait Stable Score 4Sec");
                            }

                        }
                        Global.Inst().app.doMenu(8787);
                        Global.Inst().play_status |= 0x100;
                        Log.d("ke", "disp MSG_SCORE_MSG");
                        break;
                    }
                    break;
                }
            }
        }

        boolean bThreadLive;
        final Disp this$0;

        public QueueThread()
        {
            this$0 = Disp.this;
            super();
            bThreadLive = true;
        }
    }

    private class disp_t
    {

        int msg;
        long wait;
        long no;
        final Disp this$0;

        private disp_t()
        {
            this$0 = Disp.this;
            super();
        }

        disp_t(disp_t disp_t1)
        {
            this();
        }
    }


    public Disp()
    {
        isdumped = false;
        disp_q_box = null;
        disp_q_inx = 0;
        disp_q_read_inx = 0;
        qthread = null;
        disp_q = new int[50];
        disp_q_msg = new disp_t[50];
        for(int i = 0; i < 50; i++)
            disp_q_msg[i] = new disp_t(null);

    }

    public void disp_task_init()
    {
        disp_q_inx = 0;
        disp_q_read_inx = 0;
        qthread = new QueueThread();
        qthread.start();
    }

    public void end_task()
    {
    }

    private disp_t pend()
    {
        if(disp_q_inx == disp_q_read_inx)
            return null;
        if(++disp_q_read_inx >= 50)
            disp_q_read_inx = 0;
        return disp_q_msg[disp_q_read_inx];
    }

    public void disp_send(int msg, long wait_time, long no)
    {
        int inx = disp_q_inx;
        if(++disp_q_inx >= 50)
            disp_q_inx = 0;
        disp_q_msg[disp_q_inx].msg = msg;
        disp_q_msg[disp_q_inx].wait = wait_time;
        disp_q_msg[disp_q_inx].no = no;
    }

    private void make_title()
    {
    }

    public boolean isdumped;
    private final int DISP_Q_SIZE = 50;
    private KOS.OS_EVENT disp_q_box;
    private int disp_q_inx;
    private int disp_q_read_inx;
    private int disp_q[];
    private disp_t disp_q_msg[];
    QueueThread qthread;

}
