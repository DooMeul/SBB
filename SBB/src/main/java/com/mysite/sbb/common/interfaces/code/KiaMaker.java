// ============================================================================
// [DI 연습 2단계(interfaces)] KiaMaker = CarMaker 인터페이스를 구현한 기아 제조사
// ============================================================================
package com.mysite.sbb.common.interfaces.code;

public class KiaMaker implements CarMaker {

    @Override
    public Car sell(Money money) {
        System.out.println("기아차(입금) : " + money.getAmount());
        Car car = new Car("K5");
        System.out.println("차 이름 : " + car.getName());
        return car;
    }
}
