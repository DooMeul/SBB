// ============================================================================
// [DI 연습 3단계(annotation)] CarMaker = 제조사 공통 인터페이스 (2단계와 동일)
//  - 구현체 : HyundaiMaker(@Component("Hyundai")), KiaMaker(@Component("Kia"))
// ============================================================================
package com.mysite.sbb.common.annotation.code;

public interface CarMaker {
    public Car sell(Money money);
}
