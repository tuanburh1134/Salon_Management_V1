package com.example.salon_management.controller;

import com.example.salon_management.dto.CustomerForm;
import com.example.salon_management.entity.Customer;
import com.example.salon_management.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    / ========================== DANH SÁCH KHÁCH HÀNG ==========================
    @GetMapping({"", "/", "/list"})
    public String list(@RequestParam(value = "q", required = false) String keyword,
                       @RequestParam(value = "memberType", required = false) String memberType,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       @RequestParam(defaultValue = "name") String sortBy,
                       @RequestParam(defaultValue = "asc") String dir,
                       Model model) {

        Sort sort = dir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Customer> customers = service.search(keyword, memberType, pageable);

        String nextDir = dir.equalsIgnoreCase("asc") ? "desc" : "asc";

        model.addAttribute("data", customers);
        model.addAttribute("q", keyword == null ? "" : keyword.trim());
        model.addAttribute("memberType", memberType == null ? "" : memberType);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("dir", dir);
        model.addAttribute("nextDir", nextDir);

        model.addAttribute("sortByNameUrl", buildUrl(keyword, memberType, 0, size, "name", nextDir));
        model.addAttribute("sortByPointUrl", buildUrl(keyword, memberType, 0, size, "point", nextDir));

        model.addAttribute("hasPrev", customers.hasPrevious());
        model.addAttribute("hasNext", customers.hasNext());
        model.addAttribute("currentPage", customers.getNumber() + 1);
        model.addAttribute("totalPages", customers.getTotalPages());
        model.addAttribute("prevUrl", customers.hasPrevious()
                ? buildUrl(keyword, memberType, customers.getNumber() - 1, size, sortBy, dir)
                : null);
        model.addAttribute("nextUrl", customers.hasNext()
                ? buildUrl(keyword, memberType, customers.getNumber() + 1, size, sortBy, dir)
                : null);

        model.addAttribute("memberTypes", new String[]{"", "MOI", "THAN_QUEN", "DAC_BIET"});
        model.addAttribute("pageTitle", "Danh sách khách hàng");

        return "customer/list";
    }
}
