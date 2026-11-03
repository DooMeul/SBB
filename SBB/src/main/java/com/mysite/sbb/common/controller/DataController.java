// ============================================================================
// [파일 역할] DataController = @RestController 연습용. HTML 이 아니라 "데이터(JSON)"를 응답하는 예제
//
//  [URL 정리]
//   GET /data  → 브라우저에 {"x":10.0,"y":20.0, ...} 같은 JSON 이 출력됨
//
//  ※ @Controller 와 @RestController 차이
//    @Controller     : return "index"  → templates/index.html 화면을 찾아서 보여줌
//    @RestController : return 객체     → 객체를 JSON 으로 바꿔서 그대로 응답 (= @Controller + @ResponseBody)
// ============================================================================
package com.mysite.sbb.common.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;   // java.awt.Point (x, y 좌표를 가진 자바 기본 클래스)를 사용하기 위해 import

@RestController   // 리턴값을 JSON 데이터로 응답
public class DataController {

    @GetMapping("/data")
    public Point data() {
        Point p = new Point(10,20);   // x=10, y=20 인 점 객체 생성
        return p;                     // → JSON 으로 변환되어 브라우저에 출력
    }
}
