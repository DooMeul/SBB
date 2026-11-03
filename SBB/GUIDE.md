# SBB 프로젝트 한눈에 보기 (시험 대비 정리)

> 각 파일 맨 위에 **[파일 역할]** 과 **[연결 관계]** 주석이 있습니다. 이 문서는 그 내용을 한 장으로 요약한 지도입니다.

---

## 1. 폴더 구조와 역할

```
SBB/
├─ build.gradle                         라이브러리 목록 (Thymeleaf, JPA, MySQL, Lombok, Validation ...)
├─ src/main/java/com/mysite/sbb/
│  ├─ SbbApplication.java               ▶ 실행 시작점 (이 파일 Run)
│  │
│  ├─ question/   ★ 게시글(질문) 기능
│  │  ├─ entity/Question.java           DB 테이블 question
│  │  ├─ dto/QuestionDto.java           글쓰기 폼 입력값 + 검증(@NotEmpty, @Size)
│  │  ├─ repository/QuestionRepository  DB 접근 (save, findById, findAll(pageable) ...)
│  │  ├─ service/QuestionService        목록(페이징) / 상세 / 저장 로직
│  │  └─ controller/QuestionController  URL 처리: /question/list, /question/{id}, /question/create
│  │
│  ├─ answer/     ★ 댓글(답변) 기능
│  │  ├─ entity/Answer.java             DB 테이블 answer (question_id FK)
│  │  ├─ dto/AnswerDto.java             답변 폼 입력값 + 검증
│  │  ├─ repository/AnswerRepository
│  │  ├─ service/AnswerService          답변 저장
│  │  └─ controller/AnswerController    URL 처리: POST /answer/create/{id}
│  │
│  └─ common/
│     ├─ controller/IndexController     "/" → index.html,  "/temp" → temp.html
│     ├─ controller/DataController      "/data" → JSON 응답 연습 (@RestController)
│     └─ basic / interfaces / annotation  DI(의존성 주입) 개념 연습용 (게시판과 무관, 각 main 으로 단독 실행)
│
└─ src/main/resources/
   ├─ application.yml                   포트, MySQL 접속 정보(DB명 sbb_a / root / 0000), JPA 설정
   ├─ logback-spring.xml                콘솔 로그 형식
   ├─ car-config.xml                    DI 연습 3단계용 XML 설정
   ├─ static/                           css, js (부트스트랩 로컬 파일 → 인터넷 없어도 동작)
   └─ templates/                        화면(Thymeleaf)
      ├─ layouts/layout.html            공통 뼈대 (header + 본문 + footer)
      ├─ fragments/header.html          상단 메뉴바
      ├─ fragments/footer.html          하단 푸터
      ├─ index.html                     메인 페이지
      ├─ temp.html                      새 페이지용 빈 틀 (복사해서 사용)
      └─ question/
         ├─ list.html                   목록 + 페이징 + 글쓰기 버튼
         ├─ detail.html                 상세 + 답변목록 + 답변작성 폼
         └─ inputForm.html              글쓰기 폼
```

---

## 2. 요청이 처리되는 흐름

```
브라우저 ──요청──▶ Controller ──▶ Service ──▶ Repository ──▶ DB(MySQL)
                     │
                     └─ model.addAttribute("이름", 데이터)
                        return "폴더/파일명"  ──▶ templates/폴더/파일명.html 에서 ${이름} 으로 출력
```

| URL | 메서드 | 컨트롤러 메서드 | 화면 / 결과 |
|---|---|---|---|
| `/` | GET | `IndexController.index2()` | `index.html` |
| `/question/list?page=0` | GET | `QuestionController.list()` | `question/list.html` |
| `/question/{id}` | GET | `QuestionController.detail()` | `question/detail.html` |
| `/question/create` | GET | `QuestionController.createInputForm()` | `question/inputForm.html` |
| `/question/create` | POST | `QuestionController.createQuestion()` | 성공 → `redirect:/question/list` |
| `/answer/create/{id}` | POST | `AnswerController.createAnswer()` | 성공 → `redirect:/question/{id}` |

---

## 3. 기능별 "복붙 세트"

| 만들 기능 | 같이 가져가야 하는 파일 |
|---|---|
| **공통 레이아웃 (메뉴/푸터)** | `layouts/layout.html`, `fragments/header.html`, `fragments/footer.html`, `static/css/bootstrap.min.css`, `static/js/bootstrap.bundle.min.js` |
| **게시글 목록 + 페이징** | `Question.java`, `QuestionRepository.java` (`findAll(Pageable)`), `QuestionService.getList()`, `QuestionController.list()`, `question/list.html` (테이블 + 페이징 블록 + script) |
| **게시글 상세** | `QuestionService.getQuestion()`, `QuestionController.detail()`, `question/detail.html` ①② 부분 |
| **글쓰기 + 입력값 검증** | `QuestionDto.java`, `QuestionService.createQuestion(QuestionDto)`, `QuestionController` 의 GET/POST `/create`, `question/inputForm.html` |
| **댓글(답변)** | `answer/` 폴더 전체, `Question.java` 의 `answerList` 필드, `QuestionController.detail()` 의 `AnswerDto` 매개변수, `detail.html` ③④ 부분 |
| **새 빈 페이지** | `temp.html` 복사 + 컨트롤러에 `@GetMapping` 추가 |
| **페이징 테스트 데이터 300개** | `QuestionRepositoryTest.testLargeData()` 실행 |

---

## 4. 새 게시판(예: Board)을 처음부터 만드는 순서

1. **Entity** `board/entity/Board.java` ← `Question.java` 복사 후 이름 변경 (`@Entity`, `@Id`, `@GeneratedValue`)
2. **Repository** `BoardRepository extends JpaRepository<Board, Long>` ← `QuestionRepository` 복사
3. **DTO** `BoardDto` ← `QuestionDto` 복사 (검증 어노테이션 포함)
4. **Service** `BoardService` ← `QuestionService` 복사 (`@Service`, `@RequiredArgsConstructor`, `private final BoardRepository`)
5. **Controller** `BoardController` ← `QuestionController` 복사 (`@RequestMapping("/board")`, return 경로를 `board/...` 로)
6. **화면** `templates/board/list.html, detail.html, inputForm.html` ← question 폴더 복사 후 `${question...}` → `${board...}`, URL `/question` → `/board` 로 변경
7. **메뉴** `fragments/header.html` 에 `<li class="nav-item"><a class="nav-link text-white" href="/board/list">게시판</a></li>` 추가
8. MySQL 에 DB(`sbb_a`)가 있는지 확인 → `SbbApplication` 실행 → `ddl-auto: update` 덕분에 테이블 자동 생성

---

## 5. 자주 헷갈리는 포인트

- **새 클래스는 `com.mysite.sbb` 아래 패키지**에 만들어야 스프링이 찾음.
- `@RequiredArgsConstructor` 는 **`final` 필드만** 생성자로 주입함. `final` 빠뜨리면 `NullPointerException`.
- `return "question/list"` = **화면 파일**, `return "redirect:/question/list"` = **URL 로 다시 요청**.
- `@Valid` 바로 **다음 매개변수**가 `BindingResult` 여야 함.
- 폼 화면을 여는 GET 메서드에도 **빈 DTO 매개변수**(`QuestionDto questionDto`)가 있어야 `th:object="${questionDto}"` 가 에러 안 남.
- 엔티티 1:N 관계에서 `@ToString(exclude = "answerList")` 안 하면 무한루프.
- `|...|` 는 Thymeleaf 문자열 합치기: `th:href="@{|/question/${question.id}|}"`.
- `th:each="item, loop : ${list}"` → `loop.index`(0부터), `loop.count`(1부터).
- 날짜 출력: `${#temporals.format(question.created, 'yyyy-MM-dd HH:mm')}`.
- 리스트 크기/비었는지: `${#lists.size(list)}`, `${#lists.isEmpty(list)}`.
