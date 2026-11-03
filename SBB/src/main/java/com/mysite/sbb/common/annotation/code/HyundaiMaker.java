// ============================================================================
// [DI 연습 3단계(annotation)] HyundaiMaker
//  - @Component("Hyundai") : 스프링이 이 클래스를 자동으로 객체(Bean)로 만들고 이름을 "Hyundai" 로 등록
//  - 스캔 설정 : src/main/resources/car-config.xml 의 <context:component-scan base-package="...annotation.code"/>
// ============================================================================
package com.mysite.sbb.common.annotation.code;

import org.springframework.stereotype.Component;

@Component("Hyundai")   // Bean 이름 = "Hyundai"  (OrderManager 에서 @Qualifier("Hyundai") 로 선택 가능)
public class HyundaiMaker implements CarMaker {

    @Override
    public Car sell(Money money) {
        System.out.println("현대차(입금) : " + money.getAmount());
        Car car = new Car("쏘나타");
        System.out.println("차 이름 : " + car.getName());
        return car;
    }
}
