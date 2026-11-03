// ============================================================================
// [파일 역할] AnswerController = 답변(댓글) 등록 요청을 처리하는 컨트롤러
//
//  [URL 정리]
//   POST /answer/create/{id}  → {id}번 질문에 답변 등록 → 성공 시 /question/{id} (상세화면)으로 redirect
//
//  [연결 관계]
//   - 요청 보내는 화면 : templates/question/detail.html 의
//                       <form th:action="@{|/answer/create/${question.id}|}" th:object="${answerDto}" method="post">
//   - 사용 서비스     : question/service/QuestionService.java (질문 조회), answer/service/AnswerService.java (답변 저장)
//   - 검증 DTO        : answer/dto/AnswerDto.java
// ============================================================================
package com.mysite.sbb.answer.controller;

import com.mysite.sbb.answer.dto.AnswerDto;
import com.mysite.sbb.answer.entity.Answer;
import com.mysite.sbb.answer.service.AnswerService;
import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller               // 화면 이동용 컨트롤러
@RequiredArgsConstructor  // final 필드 2개(questionService, answerService) 생성자 주입
@Slf4j
public class AnswerController {      // ※ 클래스에 @RequestMapping 이 없으므로 URL 을 메서드에 전부 적음

    private final QuestionService questionService;  // 답변을 달 "질문"을 조회하기 위해 필요
    private final AnswerService answerService;      // 답변 저장용

    // =====================================================================
    // [답변 등록]  POST /answer/create/{id}
    // =====================================================================
    @PostMapping("/answer/create/{id}")
    public String createAnswer(@PathVariable("id") Long id,       // URL 의 {id} = 질문 번호
                               @Valid AnswerDto answerDto,         // 폼 입력값(content) + 검증 실행
                               BindingResult bindingResult,        // 검증 결과 (반드시 @Valid 바로 뒤!)
                               Model model) {                      // 에러 시 화면에 질문 데이터를 다시 넘기기 위해 필요
        log.info("=========> id : {}, answerDto : {}", id, answerDto);

        Question question = questionService.getQuestion(id);       // 답변을 달 질문 조회

        if (bindingResult.hasErrors()) {                // 답변 내용이 비어 있으면
            model.addAttribute("question", question);  //  detail.html 은 ${question} 이 꼭 필요하므로 다시 담아 줌
            return "question/detail" ;                  //  상세 화면을 그대로 다시 보여줌 (에러 메시지 표시)
        }



        answerService.createAnswer(question,answerDto); // 검증 통과 → 답변 저장

        return "redirect:/question/" + question.getId(); //redicrect는 컨트롤러로 향해가고, 안붙이면 templates로 지정됨
                                                         // → 저장 후 GET /question/{id} 로 이동 (QuestionController.detail 실행)
    }


}
