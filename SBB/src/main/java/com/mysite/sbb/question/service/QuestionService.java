// ============================================================================
// [파일 역할] QuestionService = 질문 관련 "비즈니스 로직" 담당 (Controller 와 Repository 사이의 중간 계층)
//
//  [요청 흐름]  브라우저 → QuestionController → ★QuestionService★ → QuestionRepository → DB
//
//  [연결 관계]
//   - 호출하는 곳 : question/controller/QuestionController.java, answer/controller/AnswerController.java
//   - 사용하는 것 : question/repository/QuestionRepository.java
//   - 다루는 데이터 : question/entity/Question.java, question/dto/QuestionDto.java
//
//  [메서드별 용도]
//   getList(page)          → 질문 목록 + 페이징      (목록 화면 list.html 용)
//   getQuestion(id)        → 질문 1건 상세 조회      (상세 화면 detail.html 용, 답변 등록 시에도 사용)
//   createQuestion(...)    → 질문 저장              (등록 폼 inputForm.html 에서 저장 버튼 눌렀을 때)
// ============================================================================
package com.mysite.sbb.question.service;

import com.mysite.sbb.question.dto.QuestionDto;
import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.question.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;   // 조회 결과가 없을 때 던지는 예외
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;   // Pageable 을 만드는 클래스 (PageRequest.of(페이지, 개수, 정렬))
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;          // 정렬 조건
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service                   // 서비스 계층 Bean 으로 등록 → Controller 에서 주입받아 사용 가능
@RequiredArgsConstructor// 자동으로 생성자를 만들어줌(final 꼭 써줘야함)
                           //  → final 필드(questionRepository)를 매개변수로 받는 생성자를 만들어 스프링이 자동 주입(DI)
public class QuestionService {
    //    @Autowired //자동 객체 생성                           // (예전 방식) 필드에 @Autowired 로 주입. 지금은 생성자 주입 사용
    private final QuestionRepository questionRepository; //final 안쓰면 생성자 안생김

    // =====================================================================
    // [질문 목록 + 페이징]  ← QuestionController.list() 에서 호출
    //  page : 0부터 시작하는 페이지 번호 (0 = 1페이지)
    // =====================================================================
    public Page<Question> getList(int page){
//        Pageable pageable = PageRequest.of(page, 10,  Sort.Direction.DESC, "created"); // (같은 의미의 다른 작성법)
        Pageable pageable = PageRequest.of(page, 10,  Sort.by(Sort.Order.desc("created"))); // 소팅
                                                   //  → page 번째 페이지, 한 페이지에 10개, created(작성일) 내림차순(최신글 먼저)
        Page<Question> paging = questionRepository.findAll(pageable); // DB 에서 해당 페이지의 10개만 조회
        return paging;                                                // 컨트롤러로 돌려줌 → model 에 "paging" 이름으로 담김
    }

    // =====================================================================
    // [질문 1건 조회]  ← QuestionController.detail(), AnswerController.createAnswer() 에서 호출
    // =====================================================================
    public Question getQuestion(Long id) {
        Question question = questionRepository.findById(id)                                  // PK 로 조회 (Optional 반환)
                .orElseThrow(() -> new EntityNotFoundException("해당 엔티티가 없습니다."));  // 없으면 예외 발생, 있으면 꺼내서 반환
        return question;
    }

    // =====================================================================
    // [질문 저장 - 버전1] 제목/내용을 문자열로 따로 받는 방식
    //  (QuestionController 에서 주석 처리된 @RequestParam 방식 createQuestion 이 사용하던 메서드)
    // =====================================================================
    public void createQuestion(String subject, String content) {
        Question question = Question.builder()   // 빌더 패턴으로 Question 객체 생성 시작
                .subject(subject)                // 제목 세팅
                .content(content)                // 내용 세팅
                .created(LocalDateTime.now())    // 작성일 = 현재 시간
                .build();                        // 객체 완성
        questionRepository.save(question);       // DB 에 INSERT
    }

    // =====================================================================
    // [질문 저장 - 버전2] DTO 로 받는 방식 (★현재 사용 중)  ← QuestionController.createQuestion() 에서 호출
    //  DTO(화면 입력값) → Entity(DB 저장용) 로 변환해서 저장
    // =====================================================================
    public void createQuestion(QuestionDto questionDto) {
        Question question = Question.builder()
                .subject(questionDto.getSubject())   // 폼에서 입력한 제목
                .content(questionDto.getContent())   // 폼에서 입력한 내용
                .created(LocalDateTime.now())        // 작성일 = 현재 시간
                .build();
        questionRepository.save(question);           // DB 에 INSERT
    }
}
