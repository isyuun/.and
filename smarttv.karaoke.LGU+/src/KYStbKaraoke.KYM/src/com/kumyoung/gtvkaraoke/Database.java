package com.kumyoung.gtvkaraoke;

import isyoon.com.devscott.karaengine.kdb;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

public class Database extends kdb {
	
	private static final String TAG = Database.class.getSimpleName();
	private static Database _instance;	
	static {
	    _instance = new Database();
	}

	public static int sno = 0;
	public Cursor cursor;
	
	private Database()
	{
	}
	public static Database Inst() {
		return _instance;
	}
	private SQLiteDatabase		db;
	private HashMap<Integer, String> m_HashTable = new HashMap<Integer, String>(); 
	public  ArrayList<Integer> snoLately = new ArrayList<Integer> ();
	

	/**
	 * @return
	 */
	@Override
	public boolean connect(String dbPath )
	{
		try {
				
			try {
			
				db = SQLiteDatabase.openDatabase( dbPath,  null, SQLiteDatabase.CREATE_IF_NECESSARY);
//    			Toast.makeText(this, "DB was opened!", 1).show();   
				Log.i("ke", "DB was opened!");
			} catch (Exception e) {
				// TODO: handle exception
//				Toast.makeText(this, e.getMessage(), 1).show();        	
				Log.e("ke", "DB not found");
				return false;
			}
//			useCursor1( m_array_book);
//			Toast.makeText( this, "Finished", 1).show();
			
		} 
		catch (Exception e)
		{
//			Toast.makeText( this, e.getMessage(), 1).show();
			return false;
		}	
	//	Log.d(TAG, "Green glyphs loaded");	
		return true;
	}
	
	@Override
	public void disconnect()
	{
		try {
			db.close();
		} 
		catch(Exception e)
		{
		}
		Log.d(TAG, "diconnect");
	}
	
	public void insertHashData( int sno, String title_and_singer )
	{
		m_HashTable.put( sno, title_and_singer );
		
		
//		Log.d(TAG, "size : "+ m_HashTable.size() + m_HashTable.get(123)  );
	}

	
	/**
	 * 
	 * @param sno
	 * @param title
	 */
	public void addRecent( int sno, String title )
	{
		snoLately.add(sno);
	}
	
	public void writeRecent(String file) throws IOException
	{
		
		File wFile = new File(file );
		
		FileWriter fwt = new FileWriter(wFile);
	    BufferedWriter bwt = new BufferedWriter(fwt);
	    int lineNum = snoLately.size();
	    if ( lineNum > 48)
	    	lineNum = 48;
	    	
	      
	       // File에 저장
	       for(int i=0; i<lineNum; i++){
	       
	    	   bwt.write(  snoLately.get(i).toString() );
	           bwt.newLine();
	       }
	                   
	    bwt.close();
	    fwt.close();
	}

	
	public boolean check_song ( int sno )
	{
		if ( m_HashTable.containsKey(sno) )
			return true;
		
		return false;
	}

	/**
	 * @param sno
	 * @param columns
	 * @return		곡정보가 있으면 true를 리턴한다. 
	 */
	public boolean query_song_info ( int sno, String[] columns )
	{
		columns[0] = Integer.toString( sno );

		if ( m_HashTable.containsKey(sno) )
		{
			String title_and_singer = m_HashTable.get( sno );
			
			int firstTab =	title_and_singer.indexOf('\t');
        	columns[1] = title_and_singer.substring( 0,  firstTab );
        	columns[2] = title_and_singer.substring( (firstTab+1), title_and_singer.length() );
        	
        	Log.d("ke", "title :" + columns[1]);
			return true;
		}
		
		//Log.e("ke", "song not found : " + Integer.toString(sno) ) ;
		return false;
	}

	/**
	 * @param sno
	 * @param columns
	 * @return
	 */
/*	
	public boolean query_song_info ( long sno, String[] columns )
	{
		boolean bRet = false;
		
//		String[] columns = {"sno","title", "artist"};
 		columns[0] = "sno";
 		columns[1] = "title";
 		columns[2] = "artist";
 		
		Cursor c;
		try
		{
			c =  db.query("song_table", columns, "sno=="+sno, null, null, null, null);
		} 
		catch (Exception e) 
		{
			Log.e(TAG, e.getMessage() );
			return false;
		}
		
		int theTotal = c.getCount();
		int idCol = c.getColumnIndex("sno");
		int nameCol = c.getColumnIndex("title");
		int phoneCol = c.getColumnIndex("artist");
		
		boolean exit = true;
		int num = 0;

		if ( theTotal > 0 )
			bRet =true;
			
		while( theTotal-- > 0) 
		{
			c.moveToNext();
			columns[0] = Integer.toString((c.getInt(idCol)));
			columns[1] = c.getString(nameCol);
			columns[2] = c.getString(phoneCol);
//			txtMsg.append(columns[0]+"|"+columns[1]+"|"+columns[2]+"\n");
//			str[num++] = columns[0] + "|" + columns[1] + "|" +columns[2];
			num++;
//			Log.d("ke", columns[0] + columns[1] + columns[2] );
		//	if ( num >900)
		//		exit = false;
		}			
		return bRet;
	}
*/	

    /**
     * 
     * @param str
     */
/*	
	private void useCursor1( ArrayList str) {
		
//		Toast.makeText(this, "DB Query.. wait plz", 1).show();

//        arMenu1[0] = "sdf";
		int num = 0;
		try {
//			txtMsg.append("\n");
			
			String[] columns = {"sno","title", "artist"};
			Cursor c =  db.query("song_table", columns, null, null, null, null, null);
			
			int theTotal = c.getCount();
			int idCol = c.getColumnIndex("sno");
			int nameCol = c.getColumnIndex("title");
			int phoneCol = c.getColumnIndex("artist");
			
			boolean exit = true;
			while(exit) 
			{
				c.moveToNext();
				columns[0] = Integer.toString((c.getInt(idCol)));
				columns[1] = c.getString(nameCol);
				columns[2] = c.getString(phoneCol);
				
//				txtMsg.append(columns[0]+"|"+columns[1]+"|"+columns[2]+"\n");
//				str[num++] = columns[0] + "|" + columns[1] + "|" +columns[2];
				
				CSongElement	p = new CSongElement( columns[0], columns[1], columns[2]);
				str.add(p);

				if ( num >900)
					exit = false;
			}			
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(this, e.getMessage(), 1).show();
		}
	}
*/	
	
	public void fill_row( String[] columns )
	{
		int idCol = cursor.getColumnIndex("sno");
		int nameCol = cursor.getColumnIndex("title");
		int phoneCol = cursor.getColumnIndex("artist");
		
		cursor.moveToNext();
		columns[0] = Integer.toString( (cursor.getInt(idCol)));
		columns[1] = cursor.getString(nameCol);
		columns[2] = cursor.getString(phoneCol);
	}
	
	/**
	 * 
	 */
	public int query_title_info(String keyword, String[] columns)
	{
		columns[0] = "sno";
		columns[1] = "title";
		columns[2] = "artist";
		
		try {
			cursor.close();
		}
		catch(Exception e )
		{
		}
		try
		{
			cursor = db.query( "song_table", columns, "title >='"+keyword+"'", 
								null, null, null, "title", "100" );
		}
		catch ( Exception e )
		{
			Log.e(TAG, "DB ERR" +e.getMessage() );
			return 0;
		}
		return cursor.getCount();
	}
	
	/**
	 * 
	 */
	public boolean Rquery_song_info( long sno, String[] columns )
	{
		boolean bRet = false;
		
		columns[0] = "sno";
		columns[1] = "title";
		columns[2] = "artiest";
		
		try
		{
			cursor = db.query("song_table", columns, "sno==", null , null, null, null);
		}
		catch ( Exception e )
		{
			Log.e(TAG, "DB ERR" + e.getMessage() );
			return false;
		}
		
		int theTotal = cursor.getCount();
		int idCol = cursor.getColumnIndex("sno");
		int nameCol = cursor.getColumnIndex("title");
		int phoneCol = cursor.getColumnIndex("artist");
		
//		boolean exit = true;
		int num = 0;
		if ( theTotal > 0 )
			bRet = true;
		while( theTotal-- > 0)
		{
			cursor.moveToNext();
			columns[0] = Integer.toString( (cursor.getInt(idCol) ));
			columns[1] = cursor.getString( nameCol);
			columns[2] = cursor.getString( phoneCol );
			
			num++;
		}
		
		cursor.close();
		return bRet;
	}
	
	
}
