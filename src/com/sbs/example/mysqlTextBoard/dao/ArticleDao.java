package com.sbs.example.mysqlTextBoard.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sbs.example.mysqlTextBoard.dto.Article;

public class ArticleDao {
	public List<Article> getArticles() {

		List<Article> articles = new ArrayList<>();

		String driver = "com.mysql.cj.jdbc.Driver";

		String url = "jdbc:mysql://127.0.0.1:3306/textBoard?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
		String user = "sbsst";
		String pw = "sbs123414";

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		Connection con = null;

		try {
			con = DriverManager.getConnection(url, user, pw);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return articles;
	}

	private List<Article> getFakeArticles() {

		List<Article> articles = new ArrayList<>();

		Article article;
		// 첫번째 가짜 게시물 만들기
		article = new Article();
		article.id = 1;
		article.regDate = "2020-11-12 12:12:12";
		article.updateDate = "2020-11-12 12:12:12";
		article.title = "제목1";
		article.body = "내용1";
		article.memberId = 1;
		article.boardId = 1;

		articles.add(article);

		// 두번째 가짜 게시물 만들기
		article = new Article();
		article.id = 2;
		article.regDate = "2020-11-12 12:12:13";
		article.updateDate = "2020-11-12 12:12:13";
		article.title = "제목2";
		article.body = "내용2";
		article.memberId = 1;
		article.boardId = 1;

		articles.add(article);
		return articles;
	}
}
