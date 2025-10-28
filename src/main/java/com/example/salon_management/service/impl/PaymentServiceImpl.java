package com.example.salon_management.service.impl;

import com.example.salon_management.dto.PaymentForm;
import com.example.salon_management.entity.*;
import com.example.salon_management.repository.*;
import com.example.salon_management.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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

    // ================== Truy vấn ==================

    @Override
    public List<Payment> findAllDesc() {
        return paymentRepository.findAllByOrderByPaidAtDesc();
    }

    @Override
    public List<Payment> findByPaidAtBetweenDesc(LocalDateTime start, LocalDateTime end) {
        return paymentRepository.findByPaidAtBetweenOrderByPaidAtDesc(start, end);
    }

    @Override
    public boolean existsByBookingId(Long bookingId) {
        return paymentRepository.existsByBookingId(bookingId);
    }

    // ================== Tạo mới Payment ==================

    @Override
    @Transactional
    public Payment create(PaymentForm form) {
        // Validate form data
        if (form == null) {
            throw new IllegalArgumentException("Form không được null");
        }

        if (form.getBookingId() == null) {
            throw new IllegalArgumentException("Booking ID không được null");
        }

        if (form.getMethod() == null) {
            throw new IllegalArgumentException("Phương thức thanh toán không được null");
        }

        // Check if payment already exists for this booking
        if (existsByBookingId(form.getBookingId())) {
            throw new IllegalArgumentException("Đặt lịch này đã được thanh toán rồi");
        }

        Customer customer = (form.getCustomerId() == null) ? null :
                customerRepository.findById(form.getCustomerId()).orElse(null);

        List<ServiceItem> services = (form.getServiceIds() == null || form.getServiceIds().isEmpty()) ?
                Collections.emptyList() :
                serviceItemRepository.findAllById(form.getServiceIds());

        List<ProductUsage> products = (form.getProductIds() == null || form.getProductIds().isEmpty()) ?
                Collections.emptyList() :
                productUsageRepository.findAllById(form.getProductIds());

        BigDecimal totalBefore = BigDecimal.ZERO;
        List<PaymentItem> items = new ArrayList<>();

        // Process services
        for (ServiceItem s : services) {
            if (s == null) continue;

            BigDecimal price = nvl(s.getPrice());
            if (price.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Giá dịch vụ không được âm: " + s.getName());
            }

            totalBefore = totalBefore.add(price);

            PaymentItem item = new PaymentItem();
            item.setType(PaymentItem.ItemType.SERVICE);
            item.setRefId(s.getId());
            item.setName(s.getName() != null ? s.getName() : "Dịch vụ");
            item.setQuantity(1);
            item.setUnitPrice(price);
            item.setLineAmount(price);
            items.add(item);
        }

        // Process products
        for (ProductUsage pu : products) {
            if (pu == null) continue;

            BigDecimal price = nvl(pu.getPrice());
            if (price.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Giá sản phẩm không được âm: " + pu.getProductName());
            }

            BigDecimal qty = quantityToBigDecimal(pu.getQuantityUsed());
            if (qty.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Số lượng sản phẩm phải lớn hơn 0: " + pu.getProductName());
            }

            BigDecimal lineAmount = price.multiply(qty);
            totalBefore = totalBefore.add(lineAmount);

            PaymentItem item = new PaymentItem();
            item.setType(PaymentItem.ItemType.PRODUCT);
            item.setRefId(pu.getId());
            item.setName(pu.getProductName() != null ? pu.getProductName() : "Sản phẩm");
            item.setQuantity(pu.getQuantityUsed() != null ? pu.getQuantityUsed() : 1);
            item.setUnitPrice(price);
            item.setLineAmount(lineAmount);
            items.add(item);
        }

        // Validate total before discount
        if (totalBefore.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Tổng tiền trước giảm giá phải lớn hơn 0");
        }

        // Validate and get promotion
        Promotion promo = null;
        if (form.getPromotionId() != null) {
            promo = promotionRepository.findById(form.getPromotionId().intValue()).orElse(null);
            if (promo == null) {
                throw new IllegalArgumentException("Khuyến mãi không tồn tại với ID: " + form.getPromotionId());
            }

            // Validate promotion is active
            if (promo.getStatus() != null && !promo.getStatus().name().equals("ACTIVE")) {
                throw new IllegalArgumentException("Khuyến mãi không còn hoạt động");
            }

            // Validate promotion date range
            LocalDateTime now = LocalDateTime.now();
            if (promo.getStartAt() != null && now.toLocalDate().isBefore(promo.getStartAt())) {
                throw new IllegalArgumentException("Khuyến mãi chưa bắt đầu");
            }
            if (promo.getEndAt() != null && now.toLocalDate().isAfter(promo.getEndAt())) {
                throw new IllegalArgumentException("Khuyến mãi đã hết hạn");
            }
        }

        // Calculate discount
        BigDecimal discountPercentBD = BigDecimal.ZERO;
        if (promo != null && promo.getPercent() != null) {
            discountPercentBD = promo.getPercent();

            // Validate discount percentage
            if (discountPercentBD.compareTo(BigDecimal.ZERO) < 0) {
                discountPercentBD = BigDecimal.ZERO;
            }
            if (discountPercentBD.compareTo(BigDecimal.valueOf(100)) > 0) {
                discountPercentBD = BigDecimal.valueOf(100);
            }
        }

        BigDecimal discountAmount = totalBefore
                .multiply(discountPercentBD)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        BigDecimal totalAfter = totalBefore.subtract(discountAmount);

        // Ensure totalAfter is not negative
        if (totalAfter.compareTo(BigDecimal.ZERO) < 0) {
            totalAfter = BigDecimal.ZERO;
            discountAmount = totalBefore; // Adjust discount to match
        }

        Payment payment = new Payment();
        payment.setCustomer(customer);
        payment.setPromotion(promo);
        payment.setMethod(form.getMethod());
        payment.setPaidAt(form.getPaidAt() != null ? form.getPaidAt() : LocalDateTime.now());
        // Don't set bookingCode - let @PrePersist generate unique payment code
        payment.setBookingId(form.getBookingId());
        payment.setTotalBefore(totalBefore);
        payment.setDiscountPercent(discountPercentBD.setScale(0, RoundingMode.HALF_UP).intValue());
        payment.setDiscountAmount(discountAmount);
        payment.setTotalAfter(totalAfter);

        for (PaymentItem item : items) {
            payment.addItem(item);
        }

        return paymentRepository.save(payment);
    }

    // ================== Helper ==================

    private BigDecimal nvl(BigDecimal v) {
        return v == null ? BigDecimal.ZERO : v;
    }

    private BigDecimal quantityToBigDecimal(Object qtyObj) {
        if (qtyObj == null) return BigDecimal.ONE;
        if (qtyObj instanceof BigDecimal) return (BigDecimal) qtyObj;
        if (qtyObj instanceof Integer) return BigDecimal.valueOf((Integer) qtyObj);
        if (qtyObj instanceof Long) return BigDecimal.valueOf((Long) qtyObj);
        return new BigDecimal(qtyObj.toString());
    }
}