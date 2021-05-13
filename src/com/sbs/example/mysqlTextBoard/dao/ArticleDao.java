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

	public void modify(Map<String, Object> args) {
		SecSql sql = new SecSql();

		int id = (int) args.get("id");
		String title = args.get("title") != null ? (String) args.get("title") : null;
		String body = args.get("body") != null ? (String) args.get("body") : null;
		int recommendsCount = args.get("recommendsCount") != null ? (int) args.get("recommendsCount") : -1;

		sql.append("UPDATE article");
		sql.append("SET updateDate = NOW(),");
		if (title != null) {
			sql.append("title = ?,", title);
		}
		if (body != null) {
			sql.append("body = ?,", body);
		}
		if (body != null) {
			sql.append("body = ?,", body);
		}
		if (recommendsCount != -1) {
			sql.append("recommendsCount = ?", recommendsCount);
		}

		sql.append("WHERE id = ?", id);

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
		sql.append(",board.name AS extra__boardName");
		sql.append(",board.code AS extra__boardCode");
		sql.append("FROM article");
		sql.append("INNER JOIN `member`");
		sql.append("ON article.memberId = member.id");
		sql.append("INNER JOIN `board`");
		sql.append("ON article.boardId = board.id");
		if (boardId != 0) {
			sql.append("WHERE article.boardId = ?", boardId);
		}
		sql.append("ORDER BY article.id DESC");

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
		sql.append("INSERT INTO recommend");
		sql.append("SET regDate = NOW(),");
		sql.append("updateDate = NOW(),");
		sql.append("articleId = ?,", inputedId);
		sql.append("memberId = ?", loginedMemberId);

		MysqlUtil.insert(sql);

	}

	public List<Recommand> loadRecommandsById(int inputedId) {
		List<Recommand> recommends = new ArrayList<>();

		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM recommend");
		sql.append("WHERE articleId = ?", inputedId);

		List<Map<String, Object>> recommendListMap = MysqlUtil.selectRows(sql);

		for (Map<String, Object> recommendMap : recommendListMap) {

			recommends.add(new Recommand(recommendMap));

		}

		return recommends;
	}

	public void doCancelRecommand(int inputedId, int loginedMemberId) {
		SecSql sql = new SecSql();
		sql.append("DELETE FROM recommend");
		sql.append("WHERE articleId = ? AND memberId = ?", inputedId, loginedMemberId);

		MysqlUtil.delete(sql);

	}

	public Recommand loadRecommandById(int inputedId, int loginedMemberId) {
		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM recommend");
		sql.append("WHERE articleId = ? AND memberId = ?", inputedId, loginedMemberId);

		Map<String, Object> recommendMap = MysqlUtil.selectRow(sql);

		if (recommendMap.isEmpty()) {
			return null;
		}

		return new Recommand(recommendMap);
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

	public int getArticlesCount() {
		SecSql sql = new SecSql();
		sql.append("SELECT COUNT(*) AS cnt");
		sql.append("FROM article");

		return MysqlUtil.selectRowIntValue(sql);
	}

	public int getRecommandsCount(int articleId) {
		SecSql sql = new SecSql();
		sql.append("SELECT COUNT(*) AS cnt");
		sql.append("FROM recommend");
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

	public int getBoardsCount() {
		SecSql sql = new SecSql();
		sql.append("SELECT COUNT(*) AS cnt");
		sql.append("FROM board");

		return MysqlUtil.selectRowIntValue(sql);
	}

	public int updatePageHits() {
		SecSql sql = new SecSql();

		sql.append("UPDATE article AS AR");
		sql.append("INNER JOIN (");
		sql.append("    SELECT CAST(REPLACE(REPLACE(GA4_PP.pagePathWoQueryStr, '/article_detail_', ''), '.html', '') AS UNSIGNED) AS articleId,");
		sql.append("    hit");
		sql.append("    FROM (");
		sql.append("        SELECT");
		sql.append("        IF(");
		sql.append("            INSTR(GA4_PP.pagePath, '?') = 0,");
		sql.append("            GA4_PP.pagePath,");
		sql.append("            SUBSTR(GA4_PP.pagePath, 1, INSTR(GA4_PP.pagePath, '?') - 1)");
		sql.append("        ) AS pagePathWoQueryStr,");
		sql.append("        SUM(GA4_PP.hit) AS hit");
		sql.append("        FROM ga4DataPagePath AS GA4_PP");
		sql.append("        WHERE GA4_PP.pagePath LIKE '/article_detail_%.html%'");
		sql.append("        GROUP BY pagePathWoQueryStr");
		sql.append("    ) AS GA4_PP");
		sql.append(") AS GA4_PP");
		sql.append("ON AR.id = GA4_PP.articleId");
		sql.append("SET AR.hit = GA4_PP.hit;");

		return MysqlUtil.update(sql);
	}

	public List<Article> getForPrintArticlesByTag(String tagBody) {
		List<Article> articles = new ArrayList<>();

		SecSql sql = new SecSql();
		sql.append("SELECT A.*");
		sql.append(",M.name AS extra__writer");
		sql.append(",B.name AS extra__boardName");
		sql.append(",B.code AS extra__boardCode");
		sql.append("FROM article AS A");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON A.memberId = M.id");
		sql.append("INNER JOIN `board` AS B");
		sql.append("ON A.boardId = B.id");
		sql.append("INNER JOIN `tag` AS T");
		sql.append("ON T.relTypeCode = 'article'");
		sql.append("AND A.id = T.relId");
		sql.append("WHERE T.body = ?", tagBody);
		sql.append("ORDER BY A.id DESC");

		List<Map<String, Object>> articleListMap = MysqlUtil.selectRows(sql);

		for (Map<String, Object> articleMap : articleListMap) {

			articles.add(new Article(articleMap));

		}

		return articles;
	}

}
