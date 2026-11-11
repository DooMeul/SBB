package com.mysite.sbb.book.service;

import com.mysite.sbb.book.dto.BookDto;
import com.mysite.sbb.book.entity.Book;
import com.mysite.sbb.book.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookService {
    //    @Autowired //자동 객체 생성
    private final BookRepository bookRepository; //final 안쓰면 생성자 안생김

    public Page<Book> getBooks(int page){
//        Pageable pageable = PageRequest.of(page, 10,  Sort.Direction.DESC, "created");
        Pageable pageable = PageRequest.of(page, 10,  Sort.by(Sort.Order.desc("created"))); // 소팅
        Page<Book> paging = bookRepository.findAll(pageable);
        return paging;
    }

    public Book getBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 엔티티가 없습니다."));
        return book;
    }

    public void createBoook(String bookname, String author) {
        Book book = Book.builder()
                .bookname(bookname)
                .author(author)
                .created(LocalDateTime.now())
                .build();
        bookRepository.save(book);
    }

    public void createBook(BookDto bookDto) {
        Book book = Book.builder()
                .bookname(bookDto.getBookname())
                .author(bookDto.getAuthor())
                .created(LocalDateTime.now())
                .build();
        bookRepository.save(book);
    }
}
