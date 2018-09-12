// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Setup.java

package com.devscott.karaengine;


// Referenced classes of package com.devscott.karaengine:
//            Global, KMidiFS

public class Setup
{

    public Setup()
    {
    }

    public static void setup_send(int msg)
    {
        setup_send(msg, 0, 0);
    }

    public static void setup_send(int msg, int para1, int para2)
    {
        Global.Inst().midifs.mfs_open(msg, false);
    }
}
