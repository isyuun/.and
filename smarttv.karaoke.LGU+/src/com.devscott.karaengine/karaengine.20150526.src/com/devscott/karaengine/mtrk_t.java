// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   song_t.java

package com.devscott.karaengine;

import CloneNotSupportedException;
import Cloneable;

// Referenced classes of package com.devscott.karaengine:
//            midi_pos_t, midi_tick_t

class mtrk_t
{

    public mtrk_t()
    {
        throw new Error("Unresolved compilation problems: \n\tThe type java.io.Serializable cannot be resolved. It is indirectly referenced from required .class files\n\tThe type java.io.ObjectInputStream cannot be resolved. It is indirectly referenced from required .class files\n\tThe import java.io.DataInputStream cannot be resolved\n\tThe import java.io.File cannot be resolved\n\tThe import java.io.FileInputStream cannot be resolved\n\tThe import java.io.FileNotFoundException cannot be resolved\n\tThe import java.io.IOException cannot be resolved\n\tThe import java.io.ObjectInputStream cannot be resolved\n\tThe import java.io.RandomAccessFile cannot be resolved\n\tThe import java.io.Serializable cannot be resolved\n\tThe import java.io.StreamCorruptedException cannot be resolved\n\tThe import java.io.UnsupportedEncodingException cannot be resolved\n\tThe import java.nio cannot be resolved\n\tThe import java.nio cannot be resolved\n\tThe import android.util cannot be resolved\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tFile cannot be resolved to a type\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tFile cannot be resolved to a type\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tFile cannot be resolved to a type\n\tFile cannot be resolved to a type\n\tString cannot be resolved to a type\n\tFile cannot be resolved to a type\n\tFile cannot be resolved to a type\n\tRandomAccessFile cannot be resolved to a type\n\tRandomAccessFile cannot be resolved to a type\n\tFileNotFoundException cannot be resolved to a type\n\tFile cannot be resolved to a type\n\tRandomAccessFile cannot be resolved to a type\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tString cannot be resolved to a type\n\tThe method toUnicode() from the type LY refers to the missing type String\n\tString cannot be resolved to a type\n\tString cannot be resolved to a type\n\tString cannot be resolved to a type\n\tUnsupportedEncodingException cannot be resolved to a type\n\tCloneable cannot be resolved to a type\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tObject cannot be resolved to a type\n\tCloneNotSupportedException cannot be resolved to a type\n\tObject cannot be resolved to a type\n\tCloneable cannot be resolved to a type\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tObject cannot be resolved to a type\n\tCloneNotSupportedException cannot be resolved to a type\n\tObject cannot be resolved to a type\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tCloneable cannot be resolved to a type\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tObject cannot be resolved to a type\n\tCloneNotSupportedException cannot be resolved to a type\n\tObject cannot be resolved to a type\n\tCannot invoke clone() on the array type char[]\n\tCannot invoke clone() on the array type char[]\n\tThe method clone() from the type midi_pos_t refers to the missing type Object\n\tThe method clone() from the type midi_tick_t refers to the missing type Object\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined for default constructor. Must define an explicit constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tDataInputStream cannot be resolved to a type\n\tIOException cannot be resolved to a type\n\tRandomAccessFile cannot be resolved to a type\n\tIOException cannot be resolved to a type\n\tImplicit super constructor Object() is undefined. Must explicitly invoke another constructor\n\tException cannot be resolved to a type\n");
    }

    public Object clone()
        throws CloneNotSupportedException
    {
        throw new Error("Unresolved compilation problems: \n\tObject cannot be resolved to a type\n\tCloneNotSupportedException cannot be resolved to a type\n\tObject cannot be resolved to a type\n\tCannot invoke clone() on the array type char[]\n\tCannot invoke clone() on the array type char[]\n\tThe method clone() from the type midi_pos_t refers to the missing type Object\n\tThe method clone() from the type midi_tick_t refers to the missing type Object\n");
    }

    private final short MAX_LAST_NOTE = 12;
    public short mode;
    public char speed;
    public char para;
    public char patch;
    public char rpn;
    public char vol;
    public char veloc;
    public char note;
    public char nrpn;
    public char old_rpn;
    public char old_nrpn;
    public char cmd;
    public char type;
    public midi_pos_t addr;
    public long length;
    public midi_tick_t tick;
    public char last_cmd;
    public char last_cnt;
    public char last_note[];
    public long delay_tick;
    public char delay_cnt;
    public char delay_note[];
}
