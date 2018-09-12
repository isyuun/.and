// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   KScore.java

package com.devscott.karaengine;

import android.util.Log;

// Referenced classes of package com.devscott.karaengine:
//            KEnvr, Global, Disp, KPlay

public class KScore
{
    class ScoreThread extends Thread
    {

        public void run()
        {
            do
            {
                do
                    try
                    {
                        Thread.sleep(1000L);
                    }
                    catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                while(!KEnvr.is(259));
                KPlay.Inst().play_send(768);
            } while(true);
        }

        final KScore this$0;

        public ScoreThread()
        {
            this$0 = KScore.this;
            super();
        }
    }


    public static KScore Inst()
    {
        return _instance;
    }

    private KScore()
    {
        score_tag_tick = 0;
    }

    public void score_task_init()
    {
        mThread = new ScoreThread();
        mThread.start();
    }

    public Boolean setScoreMode()
    {
        if(score_tag_tick < 6720)
        {
            score_tag_tick = 0;
            return Boolean.valueOf(false);
        }
        Log.d(TAG, "## into SCORE MODE");
        KEnvr.envr_set(259);
        cur_score = 99;
        Global.Inst().play_status &= 0xfffffe7f;
        Global.Inst().disp.disp_send(1038, 88L, cur_score);
        for(int i = 100; i > 0; i--)
        {
            if((Global.Inst().play_status & 0x100) == 256)
            {
                Log.d(TAG, "Detected MSG_SCORE_MSG, in PLAY_SCORE_END flag");
                break;
            }
            try
            {
                Thread.sleep(1000L);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
            Log.d(TAG, "Wait PLAY_SCORE_END");
        }

        Log.d(TAG, "check out");
        KPlay.Inst().play_send(772, 51L);
        score_tag_tick = 0;
        return Boolean.valueOf(true);
    }

    public Boolean exitScoreMode()
    {
        Log.d(TAG, "exit score end");
        KEnvr.envr_set(257);
        return Boolean.valueOf(true);
    }

    void score_count()
    {
        score_tag_tick++;
    }

    private static final String TAG = com/devscott/karaengine/KScore.getSimpleName();
    private static KScore _instance = new KScore();
    ScoreThread mThread;
    private boolean isRunning;
    private static int cur_score = 0;
    private int score_tag_tick;

}
