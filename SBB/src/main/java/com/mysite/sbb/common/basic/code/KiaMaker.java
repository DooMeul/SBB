// ============================================================================
// [DI 연습 1단계(basic)] KiaMaker = 기아 자동차 제조사 (돈을 받으면 K5를 줌)
//  - HyundaiMaker 와 메서드 모양은 같지만 공통 인터페이스가 없어서 서로 바꿔 끼우기 불편함
// ============================================================================
package com.mysite.sbb.common.basic.code;

public class KiaMaker {
    public Car sell(Money money) {
        System.out.println("기아차(입금) : " + money.getAmount());
        Car car = new Car("K5");                                   // K5 생성
        System.out.println("차 이름 : " + car.getName());
        return car;
    }
}
