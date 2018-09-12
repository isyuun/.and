/**
 * 
 * 
 */

package com.kumyoung.gtvkaraoke;

import java.util.HashMap;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;

import kr.kumyoung.gtvkaraoke.R;
import kr.kumyoung.gtvkaraoke.R.xml;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;



import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
/**
 * @author impaler
 *
 */
public class Glyphs  {

	private static final String TAG = Glyphs.class.getSimpleName();
	private Bitmap mbitmap[] = new Bitmap[4];	// bitmap containing the character map/sheet
	
	public class glyph_meta  {
		public int id;	
		public int sx;
		public int sy;		// src
		public int width;		// width
		public int height;		// height 
	
		public	int xoffset;
		public 	int yoffset; 
		public 	int xadvance; 
		
		public int page;
		
		public glyph_meta () 
		{
		}
	}

	// Map to associate a bitmap to each character
	private Map<Integer, Bitmap> glyphs_catche = new HashMap< Integer /*Character*/, Bitmap>(1024);
	private Map<Integer, glyph_meta> glyphs = new HashMap<Integer, glyph_meta>(1024);
	

	/**
	 * 
	 */
	public Glyphs(Resources res, Bitmap _bitmap, Bitmap _bitmap1, Bitmap _bitmap2, Bitmap _bitmap3 ) {
		super();
		
		
		if (_bitmap  == null) {
			Log.e("ke", "font not found");
		}
		
		this.mbitmap[0] = _bitmap;
		this.mbitmap[1] = _bitmap1;
		this.mbitmap[2] = _bitmap2;
		this.mbitmap[3] = _bitmap3;
		
		// typo reading
		try
		{
			XmlPullParser xpp = res.getXml( R.xml.kkk );
			String sTag;
			int eventType = xpp.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT)
			{
				switch(eventType)
				{
				case XmlPullParser.START_DOCUMENT:
					break;
		    
				case XmlPullParser.END_DOCUMENT:
					break;
		    
				case XmlPullParser.START_TAG:
					sTag = xpp.getName();
					if(xpp.getName().equals("char"))
					{
						glyph_meta m = new glyph_meta();
						m.id = Integer.parseInt( xpp.getAttributeValue(0) );
						m.sx = Integer.parseInt( xpp.getAttributeValue(1) );
						m.sy = Integer.parseInt( xpp.getAttributeValue(2) );
						m.width = Integer.parseInt( xpp.getAttributeValue(3) );
						m.height = Integer.parseInt( xpp.getAttributeValue(4) );
						m.xoffset = Integer.parseInt( xpp.getAttributeValue(5) );
						m.yoffset = Integer.parseInt( xpp.getAttributeValue(6) );
						m.xadvance = Integer.parseInt( xpp.getAttributeValue(7) );
						m.page = Integer.parseInt( xpp.getAttributeValue(8) );
						/*
						if(xpp.getAttributeValue(0) == "1")
						{
							xpp.setProperty("android:Label", "ㄱ");
						}
						*/
						
						glyphs.put( m.id,  m);
					}
					break;
				case XmlPullParser.END_TAG:
					sTag = xpp.getName();
					break;
		    
				case XmlPullParser.TEXT:
					break;
				}
		    
				eventType = xpp.next();
		   }
		}
		catch(Exception e)
		{
		}           
		
		
		
		
	/*	
		// Cutting up the glyphs
		// Starting with the first row - lower cases
		for (int i = 0; i < 127; i++) {
			glyphs.put(	charactersL[i], 
						Bitmap.createBitmap(	bitmap,
												((i%16) * width), (i/16 * height), 
												width, height));
		}
	*/	

/*
		// TODO - 4th row for punctuation
		
		
       // Resources res = this.getResources() ;
       
        try {
            InputStream is = res.openRawResource(R.raw.hangul ) ;
       
            //   TunnelDef.readTunnels(is) ;
            byte[] header = new byte[0xff];
            
            int index;
              
            is.read(header);
            index = is.read();
            
            
            is.close() ;        	
        }
        catch( IOException ioe ) {
        	Log.e("TempestActivity", "readTunnels failed with error: "+ioe) ;
        }
*/        
		
	}
/*{	
	public Bitmap getBitmap() {
		return bitmap[0];
	}
*/	


	/**
	 * 글꼴 출력전에 캐시에 준비해 둔다.  적시성이 높아질수 있다. 
	 */
	public void reserved_catche(String text)
	{
		for (int i = 0; i < text.length(); i++) 
		{
			glyph_meta m = glyphs.get( text.charAt(i) );
			if (glyphs_catche.get( m.id ) == null) 
			{
				Bitmap bm = Bitmap.createBitmap( mbitmap[m.page], m.sx, m.sy, m.width, m.height);
				glyphs_catche.put( m.id, bm );
			}
		}
	}
	
	
	/**
	 * Draws the string onto the canvas at <code>x</code> and <code>y</code>
	 * @param text
	 */
	public void drawString(Canvas canvas, String text, int x, int y) {
		
		if (canvas == null) {
			Log.d(TAG, "Canvas is null");
		}
			
		Paint Pnt = new Paint();
		Pnt.setColor(Color.BLUE);
		 ///       canvas.drawColor(Color.WHITE);
//		canvas.drawCircle( x, y,80, Pnt);
//		Pnt.setMaskFilter(maskfilter);
		
		int xp = 0;
		for (int i = 0; i < text.length(); i++) 
		{
			//Character ch = text.charAt(i);
			int ch = text.charAt(i);
			if (glyphs.get(ch) != null) {
				
				glyph_meta m = glyphs.get(ch);
		
				Bitmap bm = glyphs_catche.get(ch);
				if ( bm == null )
				{
					bm = Bitmap.createBitmap( mbitmap[m.page], m.sx, m.sy, m.width, m.height);
					glyphs_catche.put( m.id, bm );
				}
				
				
				canvas.drawBitmap( bm, xp + x + m.xoffset + 
						(-2*i), y + m.yoffset, null);
				xp += m.width;
			}
		}
		// multifly 
//		Pnt.setXfermode( new PorterDuffXfermode( PorterDuff.Mode.MULTIPLY ) );
//		canvas.drawCircle( 100, 100, 80, Pnt);
//		Pnt.setXfermode(null);
	}
}
