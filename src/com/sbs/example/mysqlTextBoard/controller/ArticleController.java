package com.sbs.example.mysqlTextBoard.controller;

import java.util.List;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Member;
import com.sbs.example.mysqlTextBoard.service.ArticleService;
import com.sbs.example.mysqlTextBoard.service.MemberService;

public class ArticleController {

	private ArticleService articleService;
	private MemberService memberService;

	public ArticleController() {
		articleService = Container.articleService;
		memberService = Container.memberService;
	}

	public void doCommand(String cmd) {
		if (cmd.startsWith("article list")) {
			showList(cmd);
		} else if (cmd.startsWith("article detail ")) {
			showDetail(cmd);
		} else if (cmd.startsWith("article delete ")) {
			doDelete(cmd);
		} else if (cmd.startsWith("article modify ")) {
			doModify(cmd);
		} else if (cmd.startsWith("article write")) {
			doWrite(cmd);
		}

	}

	private void doWrite(String cmd) {
		System.out.println("== 게시물 작성 ==");

		System.out.printf("제목 : ");
		String title = Container.scanner.nextLine();
		System.out.printf("내용 : ");
		String body = Container.scanner.nextLine();

		int memberId = Container.session.loginedMemberId;
		int boardId = 1; // 임시

		int id = articleService.saveArticle(title, body, memberId, boardId);
		System.out.printf("%d번 게시물이 생성되었습니다.\n", id);

	}

	private void doModify(String cmd) {
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

		System.out.println("== 게시물 수정 ==");

		System.out.printf("수정할 제목 : ");
		String title = Container.scanner.nextLine();
		System.out.printf("수정할 내용 : ");
		String body = Container.scanner.nextLine();

		articleService.modify(inputedId, title, body);
		System.out.printf("%d번 게시물이 수정되었습니다.\n", inputedId);

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

		Member member = memberService.getMemberByMemberId(article.memberId);

		System.out.println("== 게시물 상세정보 ==");
		System.out.printf("번호 : %d\n", article.id);
		System.out.printf("작성자 : %s\n", member.name);
		System.out.printf("등록일자 : %s\n", article.regDate);
		System.out.printf("수정일자 : %s\n", article.updateDate);
		System.out.printf("제목 : %s\n", article.title);
		System.out.printf("내용 : %s\n", article.body);

	}

	public void showList(String cmd) {
		System.out.println("== 게시물 리스트 ==");

		List<Article> articles = articleService.getArticles();

		System.out.println("번호 / 작성일 / 수정일 / 작성자 / 제목");

		for (Article article : articles) {
			Member member = memberService.getMemberByMemberId(article.memberId);
			System.out.printf("%d / %s / %s / %s / %s\n", article.id, article.regDate, article.updateDate, member.name,
					article.title);
		}

	}

}
