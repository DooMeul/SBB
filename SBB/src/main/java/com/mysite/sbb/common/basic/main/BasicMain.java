// ============================================================================
// [DI 연습 1단계(basic)] 실행용 main
//  - IntelliJ 에서 이 파일의 ▶ 버튼으로 단독 실행 (웹서버와 무관)
//  - 결과 : 판매상(입금) : 1000 → 기아차(입금) : 1000 → 차 이름 : K5 → 판매상(인수) : K5
// ============================================================================
package com.mysite.sbb.common.basic.main;

import com.mysite.sbb.common.basic.code.OrderManager;

public class BasicMain {
    public static void main(String[] args) {
        OrderManager orderManager = new OrderManager();  // 판매상 생성 (안에서 KiaMaker 를 직접 만듦)
        orderManager.order();                            // 주문
    }
}
