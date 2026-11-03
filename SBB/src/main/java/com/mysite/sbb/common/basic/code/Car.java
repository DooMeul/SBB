// ============================================================================
// [DI(의존성 주입) 연습 1단계(basic) - 스프링 없이 직접 new 로 객체 생성]
// [파일 역할] Car = 판매되는 "자동차" 데이터 클래스 (차 이름 하나만 가짐)
//  - 사용처 : 같은 패키지의 HyundaiMaker / KiaMaker 가 new Car("쏘나타") 처럼 만들어서 리턴
//  - ※ 웹사이트(게시판) 기능과는 상관없는 "스프링 개념 연습용" 코드
// ============================================================================
package com.mysite.sbb.common.basic.code;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                 // getName(), setName(), toString() 등 자동 생성
@AllArgsConstructor   // new Car("쏘나타") 생성자
@NoArgsConstructor    // new Car() 생성자
public class Car {
    private String name;    // 브랜드 이름

}
