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

		List<Article> articles = articleService.getArticles();

		// 게시물 상세 페이지 시작
		String head = Util.getFileContents("site_template/head.html");

		String boardListHtml = "";
		List<Board> boards = articleService.getBoards();

		String noticeIcon = "<i class=\"fas fa-flag\">";
		String freeIcon = "<i class=\"fab fa-free-code-camp\">";
		String elseIcon = "<i class=\"fas fa-stream\">";

		for (Board board : boards) {
			String icon = "";

			if (board.code.contains("free")) {
				icon += freeIcon;
			} else if (board.code.contains("notice")) {
				icon += noticeIcon;
			} else {
				icon += elseIcon;
			}

			boardListHtml += "<li><a href=\"#\" class=\"block\">" + icon + "</i><span>" + board.name
					+ "</span></a></li>";
		}

		head = head.replace("[[existsBoardList]]", boardListHtml);

		String foot = Util.getFileContents("site_template/foot.html");

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

}
