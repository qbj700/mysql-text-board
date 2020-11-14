package com.sbs.example.mysqlTextBoard;

import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.controller.ArticleController;
import com.sbs.example.mysqlTextBoard.controller.Controller;
import com.sbs.example.mysqlTextBoard.controller.MemberController;
import com.sbs.example.mysqlTextBoard.service.ArticleService;

public class App {

	private ArticleController articleController;
	private MemberController memberController;

	public App() {
		articleController = Container.articleController;
		memberController = Container.memberController;

		makeTestData();

		init();
	}

	private void makeTestData() {
		// 만약 공지사항 게시판이 존재하지 않다면 생성
		ArticleService articleService = Container.articleService;
		if (articleService.selectBoardByBoardId(1) == null) {
			articleService.saveBoardData("공지사항");
		}
		// 만약 자유 게시판이 존재하지 않다면 생성
		if (articleService.selectBoardByBoardId(2) == null) {
			articleService.saveBoardData("자유");
		}

	}

	private void init() {
		// 기본 게시판을 공지사항 게시판으로 설정
		ArticleService articleService = Container.articleService;
		Container.session.selectedBoardId = articleService.boardDefaultSetting();
	}

	public void run() {
		Scanner sc = Container.scanner;

		while (true) {
			System.out.printf("명령어) ");
			String cmd = sc.nextLine();

			if (cmd.equals("system exit")) {
				System.out.println("== 시스템 종료 ==");
				break;
			}

			Controller controller = getControllerByCmd(cmd);
			if (controller == null) {
				System.out.println("존재하지 않는 명령어 입니다.");
				continue;
			}
			controller.doCommand(cmd);

		}
		sc.close();

	}

	private Controller getControllerByCmd(String cmd) {
		if (cmd.startsWith("article ")) {
			return articleController;
		} else if (cmd.startsWith("member ")) {
			return memberController;
		}
		return null;
	}
}
