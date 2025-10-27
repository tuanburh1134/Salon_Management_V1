package com.example.salon_management.controller;

import com.example.salon_management.dto.PaymentForm;
import com.example.salon_management.entity.Payment;
import com.example.salon_management.entity.PaymentMethod;
import com.example.salon_management.repository.CustomerRepository;
import com.example.salon_management.repository.ProductUsageRepository;
import com.example.salon_management.repository.PromotionRepository;
import com.example.salon_management.repository.ServiceItemRepository;
import com.example.salon_management.service.PaymentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    // Chỉ giữ PaymentService; các danh mục khác đọc trực tiếp từ repository
    private final PaymentService paymentService;
    private final CustomerRepository customerRepository;
    private final PromotionRepository promotionRepository;
    private final ServiceItemRepository serviceItemRepository;
    private final ProductUsageRepository productUsageRepository;

    public PaymentController(PaymentService paymentService,
                             CustomerRepository customerRepository,
                             PromotionRepository promotionRepository,
                             ServiceItemRepository serviceItemRepository,
                             ProductUsageRepository productUsageRepository) {
        this.paymentService = paymentService;
        this.customerRepository = customerRepository;
        this.promotionRepository = promotionRepository;
        this.serviceItemRepository = serviceItemRepository;
        this.productUsageRepository = productUsageRepository;
    }

    // ===== Danh sách thanh toán (có lọc ngày - optional) =====
    @GetMapping
    public String list(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            Model model) {

        List<Payment> payments;
        if (from != null && to != null) {
            LocalDateTime start = from.atStartOfDay();
            LocalDateTime end = to.atTime(LocalTime.MAX);
            payments = paymentService.findByPaidAtBetweenDesc(start, end);
        } else {
            payments = paymentService.findAllDesc();
        }

        model.addAttribute("pageTitle", "Danh sách thanh toán");
        model.addAttribute("payments", payments);
        return "payments/list";
    }

    // ===== Hiển thị form tạo mới =====
    @GetMapping("/new")
    public String createForm(Model model) {
        PaymentForm form = new PaymentForm();
        model.addAttribute("form", form);
        model.addAttribute("pageTitle", "Tạo thanh toán");

        // Dùng repository để lấy dữ liệu cho các select/checkbox
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("promotions", promotionRepository.findAll());
        model.addAttribute("services", serviceItemRepository.findAll());
        model.addAttribute("products", productUsageRepository.findAll());
        model.addAttribute("methods", PaymentMethod.values());

        return "payments/form";
    }

    // ===== Lưu =====
    @PostMapping
    public String save(@ModelAttribute("form") PaymentForm form,
                       RedirectAttributes ra) {
        Payment p = paymentService.create(form);
        ra.addFlashAttribute("success", "Đã tạo thanh toán #" + p.getId());
        return "redirect:/payments";
    }
}
