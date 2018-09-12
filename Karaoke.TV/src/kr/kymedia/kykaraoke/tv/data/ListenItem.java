package kr.kymedia.kykaraoke.tv.data;

public class ListenItem {
	public String url_record;
	public String url_profile;
	public String record_id;
	public String song_id;
	public String title;
	public String artist;
	public String nickname;
	public String heart;
	public String hit;
	public String reg_date;

	@Override
	public String toString() {
		//return super.toString();
		String ret = super.toString();
		ret += "\n[url_record]" + url_record;
		ret += "\n[url_profile]" + url_profile;
		ret += "\n[record_id]" + record_id;
		ret += "\n[song_id]" + song_id;
		ret += "\n[title]" + title;
		ret += "\n[artist]" + artist;
		ret += "\n[nickname]" + nickname;
		ret += "\n[heart]" + heart;
		ret += "\n[hit]" + hit;
		ret += "\n[reg_date]" + reg_date;
		return ret;
	}
}
