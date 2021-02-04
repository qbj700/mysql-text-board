package com.sbs.example.mysqlTextBoard.service;

import java.util.List;
import java.util.Map;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Board;
import com.sbs.example.mysqlTextBoard.dto.Member;
import com.sbs.example.mysqlTextBoard.util.Util;

public class BuildService {
	private ArticleService articleService;
	private MemberService memberService;
	private DisqusApiService disqusApiService;

	public BuildService() {
		articleService = Container.articleService;
		memberService = Container.memberService;
		disqusApiService = Container.disqusApiService;
	}

	public void buildSite() {
		System.out.println("site 폴더 생성");
		Util.mkdir("site");

		Util.copyDir("site_template/img", "site/img");

		Util.copy("site_template/app.css", "site/app.css");
		Util.copy("site_template/app.js", "site/app.js");
		Util.copy("site_template/favicon.ico", "site/favicon.ico");

		loadDataFromDisqus();
		loadDataFromGa4Data();

		buildArticleTagPage();
		buildArticleListPages();
		buildArticleDetailPages();
		buildStatisticsPage();
		buildIndexPage();
		buildArticleSearchPage();
		buildAboutPage();
	}

	private void buildAboutPage() {
		StringBuilder sb = new StringBuilder();

		String head = getHeadHtml("about");
		String bodyTemplate = Util.getFileContents("site_template/about.html");
		String foot = Util.getFileContents("site_template/foot.html");

		sb.append(head);
		sb.append(bodyTemplate);
		sb.append(foot);

		String filePath = "site/about.html";
		Util.writerFile(filePath, sb.toString());
		System.out.println(filePath + " 생성");
	}

	public void buildArticleTagPage() {
		Map<String, List<Article>> articlesByTagMap = articleService.getArticlesByTagMap();

		String jsonText = Util.getJsonText(articlesByTagMap);
		Util.writerFile("site/article_tag.json", jsonText);
	}

	private void buildArticleSearchPage() {
		List<Article> articles = articleService.getForPrintArticles(0);

		String jsonText = Util.getJsonText(articles);
		Util.writerFile("site/article_list.json", jsonText);

		Util.copy("site_template/article_search.js", "site/article_search.js");

		StringBuilder sb = new StringBuilder();

		String head = getHeadHtml("article_search");
		String foot = Util.getFileContents("site_template/foot.html");

		String html = Util.getFileContents("site_template/article_search.html");

		sb.append(head);
		sb.append(html);
		sb.append(foot);

		String filePath = "site/article_search.html";
		Util.writerFile(filePath, sb.toString());
		System.out.println(filePath + " 생성");

	}

	private void loadDataFromGa4Data() {
		Container.googleAnalyticsApiService.updatePageHits();
	}

	private void loadDataFromDisqus() {
		Container.disqusApiService.updateArticlesCounts();
	}

	private void buildIndexPage() {

		int itemsInAPage = 10;
		int pageBoxMenuSize = 10;

		List<Article> articles = articleService.getForPrintArticles();
		int articlesCount = articles.size();
		int totalPage = (int) Math.ceil((double) articlesCount / itemsInAPage);

		for (int i = 1; i <= totalPage; i++) {
			buildIndexPage(itemsInAPage, pageBoxMenuSize, articles, i);
		}

	}

	private void buildIndexPage(int itemsInAPage, int pageBoxSize, List<Article> articles, int page) {
		StringBuilder sb = new StringBuilder();

		// 헤더 시작
		sb.append(getHeadHtml("index"));

		// 바디 시작
		String bodyTemplate = Util.getFileContents("site_template/index.html");

		StringBuilder mainContent = new StringBuilder();

		int articlesCount = articles.size();
		int start = (page - 1) * itemsInAPage;
		int end = start + itemsInAPage - 1;

		if (end >= articlesCount) {
			end = articlesCount - 1;
		}

		for (int i = start; i <= end; i++) {
			Article article = articles.get(i);

			String link = getArticleDetailFileName(article.getId());

			mainContent.append("<div class=\"post-preview\">");
			mainContent.append("<a href=\"" + link + "\">");
			mainContent.append("<h2 class=\"post-title\">" + article.getTitle() + "</h2>");
			mainContent.append("<h3 class=\"post-subtitle\">" + article.getSubtitle() + "</h3>");
			mainContent.append("</a>");
			mainContent.append(
					"<p class=\"post-meta\">" + article.getRegDate() + ", writer : " + article.getExtra__writer() + " <i class=\"far fa-comments\"></i>&nbsp<a href=\"https://ssg.modify.kr/" + link + "#disqus_thread\">(Second article)</a></p>");
			mainContent.append("</div>");
			mainContent.append("<div class=\"list\"></div>");

		}
		StringBuilder pageMenuContent = new StringBuilder();

		// 토탈 페이지 계산
		int totalPage = (int) Math.ceil((double) articlesCount / itemsInAPage);

		// 현재 페이지 계산
		if (page < 1) {
			page = 1;
		}

		if (page > totalPage) {
			page = totalPage;
		}

		// 현재 페이지 박스 시작, 끝 계산
		int previousPageBoxesCount = (page - 1) / pageBoxSize;
		int pageBoxStartPage = pageBoxSize * previousPageBoxesCount + 1;
		int pageBoxEndPage = pageBoxStartPage + pageBoxSize - 1;

		if (pageBoxEndPage > totalPage) {
			pageBoxEndPage = totalPage;
		}

		// 이전버튼 페이지 계산
		int pageBoxStartBeforePage = pageBoxStartPage - 1;
		if (pageBoxStartBeforePage < 1) {
			pageBoxStartBeforePage = 1;
		}

		// 다음버튼 페이지 계산
		int pageBoxEndAfterPage = pageBoxEndPage + 1;

		if (pageBoxEndAfterPage > totalPage) {
			pageBoxEndAfterPage = totalPage;
		}

		// 이전버튼 노출여부 계산
		boolean pageBoxStartBeforeBtnNeedToShow = pageBoxStartBeforePage != pageBoxStartPage;
		// 다음버튼 노출여부 계산
		boolean pageBoxEndAfterBtnNeedToShow = pageBoxEndAfterPage != pageBoxEndPage;

		if (pageBoxStartBeforeBtnNeedToShow) {
			pageMenuContent.append(" <li><a href=\"" + getArticleIndexFileName(pageBoxStartBeforePage) + "\" class=\"flex flex-ai-c\">&lt; 이전</a></li>");
		}

		for (int i = pageBoxStartPage; i <= pageBoxEndPage; i++) {
			String selectedClass = "";

			if (i == page) {
				selectedClass = "article-page-menu__link--selected";
			}

			pageMenuContent.append("<li><a href=\"" + getArticleIndexFileName(i) + "\"class=\"flex flex-ai-c " + selectedClass + "\">" + i + "</a></li>");
		}

		if (pageBoxEndAfterBtnNeedToShow) {
			pageMenuContent.append("<li><a href=\"" + getArticleIndexFileName(pageBoxEndAfterPage) + "\" class=\"flex flex-ai-c\">다음 &gt;</a></li> ");
		}

		String body = bodyTemplate.replace("${article-list__main-content}", mainContent.toString());
		body = body.replace("${article-page-menu__content}", pageMenuContent.toString());

		sb.append(body);

		// 푸터 시작
		sb.append(Util.getFileContents("site_template/foot.html"));

		// 파일 생성 시작
		String fileName = getArticleIndexFileName(page);
		String filePath = "site/" + fileName;

		Util.writerFile(filePath, sb.toString());

		System.out.println(filePath + " 생성");

	}

	private void buildStatisticsPage() {
		StringBuilder sb = new StringBuilder();

		String head = getHeadHtml("statistics");
		String foot = Util.getFileContents("site_template/foot.html");

		String bodyTemplate = Util.getFileContents("site_template/statistics.html");

		String body = bodyTemplate;
		int articlesCount = articleService.getArticlesCount();
		int boardCount = articleService.getBoardsCount();
		int memberCount = memberService.getMembersCount();

		sb.append(head);

		body = body.replace("${statistics-article-count}", Integer.toString(articlesCount));
		body = body.replace("${statistics-board-count}", Integer.toString(boardCount));
		body = body.replace("${statistics-member-count}", Integer.toString(memberCount));

		sb.append(body);
		sb.append(foot);

		String filePath = "site/statistics.html";
		Util.writerFile(filePath, sb.toString());
		System.out.println(filePath + " 생성");
	}

	private void buildArticleListPage(Board board, int itemsInAPage, int pageBoxSize, List<Article> articles, int page) {
		StringBuilder sb = new StringBuilder();

		// 헤더 시작
		sb.append(getHeadHtml("article_list_" + board.getCode()));

		// 바디 시작
		String bodyTemplate = Util.getFileContents("site_template/article_list.html");

		StringBuilder mainContent = new StringBuilder();

		int articlesCount = articles.size();
		int start = (page - 1) * itemsInAPage;
		int end = start + itemsInAPage - 1;

		if (end >= articlesCount) {
			end = articlesCount - 1;
		}

		for (int i = start; i <= end; i++) {
			Article article = articles.get(i);

			String link = getArticleDetailFileName(article.getId());

			mainContent.append("<div class=\"post-preview\">");
			mainContent.append("<a href=\"" + link + "\">");
			mainContent.append("<h2 class=\"post-title\">" + article.getTitle() + "</h2>");
			mainContent.append("<h3 class=\"post-subtitle\">" + article.getSubtitle() + "</h3>");
			mainContent.append("</a>");
			mainContent.append(
					"<p class=\"post-meta\">" + article.getRegDate() + ", writer : " + article.getExtra__writer() + " <i class=\"far fa-comments\"></i>&nbsp<a href=\"https://ssg.modify.kr/" + link + "#disqus_thread\">(Second article)</a></p>");
			mainContent.append("</div>");
			mainContent.append("<div class=\"list\"></div>");

		}
		StringBuilder pageMenuContent = new StringBuilder();

		// 토탈 페이지 계산
		int totalPage = (int) Math.ceil((double) articlesCount / itemsInAPage);

		// 현재 페이지 계산
		if (page < 1) {
			page = 1;
		}

		if (page > totalPage) {
			page = totalPage;
		}

		// 현재 페이지 박스 시작, 끝 계산
		int previousPageBoxesCount = (page - 1) / pageBoxSize;
		int pageBoxStartPage = pageBoxSize * previousPageBoxesCount + 1;
		int pageBoxEndPage = pageBoxStartPage + pageBoxSize - 1;

		if (pageBoxEndPage > totalPage) {
			pageBoxEndPage = totalPage;
		}

		// 이전버튼 페이지 계산
		int pageBoxStartBeforePage = pageBoxStartPage - 1;
		if (pageBoxStartBeforePage < 1) {
			pageBoxStartBeforePage = 1;
		}

		// 다음버튼 페이지 계산
		int pageBoxEndAfterPage = pageBoxEndPage + 1;

		if (pageBoxEndAfterPage > totalPage) {
			pageBoxEndAfterPage = totalPage;
		}

		// 이전버튼 노출여부 계산
		boolean pageBoxStartBeforeBtnNeedToShow = pageBoxStartBeforePage != pageBoxStartPage;
		// 다음버튼 노출여부 계산
		boolean pageBoxEndAfterBtnNeedToShow = pageBoxEndAfterPage != pageBoxEndPage;

		if (pageBoxStartBeforeBtnNeedToShow) {
			pageMenuContent.append(" <li><a href=\"" + getArticleListFileName(board, pageBoxStartBeforePage) + "\" class=\"flex flex-ai-c\">&lt; 이전</a></li>");
		}

		for (int i = pageBoxStartPage; i <= pageBoxEndPage; i++) {
			String selectedClass = "";

			if (i == page) {
				selectedClass = "article-page-menu__link--selected";
			}

			pageMenuContent.append("<li><a href=\"" + getArticleListFileName(board, i) + "\"class=\"flex flex-ai-c " + selectedClass + "\">" + i + "</a></li>");
		}

		if (pageBoxEndAfterBtnNeedToShow) {
			pageMenuContent.append("<li><a href=\"" + getArticleListFileName(board, pageBoxEndAfterPage) + "\" class=\"flex flex-ai-c\">다음 &gt;</a></li> ");
		}

		String body = bodyTemplate.replace("${article-list__main-content}", mainContent.toString());
		body = body.replace("${article-page-menu__content}", pageMenuContent.toString());

		sb.append(body);

		// 푸터 시작
		sb.append(Util.getFileContents("site_template/foot.html"));

		// 파일 생성 시작
		String fileName = getArticleListFileName(board, page);
		String filePath = "site/" + fileName;

		Util.writerFile(filePath, sb.toString());

		System.out.println(filePath + " 생성");
	}

	private String getArticleIndexFileName(int page) {
		return "index.html";
	}

	private String getArticleListFileName(Board board, int page) {
		return getArticleListFileName(board.getCode(), page);
	}

	private String getArticleListFileName(String boardCode, int page) {
		return "article_list_" + boardCode + "_" + page + ".html";
	}

	private void buildArticleListPages() {
		List<Board> boards = articleService.getBoards();

		int itemsInAPage = 10;
		int pageBoxMenuSize = 10;

		for (Board board : boards) {

			List<Article> articles = articleService.getForPrintArticles(board.getId());
			int articlesCount = articles.size();
			int totalPage = (int) Math.ceil((double) articlesCount / itemsInAPage);

			for (int i = 1; i <= totalPage; i++) {
				buildArticleListPage(board, itemsInAPage, pageBoxMenuSize, articles, i);
			}

		}

	}

	private void buildArticleDetailPages() {
		List<Board> boards = articleService.getBoards();

		String bodyTemplate = Util.getFileContents("site_template/article_detail.html");
		String foot = Util.getFileContents("site_template/foot.html");

		// 게시물 상세 페이지 시작
		for (Board board : boards) {
			List<Article> articles = articleService.getForPrintArticles(board.getId());

			for (int i = 0; i < articles.size(); i++) {
				Article article = articles.get(i);

				String head = getHeadHtml("article_detail", article);

				Article prevArticle = null;
				int prevArticleIndex = i + 1;
				int prevArticleId = 0;

				if (prevArticleIndex < articles.size()) {
					prevArticle = articles.get(prevArticleIndex);
					prevArticleId = prevArticle.getId();
				}

				Article nextArticle = null;
				int nextArticleIndex = i - 1;
				int nextArticleId = 0;

				if (nextArticleIndex >= 0) {
					nextArticle = articles.get(nextArticleIndex);
					nextArticleId = nextArticle.getId();
				}

				StringBuilder sb = new StringBuilder();

				sb.append(head);

				String body = bodyTemplate;

				String articleBodyForPrint = article.getBody();
				articleBodyForPrint = articleBodyForPrint.replaceAll("script", "t-script");

				body = body.replace("${article-detail__title}", article.getTitle());
				body = body.replace("${article-detail__board-name}", "게시판 : " + article.extra__boardName);
				body = body.replace("${article-detail__reg-date}", "작성일 : " + article.getRegDate());
				body = body.replace("${article-detail__writer}", "작성자 : " + article.extra__writer);
				body = body.replace("${article-detail__id}", "번호 : " + Integer.toString(article.getId()));
				body = body.replace("${article-detail__hit}", "조회수 : " + Integer.toString(article.hit));
				body = body.replace("${article-detail__recommendsCount}", "추천수 : " + Integer.toString(article.recommendsCount));
				body = body.replace("${article-detail__body}", articleBodyForPrint);
				body = body.replace("${article-detail__link-prev-article-url}", getArticleDetailFileName(prevArticleId));
				body = body.replace("${article-detail__link-prev-article-class-addi}", prevArticleId == 0 ? "none" : "");
				body = body.replace("${article-detail__link-list-url}", getArticleListFileName(article.extra__boardCode, 1));
				body = body.replace("${article-detail__link-list-class-addi}", "");
				body = body.replace("${article-detail__link-next-article-url}", getArticleDetailFileName(nextArticleId));
				body = body.replace("${article-detail__link-next-article-class-addi}", nextArticleId == 0 ? "none" : "");

				body = body.replace("${site-domain}", "ssg.modify.kr");
				body = body.replace("${file-name}", getArticleDetailFileName(article.getId()));

				String fileName = getArticleDetailFileName(article.getId());

				body = body.replace("${article-detail__comments}", "<i class=\"far fa-comments\"></i>&nbsp<a href=\"https://ssg.modify.kr/" + fileName + "#disqus_thread\">(Second article)</a>");

				sb.append(body);
				sb.append(foot);

				String filePath = "site/" + fileName;

				Util.writerFile(filePath, sb.toString());

				System.out.println(filePath + " 생성");
			}

		}

		// 게시물 상세 페이지 끝

	}

	public String getArticleDetailFileName(int id) {
		return "article_detail_" + id + ".html";
	}

	private String getHeadHtml(String pageName) {
		return getHeadHtml(pageName, null);
	}

	private String getHeadHtml(String pageName, Object relObj) {
		String head = Util.getFileContents("site_template/head.html");
		StringBuilder boardMenuContentHtml = new StringBuilder();
		List<Board> boards = articleService.getBoards();

		for (Board board : boards) {
			boardMenuContentHtml.append("<li>");

			String link = getArticleListFileName(board, 1);

			boardMenuContentHtml.append("<a href=\"" + link + "\" class=\"block\">");

			boardMenuContentHtml.append(getTitleBarContentByPageName("article_list_" + board.getCode()));

			boardMenuContentHtml.append("</a>");

			boardMenuContentHtml.append("</li>");
		}

		head = head.replace("${menu-bar__menu-1__board-menu-content}", boardMenuContentHtml);

		String mainContentHtml = getMainContentByPageName(pageName);

		head = head.replace("${main__content}", mainContentHtml);

		String pageTitle = getPageTitle(pageName, relObj);

		head = head.replace("${page-title}", pageTitle);

		String siteName = Container.config.getSiteName();
		String siteSubject = "풀스택 개발자 지망생의 기술/일상 블로그";
		String siteDescription = "풀스택 개발자가 되기위한 기술/일상 관련 글들을 공유합니다.";
		String siteKeywords = "JAVA, MySQL, HTML, CSS, JAVASCRIPT, SPRING, CODE, CODING";
		String siteDomain = "ssg.modify.kr";
		String siteMainUrl = "https://" + siteDomain;
		String currentDate = Util.getNowDateStr().replace(" ", "T");

		head = head.replace("${site-name}", siteName);
		head = head.replace("${site-subject}", siteSubject);
		head = head.replace("${site-description}", siteDescription);
		head = head.replace("${site-domain}", siteDomain);
		head = head.replace("${current-date}", currentDate);
		head = head.replace("${site-main-url}", siteMainUrl);
		head = head.replace("${site-keywords}", siteKeywords);

		return head;
	}

	private String getMainContentByPageName(String pageName) {
		if (pageName.equals("index")) {
			return "<i class=\"fas fa-home\"></i> <span>Latest Articles</span>";
		} else if (pageName.startsWith("about")) {
			return "<i class=\"fas fa-address-card\"></i> <span>ABOUT</span>";
		} else if (pageName.startsWith("article_list_jsp")) {
			return "<i class=\"fab fa-java\"></i> <span>JSP LIST</span>";
		} else if (pageName.startsWith("article_list_notice")) {
			return "<i class=\"fas fa-flag\"></i> <span>NOTICE LIST</span>";
		} else if (pageName.startsWith("article_list_it")) {
			return "<i class=\"fas fa-laptop-code\"></i> <span>IT LIST</span>";
		} else if (pageName.equals("article_search")) {
			return "<i class=\"fas fa-search\"></i> <span>ARTICLE SEARCH</span>";
		} else if (pageName.startsWith("article_list_codeup")) {
			return "<i class=\"fas fa-laptop-code\"></i> <span>CODEUP LIST</span>";
		}
		return "";
	}

	private String getPageTitle(String pageName, Object relObj) {
		StringBuilder sb = new StringBuilder();

		String forPrintPageName = pageName;

		if (forPrintPageName.equals("index")) {
			forPrintPageName = "home";
		}

		forPrintPageName = forPrintPageName.toUpperCase();
		forPrintPageName = forPrintPageName.replaceAll("_", " ");

		sb.append(Container.config.getSiteName() + " | ");
		sb.append(forPrintPageName);

		if (relObj instanceof Article) {
			Article article = (Article) relObj;

			sb.insert(0, article.getTitle() + " | ");
		}

		return sb.toString();
	}

	private String getTitleBarContentByPageName(String pageName) {
		if (pageName.equals("index")) {
			return "<i class=\"fas fa-home\"></i> <span>HOME</span>";
		} else if (pageName.equals("article_detail")) {
			return "<i class=\"fas fa-file-alt\"></i> <span>ARTICLE DETAIL</span>";
		} else if (pageName.equals("article_search")) {
			return "<i class=\"fas fa-search\"></i> <span>ARTICLE SEARCH</span>";
		} else if (pageName.startsWith("article_list_jsp")) {
			return "<i class=\"fab fa-java\"></i> <span>JSP LIST</span>";
		} else if (pageName.startsWith("article_list_codeup")) {
			return "<i class=\"fas fa-laptop-code\"></i> <span>CODEUP LIST</span>";
		} else if (pageName.startsWith("article_list_notice")) {
			return "<i class=\"fas fa-flag\"></i> <span>NOTICE LIST</span>";
		} else if (pageName.startsWith("article_list")) {
			String boardName = pageName.replace("article_list_", "").toUpperCase();
			return "<i class=\"fas fa-clipboard-list\"></i> <span>" + boardName + " LIST</span>";
		} else if (pageName.startsWith("statistics")) {
			return "<i class=\"fas fa-chart-pie\"></i> <span>STATISTICS</span>";
		} else if (pageName.startsWith("article_list_it")) {
			return "<i class=\"fas fa-laptop-code\"></i> <span>IT LIST</span>";
		} else if (pageName.startsWith("about")) {
			return "<i class=\"fas fa-address-card\"></i> <span>ABOUT</span>";
		}

		return "";
	}

}
