package com.sbs.example.mysqlTextBoard.session;

public class Session {

	public int loginedMemberId;
	public int selectedBoardId;

	public Session() {
		loginedMemberId = 0;
		selectedBoardId = 0;
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

	public void selectBoard(int boardId) {
		selectedBoardId = boardId;

	}

}
