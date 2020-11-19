package com.sbs.example.mysqlTextBoard.dto;

import java.util.Map;

public class Member {

	public int id;
	public String regDate;
	public String loginId;
	public String loginPw;
	public String name;

	public Member(Map<String, Object> memberMap) {
		this.id = (int) memberMap.get("id");
		this.regDate = (String) memberMap.get("regDate");
		this.loginId = (String) memberMap.get("loginId");
		this.loginPw = (String) memberMap.get("loginPw");
		this.name = (String) memberMap.get("name");
	}

	public String getType() {
		return isAdmin() ? "관리자" : "일반회원";
	}

	public boolean isAdmin() {
		return loginId.equals("aaa");
	}
}
