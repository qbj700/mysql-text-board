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

CREATE TABLE recommend(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    memberId INT(10) NOT NULL,
    articleId INT(10) NOT NULL
);

SELECT * FROM article


/*

# 테스트 데이터 랜덤 생성
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = CONCAT('제목_', RAND()),
`body` = CONCAT('내용_', RAND()),
hit = 0,
memberId = FLOOR(RAND() * 2) + 1,
boardId = FLOOR(RAND() * 2) + 1;

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = CONCAT('제목_', RAND()),
`body` = CONCAT('내용_', RAND()),
hit = 0,
memberId = FLOOR(RAND() * 2) + 1,
boardId = FLOOR(RAND() * 2) + 1;

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = CONCAT('제목_', RAND()),
`body` = CONCAT('내용_', RAND()),
hit = 0,
memberId = FLOOR(RAND() * 2) + 1,
boardId = FLOOR(RAND() * 2) + 1;




# 1번글 내용을 마크다운 문법으로 수정
UPDATE article SET `body` = '# 공지사항\r\n안녕하세요.\r\n이 사이트는 저의 글 연재 공간입니다.\r\n\r\n---\r\n\r\n# 이 사이트의 특징\r\n- A\r\n- B\r\n- C'
WHERE id = '1';

SELECT * FROM article; 

# 2번 글 내용에 자바 소스코드 넣기
UPDATE article SET `body` = '#자바 기본 문법\r\n```java\r\nint a = 3;\r\nint b = 5;\r\n\r\nint c = a + b;\r\nSystem.out.println(c);\r\n\r\n```' 
WHERE id = '2'; 

*/

# IT 게시판 추가
INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`name` = 'IT',
`code` = 'it';

# 사용자 전부 삭제
TRUNCATE `member`;

# 사용자 추가
INSERT INTO `member`
SET regDate = NOW(),
loginId = 'admin',
loginPw = 'admin',
NAME = '최형석';

# 글 전부 삭제
TRUNCATE article;

# 게시물 테이블에 추천수 칼럼 추가
ALTER TABLE article ADD COLUMN recommendsCount INT(10) UNSIGNED NOT NULL;


# 구글 애널리틱스 4 페이지 경로별 통계 정보
CREATE TABLE ga4DataPagePath (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    pagePath CHAR(100) NOT NULL UNIQUE,
    hit INT(10) UNSIGNED NOT NULL
);

# 1단계, 다 불러오기
SELECT pagePath
FROM ga4DataPagePath AS GA4_PP
WHERE GA4_PP.pagePath LIKE '/article_detail_%.html%'

# 2단계, pagePath 정제
SELECT 
IF(
    INSTR(GA4_PP.pagePath, '?') = 0,
    GA4_PP.pagePath,
    SUBSTR(GA4_PP.pagePath, 1, INSTR(GA4_PP.pagePath, '?') - 1)
) AS pagePathWoQueryStr
FROM ga4DataPagePath AS GA4_PP
WHERE GA4_PP.pagePath LIKE '/article_detail_%.html%'

# 3단계, pagePathWoQueryStr(정제된 pagePth)기준으로 sum
SELECT 
IF(
    INSTR(GA4_PP.pagePath, '?') = 0,
    GA4_PP.pagePath,
    SUBSTR(GA4_PP.pagePath, 1, INSTR(GA4_PP.pagePath, '?') - 1)
) AS pagePathWoQueryStr,
SUM(GA4_PP.hit) AS hit
FROM ga4DataPagePath AS GA4_PP
WHERE GA4_PP.pagePath LIKE '/article_detail_%.html%'
GROUP BY pagePathWoQueryStr

# 4단계, subQuery를 이용
SELECT *
FROM (
    SELECT 
    IF(
        INSTR(GA4_PP.pagePath, '?') = 0,
        GA4_PP.pagePath,
        SUBSTR(GA4_PP.pagePath, 1, INSTR(GA4_PP.pagePath, '?') - 1)
    ) AS pagePathWoQueryStr,
    SUM(GA4_PP.hit) AS hit
    FROM ga4DataPagePath AS GA4_PP
    WHERE GA4_PP.pagePath LIKE '/article_detail_%.html%'
    GROUP BY pagePathWoQueryStr
) AS GA4_PP;

# 5단계, subQuery를 이용해서 나온결과를 다시 재편집
SELECT CAST(REPLACE(REPLACE(GA4_PP.pagePathWoQueryStr, '/article_detail_', ''), '.html', '') AS UNSIGNED) AS articleId,
hit
FROM (
    SELECT 
    IF(
        INSTR(GA4_PP.pagePath, '?') = 0,
        GA4_PP.pagePath,
        SUBSTR(GA4_PP.pagePath, 1, INSTR(GA4_PP.pagePath, '?') - 1)
    ) AS pagePathWoQueryStr,
    SUM(GA4_PP.hit) AS hit
    FROM ga4DataPagePath AS GA4_PP
    WHERE GA4_PP.pagePath LIKE '/article_detail_%.html%'
    GROUP BY pagePathWoQueryStr
) AS GA4_PP;

# 구글 애널리틱스에서 가져온 데이터를 기반으로 모든 게시물의 hit 정보를 갱신

# 1단계, 조인
SELECT AR.id, AR.hit, GA4_PP.hit
FROM article AS AR
INNER JOIN (
    SELECT CAST(REPLACE(REPLACE(GA4_PP.pagePathWoQueryStr, '/article_detail_', ''), '.html', '') AS UNSIGNED) AS articleId,
    hit
    FROM (
        SELECT 
        IF(
            INSTR(GA4_PP.pagePath, '?') = 0,
            GA4_PP.pagePath,
            SUBSTR(GA4_PP.pagePath, 1, INSTR(GA4_PP.pagePath, '?') - 1)
        ) AS pagePathWoQueryStr,
        SUM(GA4_PP.hit) AS hit
        FROM ga4DataPagePath AS GA4_PP
        WHERE GA4_PP.pagePath LIKE '/article_detail_%.html%'
        GROUP BY pagePathWoQueryStr
    ) AS GA4_PP
) AS GA4_PP
ON AR.id = GA4_PP.articleId;

# 2단계, 실제 쿼리
UPDATE article AS AR
INNER JOIN (
    SELECT CAST(REPLACE(REPLACE(GA4_PP.pagePathWoQueryStr, '/article_detail_', ''), '.html', '') AS UNSIGNED) AS articleId,
    hit
    FROM (
        SELECT 
        IF(
            INSTR(GA4_PP.pagePath, '?') = 0,
            GA4_PP.pagePath,
            SUBSTR(GA4_PP.pagePath, 1, INSTR(GA4_PP.pagePath, '?') - 1)
        ) AS pagePathWoQueryStr,
        SUM(GA4_PP.hit) AS hit
        FROM ga4DataPagePath AS GA4_PP
        WHERE GA4_PP.pagePath LIKE '/article_detail_%.html%'
        GROUP BY pagePathWoQueryStr
    ) AS GA4_PP
) AS GA4_PP
ON AR.id = GA4_PP.articleId
SET AR.hit = GA4_PP.hit;


# 태그 테이블 생성
CREATE TABLE tag (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    relTypeCode CHAR(20) NOT NULL,
    relId INT(10) UNSIGNED NOT NULL,
    `body` CHAR(20) NOT NULL 
);

# 아래 쿼리와 관련된 인덱스 걸기
# select * from tag where where relTypeCode = 'article' and `body` = 'SQL';
ALTER TABLE `textBoard`.`tag` ADD INDEX (`relTypeCode` , `body`); 

# 아래 쿼리와 관련된 인덱스 걸기
# 중복된 데이터 생성 금지
# select * from tag where where relTypeCode = 'article';
# select * from tag where where relTypeCode = 'article' and relId = 5;
# select * from tag where where relTypeCode = 'article' and relId = 5 and `body` = 'SQL';
ALTER TABLE `textBoard`.`tag` ADD UNIQUE INDEX (`relTypeCode` , `relId`, `body`); 

# 1번 게시글에 `JAVA`, `SQL`, `HTML` 태그 걸기
INSERT INTO tag
SET regDate = NOW(),
updateDate = NOW(),
relTypeCode = 'article',
relId = 1,
`body` = 'JAVA';

INSERT INTO tag
SET regDate = NOW(),
updateDate = NOW(),
relTypeCode = 'article',
relId = 1,
`body` = 'SQL';

INSERT INTO tag
SET regDate = NOW(),
updateDate = NOW(),
relTypeCode = 'article',
relId = 1,
`body` = 'HTML';

# 게시물 + 태그정보
SELECT A.id,
A.title,
IFNULL(GROUP_CONCAT(T.body), '') AS tags
FROM article AS A
LEFT JOIN tag AS T
ON A.id = T.relId
AND T.relTypeCode = 'article'
GROUP BY A.id;

# JSP 게시판 생성
INSERT INTO `board` (`regDate`, `updateDate`, `name`, `code`) 
VALUES ('2021-01-20 21:47:01', '2021-01-20 21:47:03', 'JSP', 'jsp');

# 부제목 칼럼 생성
ALTER TABLE `article` ADD COLUMN `subtitle` CHAR(200) NOT NULL AFTER `title`