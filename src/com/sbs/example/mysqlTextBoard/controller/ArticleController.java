package com.sbs.example.mysqlTextBoard.controller;

import java.util.ArrayList;
import java.util.List;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Board;
import com.sbs.example.mysqlTextBoard.dto.Recommand;
import com.sbs.example.mysqlTextBoard.dto.Reply;
import com.sbs.example.mysqlTextBoard.service.ArticleService;

public class ArticleController extends Controller {

	private ArticleService articleService;

	public ArticleController() {
		articleService = Container.articleService;

	}

	public void doCommand(String cmd) {
		if (cmd.startsWith("article list ")) {
			showList(cmd);
		} else if (cmd.startsWith("article list")) {
			showList(cmd);
		} else if (cmd.startsWith("article detail ")) {
			showDetail(cmd);
		} else if (cmd.startsWith("article delete ")) {
			doDelete(cmd);
		} else if (cmd.startsWith("article deleteReply ")) {
			doDeleteReply(cmd);
		} else if (cmd.startsWith("article modify ")) {
			doModify(cmd);
		} else if (cmd.startsWith("article modifyReply ")) {
			doModifyReply(cmd);
		} else if (cmd.equals("article write")) {
			doWrite(cmd);
		} else if (cmd.startsWith("article makeBoard")) {
			doMakeBoard(cmd);
		} else if (cmd.startsWith("article selectBoard")) {
			doSelectBoard(cmd);
		} else if (cmd.startsWith("article writeReply ")) {
			doWriteReply(cmd);
		} else if (cmd.startsWith("article recommand ")) {
			doRecommand(cmd);
		} else {
			System.out.println("존재하지 않는 명령어입니다.");
			return;
		}

	}

	private void doRecommand(String cmd) {
		if (Container.session.isLogined() == false) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}
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

		int loginedMemberId = Container.session.loginedMemberId;

		List<Recommand> recommands = articleService.getRecommandsById(inputedId);

		for (Recommand recommand : recommands) {
			if (recommand.articleId == inputedId) {
				if (recommand.memberId == loginedMemberId) {
					System.out.printf("이미 %d번 게시물을 추천하였습니다.\n", inputedId);
					return;
				}
			}
		}
		articleService.doRecommand(inputedId, loginedMemberId);
		System.out.printf("%d번 게시물을 추천하였습니다.\n", inputedId);

	}

	private void doModifyReply(String cmd) {
		if (Container.session.isLogined() == false) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}

		int inputedId = 0;
		try {
			inputedId = Integer.parseInt(cmd.split(" ")[2]);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("댓글 번호를 입력해주세요.");
			return;
		} catch (NumberFormatException e) {
			System.out.println("댓글 번호는 양의 정수를입력해 주세요.");
			return;
		}

		Reply reply = articleService.getReplyById(inputedId);

		if (reply == null) {
			System.out.printf("%d번 댓글은 존재하지 않습니다.\n", inputedId);
			return;
		}
		if (reply.memberId != Container.session.loginedMemberId) {
			System.out.println("수정할 권한이 없습니다. (작성자만 수정 가능)");
			return;
		}

		System.out.println("== 댓글 수정 ==");

		System.out.printf("수정할 내용 : ");
		String modifiedReply = Container.scanner.nextLine();

		articleService.modifyReply(inputedId, modifiedReply);
		System.out.printf("%d번 댓글이 수정되었습니다.\n", inputedId);

	}

	private void doDeleteReply(String cmd) {
		if (Container.session.isLogined() == false) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}

		int inputedId = 0;
		try {
			inputedId = Integer.parseInt(cmd.split(" ")[2]);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("댓글 번호를 입력해주세요.");
			return;
		} catch (NumberFormatException e) {
			System.out.println("댓글 번호는 양의 정수를입력해 주세요.");
			return;
		}

		Reply reply = articleService.getReplyById(inputedId);
		if (reply == null) {
			System.out.printf("%d번 댓글은 존재하지 않습니다.\n", inputedId);
			return;
		}

		if (reply.memberId != Container.session.loginedMemberId) {
			System.out.println("삭제할 권한이 없습니다. (작성자만 삭제 가능)");
			return;
		}

		articleService.deleteReply(inputedId);
		System.out.printf("%d번 댓글을 삭제하였습니다.\n", inputedId);

	}

	private void doWriteReply(String cmd) {
		if (Container.session.isLogined() == false) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}

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

		String writer = article.extra__writer;

		System.out.println("== 게시물 정보 ==");
		System.out.printf("번호 : %d\n", article.id);
		System.out.printf("작성자 : %s\n", writer);
		System.out.printf("등록일자 : %s\n", article.regDate);
		System.out.printf("수정일자 : %s\n", article.updateDate);
		System.out.printf("제목 : %s\n", article.title);
		System.out.printf("내용 : %s\n\n", article.body);

		System.out.println("== 댓글 등록 ==");
		System.out.printf("입력할 댓글 : ");
		String reply = Container.scanner.nextLine();

		int loginedMemberId = Container.session.loginedMemberId;

		int id = articleService.addReply(inputedId, loginedMemberId, reply);
		System.out.printf("%d번 게시물의 %d번 댓글이 등록되었습니다.\n", inputedId, id);

	}

	private void doSelectBoard(String cmd) {
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

		Board board = articleService.selectBoardByBoardId(inputedId);
		if (board == null) {
			System.out.printf("%d번 게시판은 존재하지 않습니다.\n", inputedId);
			return;
		}

		Container.session.selectBoard(board.boardId);
		System.out.printf("%s (%d번) 게시판이 선택되었습니다.\n", board.boardName, board.boardId);

	}

	private void doMakeBoard(String cmd) {
		System.out.println("== 게시판 등록 ==");

		System.out.printf("게시판 이름 : ");
		String boardName = Container.scanner.nextLine();

		int boardId = articleService.saveBoardData(boardName);
		System.out.printf("%s (%d번) 게시판이 생성되었습니다.\n", boardName, boardId);

	}

	private void doWrite(String cmd) {
		if (Container.session.isLogined() == false) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}

		System.out.println("== 게시물 작성 ==");

		System.out.printf("제목 : ");
		String title = Container.scanner.nextLine();
		System.out.printf("내용 : ");
		String body = Container.scanner.nextLine();

		int memberId = Container.session.loginedMemberId;
		int boardId = Container.session.selectedBoardId;

		int id = articleService.saveArticle(title, body, memberId, boardId);
		System.out.printf("%d번 게시물이 생성되었습니다.\n", id);

	}

	private void doModify(String cmd) {
		if (Container.session.isLogined() == false) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}

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
		if (article.memberId != Container.session.loginedMemberId) {
			System.out.println("수정할 권한이 없습니다. (작성자만 수정 가능)");
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
		if (Container.session.isLogined() == false) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}

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
		if (article.memberId != Container.session.loginedMemberId) {
			System.out.println("삭제할 권한이 없습니다. (작성자만 삭제 가능)");
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

		Article article = articleService.getForPrintArticleById(inputedId);

		if (article == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", inputedId);
			return;
		}
		articleService.incrementHit(inputedId);

		String writer = article.extra__writer;

		System.out.println("== 게시물 상세정보 ==");
		System.out.printf("번호 : %d\n", article.id);
		System.out.printf("조회수 : %d\n", article.hit + 1);
		System.out.printf("작성자 : %s\n", writer);
		System.out.printf("등록일자 : %s\n", article.regDate);
		System.out.printf("수정일자 : %s\n", article.updateDate);
		System.out.printf("제목 : %s\n", article.title);
		System.out.printf("내용 : %s\n\n", article.body);

		List<Reply> replies = articleService.getForPrintRepliesById(inputedId);
		System.out.println("== 댓글 리스트 ==");

		if (replies.size() == 0) {
			System.out.println("댓글이 존재하지 않습니다.");
			return;
		}

		for (int i = 0; i < replies.size(); i++) {
			Reply reply = replies.get(i);

			System.out.printf("== %d번 댓글 ==\n", reply.id);
			System.out.printf("작성자 : %s\n", reply.extra__writer);
			System.out.printf("작성일자 : %s\n", reply.regDate);
			System.out.printf("수정일자 : %s\n", reply.updateDate);
			System.out.printf("내용 : %s\n\n", reply.reply);
		}

	}

	public void showList(String cmd) {
		int page = 0;

		try {
			page = Integer.parseInt(cmd.split(" ")[2]);
		} catch (ArrayIndexOutOfBoundsException e) {
			page = 1;
		} catch (NumberFormatException e) {
			System.out.println("페이지 번호를 양의정수로 입력해주세요.");
			return;
		}

		if (page <= 1) {
			page = 1;
		}

		List<Article> articles = articleService.getForPrintArticles();
		List<Article> selectedBoardArticles = new ArrayList<>();
		for (Article article : articles) {
			if (article.boardId == Container.session.selectedBoardId) {
				selectedBoardArticles.add(article);
			}
		}

		Board board = articleService.selectBoardByBoardId(Container.session.selectedBoardId);

		System.out.printf("== %s 게시판 게시물 리스트 ==\n", board.boardName);

		if (selectedBoardArticles.size() == 0) {
			System.out.println("게시물이 존재하지 않습니다.");
			return;
		}

		int itemsInAPage = 10;
		int startPos = selectedBoardArticles.size() - 1;
		startPos -= (page - 1) * itemsInAPage;
		int endPos = startPos - (itemsInAPage - 1);

		if (startPos < 0) {
			System.out.printf("%d 페이지는 존재하지 않습니다.\n", page);
			return;
		}
		if (endPos < 0) {
			endPos = 0;
		}

		System.out.println("번호 / 작성일 / 수정일 / 작성자 / 제목 / 조회수");

		for (int i = startPos; i >= endPos; i--) {
			Article article = selectedBoardArticles.get(i);
			String writer = article.extra__writer;
			System.out.printf("%d / %s / %s / %s / %s / %d\n", article.id, article.regDate, article.updateDate, writer,
					article.title, article.hit);
		}

	}

}
