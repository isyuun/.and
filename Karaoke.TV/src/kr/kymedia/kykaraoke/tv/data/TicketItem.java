package kr.kymedia.kykaraoke.tv.data;

import android.widget.Button;

import kr.kymedia.kykaraoke.tv.api._Const;

public class TicketItem implements _Const {
	// private final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();
@Override
	public String toString() {
		//return super.toString();
		return _toString();
	}

	private String _toString() {
		String ret = getClass().getSimpleName() + '@' + Integer.toHexString(hashCode());
		ret += "{";
		ret += ",p_passtype:" + p_passtype;
		ret += ",id_product:" + id_product;
		ret += ",product_name:" + product_name;
		ret += ",product_type:" + product_type;
		ret += ",product_term:" + product_term;
		ret += ",product_desc:" + product_desc;
		ret += ",product_info:" + product_info;
		ret += ",product_note_01:" + product_note_01;
		ret += ",product_note_02:" + product_note_02;
		ret += ",product_image:" + product_image;
		ret += ",service_item_id:" + service_item_id;
		ret += ",price:" + price;
		ret += ",real_price:" + real_price;
		ret += ",start_date:" + start_date;
		ret += ",end_date:" + end_date;
		ret += ",button:" + button;
		ret += "}";
		return ret;
	}

	public TicketItem() {
		p_passtype = "";
		product_type = PRODUCT_TYPE.NONE;
	}

	/**
	 * 이용권구분 - 절대공백제거
	 */
	public String p_passtype;
	public String id_product;
	public String product_name;
	public PRODUCT_TYPE product_type;
	public int product_term;
	public String product_desc;
	public String product_info;
	public String product_note_01;
	public String product_note_02;
	public String product_image;
	public String service_item_id;
	public int price;
	public int real_price;
	public String start_date;
	public String end_date;
	public Button button;
}