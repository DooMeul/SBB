// ============================================================================
// [파일 역할] Question(질문) 엔티티 = DB 의 "question" 테이블과 1:1 로 매핑되는 클래스
//
//  ※ 게시판 만들 때 "게시글" 테이블에 해당하는 부분. 복붙 후 필드만 바꿔서 쓰면 됨.
//
//  [연결 관계]
//   - DB 테이블 : question  (application.yml 의 ddl-auto: update 때문에 자동으로 테이블 생성/수정됨)
//   - Repository : question/repository/QuestionRepository.java  (이 엔티티를 저장/조회)
//   - Service    : question/service/QuestionService.java       (엔티티를 만들어서 저장)
//   - 화면       : templates/question/list.html, detail.html     (${question.subject} 처럼 필드를 꺼내 씀)
//   - 관계       : answer/entity/Answer.java  (질문 1개 : 답변 N개  → 1:N 관계)
// ============================================================================
package com.mysite.sbb.question.entity;

import com.mysite.sbb.answer.entity.Answer;  // 답변 엔티티 (answerList 필드에서 사용)
import jakarta.persistence.*;                // JPA 어노테이션들 (@Entity, @Id, @Column, @OneToMany ...)
import lombok.*;                             // Lombok: getter/setter/생성자 등을 자동 생성해 주는 라이브러리

import java.time.LocalDateTime;              // 날짜 + 시간 타입 (작성일 저장용)
import java.util.List;                       // 답변 목록을 담을 리스트

@Entity                              // 이 클래스는 JPA 엔티티 → DB 테이블로 만들어짐 (테이블명 = 클래스명 question)
@Getter                              // 모든 필드의 getXxx() 메서드 자동 생성 (Thymeleaf 에서 ${question.subject} 가 getSubject() 호출)
@Setter                              // 모든 필드의 setXxx() 메서드 자동 생성
//@Table(name = "ABCD")              // (참고) 테이블 이름을 직접 정하고 싶을 때 사용
@ToString(exclude = "answerList")    // toString() 자동 생성. answerList 는 제외!
                                     //  → 제외 안 하면 Question → Answer → Question ... 무한 반복(StackOverflow) 발생
@NoArgsConstructor                   // 기본 생성자 new Question() 자동 생성 (JPA 필수)
@AllArgsConstructor                  // 모든 필드를 받는 생성자 자동 생성 (@Builder 가 내부적으로 사용)
@Builder                             // Question.builder().subject("..").content("..").build() 형태로 객체 생성 가능
public class Question {

    @Id                                                   // 기본키(PK) 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // PK 값을 DB 가 자동 증가(AUTO_INCREMENT)로 생성 → 1, 2, 3 ...
    //@Column(name="question_id")                         // (참고) 컬럼명을 직접 지정하고 싶을 때
    private long id; // 아이디(글 번호)

    @Column(length = 100, nullable = false)               // VARCHAR(100), NOT NULL 컬럼
    private String subject; // 질문 제목

    @Column(columnDefinition = "TEXT")                    // 긴 글을 저장할 수 있도록 TEXT 타입 컬럼
    private String content; // 질문 내용

    private LocalDateTime created; // 생성일 (DB 컬럼명은 created, 타입은 DATETIME)

    // ---------------- 질문(1) : 답변(N) 관계 설정 ----------------
    //fetch = FetchType.LAZY는 디폴트임                     // LAZY = 실제로 answerList 를 사용할 때 DB 에서 가져옴(지연 로딩)
    @OneToMany(fetch = FetchType.LAZY,                    // 질문 1개에 답변 여러 개 (1:N)
               mappedBy = "question",                     // Answer 엔티티의 "question" 필드가 관계의 주인(FK 를 가짐)
               cascade = CascadeType.ALL)                 // 질문을 삭제하면 딸린 답변들도 같이 삭제됨
    private List<Answer> answerList;                      // 이 질문에 달린 답변 목록 (detail.html 에서 th:each 로 출력)

}
