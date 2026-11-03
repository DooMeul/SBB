// ============================================================================
// [파일 역할] AnswerRepository = answer 테이블 DB 접근 담당
//  - JpaRepository<Answer, Long> 상속만으로 save(), findById(), findAll(), deleteById() 등 자동 제공
//
//  [연결 관계]
//   - 엔티티 : answer/entity/Answer.java
//   - 사용처 : answer/service/AnswerService.java
//   - 테스트 : src/test/.../answer/repository/AnswerRepositoryTest.java
// ============================================================================
package com.mysite.sbb.answer.repository;

import com.mysite.sbb.answer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> { // <엔티티, PK 타입>
    // 추가 조회 메서드가 필요하면 여기에 작성 (예: List<Answer> findByQuestion(Question question);)
}
