package com.sbs.example.mysqlTextBoard.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Board;
import com.sbs.example.mysqlTextBoard.util.MysqlUtil;
import com.sbs.example.mysqlTextBoard.util.SecSql;

public class ArticleDao {

	public List<Article> getArticles() {
		List<Article> articles = new ArrayList<>();

		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("ORDER BY id DESC");

		List<Map<String, Object>> articleListMap = MysqlUtil.selectRows(sql);

		for (Map<String, Object> articleMap : articleListMap) {
			Article article = new Article();
			article.id = (int) articleMap.get("id");
			article.regDate = (String) articleMap.get("regDate");
			article.updateDate = (String) articleMap.get("updateDate");
			article.title = (String) articleMap.get("title");
			article.body = (String) articleMap.get("body");
			article.memberId = (int) articleMap.get("memberId");
			article.boardId = (int) articleMap.get("boardId");

			articles.add(article);

		}

		return articles;
	}

	public Article loadArticleDataById(int inputedId) {
		Article article = new Article();

		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?", inputedId);

		Map<String, Object> articleMap = MysqlUtil.selectRow(sql);

		article.id = (int) articleMap.get("id");
		article.regDate = (String) articleMap.get("regDate");
		article.updateDate = (String) articleMap.get("updateDate");
		article.title = (String) articleMap.get("title");
		article.body = (String) articleMap.get("body");
		article.memberId = (int) articleMap.get("memberId");
		article.boardId = (int) articleMap.get("boardId");

		return article;
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

	public int addArticleData(String title, String body, int memberId, int boardId) {

		SecSql sql = new SecSql();
		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW(),");
		sql.append("updateDate = NOW(),");
		sql.append("title = ?,", title);
		sql.append("body = ?,", body);
		sql.append("memberId = ?,", memberId);
		sql.append("boardId = ?", boardId);

		int id = MysqlUtil.insert(sql);

		return id;
	}

	public int addBoardData(String boardName) {
		int id = 0;
		Connection con = null;

		try {

			String driver = "com.mysql.cj.jdbc.Driver";

			String url = "jdbc:mysql://127.0.0.1:3306/textBoard?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull&connectTimeout=60000&socketTimeout=60000";
			String user = "sbsst";
			String pw = "sbs123414";

			// MySQL 드라이버 등록
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}

			// 연결 생성
			try {
				con = DriverManager.getConnection(url, user, pw);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			String sql = "INSERT INTO board SET boardName = ?";

			try {
				PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

				pstmt.setString(1, boardName);
				pstmt.executeUpdate();

				ResultSet rs = pstmt.getGeneratedKeys();
				rs.next();
				id = rs.getInt(1);

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id;
	}

	public Board loadBoardDataByBoardId(int inputedId) {
		Connection con = null;
		Board board = null;

		try {

			String driver = "com.mysql.cj.jdbc.Driver";

			String url = "jdbc:mysql://127.0.0.1:3306/textBoard?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull&connectTimeout=60000&socketTimeout=60000";
			String user = "sbsst";
			String pw = "sbs123414";

			// MySQL 드라이버 등록
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}

			// 연결 생성
			try {
				con = DriverManager.getConnection(url, user, pw);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			String sql = "SELECT * FROM board WHERE boardId = ?";

			try {
				PreparedStatement pstmt = con.prepareStatement(sql);

				pstmt.setInt(1, inputedId);

				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {
					int boardId = rs.getInt("boardId");
					String boardName = rs.getString("boardName");

					board = new Board(boardId, boardName);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return board;

	}

}
