/*
 * Copyright 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * 2015 All rights (c)KYGroup Co.,Ltd. reserved.
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * <p/>
 * project	:	Karaoke.TV
 * filename	:	Main29.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv
 *    |_ Main29.java
 * </pre>
 */

package kr.kymedia.kykaraoke.tv;

import android.graphics.drawable.Drawable;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;

import kr.kymedia.karaoke.widget.util.WidgetUtils;
import kr.kymedia.kykaraoke.api.IKaraokeTV;
import kr.kymedia.kykaraoke.tv.app._Thread;
import kr.kymedia.kykaraoke.tv.data.ListenItem;
import kr.kymedia.kykaraoke.tv.data.SongItem;

/**
 * <pre>
 * 반주곡/녹음곡 리스트 고도화
 *  리스트배경처리
 *  반주곡리스트(KP전문)
 *  녹음곡리스트(KP전문)
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2015. 7. 14.
 */
class Main2XXXX extends Main2XXX {
	private final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	private String _toString() {

		return (BuildConfig.DEBUG ? __CLASSNAME__ : getClass().getSimpleName()) + '@' + Integer.toHexString(hashCode());
	}

	@Override
	protected String getMethodName() {
		String name = Thread.currentThread().getStackTrace()[3].getMethodName();
		// int line = Thread.currentThread().getStackTrace()[3].getLineNumber();
		// name = String.format("line:%d - %s() ", line, name);
		name += "() ";
		return name;
	}

	/**
	 * 반주곡/녹음곡리스트
	 *
	 * @see kr.kymedia.kykaraoke.tv.Main2#KP(int, String, String, String)
	 * @see kr.kymedia.kykaraoke.tv.Main2#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3X#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3XXX#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3XXXX#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3XXXXX#KP(android.os.Message)
	 */
	@Override
	protected void KP(Message msg) {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")) + ":" + msg);
		super.KP(msg);

		int state = msg.getData().getInt("state");

		switch (state) {
			case COMPLETE_SONG_LIST: // 반주곡 리스트
				//if (BuildConfig.DEBUG) _LOG.wtf(_toString(), getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")) + ":" + msg);
				postDelayed(showSingList, 100);
				break;
			case COMPLETE_LISTEN_LIST: // 녹음곡 리스트
				//if (BuildConfig.DEBUG) _LOG.wtf(_toString(), getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")) + ":" + msg);
				postDelayed(showListenList, 100);
				break;
			case COMPLETE_CUSTOMER_LIST: // 공지사항 or 이용안내 리스트
				break;
			case COMPLETE_CUSTOMER_LIST_DETAIL: // 공지사항 or 이용안내 상세 정보
				break;
		}
	}

	@Override
	protected void addView(ViewGroup parent, int layout, int id) {
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		ViewGroup view = (ViewGroup) inflate(layout, null);
		view.setLayoutParams(params);
		addView(parent, view);
	}

	@Override
	protected void removeAllViewsContent() {
		super.removeAllViewsContent();
	}

	@Override
	protected void addViewSingMenu() {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		super.addViewSingMenu();
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	private void addKaraokeViewSongList() {
		addView(layout_content, R.layout.sing_list2, R.id.sing_list);
		inflateListSong(R.id.sing_list);
		resetSongList();
		unselectSongList();
	}

	@Override
	protected void addViewSingList() {

		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		addKaraokeViewSongList();
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	@Override
	protected void addViewMyList() {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		addKaraokeViewSongList();
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	Drawable draNote;
	Drawable draFavor;
	Drawable draNew;

	@Override
	protected void inflateListSong(int res) {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[ST]");

		sing_list = (ViewGroup) layout_content.findViewById(res);
		sing_list.setId(res);

		if (draNote == null) {
			draNote = WidgetUtils.getDrawable(getApplicationContext(), R.drawable.common_bullet_note_on);
		}

		if (draFavor == null) {
			draFavor = WidgetUtils.getDrawable(getApplicationContext(), R.drawable.common_bullet_favor_off);
		}

		if (draNew == null) {
			draNew = WidgetUtils.getDrawable(getApplicationContext(), R.drawable.common_bullet_new_off);
		}

		sing_line.clear();
		Collections.addAll(sing_line
				, new SingLine((ViewGroup) sing_list.findViewById(R.id.sing_item_1))
				, new SingLine((ViewGroup) sing_list.findViewById(R.id.sing_item_2))
				, new SingLine((ViewGroup) sing_list.findViewById(R.id.sing_item_3))
				, new SingLine((ViewGroup) sing_list.findViewById(R.id.sing_item_4))
				, new SingLine((ViewGroup) sing_list.findViewById(R.id.sing_item_5))
				, new SingLine((ViewGroup) sing_list.findViewById(R.id.sing_item_6))
		);

		int r = 1;
		int c = 1;
		if (sing_list instanceof GridLayout) {
			r = ((GridLayout) sing_list).getRowCount();
			c = ((GridLayout) sing_list).getColumnCount();
		}
		int w = (layout_content.getWidth() / c);
		int h = (layout_content.getHeight() / r);

		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[WH]" + layout_content.getWidth() + "," + layout_content.getHeight() + "->" + +w + "," + h + ":" + r + "," + c);

		for (SingLine line : sing_line) {
			if (line.layout instanceof GridLayout) {
				GridLayout.LayoutParams params = (GridLayout.LayoutParams) line.layout.getLayoutParams();
				params.width = GridLayout.LayoutParams.MATCH_PARENT;
				params.height = h - params.topMargin - params.bottomMargin;
				line.layout.setLayoutParams(params);
				line.layout.setVisibility(View.INVISIBLE);
			}
			line.dra_note = draNote;
		}

		txtPage = (TextView) findViewById(R.id.txt_song_page);

		if (sing_list != null) {
			sing_list.setFocusable(true);
			sing_list.setFocusableInTouchMode(false);
			sing_list.setClickable(false);
			// requestFocus(sing_list);
		}

		for (SingLine line : sing_line) {
			// 포커스설정
			line.layout.setFocusable(false);
			line.layout.setFocusableInTouchMode(false);
			// 포커스해제
			line.layout.clearFocus();
			line.layout.setSelected(false);
			line.layout.setPressed(false);
		}

		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[ED]");
	}

	@Override
	public void displayMenuSing(int keyID) {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + REMOTE_STATE.get(keyID) + ":" + remote.isGenre());

		if (remote.m_iState == STATE_SING_MENU) {
			if (keyID == REMOTE_LEFT || keyID == REMOTE_RIGHT) {
				//post(hideSingList);
				hideSingList();
			}
		}

		super.displayMenuSing(keyID);

		SingLine line = null;
		if (null != sing_line && sing_line.size() > 0) {
			line = sing_line.get(0);
		}

		// 노래부르기->인기장르
		if (remote.m_iMenuMainFocus == 1 && remote.m_iMenuSingFocus == 3) {
			if (null != line) {
				line.layout.setVisibility(View.INVISIBLE);
			}
		} else {
			if (null != line) {
				line.layout.setVisibility(View.VISIBLE);
			}
		}

		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + REMOTE_STATE.get(keyID) + ":" + remote.isGenre());
	}

	TextView txtPage;

	@Override
	protected void setPageTextSongList() {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + "[ST]" + remote.isGenre());
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + "page " + m_iCurrentViewSongListPage + "/" + m_iTotalSongListPage);
		try {
			int count = 6;

			if (isGenre()) {
				count = 5;
			}

			m_iSongItemCount = (m_iCurrentSongListPage - 1) * count;

			if (txtPage != null) {
				txtPage.setText("page " + (m_iCurrentViewSongListPage) + "/" + (m_iTotalSongListPage));
				if (m_iTotalSongListPage > 0) {
					txtPage.setVisibility(View.VISIBLE);
				} else {
					txtPage.setVisibility(View.INVISIBLE);
				}
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + "[ED]" + remote.isGenre());
	}

	@Override
	protected void resetSongList() {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ST]");
		super.resetSongList();
		for (SingLine line : sing_line) {
			switch (remote.m_iMenuSingFocus) {
				case 1:
					line.txt_rank.setVisibility(View.VISIBLE);
					break;
				case 2:
					line.txt_rank.setVisibility(View.GONE);
					break;
				case 3:
					line.txt_rank.setVisibility(View.GONE);
					break;
			}
			line.txt_rank.setText("");
			line.ico_note.setVisibility(View.INVISIBLE);
			line.txt_number.setText("");
			line.txt_title.setText("");
			line.txt_singer.setText("");
		}
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ED]");
	}

	/**
	 * <pre>
	 * 젖같은거만들어서는지랄이네.
	 * </pre>
	 */
	protected void unselectListenList() {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + "[ST]");
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + remote.m_iListenListFocusX + "," + remote.m_iListenListFocusY);

		for (ListenLine line : listen_line) {
			if (line.img_focus != null) {
				line.img_focus.setVisibility(View.INVISIBLE);
			}
			//마퀴처리...막어...존나버벅된다.
			//if (line.txt_listen_title != null) {
			//	line.txt_listen_title.setSelected(false);
			//}
		}

		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + "[ED]");
	}

	private void selectListenList() {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + "[ST]");
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + remote.m_iListenListFocusX + "," + remote.m_iListenListFocusY);

		if (remote.m_iListenListFocusY > 0) {
			int focus = remote.m_iListenListFocusX + ((remote.m_iListenListFocusY - 1) * 4) - 1;
			if (listen_line != null && focus > -1 && focus < listen_line.size()) {
				if (listen_line.get(focus).img_focus != null) {
					listen_line.get(focus).img_focus.setVisibility(View.VISIBLE);
				}
				//마퀴처리...막어...존나버벅된다.
				//if (listen_line.get(index).txt_listen_title != null) {
				//	listen_line.get(index).txt_listen_title.setSelected(true);
				//}
			}
		} else {
			unselectListenList();
		}

		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + "[ED]");
	}

	@Override
	public void exitListListen() {
		super.exitListListen();
		unselectListenList();
	}

	private final Runnable setSongListIcons = new Runnable() {

		@Override
		public void run() {
			setSongListIcons();
		}
	};

	private void setSongListIcons() {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[ST]");

		int index = 0;
		for (SingLine line : sing_line) {
			//test
			//if (null != line.ico_note) {
			//	line.ico_note.setVisibility(View.INVISIBLE);
			//	continue;
			//}

			String isFavor = "N";

			if (index < mSongFavors.size()) {
				isFavor = mSongFavors.get(index);
			}

			if (("Y").equalsIgnoreCase(isFavor)) {
				// line.setImageResource(R.drawable.common_bullet_favor_off);
				line.setImageDrawable(draFavor);
			} else {
				switch (remote.m_iMenuMainFocus) {
					// 마이노래방
					case 3:
						// line.setImageResource(R.drawable.common_bullet_note_off);
						line.setImageDrawable(draNote);
						// switch (remote.m_iMenuMyFocus) {
						// case 1:
						// break;
						// case 2:
						// break;
						// }
						break;
					// 노래부르기
					default:
						switch (remote.m_iMenuSingFocus) {
							// case 1:
							// break;
							case 2:
								// line.setImageResource(R.drawable.common_bullet_new_off);
								line.setImageDrawable(draNew);
								break;
							// case 3:
							// break;
							default:
								// line.setImageResource(R.drawable.common_bullet_note_off);
								line.setImageDrawable(draNote);
								break;
						}
						break;
				}
			}
			index++;
		}
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[ED]");
	}

	private final Runnable moveSongListLines = new Runnable() {

		@Override
		public void run() {
			moveSongListLines();
		}
	};

	private void moveSongListLines() {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + remote.isGenre());

		int index = 0;
		int idx = 0;

		mSongFavors.clear();

		for (SingLine line : sing_line) {
			try {

				switch (remote.m_iMenuMainFocus) {
					// 마이노래방
					case 3:
						line.txt_rank.setVisibility(View.GONE);
						// switch (remote.m_iMenuMyFocus) {
						// case 1:
						// break;
						// case 2:
						// break;
						// }
						break;
					// 노래부르기
					default:
						switch (remote.m_iMenuSingFocus) {
							case 1:
								line.txt_rank.setVisibility(View.VISIBLE);
								break;
							case 2:
								line.txt_rank.setVisibility(View.GONE);
								break;
							case 3:
								line.txt_rank.setVisibility(View.GONE);
								break;
						}
						break;
				}

				index = (idx + (m_iCurrentSongListPage - 1) * 6);

				if (isGenre()) {
					index = (idx + (m_iCurrentSongListPage - 1) * 5) - 1;
					if (idx == 0) {
						line.layout.setVisibility(View.INVISIBLE);
						idx++;
						continue;
					}
				}

				//븅신개삽지랄
				//if (m_bIsGenre && idx == 5) {
				//	line.layout.setVisibility(View.GONE);
				//	continue;
				//}

				SongItem item = null;

				if (index < mSongItems.size()) {
					item = mSongItems.get(index);
				}

				if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + index + "-" + item);

				if (item != null) {
					line.layout.setVisibility(View.VISIBLE);

					if (m_iRequestPage == 2) {
						line.txt_rank.setText((index + 1 + 60) + "위");
					} else {
						line.txt_rank.setText((index + 1) + "위");
					}

					line.txt_number.setText(item.song_id);
					line.txt_title.setText(item.title);
					line.txt_singer.setText(item.artist);
					line.ico_note.setVisibility(View.VISIBLE);

					mSongFavors.add(item.mark_favorite);

				} else {
					line.layout.setVisibility(View.INVISIBLE);
					//line.txt_rank.setText("");
					//line.txt_number.setText("");
					//line.txt_title.setText("");
					//line.txt_singer.setText("");
					//line.ico_note.setVisibility(View.INVISIBLE);
				}

				idx++;
			} catch (Exception e) {
				if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
			}
		}

		m_iSongItemCount = idx;

		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + remote.isGenre());
	}

	@Override
	public void moveSingListPage() {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + remote.isGenre());
		moveSongListPage();
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + remote.isGenre());
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main2#moveMyListPage()
	 */
	@Override
	public void moveMyListPage() {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + remote.isGenre());
		moveSongListPage();
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + remote.isGenre());
	}

	private void moveSongListPage() {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + remote.isGenre());

		// if (BuildConfig.DEBUG) _LOG.i(_toString(), "moveSongListPage >");

		setPageTextSongList();

		//hideSingList();

		post(moveSongListLines);

		if (P_APPNAME_SKT_BOX.equalsIgnoreCase(m_strSTBVender)) {
			postDelayed(setSongListIcons, 100);
		} else {
			post(setSongListIcons);
		}

		//showSingList();

		// if (BuildConfig.DEBUG) _LOG.i(_toString(), "moveSongListPage <");

		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + remote.isGenre());
	}

	@Override
	public void displayDetailSong(int keyID) {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + "[ST]" + REMOTE_STATE.get(keyID) + ":" + remote.isGenre());

		super.displayDetailSong(keyID);

		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + "[ED]" + REMOTE_STATE.get(keyID) + ":" + remote.isGenre());
	}

	@Override
	public void displayListSing(int keyID) {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + "[ST]" + REMOTE_STATE.get(keyID) + ":" + remote.isGenre());

		displayListSong(keyID);

		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + "[ED]" + REMOTE_STATE.get(keyID) + ":" + remote.isGenre());
	}

	@Override
	public void displayListMy(int keyID) {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + "[ST]" + REMOTE_STATE.get(keyID) + ":" + remote.isGenre());

		displayListSong(keyID);

		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + "[ED]" + REMOTE_STATE.get(keyID) + ":" + remote.isGenre());
	}

	protected void displayListSong(int keyID) {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + "[ST]" + REMOTE_STATE.get(keyID) + ":" + remote.isGenre());

		// if (BuildConfig.DEBUG) _LOG.i(_toString(), getMethodName() + layoutList);
		// if (BuildConfig.DEBUG) _LOG.i(_toString(), "keyID=" + REMOTE_STATE.get(keyID) + "/curPage=" + m_iCurrentViewSongListPage + "/toPage=" + m_iTotalSongListPage);
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + "/curPage=" + m_iCurrentViewSongListPage + "/toPage=" + m_iTotalSongListPage);

		if (mSongItems == null || mSongItems.size() == 0) {
			Log.wtf(_toString(), "displayListSong()" + "[NO]" + REMOTE_STATE.get(keyID) + ":" + remote.isGenre());
			remote.m_iSongListFocus = 0;
			//인기장르
			if (remote.m_iMenuSingFocus == 3) {
				remote.m_iSongListFocus = 1;
			}
			return;
		}

		if (keyID == REMOTE_UP) {
			if (remote.m_iSongListFocus == 0) {
				exitListSong();
				return;
			}
		}

		int request/* = REQUEST_SONG_LIST*/;
		String OP = null;
		String M1 = null;
		String M2 = null;

		switch (remote.m_iMenuMainFocus) {
			// 마이노래방
			case 3:
				request = REQUEST_SONG_LIST;
				OP = KP_3000;
				switch (remote.m_iMenuMyFocus) {
					case 1:
						M1 = M1_MENU_MYLIST;
						M2 = M2_MYLIST_RECENT;
						break;
					case 2:
						M1 = M1_MENU_MYLIST;
						M2 = M2_MYLIST_FAVORITE;
						break;
				}
				break;
			// 노래부르기
			default:
				request = REQUEST_SONG_LIST;
				OP = KP_1000;
				switch (remote.m_iMenuSingFocus) {
					case 1:
						M1 = M1_MENU_SING;
						M2 = M2_SING_HOT;
						break;
					case 2:
						M1 = M1_MENU_SING;
						M2 = M2_SING_RECENT;
						break;
					case 3:
						switch (remote.m_iMenuSingGenreFocus) {
							case 1:
								M1 = M1_SING_GENRE;
								M2 = M2_GENRE_1;
								break;
							case 2:
								M1 = M1_SING_GENRE;
								M2 = M2_GENRE_2;
								break;
							case 3:
								M1 = M1_SING_GENRE;
								M2 = M2_GENRE_3;
								break;
							case 4:
								M1 = M1_SING_GENRE;
								M2 = M2_GENRE_4;
								break;
							case 5:
								M1 = M1_SING_GENRE;
								M2 = M2_GENRE_5;
								break;
							case 6:
								M1 = M1_SING_GENRE;
								M2 = M2_GENRE_6;
								break;
							default:
								break;
						}
						break;
				}
				break;
		}

		if (keyID == REMOTE_RIGHT) {
			if (m_iCurrentViewSongListPage < m_iTotalSongListPage) {
				if (BuildConfig.DEBUG) Log.i(_toString(), "next");
				remote.m_iSongListFocus = 1;
				//인기장르
				if (remote.m_iMenuSingFocus == 3) {
					remote.m_iSongListFocus = 2;
				}

				m_iCurrentSongListPage++;
				m_iCurrentViewSongListPage++;

				// 1, 21, 31... 페이지면 새로 요청
				if (m_iCurrentViewSongListPage % 10 == 1) {
					m_iRequestPage++;
					m_iCurrentSongListPage = 1;
					KP(request, OP, M1, M2);
				} else {
					moveSingListPage();
				}
			} else {
				// 마지막 페이지에서 RIGHT키 입력 시 1페이지로 이동
				if (m_iTotalSongListPage > 1) {
					remote.m_iSongListFocus = 1;
					//인기장르
					if (remote.m_iMenuSingFocus == 3) {
						remote.m_iSongListFocus = 2;
					}

					m_iCurrentViewSongListPage = 1;
					m_iCurrentSongListPage = 1;
					int temp = m_iRequestPage;
					m_iRequestPage = 1;

					// 이전 Request했던 페이지와 다른 데이터(새로받음)이면 KP 새로 요청
					if (temp != m_iRequestPage) {
						KP(request, OP, M1, M2);
					}

					moveSingListPage();
				}
			}
		} else if (keyID == REMOTE_LEFT) {
			if (m_iCurrentViewSongListPage > 1) {
				if (BuildConfig.DEBUG) Log.i(_toString(), "prev");
				remote.m_iSongListFocus = 1;
				//인기장르
				if (remote.m_iMenuSingFocus == 3) {
					remote.m_iSongListFocus = 2;
				}

				m_iCurrentSongListPage--;
				m_iCurrentViewSongListPage--;

				// 10, 20, 30... 페이지면 새로 요청
				if (m_iCurrentViewSongListPage % 10 == 0) {
					m_iRequestPage--;
					m_iCurrentSongListPage = 10;
					KP(request, OP, M1, M2);
				} else {
					moveSingListPage();
				}
				// 1페이지에서 LEFT키 입력 : 마지막 페이지로 이동
			} else {
				if (m_iTotalSongListPage > 1) {
					remote.m_iSongListFocus = 1;
					//인기장르
					if (remote.m_iMenuSingFocus == 3) {
						remote.m_iSongListFocus = 2;
					}

					m_iCurrentViewSongListPage = m_iTotalSongListPage;
					m_iCurrentSongListPage = m_iTotalSongListPage % 10;
					if (m_iCurrentSongListPage == 0) {
						m_iCurrentSongListPage = 10;
					} else {
						int temp = m_iRequestPage;
						m_iRequestPage = (m_iTotalSongListPage / 10) + 1;
						// 이전 Request했던 페이지와 다른 데이터(새로받음)이면 KP 새로 요청
						if (temp != m_iRequestPage) {
							KP(request, OP, M1, M2);
						}
					}
					moveSingListPage();
				}
			}
		}

		if (keyID != REMOTE_NONE && (keyID != REMOTE_UP || keyID != REMOTE_DOWN)) {
			unselectSongList();
		}

		selectSongList();

		setListBackground(getMethodName());

		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + "[ED]" + REMOTE_STATE.get(keyID) + ":" + remote.isGenre());
	}

	/**
	 * <pre>
	 * 젖같은거만들어서는지랄이네.
	 * </pre>
	 */
	@Override
	protected void unselectSongList() {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + "[ST]");
		super.unselectSongList();

		// if (BuildConfig.DEBUG) _LOG.e(_toString(), getMethodName() + sing_list);

		for (SingLine line : sing_line) {
			line.layout.setSelected(false);
			line.layout.setPressed(false);
			//if (line.img_focus != null) {
			//	line.img_focus.setVisibility(View.INVISIBLE);
			//}
		}

		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + "[ED]");
	}

	private void selectSongList() {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + "[ST]");

		if (remote.m_iSongListFocus > 0) {
			int index = remote.m_iSongListFocus - 1;
			if (sing_line != null && index > -1 && index < sing_line.size()) {
				sing_line.get(index).layout.setPressed(false);
				sing_line.get(index).layout.setSelected(true);
				// sing_line.get(index).txt_rank.setTextColor(getResources().getColor(R.color.solid_text_on));
				// sing_line.get(index).txt_number.setTextColor(getResources().getColor(R.color.solid_text_on));
				//sing_line.get(index).img_focus.setVisibility(View.VISIBLE);
			}
		} else {
			unselectSongList();
		}

		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + "[ED]");
	}

	protected void hideListArrows() {
		if (mArrowListLeft != null && mArrowListLeft.getVisibility() != View.INVISIBLE) {
			mArrowListLeft.setVisibility(View.INVISIBLE);
		}
		if (mArrowListRight != null && mArrowListRight.getVisibility() != View.INVISIBLE) {
			mArrowListRight.setVisibility(View.INVISIBLE);
		}
	}

	protected void showListArrows() {
		if (mArrowListLeft != null && mArrowListLeft.getVisibility() != View.VISIBLE) {
			mArrowListLeft.setVisibility(View.VISIBLE);
		}
		if (mArrowListRight != null && mArrowListRight.getVisibility() != View.VISIBLE) {
			mArrowListRight.setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void setListBackground(String method) {
		super.setListBackground(method);

		int count = 0;

		boolean list = false;

		int visibility = View.VISIBLE;
		switch (remote.m_iMenuMainFocus) {
			// 노래부르기
			case 1:
				visibility = (View.VISIBLE);
				break;
			// 녹음곡감상
			case 2:
				visibility = (View.INVISIBLE);
				break;
			// 마이노래방
			case 3:
				visibility = (View.VISIBLE);
				break;
			// 노래방샵
			case 4:
				visibility = (View.INVISIBLE);
				break;
			// 고객센터
			case 5:
				visibility = (View.VISIBLE);
				break;
			default:
				break;
		}

		switch (remote.getState()) {
			// 노래부르기
			case STATE_SONG_LIST:
				list = true;
				count = mSongItems.size();
				// switch (remote.m_iMenuSingFocus) {
				// case 1:
				// break;
				// case 2:
				// break;
				// case 3:
				// break;
				// }
				break;
			// 녹음곡감상
			case STATE_LISTEN_LIST:
				list = true;
				count = mListenItems.size();
				break;
			// 마이노래방->최근부른곡/애창곡
			case STATE_MY_LIST:
				list = true;
				count = mSongItems.size();
				break;
			//마이노래방->녹음곡
			case STATE_MY_RECORD_LIST:
				list = true;
				count = mListenItems.size();
				break;
			// 노래방샵
			// case STATE_CUSTOMER_LIST:
			// visibility = (View.INVISIBLE);
			// list = false;
			// break;
			// 고객센터
			case STATE_CUSTOMER_LIST:
			case STATE_CUSTOMER_LIST_EVENT:
				switch (remote.m_iMenuCustomerFocus) {
					case 1:
						list = true;
						count = mCustomerItems.size();
						break;
					case 2:
						list = true;
						count = mCustomerItems.size();
						break;
					case 3:
						list = true;
						count = mCustomerItems.size();
						break;
					case 4:
						list = false;
						count = 0;
						break;
					case 5:
						list = false;
						count = 0;
						break;
					default:
						list = false;
						count = 0;
						break;
				}
				break;
			// 검색
			case STATE_SEARCH_LIST:
				visibility = (View.VISIBLE);
				list = true;
				count = mSearchItems.size();
				break;
			default:
				list = false;
				count = 0;
				break;
		}

		if (mLayoutListBack != null && mLayoutListBack.getVisibility() != visibility) {
			mLayoutListBack.setVisibility(visibility);
		}

		if (list && count > 1) {
			showListArrows();
		} else {
			hideListArrows();
		}

		if (BuildConfig.DEBUG) Log.wtf(_toString(), getMethodName() + method + ":" + remote.getState() + ":" + remote.m_iMenuMainFocus + ":" + list + ":" + count);
	}

	@Override
	public void displayListCustomer(int keyID) {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + ":remote.m_iMenuCustomerFocus:" + remote.m_iMenuCustomerFocus + ":m_iEnterCustomerMenu:" + m_iEnterCustomerMenu + ":m_bDisplayingCustomerDetail:" + m_bDisplayingCustomerDetail);

		if (m_iEnterCustomerMenu == CUSTOMER_ENTER_KEY && (mCustomerItems == null || mCustomerItems.size() == 0)) {
			Log.wtf(_toString(), "displayListCustomer()" + "[NO]" + REMOTE_STATE.get(keyID) + ":remote.m_iMenuCustomerFocus:" + remote.m_iMenuCustomerFocus + ":m_iEnterCustomerMenu:" + m_iEnterCustomerMenu + ":m_bDisplayingCustomerDetail:" + m_bDisplayingCustomerDetail);
			remote.m_iCustomerListFocus = 0;
			resetCustomerList();
			return;
		}

		super.displayListCustomer(keyID);
		setListBackground(getMethodName());
	}

	@Override
	public void displayListSearch(int keyID) {

		super.displayListSearch(keyID);
		setListBackground(getMethodName());
	}

	/**
	 * @param keyID REMOTE_NONE:제자리
	 * @return
	 */
	@Override
	protected int setSelectedOkCancel(final int ok, final int cancel, final int val, final int keyID) {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + val + ":" + REMOTE_STATE.get(keyID));

		int ret = val;

		Button btnOK = (Button) findViewById(ok);
		Button btnCancel = (Button) findViewById(cancel);

		if (btnOK == null || btnCancel == null) {
			return ret;
		}

		if (keyID == REMOTE_LEFT || keyID == REMOTE_RIGHT) {
			if (ret == POPUP_OK) {
				ret = POPUP_CANCEL;
			} else {
				ret = POPUP_OK;
			}
		}

		if (ret == POPUP_OK) {
			btnOK.setSelected(true);
			btnCancel.setSelected(false);
		} else {
			btnOK.setSelected(false);
			btnCancel.setSelected(true);
		}

		return ret;
	}

	/**
	 * @param keyID REMOTE_NONE:제자리
	 * @return
	 */
	@Override
	protected int setSelectedOkBackCancel(final int ok, final int back, final int cancel, final int val, final int keyID) {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + val + ":" + REMOTE_STATE.get(keyID));

		int ret = val;

		Button btnOK = (Button) findViewById(ok);
		Button btnBack = (Button) findViewById(back);
		Button btnCancel = (Button) findViewById(cancel);

		if (btnOK == null || btnBack == null || btnCancel == null) {
			return ret;
		}

		if (keyID == REMOTE_RIGHT) {
			if (ret == POPUP_OK) {
				ret = POPUP_BACK;
			} else if (ret == POPUP_BACK) {
				ret = POPUP_CANCEL;
			} else {
				ret = POPUP_OK;
			}
		}

		if (keyID == REMOTE_LEFT) {
			if (ret == POPUP_OK) {
				ret = POPUP_CANCEL;
			} else if (ret == POPUP_BACK) {
				ret = POPUP_OK;
			} else {
				ret = POPUP_BACK;
			}
		}

		if (ret == POPUP_OK) {
			btnOK.setSelected(true);
			btnBack.setSelected(false);
			btnCancel.setSelected(false);
		} else if (ret == POPUP_BACK) {
			btnOK.setSelected(false);
			btnBack.setSelected(true);
			btnCancel.setSelected(false);
		} else {
			btnOK.setSelected(false);
			btnBack.setSelected(false);
			btnCancel.setSelected(true);
		}

		return ret;
	}

	private int popup_type = POPUP_NONE;

	@Override
	protected void ShowMessageExit(int state) {
		popup_type = POPUP_EXIT;
		super.ShowMessageExit(state);
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "onKeyDown()" + ":popup_type:" + popup_type + ":m_bIsExit:" + m_bIsExit);
	}

	@Override
	public void ShowMessageAlert(String message) {
		popup_type = POPUP_NONE;
		super.ShowMessageAlert(message);
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "onKeyDown()" + ":popup_type:" + popup_type + ":m_bIsExit:" + m_bIsExit);
	}

	@Override
	protected void ShowMessageCommon(int close, String title, String message) {
		popup_type = POPUP_NONE;
		super.ShowMessageCommon(close, title, message);
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "onKeyDown()" + ":popup_type:" + popup_type + ":m_bIsExit:" + m_bIsExit);
	}

	@Override
	protected void ShowMessageNotResponse(String title, String message) {
		popup_type = POPUP_NONE;
		super.ShowMessageNotResponse(title, message);
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "onKeyDown()" + ":popup_type:" + popup_type + ":m_bIsExit:" + m_bIsExit);
	}

	@Override
	public void ShowMessageOk(int type, String title, String message) {
		popup_type = POPUP_NONE;
		super.ShowMessageOk(type, title, message);
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "onKeyDown()" + ":popup_type:" + popup_type + ":m_bIsExit:" + m_bIsExit);
	}

	@Override
	protected void ShowMessageOkCancel(String title, String message) {
		popup_type = POPUP_NONE;
		super.ShowMessageOkCancel(title, message);
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "onKeyDown()" + ":popup_type:" + popup_type + ":m_bIsExit:" + m_bIsExit);
	}

	//@Override
	//protected void HideMessageCommon() {
	//	super.HideMessageCommon();
	//	if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "onKeyDown()" + ":popup_type:" + popup_type + ":m_bIsExit:" + m_bIsExit);
	//}
	//
	//@Override
	//public void HideMessageOk() {
	//	popup_type = POPUP_NONE;
	//	super.HideMessageOk();
	//	if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "onKeyDown()" + ":popup_type:" + popup_type + ":m_bIsExit:" + m_bIsExit);
	//}
	//
	//@Override
	//protected void HideMessageOkCancel() {
	//	popup_type = POPUP_NONE;
	//	super.HideMessageOkCancel();
	//	if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "onKeyDown()" + ":popup_type:" + popup_type + ":m_bIsExit:" + m_bIsExit);
	//}

	@Override
	protected void exitPopups() {
		popup_type = POPUP_NONE;
		super.exitPopups();
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "onKeyDown()" + ":popup_type:" + popup_type + ":m_bIsExit:" + m_bIsExit);
	}

	@Override
	protected void setSelectedMessageOkCancel(int keyCode) {
		int keyID = REMOTE_NONE;

		if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			keyID = REMOTE_LEFT;
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			keyID = REMOTE_RIGHT;
		}

		m_iMessageOkCancelFocusX = setSelectedOkCancel(R.id.btn_message_okcancel_ok, R.id.btn_message_okcancel_cancel, m_iMessageOkCancelFocusX, keyID);

		m_bIsExit = false;

		if (popup_type == POPUP_EXIT) {
			if (m_iMessageOkCancelFocusX == POPUP_OK) {
				m_bIsExit = true;
			}
		}

		if (BuildConfig.DEBUG) Log.wtf(_toString(), getMethodName() + "onKeyDown()" + REMOTE_STATE.get(keyID) + ":popup_type:" + popup_type + ":m_bIsExit:" + m_bIsExit);
	}

	@Override
	public void setSelectedPPXInfoOkCancel(int keyID) {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + REMOTE_STATE.get(keyID));

		super.setSelectedPPXInfoOkCancel(keyID);

		m_iTicketMessageFocusX = setSelectedOkCancel(R.id.btn_message_ticket_day_ok, R.id.btn_message_ticket_day_cancel, m_iTicketMessageFocusX, keyID);
	}

	@Override
	public void resetPPMNotice() {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName());

		ImageView imgNarrowUp = (ImageView) findViewById(R.id.img_message_ticket_narrow_up);
		imgNarrowUp.setImageResource(R.drawable.ticket_popup_narrow_up_off);

		ImageView imgNarrowDown = (ImageView) findViewById(R.id.img_message_ticket_narrow_down);
		imgNarrowDown.setImageResource(R.drawable.ticket_popup_narrow_down_off);

		Button btnOK = (Button) findViewById(R.id.btn_message_ticket_day_ok);
		btnOK.setSelected(false);

		Button btnCancel = (Button) findViewById(R.id.btn_message_ticket_day_cancel);
		btnCancel.setSelected(false);
	}

	@Override
	public void setSelectedPPMNotice(int keyID) {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + REMOTE_STATE.get(keyID));

		switch (keyID) {
			case REMOTE_UP:
				if (m_iTicketMessageFocusY > 1) {
					m_iTicketMessageFocusY--;
				}
				break;
			case REMOTE_DOWN:
				if (m_iTicketMessageFocusY < 3) {
					m_iTicketMessageFocusY++;

					if (m_iTicketMessageFocusY == 3) {
						m_iTicketMessageFocusX = POPUP_OK;
					}
				}
				break;
			case REMOTE_LEFT:
				// if (m_iTicketMessageFocusY == 3) {
				// m_iTicketMessageFocusX = OK;
				// }
				break;
			case REMOTE_RIGHT:
				// if (m_iTicketMessageFocusY == 3) {
				// m_iTicketMessageFocusX = CANCEL;
				// }
				break;
		}

		resetPPMNotice();

		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + ":" + m_iTicketMessageFocusY + ":" + m_iTicketMessageFocusX);

		try {
			switch (m_iTicketMessageFocusY) {
				case 1:
					ImageView imgNarrowUp = (ImageView) findViewById(R.id.img_message_ticket_narrow_up);
					imgNarrowUp.setImageResource(R.drawable.ticket_popup_narrow_up_on);
					break;
				case 2:
					ImageView imgNarrowDown = (ImageView) findViewById(R.id.img_message_ticket_narrow_down);
					imgNarrowDown.setImageResource(R.drawable.ticket_popup_narrow_down_on);
					break;
				case 3:
					m_iTicketMessageFocusX = setSelectedOkCancel(R.id.btn_message_ticket_day_ok, R.id.btn_message_ticket_day_cancel, m_iTicketMessageFocusX, keyID);
					break;
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	@Override
	public void resetPPXPass() {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName());

		LinearLayout layoutPass01 = (LinearLayout) findViewById(R.id.layout_ticket_pass_01);
		layoutPass01.setBackgroundResource(R.drawable.ticket_popup_pass);
		layoutPass01.setSelected(false);

		LinearLayout layoutPass02 = (LinearLayout) findViewById(R.id.layout_ticket_pass_02);
		layoutPass02.setBackgroundResource(R.drawable.ticket_popup_pass);
		layoutPass02.setSelected(false);

		LinearLayout layoutPass03 = (LinearLayout) findViewById(R.id.layout_ticket_pass_03);
		layoutPass03.setBackgroundResource(R.drawable.ticket_popup_pass);
		layoutPass03.setSelected(false);

		LinearLayout layoutPass04 = (LinearLayout) findViewById(R.id.layout_ticket_pass_04);
		layoutPass04.setBackgroundResource(R.drawable.ticket_popup_pass);
		layoutPass04.setSelected(false);

		if (m_strInputPass[0] != "") {
			ImageView imgPass01 = (ImageView) findViewById(R.id.img_ticket_pass_01);
			imgPass01.setImageResource(R.drawable.pop_pw_off);
		}

		if (m_strInputPass[1] != "") {
			ImageView imgPass02 = (ImageView) findViewById(R.id.img_ticket_pass_02);
			imgPass02.setImageResource(R.drawable.pop_pw_off);
		}

		if (m_strInputPass[2] != "") {
			ImageView imgPass04 = (ImageView) findViewById(R.id.img_ticket_pass_03);
			imgPass04.setImageResource(R.drawable.pop_pw_off);
		}

		if (m_strInputPass[3] != "") {
			ImageView imgPass04 = (ImageView) findViewById(R.id.img_ticket_pass_04);
			imgPass04.setImageResource(R.drawable.pop_pw_off);
		}

		Button btnOK = (Button) findViewById(R.id.btn_message_ticket_password_ok);
		btnOK.setSelected(false);

		Button btnBack = (Button) findViewById(R.id.btn_message_ticket_password_back);
		btnBack.setSelected(false);

		Button btnCancel = (Button) findViewById(R.id.btn_message_ticket_password_cancel);
		btnCancel.setSelected(false);
	}

	@Override
	public void displayPPXPass(int keyID) {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + REMOTE_STATE.get(keyID));

		switch (keyID) {
			case REMOTE_UP:
				m_iTicketMessageFocusX = 1;
				m_iTicketMessageFocusY = 1;
				m_bIsFocusedOnPassNumber = true;
				break;
			case REMOTE_DOWN:
				m_iTicketMessageFocusX = 1;
				m_iTicketMessageFocusY = 2;
				// m_bIsFocusedOnPassNumber = false;
				break;
			case REMOTE_LEFT:
				if (m_iTicketMessageFocusX > 1) {
					m_iTicketMessageFocusX--;
				} else {
					if (m_iTicketMessageFocusY == 1) {
						m_iTicketMessageFocusX = 4;
					} else {
						m_iTicketMessageFocusX = 3;
					}
				}
				break;
			case REMOTE_RIGHT:
				if (m_iTicketMessageFocusY == 1) {
					if (m_iTicketMessageFocusX < 4) {
						m_iTicketMessageFocusX++;
					} else {
						m_iTicketMessageFocusX = 1;
					}
				} else {
					if (m_iTicketMessageFocusX < 3) {
						m_iTicketMessageFocusX++;
					} else {
						m_iTicketMessageFocusX = 1;
					}
				}
				break;
		}

		resetPPXPass();

		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + ":" + m_iTicketMessageFocusY + ":" + m_iTicketMessageFocusX);

		try {
			switch (m_iTicketMessageFocusY) {
				case 1:
					if (m_iTicketMessageFocusX == 1) {
						LinearLayout layoutPass01 = (LinearLayout) findViewById(R.id.layout_ticket_pass_01);
						layoutPass01.setBackgroundResource(R.drawable.ticket_popup_pass_on);

						if (m_strInputPass[0] != "") {
							ImageView imgPass01 = (ImageView) findViewById(R.id.img_ticket_pass_01);
							imgPass01.setImageResource(R.drawable.pop_pw_on);
						}
					} else if (m_iTicketMessageFocusX == 2) {
						LinearLayout layoutPass02 = (LinearLayout) findViewById(R.id.layout_ticket_pass_02);
						layoutPass02.setBackgroundResource(R.drawable.ticket_popup_pass_on);

						if (m_strInputPass[1] != "") {
							ImageView imgPass02 = (ImageView) findViewById(R.id.img_ticket_pass_02);
							imgPass02.setImageResource(R.drawable.pop_pw_on);
						}
					} else if (m_iTicketMessageFocusX == 3) {
						LinearLayout layoutPass03 = (LinearLayout) findViewById(R.id.layout_ticket_pass_03);
						layoutPass03.setBackgroundResource(R.drawable.ticket_popup_pass_on);

						if (m_strInputPass[2] != "") {
							ImageView imgPass03 = (ImageView) findViewById(R.id.img_ticket_pass_03);
							imgPass03.setImageResource(R.drawable.pop_pw_on);
						}
					} else if (m_iTicketMessageFocusX == 4) {
						LinearLayout layoutPass04 = (LinearLayout) findViewById(R.id.layout_ticket_pass_04);
						layoutPass04.setBackgroundResource(R.drawable.ticket_popup_pass_on);

						if (m_strInputPass[3] != "") {
							ImageView imgPass04 = (ImageView) findViewById(R.id.img_ticket_pass_04);
							imgPass04.setImageResource(R.drawable.pop_pw_on);
						}
					}
					break;
				case 2:
					m_iTicketMessageFocusX = setSelectedOkBackCancel(R.id.btn_message_ticket_password_ok, R.id.btn_message_ticket_password_back, R.id.btn_message_ticket_password_cancel, m_iTicketMessageFocusX, REMOTE_NONE);
					break;
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	@Override
	protected void setPageTextListenList() {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + "[ST]" + remote.isGenre());
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + "page " + m_iCurrentViewListenListPage + "/" + m_iTotalListenListPage);
		try {
			m_iListenItemCount = (m_iCurrentListenListPage - 1) * 8;

			//TextView txtPage = null;
			//
			//if (remote.m_iMenuMainFocus == 2) {
			//	txtPage = (TextView) findViewById(R.id.txt_listen_sub_page);
			//	txtPage.setText("page " + String.valueOf(m_iCurrentViewListenListPage) + "/" + String.valueOf(m_iTotalListenListPage));
			//} else {
			//	txtPage = (TextView) findViewById(R.id.txt_song_page);
			//	txtPage.setText("page " + String.valueOf(m_iCurrentViewListenListPage) + "/" + String.valueOf(m_iTotalListenListPage));
			//}

			TextView txtPage = (TextView) findViewById(R.id.txt_song_page);

			if (remote.m_iMenuMainFocus == 2) {
				txtPage = (TextView) findViewById(R.id.txt_listen_sub_page);
			}

			if (txtPage != null) {
				txtPage.setText("page " + String.valueOf(m_iCurrentViewListenListPage) + "/" + String.valueOf(m_iTotalListenListPage));
				if (m_iTotalListenListPage > 0) {
					txtPage.setVisibility(View.VISIBLE);
				} else {
					txtPage.setVisibility(View.INVISIBLE);
				}
			}

		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + "[ED]" + remote.isGenre());
	}

	@Override
	protected void addViewListenList() {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "m_iMenuListenFocus:" + remote.m_iMenuListenFocus);
		addView(layout_content, R.layout.listen_list3, R.id.listen_list);
		inflateListListen(R.id.listen_list);
	}

	@Override
	protected void inflateListListen(int res) {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[ST]");

		listen_list = (ViewGroup) layout_content.findViewById(res);
		listen_list.setId(res);

		listen_line.clear();
		Collections.addAll(listen_line
				, new ListenLine((ViewGroup) listen_list.findViewById(R.id.listen_item_1))
				, new ListenLine((ViewGroup) listen_list.findViewById(R.id.listen_item_2))
				, new ListenLine((ViewGroup) listen_list.findViewById(R.id.listen_item_3))
				, new ListenLine((ViewGroup) listen_list.findViewById(R.id.listen_item_4))
				, new ListenLine((ViewGroup) listen_list.findViewById(R.id.listen_item_5))
				, new ListenLine((ViewGroup) listen_list.findViewById(R.id.listen_item_6))
				, new ListenLine((ViewGroup) listen_list.findViewById(R.id.listen_item_7))
				, new ListenLine((ViewGroup) listen_list.findViewById(R.id.listen_item_8))
		);

		int r = 1;
		int c = 1;
		if (listen_list instanceof GridLayout) {
			r = ((GridLayout) listen_list).getRowCount();
			c = ((GridLayout) listen_list).getColumnCount();
		}
		int w = (layout_content.getWidth() / c);
		int h = (layout_content.getHeight() / r);

		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[WH]" + layout_content.getWidth() + "," + layout_content.getHeight() + "->" + +w + "," + h + ":" + r + "," + c);

		for (final ListenLine line : listen_line) {
			if (line.layout instanceof GridLayout) {
				GridLayout.LayoutParams params = (GridLayout.LayoutParams) line.layout.getLayoutParams();
				params.width = w - params.rightMargin - params.leftMargin;
				params.height = h - params.topMargin - params.bottomMargin;
				line.layout.setLayoutParams(params);
				line.layout.setVisibility(View.INVISIBLE);
			}
		}

		//if (listen_list != null) {
		//	listen_list.setFocusable(true);
		//	listen_list.setFocusableInTouchMode(false);
		//	listen_list.setClickable(false);
		//	// requestFocus(listen_list);
		//}
		//
		//for (ListenLine line : listen_line) {
		//	// 포커스설정
		//	line.layout.setFocusable(false);
		//	line.layout.setFocusableInTouchMode(false);
		//	// 포커스해제
		//	line.layout.clearFocus();
		//	line.layout.setSelected(false);
		//	line.layout.setPressed(false);
		//	// 포커스해제
		//	if (line.img_focus != null) {
		//		line.img_focus.clearFocus();
		//		line.img_focus.setSelected(false);
		//		line.img_focus.setPressed(false);
		//	}
		//}

		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[ED]");
	}

	@Override
	protected void resetListenList() {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ST]");
		super.resetListenList();

		for (ListenLine line : listen_line) {
			line.layout.setVisibility(View.INVISIBLE);
			//포커스
			line.img_focus.setVisibility(View.INVISIBLE);
			// 프로필
			line.img_listen_profile.setImageResource(R.drawable.profile_default_2);
			// 제목 - 가수
			line.txt_listen_title.setText("");
			// // 닉네임
			line.txt_listen_nickname.setText("");
			// 추천 횟수
			line.txt_listen_recommand.setText("");
			// 듣기 횟수
			line.txt_listen_count.setText("");
			// 등록일
			line.txt_listen_day.setText("");
		}
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ED]");
	}

	@Override
	public void clickListListen() {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		super.clickListListen();
	}

	@Override
	public void displayListListen(int keyID) {
		if (BuildConfig.DEBUG) Log.wtf(_toString(), getMethodName() + "[ST]" + REMOTE_STATE.get(keyID) + ":" + remote.m_iListenListFocusX + "," + remote.m_iListenListFocusY);

		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[BF]" + REMOTE_STATE.get(keyID) + ":" + remote.m_iListenListFocusX + "," + remote.m_iListenListFocusY);

		if (mListenItems == null || mListenItems.size() == 0) {
			Log.wtf(_toString(), "displayListListen()" + "[NO]" + REMOTE_STATE.get(keyID) + ":" + remote.m_iListenListFocusX + "," + remote.m_iListenListFocusY);
			remote.m_iListenListFocusX  = 0;
			remote.m_iListenListFocusY  = 0;
			return;
		}

		if (keyID == REMOTE_UP) {
			if (remote.m_iListenListFocusY == 0) {
				exitListListen();
				return;
			}
		}

		int request = REQUEST_LISTEN_LIST;
		String OP = null;
		String M1 = null;
		String M2 = null;
		switch (remote.m_iMenuListenFocus) {
			case 1:
				OP = KP_2100;
				M1 = M1_MENU_LISTEN;
				M2 = M2_LISTEN_TIMELINE;
				break;
			case 2:
				OP = KP_2100;
				M1 = M1_MENU_LISTEN;
				M2 = M2_LISTEN_WEEK;
				break;
			case 3:
				OP = KP_2100;
				M1 = M1_MENU_LISTEN;
				M2 = M2_LISTEN_TOP100;
				break;
		}

		if (keyID == REMOTE_RIGHT) {
			if (remote.m_iListenListFocusX == 5) {
				if (m_iCurrentViewListenListPage < m_iTotalListenListPage) {
					if (BuildConfig.DEBUG) Log.i(_toString(), "next");
					remote.m_iListenListFocusX = 1;

					m_iCurrentListenListPage++;
					m_iCurrentViewListenListPage++;

					// 1, 21, 31... 페이지면 새로 요청
					if (m_iCurrentViewListenListPage % 10 == 1) {
						m_iRequestPage++;
						m_iCurrentListenListPage = 1;
						// if (BuildConfig.DEBUG) _LOG.wtf(_toString(), getMethodName() + "[KP]" + REMOTE_STATE.get(keyID) + ":" + m_iCurrentViewListenListPage + "," + m_iCurrentListenListPage + "," + m_iRequestPage);
						mListenListPage = m_iCurrentListenListPage;
						KP(request, OP, M1, M2);
					}
					// 븅신아왜막았나맞춰봐
					// else
					// {
					// // if (BuildConfig.DEBUG) _LOG.wtf(_toString(), getMethodName() + "[MV]" + REMOTE_STATE.get(keyID) + ":" + m_iCurrentViewListenListPage + "," + m_iCurrentListenListPage + "," + m_iRequestPage);
					// // moveListenListPage();
					// }
				} else {
					// 마지막 페이지에서 RIGHT키 입력 시 1페이지로 이동
					remote.m_iListenListFocusX = 1;
					m_iCurrentViewListenListPage = 1;
					m_iCurrentListenListPage = 1;
					int temp = m_iRequestPage;
					m_iRequestPage = 1;

					// 이전 Request했던 페이지와 다른 데이터(새로받음)이면 KP 새로 요청
					if (temp != m_iRequestPage && m_iTotalListenListPage > 1) {
						// if (BuildConfig.DEBUG) _LOG.wtf(_toString(), getMethodName() + "[KP]" + REMOTE_STATE.get(keyID) + ":" + m_iCurrentViewListenListPage + "," + m_iCurrentListenListPage + "," + m_iRequestPage);
						mListenListPage = m_iCurrentListenListPage;
						KP(request, OP, M1, M2);
					}
					// 븅신아왜막았나맞춰봐
					// else
					// {
					// // if (BuildConfig.DEBUG) _LOG.wtf(_toString(), getMethodName() + "[MV]" + REMOTE_STATE.get(keyID) + ":" + m_iCurrentViewListenListPage + "," + m_iCurrentListenListPage + "," + m_iRequestPage);
					// // moveListenListPage();
					// }
				}
			}
		} else if (keyID == REMOTE_LEFT) {
			if (remote.m_iListenListFocusX == 0) {
				if (m_iCurrentViewListenListPage > 1) {
					if (BuildConfig.DEBUG) Log.i(_toString(), "prev");

					remote.m_iListenListFocusX = 4;

					m_iCurrentListenListPage--;
					m_iCurrentViewListenListPage--;

					// 10, 20, 30... 페이지면 새로 요청
					if (m_iCurrentViewListenListPage % 10 == 0) {
						m_iRequestPage--;
						m_iCurrentListenListPage = 10;

						// if (BuildConfig.DEBUG) _LOG.wtf(_toString(), getMethodName() + "[KP]" + REMOTE_STATE.get(keyID) + ":" + m_iCurrentViewListenListPage + "," + m_iCurrentListenListPage + "," + m_iRequestPage);
						mListenListPage = m_iCurrentListenListPage;
						KP(request, OP, M1, M2);
					}
					// 븅신아왜막았나맞춰봐
					// else
					// {
					// // if (BuildConfig.DEBUG) _LOG.wtf(_toString(), getMethodName() + "[MV]" + REMOTE_STATE.get(keyID) + ":" + m_iCurrentViewListenListPage + "," + m_iCurrentListenListPage + "," + m_iRequestPage);
					// // moveListenListPage();
					// }
					// 1페이지에서 LEFT키 입력 : 마지막 페이지로 이동
				} else {
					if (m_iTotalListenListPage > 1) {
						// 최소한 1 페이지 이상 있을 때
						if (BuildConfig.DEBUG) Log.i(_toString(), "GO Last Page");

						remote.m_iListenListFocusX = 4;

						m_iCurrentViewListenListPage = m_iTotalListenListPage;
						m_iCurrentListenListPage = m_iTotalListenListPage % 10;
						if (m_iCurrentListenListPage == 0) {
							m_iCurrentListenListPage = 10;
						} else {
							int temp = m_iRequestPage;
							if (m_iTotalListenListPage < 100) {
								m_iRequestPage = (m_iTotalListenListPage / 10) + 1;
							} else {
								m_iRequestPage = m_iTotalListenListPage / 10;
							}

							// 이전 Request했던 페이지와 다른 데이터(새로받음)이면 KP 새로 요청
							if (temp != m_iRequestPage) {
								// if (BuildConfig.DEBUG) _LOG.wtf(_toString(), getMethodName() + "[KP]" + REMOTE_STATE.get(keyID) + ":" + m_iCurrentViewListenListPage + "," + m_iCurrentListenListPage + "," + m_iRequestPage);
								mListenListPage = m_iCurrentListenListPage;
								KP(request, OP, M1, M2);
							}
						}
					} else {
						// 그도 아니면 포커스 움직이지 않는 것으로 보여줌
						remote.m_iListenListFocusX = 1;
					}
				}
			}
		}

		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[AF]" + REMOTE_STATE.get(keyID) + ":" + remote.m_iListenListFocusX + "," + remote.m_iListenListFocusY);

		setPageTextListenList();

		if (mListenListPage != m_iCurrentListenListPage) {
			if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[UP]" + REMOTE_STATE.get(keyID) + (mListenListPage != m_iCurrentListenListPage) + ":" + mListenListPage + ":" + m_iCurrentListenListPage);
			startListenListPage();
		}

		if (keyID != REMOTE_NONE && (keyID != REMOTE_UP || keyID != REMOTE_DOWN)) {
			unselectListenList();
		}

		selectListenList();

		setListBackground(getMethodName());

		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + REMOTE_STATE.get(keyID) + ":" + remote.m_iListenListFocusX + "," + remote.m_iListenListFocusY);
	}

	@Override
	protected void startListenListPage() {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[ST]");

		postDelayed(startListenListPage, 200);

		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[ED]");
	}

	private final Runnable startListenListPage = new Runnable() {

		@Override
		public void run() {

			(new moveListenListPage()).execute();
		}
	};

	//private class moveListenListPage extends AsyncTask<Void, Integer, Boolean> {
	//
	//	@Override
	//	protected Boolean doInBackground(Void... params) {
	//
	//		post(moveListenListPage);
	//		return null;
	//	}
	//
	//}
	private class moveListenListPage extends _Thread {

		@Override
		public void run() {
			post(moveListenListPage);
		}

	}

	private final Runnable moveListenListPage = new Runnable() {
		@Override
		public void run() {

			moveListenListPage();
		}
	};

	public void moveListenListPage() {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[ST]");

		startLoading(getMethodName(), LOADING_SHORT);

		mListenListPage = m_iCurrentListenListPage;

		//hideListenList();

		postDelayed(moveListenListLines, 0);

		postDelayed(setListenListImages, 500);

		//showListenList();

		stopLoading(getMethodName());

		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[ED]");
	}

	private Runnable hideSingList = new Runnable() {
		@Override
		public void run() {
			hideSingList();
		}
	};

	private void hideSingList() {
		try {
			if (sing_list != null) {
				sing_list.setVisibility(View.INVISIBLE);
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	private Runnable showSingList = new Runnable() {
		@Override
		public void run() {
			showSingList();
		}
	};

	private void showSingList() {
		try {
			if (sing_list != null) {
				sing_list.setVisibility(View.VISIBLE);
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	private Runnable hideSingLines = new Runnable() {
		@Override
		public void run() {
			hideSingLines();
		}
	};

	private void hideSingLines() {
		try {
			for (final SingLine line : sing_line) {
				line.layout.setVisibility(View.INVISIBLE);
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	private Runnable showSingLines = new Runnable() {
		@Override
		public void run() {
			showSingLines();
		}
	};

	private void showSingLines() {
		try {
			for (final SingLine line : sing_line) {
				line.layout.setVisibility(View.VISIBLE);
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	private Runnable hideListenList = new Runnable() {
		@Override
		public void run() {
			hideListenList();
		}
	};

	private void hideListenList() {
		try {
			if (listen_list != null) {
				listen_list.setVisibility(View.INVISIBLE);
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	private Runnable showListenList = new Runnable() {
		@Override
		public void run() {
			showListenList();
		}
	};

	private void showListenList() {
		try {
			if (listen_list != null) {
				listen_list.setVisibility(View.VISIBLE);
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	private Runnable hideListenLines = new Runnable() {
		@Override
		public void run() {
			hideListenLines();
		}
	};

	private void hideListenLines() {
		try {
			for (final ListenLine line : listen_line) {
				line.layout.setVisibility(View.INVISIBLE);
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	private Runnable showListenLines = new Runnable() {
		@Override
		public void run() {
			showListenLines();
		}
	};

	private void showListenLines() {
		try {
			for (final ListenLine line : listen_line) {
				line.layout.setVisibility(View.VISIBLE);
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	private final Runnable moveListenListLines = new Runnable() {
		@Override
		public void run() {

			moveListenListLines();
		}
	};

	int mListenListPage = 0;

	protected void moveListenListLines() {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[ST]");

		int index = 0;
		int i = 0;

		if (mListenItems == null || mListenItems.size() == 0) {
			return;
		}

		for (final ListenLine line : listen_line) {
			try {
				index = (i + (m_iCurrentListenListPage - 1) * 8);

				ListenItem item = null;

				if (index < mListenItems.size()) {
					item = mListenItems.get(index);
				}

				//if (BuildConfig.DEBUG) _LOG.i(_toString(), getMethodName() + idx + ":" + index + "-" + item);

				final int idx = i;

				if (item != null) {
					line.layout.setVisibility(View.VISIBLE);

					//프로필
					line.img_listen_profile.setImageResource(R.drawable.profile_default_2);

					// 제목 - 가수
					if (line.txt_listen_title != null) {
						String title = item.title + " - " + item.artist;
						line.txt_listen_title.setText(title);
					}

					// 닉네임
					if (line.txt_listen_nickname != null) {
						String nick = "by " + item.nickname;
						line.txt_listen_nickname.setText(nick);
					}

					// 등록일
					if (line.txt_listen_day != null) {
						line.txt_listen_day.setText(item.reg_date);
					}

					// 추천 횟수
					if (line.txt_listen_recommand != null) {
						line.txt_listen_recommand.setText(item.heart);
						//String heart = "00000";
						//if (!TextUtil.isEmpty(item.heart)) {
						//	heart = String.format("%05d", Integer.parseInt(item.heart));
						//}
						//line.txt_listen_recommand.setText(heart);
					}

					// 듣기 횟수
					if (line.txt_listen_count != null) {
						line.txt_listen_count.setText(item.hit);
						//String hit = item.hit;
						//if (!TextUtil.isEmpty(hit) && hit.length() < 6) {
						//	while (hit.length() < 6) {
						//		hit = "0" + hit;
						//	}
						//}
						//String hit = "000000";
						//if (!TextUtil.isEmpty(item.hit)) {
						//	hit = String.format("%06d", Integer.parseInt(item.hit));
						//}
						//line.txt_listen_count.setText(hit);
					}
				} else {
					line.layout.setVisibility(View.INVISIBLE);
				}

			} catch (Exception e) {
				if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
			}

			i++;
		}

		// m_iSetListenItemCount = idx;
		m_iListenItemCount = i;

		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[ED]");
	}

	private void putURLImage(ImageView v, String url, int idx) {
		int h = v.getHeight();
		int w = v.getWidth();
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + idx + ":" + w + "," + h + ":" + v + url);

		putURLImage(v, url, false);
	}

	private final Runnable setListenListImages = new Runnable() {
		@Override
		public void run() {

			setListenListImages();
		}
	};

	static private String PROFILE_DEFAILT = "http://resource.kymedia.kr/images/kpop/mem/profile_default.png";

	protected void setListenListImages() {
		int index = 0;
		int i = 0;

		for (final ListenLine line : listen_line) {
			index = (i + (m_iCurrentListenListPage - 1) * 8);

			try {
				final ListenItem item = mListenItems.get(index);

				//if (BuildConfig.DEBUG) _LOG.i(_toString(), getMethodName() + idx + ":" + index + "-" + item);

				final int idx = i;

				//프로필
				putURLImage(line.img_listen_profile, item.url_profile, idx);
			} catch (Exception e) {
				if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
			}

			i++;
		}
	}

}
