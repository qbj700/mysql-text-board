package com.sbs.example.mysqlTextBoard.dto;

import java.util.Map;

import lombok.Data;

@Data
public class Board {

	private int id;
	private String regDate;
	private String updateDate;
	private String name;
	private String code;

	public Board(Map<String, Object> boardMap) {
		this.id = (int) boardMap.get("id");
		this.regDate = (String) boardMap.get("regDate");
		this.updateDate = (String) boardMap.get("updateDate");
		this.name = (String) boardMap.get("name");
		this.code = (String) boardMap.get("code");

	}
}
