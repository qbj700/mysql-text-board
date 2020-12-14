package com.sbs.example.mysqlTextBoard.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Board;
import com.sbs.example.mysqlTextBoard.dto.Recommand;
import com.sbs.example.mysqlTextBoard.dto.Reply;
import com.sbs.example.mysqlTextBoard.util.MysqlUtil;
import com.sbs.example.mysqlTextBoard.util.SecSql;

public class ArticleDao {

	public List<Article> getArticles() {
		List<Article> articles = new ArrayList<>();

		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM article");

		List<Map<String, Object>> articleListMap = MysqlUtil.selectRows(sql);

		for (Map<String, Object> articleMap : articleListMap) {

			articles.add(new Article(articleMap));

		}

		return articles;
	}

	public Article loadArticleDataById(int inputedId) {

		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?", inputedId);

		Map<String, Object> articleMap = MysqlUtil.selectRow(sql);

		if (articleMap.isEmpty()) {
			return null;
		}

		return new Article(articleMap);
	}

	public void delete(int inputedId) {

		SecSql sql = new SecSql();
		sql.append("DELETE FROM article");
		sql.append("WHERE id = ?", inputedId);

		MysqlUtil.delete(sql);

	}

	public void modify(int inputedId, String title, String body) {

		SecSql sql = new SecSql();
		sql.append("UPDATE article");
		sql.append("SET title = ?,", title);
		sql.append("body = ?,", body);
		sql.append("updateDate = NOW()");
		sql.append("WHERE id = ?", inputedId);

		MysqlUtil.update(sql);

	}

	public int addArticleData(String title, String body, int memberId, int boardId, int hit) {

		SecSql sql = new SecSql();
		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW(),");
		sql.append("updateDate = NOW(),");
		sql.append("title = ?,", title);
		sql.append("body = ?,", body);
		sql.append("hit = ?,", hit);
		sql.append("memberId = ?,", memberId);
		sql.append("boardId = ?", boardId);

		return MysqlUtil.insert(sql);
	}

	public int addBoardData(String code, String name) {

		SecSql sql = new SecSql();
		sql.append("INSERT INTO board");
		sql.append("SET regDate = NOW(),");
		sql.append("updateDate = NOW(),");
		sql.append("`code` = ?,", code);
		sql.append("`name` = ?", name);

		return MysqlUtil.insert(sql);
	}

	public Board loadBoardDataByBoardId(int inputedId) {

		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM board");
		sql.append("WHERE id = ?", inputedId);

		Map<String, Object> boardMap = MysqlUtil.selectRow(sql);

		return new Board(boardMap);

	}

	public List<Article> getForPrintArticles(int boardId) {
		List<Article> articles = new ArrayList<>();

		SecSql sql = new SecSql();
		sql.append("SELECT article.*, member.name AS extra__writer");
		sql.append("FROM article");
		sql.append("INNER JOIN member");
		sql.append("ON article.memberId = member.id");
		if (boardId != 0) {
			sql.append("WHERE article.boardId = ?", boardId);
		}

		List<Map<String, Object>> articleListMap = MysqlUtil.selectRows(sql);

		for (Map<String, Object> articleMap : articleListMap) {

			articles.add(new Article(articleMap));

		}

		return articles;
	}

	public int saveReplyData(int inputedId, int loginedMemberId, String reply) {
		SecSql sql = new SecSql();
		sql.append("INSERT INTO articleReply");
		sql.append("SET regDate = NOW(),");
		sql.append("updateDate = NOW(),");
		sql.append("articleId = ?,", inputedId);
		sql.append("memberId = ?,", loginedMemberId);
		sql.append("reply = ?", reply);

		return MysqlUtil.insert(sql);
	}

	public List<Reply> getForPrintRepliesById(int id) {
		List<Reply> replies = new ArrayList<>();

		SecSql sql = new SecSql();
		sql.append("SELECT articleReply.*, `member`.name AS extra__writer");
		sql.append("FROM articleReply");
		sql.append("INNER JOIN member");
		sql.append("ON articleReply.articleId = ? AND articleReply.memberId = `member`.id", id);
		sql.append("ORDER BY articleReply.id DESC");

		List<Map<String, Object>> replyListMap = MysqlUtil.selectRows(sql);

		for (Map<String, Object> replyMap : replyListMap) {

			replies.add(new Reply(replyMap));

		}

		return replies;
	}

	public Article loadForPrintArticleDataById(int inputedId) {

		SecSql sql = new SecSql();
		sql.append("SELECT article.*,member.name AS extra__writer");
		sql.append("FROM article");
		sql.append("INNER JOIN member");
		sql.append("ON article.id = ? AND article.memberId = member.id", inputedId);

		Map<String, Object> articleMap = MysqlUtil.selectRow(sql);

		if (articleMap.isEmpty()) {
			return null;
		}

		return new Article(articleMap);
	}

	public Reply getReplyById(int inputedId) {
		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM articleReply");
		sql.append("WHERE id = ?", inputedId);

		Map<String, Object> replyMap = MysqlUtil.selectRow(sql);

		if (replyMap.isEmpty()) {
			return null;
		}

		return new Reply(replyMap);
	}

	public void deleteReply(int inputedId) {
		SecSql sql = new SecSql();
		sql.append("DELETE FROM articleReply");
		sql.append("WHERE id = ?", inputedId);

		MysqlUtil.delete(sql);

	}

	public void modifyReply(int inputedId, String modifiedReply) {
		SecSql sql = new SecSql();
		sql.append("UPDATE articleReply");
		sql.append("SET reply = ?,", modifiedReply);
		sql.append("updateDate = NOW()");
		sql.append("WHERE id = ?", inputedId);

		MysqlUtil.update(sql);

	}

	public void addHitData(int inputedId) {
		SecSql sql = new SecSql();
		sql.append("UPDATE article");
		sql.append("SET hit = hit + 1");
		sql.append("WHERE id = ?", inputedId);

		MysqlUtil.update(sql);
	}

	public void addRecommandData(int inputedId, int loginedMemberId) {
		SecSql sql = new SecSql();
		sql.append("INSERT INTO recommand");
		sql.append("SET regDate = NOW(),");
		sql.append("updateDate = NOW(),");
		sql.append("articleId = ?,", inputedId);
		sql.append("memberId = ?", loginedMemberId);

		MysqlUtil.insert(sql);

	}

	public List<Recommand> loadRecommandsById(int inputedId) {
		List<Recommand> recommands = new ArrayList<>();

		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM recommand");
		sql.append("WHERE articleId = ?", inputedId);

		List<Map<String, Object>> recommandListMap = MysqlUtil.selectRows(sql);

		for (Map<String, Object> recommandMap : recommandListMap) {

			recommands.add(new Recommand(recommandMap));

		}

		return recommands;
	}

	public void doCancelRecommand(int inputedId, int loginedMemberId) {
		SecSql sql = new SecSql();
		sql.append("DELETE FROM recommand");
		sql.append("WHERE articleId = ? AND memberId = ?", inputedId, loginedMemberId);

		MysqlUtil.delete(sql);

	}

	public Recommand loadRecommandById(int inputedId, int loginedMemberId) {
		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM recommand");
		sql.append("WHERE articleId = ? AND memberId = ?", inputedId, loginedMemberId);

		Map<String, Object> recommandMap = MysqlUtil.selectRow(sql);

		if (recommandMap.isEmpty()) {
			return null;
		}

		return new Recommand(recommandMap);
	}

	public Board getBoardByCode(String code) {
		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM board");
		sql.append("WHERE `code` = ?", code);

		Map<String, Object> boardMap = MysqlUtil.selectRow(sql);

		if (boardMap.isEmpty()) {
			return null;
		}

		return new Board(boardMap);
	}

	public Board getBoardByName(String name) {
		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM board");
		sql.append("WHERE `name` = ?", name);

		Map<String, Object> boardMap = MysqlUtil.selectRow(sql);

		if (boardMap.isEmpty()) {
			return null;
		}

		return new Board(boardMap);
	}

	public List<Board> getForPrintBoards() {
		List<Board> boards = new ArrayList<>();

		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM board");
		sql.append("ORDER BY board.id DESC");

		List<Map<String, Object>> boardListMap = MysqlUtil.selectRows(sql);

		for (Map<String, Object> boardMap : boardListMap) {

			boards.add(new Board(boardMap));

		}

		return boards;
	}

	public int getArticlesCount(int boardId) {

		SecSql sql = new SecSql();
		sql.append("SELECT COUNT(*) AS cnt");
		sql.append("FROM article");
		sql.append("WHERE boardId = ?", boardId);

		return MysqlUtil.selectRowIntValue(sql);
	}

	public int getRecommandsCount(int articleId) {
		SecSql sql = new SecSql();
		sql.append("SELECT COUNT(*) AS cnt");
		sql.append("FROM recommand");
		sql.append("WHERE articleId = ?", articleId);

		return MysqlUtil.selectRowIntValue(sql);
	}

	public List<Board> getBoards() {
		List<Board> boards = new ArrayList<>();

		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM board");

		List<Map<String, Object>> boardListMap = MysqlUtil.selectRows(sql);

		for (Map<String, Object> boardMap : boardListMap) {

			boards.add(new Board(boardMap));

		}

		return boards;
	}

	public Board getBoardByBoardId(int boardId) {
		
		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM board");
		sql.append("WHERE id = ?", boardId);

		Map<String, Object> boardMap = MysqlUtil.selectRow(sql);

		if (boardMap.isEmpty()) {
			return null;
		}

		return new Board(boardMap);
	}

}
