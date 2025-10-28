package com.example.salon_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LegacyRoutesController {

    @GetMapping("/payment")
    public String redirectPayment() {
        return "redirect:/payments";
    }

    @GetMapping("/templates/payment")
    public String redirectTemplatesPayment() {
        return "redirect:/payments";
    }
}
