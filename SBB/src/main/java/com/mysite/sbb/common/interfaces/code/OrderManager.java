// ============================================================================
// [DI 연습 2단계(interfaces)] OrderManager = 판매상
//
//  ★ 1단계와 차이점: 필드 타입이 인터페이스(CarMaker)이고, 제조사 객체를 "밖에서 받아옴"(주입)
//     → 현대/기아 어떤 걸 넣어도 이 파일은 고칠 필요 없음
//     → 이렇게 외부에서 넣어주는 것 = DI(Dependency Injection, 의존성 주입)
//
//  - 실행 : common/interfaces/main/InterfaceMain.java (여기서 new HyundaiMaker() 를 만들어 넣어 줌)
// ============================================================================
package com.mysite.sbb.common.interfaces.code;

public class OrderManager {

    private CarMaker maker;                    // 인터페이스 타입 → 어떤 제조사든 담을 수 있음

    public OrderManager(CarMaker maker) {      // [방법1] 생성자 주입 : 객체 만들 때 제조사를 받음
        this.maker = maker;
    }

    public void setMaker(CarMaker maker) {     // [방법2] setter 주입 : 나중에 제조사를 바꿔 끼울 수 있음
        this.maker = maker;
    }

    public void order() {
        Money money = new Money(1000);
        System.out.println("판매상(입금) : " + money.getAmount());
        Car car = maker.sell(money);           // 실제로 어떤 제조사인지 몰라도 sell() 호출 가능 (다형성)
        System.out.println("판매상(인수) : " + car.getName());
    }
}
