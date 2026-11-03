// ============================================================================
// [파일 역할] QuestionRepository / QuestionService 테스트 모음 (JPA 기본 CRUD 연습)
//  - 테스트 대상 : main/.../question/repository/QuestionRepository.java, question/service/QuestionService.java
//  - 주의 : 실제 MySQL(sbb_a) DB 를 사용 → 테스트 실행하면 진짜로 데이터가 저장/수정/삭제됨
//           assertEquals 의 숫자(id, 개수)는 "그때 DB 상태" 기준이라 다시 돌리면 실패할 수 있음
//
//  [테스트 목록]
//   testSave               : INSERT (setter 방식 / builder 방식)
//   testFindAl             : 전체 조회 findAll()
//   testFindById           : PK 로 조회 findById()
//   testFindBySubject      : 제목으로 조회 (쿼리 메서드)
//   testFindBySubjectAndContent : 제목 + 내용으로 조회
//   testFindBySubjectLike  : LIKE 검색
//   testSubjectUpdate      : 수정 (조회 → set → save)
//   testDelete             : 삭제 deleteById()
//   testLargeData          : 페이징 테스트용 더미 데이터 300개 생성 ★
// ============================================================================
package com.mysite.sbb.question.repository;

import com.mysite.sbb.question.dto.QuestionDto;
import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.question.service.QuestionService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository ;

    @Autowired
    private QuestionService questionService;

    // 테스트 동작 확인용
    @Test
    void test() {
        System.out.println("테스트 확인중...");
    }

    // ---------------- [저장] INSERT ----------------
    @Test
    //@Transactional          // (참고) 붙이면 테스트 끝난 뒤 자동으로 롤백(저장 취소)됨
    void testSave(){
        // 방법1) 기본 생성자 + setter 로 객체 만들기
        Question q1 = new Question();
        q1.setSubject("SBB가 뭔가요?");
        q1.setContent("sbb에 대해서 알고 싶어요.");
        q1.setCreated(LocalDateTime.now());
        Question saved = questionRepository.save(q1);   // INSERT
        assertEquals(1,saved.getId());                   // 첫 번째 글이면 id = 1

        // 방법2) builder 로 객체 만들기 (순서 상관없이 원하는 필드만 세팅 가능)
        Question q2 = Question.builder()
                .content("스프링은 왜 어렵다고 하는거죠?")
                .subject("스프링 부트도 어려운가요?")
                .created(LocalDateTime.now())
                .build();
        Question saved2 = questionRepository.save(q2);
        assertEquals(2,saved2.getId());

    }

    // ---------------- [전체 조회] ----------------
    @Test
    void testFindAl(){
        List<Question> questionList = questionRepository.findAll();   // SELECT * FROM question
        assertEquals(3,questionList.size());                           // 전체 개수 확인
        Question question = questionList.get(0);                       // 첫 번째 데이터
        assertEquals("SBB가 뭔가요?",question.getSubject());
    }

    // ---------------- [PK 로 1건 조회] ----------------
    @Test
    void testFindById(){
        // 방법1) Optional 로 받아서 isPresent() 로 확인 후 get()
        Optional<Question> byId = questionRepository.findById(1L);
        if(byId.isPresent()){
            Question question = byId.get();
            assertEquals(1,question.getId());
        }

        // 방법2) orElseThrow : 있으면 꺼내고 없으면 예외 (★ QuestionService.getQuestion 에서 쓰는 방식)
        Question question = questionRepository.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException("해당 객체 없음"));
        assertEquals(1,question.getId());
    }

    // ---------------- [제목으로 조회] ----------------
    @Test
    void testFindBySubject(){
        Question question = questionRepository.findBySubject("SBB가 뭔가요?");
        assertNotNull(question);              // null 이 아니면 성공
        assertEquals(1,question.getId());
    }

    // ---------------- [제목 + 내용으로 조회] ----------------
    @Test
    void testFindBySubjectAndContent(){
        Question question = questionRepository.findBySubjectAndContent("SBB가 뭔가요?", "sbb에 대해서 알고 싶어요.")
                .orElseThrow(() -> new EntityNotFoundException("해당 자료가 없습니다."));
        assertEquals(1,question.getId());
    }

    // ---------------- [LIKE 검색] ----------------
    @Test
    void testFindBySubjectLike(){
        List<Question> list = questionRepository.findBySubjectLike("%SBB%");   // 제목에 "SBB" 가 포함된 글
        assertEquals(1,list.size());
    }

    // ---------------- [수정] UPDATE : 조회 → 값 변경 → save ----------------
    @Test
    void testSubjectUpdate(){
        Question question = questionRepository.findById(2L).orElseThrow(() -> new EntityNotFoundException("해당 정보가 없습니다."));
        System.out.println("변경 전 : " + question);
        question.setSubject("새로운 질문");                  // 제목 변경
        Question saved = questionRepository.save(question); // id 가 이미 있으므로 INSERT 가 아니라 UPDATE 실행
        System.out.println("변경 후 : " + saved);
    }

    // ---------------- [삭제] DELETE ----------------
    @Test
    void testDelete(){
        assertEquals(2,questionRepository.count());   // 삭제 전 개수
        questionRepository.deleteById(2L);           // 2번 글 삭제 (cascade 설정 때문에 딸린 답변도 같이 삭제)
        assertEquals(1,questionRepository.count());   // 삭제 후 개수
    }

    // ---------------- [대량 데이터 생성] 페이징 확인용 300개 ----------------
    //  ★ 목록 페이징 화면을 확인하려면 이 테스트 하나만 실행해서 데이터를 채우면 됨
    @Test
    void testLargeData(){
        for(int i=1; i<=300; i++){
            QuestionDto questionDto = QuestionDto.builder()
                    .subject("테스트 데이터 입니다. ["+ i + "]")
                    .content("내용 입니다. [" + i + "]")
                    .build();
            questionService.createQuestion(questionDto);   // 서비스의 저장 메서드 사용
        }
    }
}
