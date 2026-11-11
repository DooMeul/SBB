package com.mysite.sbb.common.basic.code;

public class OrderManager {

    //private HyundaiMaker maker;

    private KiaMaker maker;

    public OrderManager() {
    //    maker = new HyundaiMaker();
        maker = new KiaMaker();
    }

    public void order() {
        Money money = new Money(1000);
        System.out.println("판매상(입금) : " + money.getAmount());
        Car car = maker.sell(money);
        System.out.println("판매상(인수) : " + car.getName());
    }
}
