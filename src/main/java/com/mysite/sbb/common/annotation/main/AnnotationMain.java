package com.mysite.sbb.common.annotation.main;

import com.mysite.sbb.common.annotation.code.CarMaker;
import com.mysite.sbb.common.annotation.code.HyundaiMaker;
import com.mysite.sbb.common.annotation.code.OrderManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnotationMain {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("car-config.xml");

        OrderManager orderManager = (OrderManager) context.getBean("orderManager", OrderManager.class);
        System.out.println("maager : " + orderManager);
        orderManager.order();
    }
}
