// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   song_t.java

package com.devscott.karaengine;

import java.io.*;

class FH
{

    public FH()
    {
        f = null;
    }

    public FH(short val)
    {
    }

    public boolean valid()
    {
        if(f != null)
            return f.exists();
        else
            return false;
    }

    void reset()
    {
    }

    void open(String filePath)
    {
        f = new File(filePath);
        try
        {
            in = new RandomAccessFile(filePath, "r");
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    long size()
    {
        return f.length();
    }

    private File f;
    public RandomAccessFile in;
}
