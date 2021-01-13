package com.sbs.example.mysqlTextBoard.dto;

import java.util.Map;

import lombok.Data;

@Data
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
}
