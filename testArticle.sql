INSERT INTO article (`id`, `regDate`, `updateDate`, `title`, `body`, `hit`, `memberId`, `boardId`) 
VALUES ('1', NOW(), NOW(), '(공지사항) 최형석 블로그에 오신걸 환영합니다.', '# 최형석 IT BLOG\r\n\r\n## IT관련 강의 및 소식을 다룰 예정\r\n\r\n - 주된 개발언어\r\n  - JAVA\r\n  - SQL\r\n  - HTML\r\n  - CSS\r\n  - JS\r\n  - 그외 다수', '0', '1', '1'); 

INSERT INTO article (`id`, `regDate`, `updateDate`, `title`, `body`, `hit`, `memberId`, `boardId`) 
VALUES ('2', NOW(), NOW(), 'JAVA / 1강 - HELLO WORLD 출력', '# JAVA 입문\r\n- 기본 출력\r\n\r\n## \"HELLO WORLD\" 라는 단어를 출력해 보자.\r\n - 작성 예시\r\n\r\n```java\r\npublic class Main {\r\n\r\n public static void main(String[] args) {\r\n System.out.println(\"HELLO WORLD\");\r\n }\r\n}\r\n```\r\n\r\n\r\n - 출력 예시\r\n\r\n```java\r\nHELLO WORLD\r\n```\r\n\r\n---\r\n\r\n# 설명\r\n\r\nSystem.out.println(\"출력하고자 하는 문자\");\r\n\"\" 사이에 원하는 문자를 입력하여 출력 가능', '0', '1', '3');


INSERT INTO article (`id`, `regDate`, `updateDate`, `title`, `body`, `hit`, `memberId`, `boardId`) 
VALUES ('3', NOW(), NOW(), 'JAVA / 2강 - 입력 받기', '# JAVA 입문 2강\r\n- 입력 받기\r\n\r\n## Scanner 를 이용하여 값을 입력받기.\r\n - 작성 예시\r\n\r\n```java\r\nimport java.util.Scanner;\r\n\r\npublic class Main {\r\n\r\n public static void main(String[] args) {\r\n Scanner scanner = new Scanner(System.in);\r\n \r\n String name = scanner.nextLine();\r\n System.out.println(name);\r\n }\r\n}\r\n```\r\n\r\n\"최형석\" 을 입력\r\n\r\n\r\n - 출력 예시\r\n\r\n```java\r\n최형석\r\n\r\n```\r\n\r\n---\r\n\r\n# 설명\r\nScanner 를 import 하여 사용할수 있도록 한 후\r\n\r\nnew Scanner(System.in); 으로 객체 생성\r\n\r\n최형석 이라는 입력을 받은후 그것을 String name 에 저장\r\n\r\nname을 출력', '0', '1', '3');

INSERT INTO article (`id`, `regDate`, `updateDate`, `title`, `body`, `hit`, `memberId`, `boardId`) 
VALUES ('4', NOW(), NOW(), '(자유게시판) 이곳은 자유게시판입니다.', '# 자유게시판\r\n\r\n\r\n## 이곳은 자유게시판이므로 \r\n - IT 관련 혹은 자유롭게 글을 작성하는 곳입니다.', '0', '1', '2'); 
