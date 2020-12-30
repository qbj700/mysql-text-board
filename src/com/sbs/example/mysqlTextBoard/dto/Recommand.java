package com.sbs.example.mysqlTextBoard.dto;

import java.util.Map;

public class Recommand {

	public int id;
	public String regDate;
	public String updateDate;
	public int memberId;
	public int articleId;

	public Recommand(Map<String, Object> recommendMap) {
		this.id = (int) recommendMap.get("id");
		this.regDate = (String) recommendMap.get("regDate");
		this.updateDate = (String) recommendMap.get("updateDate");
		this.memberId = (int) recommendMap.get("memberId");
		this.articleId = (int) recommendMap.get("articleId");

	}
}
