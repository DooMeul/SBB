package com.mysite.sbb.answer.repository;

import com.mysite.sbb.answer.entity.Answer;
import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.question.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void testSave() {
        Question question = questionRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("해당 질문이 없습니다."));
        Answer answer = Answer.builder()
                .content("저도 아직 잘 몰라요")
                .question(question)
                .created(LocalDateTime.now())
                .build();
        Answer save = answerRepository.save(answer);
        System.out.println("save : " + save);
        assertEquals(1,save.getId());
    }


}