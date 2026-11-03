// ============================================================================
// [파일 역할] AnswerService = 답변 저장 비즈니스 로직
//
//  [요청 흐름]  detail.html 답변폼 → AnswerController → ★AnswerService★ → AnswerRepository → DB(answer 테이블)
//
//  [연결 관계]
//   - 호출하는 곳 : answer/controller/AnswerController.java
//   - 사용하는 것 : answer/repository/AnswerRepository.java
// ============================================================================
package com.mysite.sbb.answer.service;

import com.mysite.sbb.answer.dto.AnswerDto;
import com.mysite.sbb.answer.entity.Answer;
import com.mysite.sbb.answer.repository.AnswerRepository;
import com.mysite.sbb.question.entity.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service                  // 서비스 Bean 등록
@RequiredArgsConstructor  // final 필드 생성자 주입
@Slf4j                    // log 사용
public class AnswerService {

    private final AnswerRepository answerRepository;  // 답변 DB 접근 객체 (자동 주입)


    // =====================================================================
    // [답변 저장]  ← AnswerController.createAnswer() 에서 호출
    //   question  : 어느 질문에 달 답변인지 (QuestionService.getQuestion(id) 로 조회해 온 객체)
    //   answerDto : 폼에서 입력한 답변 내용
    // =====================================================================
    public void createAnswer(Question question, AnswerDto answerDto) {
        Answer answer = Answer.builder()
                .content(answerDto.getContent())   // 입력한 답변 내용
                .question(question)                // ★ 질문과 연결 (DB 에는 question_id FK 로 저장됨)
                .created(LocalDateTime.now())      // 작성일 = 현재 시간
                .build();
        Answer saved = answerRepository.save(answer);         // DB 에 INSERT 후, id 가 채워진 객체를 돌려받음
        log.info("===========> saved answer : {}", saved);    // 저장 결과 로그 출력
    }
}
