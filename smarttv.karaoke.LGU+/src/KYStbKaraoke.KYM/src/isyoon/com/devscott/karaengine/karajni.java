// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   karajni.java

package isyoon.com.devscott.karaengine;

import java.nio.ShortBuffer;

public class karajni
{

	public karajni()
	{
	}

	public native int init();

	public native int openFile(String s);

	public native int closeFile(int i);

	public native int readSamples(int i, ShortBuffer shortbuffer, int j);

	public native int readFrame(int i, ShortBuffer shortbuffer, int j);

	public native int Huffman_init();

	public native int Huffman_DSR(long l, byte abyte0[], long l1, long l2);

	static final long SKY_ID = 0x414e4148L;

	static
	{
		System.loadLibrary("kara-jni");
	}
}
