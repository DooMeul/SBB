package com.mysite.sbb.book.repository;

import com.mysite.sbb.book.dto.BookDto;
import com.mysite.sbb.book.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookService bookService;
    @Test
    void testLargeData(){
        for(int i=1; i<=120; i++){
            BookDto bookDto = BookDto.builder()
                    .bookname("테스트 데이터 입니다. ["+ i + "]")
                    .author("저자 입니다. [" + i + "]")
                    .build();
            bookService.createBook(bookDto);
        }
    }
}
