package com.sbs.example.mysqlTextBoard.controller;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dto.Member;
import com.sbs.example.mysqlTextBoard.service.MemberService;

public class MemberController {

	private MemberService memberService;

	public MemberController() {
		memberService = Container.memberService;
	}

	public void doCommand(String cmd) {
		if (cmd.equals("member join")) {
			doJoin(cmd);
		} else if (cmd.equals("member login")) {
			doLogin(cmd);
		}

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

		Container.session.login(member.memberId);
		System.out.printf("로그인 성공, %s님 환영합니다.\n", member.name);

	}

	private void doJoin(String cmd) {
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
