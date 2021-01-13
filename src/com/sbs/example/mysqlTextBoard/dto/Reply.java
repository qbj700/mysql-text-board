package com.sbs.example.mysqlTextBoard.dto;

import java.util.Map;

public class Reply {

	private int id;
	private String regDate;
	private String updateDate;
	private String reply;
	private int memberId;
	private int articleId;
	private String extra__writer;

	public Reply(Map<String, Object> replyMap) {
		this.id = (int) replyMap.get("id");
		this.regDate = (String) replyMap.get("regDate");
		this.updateDate = (String) replyMap.get("updateDate");
		this.reply = (String) replyMap.get("reply");
		this.memberId = (int) replyMap.get("memberId");
		this.articleId = (int) replyMap.get("articleId");

		if (replyMap.containsKey("extra__writer")) {
			this.extra__writer = (String) replyMap.get("extra__writer");
		}

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

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
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

	public String getExtra__writer() {
		return extra__writer;
	}

	public void setExtra__writer(String extra__writer) {
		this.extra__writer = extra__writer;
	}
}
