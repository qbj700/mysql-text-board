package com.sbs.example.mysqlTextBoard.dto;

import java.util.Map;

public class Board {

	public int boardId;
	public String boardName;

	public Board(Map<String, Object> articleMap) {
		this.boardId = (int) articleMap.get("boardId");
		this.boardName = (String) articleMap.get("boardName");
	}
}
