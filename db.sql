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
    memberId INT(10) UNSIGNED NOT NULL,
    boardId INT(10) UNSIGNED NOT NULL
);
 
# 게시물 데이터 3개 생성
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목1',
`body` = '내용1',
memberId = 1,
boardId = 1;
 
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목2',
`body` = '내용2',
memberId = 1,
boardId = 1;
 
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목3',
`body` = '내용3',
memberId = 1,
boardId = 1;
 
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목4',
`body` = '내용4',
memberId = 1,
boardId = 1;
 
SELECT * FROM article;
 
UPDATE article
SET updatedate = NOW()
WHERE id = 3;
 
SELECT * FROM article;

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

SHOW TABLES;

DESC `member`;

SELECT * FROM `member`;

SELECT * FROM MEMBER WHERE loginId = 'aaa';