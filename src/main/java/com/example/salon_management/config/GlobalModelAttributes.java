package com.example.salon_management.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    @ModelAttribute("activeMenu")
    public String activeMenu(HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (uri == null) return "";
        if (uri.startsWith("/services")) return "services";
        if (uri.startsWith("/productusage")) return "productusage";
        if (uri.startsWith("/employees")) return "employees";
        if (uri.startsWith("/customers")) return "customers";
        if (uri.startsWith("/promotions")) return "promotions";
        if (uri.startsWith("/payments")) return "payments";
        return "";
    }
}
