// ============================================================================
// [DI 연습 2단계(interfaces)] 실행용 main
//  - 개발자가 직접 제조사 객체를 만들어서 OrderManager 에 "주입"해 줌
//  - 결과 : 현대차 → 쏘나타
// ============================================================================
package com.mysite.sbb.common.interfaces.main;

import com.mysite.sbb.common.interfaces.code.*;   // code 패키지의 모든 클래스 import

public class InterfaceMain {
    public static void main(String[] args) {
        CarMaker maker = new HyundaiMaker();                // 제조사 선택 (KiaMaker 로 바꾸면 K5)
        OrderManager orderManager = new OrderManager(maker); // 생성자로 주입
        //orderManager.setMaker(maker);                     // (또는) setter 로 주입

        orderManager.order();
    }
}
