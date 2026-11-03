// ============================================================================
// [DI 연습 3단계(annotation)] OrderManager = 판매상 (스프링이 알아서 제조사를 넣어 줌)
//
//  ★ 2단계와 차이점: main 에서 new 하지 않음. 스프링 컨테이너가 객체 생성 + 주입까지 전부 해 줌
//    - @Service   : 이 클래스도 Bean 으로 등록 (Bean 이름은 클래스명 첫 글자 소문자 → "orderManager")
//    - @Autowired : 맞는 타입(CarMaker)의 Bean 을 찾아서 자동 주입
//    - CarMaker 구현체가 2개(Hyundai, Kia)라서 어떤 걸 쓸지 이름으로 골라야 함
//        → @Qualifier("Kia") (스프링 방식) 또는 @Named("Kia") (자바 표준 jakarta.inject 방식)
//
//  ※ 이 게시판 프로젝트의 Controller/Service 에서 쓰는 @RequiredArgsConstructor + final 필드도
//    결국 이 "생성자 주입"을 Lombok 으로 자동 작성한 것!
//
//  - 실행 : common/annotation/main/AnnotationMain.java
// ============================================================================
package com.mysite.sbb.common.annotation.code;

import jakarta.inject.Named;                                   // build.gradle 의 jakarta.inject-api 라이브러리
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OrderManager {

    // [방법1] 필드 주입 : 필드에 바로 @Autowired (+ 이름 지정 @Named)
    @Autowired
    @Named("Kia")
    private CarMaker maker;

    // [방법2] 생성자 주입 (★권장) : 생성자 매개변수로 받음. @Qualifier 로 "Kia" Bean 선택
    //   ※ 현재는 방법1, 방법2 가 둘 다 적혀 있어서 같은 Kia 를 두 번 넣는 셈 (연습용이라 둘 다 둔 것)
    @Autowired
    public OrderManager(@Qualifier("Kia") CarMaker maker) {
        this.maker = maker;
    }
    // [방법3] setter 주입 : 위에 @Autowired 를 붙이면 setter 로도 주입 가능 (지금은 안 붙어 있음)
    public void setMaker(CarMaker maker) {
        this.maker = maker;
    }

    public void order() {
        Money money = new Money(1000);
        System.out.println("판매상(입금) : " + money.getAmount());
        Car car = maker.sell(money);                    // 주입된 Kia 제조사 사용
        System.out.println("판매상(인수) : " + car.getName());
    }
}
