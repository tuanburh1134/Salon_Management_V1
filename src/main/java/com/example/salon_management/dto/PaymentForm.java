package com.example.salon_management.dto;

import com.example.salon_management.entity.PaymentMethod;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO nhận dữ liệu từ form tạo thanh toán.
 */
public class PaymentForm {

    private Long customerId;
    private Long promotionId;       // có thể null nếu không áp dụng khuyến mãi
    private PaymentMethod method;   // Enum: CASH, BANK_TRANSFER, ...
    private LocalDateTime paidAt;   // có thể null (auto set khi lưu)
    private List<Long> serviceIds;  // danh sách id dịch vụ được tick chọn
    private List<Long> productIds;  // danh sách id sản phẩm được tick chọn

    public PaymentForm() {}

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
