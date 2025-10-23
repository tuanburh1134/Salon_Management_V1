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

    // LIST
    @GetMapping({"", "/", "/list"})
    public String list(@RequestParam(value = "q", required = false) String q,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       @RequestParam(defaultValue = "name") String sortBy,
                       @RequestParam(defaultValue = "asc") String dir,
                       Model model) {

        Sort sort = "asc".equalsIgnoreCase(dir)
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Customer> data = service.search(q, pageable);

        String nextDir = "asc".equalsIgnoreCase(dir) ? "desc" : "asc";

        model.addAttribute("data", data);
        model.addAttribute("q", q == null ? "" : q);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("dir", dir);
        model.addAttribute("sortByNameUrl", buildUrl(q, 0, size, "name", nextDir));
        model.addAttribute("sortByPointUrl", buildUrl(q, 0, size, "point", nextDir));

        model.addAttribute("hasPrev", data.hasPrevious());
        model.addAttribute("hasNext", data.hasNext());
        model.addAttribute("currentPage", data.getNumber() + 1);
        model.addAttribute("totalPages", data.getTotalPages());
        model.addAttribute("prevUrl", data.hasPrevious() ? buildUrl(q, data.getNumber() - 1, size, sortBy, dir) : null);
        model.addAttribute("nextUrl", data.hasNext() ? buildUrl(q, data.getNumber() + 1, size, sortBy, dir) : null);

        return "customer/list";
    }

    // Helpers
    private String buildUrl(String q, int page, int size, String sortBy, String dir) {
        String kw = (q == null || q.isBlank()) ? "" : q.trim().replace(" ", "%20");
        return "/customers?q=" + kw + "&page=" + page + "&size=" + size + "&sortBy=" + sortBy + "&dir=" + dir;
    }
}