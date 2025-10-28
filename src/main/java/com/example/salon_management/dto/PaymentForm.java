package com.example.salon_management.dto;

import com.example.salon_management.entity.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO nhận dữ liệu từ form tạo thanh toán.
 * Giữ nguyên các trường cũ để tương thích với PaymentServiceImpl.
 * Thêm bookingId và bookingCode để hỗ trợ Thymeleaf binding và lưu mã booking.
 */
public class PaymentForm {

    @NotNull(message = "Vui lòng chọn đặt lịch")
    private Long bookingId;         // NEW: để Thymeleaf bind *{bookingId}
    private String bookingCode;     // FIX 5: Thêm trường để lưu mã booking
    private Long customerId;
    private Long promotionId;       // có thể null nếu không áp dụng khuyến mãi
    @NotNull(message = "Vui lòng chọn phương thức thanh toán")
    private PaymentMethod method;   // Enum: CASH, BANK_TRANSFER, ...
    @PastOrPresent(message = "Ngày thanh toán không được ở tương lai")
    private LocalDateTime paidAt;   // có thể null (auto set khi lưu)
    private List<Long> serviceIds;  // danh sách id dịch vụ được tick chọn
    private List<Long> productIds;  // danh sách id productUsage được tick chọn

    // ===== Getters / Setters =====

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public List<Long> getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(List<Long> serviceIds) {
        this.serviceIds = serviceIds;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}
