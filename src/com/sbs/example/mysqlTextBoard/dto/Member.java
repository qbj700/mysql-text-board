package com.sbs.example.mysqlTextBoard.dto;

import java.util.Map;

public class Member {

	private int id;
	private String regDate;
	private String loginId;
	private String loginPw;
	private String name;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginPw() {
		return loginPw;
	}

	public void setLoginPw(String loginPw) {
		this.loginPw = loginPw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
