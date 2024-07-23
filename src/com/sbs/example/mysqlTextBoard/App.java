package com.sbs.example.mysqlTextBoard;

import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.controller.ArticleController;
import com.sbs.example.mysqlTextBoard.controller.BuildController;
import com.sbs.example.mysqlTextBoard.controller.Controller;
import com.sbs.example.mysqlTextBoard.controller.MemberController;
import com.sbs.example.mysqlTextBoard.util.MysqlUtil;

public class App {

	private ArticleController articleController;
	private MemberController memberController;
	private BuildController buildController;

	public App() {
		articleController = Container.articleController;
		memberController = Container.memberController;
		buildController = Container.buildController;

	}

	public void run() {
		Scanner sc = Container.scanner;

		while (true) {
			System.out.printf("명령어) ");
			String cmd = sc.nextLine();

			// DB 접속
			MysqlUtil.setDBInfo("172.19.91.73:3306", "sbsst", "sbs123414", "textBoard");

			// 개발자모드 (true = 명령어 출력 / false = 명령어 출력 X)
			MysqlUtil.setDevMode(false);

			if (cmd.equals("system exit")) {
				System.out.println("== 시스템 종료 ==");
				MysqlUtil.closeConnection();
				break;
			}

			Controller controller = getControllerByCmd(cmd);
			if (controller == null) {
				System.out.println("존재하지 않는 명령어 입니다.");
				MysqlUtil.closeConnection();
				continue;
			}
			controller.doCommand(cmd);

			// DB접속 종료
			MysqlUtil.closeConnection();

		}
		sc.close();

	}

	private Controller getControllerByCmd(String cmd) {
		if (cmd.startsWith("article ")) {
			return articleController;
		} else if (cmd.startsWith("member ")) {
			return memberController;
		} else if (cmd.startsWith("build ")) {
			return buildController;
		}
		return null;
	}
}
