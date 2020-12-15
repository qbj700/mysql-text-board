package com.sbs.example.mysqlTextBoard.service;

import java.util.List;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Board;
import com.sbs.example.mysqlTextBoard.dto.Member;
import com.sbs.example.mysqlTextBoard.util.Util;

public class BuildService {
	private ArticleService articleService;
	private MemberService memberService;

	public BuildService() {
		articleService = Container.articleService;
		memberService = Container.memberService;
	}

	public void buildSite() {
		System.out.println("site 폴더 생성");
		Util.rmdir("site");
		Util.mkdir("site");

		Util.copy("site_template/app.css", "site/app.css");

		buildIndexPage();
		buildArticleListPages();
		buildArticleDetailPages();
	}

	private void buildArticleListPage(Board board, int itemsInAPage, int pageBoxMenuSize, List<Article> articles,
			int page) {
		StringBuilder sb = new StringBuilder();

		// 헤더 시작
		sb.append(getHeadHtml("article_list_" + board.code));

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

			String link = "article_detail_" + article.id + ".html";

			mainContent.append("<div>");
			mainContent.append("<div class=\"article-list__cell-id\">" + article.id + "</div>");
			mainContent.append("<div class=\"article-list__cell-reg-date\">" + article.regDate + "</div>");
			mainContent.append("<div class=\"article-list__cell-writer\">" + article.extra__writer + "</div>");
			mainContent.append("<div class=\"article-list__cell-title\">");

			mainContent.append("<a href=\"" + link + "\" class=\"hover-underline\">" + article.title + "</a>");

			mainContent.append("</div>");
			mainContent.append("</div>");

		}
		StringBuilder pageMenuContent = new StringBuilder();

		pageMenuContent.append(" <li><a href=\"#\" class=\"flex flex-ai-c\">&lt; 이전</a></li>");
		pageMenuContent
				.append("<li><a href=\"#\"class=\"flex flex-ai-c article-page-menu__link--selected\">1</a></li>");
		pageMenuContent.append("<li><a href=\"#\" class=\"flex flex-ai-c\">다음 &gt;</a></li> ");

		String body = bodyTemplate.replace("${article-list__main-content}", mainContent.toString());
		body = body.replace("${article-page-menu__content}", pageMenuContent.toString());

		sb.append(body);

		// 푸터 시작
		sb.append(Util.getFileContents("site_template/foot.html"));

		// 파일 생성 시작
		String fileName = "article_list_" + board.code + "_" + page + ".html";
		String filePath = "site/" + fileName;

		Util.writerFile(filePath, sb.toString());

		System.out.println(filePath + " 생성");
	}

	private void buildArticleListPages() {
		List<Board> boards = articleService.getBoards();

		int itemsInAPage = 10;
		int pageBoxMenuSize = 10;

		for (Board board : boards) {

			List<Article> articles = articleService.getForPrintArticles(board.id);
			int articlesCount = articles.size();
			int totalPage = (int) Math.ceil((double) articlesCount / itemsInAPage);

			for (int i = 1; i <= totalPage; i++) {
				buildArticleListPage(board, itemsInAPage, pageBoxMenuSize, articles, i);
			}

		}

	}

	private void buildIndexPage() {
		StringBuilder sb = new StringBuilder();

		String head = getHeadHtml("index");
		String foot = Util.getFileContents("site_template/foot.html");

		String mainHtml = Util.getFileContents("site_template/index.html");

		sb.append(head);
		sb.append(mainHtml);
		sb.append(foot);

		String filePath = "site/index.html";
		Util.writerFile(filePath, sb.toString());
		System.out.println(filePath + " 생성");

	}

	private void buildArticleDetailPages() {
		List<Article> articles = articleService.getArticles();

		String bodyTemplate = Util.getFileContents("site_template/article_detail.html");
		String foot = Util.getFileContents("site_template/foot.html");

		// 게시물 상세 페이지 시작

		for (Article article : articles) {
			StringBuilder sb = new StringBuilder();
			StringBuilder articleDetailContent = new StringBuilder();

			String body = bodyTemplate;
			sb.append(getHeadHtml("article_detail"));
			Board board = articleService.getBoardByBoardId(article.boardId);

			String link = "article_list_" + board.code + "_1.html";
			int recommandCount = articleService.getRecommandsCount(article.id);
			Member member = memberService.getMemberByMemberId(article.memberId);
			String writer = member.name;

			articleDetailContent.append(
					"<div class=\"article-detail__title\" style=\"font-size:2rem\">" + article.title + "</div>");
			articleDetailContent.append("<div class=\"article-detail__meta-data flex flex-jc-sb\">");
			articleDetailContent.append("<div class=\"meta-data__left\">");
			articleDetailContent.append("<span>게시판 : " + board.name + "</span>");
			articleDetailContent.append("<span>작성일 : " + article.regDate + "</span>");
			articleDetailContent.append("<span>작성자 : " + writer + "</span></div>");
			articleDetailContent.append("<div class=\"meta-data__right\">");
			articleDetailContent.append("<span>번호 : " + article.id + "</span>");
			articleDetailContent.append("<span>조회수 : " + article.hit + "</span>");
			articleDetailContent.append("<span>추천수 : " + recommandCount + "</span></div></div>");
			articleDetailContent.append("<div class=\"article-detail__body\">" + article.body + "</div>");
			articleDetailContent.append("<div class=\"article-detail__menu flex flex-jc-c\">");
			if (article.id - 1 != 0) {
				articleDetailContent
						.append("<span><a href=\"article_detail_" + (+article.id - 1) + ".html\">&lt; 이전 글</a></span>");
			}
			articleDetailContent.append("<span><a href=\"" + link + "\">리스트</a></span>");

			if (article.id + 1 <= articles.size()) {
				articleDetailContent.append(
						"<span><a href=\"article_detail_" + (+article.id + 1) + ".html\">다음 글 &gt;</a></span> </div>");
			}
			body = bodyTemplate.replace("${article-detail__content}", articleDetailContent.toString());

			sb.append(body);
			sb.append(foot);

			String fileName = "article_detail_" + article.id + ".html";
			String filePath = "site/" + fileName;

			Util.writerFile(filePath, sb.toString());

			System.out.println(filePath + " 생성");
		}

		// 게시물 상세 페이지 끝

	}

	private String getHeadHtml(String pageName) {
		String head = Util.getFileContents("site_template/head.html");
		StringBuilder boardMenuContentHtml = new StringBuilder();
		List<Board> boards = articleService.getBoards();

		for (Board board : boards) {
			boardMenuContentHtml.append("<li>");

			String link = "article_list_" + board.code + "_1.html";

			boardMenuContentHtml.append("<a href=\"" + link + "\" class=\"block\">");

			boardMenuContentHtml.append(getTitleBarContentByPageName("article_list_" + board.code));

			boardMenuContentHtml.append("</a>");

			boardMenuContentHtml.append("</li>");
		}

		head = head.replace("${menu-bar__menu-1__board-menu-content}", boardMenuContentHtml);

		String titleBarContentHtml = getTitleBarContentByPageName(pageName);

		head = head.replace("${title-bar__content}", titleBarContentHtml);

		return head;
	}

	private String getTitleBarContentByPageName(String pageName) {
		if (pageName.equals("index")) {
			return "<i class=\"fas fa-home\"></i><span>HOME</span>";
		} else if (pageName.equals("article_detail")) {
			return "<i class=\"fas fa-file-alt\"></i><span>ARTICLE DETAIL</span>";
		} else if (pageName.startsWith("article_list_free")) {
			return "<i class=\"fab fa-free-code-camp\"></i><span>FREE LIST</span>";
		} else if (pageName.startsWith("article_list_notice")) {
			return "<i class=\"fas fa-flag\"></i><span>NOTICE LIST</span>";
		} else if (pageName.startsWith("article_list")) {
			return "<i class=\"fas fa-clipboard-list\"></i><span>LIST</span>";
		}

		return "";
	}

}
