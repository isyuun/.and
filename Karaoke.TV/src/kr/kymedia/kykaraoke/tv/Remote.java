package kr.kymedia.kykaraoke.tv;

import kr.kymedia.kykaraoke.tv.api._Const;

/**
 * <pre>
 * 전체개발중...제일븅신같은짓!!!이걸어떻게없애나...한심하다~~~
 * </pre>
 * 
 * @author isyoon
 *
 */
class Remote implements _Const {
	/**
	 * 메뉴상태(위치)
	 * 
	 * @see kr.kymedia.kykaraoke.tv.api._Const.MENU_STATE
	 */
	public int m_iState;
	/**
	 * 메뉴(홈화면)
	 */
	public int m_iMenuHomeFocus;
	/**
	 * 메뉴(홈화면)
	 */
	public int m_iMenuHomeFocusY;
	/**
	 * 메뉴(메인화면)
	 */
	public int m_iMenuMainFocus;
	/**
	 * 1.노래부르기->서브메뉴
	 */
	public int m_iMenuSingFocus;
	/**
	 * 1.노래부르기->장르 서브메뉴
	 */
	public int m_iMenuSingGenreFocus;
	/**
	 * 1.노래부르기->곡목록 위치
	 */
	public int m_iSongListFocus;
	/**
	 * 1.노래부르기->곡목록 현재 페이지
	 */
	public int m_iCurrentSongListPage;
	/**
	 * 1.노래부르기->곡목록 상세메뉴 위치
	 */
	public int m_iSongListDetailFocus;
	/**
	 * 2.녹음곡감상->서브메뉴
	 */
	public int m_iMenuListenFocus;
	/**
	 * 2.녹음곡감상->리스트 가로
	 */
	public int m_iListenListFocusX;
	/**
	 * 2.녹음곡감상->리스트 세로
	 */
	public int m_iListenListFocusY;
	/**
	 * 2.녹음곡감상->다른 녹음곡 포커스
	 */
	public int m_iListenOther;
	/**
	 * 2.녹음곡감상->다른 녹음곡 포커스 (이전)
	 */
	public int m_iListenOtherBefore = 0;
	/**
	 * 3.마이노래방->서브메뉴
	 */
	public int m_iMenuMyFocus;
	/**
	 * 4.노래방샵->서브메뉴
	 */
	public int m_iMenuShopFocus;
	/**
	 * 인증센터 - 휴대폰 번호 등록 팝업 가로
	 */
	public int m_iCertifyHPFocusX = 1;
	/**
	 * 인증센터 - 휴대폰 번호 등록 팝업 세로
	 */
	public int m_iCertifyHPFocusY = 1;
	/**
	 * 인증센터 - 인증 번호 등록 팝업 가로
	 */
	public int m_iCertifyFocusX = 1;
	/**
	 * 인증센터 - 인증 번호 등록 팝업 세로
	 */
	public int m_iCertifyFocusY = 1;
	/**
	 * 5.고객센터 서브메뉴
	 */
	public int m_iMenuCustomerFocus;
	/**
	 * 공지/이용안내목록 위치
	 */
	public int m_iCustomerListFocus;
	/**
	 * 공지/이용안내목록 현재 페이지
	 */
	public int m_iCurrentCustomerListPage;
	/**
	 * 6.곡찾기 서브메뉴
	 */
	public int m_iSearchSubMenuFocus;
	/**
	 * 직접검색 포커스
	 */
	public int m_iSearchSelfFocus;
	/**
	 * 색인검색 포커스 가로
	 */
	public int m_iSearchLetterFocusX;
	/**
	 * 색인검색 포커스 세로
	 */
	public int m_iSearchLetterFocusY;
	/**
	 * 곡찾기 목록 위치
	 */
	public int m_iSearchListFocus;
	///**
	// * 4.노래방샵 - 쿠폰등록 메뉴 노출 여부
	// */
	//public boolean m_bShowCouponMenu;

	public Remote() {
		m_iState = STATE_HOME_MENU;
		m_iMenuHomeFocus = 1;
		m_iMenuHomeFocusY = 1;
		m_iMenuSingFocus = 1;
		m_iSongListFocus = 1;
		m_iCurrentSongListPage = 1;
		m_iSongListDetailFocus = 1;
		m_iMenuSingGenreFocus = 1;
		m_iMenuMainFocus = 1;
		m_iMenuMyFocus = 1;
		m_iMenuShopFocus = 1;
		m_iMenuCustomerFocus = 1;
		m_iCustomerListFocus = 1;
		m_iCurrentCustomerListPage = 1;
		m_iSearchSubMenuFocus = 1;
		m_iSearchSelfFocus = 1;
		m_iSearchLetterFocusX = 0;
		m_iSearchLetterFocusY = 1;
		m_iSearchListFocus = 1;
		m_iMenuListenFocus = 1;
		m_iListenListFocusX = 1;
		m_iListenListFocusY = 1;

		//m_bShowCouponMenu = false;
	}

	public void inputKey(int keyID) {
		switch (m_iState)
		{
		case STATE_HOME_MENU:
			setMainMenuState(keyID);
			break;
		case STATE_MAIN_MENU:
			setSubMainMenuState(keyID);
			break;
		case STATE_SING_MENU:
			setSingSubState(keyID);
			break;
		case STATE_SING_GENRE:
			setSingSubGenreState(keyID);
			break;
		case STATE_SONG_LIST:
			setSongListState(keyID);
			break;
		case STATE_SONG_LIST_DETAIL:
			setSongListDetailState(keyID);
			break;
		case STATE_MY_MENU:
			setMySubMenuState(keyID);
			break;
		case STATE_MY_LIST:
			setSongListState(keyID);
			break;
		case STATE_SHOP_MENU:
			setShopSubMenuState(keyID);
			break;
		case STATE_SHOP_TICKET:
			setShopTicketState(keyID);
			break;
		case STATE_CUSTOMER_MENU:
			setCustomerSubMenuState(keyID);
			break;
		case STATE_CUSTOMER_LIST:
		case STATE_CUSTOMER_LIST_EVENT:
			setCustomerListState(keyID);
			break;
		case STATE_SEARCH_MENU:
			setSearchSubMenuState(keyID);
			break;
		case STATE_SEARCH_SELF:
			setSearchSelfState(keyID);
			break;
		case STATE_SEARCH_LETTER_KOR:
			setSearchLetterKorState(keyID);
			break;
		case STATE_SEARCH_LETTER_ENG:
			setSearchLetterEngState(keyID);
			break;
		case STATE_SEARCH_LETTER_NUM:
			setSearchLetterNumState(keyID);
			break;
		case STATE_SEARCH_LIST:
			setSearchListState(keyID);
			break;
		case STATE_LISTEN_MENU:
			setListenSubMenuState(keyID);
			break;
		case STATE_LISTEN_LIST:
			setListenListState(keyID);
			break;
		case STATE_LISTEN_OTHER:
			setListenOtherState(keyID);
			break;
		case STATE_SEARCH_LIST_DETAIL:
			setSongListDetailState(keyID);
			break;
		case STATE_CERTIFY_HP:
		case STATE_EVENT_HP:
			setCertifyHPState(keyID);
			break;
		case STATE_CERTIFY:
			setCertifyState(keyID);
			break;
		case STATE_MY_RECORD_LIST:
			setMyRecordListState(keyID);
			break;
		}
	}

	@Deprecated
	public void setMainMenuState(int keyID) {
		// switch (keyID)
		// {
		// case REMOTE_RIGHT:
		// if (m_iMainMenuFocusY == 1) {
		// if (m_iMenuMainFocus < 5) {
		// m_iMenuMainFocus++;
		// } else if (m_iMenuMainFocus == 5) {
		// m_iMenuMainFocus = 1;
		// }
		// } else if (m_iMainMenuFocusY == 2) {
		// if (m_iMenuMainFocus == 6) {
		// m_iMenuMainFocus = 8;
		// } else if (m_iMenuMainFocus < 10) {
		// m_iMenuMainFocus++;
		// }
		// }
		// break;
		// case REMOTE_LEFT:
		// if (m_iMainMenuFocusY == 1) {
		// if (m_iMenuMainFocus > 1) {
		// m_iMenuMainFocus--;
		// } else if (m_iMenuMainFocus == 1) {
		// m_iMenuMainFocus = 5;
		// }
		// } else if (m_iMainMenuFocusY == 2) {
		// if (m_iMenuMainFocus == 8) {
		// m_iMenuMainFocus = 6;
		// } else if (m_iMenuMainFocus > 6) {
		// m_iMenuMainFocus--;
		// }
		// }
		// break;
		// case REMOTE_UP:
		// if (m_iMainMenuFocusY == 2) {
		// if (m_iMenuMainFocus == 7) {
		// m_iMenuMainFocus = 6;
		// } else {
		// m_iMainMenuFocusY = 1;
		// m_iMenuMainFocus = 1;
		// }
		// } else if (m_iMainMenuFocusY == 3) {
		// m_iMainMenuFocusY = 2;
		// m_iMenuMainFocus = 7;
		// }
		// break;
		// case REMOTE_DOWN:
		// if (m_iMainMenuFocusY == 1) {
		// m_iMainMenuFocusY = 2;
		// m_iMenuMainFocus = 6;
		// } else if (m_iMainMenuFocusY == 2) {
		// if (m_iMenuMainFocus == 6) {
		// m_iMenuMainFocus = 7;
		// } else {
		// m_iMainMenuFocusY = 3;
		// m_iMenuMainFocus = 11;
		// }
		// } else {
		// m_iMainMenuFocusY = 1;
		// m_iMenuMainFocus = 1;
		// }
		// break;
		// }
	}

	public void setSubMainMenuState(int keyID) {
		switch (keyID)
		{
		case REMOTE_RIGHT:
			if (m_iMenuMainFocus < 5) {
				m_iMenuMainFocus++;
			} else if (m_iMenuMainFocus == 5) {
				m_iMenuMainFocus = 1;
			}
			break;
		case REMOTE_LEFT:
			if (m_iMenuMainFocus > 1) {
				m_iMenuMainFocus--;
			} else if (m_iMenuMainFocus == 1) {
				m_iMenuMainFocus = 5;
			}
			break;
		}
	}

	public void setSingSubState(int keyID) {
		switch (keyID)
		{
		case REMOTE_LEFT:
			if (m_iMenuSingFocus > 1) {
				m_iMenuSingFocus--;
			} else {
				m_iMenuSingFocus = 3;
			}
			break;
		case REMOTE_RIGHT:
			if (m_iMenuSingFocus < 3) {
				m_iMenuSingFocus++;
			} else {
				m_iMenuSingFocus = 1;
			}
			break;
		}
	}

	public void setSingSubGenreState(int keyID) {
		switch (keyID)
		{
		case REMOTE_LEFT:
			if (m_iMenuSingGenreFocus > 1) {
				m_iMenuSingGenreFocus--;
			} else {
				m_iMenuSingGenreFocus = 6;
			}
			break;
		case REMOTE_RIGHT:
			if (m_iMenuSingGenreFocus < 6) {
				m_iMenuSingGenreFocus++;
			} else {
				m_iMenuSingGenreFocus = 1;
			}
			break;
		}
	}

	public boolean isGenre() {
		return (m_iMenuMainFocus == 1 && m_iMenuSingFocus == 3);
	}

	public void setSongListState(int keyID) {
		switch (keyID)
		{
		case REMOTE_UP:
			if (m_iSongListFocus > 0) {
				m_iSongListFocus--;
				//인기장르
				if (isGenre() && m_iSongListFocus == 1) {
					m_iSongListFocus--;
				}
			}
			break;
		case REMOTE_DOWN:
			if (m_iSongListFocus < 6) {
				m_iSongListFocus++;
			}
			break;
		}
	}

	public void setSongListDetailState(int keyID) {
		switch (keyID)
		{
		case REMOTE_DOWN:
			if (m_iSongListDetailFocus < 4) {
				m_iSongListDetailFocus++;
			}
			break;
		case REMOTE_UP:
			if (m_iSongListDetailFocus > 1) {
				m_iSongListDetailFocus--;
			}
			break;
		}
	}

	public void setMySubMenuState(int keyID) {
		/*
		 * switch(keyID)
		 * {
		 * case LEFT :
		 * if (m_iMenuMyFocus > 1) {
		 * m_iMenuMyFocus = 1;
		 * } else {
		 * m_iMenuMyFocus = 2;
		 * }
		 * break;
		 * case RIGHT :
		 * if (m_iMenuMyFocus < 2) {
		 * m_iMenuMyFocus = 2;
		 * } else {
		 * m_iMenuMyFocus = 1;
		 * }
		 * break;
		 * }
		 */
		if (keyID == REMOTE_LEFT) {
			if (m_iMenuMyFocus > 1) {
				m_iMenuMyFocus--;
			} else {
				m_iMenuMyFocus = 3;
			}
		} else if (keyID == REMOTE_RIGHT) {
			if (m_iMenuMyFocus < 3) {
				m_iMenuMyFocus++;
			} else {
				m_iMenuMyFocus = 1;
			}
		}

	}

	public void setShopSubMenuState(int keyID) {
		switch (keyID)
		{
		case REMOTE_LEFT:
			if (m_iMenuShopFocus > 1) {
				m_iMenuShopFocus = 1;
			} else {
				m_iMenuShopFocus = 2;
			}
			break;
		case REMOTE_RIGHT:
			if (m_iMenuShopFocus < 2) {
				m_iMenuShopFocus = 2;
			} else {
				m_iMenuShopFocus = 1;
			}
			break;
		}
	}

	public void setShopTicketState(int keyID) {
		//switch (keyID)
		//{
		//case REMOTE_LEFT:
		//	if (m_iShopTicketFocusY == 1) {
		//		if (m_iShopTicketFocus > 1) {
		//			m_iShopTicketFocus--;
		//		}
		//	}
		//	break;
		//case REMOTE_RIGHT:
		//	if (m_iShopTicketFocusY == 1) {
		//		if (m_bShowCouponMenu) {
		//			if (m_iShopTicketFocus < 3) {
		//				m_iShopTicketFocus++;
		//			}
		//		} else {
		//			if (m_iShopTicketFocus < 2) {
		//				m_iShopTicketFocus++;
		//			}
		//		}
		//	}
		//	break;
		//}
	}

	public void setCustomerSubMenuState(int keyID) {
		switch (keyID)
		{
		case REMOTE_LEFT:
			if (m_iMenuCustomerFocus > 1) {
				m_iMenuCustomerFocus--;
			} else {
				m_iMenuCustomerFocus = 5;
			}
			break;
		case REMOTE_RIGHT:
			if (m_iMenuCustomerFocus < 5) {
				m_iMenuCustomerFocus++;
			} else {
				m_iMenuCustomerFocus = 1;
			}
			break;
		}
	}

	public void setCustomerListState(int keyID) {
		switch (keyID)
		{
		case REMOTE_UP:
			if (m_iCustomerListFocus > 0) {
				m_iCustomerListFocus--;
			}
			break;
		case REMOTE_DOWN:
			if (m_iCustomerListFocus < 6) {
				m_iCustomerListFocus++;
			}
			break;
		}
	}

	public void setSearchSubMenuState(int keyID) {
		switch (keyID)
		{
		case REMOTE_LEFT:
			m_iSearchSubMenuFocus = 1;
			break;
		case REMOTE_RIGHT:
			m_iSearchSubMenuFocus = 2;
			break;
		}
	}

	@Deprecated
	public void setSearchSelfState(int keyID) {
		// switch(keyID)
		// {
		// case LEFT :
		// if (m_iSearchSelfFocus > 1) {
		// m_iSearchSelfFocus --;
		// }
		// break;
		// case RIGHT :
		// if (P_DEVICE == SMART_BOX) {
		// if (m_iSearchSelfFocus < 2) {
		// m_iSearchSelfFocus++;
		// }
		// } else {
		// if (m_iSearchSelfFocus < 3) {
		// m_iSearchSelfFocus++;
		// }
		// }
		// break;
		// }
	}

	public void setSearchLetterKorState(int keyID) {
		switch (keyID)
		{
		case REMOTE_UP:
			if (m_iSearchLetterFocusY == 3) {
				if (m_iSearchLetterFocusX == 0 || m_iSearchLetterFocusX > 5) {
					m_iSearchLetterFocusY = 1;
					return;
				}
			}

			if (m_iSearchLetterFocusY > 0) {
				m_iSearchLetterFocusY--;
			}
			break;
		case REMOTE_DOWN:
			if (m_iSearchLetterFocusX == 0 || m_iSearchLetterFocusX > 5) {
				m_iSearchLetterFocusY = 3;
				return;
			}

			if (m_iSearchLetterFocusY < 3) {
				m_iSearchLetterFocusY++;
			}
			break;
		case REMOTE_LEFT:
			if (m_iSearchLetterFocusY == 1) {
				if (m_iSearchLetterFocusX > 0) {
					m_iSearchLetterFocusX--;
				}
			} else if (m_iSearchLetterFocusY == 2) {
				if (m_iSearchLetterFocusX > 1) {
					m_iSearchLetterFocusX--;
				}
			}
			break;
		case REMOTE_RIGHT:
			if (m_iSearchLetterFocusY == 1) {
				if (m_iSearchLetterFocusX < 14) {
					m_iSearchLetterFocusX++;
				}
			} else if (m_iSearchLetterFocusY == 2) {
				if (m_iSearchLetterFocusX < 5) {
					m_iSearchLetterFocusX++;
				}
			}
			break;
		}
	}

	public void setSearchLetterEngState(int keyID) {
		switch (keyID)
		{
		case REMOTE_UP:
			if (m_iSearchLetterFocusY == 3) {
				if (m_iSearchLetterFocusX == 0 || m_iSearchLetterFocusX > 12) {
					m_iSearchLetterFocusY = 1;
					return;
				}
			}

			if (m_iSearchLetterFocusY > 0) {
				m_iSearchLetterFocusY--;
			}
			break;
		case REMOTE_DOWN:
			if (m_iSearchLetterFocusX == 0 || m_iSearchLetterFocusX > 12) {
				m_iSearchLetterFocusY = 3;
				return;
			}

			if (m_iSearchLetterFocusY < 3) {
				m_iSearchLetterFocusY++;
			}
			break;
		case REMOTE_LEFT:
			if (m_iSearchLetterFocusY == 1) {
				if (m_iSearchLetterFocusX > 0) {
					m_iSearchLetterFocusX--;
				}
			} else if (m_iSearchLetterFocusY == 2) {
				if (m_iSearchLetterFocusX > 1) {
					m_iSearchLetterFocusX--;
				}
			}
			break;
		case REMOTE_RIGHT:
			if (m_iSearchLetterFocusY == 1) {
				if (m_iSearchLetterFocusX < 14) {
					m_iSearchLetterFocusX++;
				}
			} else if (m_iSearchLetterFocusY == 2) {
				if (m_iSearchLetterFocusX < 12) {
					m_iSearchLetterFocusX++;
				}
			}
			break;
		}
	}

	public void setSearchLetterNumState(int keyID) {
		switch (keyID)
		{
		case REMOTE_UP:
			if (m_iSearchLetterFocusY == 1) {
				m_iSearchLetterFocusY = 0;
			} else {
				m_iSearchLetterFocusY = 1;
			}
			break;
		case REMOTE_DOWN:
			m_iSearchLetterFocusY = 3;
			break;
		case REMOTE_LEFT:
			if (m_iSearchLetterFocusY == 1) {
				if (m_iSearchLetterFocusX > 0) {
					m_iSearchLetterFocusX--;
				}
			}
			break;
		case REMOTE_RIGHT:
			if (m_iSearchLetterFocusY == 1) {
				if (m_iSearchLetterFocusX < 10) {
					m_iSearchLetterFocusX++;
				}
			}
			break;
		}
	}

	public void setSearchListState(int keyID) {
		switch (keyID)
		{
		case REMOTE_UP:
			if (m_iSearchListFocus > 0) {
				m_iSearchListFocus--;
			}
			break;
		case REMOTE_DOWN:
			if (m_iSearchSubMenuFocus == 1) {
				if (m_iSearchListFocus < 5) {
					m_iSearchListFocus++;
				}
			} else {
				if (m_iSearchListFocus < 4) {
					m_iSearchListFocus++;
				}
			}
			break;
		}
	}

	public void setListenSubMenuState(int keyID) {
		switch (keyID)
		{
		case REMOTE_LEFT:
			if (m_iMenuListenFocus > 1) {
				m_iMenuListenFocus--;
			} else {
				m_iMenuListenFocus = 3;
			}
			break;
		case REMOTE_RIGHT:
			if (m_iMenuListenFocus < 3) {
				m_iMenuListenFocus++;
			} else {
				m_iMenuListenFocus = 1;
			}
			break;
		}
	}

	public void setListenListState(int keyID) {
		switch (keyID)
		{
		case REMOTE_LEFT:
			if (m_iListenListFocusX > 0) {
				m_iListenListFocusX--;
			}
			break;
		case REMOTE_RIGHT:
			if (m_iListenListFocusX < 5) {
				m_iListenListFocusX++;
			}
			break;
		case REMOTE_UP:
			if (m_iListenListFocusY > 0) {
				m_iListenListFocusY--;
			}
			break;
		case REMOTE_DOWN:
			m_iListenListFocusY = 2;
			break;
		}
	}

	public void setListenOtherState(int keyID) {
		switch (keyID)
		{
		case REMOTE_LEFT:
			if (m_iListenOther > 0) {
				m_iListenOtherBefore = m_iListenOther;
				m_iListenOther--;
			}
			break;
		case REMOTE_RIGHT:
			if (m_iListenOther < 9) {
				m_iListenOtherBefore = m_iListenOther;
				m_iListenOther++;
			}
			break;
		}
	}

	@Deprecated
	public void setCertifyHPState(int keyID) {
		// switch (keyID)
		// {
		// case REMOTE_LEFT:
		// if (m_iCertifyHPFocusX > 1) {
		// m_iCertifyHPFocusX--;
		// }
		// break;
		// case REMOTE_RIGHT:
		// if (m_iCertifyHPFocusY == 1) {
		// if (m_iCertifyHPFocusX < 3) {
		// m_iCertifyHPFocusX++;
		// }
		// } else {
		// if (m_iCertifyHPFocusX < 2) {
		// m_iCertifyHPFocusX++;
		// }
		// }
		// break;
		// case REMOTE_DOWN:
		// if (m_iCertifyHPFocusY == 1) {
		// m_iCertifyHPFocusY = 2;
		// m_iCertifyHPFocusX = 1;
		// }
		// break;
		// case REMOTE_UP:
		// if (m_iCertifyHPFocusY == 2) {
		// m_iCertifyHPFocusY = 1;
		// m_iCertifyHPFocusX = 1;
		// }
		// break;
		// }
	}

	@Deprecated
	public void setCertifyState(int keyID) {
		// switch (keyID)
		// {
		// case REMOTE_LEFT:
		// if (m_iCertifyFocusX > 1) {
		// m_iCertifyFocusX--;
		// }
		// break;
		// case REMOTE_RIGHT:
		// if (m_iCertifyFocusX < 2) {
		// m_iCertifyFocusX++;
		// }
		// break;
		// case REMOTE_DOWN:
		// if (m_iCertifyFocusY == 1) {
		// m_iCertifyFocusY = 2;
		// m_iCertifyFocusX = 1;
		// }
		// break;
		// case REMOTE_UP:
		// if (m_iCertifyFocusY == 2) {
		// m_iCertifyFocusY = 1;
		// m_iCertifyFocusX = 1;
		// }
		// break;
		// }
	}

	public void setMyRecordListState(int keyID) {
		switch (keyID)
		{
		case REMOTE_UP:
			if (m_iSongListFocus > 0) {
				m_iSongListFocus--;
			}
			break;
		case REMOTE_DOWN:
			if (m_iSongListFocus < 6) {
				m_iSongListFocus++;
			}
			break;
		}
	}
}