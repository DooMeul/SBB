package com.mysite.sbb.book.repository;

import com.mysite.sbb.book.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>  {
    Book findByBookname(String bookname); // Optional뜻 있는 놈이 있거나 없거나 확인
    Optional<Book> findByBooknameAndAuthor(String bookname, String author);

    List<Book> findByBooknameLike(String bookname);
    Page<Book> findAll(Pageable pageable);
}
