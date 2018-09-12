package kr.kymedia.kykaraoke.tv.data;

public class SongItem {
	public String song_id;
	public String title;
	public String artist;
	public String mark_favorite;

	public String toString() {
		//return super.toString();
		String ret = super.toString();
		ret += "\n[song_id]" + song_id;
		ret += "\n[title]" + title;
		ret += "\n[reg_date]" + artist;
		ret += "\n[mark_favorite]" + mark_favorite;
		return ret;
	}
}