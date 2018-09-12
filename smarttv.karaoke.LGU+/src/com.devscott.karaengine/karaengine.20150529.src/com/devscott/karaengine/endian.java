// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   song_t.java

package com.devscott.karaengine;


class endian
{

    endian()
    {
    }

    private static short changeByteOrder(short x)
    {
        return (short)(x << 8 | x >> 8 & 0xff);
    }

    private static char changeByteOrder(char x)
    {
        return (char)(x << 8 | x >> 8 & 0xff);
    }

    private static int changeByteOrder(int x)
    {
        return changeByteOrder((short)x) << 16 | changeByteOrder((short)(x >> 16)) & 0xffff;
    }

    private static long changeByteOrder(long x)
    {
        return (long)changeByteOrder((int)x) << 32 | (long)changeByteOrder((int)(x >> 32)) & 0xffffffffL;
    }

    private static long unsigned32(int n)
    {
        return (long)n & 0xffffffffL;
    }

    static long G4LTBPL(int a)
    {
        return unsigned32(changeByteOrder(a));
    }

    static int G4LTBP(int a)
    {
        return changeByteOrder(a);
    }

    static short G2LTBP(short a)
    {
        return a;
    }

    static short G2BTL(short a)
    {
        return a;
    }

    static long G4BTL(long a)
    {
        return a;
    }
}
