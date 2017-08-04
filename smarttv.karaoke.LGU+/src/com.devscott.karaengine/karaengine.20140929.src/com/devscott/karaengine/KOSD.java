// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   KOSD.java

package com.devscott.karaengine;

import android.util.Log;
import com.kumyoung.gtvkaraoke.ATVKaraokeActivity;
import com.kumyoung.gtvkaraoke.Database;
import com.kumyoung.stbui.OSDView;
import com.kumyoung.stbui.ViewManager;

// Referenced classes of package com.devscott.karaengine:
//            Global, key_t, def, KMidiFS, 
//            KOS, KMenu, KTask, KEnvr

public class KOSD
{
    class OSDThread extends Thread
    {

        public void run()
        {
            int key = 767;
            osd_reset();
            KMenu.menu_reset();
            book_reset();
            do
            {
                KTask.task_status &= -9;
                if(KEnvr.is(260))
                    try
                    {
                        Thread.sleep(10L);
                    }
                    catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                osd_t msg = pend();
                KTask.task_status |= 8;
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
                if(!KEnvr.is(256) && !KEnvr.is(259) && !KEnvr.is(260) && !KEnvr.is(263))
                {
                    key = KMenu._do_menu_mode(msg.msg);
                    if(key != 767)
                    {
                        switch(KEnvr.envr_get())
                        {
                        case 257: 
                            int _tmp = key;
                            Log.i(KOSD.TAG, (new StringBuilder("osd default key (IDLE) = ")).append(key).toString());
                            break;

                        case 258: 
                            Log.i("TAG", (new StringBuilder("osd default key (ENVR) = ")).append(key).toString());
                            break;

                        case 259: 
                            Log.i("TAG", (new StringBuilder("osd default key (SCORE) = ")).append(key).toString());
                            break;

                        default:
                            key = 767;
                            break;
                        }
                        switch(key)
                        {
                        case 320: 
                        default:
                            break;

                        case 325: 
                            if(set_osd_mode())
                                osd_song_edit();
                            break;

                        case 326: 
                        case 331: 
                            if(GetBookCnt() > 0)
                                osd_disp(key);
                            break;

                        case 67: // 'C'
                            Log.e(KOSD.TAG, "MSG_KBD_CANCEL..... del song!");
                            if(GetBookCnt() > 0)
                            {
                                del_book(GetBookCnt() - 1);
                                osd_disp(326);
                            }
                            break;
                        }
                    }
                }
            } while(true);
        }

        final KOSD this$0;

        public OSDThread()
        {
            this$0 = KOSD.this;
            super();
        }
    }

    private class book_t
    {

        public void set(book_t a)
        {
            sno = a.sno;
            sex = a.sex;
            key = a.key;
        }

        int sno;
        int sex;
        int key;
        final KOSD this$0;

        private book_t()
        {
            this$0 = KOSD.this;
            super();
        }

        book_t(book_t book_t1)
        {
            this();
        }
    }

    private class osd_t
    {

        int msg;
        long wait;
        long no;
        final KOSD this$0;

        private osd_t()
        {
            this$0 = KOSD.this;
            super();
        }

        osd_t(osd_t osd_t1)
        {
            this();
        }
    }


    private void BOOKQ_LOCK()
    {
    }

    private void BOOKQ_UNLOCK()
    {
    }

    public static KOSD Inst()
    {
        return _instance;
    }

    private KOSD()
    {
        ov = null;
        osd_q_box = null;
        osd_q_inx = 0;
        osd_q_read_inx = 0;
        m_random_song = 0;
        m_repeat = 0;
        m_midi_melody = 0;
        m_lyrics_mode = 0;
        m_book_inx = 0;
        m__book_cnt = 0;
        sno = 0;
        m_cur_osd = 0;
        book_q = new book_t[201];
        for(int i = 0; i < 201; i++)
            book_q[i] = new book_t(null);

        osd_q = new int[50];
        osd_q_msg = new osd_t[50];
        for(int i = 0; i < 50; i++)
            osd_q_msg[i] = new osd_t(null);

    }

    public void osd_task_init()
    {
        ov = ViewManager.Inst().lpOsdView;
        mThread = new OSDThread();
        mThread.start();
    }

    osd_t pend()
    {
        if(osd_q_inx == osd_q_read_inx)
            return null;
        if(++osd_q_read_inx >= 50)
            osd_q_read_inx = 0;
        return osd_q_msg[osd_q_read_inx];
    }

    public void osd_send(int msg)
    {
        osd_send(msg, 0L, 0L);
    }

    public void osd_send(int msg, long wait_time, long no)
    {
        int inx = osd_q_inx;
        if(++osd_q_inx >= 50)
            osd_q_inx = 0;
        osd_q_msg[osd_q_inx].msg = msg;
        osd_q_msg[osd_q_inx].wait = wait_time;
        osd_q_msg[osd_q_inx].no = no;
    }

    public int GetBookInx()
    {
        return m_book_inx;
    }

    public void SetBookInx(int inx)
    {
        m_book_inx = inx;
    }

    public int GetBookCnt()
    {
        return m__book_cnt;
    }

    public int GetBookRealCnt()
    {
        if(Inst().is(2))
            return m__book_cnt - 1;
        else
            return m__book_cnt;
    }

    public int get_book()
    {
        int no = 0;
        BOOKQ_LOCK();
        if(m__book_cnt > 0)
        {
            no = book_q[0].sno;
            for(int i = 0; i < m__book_cnt - 1; i++)
                book_q[i].set(book_q[i + 1]);

            m__book_cnt--;
        }
        BOOKQ_UNLOCK();
        return no;
    }

    public int read_book(int index)
    {
        int ret = 0;
        BOOKQ_LOCK();
        if(m__book_cnt > 0 && index < 201)
            ret = book_q[index].sno;
        BOOKQ_UNLOCK();
        return ret;
    }

    public void set_book_key(int index, int key)
    {
        BOOKQ_LOCK();
        if(m__book_cnt > 0 && index < 201)
            book_q[index].key = key;
        BOOKQ_UNLOCK();
    }

    public int read_book_key(int index)
    {
        int ret = 0;
        BOOKQ_LOCK();
        if(m__book_cnt > 0 && index < 201)
            ret = book_q[index].key;
        BOOKQ_UNLOCK();
        return ret;
    }

    public void set_book_sex(int index, int sex)
    {
        BOOKQ_LOCK();
        if(m__book_cnt > 0 && index < 201)
            book_q[index].sex = sex;
        BOOKQ_UNLOCK();
    }

    public void osd_reset()
    {
        change_osd_mode(320);
    }

    private void book_reset()
    {
        m__book_cnt = m_book_inx = 0;
    }

    private void book_verify()
    {
    }

    public void del_book(int index)
    {
        if(index >= 201)
            return;
        BOOKQ_LOCK();
        if(m__book_cnt > 0)
        {
            for(int i = index; i < m__book_cnt; i++)
                book_q[i].set(book_q[i + 1]);

            m__book_cnt--;
        }
        BOOKQ_UNLOCK();
    }

    public void insert_book(int index, int song_no)
    {
        if(index >= 201)
            return;
        BOOKQ_LOCK();
        if(index > m__book_cnt)
            index = m__book_cnt;
        for(int i = m__book_cnt; i > index; i--)
            book_q[i].set(book_q[i - 1]);

        book_q[index].sno = song_no;
        book_q[index].sex = 32767;
        book_q[index].key = 0;
        m__book_cnt++;
        if(m__book_cnt > 200)
            m__book_cnt = 200;
        BOOKQ_UNLOCK();
    }

    public boolean is(int ismode)
    {
        return m_cur_osd == ismode;
    }

    public boolean not(int ismode)
    {
        return m_cur_osd != ismode;
    }

    public int GetCurrent()
    {
        return m_cur_osd;
    }

    public boolean IsOSDMode()
    {
        return m_cur_osd != 0;
    }

    public boolean IsIDLE()
    {
        return m_cur_osd == 0;
    }

    public void change_osd_mode(int mode)
    {
        Log.e(TAG, (new StringBuilder("change_osd_mode : ")).append(mode).toString());
        switch(mode)
        {
        case 320: 
            if(!IsIDLE())
            {
                if(ViewManager.Inst().lpOsdView == null);
                ViewManager.Inst().lpOsdView.setInfo(0, "", "");
                if(Global.Inst().app != null)
                    Global.Inst().app.setBookMode(Boolean.valueOf(false));
            }
            m_cur_osd = 0;
            break;

        case 324: 
            m_cur_osd = 1;
            if(Global.Inst().app != null)
                Global.Inst().app.setBookMode(Boolean.valueOf(false));
            break;

        case 325: 
            m_cur_osd = 2;
            if(Global.Inst().app != null)
                Global.Inst().app.setBookMode(Boolean.valueOf(true));
            break;

        case 326: 
            m_cur_osd = 3;
            break;

        case 331: 
            m_cur_osd = 4;
            break;

        case 332: 
            m_cur_osd = 7;
            break;

        case 39321: 
        default:
            m_cur_osd = 5;
            if(Global.Inst().app != null)
                Global.Inst().app.setBookMode(Boolean.valueOf(false));
            break;
        }
    }

    public boolean set_osd_mode()
    {
        return true;
    }

    public void osd_song_edit()
    {
        int no = 0;
        int book_inx = m_book_inx;
        Log.i(TAG, "osd_song_edit---------------------");
        no = read_book(book_inx);
        key_t key = new key_t();
        String columns[] = {
            "sno", "-----------------------------", "-----"
        };
        if(no >= 101)
        {
            key.sex = (short)read_book_sex(book_inx);
            if(key.sex == 32767)
                key.sex = key.sex;
            short _tmp = key.sex;
            int temp_key;
            if(key.sex == 0)
                temp_key = key.man_key + read_book_key(book_inx);
            else
                temp_key = key.woman_key + read_book_key(book_inx);
            for(; temp_key < 0; temp_key += 12);
            char q = def.get_octave(temp_key % 12);
            if(!Database.Inst().query_song_info(no, columns))
            {
                columns[1] = "";
                columns[2] = "";
            }
        }
        if(ov != null)
            ov.setInfo(no, columns[1], columns[2]);
    }

    public void osd_next_song()
    {
        String strList = "";
        if(GetBookCnt() > 0)
        {
            for(int n = 0; n < Math.min(GetBookCnt(), 7); n++)
                strList = (new StringBuilder(String.valueOf(strList))).append(String.format("%05d  ", new Object[] {
                    Integer.valueOf(read_book(n))
                })).toString();

        }
        Log.d(TAG, (new StringBuilder(" # next song :")).append(strList).toString());
    }

    public void osd_song_list()
    {
        String strList = "";
        if(GetBookCnt() > 0)
        {
            for(int n = 0; n < Math.min(GetBookCnt(), 7); n++)
                strList = (new StringBuilder(String.valueOf(strList))).append(String.format("%05d  ", new Object[] {
                    Integer.valueOf(read_book(n))
                })).toString();

        }
        Log.d(TAG, (new StringBuilder(" # song list :")).append(strList).toString());
        if(ov != null)
            ov.UpdateSongList();
    }

    public void osd_disp(int msg)
    {
        if(!set_osd_mode())
        {
            Log.i(TAG, "osd_disp in( set_osd_mode is false or  menu is ");
            return;
        }
        switch(msg)
        {
        case 320: 
            if(GetBookCnt() == 0)
            {
                osd_reset();
            } else
            {
                book_verify();
                osd_song_list();
                change_osd_mode(326);
            }
            break;

        case 326: 
            osd_song_list();
            break;

        case 331: 
            osd_next_song();
            break;

        default:
            Log.e("ke", (new StringBuilder("out of message")).append(msg).toString());
            break;
        }
        change_osd_mode(msg);
    }

    public void set_book_cont(int song_no)
    {
        if(GetBookCnt() == 0 && (m_repeat == 1 || m_repeat == 2 || m_repeat == 3))
        {
            switch(m_repeat)
            {
            case 2: // '\002'
                song_no = Global.Inst().midifs.mfs_next(song_no);
                break;

            case 1: // '\001'
                song_no = song_no;
                break;

            case 3: // '\003'
                song_no = m_random_song;
                if(!KMidiFS.mfs_exist(song_no))
                {
                    m_random_song = Global.Inst().midifs.mfs_random();
                    song_no = m_random_song;
                }
                m_random_song = KMidiFS.mfs_random();
                break;

            default:
                return;
            }
            if(song_no > 0)
            {
                BOOKQ_LOCK();
                m_book_inx = 0;
                BOOKQ_UNLOCK();
                insert_book(0, song_no);
            }
        }
    }

    int read_book_sex(int index)
    {
        int ret = 0;
        BOOKQ_LOCK();
        if(m__book_cnt > 0 && index < 201)
            ret = book_q[index].sex;
        BOOKQ_UNLOCK();
        return ret;
    }

    private OSDView ov;
    private final int MAX_BOOK_BUF = 201;
    public static final int eOSD_IDLE = 0;
    public static final int eOSD_START = 1;
    public static final int eOSD_BOOK_EDIT = 2;
    public static final int eOSD_BOOK_LIST = 3;
    public static final int eOSD_NEXT_SONG = 4;
    public static final int eOSD_UNDEFINE = 5;
    public static final int eOSD_ANIM = 6;
    public static final int eOSD_MEDLEY_LOGO = 7;
    public static final int eOSD_MNEXT = 8;
    private final int OSD_Q_SIZE = 50;
    private KOS.OS_EVENT osd_q_box;
    private int osd_q_inx;
    private int osd_q_read_inx;
    private int osd_q[];
    private osd_t osd_q_msg[];
    private int m_random_song;
    private int m_repeat;
    private int m_midi_melody;
    private int m_lyrics_mode;
    private int m_book_inx;
    private int m__book_cnt;
    private book_t book_q[];
    private static final String TAG = com/devscott/karaengine/KOSD.getSimpleName();
    private int sno;
    private int m_cur_osd;
    OSDThread mThread;
    private static KOSD _instance = new KOSD();



}
