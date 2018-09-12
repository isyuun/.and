// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Global.java

package com.devscott.karaengine;


class Data
{

    Data()
    {
    }

    static final int MAX_XOR_SIZE = 11;
    static final char mode2_cmd[] = {
        '\360', 'A', '\020', 'B', '\022', '\0', '\0', '\177', '\001', '\0', 
        '\367'
    };
    static final char channel4_cmd[] = {
        '\360', 'A', '\020', 'B', '\022', '\0', '\0', '\005', '\001', 'z', 
        '\367'
    };
    static final char mode1_cmd[] = {
        '\360', 'A', '\020', 'B', '\022', '\0', '\0', '\177', '\0', '\001', 
        '\367'
    };
    static final char gs_reset_cmd[] = {
        '\360', 'A', '\020', 'B', '\022', '@', '\0', '\177', '\0', 'A', 
        '\367'
    };
    static final char port1drum2_cmd[] = {
        '\360', 'A', '\020', 'B', '\022', '@', '\032', '\025', '\002', '\017', 
        '\367'
    };
    static final char port2drum2_cmd[] = {
        '\360', 'A', '\020', 'B', '\022', 'P', '\032', '\025', '\002', '\177', 
        '\367'
    };
    static final char master_volume_cmd[] = {
        '\360', 'A', '\020', 'B', '\022', '@', '\0', '\004', '\177', '\0', 
        '\367'
    };

}
