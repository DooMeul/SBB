// ============================================================================
// [파일 역할] QuestionRepository = Question 테이블에 접근(저장/조회/수정/삭제)하는 DB 담당 인터페이스
//
//  ※ 인터페이스만 만들면 Spring Data JPA 가 구현 클래스를 "자동으로" 만들어 줌 (SQL 직접 작성 X)
//  ※ JpaRepository<엔티티 클래스, PK 타입> 을 상속하면 아래 메서드가 기본 제공됨
//      save(엔티티)      : INSERT (id 가 이미 있으면 UPDATE)
//      findById(id)      : PK 로 1건 조회 → Optional 로 반환
//      findAll()         : 전체 조회
//      count()           : 전체 개수
//      deleteById(id)    : 삭제
//
//  [연결 관계]
//   - 엔티티 : question/entity/Question.java
//   - 사용처 : question/service/QuestionService.java (final 필드로 주입받아 사용)
//   - 테스트 : src/test/.../question/repository/QuestionRepositoryTest.java
// ============================================================================
package com.mysite.sbb.question.repository;

import com.mysite.sbb.question.entity.Question;
import org.springframework.data.domain.Page;        // 페이징 결과(현재 페이지 데이터 + 전체 페이지 수 등)를 담는 객체
import org.springframework.data.domain.Pageable;    // "몇 페이지, 한 페이지에 몇 개, 정렬 기준" 정보를 담는 객체
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> { // <엔티티 타입, PK(id) 타입>

    // ---------- 쿼리 메서드 : 메서드 "이름 규칙"만 지키면 SQL 이 자동 생성됨 ----------
    Question findBySubject(String subject);                                  // SELECT * FROM question WHERE subject = ?
                                                                             // (아래처럼 Optional 로 받으면 "있거나 없거나"를 안전하게 처리 가능)
    Optional<Question> findBySubjectAndContent(String subject, String content); // WHERE subject = ? AND content = ?

    List<Question> findBySubjectLike(String subject);                        // WHERE subject LIKE ?  (호출 시 "%SBB%" 처럼 % 직접 붙임)
    Page<Question> findAll(Pageable pageable);                               // 페이징 조회 (LIMIT/OFFSET 자동) → QuestionService.getList() 에서 사용
}
