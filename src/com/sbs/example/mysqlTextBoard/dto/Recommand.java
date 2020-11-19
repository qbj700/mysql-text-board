package com.sbs.example.mysqlTextBoard.dto;

import java.util.Map;

public class Recommand {

	public int id;
	public String regDate;
	public String updateDate;
	public int memberId;
	public int articleId;

	public Recommand(Map<String, Object> recommandMap) {
		this.id = (int) recommandMap.get("id");
		this.regDate = (String) recommandMap.get("regDate");
		this.updateDate = (String) recommandMap.get("updateDate");
		this.memberId = (int) recommandMap.get("memberId");
		this.articleId = (int) recommandMap.get("articleId");

	}
}
