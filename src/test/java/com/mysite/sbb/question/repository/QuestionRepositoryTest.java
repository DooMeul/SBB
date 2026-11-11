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

    @Test
    void test() {
        System.out.println("테스트 확인중...");
    }

    @Test
    //@Transactional
    void testSave(){
        Question q1 = new Question();
        q1.setSubject("SBB가 뭔가요?");
        q1.setContent("sbb에 대해서 알고 싶어요.");
        q1.setCreated(LocalDateTime.now());
        Question saved = questionRepository.save(q1);
        assertEquals(1,saved.getId());

        Question q2 = Question.builder()
                .content("스프링은 왜 어렵다고 하는거죠?")
                .subject("스프링 부트도 어려운가요?")
                .created(LocalDateTime.now())
                .build();
        Question saved2 = questionRepository.save(q2);
        assertEquals(2,saved2.getId());

    }

    @Test
    void testFindAl(){
        List<Question> questionList = questionRepository.findAll();
        assertEquals(3,questionList.size());
        Question question = questionList.get(0);
        assertEquals("SBB가 뭔가요?",question.getSubject());
    }

    @Test
    void testFindById(){
        Optional<Question> byId = questionRepository.findById(1L);
        if(byId.isPresent()){
            Question question = byId.get();
            assertEquals(1,question.getId());
        }

        Question question = questionRepository.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException("해당 객체 없음"));
        assertEquals(1,question.getId());
    }

    @Test
    void testFindBySubject(){
        Question question = questionRepository.findBySubject("SBB가 뭔가요?");
        assertNotNull(question);
        assertEquals(1,question.getId());
    }

    @Test
    void testFindBySubjectAndContent(){
        Question question = questionRepository.findBySubjectAndContent("SBB가 뭔가요?", "sbb에 대해서 알고 싶어요.")
                .orElseThrow(() -> new EntityNotFoundException("해당 자료가 없습니다."));
        assertEquals(1,question.getId());
    }

    @Test
    void testFindBySubjectLike(){
        List<Question> list = questionRepository.findBySubjectLike("%SBB%");
        assertEquals(1,list.size());
    }

    @Test
    void testSubjectUpdate(){
        Question question = questionRepository.findById(2L).orElseThrow(() -> new EntityNotFoundException("해당 정보가 없습니다."));
        System.out.println("변경 전 : " + question);
        question.setSubject("새로운 질문");
        Question saved = questionRepository.save(question);
        System.out.println("변경 후 : " + saved);
    }

    @Test
    void testDelete(){
        assertEquals(2,questionRepository.count());
        questionRepository.deleteById(2L);
        assertEquals(1,questionRepository.count());
    }

    @Test
    void testLargeData(){
        for(int i=1; i<=300; i++){
            QuestionDto questionDto = QuestionDto.builder()
                    .subject("테스트 데이터 입니다. ["+ i + "]")
                    .content("내용 입니다. [" + i + "]")
                    .build();
            questionService.createQuestion(questionDto);
        }
    }
}