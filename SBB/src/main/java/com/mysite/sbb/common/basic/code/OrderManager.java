// ============================================================================
// [DI 연습 1단계(basic)] OrderManager = 판매상(대리점). 손님 대신 제조사에 돈을 주고 차를 받아 옴
//
//  ★ 문제점: 제조사를 바꾸려면 이 파일의 "필드 타입"과 "new 부분"을 직접 고쳐야 함 (강한 결합)
//           → 2단계(interfaces)에서 인터페이스로 해결, 3단계(annotation)에서 스프링이 자동으로 해결
//
//  - 실행 : common/basic/main/BasicMain.java
// ============================================================================
package com.mysite.sbb.common.basic.code;

public class OrderManager {

    //private HyundaiMaker maker;     // 현대로 바꾸려면 이 줄 주석 해제 + 아래 줄 주석 처리

    private KiaMaker maker;           // 현재는 기아 제조사를 사용 (타입이 KiaMaker 로 고정됨)

    public OrderManager() {           // 생성자에서 직접 제조사 객체를 만듦
    //    maker = new HyundaiMaker();
        maker = new KiaMaker();       // OrderManager 가 직접 new → 의존 객체를 "스스로" 생성
    }

    public void order() {                                          // 주문 실행
        Money money = new Money(1000);                             // 1000원 준비
        System.out.println("판매상(입금) : " + money.getAmount());
        Car car = maker.sell(money);                               // 제조사에 돈 주고 차 받기
        System.out.println("판매상(인수) : " + car.getName());       // 받은 차 이름 출력
    }
}
