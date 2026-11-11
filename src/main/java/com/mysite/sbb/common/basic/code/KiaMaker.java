package com.mysite.sbb.common.basic.code;

public class KiaMaker {
    public Car sell(Money money) {
        System.out.println("기아차(입금) : " + money.getAmount());
        Car car = new Car("K5");
        System.out.println("차 이름 : " + car.getName());
        return car;
    }
}
