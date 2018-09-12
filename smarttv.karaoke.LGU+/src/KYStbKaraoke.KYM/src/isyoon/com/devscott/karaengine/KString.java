// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   KString.java

package isyoon.com.devscott.karaengine;

// Referenced classes of package com.devscott.karaengine:
//            LY, sng_t, FH

public class KString
{

	public KString()
	{
	}

	public static void change_str_count(char ac[], int i, char c, char c1)
	{
	}

	public static int get_erase_cnt(LY s, int country)
	{
		int inx = 0;
		int n = 0;
		n = 0;
		while (s.valid(inx))
			if ((s.at(inx) & 0x80) == 128 && (country == 0 || country == 3 || country == 17 || country == 11 || country == 4))
			{
				inx += 2;
				n++;
			} else if (s.at(inx) == 32 || s.at(inx) == 9)
				inx++;
			else if (s.at(inx) >= 65 && s.at(inx) <= 90 || s.at(inx) >= 97 && s.at(inx) <= 122)
			{
				for (; s.at(inx) >= 65 && s.at(inx) <= 90 || s.at(inx) >= 97 && s.at(inx) <= 122; inx++)
					;
				n++;
			} else
			{
				inx++;
				n++;
			}
		return n;
	}

	public int get_english_cnt(LY s, int country)
	{
		int n = 0;
		return n;
	}

	public void strsplit(char ac[], char ac1[], int i, char c, char c1)
	{
	}

	public void strsplit_char(char ac[], char ac1[], int i, char c, char c1)
	{
	}

	public void strip_cr(char ac[])
	{
	}

	public static void strip_space(LY s)
	{
		int i;
		for (i = 0; s.at(i) == 32 || s.at(i) == 9; i++)
			;
		int j;
		for (j = 0; (s.at(i) & 0xff) > 0; j++)
		{
			if (s.at(i) == 13 || s.at(i) == 10)
				break;
			s.set(j, s.at(i));
			i++;
		}

		for (; j > 0; j--)
			if (s.at(j - 1) != 32 && s.at(j - 1) != 9)
				break;

		s.set(j, (byte) 0);
		s.set(j + 1, (byte) 0);
	}

	void strip_all_space(char ac[])
	{
	}

	public static void make_title(char ac[], sng_t sng_t)
	{
	}

	public static void make_singer(char ac[], sng_t sng_t)
	{
	}

	public int get_fline(FH file_handle, char buf[])
	{
		int i = 0;
		return i;
	}

	public int get_fline_long(FH file_handle, char buf[])
	{
		int i = 0;
		return i;
	}

	public long get_text_sno(char s[])
	{
		long sno = 0L;
		return sno;
	}

	public void get_text_item(char s[], char d[], short n)
	{
		int i = 0;
		d[i] = '\0';
	}

	public int get_country(char str[])
	{
		int i = 0;
		return i;
	}

	public int str_nicmp(char s1[], char s2[], int len)
	{
		return 0;
	}

	public static void str_ncat_fill(char ac[], char ac1[], int i, int j)
	{
	}

	public void str_ncpy(char ac[], char ac1[], int i, int j)
	{
	}

	public static void str_upper_fill(char ac[])
	{
	}

	static void strip_china_special(sng_t sng_t)
	{
	}

	static void strip_special_code(char ac[], int i)
	{
	}

	static boolean memcmp(byte a[], byte b[], int size)
	{
		return true;
	}

	static boolean memcmp(char a[], char b[], int size)
	{
		return true;
	}

	static String singer_msg[] = {
			"\u2265\316\u2211\260", "singer", "singer", "\u2265\u2122 :", "\261\245", "singer", "singer", "singer", "singer", "singer",
			"singer", "singer", "singer", "singer", "singer", "singer", "singer", "\u221E\u20AC :"
	};

}
