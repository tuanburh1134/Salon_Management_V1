package com.example.salon_management.service.impl;

import com.example.salon_management.entity.*;
import com.example.salon_management.dto.PaymentForm;

import com.example.salon_management.repository.*;
import com.example.salon_management.service.PaymentService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final CustomerRepository customerRepository;
    private final PromotionRepository promotionRepository;
    private final ServiceItemRepository serviceItemRepository;
    private final ProductUsageRepository productUsageRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              CustomerRepository customerRepository,
                              PromotionRepository promotionRepository,
                              ServiceItemRepository serviceItemRepository,
                              ProductUsageRepository productUsageRepository) {
        this.paymentRepository = paymentRepository;
        this.customerRepository = customerRepository;
        this.promotionRepository = promotionRepository;
        this.serviceItemRepository = serviceItemRepository;
        this.productUsageRepository = productUsageRepository;
    }

    @Override
    @Transactional
    public Payment create(PaymentForm form) {
        // 1) Khách hàng
        Customer customer = customerRepository.findById(form.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        // 2) Khởi tạo payment
        Payment payment = new Payment();
        payment.setCustomer(customer);

        // 3) Phương thức thanh toán
        try {
            if (form.getMethod() != null) {
                if (form.getMethod() instanceof PaymentMethod) {
                    payment.setMethod((PaymentMethod) form.getMethod());
                } else {
                    payment.setMethod(PaymentMethod.valueOf(form.getMethod().toString()));
                }
            }
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid payment method");
        }

        // 4) Ngày giờ thanh toán (nếu form để trống sẽ được @PrePersist tự set)
        if (form.getPaidAt() != null) {
            payment.setPaidAt(form.getPaidAt());
        }

        // 5) Khuyến mãi (NULL-SAFE)
        Integer discountPercent = 0;
        if (form.getPromotionId() != null) {
            Promotion promo = promotionRepository.findById(form.getPromotionId())
                    .orElseThrow(() -> new IllegalArgumentException("Promotion not found"));
            payment.setPromotion(promo);
            // Đổi lại nếu entity của bạn dùng tên trường khác
            discountPercent = promo.getPercent();
        }
        payment.setDiscountPercent(discountPercent);

        // 6) Tính tổng trước giảm
        BigDecimal totalBefore = BigDecimal.ZERO;

        // Dịch vụ
        if (form.getServiceIds() != null) {
            for (Long sid : form.getServiceIds()) {
                ServiceItem s = serviceItemRepository.findById(sid)
                        .orElseThrow(() -> new IllegalArgumentException("Service not found: " + sid));
                if (s.getPrice() != null) {
                    totalBefore = totalBefore.add(s.getPrice());
                }
            }
        }

        // Sản phẩm sử dụng
        if (form.getProductIds() != null) {
            for (Long pid : form.getProductIds()) {
                ProductUsage p = productUsageRepository.findById(pid)
                        .orElseThrow(() -> new IllegalArgumentException("Product usage not found: " + pid));
                BigDecimal unit = p.getPrice() == null ? BigDecimal.ZERO : p.getPrice();
                int qty = p.getQuantityUsed() == null ? 0 : p.getQuantityUsed();
                BigDecimal line = unit.multiply(BigDecimal.valueOf(qty));
                totalBefore = totalBefore.add(line);
            }
        }

        payment.setTotalBefore(totalBefore);

        // 7) Giảm & tổng sau giảm
        BigDecimal discountAmount = totalBefore
                .multiply(BigDecimal.valueOf(discountPercent))
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);

        payment.setDiscountAmount(discountAmount);
        payment.setTotalAfter(totalBefore.subtract(discountAmount));

        // 8) Lưu
        return paymentRepository.save(payment);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Payment> findAllDesc() {
        return paymentRepository.findAllByOrderByIdDesc();
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Payment> findByPaidAtBetweenDesc(LocalDateTime start, LocalDateTime end) {
        return paymentRepository.findByPaidAtBetweenOrderByIdDesc(start, end);
    }
}
