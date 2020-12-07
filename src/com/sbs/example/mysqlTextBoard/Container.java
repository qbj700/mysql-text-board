package com.sbs.example.mysqlTextBoard;

import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.controller.ArticleController;
import com.sbs.example.mysqlTextBoard.controller.BuildController;
import com.sbs.example.mysqlTextBoard.controller.MemberController;
import com.sbs.example.mysqlTextBoard.dao.ArticleDao;
import com.sbs.example.mysqlTextBoard.dao.MemberDao;
import com.sbs.example.mysqlTextBoard.service.ArticleService;
import com.sbs.example.mysqlTextBoard.service.BuildService;
import com.sbs.example.mysqlTextBoard.service.MemberService;
import com.sbs.example.mysqlTextBoard.session.Session;

public class Container {

	public static Scanner scanner;
	public static Session session;

	public static ArticleController articleController;
	public static MemberController memberController;
	public static BuildController buildController;

	public static ArticleService articleService;
	public static MemberService memberService;
	public static BuildService buildService;

	public static ArticleDao articleDao;
	public static MemberDao memberDao;

	static {
		scanner = new Scanner(System.in);
		session = new Session();

		articleDao = new ArticleDao();
		memberDao = new MemberDao();

		articleService = new ArticleService();
		memberService = new MemberService();
		buildService = new BuildService();

		articleController = new ArticleController();
		memberController = new MemberController();
		buildController = new BuildController();
	}
}
