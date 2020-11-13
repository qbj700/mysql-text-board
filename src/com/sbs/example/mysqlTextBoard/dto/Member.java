package com.sbs.example.mysqlTextBoard.dto;

public class Member {

	public int memberId;
	public String regDate;
	public String loginId;
	public String loginPw;
	public String name;

	public Member() {

	}

	public Member(int memberId, String regDate, String loginId, String loginPw, String name) {
		this.memberId = memberId;
		this.regDate = regDate;
		this.loginId = loginId;
		this.loginPw = loginPw;
		this.name = name;
	}
}
