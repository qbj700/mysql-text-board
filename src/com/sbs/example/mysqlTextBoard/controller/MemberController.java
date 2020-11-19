package com.sbs.example.mysqlTextBoard.controller;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dto.Member;
import com.sbs.example.mysqlTextBoard.service.MemberService;

public class MemberController extends Controller {

	private MemberService memberService;

	public MemberController() {
		memberService = Container.memberService;
	}

	public void doCommand(String cmd) {
		if (cmd.equals("member join")) {
			doJoin(cmd);
		} else if (cmd.equals("member login")) {
			doLogin(cmd);
		} else if (cmd.equals("member logout")) {
			doLogout(cmd);
		} else if (cmd.equals("member whoami")) {
			showWhoami(cmd);
		} else {
			System.out.println("존재하지 않는 명령어입니다.");
			return;
		}

	}

	private void showWhoami(String cmd) {
		if (Container.session.isLogined() == false) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}

		System.out.println("== 회원 정보 ==");

		int loginedMemberId = Container.session.loginedMemberId;
		Member member = memberService.getMemberByMemberId(loginedMemberId);

		System.out.printf("번호 : %d\n", member.id);
		System.out.printf("가입일자 : %s\n", member.regDate);
		System.out.printf("로그인아이디 : %s\n", member.loginId);
		System.out.printf("이름 : %s\n", member.name);
		System.out.printf("회원종류 : %s\n", member.getType());

	}

	private void doLogout(String cmd) {
		if (Container.session.isLogined() == false) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}

		memberService.logout();
		System.out.println("정상적으로 로그아웃 되었습니다.");

	}

	private void doLogin(String cmd) {
		if (Container.session.isLogined()) {
			System.out.println("이미 로그인 중입니다.");
			return;
		}

		System.out.println("== 로그인 ==");
		String loginId;
		String loginPw;

		System.out.printf("로그인 아이디 : ");
		loginId = Container.scanner.nextLine();

		Member member = memberService.getMemberByLoginId(loginId);
		if (member == null) {
			System.out.printf("%s(은)는 존재하지 않는 로그인 아이디입니다.\n", loginId);
			return;
		}

		System.out.printf("로그인 비밀번호 : ");
		loginPw = Container.scanner.nextLine();
		if (member.loginPw.equals(loginPw) == false) {
			System.out.println("비밀번호가 일치하지 않습니다.");
			return;
		}

		Container.session.login(member.id);
		System.out.printf("로그인 성공, %s님 환영합니다.\n", member.name);

	}

	private void doJoin(String cmd) {
		if (Container.session.isLogined()) {
			System.out.println("로그아웃 후에 이용해주세요.");
			return;
		}
		System.out.println("== 회원 가입 ==");
		String loginId;
		String loginPw;
		String name;

		System.out.printf("로그인 아이디 : ");
		loginId = Container.scanner.nextLine();

		Member member = memberService.getMemberByLoginId(loginId);
		if (member != null) {
			System.out.printf("%s(은)는 이미 존재하는 로그인 아이디입니다.\n", loginId);
			return;
		}

		System.out.printf("로그인 비밀번호 : ");
		loginPw = Container.scanner.nextLine();
		System.out.printf("사용자 이름 : ");
		name = Container.scanner.nextLine();

		int id = memberService.join(loginId, loginPw, name);
		System.out.printf("%d번 회원이 생성되었습니다.\n", id);

	}

}
