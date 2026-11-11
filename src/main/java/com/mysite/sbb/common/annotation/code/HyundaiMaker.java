package com.mysite.sbb.common.annotation.code;

import org.springframework.stereotype.Component;

@Component("Hyundai")
public class HyundaiMaker implements CarMaker {

    @Override
    public Car sell(Money money) {
        System.out.println("현대차(입금) : " + money.getAmount());
        Car car = new Car("쏘나타");
        System.out.println("차 이름 : " + car.getName());
        return car;
    }
}
