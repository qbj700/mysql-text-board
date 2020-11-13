package com.sbs.example.mysqlTextBoard.service;

import java.util.List;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dao.ArticleDao;
import com.sbs.example.mysqlTextBoard.dto.Article;

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

	public int delete(int inputedId) {
		return articleDao.delete(inputedId);

	}

	public void modify(int inputedId, String title, String body) {
		articleDao.modify(inputedId, title, body);
	}

	public int saveArticle(String title, String body, int memberId, int boardId) {
		return articleDao.addArticleData(title, body, memberId, boardId);
	}

}
