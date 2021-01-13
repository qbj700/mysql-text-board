package com.sbs.example.mysqlTextBoard.dto;

import java.util.Map;

import lombok.Data;

@Data
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
}
