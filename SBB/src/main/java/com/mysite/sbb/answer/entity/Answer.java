// ============================================================================
// [파일 역할] Answer(답변) 엔티티 = DB 의 "answer" 테이블과 매핑되는 클래스
//
//  ※ 게시판의 "댓글" 테이블에 해당. 게시글(Question)에 딸린 댓글 구조를 만들 때 그대로 참고.
//
//  [연결 관계]
//   - DB 테이블 : answer  (question_id 라는 FK 컬럼이 자동으로 생김 → question 테이블의 id 를 가리킴)
//   - 관계 : 답변 N개 : 질문 1개 (N:1)  ↔  Question.java 의 @OneToMany(mappedBy = "question") 와 짝꿍
//   - Repository : answer/repository/AnswerRepository.java
//   - Service    : answer/service/AnswerService.java (여기서 Answer 를 만들어 저장)
//   - 화면       : templates/question/detail.html 의 th:each="answer : ${question.answerList}" 에서 출력
// ============================================================================
package com.mysite.sbb.answer.entity;

import com.mysite.sbb.question.entity.Question;  // 어떤 질문에 달린 답변인지 연결하기 위해 import
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity                 // DB 테이블 answer 로 매핑
@Getter
@Setter
//@Table(name = "ABCD")
@ToString               // ※ question 필드도 출력에 포함됨 (Question 쪽에서 answerList 를 exclude 했기 때문에 무한루프는 안 생김)
@NoArgsConstructor
@AllArgsConstructor
@Builder                // Answer.builder().content(..).question(..).created(..).build()
public class Answer {
    @Id                                                   // 기본키(PK)
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // AUTO_INCREMENT
    private long id;

    @Column(length = 100, nullable = true)                // VARCHAR(100), NULL 허용
    private String content;   // 답변 내용

    private LocalDateTime created;  // 답변 작성일

    // ---------------- 답변(N) : 질문(1) 관계 ----------------
    @ManyToOne(fetch = FetchType.LAZY)                    // 여러 답변이 하나의 질문에 연결됨. LAZY = 필요할 때 질문을 조회
    //@JoinColumn(name = "question_id", nullable = false) // (참고) FK 컬럼명 직접 지정. 안 쓰면 기본값 question_id 로 생성됨
    private Question question;                            // 이 답변이 달린 질문 (FK 를 실제로 가진 쪽 = 연관관계의 주인)
}
