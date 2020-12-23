package com.sbs.example.mysqlTextBoard.service;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dao.MemberDao;
import com.sbs.example.mysqlTextBoard.dto.Member;

public class MemberService {

	private MemberDao memberDao;

	public MemberService() {
		memberDao = Container.memberDao;

	}

	public int join(String loginId, String loginPw, String name) {
		return memberDao.saveMemberData(loginId, loginPw, name);

	}

	public Member getMemberByLoginId(String loginId) {
		return memberDao.getMemberByLoginId(loginId);
	}

	public Member getMemberByMemberId(int memberId) {
		return memberDao.getMemberByMemberId(memberId);
	}

	public void logout() {
		memberDao.logout();

	}

	public int getMembersCount() {
		return memberDao.getMembersCount();
	}
}
