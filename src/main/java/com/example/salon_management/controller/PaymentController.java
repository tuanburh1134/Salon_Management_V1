package com.example.salon_management.controller;

import com.example.salon_management.dto.PaymentForm;
import com.example.salon_management.entity.Booking;
import com.example.salon_management.entity.Payment;
import com.example.salon_management.entity.PaymentMethod;
import com.example.salon_management.repository.BookingRepository;
import com.example.salon_management.repository.PaymentRepository;
import com.example.salon_management.repository.PromotionRepository;
import com.example.salon_management.service.PaymentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final PromotionRepository promotionRepository;

    public PaymentController(PaymentService paymentService,
                             PaymentRepository paymentRepository,
                             BookingRepository bookingRepository,
                             PromotionRepository promotionRepository) {
        this.paymentService = paymentService;
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
        this.promotionRepository = promotionRepository;
    }

    @GetMapping
    public String list(
            @RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(value = "to", required = false)   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(value = "q", required = false) String q,
            Model model) {

        List<Payment> payments;
        if (from != null && to != null) {
            var start = from.atStartOfDay();
            var end = to.atTime(23, 59, 59);
            payments = paymentService.findByPaidAtBetweenDesc(start, end);
        } else {
            payments = paymentService.findAllDesc();
        }

        if (q != null && !q.isBlank()) {
            String lowerQ = q.toLowerCase().trim();
            payments = payments.stream()
                    .filter(p -> (p.getBookingCode() != null && p.getBookingCode().toLowerCase().contains(lowerQ))
                            || (p.getCustomer() != null && p.getCustomer().getName() != null && p.getCustomer().getName().toLowerCase().contains(lowerQ))
                    )
                    .collect(Collectors.toList());
        }


        model.addAttribute("payments", payments);
        model.addAttribute("pageTitle", "Quản lý thanh toán");
        return "payment/list";
    }

    @GetMapping("/new")
    public String showCreate(Model model) {
        PaymentForm form = new PaymentForm();
        form.setPaidAt(LocalDateTime.now());

        model.addAttribute("form", form);
        model.addAttribute("pageTitle", "Tạo thanh toán");
        model.addAttribute("bookings", bookingRepository.findAll());
        model.addAttribute("promotions", promotionRepository.findAll());
        model.addAttribute("methods", PaymentMethod.values());
        return "payment/form";
    }

    @PostMapping
    public String save(@Validated @ModelAttribute("form") PaymentForm form,
                       BindingResult bindingResult,
                       RedirectAttributes ra,
                       Model model) {

        // Validation errors
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Tạo thanh toán");
            model.addAttribute("bookings", bookingRepository.findAll());
            model.addAttribute("promotions", promotionRepository.findAll());
            model.addAttribute("methods", PaymentMethod.values());
            return "payment/form";
        }

        try {
            // Validate booking exists
            Booking booking = bookingRepository.findById(form.getBookingId())
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đặt lịch với ID: " + form.getBookingId()));

            // Check if booking is already paid
            if (paymentService.existsByBookingId(form.getBookingId())) {
                ra.addFlashAttribute("error", "Đặt lịch này đã được thanh toán rồi");
                return "redirect:/payments/new";
            }

            // Set booking code
            String bookingCode = (booking.getBookingCode() != null) ? booking.getBookingCode() : "BK-" + booking.getId();
            form.setBookingCode(bookingCode);

            // Fill information from Booking
            if (booking.getCustomer() != null) {
                form.setCustomerId(booking.getCustomer().getId());
            }

            // Set service IDs from booking
            if (booking.getService() != null) {
                form.setServiceIds(Collections.singletonList(booking.getService().getId()));
            } else {
                form.setServiceIds(Collections.emptyList());
            }

            // Initialize product IDs if null
            if (form.getProductIds() == null) {
                form.setProductIds(Collections.emptyList());
            }

            // Set current time if paidAt is null
            if (form.getPaidAt() == null) {
                form.setPaidAt(LocalDateTime.now());
            }

            // Validate promotion if provided
            if (form.getPromotionId() != null) {
                if (!promotionRepository.existsById(form.getPromotionId().intValue())) {
                    ra.addFlashAttribute("error", "Khuyến mãi không tồn tại");
                    return "redirect:/payments/new";
                }
            }

            // Create payment
            Payment payment = paymentService.create(form);
            ra.addFlashAttribute("success", "Đã tạo thanh toán thành công! Mã: " + payment.getBookingCode());
            return "redirect:/payments";

        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/payments/new";
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Có lỗi xảy ra khi tạo thanh toán: " + e.getMessage());
            return "redirect:/payments/new";
        }
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy thanh toán với ID: " + id));

        model.addAttribute("payment", payment);
        model.addAttribute("pageTitle", "Chi tiết thanh toán");
        return "payment/view";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy thanh toán với ID: " + id));

        // Convert Payment to PaymentForm for editing
        PaymentForm form = new PaymentForm();
        form.setBookingId(payment.getBookingId());
        form.setBookingCode(payment.getBookingCode());
        form.setCustomerId(payment.getCustomer() != null ? payment.getCustomer().getId() : null);
        form.setPromotionId(payment.getPromotion() != null ? payment.getPromotion().getId().longValue() : null);
        form.setMethod(payment.getMethod());
        form.setPaidAt(payment.getPaidAt());

        model.addAttribute("form", form);
        model.addAttribute("bookings", bookingRepository.findAll());
        model.addAttribute("promotions", promotionRepository.findAll());
        model.addAttribute("methods", PaymentMethod.values());
        model.addAttribute("pageTitle", "Chỉnh sửa thanh toán");
        return "payment/form";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        try {
            paymentRepository.deleteById(id);
            ra.addFlashAttribute("success", "Đã xóa thanh toán thành công");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Có lỗi xảy ra khi xóa thanh toán: " + e.getMessage());
        }
        return "redirect:/payments";
    }
}