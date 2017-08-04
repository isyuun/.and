// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   KBook.java

package com.devscott.karaengine;

import android.util.Log;
import com.kumyoung.gtvkaraoke.Database;

// Referenced classes of package com.devscott.karaengine:
//            KOSD, Global, Setup, def, 
//            KEnvr, KPlay, KMenu

public class KBook
{

    public static boolean MEMORY_BOOK(int sno, boolean newsong)
    {
        return wrapper_INSERT_MEMORY_BOOK(sno, newsong, false, false);
    }

    public static boolean INSERT_BOOK(int sno, boolean newsong)
    {
        return wrapper_INSERT_MEMORY_BOOK(sno, newsong, true, false);
    }

    private static boolean wrapper_INSERT_MEMORY_BOOK(int sno, boolean newsong, boolean is_force_first, boolean is_nouse_book_edit)
    {
        int bkey = 0xdefa000;
        int bsex = 0xdefa000;
        boolean is_book_edit_mode = !is_nouse_book_edit && KOSD.Inst().is(2);
        if(is_book_edit_mode)
        {
            sno = KOSD.Inst().read_book(KOSD.Inst().GetBookInx());
            bkey = KOSD.Inst().read_book_key(KOSD.Inst().GetBookInx());
            bsex = KOSD.Inst().read_book_sex(KOSD.Inst().GetBookInx());
        }
        if(KOSD.Inst().GetBookCnt() > 199 - (is_book_edit_mode ? 0 : 1))
        {
            if(is_book_edit_mode)
                KOSD.Inst().del_book(KOSD.Inst().GetBookInx());
            return false;
        }
        int ins_position;
        if(is_force_first)
        {
            if(is_book_edit_mode)
                KOSD.Inst().del_book(KOSD.Inst().GetBookInx());
            ins_position = 0;
            KOSD.Inst().insert_book(0, sno);
        } else
        {
            if(is_book_edit_mode)
                ins_position = KOSD.Inst().GetBookInx();
            else
                ins_position = KOSD.Inst().GetBookCnt();
            if(!is_book_edit_mode)
                KOSD.Inst().insert_book(ins_position, sno);
        }
        if(bkey != 0xdefa000)
            KOSD.Inst().set_book_key(ins_position, bkey);
        if(bsex != 0xdefa000)
            KOSD.Inst().set_book_sex(ins_position, bsex);
        if(ins_position == 0)
        {
            Setup _tmp = Global.Inst().setup;
            Setup.setup_send(0);
            disp_led_sno();
        }
        if(is_book_edit_mode)
        {
            KOSD.Inst().osd_reset();
            if(KOSD.Inst().GetBookInx() == 0)
            {
                Setup _tmp1 = Global.Inst().setup;
                Setup.setup_send(0);
            }
            disp_led_sno();
        }
        KOSD.Inst().osd_song_list();
        return true;
    }

    public static int _OnKey(int key)
    {
        int sno = 0;
        int val = 0;
        ret_key = 767;
        switch(key)
        {
        case 67: // 'C'
        case 652: 
            break;

        case 16: // '\020'
            val++;
            // fall through

        case 15: // '\017'
            val++;
            // fall through

        case 14: // '\016'
            val++;
            // fall through

        case 13: // '\r'
            val++;
            // fall through

        case 12: // '\f'
            val++;
            // fall through

        case 11: // '\013'
            val++;
            // fall through

        case 10: // '\n'
            val++;
            // fall through

        case 9: // '\t'
            val++;
            // fall through

        case 8: // '\b'
            val++;
            // fall through

        case 7: // '\007'
            if(KOSD.Inst().is(2))
            {
                sno = KOSD.Inst().read_book(KOSD.Inst().GetBookInx() % 10000) * 10 + val;
                if(def.SONG_HIGH_OVER(sno))
                    sno %= 0x186a0;
                KOSD.Inst().del_book(KOSD.Inst().GetBookInx());
                if(sno > 0)
                {
                    KOSD.Inst().insert_book(KOSD.Inst().GetBookInx(), sno);
                    KOSD.Inst().osd_send(325);
                } else
                {
                    book_end(false, key);
                }
                break;
            }
            if(KEnvr.is(257))
            {
                sno = 0 + val;
                if(def.SONG_HIGH_OVER(sno))
                    sno %= 10000;
            } else
            {
                sno = val;
            }
            if(sno > 0)
            {
                if(sno != 30)
                {
                    KOSD.Inst().SetBookInx(KOSD.Inst().GetBookCnt());
                    KOSD.Inst().insert_book(KOSD.Inst().GetBookCnt(), sno);
                    KOSD.Inst().osd_send(325);
                    KOSD.Inst().change_osd_mode(325);
                }
            } else
            {
                book_end(false, key);
            }
            break;

        case 85: // 'U'
            if(!KEnvr.is(257))
                break;
            sno = KOSD.Inst().read_book(KOSD.Inst().GetBookInx());
            if(!Database.Inst().check_song(sno))
                break;
            if(KOSD.Inst().is(2))
            {
                if(sno >= 101)
                {
                    int bkey = KOSD.Inst().read_book_key(KOSD.Inst().GetBookInx());
                    int bsex = KOSD.Inst().read_book_sex(KOSD.Inst().GetBookInx());
                    KOSD.Inst().del_book(KOSD.Inst().GetBookInx());
                    KOSD.Inst().insert_book(0, sno);
                    KOSD.Inst().set_book_key(0, bkey);
                    KOSD.Inst().set_book_sex(0, bsex);
                    book_end(true, key);
                    KPlay.Inst().play_send(85, 0L);
                } else
                {
                    KOSD.Inst().del_book(KOSD.Inst().GetBookInx());
                    book_end(false, key);
                }
            } else
            {
                book_end(false, key);
            }
            Log.i("ke", "MSG_KBD_START in KBook");
            break;

        case 23: // '\027'
        case 66: // 'B'
            sno = KOSD.Inst().read_book(KOSD.Inst().GetBookInx());
            if(Database.Inst().check_song(sno) && MEMORY_BOOK(sno, false))
                book_end(false, key);
            break;

        case 86: // 'V'
            if(KOSD.Inst().is(2))
                KOSD.Inst().del_book(KOSD.Inst().GetBookInx());
            book_end(false, key);
            break;

        default:
            ret_key = key;
            break;
        }
        return ret_key;
    }

    public static void disp_led_sno()
    {
    }

    public static void book_end(boolean mode, int type)
    {
        book_end(mode, type, false);
    }

    public static void book_end(boolean mode, int type, boolean bsuc)
    {
        KOSD.Inst().osd_reset();
        KMenu.menu_osd_reset();
        KMenu.menu_reset();
        if(!mode)
            ret_key = 326;
    }

    private static void reset_memory()
    {
        int nbook_cnt = KOSD.Inst().GetBookCnt();
        for(int i = 0; i < nbook_cnt; i++)
            KOSD.Inst().del_book(0);

    }

    public KBook()
    {
    }

    private static int ret_key = 0;

}
