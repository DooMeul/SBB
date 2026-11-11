package com.mysite.sbb.answer.entity;

import com.mysite.sbb.question.entity.Question;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
//@Table(name = "ABCD")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100, nullable = true)
    private String content;

    private LocalDateTime created;

    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "question_id", nullable = false)
    private Question question;
}