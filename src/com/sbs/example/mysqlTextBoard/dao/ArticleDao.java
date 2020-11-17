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

		List<Map<String, Object>> articleListMap = MysqlUtil
				.selectRows(new SecSql().append("SELECT * FROM article ORDER BY id DESC"));

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
		Connection con = null;
		Article article = null;

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

			String sql = "SELECT * FROM article WHERE id = ?";

			try {
				PreparedStatement pstmt = con.prepareStatement(sql);

				pstmt.setInt(1, inputedId);

				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {
					int id = rs.getInt("id");
					String regDate = rs.getString("regDate");
					String updateDate = rs.getString("updateDate");
					String title = rs.getString("title");
					String body = rs.getString("body");
					int memberId = rs.getInt("memberId");
					int boardId = rs.getInt("boardId");

					article = new Article(id, regDate, updateDate, title, body, memberId, boardId);
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

		return article;
	}

	public int delete(int inputedId) {
		int affectedRows = 0;
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

			String sql = "DELETE FROM article WHERE id = ?";

			try {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, inputedId);
				affectedRows = pstmt.executeUpdate();
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
		return affectedRows;
	}

	public void modify(int inputedId, String title, String body) {

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

			String sql = "UPDATE article SET title = ?,body = ? , updateDate = NOW()  WHERE id = ?";

			try {
				PreparedStatement pstmt = con.prepareStatement(sql);

				pstmt.setString(1, title);
				pstmt.setString(2, body);
				pstmt.setInt(3, inputedId);

				pstmt.executeUpdate();

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

	}

	public int addArticleData(String title, String body, int memberId, int boardId) {
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

			String sql = "INSERT INTO article SET regDate = NOW(), updateDate = NOW(), title = ?, body = ?, memberId = ?, boardId = ?";

			try {
				PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

				pstmt.setString(1, title);
				pstmt.setString(2, body);
				pstmt.setInt(3, memberId);
				pstmt.setInt(4, boardId);
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
