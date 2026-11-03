// ============================================================================
// [파일 역할] AnswerRepository 테스트 - 1번 질문에 답변을 하나 저장해 보는 테스트
//  - 테스트 대상 : main/.../answer/repository/AnswerRepository.java
//  - 주의 : 실제 MySQL(sbb_a)에 데이터가 저장됨. 1번 질문이 DB 에 있어야 함
// ============================================================================
package com.mysite.sbb.answer.repository;

import com.mysite.sbb.answer.entity.Answer;
import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.question.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;   // assertEquals 등 검증 메서드

@SpringBootTest
class AnswerRepositoryTest {

    @Autowired      // 테스트 클래스에서는 생성자 주입 대신 필드 @Autowired 사용
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void testSave() {
        // 1) 1번 질문 조회 (없으면 예외)
        Question question = questionRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("해당 질문이 없습니다."));
        // 2) 답변 객체 생성 + 1번 질문과 연결
        Answer answer = Answer.builder()
                .content("저도 아직 잘 몰라요")
                .question(question)
                .created(LocalDateTime.now())
                .build();
        // 3) 저장
        Answer save = answerRepository.save(answer);
        System.out.println("save : " + save);
        assertEquals(1,save.getId());   // 저장된 답변 id 가 1인지 확인 (처음 저장할 때만 통과)
    }


}
