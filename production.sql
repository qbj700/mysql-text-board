#운영시작


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


SELECT * FROM article; 