package com.sbs.example.mysqlTextBoard.session;

public class Session {

	public int loginedMemberId;
	public int selectedBoardId;
	private String CurrentBoardCode;

	public Session() {
		loginedMemberId = 0;

		// 공지사항을 기본 선택된 게시판으로 지정
		selectedBoardId = 1;
		CurrentBoardCode = "notice";
	}

	public void login(int memberId) {
		loginedMemberId = memberId;

	}

	public void logout() {
		loginedMemberId = 0;
	}

	public boolean isLogined() {
		return loginedMemberId != 0;
	}

	public int getSelectedBoardId() {
		return selectedBoardId;
	}

	public void setSelectedBoardId(int BoardId) {
		this.selectedBoardId = BoardId;
	}

	public String getCurrentBoardCode() {
		return CurrentBoardCode;
	}

	public void setCurrentBoardCode(String boardCode) {
		this.CurrentBoardCode = boardCode;
	}

}
