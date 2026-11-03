// ============================================================================
// [파일 역할] QuestionController = 질문(게시글) 관련 "URL 요청"을 받아서 처리하고, 어떤 화면을 보여줄지 결정
//
//  [요청 흐름]  브라우저 → ★QuestionController★ → QuestionService → QuestionRepository → DB
//                                ↓ (model 에 데이터 담아서)
//                         templates/question/*.html 화면 출력
//
//  [URL 정리]  (클래스 위 @RequestMapping("/question") 때문에 모든 주소 앞에 /question 이 붙음)
//   GET  /question/list          → 질문 목록(+페이징)   → templates/question/list.html
//   GET  /question/{id}          → 질문 상세 + 답변 목록 → templates/question/detail.html
//   GET  /question/create        → 질문 등록 폼 보여주기 → templates/question/inputForm.html
//   POST /question/create        → 질문 등록 처리(저장)  → 성공 시 /question/list 로 redirect
//
//  ※ 답변 등록(POST /answer/create/{id})은 answer/controller/AnswerController.java 에 있음
// ============================================================================
package com.mysite.sbb.question.controller;

import com.mysite.sbb.answer.dto.AnswerDto;             // 상세 화면의 "답변 작성 폼"에서 사용할 DTO
import com.mysite.sbb.question.dto.QuestionDto;         // 질문 등록 폼 DTO
import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.question.repository.QuestionRepository;
import com.mysite.sbb.question.service.QuestionService;
import jakarta.validation.Valid;                        // DTO 검증을 실행시키는 어노테이션
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;                       // log.info(...) 를 쓸 수 있게 해 주는 Lombok 어노테이션
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;                    // 화면(html)으로 데이터를 넘길 때 쓰는 상자
import org.springframework.validation.BindingResult;    // @Valid 검증 결과(에러 여부)를 담는 객체
import org.springframework.web.bind.annotation.*;       // @GetMapping, @PostMapping, @PathVariable, @RequestParam ...

import java.util.List;

@Controller                 // 화면(html 템플릿 이름)을 리턴하는 컨트롤러 (데이터만 리턴하려면 @RestController)
@Slf4j                      // log 변수 자동 생성 → log.info("...{}", 값) 으로 콘솔 출력
@RequiredArgsConstructor // 자동으로 생성자를 만들어줌(final 꼭 써줘야함)
@RequestMapping("/question") // 이 클래스 안의 모든 URL 앞에 "/question" 이 공통으로 붙음
public class QuestionController {

    //    @Autowired //자동 객체 생성
    private final QuestionService questionService;  // 서비스 주입 (생성자 주입)

    // =====================================================================
    // [1] 질문 목록 화면   GET /question/list?page=0
    //   - list.html 의 페이지 번호 버튼을 누르면 page 값이 바뀌어서 다시 이 메서드로 들어옴
    //   - 연결 화면 : templates/question/list.html  (${paging} 사용)
    //   - 연결 메뉴 : templates/fragments/header.html 의 "질문목록" 링크
    // =====================================================================
    @GetMapping("/list")
    //@ResponseBody //첫번 째 데이터 값 보내기             // (참고) 붙이면 html 이 아니라 리턴값 자체(문자열/JSON)를 응답
    public String list(Model model,                                                   // 화면으로 데이터 전달용
                       @RequestParam(value = "page" ,defaultValue="0") int page){     // ?page=숫자 값을 받음, 없으면 0
         Page<Question> paging = questionService.getList(page);                       // 해당 페이지의 질문 10개 조회
        log.info("================================> paging : {}", paging);           // 콘솔 로그 출력 (디버깅용)
        model.addAttribute("paging", paging);                                         // 화면에서 ${paging} 으로 사용 가능
        return "question/list";                                                       // templates/question/list.html 을 보여줌
    }

    // =====================================================================
    // [2] 질문 상세 화면   GET /question/1, /question/2 ...
    //   - list.html 에서 제목 링크(th:href="@{|/question/${question.id}|}")를 클릭하면 여기로 옴
    //   - AnswerDto answerDto : 상세 화면 아래 "답변 작성 폼"(th:object="${answerDto}")에 빈 객체를 넘겨주기 위해 필요
    //                           (매개변수에 쓰기만 하면 스프링이 자동으로 model 에 "answerDto" 로 담아 줌)
    //   - 연결 화면 : templates/question/detail.html  (${question} 사용)
    // =====================================================================
    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long id,       // URL 의 {id} 자리 값을 꺼내서 id 변수에 넣음
                         AnswerDto answerDto,                // 답변 폼용 빈 DTO (없으면 detail.html 에서 에러 발생!)
                         Model model){ // @PathVariable는 getmapping으로 들어온 id를 변수처리(자동으로 값을 세팅)
        log.info("========================> id : {}",id);
        Question question = questionService.getQuestion(id); // id 로 질문 1건 조회 (답변 목록 answerList 도 함께 사용 가능)
        model.addAttribute("question", question);            // 화면에서 ${question.subject}, ${question.answerList} 등으로 사용
        return "question/detail";                            // templates/question/detail.html
    }

    // =====================================================================
    // [3] 질문 등록 "폼 화면" 보여주기   GET /question/create
    //   - list.html 의 "질문 등록" 버튼(th:href="@{/question/create}")을 누르면 여기로 옴
    //   - QuestionDto questionDto : 폼(th:object="${questionDto}")에서 쓸 빈 객체를 model 에 자동으로 담아 줌
    //   - 연결 화면 : templates/question/inputForm.html
    // =====================================================================
    @GetMapping("/create")
    public String createInputForm(QuestionDto questionDto){
        return "question/inputForm";
    }

    // ---------------------------------------------------------------------
    // (예전 버전) @RequestParam 으로 폼 값을 하나씩 받던 방식 - 검증(Validation) 없음
    //   → 지금은 아래의 DTO + @Valid 방식으로 대체됨
    // ---------------------------------------------------------------------
//    @PostMapping("/create")
//    public String createQuestion(@RequestParam("subject") String subject,
//                                 @RequestParam("content") String content){
//        log.info("=========> subject : {} content : {}", subject, content);
//        questionService.createQuestion(subject, content);
//        return "redirect:/question/list";
//    }

    // =====================================================================
    // [4] 질문 등록 "처리(저장)"   POST /question/create
    //   - inputForm.html 의 <form th:action="@{/question/create}" method="post"> 에서 "저장하기" 누르면 여기로 옴
    //   - @Valid        : QuestionDto 에 적어둔 @NotEmpty, @Size 규칙 검사
    //   - BindingResult : 검사 결과. 반드시 @Valid 매개변수 "바로 다음"에 와야 함!
    // =====================================================================
    @PostMapping("/create")
    public String createQuestion(@Valid QuestionDto questionDto,
                                 BindingResult bindingResult){
        log.info("=========> questionDto : {}", questionDto);
        if(bindingResult.hasErrors()){          // 검증 실패 (제목/내용 비었거나 너무 김)
            return "question/inputForm";        //  → 등록 폼을 다시 보여줌 (입력값 + 에러메시지가 함께 표시됨)
        }

        questionService.createQuestion(questionDto);  // 검증 통과 → DB 에 저장
        return "redirect:/question/list";             // 저장 후 목록 URL 로 이동(redirect = 브라우저가 해당 URL 로 새로 요청)
    }
}
