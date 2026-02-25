package com.example.studentportalspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentController {

    @GetMapping("/pay")
    public String pay() {
        System.out.println("Pay page");
        return "pay";
    }
}
