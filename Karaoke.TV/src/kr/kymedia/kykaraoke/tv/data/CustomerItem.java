package kr.kymedia.kykaraoke.tv.data;

public class CustomerItem {
	public String id;
	public String title;
	public String reg_date;
	public String term_date;
	public String e_stats;

	@Override
	public String toString() {
		//return super.toString();
		String ret = super.toString();
		ret += "\n[id]" + id;
		ret += "\n[title]" + title;
		ret += "\n[reg_date]" + reg_date;
		ret += "\n[term_date]" + term_date;
		ret += "\n[e_stats]" + e_stats;
		return ret;
	}
}
