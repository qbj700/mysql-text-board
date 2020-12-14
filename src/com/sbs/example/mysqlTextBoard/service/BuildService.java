package com.sbs.example.mysqlTextBoard.service;

import java.util.List;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Board;
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
		buildListPages();
		buildArticleDetailPages();
	}

	private void buildListPages() {
		StringBuilder sb = new StringBuilder();

		String head = "";
		String foot = Util.getFileContents("site_template/foot.html");

		List<Board> boards = articleService.getBoards();

		for (Board board : boards) {
			if (board.code.startsWith("notice")) {
				List<Article> articles = articleService.getForPrintArticles(board.id);
				head = getHeadHtml("article_list_" + board.code);

				sb.append(head);
				sb.append("<section class=\"section-1 con-min-width\">");
				sb.append("<div class=\"con\">");
				sb.append("<div class=\"article-list\">");
				sb.append("<header>");
				sb.append("<div>");

				sb.append("<div class=\"article-list__cell-id\">번호</div>");
				sb.append("<div class=\"article-list__cell-reg-date\">날짜</div>");
				sb.append("<div class=\"article-list__cell-writer\">작성자</div>");
				sb.append("<div class=\"article-list__cell-title\">제목</div>");

				sb.append("</div>");
				sb.append("</header>");

				sb.append("<main>");
				for (Article article : articles) {
					sb.append("<div>");
					sb.append("<div class=\"article-list__cell-id\">" + article.id + "</div>");
					sb.append("<div class=\"article-list__cell-reg-date\">" + article.regDate + "</div>");
					sb.append("<div class=\"article-list__cell-writer\">" + article.extra__writer + "</div>");
					sb.append("<div class=\"article-list__cell-title\">");
					sb.append("<a href=\"article_detail_" + article.id + ".html\" class=\"hover-underline\">");
					sb.append(article.title + "</a></div>");
					sb.append("</div>");
				}
				sb.append("</main>");

				sb.append("</div>");
				sb.append("</div>");
				sb.append("</section>");
				sb.append(foot);

				String fileName = "article_" + board.code + "_list.html";
				String filePath = "site/" + fileName;

				Util.writerFile(filePath, sb.toString());

				System.out.println(filePath + " 생성");

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

		String head = getHeadHtml("article_detail");
		String foot = Util.getFileContents("site_template/foot.html");

		// 게시물 상세 페이지 시작
		for (Article article : articles) {
			StringBuilder sb = new StringBuilder();

			sb.append(head);

			sb.append("번호 : " + article.id + "<br>");
			sb.append("작성날짜 : " + article.regDate + "<br>");
			sb.append("갱신날짜 : " + article.updateDate + "<br>");
			sb.append("제목 : " + article.title + "<br>");
			sb.append("내용 : " + article.body + "<br>");
			if (article.id - 1 != 0) {
				sb.append("<a href=\"article_detail_" + (+article.id - 1) + ".html\">이전글</a><br>");
			}
			if (article.id + 1 <= articles.size()) {
				sb.append("<a href=\"article_detail_" + (+article.id + 1) + ".html\">다음글</a><br>");
			}

			sb.append("</div>");

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

			String link = board.code + "-list-1.html";

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
