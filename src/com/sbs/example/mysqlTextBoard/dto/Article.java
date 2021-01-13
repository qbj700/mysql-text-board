package com.sbs.example.mysqlTextBoard.dto;

import java.util.Map;

public class Article {
	private int id;
	private String regDate;
	private String updateDate;
	private String title;
	private String body;
	private int memberId;
	private int boardId;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public int getRecommendsCount() {
		return recommendsCount;
	}

	public void setRecommendsCount(int recommendsCount) {
		this.recommendsCount = recommendsCount;
	}

	public String getExtra__writer() {
		return extra__writer;
	}

	public void setExtra__writer(String extra__writer) {
		this.extra__writer = extra__writer;
	}

	public String getExtra__boardName() {
		return extra__boardName;
	}

	public void setExtra__boardName(String extra__boardName) {
		this.extra__boardName = extra__boardName;
	}

	public String getExtra__boardCode() {
		return extra__boardCode;
	}

	public void setExtra__boardCode(String extra__boardCode) {
		this.extra__boardCode = extra__boardCode;
	}

	public int hit;
	public int recommendsCount;

	public String extra__writer;
	public String extra__boardName;
	public String extra__boardCode;

	public Article(Map<String, Object> articleMap) {
		this.id = (int) articleMap.get("id");
		this.regDate = (String) articleMap.get("regDate");
		this.updateDate = (String) articleMap.get("updateDate");
		this.title = (String) articleMap.get("title");
		this.body = (String) articleMap.get("body");
		this.memberId = (int) articleMap.get("memberId");
		this.boardId = (int) articleMap.get("boardId");
		this.hit = (int) articleMap.get("hit");
		this.recommendsCount = (int) articleMap.get("recommendsCount");

		if (articleMap.containsKey("extra__writer")) {
			this.extra__writer = (String) articleMap.get("extra__writer");
		}
		if (articleMap.containsKey("extra__boardName")) {
			this.extra__boardName = (String) articleMap.get("extra__boardName");
		}
		if (articleMap.containsKey("extra__boardCode")) {
			this.extra__boardCode = (String) articleMap.get("extra__boardCode");
		}

	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", regDate=" + regDate + ", updateDate=" + updateDate + ", title=" + title
				+ ", body=" + body + ", memberId=" + memberId + ", boardId=" + boardId + ", hit=" + hit
				+ ", recommendsCount=" + recommendsCount + ", extra__writer=" + extra__writer + ", extra__boardName="
				+ extra__boardName + ", extra__boardCode=" + extra__boardCode + "]";
	}

}
