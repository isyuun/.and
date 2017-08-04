package com.kumyoung.stbui;


//import java.util.ArrayList;
import isyoon.com.devscott.karaengine.*;

import com.kumyoung.common.Constants;
import com.kumyoung.gtvkaraoke.DataHandler;
import kr.kumyoung.gtvkaraoke.R;
import com.kumyoung.stbcomm.ISUHandler;
import com.kumyoung.stbcomm.SIMClientHandlerLGU;
import com.kumyoung.stbcomm.SetopHandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

/*
public interface IEventListener
{
	public void invokeEvent ( int number, int count );
}
*/

public class BuyTicketView extends AbstractLV/*View*/ implements IEventListener {
	
	public boolean wrong_password = false;
	public boolean reset = false;
	

	public boolean regist = false;
    boolean ret_payment = false;
	
	private ProgressDialog loadingDialog; // loading dialog
    private Handler handler = null;
	void createThreadAndDialog() {
		// progress dialog
		loadingDialog = ProgressDialog.show( this.getContext(), "결제 요청 중", "잠시만 기다려 주십시오....", true, false);
		Thread thread = new Thread(new Runnable() {
			public void run() {
				// wailt
			
				
				ret_payment =false;
				
				if ( Global.isTestPayment )
				{
			
					try {
						Thread.sleep(4000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					ret_payment = true;
					
				}
				else
				{
					switch( DataHandler.ticket_type) {
			    		case 0:
			    		    
			    			ret_payment  = DataHandler.BuyTicket( SIMClientHandlerLGU.cont_no, 
			    			                                      SIMClientHandlerLGU.dmc_id,
			    			                                      SIMClientHandlerLGU.so_id,
			    			                                      SIMClientHandlerLGU.getSessionID(),
			    			                                      0,                                // register
			    			                                      SIMClientHandlerLGU.item_id_d);	//  300, 30		// 일정액 
			    			// result 확인 하여 ..  
			    			if ( ret_payment == true) {
			    				if (DataHandler.resultDate != null )
			    					DataHandler.login_message	 = new String(DataHandler.resultDate);
			    				//   DataHandler. 
			    				//DataHandler.GetUserInfoFromKY(  have_product );
			    			}
			    			break;
			    		case 1:
			    			ret_payment = ISUHandler.getInstance().subServiceConfirmAuth( SIMClientHandlerLGU.cont_no, SIMClientHandlerLGU.stb_mac_addr); 
			    			if ( ret_payment == true)
			    				ret_payment = ISUHandler.getInstance().subServiceReq( SIMClientHandlerLGU.cont_no, SIMClientHandlerLGU.stb_mac_addr, SIMClientHandlerLGU.item_id_m );
			    			
			    			if ( ret_payment == true )
			    			{
			    				ret_payment  = DataHandler.BuyTicket( SIMClientHandlerLGU.cont_no, 
			    				                                      SIMClientHandlerLGU.dmc_id,
			    				                                      SIMClientHandlerLGU.so_id,
			    				                                      SIMClientHandlerLGU.getSessionID(),
			    				                                      1,                                // register 
			    				                                      SIMClientHandlerLGU.item_id_m);	//  300, 30		// 일정액 
			    			}
			    			
			    			if ( ret_payment == true )    //message
			    			{
			    				if (DataHandler.resultDate != null )
			    					DataHandler.login_message	 = new String(DataHandler.resultDate);
			    			}
			    			break;
			    		default:
			    			ret_payment = ISUHandler.getInstance().subServiceConfirmAuth( SIMClientHandlerLGU.cont_no, SIMClientHandlerLGU.stb_mac_addr); 
			    			if ( ret_payment == true)
			    				ret_payment = ISUHandler.getInstance().subServiceReq( SIMClientHandlerLGU.cont_no, SIMClientHandlerLGU.stb_mac_addr, SIMClientHandlerLGU.item_id_mR );
			    			if ( ret_payment == true )
			    			{
			    				ret_payment  = DataHandler.BuyTicket( SIMClientHandlerLGU.cont_no, 
			    				                                      SIMClientHandlerLGU.dmc_id,
			    				                                      SIMClientHandlerLGU.so_id,
			    				                                      SIMClientHandlerLGU.getSessionID(),
			    				                                      2, 
			    				                                      SIMClientHandlerLGU.item_id_mR);	//  300, 30		// 일정액 
			    			}
			    			if ( ret_payment == true )
			    			{
			    				if (DataHandler.resultDate != null )
			    					DataHandler.login_message	 = new String(DataHandler.resultDate);
			    			}
			    			break;
			    	}
					
					
				}
			
				
				regist = true;
				handler.sendEmptyMessage(0);
			}
		});
		thread.start();
		
		handler = new Handler() {
			public void handleMessage(Message msg) {
				loadingDialog.dismiss();
				
				
				invalidate();   //  결과를 화면에 그린다. 
			}
		};
		
	}
	
	class Sheet {
	
		IEventListener		L;
		private int 		product = 0;
		private	int			curPos = 0;
	    private int 		lines = 2;
		private Rect		_rc;
		private Bitmap      bmNormal = null;
		private Bitmap bmActive = null;
		private String text 	= null;
		
		button [] btn;
		int btn_count = 0;
		
		String	strPassNumber = "";
		int	nLength = 0;
		//int value = 1000;
			
			 
		private String 		buf[] = {	"", 	"", "     ", 
									"     취 소  ", "", ""
								};
		private String		description[] = { 
									"하루 1,000원으로 전 곡을 무제한으로 즐길 수 있습니다.", 
									"구매 후 취소가 되지 않습니다.",
									"단, 콘텐츠가 광고된 내용과 다른경우 관련법에 따라 취소할 수 있습니다.",
									"　",
									
									"매월 3,000원이 자동 청구됩니다.", 
									"구매 후 취소 되지 않으며, 1개월 이내 해지시 1개월 요금이 청구됩니다.",
									"해지는 고객센터 또는 마이메뉴를 통해서 가능합니다.",
									"(단, 컨텐츠가 광고된 내용과 다른 경우 관련법에 따라 취소 할 수 있습니다)",
							//		"* 매월 3,000원이 자동 결재되며, ", 
							//		"  가입후1개월은 해지와 관계없이 1개월요금이 청구됩니다", 
							//		"* 해지는 리모콘 [메뉴>마이메뉴>가입내역]에서 확인 가능합니다.",
									
									
									"매월 3,000원이 자동 청구됩니다.(마이크 1개 무료 제공!)", 
							//		"* 매월 3,000원이 자동 결재됩니다.", 
									"구매 후 취소 되지 않으며, 1개월 이내 해지시 1개월 요금이 청구됩니다.",
									"해지는 고객센터 또는 마이메뉴를 통해서 가능합니다.",
									"(단, 컨텐츠가 광고된 내용과 다른 경우 관련법에 따라 취소 할 수 있습니다)",
									
									"", "",   "",
									"", "",   "",
									"", "",   "",
									"", "",   "",
									"", "",   "",
									"", "",   "",
									"", "",   "",
									"", "",   "",
									"", "",   "",
									"", "",   "",
									"", "",   "",
									"", "",   "",
									
									"", "",   "",
									"", "",   "",
									"", "",   "",
									"", "",   "",
									
		};
    	private int 		id[] = { 	
    		R.drawable.ticket1,
    		R.drawable.ticket2,
    		R.drawable.ticket3,
    		R.drawable.pop_bt
    	};
    	private int 		id_f[] = { 	
    		R.drawable.ticket_f,
    		R.drawable.ticket_f,
    		R.drawable.ticket_f,
    		R.drawable.pop_bt_f
    	};

		
		public Sheet( IEventListener _l,int _x, int _y, int _w, int _h, Bitmap n, Bitmap a, String _text)
		{
			L = _l;
			_rc = new Rect ( _x, _y, _x+_w, _y+_h );
			this.bmNormal = n;
			this.bmActive = a;
			this.text = _text;
			
			btn = new button[4];
		}
		
		public int	GetPos()
		{
			return curPos;
		}
		
		public void addSheet1()
		{
		    int dpX = Global.DPFromPixel(Constants.width)/2 - bmBG.getWidth()/2;
 	        int dpY = Global.DPFromPixel(Constants.height)/2 - bmBG.getHeight()/2;
			btn_count = 4;
			
			final int ITEM_Y = 76;
			
			int indent = 0;
	        for ( int i = 0; i < (btn_count-1); i++ )
	        {
	        	
	            Bitmap bmPayment = BitmapFactory.decodeResource( contax.getResources() , id[i]	);
	        	Bitmap bmFocus = BitmapFactory.decodeResource( contax.getResources() , id_f[i] );
	        	
	        	indent = Global.PixelFromDP(30);
	        	btn[i] = 		new button(  
	        	                             Global.PixelFromDP(dpX) + indent + (i%3*(Global.PixelFromDP(220))), 
	        	                             Global.PixelFromDP(dpY) + Global.PixelFromDP(ITEM_Y),
	        	                             bmFocus.getWidth(), bmFocus.getHeight(), 
        						bmPayment, 		// image
        						bmFocus, 
        						buf[i] );
	        	btn[i].Indent(6);
	        }
	        // close
	        btn[btn_count-1] = 	new button(  Global.PixelFromDP(dpX) + 400,  Global.PixelFromDP(dpY)+ 570 , 250, 90, 
        						BitmapFactory.decodeResource( contax.getResources() , R.drawable.pop_bt),
        						BitmapFactory.decodeResource( contax.getResources() , R.drawable.pop_bt_f), 
        						buf[3]);
	        curPos = 1;
		}
		
		public void addSheet2()
		{
 	        int dpX = Global.DPFromPixel(Constants.width)/2 - bmBGsmall.getWidth()/2;
 			int dpY = Global.DPFromPixel(Constants.height)/2 - bmBGsmall.getHeight()/2;
			btn_count = 3;
			
		    btn[0]           = 	new button( Global.PixelFromDP(dpX) + 310,  Global.PixelFromDP(dpY) + 268, 220, 50, 
        						BitmapFactory.decodeResource( contax.getResources() , R.drawable.ticket_pay_n ),
        						BitmapFactory.decodeResource( contax.getResources() , R.drawable.ticket_pay_f), 
        						"");
		    
		    btn[0].focusTextColor = Color.BLACK;
   	        btn[btn_count-2] = 	new button( Global.PixelFromDP(dpX) + 210, Global.PixelFromDP(dpY) +  455 , 200, 50, 
        						BitmapFactory.decodeResource( contax.getResources() , R.drawable.pop_bt),
        						BitmapFactory.decodeResource( contax.getResources() , R.drawable.pop_bt_f), 
        						"     확 인  ");
	        btn[btn_count-1] = 	new button( Global.PixelFromDP(dpX) + 450, Global.PixelFromDP(dpY) +  455 , 200, 50, 
        						BitmapFactory.decodeResource( contax.getResources() , R.drawable.pop_bt),
        						BitmapFactory.decodeResource( contax.getResources() , R.drawable.pop_bt_f), 
        						"     취 소  ");
		}
		
		public void addSheet3()
		{
			btn_count = 1;
 	        int dpX = Global.DPFromPixel(Constants.width)/2 - bmBGsmall.getWidth()/2;
 			int dpY = Global.DPFromPixel(Constants.height)/2 - bmBGsmall.getHeight()/2;
 
	        btn[btn_count-1] = 	new button( Global.PixelFromDP(dpX)+320,  Global.PixelFromDP(dpY)+ 455, 200, 50, 
        						BitmapFactory.decodeResource( contax.getResources() , R.drawable.pop_bt),
        						BitmapFactory.decodeResource( contax.getResources() , R.drawable.pop_bt_f), 
        						"     확 인  ");
		}
	
	
		
		public void drawDescription( Canvas c)
		{
			Paint p = new Paint();
			p.setAntiAlias(true); // 테두리를 부드럽게한다
	
		
			//final int DESCRIPTION_Y = 472;
			final int DESCRIPTION_Y = 462;
			if ( curStep == 0)
			{
			    int dpX = Global.DPFromPixel(Constants.width)/2 - bmBG.getWidth()/2;
			    int dpY = Global.DPFromPixel(Constants.height)/2 - bmBG.getHeight()/2;
 	
				p.setColor( 0xFF202020) ;
				/*
				p.setTextSize( 22 );
				c.drawText( description[ curPos*3 ], dpX +  Global.DPFromPixel(90), dpY + Global.DPFromPixel(DESCRIPTION_Y), p );
				c.drawText( description[ curPos*3 +1 ], dpX +  Global.DPFromPixel(90), dpY + Global.DPFromPixel(DESCRIPTION_Y+40), p );
				c.drawText( description[ curPos*3 +2 ], dpX +  Global.DPFromPixel(90), dpY + Global.DPFromPixel(DESCRIPTION_Y+80), p );
				*/
				p.setTextSize( 16 );
				c.drawText( description[ curPos*4 ], dpX +  Global.DPFromPixel(90), dpY + Global.DPFromPixel(DESCRIPTION_Y)-8, p );
				c.drawText( description[ curPos*4 +1 ], dpX +  Global.DPFromPixel(90), dpY + Global.DPFromPixel(DESCRIPTION_Y+30-8), p );
				c.drawText( description[ curPos*4 +2 ], dpX +  Global.DPFromPixel(90), dpY + Global.DPFromPixel(DESCRIPTION_Y+60-8), p );
				c.drawText( description[ curPos*4 +3 ], dpX +  Global.DPFromPixel(90), dpY + Global.DPFromPixel(DESCRIPTION_Y+90-8), p );
			}
			else
			if ( curStep == 1 )
			{
			   
			    regist = false;
			    
		        int dpX = Global.DPFromPixel(Constants.width)/2 - bmBGsmall.getWidth()/2;
 	            int dpY = Global.DPFromPixel(Constants.height)/2 - bmBGsmall.getHeight()/2;
 	
				p.setColor( 0xFF202020) ;
				p.setTextSize( 24 );
				c.drawText( "결제를 위해 비밀번호 4자리를 입력해 주세요.", 
				            dpX + Global.DPFromPixel(90), dpY + Global.DPFromPixel(150), p );
				p.setTextSize( 26);
				
				if (DataHandler.ticket_type == 0 )
				    c.drawText( "1,000 원 ", dpX+ Global.DPFromPixel(400), dpY+Global.DPFromPixel(240), p );
				else
				    c.drawText( "3,000 원 ", dpX+ Global.DPFromPixel(400), dpY+Global.DPFromPixel(240), p );
				// comma
				if ( wrong_password == true )
				{
					p.setColor( 0xFF0Fff0f) ;
					p.setTextSize( 20 );
					c.drawText( "비밀번호가 틀렸습니다. 재입력해주세요 ",  dpX + Global.DPFromPixel(170),dpY+ Global.DPFromPixel(420), p );
				}
			}
			else
			if ( curStep == 2 )
			{
			    
			    int dpX = Global.DPFromPixel(Constants.width)/2 - bmBGsmall.getWidth()/2;
			    int dpY = Global.DPFromPixel(Constants.height)/2 - bmBGsmall.getHeight()/2;
				int result = -1;
//				if ( sheet[curStep].GetPos() == 0 )
				
			    if ( regist == false ) // 두번진입 방지 
			    {
			    	createThreadAndDialog();
			    }
			   
			   
			    //
			    // message
			    //
			    if ( regist == true ) {
			    	p.setColor( 0xFF202020) ;
			    	p.setTextSize( 18 );
			    	if (  ret_payment == true  ) {
			    		p.setTextSize( 24 );
			    		c.drawText( "상품명 : " + DataHandler.ticket_name + " 을", dpX+ Global.DPFromPixel(180), dpY+Global.DPFromPixel(230-10), p );
			    		c.drawText( "성공적으로 결제하였습니다 ", dpX+ Global.DPFromPixel(180), dpY+Global.DPFromPixel(272-10), p );
			    		//
			    		// DataHandler.resultDate =new String("asijdfoaidsfj asdfasdopif");
			    		if ( DataHandler.resultDate != null) {
			    			p.setColor( 0xFF204040) ;
			    			p.setTextSize( 18 );
			    			if ( DataHandler.ticket_type == 0)	{
			    			   c.drawText( "- 사용기간 -",  dpX+ Global.DPFromPixel(320), dpY+Global.DPFromPixel(300), p );
			    			}
			    			c.drawText( DataHandler.resultDate,  dpX +Global.DPFromPixel(180), dpY + Global.DPFromPixel(272+60), p );
			    		}
			    		
			    		switch ( DataHandler.ticket_type)
			    		{
			    		case 0:	break;
			    		case 1:
			    			p.setColor( 0xFF402020) ;
			    			p.setTextSize( 18 );
			    			c.drawText( "월정액 가입이 완료되었습니다.", dpX+ Global.DPFromPixel(130),dpY+ Global.DPFromPixel(322+60), p );
			    			c.drawText( "해지는 리모콘 마이메뉴키 > 가입내역에서 가능합니다.", dpX+ Global.DPFromPixel(130),dpY+ Global.DPFromPixel(322+90), p );
			    			break;
			    		case 2:	break;
			    		}
	
			    	/*
			    			// 고객센터 출력 
			    			p.setColor( 0xFF402020) ;
			    			p.setTextSize( 18 );
			   
			    			c.drawText( DataHandler.svc_kumyoung, dpX + Global.DPFromPixel(130), dpY +Global.DPFromPixel(332 + 40), p);
			    	*/	
			    	}
			    	else
			    	{
			    		p.setTextSize( 24 );
			    		c.drawText( "결제를 실패하였습니다.", dpX+ Global.DPFromPixel(180), dpY+Global.DPFromPixel(212), p );
			    		
			    		switch ( DataHandler.ticket_type)
			    		{
			    		case 0:
			    			p.setTextSize( 18 );
			    			c.drawText( "리턴코드 :" + DataHandler.getInstance().retString, dpX+ Global.DPFromPixel(180), dpY+Global.DPFromPixel(252), p );
			    			c.drawText( DataHandler.getInstance().retString, dpX+ Global.DPFromPixel(180), dpY+Global.DPFromPixel(282), p );
			    			break;
				       
			    		case 1:
			    		case 2:
			    			p.setTextSize( 18 );
			    			c.drawText( "리턴코드 :" + ISUHandler.getInstance().resultCode[0], dpX+ Global.DPFromPixel(180), dpY+Global.DPFromPixel(252), p );
			    			c.drawText( ISUHandler.getInstance().retString, dpX+ Global.DPFromPixel(180), dpY+Global.DPFromPixel(282), p );
			    			break;
			    		}
			    		
				    	p.setColor( 0xFF402020) ;
			    		p.setTextSize( 18 );
			    		c.drawText( SIMClientHandlerLGU.svc_kumyoung, dpX+ Global.DPFromPixel(250),dpY+ Global.DPFromPixel(292+140), p );
			    		
			    	}
				
			    }
				
//				if (  DataHandler.resultMessage[1] != null )
//					c.drawText( DataHandler.resultMessage[1],  Global.DPFromPixel(410), Global.DPFromPixel(252), p );
				if ( result != 0 )		//  successs
				{
	//				if ( DataHandler.resultDate != null)
	//					c.drawText( DataHandler.resultDate,  Global.DPFromPixel(380), Global.DPFromPixel(262+30), p );
			
					// update information ( 가입자 주문내영 요청 
				    
	/* 100, 11 으로 확인할것. 			    
					//600, 				    String have_product = SIMClientHandlerLGU.orders_info_sendRequestData();				// 가입자 주문내역정보 ( 날자조회를 몇일부터 몇일까지 하는거인가? )
					DataHandler.GetUserInfoFromKY(  have_product );
	 */	
				   // 100,11 
				  // SIMClientHandlerLGU.sendRequestServiceUserData();
				    
				    
				}
			}
		}
		
		public void drawControl ( Canvas c )
		{
			for (int i = 0; i < btn_count;  i++)
				btn[i].draw(c,  i==curPos );
	
			drawDescription( c );
		}
		
		
		public void left()	{   
			if ( curPos > 0)
				curPos--;
		}
		public void up() 	{
			if ( curPos > 0)
				curPos --;
		}
		public void right()
		{
			if ( curPos < btn_count-1)
				curPos++;
		}
		public void down()
		{
//			if (curPos< btn_count-1)
//				curPos++;
			curPos = btn_count-1;
		}
		
		
		public void onTouch ( int x, int y )
		{
			for ( int i = 0; i < btn_count; i++)
			{
				if ( btn[i].ptInRect( x, y ) == true )
				{
					curPos = i; 
					
					if ( curPos == btn_count-1 )	//
						L.invokeEvent(0, 0);		// cancel 
					else
						L.invokeEvent(1, curPos);
				}
			}
		}
	
		public String val2num( int n )
		{
		
			switch ( n )
			{
				case KMsg.MSG_KBD_9:	return new String("9");	
				case KMsg.MSG_KBD_8:	return new String("8");	
				case KMsg.MSG_KBD_7:	return new String("7");	
				case KMsg.MSG_KBD_6:	return new String("6");	
				case KMsg.MSG_KBD_5:	return new String("5");	
				case KMsg.MSG_KBD_4:	return new String("4");	
				case KMsg.MSG_KBD_3:	return new String("3");	
				case KMsg.MSG_KBD_2:	return new String("2");	
				case KMsg.MSG_KBD_1:	return new String("1");	
				case KMsg.MSG_KBD_0:	return new String("0");
			}
		
			return new String(" ");
			
		}
		
		public void onKey( int key , int step )
		{
			switch ( key )
			{
				case KMsg.MSG_KBD_UP : 		up(); 	break;
				case KMsg.MSG_KBD_LEFT : 	left(); break;
				case KMsg.MSG_KBD_RIGHT : 	right();break;
				case KMsg.MSG_KBD_DOWN : 	
				    {
						if ( step == 1 && curPos == 0)
							curPos = 1;
						else
							down(); 
				    }
					break;
		
				case KeyEvent.KEYCODE_DPAD_CENTER:
				case KeyEvent.KEYCODE_ENTER : 
					if ( curPos == btn_count-1 )	//
						L.invokeEvent(0, 0);		// cancel 
					else
						L.invokeEvent(1, curPos);
					
					if ( reset == true)
					{
						reset =  false;
						curPos = 0;
						nLength  = 0;
						password = "";       			   
						btn[0].SetText( password );
					}
					break;
					
				case KeyEvent.KEYCODE_DEL :
					if ( nLength > 0 )
					{
						nLength--;
						String str = new String("");
						for ( int i = 0; i < nLength; i++ )
							str += "* ";
						btn[0].SetText( str );
						if ( password.length() > 0 )
						{
						//	password /= 10;
							//password = password.left( password.length() -1 );
							password = password.substring(0, password.length()-1); 
						}
					}
					break;
					
					
				case KMsg.MSG_KBD_9:	;
				case KMsg.MSG_KBD_8:	;
				case KMsg.MSG_KBD_7:	;
				case KMsg.MSG_KBD_6:	;
				case KMsg.MSG_KBD_5:	;
				case KMsg.MSG_KBD_4:	;
				case KMsg.MSG_KBD_3:	;
				case KMsg.MSG_KBD_2:	;
				case KMsg.MSG_KBD_1:	;
				case KMsg.MSG_KBD_0:	
					if ( step == 1)
					{
						if ( nLength < 4)
						{
							//password = ((password*10)+nVal);
							password += val2num( key) ;
							//----------------------------------
						
							nLength++ ;
							String str = new String("");
							for ( int i = 0; i < nLength; i++ )
								str += "* ";
							btn[0].SetText( str );
						}
						if ( nLength == 4 )
						{
							curPos = 1;
						}
					}
					break;
			}
		}
	}	
		
    
	private static final String TAG = BuyTicketView.class.getSimpleName();
	private int 				curStep = 0;
	private Sheet				[]sheet;
   
    private Bitmap 			bmBG;
    private Bitmap 			bmBGsmall;
    
    private Bitmap			bmTicketPay;
    //private Bitmap			bmTicketPayFocus;
    private Bitmap			bmTicketDate;

    // memory dc  
	protected Bitmap 		bitmapMemory ;
	protected Canvas 		canvasMemory = null;
	public String				password = "";
	
	
    // 속성이 없는 생성자는 소스상에서 직접 생성할때만 쓰인다. 
    public BuyTicketView(Context context) 
    {
        super(context);
//        _prepare(3, 1, 1);
        Log.w(TAG,"BuyTicketView("+context+")");
        bmBG = BitmapFactory.decodeResource( contax.getResources(), R.drawable.ticket_bg_big);
        bmBGsmall = BitmapFactory.decodeResource(contax.getResources(), R.drawable.ticket_bg_small);
        
        bmTicketPay = BitmapFactory.decodeResource(contax.getResources(), R.drawable.ticket_pay);
        //bmTicketPayFocus = BitmapFactory.decodeResource(contax.getResources(), R.drawable.ticket_pay_f);
        bmTicketDate = BitmapFactory.decodeResource(contax.getResources(), R.drawable.ticket_date );
        
        //
        // create memory 
       	bitmapMemory = Bitmap.createBitmap( Global.DPFromPixel(Constants.width), Global.DPFromPixel(Constants.height), Config.ARGB_8888 );
        canvasMemory = new Canvas(bitmapMemory);

		// isyoon_20150427
		canvasMemory.setDensity(metrics.densityDpi);

        sheet	= new Sheet[5];
        sheet[0]	= 	new Sheet( this, 0/*420*/,  388 , 200, 50, 
        							BitmapFactory.decodeResource( contax.getResources() , R.drawable.pop_bt),
        							BitmapFactory.decodeResource( contax.getResources() , R.drawable.pop_bt_f), 
        							"");
        
        sheet[1] 	= 	new Sheet( this, 	420,  388 , 200, 50, 
        							BitmapFactory.decodeResource( contax.getResources() , R.drawable.pop_bt),
        							BitmapFactory.decodeResource( contax.getResources() , R.drawable.pop_bt_f), 
        							"");
        
        sheet[2] 	= 	new Sheet( this, 	420,  388 , 200, 50, 
        							BitmapFactory.decodeResource( contax.getResources() , R.drawable.pop_bt),
        							BitmapFactory.decodeResource( contax.getResources() , R.drawable.pop_bt_f), 
        							"");
        sheet[0].addSheet1();
        sheet[1].addSheet2();
        sheet[2].addSheet3();
        drawBackground( curStep );

/*
   		// draw
	    for ( int i =0; i< MAX_ITEMS; i++)
       		btn[i].drawTranslate(canvasMemory);
*/
        
    
//        new EventNotifier(this);
    }

    
    public void onSubDraw(Canvas c )
    {
 /*	
    	Paint p = new Paint();
        p.setAntiAlias(true); // 테두리를 부드럽게한다
		p.setColor( 0xA0000000);
		p.setTextSize( 15 );
		if ( curStep == 0)
		{
			int pos = sheet[curStep].GetPos();
			c.drawText( description[ pos ],  Global.DPFromPixel(270), Global.DPFromPixel(360), p );
		}
*/		
    }
    
/*  
    public void makeFocus()
    {
    	setFocusable(true);
    	requestFocus();
       	Log.e(TAG, "makeFocus()");
       	invalidate();
    }

    public void doFocusChange(Boolean focused, int direction )
    {
            if (focused) {
                switch (direction) {
                    case View.FOCUS_DOWN:
                       // mSelectedRow = 0;
                    	
                    	Log.e(TAG, "FOCUS_DOWN");
                        
                        break;
                    case View.FOCUS_UP:
//                        mSelectedRow = mNumRows - 1;
                        
                    	Log.e(TAG, "FOCUS_UP");
                        break;
                    case View.FOCUS_LEFT:  // fall through
                    case View.FOCUS_RIGHT:
                    	
                    	Log.e(TAG, "FOCUS_LEFT/RIGHT");
                             // set the row that is closest to the rect
       //                 if (previouslyFocusedRect != null) {
      //                      int y = previouslyFocusedRect.top
     //                               + (previouslyFocusedRect.height() / 2);
    //                        int yPerRow = getHeight() / mNumRows;
   //                         mSelectedRow = y / yPerRow;
  //                      } else {
 //                           mSelectedRow = 0;
//                        }
                             
                        break;
                    default:
                        // can't gleam any useful information about what internal
                        // selection should be...
                    	Log.e(TAG, "FOCUS_DEFAULT");
                        return;
                }
                
               
            }
            invalidate();
    }
 */ 
    
    void drawControl( Canvas cv)
    {
/*
		int row = Global.DPFromPixel(33 + 2);
        int lineperheight = row;
 */      
       	if ( bitmapMemory == null)
    	{
    		Log.e(TAG, "not create bitmapMemory");
    	}
    	else
    	{
    		cv.drawBitmap(bitmapMemory, 0, 0, null );
    	}
 
		sheet[curStep].drawControl(cv);
		onSubDraw(cv);
        
        
//       if ( bmBG != null )
 //       	cv.drawBitmap ( bmBG, 0, 0, null ); 		// 버퍼에 그리기 
        
       /* 
        if ( curItem == 0 )
        	cv.drawBitmap ( bmItem1, 125, 15, null ); 		// 버퍼에 그리기 
        else
        	cv.drawBitmap ( bmItem2, 125, 15, null ); 		// 버퍼에 그리기 

        //draw focus
        if ( hasFocus())
        {
        if ( curPos == 0)
        	cv.drawBitmap ( bmFocus, 125, 15, null ); 		// 버퍼에 그리기 
        
        if ( curPos == 1)
        	cv.drawBitmap( bmInputFocus, 248,15, null);
        }
*/ 
        	
        //cv.drawRect( 0, curPos*row, width, curPos*row+row, p);
 
 /*       
        for ( int i = 0; i < lines; i++ )
        {
        	int y = ( row * i);
        	int typo_y = y +  Global.DPFromPixel(32); // font h;
        	
       		String[] columns = {"sno","title", "artist"};
       		Database.Inst().query_song_info( cur_sno +  i, columns);	
        	
        	drawSongNumber( cv, 14, 16+y,  (cur_sno+i) );
        	
        	p.setColor( 0xFFD09090);
        	cv.drawText( columns[1], Global.DPFromPixel(105), typo_y, p );
        	p.setColor( 0xFFD0D0D0);
        	cv.drawText( columns[2], Global.DPFromPixel(536), typo_y, p); 
//        	cv.drawText("*", 955, y, p); 
        	
        }
*/    	
    }
    
        @Override
        protected void onDraw(Canvas canvas) {
        	
    //		lines = 20;
//    		Log.w(TAG,"onDraw("+canvas+")" + (++redraw_count) );
        	drawControl(canvas );

    	//canvas.drawBitmap( bitmapList, 240, bmTop.getHeight(), null );
    	
        	Paint paint = new Paint();
        	paint.setColor(Color.argb(0x80, 0, 0, 0));
        	paint.setStyle(Paint.Style.FILL);
/* 
    	if ( strText != null )
    	{
    		paint.setTextSize( 24 );
			paint.setColor(Color.argb(0xFF, 0x0, 0x0,0x0 ));
			paint.setAntiAlias(true);
 
    		canvas.drawText( strText, 260, 43, paint);
    	}
*/	
        
//  		if ( hasFocus() )
// 		{
//    		if ( !KOSD.Inst().is( KOSD.eOSD_BOOK_EDIT ) )
//   			btn[curPos].draw(canvas, true );
//  		}
 
    	super.onDraw(canvas);
           
    }
            
    @Override
    public boolean onTouchEvent(MotionEvent event) 
    {
	   Log.w(TAG,"onTouchEvent("+event+")");
       int mouseX =(int) event.getX();
       int mouseY =(int) event.getY();
   	
       switch(event.getAction()) {
       
       case MotionEvent.ACTION_UP:
           backgroundColor = Color.RED;
           sheet[curStep].onTouch( mouseX, mouseY );
           break;
           
       case MotionEvent.ACTION_DOWN:
           backgroundColor = Color.YELLOW;
           break;
           
       case MotionEvent.ACTION_MOVE:
           backgroundColor = Color.TRANSPARENT;
           text = "Moved!";
           break;
          
       case MotionEvent.ACTION_POINTER_UP:
           backgroundColor = Color.BLUE;
           text = "Outside";
           break;
       }
//       return super.onTouchEvent(event);
       
       return true;

    } 
 
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	
		// 20150414
		// 종료키처리
		try {
			if (keyCode == KeyEvent.KEYCODE_TV || keyCode == 170) {
				Log.e(TAG, "onKeyDown(" + keyCode + "," + event + ")" + "[종료]");
				return super.onKeyDown(keyCode, event);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sheet[curStep].onKey( keyCode, curStep );
    	
  /*  	
        Log.w(TAG,"onKeyDown("+keyCode+","+event+")");
        switch ( keyCode )
        {
        
        case KMsg.MSG_KBD_DOWN : 			sheet[curStep].down();
//        			if ( curPos == 1)
        			{
//        				toggleFocus(); 
        				break;
        			}
        			
        case KMsg.MSG_KBD_UP:				sheet[curStep].up();	
//       			if ( curPos == 0 )
//        				curItem = (++curItem)%2;
        			break;
        			
        case KMsg.MSG_KBD_LEFT:				sheet[curStep].left();
 //       	if ( curPos > 0 )
	//			curPos--; 
	//		else 
	//			curPos = (lines-1);
        	break;
        			
        case KMsg.MSG_KBD_RIGHT:			sheet[curStep].right();
//  			if ( curPos < (lines-1) )
//   				curPos++;
//  			else	
 // 				toggleFocus();
   			break;
        			
        			
      	case KMsg.MSG_KBD_STOP: 
        	Global.Inst().app.doMenu( 99 );
        	return false;
        			
        case KeyEvent.KEYCODE_ENTER : 
       		fireCommand();
        	return true;
        	

       	default:
        // PARENT가 처리하게 내버려 둔다. 
        case KeyEvent.KEYCODE_BACK:
        	return super.onKeyDown(keyCode, event); 
        }
*/        
        
        invalidate();
        
//      return super.onKeyDown(keyCode, event); 
        return true;		
    }
   
    /**
     * 
     */
   /* 
    private void fireCommand()
    {
    	
    	switch ( curStep )
    	{
    	case 0:		
    		curStep ++;
    		drawBackground( curStep);
    		break;
    	
    	case 1:		
    		curStep ++;
    		drawBackground( curStep);
    		break;
    	}
    }
*/ 

    void drawBackground( int _step )
    {
    	// shade 
   		Paint paint = new Paint();
        paint.setColor(Color.argb(0x80, 0x0, 0, 0));
   		paint.setStyle(Paint.Style.FILL);
   		canvasMemory.drawRect(new Rect( 0,  0,(Constants.width), Constants.height), paint );
   		
   		int width = Constants.widthReal;
   		int height = Constants.heightReal; 
  		int px, py;
	
  		switch ( _step )
 		{
 			case 0:
 			    {
 			        int dpX = Global.DPFromPixel(Constants.width)/2 - bmBG.getWidth()/2;
 				    int dpY = Global.DPFromPixel(Constants.height)/2 - bmBG.getHeight()/2;
 				    canvasMemory.drawBitmap ( bmBG, dpX, dpY, null ); 		// 버퍼에 그리기 
 			    }
 				break;
 			
 			case 1:
 			    {
 			        int dpX = Global.DPFromPixel(Constants.width)/2 - bmBGsmall.getWidth()/2;
 				    int dpY = Global.DPFromPixel(Constants.height)/2 - bmBGsmall.getHeight()/2;
 				    canvasMemory.drawBitmap ( bmBGsmall, dpX, dpY, null ); 		// 버퍼에 그리기 
 				    canvasMemory.drawBitmap ( bmTicketPay,  (dpX+Global.DPFromPixel(90)), 
 				                                            (dpY+Global.DPFromPixel(140)), null);
 				    // 결제를 위해 비밀번호 4자리를 입력해 주세요.
 			    }
 				break;
 				
 			default:
 			case 2:
 			    {
 			        int dpX = Global.DPFromPixel(Constants.width)/2 - bmBGsmall.getWidth()/2;
 				    int dpY = Global.DPFromPixel(Constants.height)/2 - bmBGsmall.getHeight()/2;
 			        canvasMemory.drawBitmap ( bmBGsmall, dpX, dpY, null ); 		// 버퍼에 그리기 
 			        //canvasMemory.drawBitmap ( bmTicketDate, (x+70), (y+85), null);
 			        // 결제를 위해 비밀번호 4자리를 입력해 주세요.
 			    }
 				break;
 		}
 		
 		/*
   			final Paint p = new Paint();
   			p.setColor( 0x7fffffFF );   	//빨간 계열 비트맵으로 그린다.	
   			canvasMemory.drawLine(x, y,   					x+bmBG.getWidth(), y+bmBG.getHeight(), p);
   			canvasMemory.drawLine(x, y+bmBG.getHeight(), 	x+bmBG.getWidth(), y, p); 
   		*/	
    }
 		
    
    //interface implement
    @Override
	public void invokeEvent( int lparam, int wparam)
    {
//        System.out.println( count + " : " + number + " called" );
        try
        {
            Thread.sleep( 10 );
        }
        catch ( InterruptedException e )
        {
            e.printStackTrace( );
        }
        
        if ( lparam == 0)
        {
        	container.dismiss();
       		KPlay.Inst().play_send( KMsg.MSG_MAIN_MENU );
        }
        else
        {
        	if ( curStep == 0 )
        	{
        		/*
        		 * 3월 25일 요청사항에 의해 티켓 구매시 해피콜 확인.
        		 * 2. 해피콜 미완료된 상태에서 구매시 팝업 표시
        		 *   해피콜 미완료인 경우, 각 상품 구입/가입시 -> 정확히 말하면 "1일권 구입, 월정액 가입, 1년 약정 가입" 팝업에서 선택시
        		 *   오류코드 : M-53002
        		 *   해피콜이 미완료되어 구매(가입)하실 수 없습니다.
        		 *   고객센터(국번없이 101)로 문의 바랍니다.
        		 *   라는 팝업이 표시해주세요.
        		 */

        		if ( SIMClientHandlerLGU.happy_call == false )
        		{
        			AlertDialog ad = new AlertDialog.Builder(this.getContext()).create();  
        			ad.setCancelable(false); // This blocks the 'BACK' button  
        			ad.setMessage("이용권을 구매할 수 없습니다.\n\n"
        					+ "오류코드 : M-53002\n" 
        					+ "해피콜이 미완료되어 구매(가입)하실 수 없습니다.\n"
        					+ DataHandler.telephone);

        			// 오류코드 및 메시지 참조방식 예제.
        			// ad.setMessage("서비스를 이용하실 수 없습니다.\n\n"
        			//		+ "오류코드 : M-" + String.format("%05d", SIMClientHandlerLGU.return_code) + "\n" 
        			//		+ "오류메시지 : " + SIMClientHandlerLGU.return_message);  

        			ad.setButton( AlertDialog.BUTTON_POSITIVE, "확 인", new DialogInterface.OnClickListener() {  
        				@Override  
        				public void onClick(DialogInterface dialog, int which) {  
        					dialog.dismiss();                      
        					//ATVKaraokeActivity.this.finish();
        				}  
        			});  
        			ad.show(); 		                
        			return;
        		}
        	}
        	
        	
        	if ( curStep == 1 )		// 이용권 구매
       		{
       			/* 오류 보정 루틴 */	
       			String system_password = "";
       			String temp_password = new String( SetopHandler.getInstance().getSecretNum() );
       			//Settings.System.getString( null, "dtv.purchase_password");
       			for ( int i = 0; i < 4 - temp_password.length(); i++)
       				system_password += "0";
       			system_password += temp_password;

       			/* 문자열 비교 루틴   */	
       			//        		String input_password = "";
       			//        		input_password.format("%04d", password );

       			// password comapre 
       			if ( system_password.equals(password)) 
       			{
       				curStep++;
       				drawBackground( curStep );
       			}
       			else
       			{
       				wrong_password = true;
       				reset = true;
       				drawBackground( curStep );
       			}
       		}
        	else
        	{
       			   curStep++;
       			   if ( curStep == 1 )
       			   {
       				   DataHandler.ticket_type = wparam ;
       				 
       				   switch ( wparam )
       				   {
       				   case 0:  DataHandler.ticket_name = new String("\"1일 이용권\"" );break;
       				   case 1:  DataHandler.ticket_name = new String("\"월 이용권\"" );break;
       				   case 2:  DataHandler.ticket_name = new String("\"1년 약정 이용권\"" );break;
       				   default :
       					   DataHandler.ticket_name = new String("Unknown Product"); break;
       				   }
       				   
       				   
       			   }
       			   drawBackground(curStep);
        	}
        }
        invalidate();
    }

}


