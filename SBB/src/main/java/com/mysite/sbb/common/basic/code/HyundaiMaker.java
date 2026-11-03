// ============================================================================
// [DI 연습 1단계(basic)] HyundaiMaker = 현대 자동차 제조사 (돈을 받으면 쏘나타를 줌)
//  - 인터페이스 없이 그냥 클래스 → OrderManager 가 이 클래스에 "직접" 묶여 있음(강한 결합)
// ============================================================================
package com.mysite.sbb.common.basic.code;

public class HyundaiMaker {
    public Car sell(Money money) {                                 // 돈을 받고 차를 판매
        System.out.println("현대차(입금) : " + money.getAmount());  // 받은 금액 출력
        Car car = new Car("쏘나타");                                 // 쏘나타 생성
        System.out.println("차 이름 : " + car.getName());
        return car;                                                // 차를 돌려줌
    }
}
