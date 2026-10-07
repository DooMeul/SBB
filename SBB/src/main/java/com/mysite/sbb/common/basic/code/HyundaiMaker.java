package com.mysite.sbb.common.basic.code;

public class HyundaiMaker {
    public Car sell(Money money) {
        System.out.println("현대차(입금) : " + money.getAmount());
        Car car = new Car("쏘나타");
        System.out.println("차 이름 : " + car.getName());
        return car;
    }
}
