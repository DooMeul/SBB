package com.mysite.sbb.common.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@RestController
public class DataController {

    @GetMapping("/data")
    public Point data() {
        Point p = new Point(10,20);
        return p;
    }
}
