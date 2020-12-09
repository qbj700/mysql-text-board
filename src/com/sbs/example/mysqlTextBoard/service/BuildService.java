package com.sbs.example.mysqlTextBoard.service;

import java.util.List;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dto.Article;
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

		List<Article> articles = articleService.getArticles();

		// 게시물 상세 페이지 시작
		String head = Util.getFileContents("site_template/head.html");
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

//		// 메인 페이지 시작
//		StringBuilder mainPage = new StringBuilder();
//
//		mainPage.append("<!DOCTYPE html");
//		mainPage.append("<html lang=\"ko\">");
//
//		mainPage.append("<head>");
//		mainPage.append("<meta charset=\"UTF-8\">");
//		mainPage.append(" <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
//		mainPage.append("<title>메인 페이지</title>");
//		mainPage.append("</head>");
//
//		mainPage.append("<body>");
//		mainPage.append("<h1>메인 페이지</h1>");
//		mainPage.append("<div>");
//		mainPage.append("<a href=\"list.html\">게시물 리스트</a><br>");
//		mainPage.append("</div>");
//		mainPage.append("</body>");
//
//		mainPage.append("</html>");
//
//		String mainPageFileName = "mainPage.html";
//		String mainPageFilePath = "site/" + mainPageFileName;
//
//		Util.writerFile(mainPageFilePath, mainPage.toString());
//		System.out.println(mainPageFilePath + " 생성");
//		// 메인 페이지 끝
//
//		// 게시물 리스트 페이지 시작
//		StringBuilder listPage = new StringBuilder();
//
//		listPage.append("<!DOCTYPE html");
//		listPage.append("<html lang=\"ko\">");
//
//		listPage.append("<head>");
//		listPage.append("<meta charset=\"UTF-8\">");
//		listPage.append(" <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
//		listPage.append("<title>게시물 리스트</title>");
//		listPage.append("</head>");
//
//		listPage.append("<body>");
//		listPage.append("<h1>게시물 리스트</h1>");
//		listPage.append("<div>");
//		listPage.append("<a href=\"mainPage.html\">메인페이지로 이동</a><br>");
//		listPage.append("번호 / 작성일 / 수정일 / 제목 / 작성자 <br>");
//		for (int i = 0; i < articles.size(); i++) {
//			int id = articles.get(i).id;
//			String title = articles.get(i).title;
//			String regDate = articles.get(i).regDate;
//			String updateDate = articles.get(i).updateDate;
//			Member member = memberService.getMemberByMemberId(articles.get(i).memberId);
//			String writer = member.name;
//
//			listPage.append("<a href=\"" + (+articles.get(i).id) + ".html\">");
//			listPage.append(id + " / " + regDate + " / " + updateDate + " / " + title + " / " + writer);
//			listPage.append("</a><br>");
//
//		}
//		listPage.append("</div>");
//
//		listPage.append("</body>");
//
//		listPage.append("</html>");
//
//		String listFileName = "list.html";
//		String listFilePath = "site/article/" + listFileName;
//
//		Util.writerFile(listFilePath, listPage.toString());
//		System.out.println(listFilePath + " 생성");
//
//		// 게시물 리스트 페이지 끝

	}

}
