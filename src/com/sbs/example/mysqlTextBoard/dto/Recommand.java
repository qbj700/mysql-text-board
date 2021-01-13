package com.sbs.example.mysqlTextBoard.dto;

import java.util.Map;

public class Recommand {

	private int id;
	private String regDate;
	private String updateDate;
	private int memberId;
	private int articleId;

	public Recommand(Map<String, Object> recommendMap) {
		this.id = (int) recommendMap.get("id");
		this.regDate = (String) recommendMap.get("regDate");
		this.updateDate = (String) recommendMap.get("updateDate");
		this.memberId = (int) recommendMap.get("memberId");
		this.articleId = (int) recommendMap.get("articleId");

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

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}
}
