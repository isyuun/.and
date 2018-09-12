// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   KMenu.java

package com.devscott.karaengine;

import com.kumyoung.gtvkaraoke.ATVKaraokeActivity;

// Referenced classes of package com.devscott.karaengine:
//            KOSD, KMsg, KBook, Global, 
//            KEnvr

public class KMenu
{

    public static boolean IsWORK(int ismode)
    {
        return ismode == m_cur_menu;
    }

    public static int GetCurrent()
    {
        return m_cur_menu;
    }

    public static int OSDGetCurrent()
    {
        return m_cur_osd_menu;
    }

    public static boolean IsOSD(int ismode)
    {
        return m_cur_osd_menu == ismode;
    }

    private static void __setcurmenu(int _mode)
    {
        m_cur_menu = _mode;
    }

    private static void __setcurosdmenu(int _mode)
    {
        m_cur_osd_menu = _mode;
    }

    private static void pk_idle(int key)
    {
        int _tmp = key;
    }

    private static int pk_1(int i)
    {
        throw new Error("Unresolved compilation problems: \n\tcase expressions must be constant expressions\n\tcase expressions must be constant expressions\n\tcase expressions must be constant expressions\n\tcase expressions must be constant expressions\n\tcase expressions must be constant expressions\n\tcase expressions must be constant expressions\n\tcase expressions must be constant expressions\n\tcase expressions must be constant expressions\n\tcase expressions must be constant expressions\n\tcase expressions must be constant expressions\n");
    }

    private static void pk_2(int i)
    {
    }

    public static void menu_reset()
    {
        if(KOSD.Inst().is(2))
        {
            KOSD.Inst().osd_reset();
            KOSD.Inst().del_book(KOSD.Inst().GetBookInx());
            KBook.book_end(false, KMsg.MSG_KBD_STOP);
            KBook.disp_led_sno();
        }
        __setcurmenu(0);
    }

    public static void menu_osd_reset()
    {
        if(KOSD.Inst().is(2))
        {
            KOSD.Inst().osd_reset();
            KOSD.Inst().del_book(KOSD.Inst().GetBookInx());
            KBook.disp_led_sno();
        }
        __setcurmenu(0);
        __setcurosdmenu(0);
        if(Global.Inst().app != null)
            Global.Inst().app.callback_osd_reset();
    }

    public static int _do_menu_mode(int key_val)
    {
        int key = 0;
        key = key_val;
        if(m_cur_menu == 0)
        {
            pk_idle(key);
            if(m_cur_menu != 0)
                key = 767;
        }
        if(m_cur_menu == 0 || m_cur_menu == 36)
            key = pk_1(key);
        if(m_cur_menu == 0 && KEnvr.is(257))
        {
            pk_2(key);
            if(m_cur_menu != 0)
                key = 767;
        }
        if(m_cur_osd_menu == 1)
            key = KBook._OnKey(key);
        return key;
    }

    private static boolean set_osd_menu_mode(int mode)
    {
        if(mode == 1 && !KOSD.Inst().set_osd_mode())
        {
            menu_osd_reset();
            return false;
        } else
        {
            __setcurosdmenu(mode);
            return true;
        }
    }

    private static boolean set_menu_mode(int mode)
    {
        set_osd_menu_mode(0);
        if((mode == 1 || mode == 2 || mode == 4) && !KOSD.Inst().set_osd_mode())
        {
            menu_reset();
            return false;
        }
        if(m_cur_menu != mode)
            __setcurmenu(mode);
        return true;
    }

    boolean IsBusyOSDArea()
    {
        return m_cur_menu == 1 || m_cur_menu == 2 || m_cur_menu == 3 || m_cur_menu == 4 || m_cur_menu == 5 || m_cur_menu == 6;
    }

    boolean IsBusyWorkArea()
    {
        return m_cur_menu == 36 || m_cur_menu == 56 || m_cur_menu == 33;
    }

    public KMenu()
    {
        throw new Error("Unresolved compilation problems: \n\tcase expressions must be constant expressions\n\tcase expressions must be constant expressions\n\tcase expressions must be constant expressions\n\tcase expressions must be constant expressions\n\tcase expressions must be constant expressions\n\tcase expressions must be constant expressions\n\tcase expressions must be constant expressions\n\tcase expressions must be constant expressions\n\tcase expressions must be constant expressions\n\tcase expressions must be constant expressions\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n");
    }

    public static final int MENU_IDLE = 0;
    public static final int MENU_BOOK = 1;
    public static final int MENU_EFFECT = 2;
    public static final int MENU_HARMONY = 3;
    public static final int MENU_OSD_COIN = 4;
    public static final int MENU_OSD_MEDLEY = 5;
    public static final int MENU_OSD_START = 6;
    public static final int MENU_FANFARE = 7;
    public static final int MENU_GAME = 8;
    public static final int MENU_QUIZ = 9;
    public static final int MENU_SMEDLEY = 10;
    public static final int MENU_SETUP = 12;
    public static final int MENU_USER = 13;
    public static final int MENU_MMEDLEY = 17;
    public static final int MENU_COIN = 18;
    public static final int MENU_SELECTOR = 33;
    public static final int MENU_SEARCH = 48;
    public static final int MENU_TITLE = 36;
    public static final int MENU_CHILD = 56;
    private static int m_cur_menu = 0;
    private static int m_cur_osd_menu = 0;

}
