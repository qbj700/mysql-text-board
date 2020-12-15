# DB 생성
DROP DATABASE IF EXISTS textBoard;
CREATE DATABASE textBoard;
USE textBoard;
 
# 게시물 테이블 생성
CREATE TABLE article (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(200) NOT NULL,
    `body` TEXT NOT NULL,
    hit INT(10) UNSIGNED NOT NULL,
    memberId INT(10) UNSIGNED NOT NULL,
    boardId INT(10) UNSIGNED NOT NULL
);
 
# 게시물 데이터 3개 생성
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목1',
`body` = '내용1',
hit = 0,
memberId = 1,
boardId = 1;
 
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목2',
`body` = '내용2',
hit = 0,
memberId = 1,
boardId = 1;
 
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목3',
`body` = '내용3',
hit = 0,
memberId = 1,
boardId = 1;
 
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목4',
`body` = '내용4',
memberId = 1,
boardId = 1;

#회원 테이블 생성
CREATE TABLE `member`(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    loginId VARCHAR(100) NOT NULL,
    loginPw VARCHAR(100) NOT NULL,
    `name` VARCHAR(50) NOT NULL
);

# 1번 임시 회원 생성
INSERT INTO `member`
SET regDate = NOW(),
loginId = 'aaa',
loginPw = 'aaa',
NAME = 'aaa';

# 2번 임시 회원 생성
INSERT INTO `member`
SET regDate = NOW(),
loginId = 'bbb',
loginPw = 'bbb',
NAME = 'bbb';


CREATE TABLE board(
    id INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `name` CHAR(50) NOT NULL,
    `code` CHAR(20) NOT NULL
);

# 1번 게시판 생성
INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`name` = '공지사항',
`code` = 'notice';

# 2번 게시판 생성
INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`name` = '자유',
`code` = 'free';

CREATE TABLE articleReply(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    memberId INT(10) NOT NULL,
    articleId INT(10) NOT NULL,
    reply TEXT NOT NULL
);

CREATE TABLE recommand(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    memberId INT(10) NOT NULL,
    articleId INT(10) NOT NULL
);

SELECT * FROM article


# 테스트 데이터 랜덤 생성
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = CONCAT('제목_', RAND()),
`body` = CONCAT('내용_', RAND()),
hit = 0,
memberId = IF(RAND() > 0.5, 1, 2),
boardId = IF(RAND() > 0.5, 1, 2);

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = CONCAT('제목_', RAND()),
`body` = CONCAT('내용_', RAND()),
hit = 0,
memberId = IF(RAND() > 0.5, 1, 2),
boardId = IF(RAND() > 0.5, 1, 2);

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = CONCAT('제목_', RAND()),
`body` = CONCAT('내용_', RAND()),
hit = 0,
memberId = IF(RAND() > 0.5, 1, 2),
boardId = IF(RAND() > 0.5, 1, 2);

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = CONCAT('제목_', RAND()),
`body` = CONCAT('내용_', RAND()),
hit = 0,
memberId = IF(RAND() > 0.5, 1, 2),
boardId = IF(RAND() > 0.5, 1, 2);
