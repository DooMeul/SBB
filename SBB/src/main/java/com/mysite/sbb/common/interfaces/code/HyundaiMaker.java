// ============================================================================
// [DI 연습 2단계(interfaces)] HyundaiMaker = CarMaker 인터페이스를 구현한 현대 제조사
// ============================================================================
package com.mysite.sbb.common.interfaces.code;

public class HyundaiMaker implements CarMaker {   // CarMaker 규칙을 따르겠다

    @Override                                      // 인터페이스 메서드를 재정의(구현)한다는 표시
    public Car sell(Money money) {
        System.out.println("현대차(입금) : " + money.getAmount());
        Car car = new Car("쏘나타");
        System.out.println("차 이름 : " + car.getName());
        return car;
    }
}
