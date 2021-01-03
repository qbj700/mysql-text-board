package com.sbs.example.mysqlTextBoard.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.apidto.DisqusApiDataListThread;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.util.Util;

public class DisqusApiService {
	public Map<String, Object> getArticleData(Article article) {
		String fileName = Container.buildService.getArticleDetailFileName(article.id);
		String url = "https://disqus.com/api/3.0/forums/listThreads.json";
		DisqusApiDataListThread disqusApiDataListThread = (DisqusApiDataListThread) Util.callApiResponseTo(DisqusApiDataListThread.class, url, "api_key=" + Container.config.getDisqusApiKey(), "forum=" + Container.config.getDisqusForumName(),
				"thread:ident=" + fileName);

		if (disqusApiDataListThread == null) {
			return null;
		}

		Map<String, Object> rs = new HashMap<>();
		rs.put("recommendsCount", disqusApiDataListThread.response.get(0).likes);

		return rs;
	}

	public void updateArticlesCounts() {
		List<Article> articles = Container.articleService.getForPrintArticles();

		for (Article article : articles) {
			Map<String, Object> disqusArticleData = Container.disqusApiService.getArticleData(article);

			if (disqusArticleData != null) {
				int recommendsCount = (int) disqusArticleData.get("recommendsCount");

				Map<String, Object> modifyArgs = new HashMap<>();
				modifyArgs.put("id", article.id);
				modifyArgs.put("recommendsCount", recommendsCount);

				Container.articleService.modify(modifyArgs);
			}
		}

	}

}
