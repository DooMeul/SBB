// ============================================================================
// [DI(의존성 주입) 연습 3단계(annotation) - 스프링이 @Component/@Autowired 로 자동 주입]
// [파일 역할] Money = 자동차 살 때 내는 "돈" 데이터 클래스 (금액 하나만 가짐)
//  - 사용처 : OrderManager.order() 에서 new Money(1000) 으로 만들어 Maker.sell(money) 에 전달
// ============================================================================
package com.mysite.sbb.common.annotation.code;

import lombok.*;

@Getter               // getAmount()
@Setter               // setAmount()
@ToString
@NoArgsConstructor
@AllArgsConstructor   // new Money(1000)
@Builder
public class Money {
    private int amount;     // 금액
}
