package com.mysite.sbb.book.entity;

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

public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // 아이디

    @Column(length = 100, nullable = false)
    private String bookname; // 책제목

    @Column(columnDefinition = "TEXT")
    private String author; // 저자

    private LocalDateTime created; // 생성일
}
