package com.mysite.sbb.common.interfaces.code;

public class OrderManager {

    private CarMaker maker;

    public OrderManager(CarMaker maker) {
        this.maker = maker;
    }

    public void setMaker(CarMaker maker) {
        this.maker = maker;
    }

    public void order() {
        Money money = new Money(1000);
        System.out.println("판매상(입금) : " + money.getAmount());
        Car car = maker.sell(money);
        System.out.println("판매상(인수) : " + car.getName());
    }
}
