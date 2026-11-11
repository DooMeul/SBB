package com.mysite.sbb.common.interfaces.code;

public class KiaMaker implements CarMaker {

    @Override
    public Car sell(Money money) {
        System.out.println("기아차(입금) : " + money.getAmount());
        Car car = new Car("K5");
        System.out.println("차 이름 : " + car.getName());
        return car;
    }
}
