package com.kumyoung.gtvkaraoke;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;


public class XmlFinder
{
	//private String mXml;
	private String mTag;
	
	private HashMap<String,String> mAtt = new HashMap<String,String>();
	private String mContent;
	
	public XmlFinder()
	{
	}
	
/*
	public XmlFinder(String xml)
	{
		setXml(xml);
	}
	public void setXml(String xml)
	{
		mXml = xml;
	}
*/	
	public boolean find(String mXml, String tag)
	{
		mTag = tag;
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = null;
		try
		{
			saxParser = factory.newSAXParser();
			
			Log.d("LOG", "mXml " + mXml );
			InputStream is = new ByteArrayInputStream( mXml.getBytes() );
			saxParser.parse(is, mHandler);
		}
		catch (ParserConfigurationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SAXException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return isFind();
	}
	
	public boolean isFind()
	{
		return !(mAtt.isEmpty());
	}
	
	public String getAttribute(String localName)
	{
		if(isFind())
			return mAtt.get(localName);
		else
			return null;
	}
	
	public String getContent()
	{
		return mContent;
	}
	
	
	DefaultHandler mHandler = new DefaultHandler()
	{

		public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException
		{
//			System.out.println("Start Element :" + qName);
			
			if(qName.equalsIgnoreCase(mTag))
			{
				mAtt.clear();
//				System.out.println("Found tag !!!!!!!!!!!!!!!!!!!!!!!!");
				for( int i=0; i<attributes.getLength(); ++i )
				{
//					System.out.println("Att ("+attributes.getLocalName(i)+") :" + attributes.getValue(i));
					mAtt.put(attributes.getLocalName(i), attributes.getValue(i));
				}
			}
		}

		public void endElement(String uri, String localName, String qName) throws SAXException
		{
//			System.out.println("End Element :" + qName);
		}

		public void characters(char ch[], int start, int length) throws SAXException
		{
			if(isFind())
			{
				mContent = new String(ch, start, length);
			}
		}

	};
/*	
	public static void main(String argv[])
	{
		FileReader filereader;
		try
		{
			filereader = new FileReader("testxml.xml");
		}
		catch (FileNotFoundException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("File not found.");
			return;
		}
		
		BufferedReader reader = new BufferedReader(filereader);
	
		String line = new String("");
		try
		{
			while(reader.ready())
				line += reader.readLine();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(line.isEmpty())
		{
			System.out.println("File read failure.");
			return;
		}
		
		XmlFinder xf = new XmlFinder();
		xf.setXml(line);
		xf.find("dbs");
		
		if(xf.isFind())
		{
			System.out.println("Element is existed.");
			System.out.println("Address: " + xf.getAttribute("address"));
			System.out.println("Port   : " + xf.getAttribute("port"));
		}
	
	}
*/
}
