package com.mysite.sbb.book.controller;

import com.mysite.sbb.book.dto.BookDto;
import com.mysite.sbb.book.entity.Book;
import com.mysite.sbb.book.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor // 자동으로 생성자를 만들어줌(final 꼭 써줘야함)
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;
    @GetMapping("/booklist")
    //@ResponseBody //첫번 째 데이터 값 보내기
    public String booklist(Model model,
                       @RequestParam(value = "page" ,defaultValue="0") int page){
        Page<Book> paging = bookService.getBooks(page);
        log.info("================================> paging : {}", paging);
        model.addAttribute("paging", paging);
        return "book/booklist";
    }

    @GetMapping("/create")
    public String createInputForm(BookDto bookDto){
        return "book/inputForm";
    }

//    @PostMapping("/create")
//    public String createQuestion(@RequestParam("subject") String subject,
//                                 @RequestParam("content") String content){
//        log.info("=========> subject : {} content : {}", subject, content);
//        questionService.createQuestion(subject, content);
//        return "redirect:/question/list";
//    }

    @PostMapping("/create")
    public String createQuestion(@Valid BookDto bookDto,
                                 BindingResult bindingResult){
        log.info("=========> bookDto : {}", bookDto);
        if(bindingResult.hasErrors()){
            return "question/inputForm";
        }

        bookService.createBook(bookDto);
        return "redirect:/question/list";
    }
}
