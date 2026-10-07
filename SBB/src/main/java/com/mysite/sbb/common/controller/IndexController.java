package com.mysite.sbb.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @GetMapping("/")
   // @ResponseBody
    public String index2() {
        return "index";
    }

    @GetMapping("/temp")
    public String temp() {
        return "temp";
    }
}
