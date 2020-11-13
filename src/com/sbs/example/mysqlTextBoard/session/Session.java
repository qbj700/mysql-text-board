package com.sbs.example.mysqlTextBoard.session;

public class Session {

	public int loginedMemberId;

	public Session() {
		loginedMemberId = 0;
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

}
