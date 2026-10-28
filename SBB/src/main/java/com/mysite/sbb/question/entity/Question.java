package com.mysite.sbb.question.entity;

import com.mysite.sbb.answer.entity.Answer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
//@Table(name = "ABCD")
@ToString(exclude = "answerList")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name="question_id")
    private long id; // 아이디

    @Column(length = 100, nullable = false)
    private String subject; // 질문 제목

    @Column(columnDefinition = "TEXT")
    private String content; // 질문 내용

    private LocalDateTime created; // 생성일
    //fetch = FetchType.LAZY는 디폴트임
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question",cascade = CascadeType.ALL)
    private List<Answer> answerList;

}