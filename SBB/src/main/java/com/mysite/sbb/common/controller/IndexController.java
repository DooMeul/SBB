// ============================================================================
// [파일 역할] IndexController = 메인(홈) 화면과 연습용 화면으로 이동시키는 컨트롤러
//
//  [URL 정리]
//   GET /      → templates/index.html  (메인 페이지, header.html 의 로고/Home 링크가 여기로 옴)
//   GET /temp  → templates/temp.html   (새 페이지 만들 때 복사해서 쓰는 "빈 틀" 페이지)
//
//  ※ 새 페이지를 추가하는 방법 (시험용 요약)
//    1) 여기에 @GetMapping("/주소") 메서드 추가 → return "파일이름";
//    2) templates/파일이름.html 만들기 (temp.html 복붙)
// ============================================================================
package com.mysite.sbb.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller   // 리턴값 = 템플릿(html) 파일 이름
public class IndexController {
    @GetMapping("/")              // http://localhost:8080/ 접속 시 실행
   // @ResponseBody               // (참고) 이걸 붙이면 html 파일이 아니라 "index" 라는 글자 자체가 화면에 출력됨
    public String index2() {
        return "index";           // templates/index.html 을 보여줌 (확장자 .html 은 생략)
    }

    @GetMapping("/temp")          // http://localhost:8080/temp
    public String temp() {
        return "temp";            // templates/temp.html
    }
}
