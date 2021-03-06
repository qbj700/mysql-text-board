package com.sbs.example.mysqlTextBoard.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dao.ArticleDao;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Board;
import com.sbs.example.mysqlTextBoard.dto.Recommand;
import com.sbs.example.mysqlTextBoard.dto.Reply;
import com.sbs.example.mysqlTextBoard.dto.Tag;

public class ArticleService {
	private ArticleDao articleDao;
	private TagService tagService;

	public ArticleService() {
		articleDao = Container.articleDao;
		tagService = Container.tagService;
	}

	public List<Article> getArticles() {
		return articleDao.getArticles();
	}

	public Article getArticleById(int inputedId) {
		return articleDao.loadArticleDataById(inputedId);
	}

	public void delete(int inputedId) {
		articleDao.delete(inputedId);

	}

	public void modify(int inputedId, String title, String body) {
		Map<String, Object> modifyArgs = new HashMap<>();
		modifyArgs.put("id", inputedId);
		modifyArgs.put("title", title);
		modifyArgs.put("body", body);

		modify(modifyArgs);
	}

	public int saveArticle(String title, String body, int memberId, int boardId, int hit) {
		return articleDao.addArticleData(title, body, memberId, boardId, hit);
	}

	public int saveBoardData(String code, String name) {
		return articleDao.addBoardData(code, name);
	}

	public Board selectBoardByBoardId(int inputedId) {
		return articleDao.loadBoardDataByBoardId(inputedId);

	}

	public int boardDefaultSetting() {
		Board board = articleDao.loadBoardDataByBoardId(1);
		return board.getId();
	}

	public List<Article> getForPrintArticles(int boardId) {
		return articleDao.getForPrintArticles(boardId);
	}

	public List<Article> getForPrintArticles() {
		return articleDao.getForPrintArticles(0);
	}

	public int addReply(int inputedId, int loginedMemberId, String reply) {
		return articleDao.saveReplyData(inputedId, loginedMemberId, reply);
	}

	public List<Reply> getForPrintRepliesById(int id) {
		return articleDao.getForPrintRepliesById(id);
	}

	public Article getForPrintArticleById(int inputedId) {
		return articleDao.loadForPrintArticleDataById(inputedId);
	}

	public Reply getReplyById(int inputedId) {
		return articleDao.getReplyById(inputedId);
	}

	public void deleteReply(int inputedId) {
		articleDao.deleteReply(inputedId);

	}

	public void modifyReply(int inputedId, String modifiedReply) {
		articleDao.modifyReply(inputedId, modifiedReply);

	}

	public void incrementHit(int inputedId) {
		articleDao.addHitData(inputedId);
	}

	public void doRecommand(int inputedId, int loginedMemberId) {
		articleDao.addRecommandData(inputedId, loginedMemberId);
	}

	public List<Recommand> getRecommandsById(int inputedId) {
		return articleDao.loadRecommandsById(inputedId);
	}

	public void doCancelRecommand(int inputedId, int loginedMemberId) {
		articleDao.doCancelRecommand(inputedId, loginedMemberId);

	}

	public Recommand getRecommandById(int inputedId, int loginedMemberId) {
		return articleDao.loadRecommandById(inputedId, loginedMemberId);
	}

	public Board getBoardByCode(String boardCode) {
		return articleDao.getBoardByCode(boardCode);
	}

	public boolean isMakeBoardAvilableName(String name) {
		Board board = articleDao.getBoardByName(name);

		return board == null;
	}

	public boolean isMakeBoardAvilableCode(String code) {
		Board board = articleDao.getBoardByCode(code);
		return board == null;
	}

	public List<Board> getForPrintBoards() {
		return articleDao.getForPrintBoards();
	}

	public int getArticlesCount(int boardId) {
		return articleDao.getArticlesCount(boardId);
	}

	public int getArticlesCount() {
		return articleDao.getArticlesCount();
	}

	public int getRecommandsCount(int articleId) {
		return articleDao.getRecommandsCount(articleId);
	}

	public List<Board> getBoards() {
		return articleDao.getBoards();
	}

	public Board getBoardByBoardId(int boardId) {
		return articleDao.getBoardByBoardId(boardId);
	}

	public int getBoardsCount() {
		return articleDao.getBoardsCount();
	}

	public void modify(Map<String, Object> args) {
		articleDao.modify(args);
	}

	public void updatePageHits() {
		articleDao.updatePageHits();
	}

	public Map<String, List<Article>> getArticlesByTagMap() {
		Map<String, List<Article>> map = new LinkedHashMap<>();
		
		List<String> tagBodies = tagService.getDedupTagBodiesByRelTypeCode("article");

		for ( String tagBody : tagBodies) {
			List<Article> articles = getForPrintArticlesByTag(tagBody);
			
			map.put(tagBody, articles);
		}

		return map;
	}

	private List<Article> getForPrintArticlesByTag(String tagBody) {
		return articleDao.getForPrintArticlesByTag(tagBody);
	}
}
