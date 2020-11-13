package com.sbs.example.mysqlTextBoard.controller;

import java.util.List;

import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.service.ArticleService;

public class ArticleController {

	private ArticleService articleService;

	public ArticleController() {
		articleService = new ArticleService();
	}

	public void doCommand(String cmd) {
		if (cmd.equals("article list")) {
			showList(cmd);
		} else if (cmd.startsWith("article detail ")) {
			showDetail(cmd);
		} else if (cmd.startsWith("article delete ")) {
			doDelete(cmd);
		}

	}

	private void doDelete(String cmd) {
		int inputedId = 0;
		try {
			inputedId = Integer.parseInt(cmd.split(" ")[2]);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("게시물 번호를 입력해주세요.");
			return;
		} catch (NumberFormatException e) {
			System.out.println("게시물 번호는 양의 정수를입력해 주세요.");
			return;
		}

		Article article = articleService.getArticleById(inputedId);

		if (article == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", inputedId);
			return;
		}

		articleService.delete(inputedId);
		System.out.printf("%d번 게시물을 삭제하였습니다.\n", inputedId);

	}

	private void showDetail(String cmd) {
		int inputedId = 0;
		try {
			inputedId = Integer.parseInt(cmd.split(" ")[2]);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("게시물 번호를 입력해주세요.");
			return;
		} catch (NumberFormatException e) {
			System.out.println("게시물 번호는 양의 정수를입력해 주세요.");
			return;
		}

		Article article = articleService.getArticleById(inputedId);

		if (article == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", inputedId);
			return;
		}

		System.out.println("== 게시물 상세정보 ==");
		System.out.printf("번호 : %d\n", article.id);
		System.out.printf("등록일자 : %s\n", article.regDate);
		System.out.printf("수정일자 : %s\n", article.updateDate);
		System.out.printf("제목 : %s\n", article.title);
		System.out.printf("내용 : %s\n", article.body);

	}

	public void showList(String cmd) {
		System.out.println("== 게시물 리스트 ==");

		List<Article> articles = articleService.getArticles();

		System.out.println("번호 / 작성 / 수정 /작성자 / 제목");

		for (Article article : articles) {
			System.out.printf("%d / %s / %s / %d / %s\n", article.id, article.regDate, article.updateDate,
					article.memberId, article.title);
		}

	}

}
