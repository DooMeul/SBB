// ============================================================================
// [DI 연습 3단계(annotation)] KiaMaker
//  - @Component("Kia") : 스프링이 자동으로 Bean 생성, 이름 "Kia"
//  - OrderManager 의 @Qualifier("Kia") / @Named("Kia") 가 이 Bean 을 가리킴
// ============================================================================
package com.mysite.sbb.common.annotation.code;

import org.springframework.stereotype.Component;

@Component("Kia")       // Bean 이름 = "Kia"
public class KiaMaker implements CarMaker {

    @Override
    public Car sell(Money money) {
        System.out.println("기아차(입금) : " + money.getAmount());
        Car car = new Car("K5");
        System.out.println("차 이름 : " + car.getName());
        return car;
    }
}
