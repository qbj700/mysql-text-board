package com.sbs.example.mysqlTextBoard.service;

import java.util.List;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dao.ArticleDao;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Board;
import com.sbs.example.mysqlTextBoard.dto.Recommand;
import com.sbs.example.mysqlTextBoard.dto.Reply;

public class ArticleService {
	private ArticleDao articleDao;

	public ArticleService() {
		articleDao = Container.articleDao;
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
		articleDao.modify(inputedId, title, body);
	}

	public int saveArticle(String title, String body, int memberId, int boardId) {
		return articleDao.addArticleData(title, body, memberId, boardId);
	}

	public int saveBoardData(String boardName) {
		return articleDao.addBoardData(boardName);
	}

	public Board selectBoardByBoardId(int inputedId) {
		return articleDao.loadBoardDataByBoardId(inputedId);

	}

	public int boardDefaultSetting() {
		Board board = articleDao.loadBoardDataByBoardId(1);
		return board.boardId;
	}

	public List<Article> getForPrintArticles() {
		return articleDao.getForPrintArticles();
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

}
