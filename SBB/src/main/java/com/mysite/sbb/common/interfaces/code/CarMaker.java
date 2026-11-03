// ============================================================================
// [DI 연습 2단계(interfaces)] CarMaker = 자동차 제조사의 "공통 규칙(인터페이스)"
//  - "sell(Money) 메서드를 가지고 Car 를 돌려준다" 는 약속만 정의
//  - HyundaiMaker, KiaMaker 가 이 인터페이스를 implements(구현)함
//  - OrderManager 는 구체 클래스가 아닌 이 인터페이스 타입만 알면 됨 → 제조사 교체가 쉬워짐(느슨한 결합)
// ============================================================================
package com.mysite.sbb.common.interfaces.code;

public interface CarMaker {
    public Car sell(Money money);   // 구현 클래스가 반드시 만들어야 하는 메서드 (몸통 없음)
}
