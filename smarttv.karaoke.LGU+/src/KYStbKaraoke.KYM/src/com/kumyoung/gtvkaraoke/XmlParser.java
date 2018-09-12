package com.kumyoung.gtvkaraoke;


import isyoon.com.devscott.karaengine.Global;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import android.util.Log;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

public class XmlParser 
{
	private static final String TAG = XmlParser.class.getSimpleName();
	
	public ArrayList<Integer> contentList = null;  	 
	public ArrayList<String> contentTitleList = null;  	 
	public ArrayList<String> contentSingerList = null;  	 
	
	
	public int nMaxItems;

	public boolean ParserLocal( String pathXML )
	{
	/*	
       // InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "EUC-KR"); 3
		try 
		{
			contentList = new ArrayList<Integer>();
			contentList = new ArrayList<Integer>();
			FileInputStream fstream = null;
			fstream = new FileInputStream( pathXML );
		
			SAXParserFactory parserModel = SAXParserFactory.newInstance();
			SAXParser concreteParser = parserModel.newSAXParser();
			//
			XMLReader myReader = concreteParser.getXMLReader();
			MySampleHandler mySample = new MySampleHandler(this);
			myReader.setContentHandler( mySample);
			myReader.parse(new InputSource(fstream));
		} 
		catch( Exception FileNotFoundException )
		{
			Log.v("report", "File not found exception!:" + pathXML);
			return false;
		}
		if ( contentList.size() <= 0 )
			return false;
	*/	
		return true;
	}

	public boolean Parser( String urlXML, ArrayList<Integer> p, ArrayList<String> title, ArrayList<String> singer)
	{
	    
   			contentList = p;
			contentTitleList = title;
			contentSingerList = singer;
			
	
			InputStream is = null;
			try
			{
			    
	            URL url = new URL(urlXML );
	if ( Global.isDebugLog )            
	{
	            Log.d("ke", "url :" + url);
	}
	            Log.d("ke", "url :" + url);

	            /** org */
//	          InputStream is = url.openStream();
	            
	            /** conversion */
	            URLConnection t_connection = url.openConnection();
	            t_connection.setReadTimeout( 5000);
	            t_connection.setConnectTimeout( 5000);
	            is = t_connection.getInputStream();
			}
			catch( Exception e)
		    {
		            Log.e( "KE", "network exception : 123" +  e.getMessage() ); 
		            //Log.e("report", e.getMessage());
		            return false;
		   }
	    
		try 
		{

			
			SAXParserFactory parserModel = SAXParserFactory.newInstance();
			SAXParser concreteParser = parserModel.newSAXParser();
		
			//
			XMLReader myReader = concreteParser.getXMLReader();
			MySampleHandler mySample = new MySampleHandler(this);
			myReader.setContentHandler( mySample);
			

			
			myReader.parse(new InputSource( is ));
			nMaxItems = mySample.countTotal;
			is.close();
		
		} 
		catch( Exception e)
		{
		    Log.e( TAG, "xml parser exception" +  e.getMessage() ); 
			//Log.e("report", e.getMessage());
			return false;
		}
		
		if ( contentList.size() <= 0 )
			return false;
	
		return true;
  		
	}
}

class MySampleHandler extends DefaultHandler
{
	private StringBuffer total 	= new StringBuffer();
	private StringBuffer now 	= new StringBuffer();
	private StringBuffer soseq 	= new StringBuffer();
	private StringBuffer title 	= new StringBuffer();
	private StringBuffer sngr1 	= new StringBuffer();
	private StringBuffer chorus = new StringBuffer();

	
	private boolean hasTotal 	= false;
	private boolean hasNow 		= false;
	private boolean hasSoseq 	= false;
	private boolean hasTitle 	= false;
	private boolean hasSngr1 	= false;
	private boolean hasChorus	= false;
	private boolean hasBody	= false;
	
	
	public int		countTotal = 0;
	
	private XmlParser  xp;

	public MySampleHandler( XmlParser xp ) 
	{ 
		this.xp = xp;
	}

	/**
	 * 
	 */
	public void startElement(String uri, String localName, String qName, Attributes atts )
	{

		if ( localName.equals("total"))
			hasTotal = true;
		else
		if ( localName.equals("now"))
			hasNow = true;
		else
		if ( localName.equals("soseq"))
			hasSoseq = true;
		else
		if ( localName.equals("title"))
			hasTitle = true;
		else
		if ( localName.equals("sngr1"))
			hasSngr1 = true;
		else
		if ( localName.equals("chorus"))
			hasChorus = true;
		
	
		if ( localName.equals("seq") )
			hasSoseq = true;
		else
		if ( localName.equals("body1") )
			hasBody = true;
			
			
	}
	
	public void endElement(String uri, String localName, String qName)
	{
		if ( localName.equals("record"))
		{
if (Global.isDebugLog)	
{
			// xp.updateTextView
			Log.d("XmlParser", "values: " + total.toString() + " : " + title.toString() );
}
			
			int sno =  Integer.parseInt(soseq.toString());
			xp.contentList.add( sno );
			xp.contentTitleList.add( title.toString() );
			xp.contentSingerList.add( sngr1.toString() );
			
			sngr1.delete(0, 1024);
		}
	}

	/**
	 * 
	 */
	public void characters( char[] chars, int start, int leng)
	{
		if ( hasTotal )
		{
			hasTotal = false;	
			total.delete(0, 255);	
			total.append(chars, start, leng);
			
			countTotal = Integer.parseInt( total.toString() );
		}
		else
		if ( hasNow )
		{
			hasNow = false;
			now.delete(0, 255);	
			now.append(chars, start, leng);
		}
		else
		if ( hasSoseq )
		{
			hasSoseq = false;
			soseq.delete(0, 255);	
			soseq.append(chars, start, leng);
		}
		else
		if ( hasTitle )
		{
			hasTitle = false;
			title.delete(0, 255);	
			title.append(chars, start, leng);
		}
		else
		if ( hasSngr1 )
		{
			hasSngr1 = false;
			sngr1.delete(0, 255);	
			sngr1.append(chars, start, leng);
		}
		else
		if ( hasChorus )
		{
			hasChorus = false;
			chorus.delete(0, 255);	
			chorus.append(chars, start, leng);
		}
		else
		if ( hasBody )
		{
			hasBody = false;
		//	sngr1.delete(0, 1024);
			sngr1.append(chars, start, leng);
		}
		
	}

}
