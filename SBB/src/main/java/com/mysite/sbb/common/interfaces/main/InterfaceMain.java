package com.mysite.sbb.common.interfaces.main;

import com.mysite.sbb.common.interfaces.code.*;

public class InterfaceMain {
    public static void main(String[] args) {
        CarMaker maker = new HyundaiMaker();
        OrderManager orderManager = new OrderManager(maker);
        //orderManager.setMaker(maker);

        orderManager.order();
    }
}
