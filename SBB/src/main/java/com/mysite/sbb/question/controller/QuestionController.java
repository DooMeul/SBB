package com.mysite.sbb.question.controller;

import com.mysite.sbb.answer.dto.AnswerDto;
import com.mysite.sbb.question.dto.QuestionDto;
import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.question.repository.QuestionRepository;
import com.mysite.sbb.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor // 자동으로 생성자를 만들어줌(final 꼭 써줘야함)
@RequestMapping("/question")
public class QuestionController {

    //    @Autowired //자동 객체 생성
    private final QuestionService questionService;

    @GetMapping("/list")
    //@ResponseBody //첫번 째 데이터 값 보내기
    public String list(Model model,
                       @RequestParam(value = "page" ,defaultValue="0") int page){
         Page<Question> paging = questionService.getList(page);
        log.info("================================> paging : {}", paging);
        model.addAttribute("paging", paging);
        return "question/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long id,
                         AnswerDto answerDto,
                         Model model){ // @PathVariable는 getmapping으로 들어온 id를 변수처리(자동으로 값을 세팅)
        log.info("========================> id : {}",id);
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question/detail";
    }

    @GetMapping("/create")
    public String createInputForm(QuestionDto questionDto){
        return "question/inputForm";
    }

//    @PostMapping("/create")
//    public String createQuestion(@RequestParam("subject") String subject,
//                                 @RequestParam("content") String content){
//        log.info("=========> subject : {} content : {}", subject, content);
//        questionService.createQuestion(subject, content);
//        return "redirect:/question/list";
//    }

    @PostMapping("/create")
    public String createQuestion(@Valid QuestionDto questionDto,
                                 BindingResult bindingResult){
        log.info("=========> questionDto : {}", questionDto);
        if(bindingResult.hasErrors()){
            return "question/inputForm";
        }

        questionService.createQuestion(questionDto);
        return "redirect:/question/list";
    }
}