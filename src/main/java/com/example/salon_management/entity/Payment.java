package com.example.salon_management.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Số thứ tự tự tăng

    // NEW: map cột booking_code NOT NULL
    @Column(name = "booking_code", length = 50, nullable = false, unique = true)
    private String bookingCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    private LocalDateTime paidAt;

    @Column(precision = 18, scale = 2)
    private BigDecimal totalBefore = BigDecimal.ZERO;

    private Integer discountPercent;

    @Column(precision = 18, scale = 2)
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(precision = 18, scale = 2)
    private BigDecimal totalAfter = BigDecimal.ZERO;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentItem> items = new ArrayList<>();

    /* ===== Conveniences ===== */
    public void addItem(PaymentItem item) {
        if (item == null) return;
        items.add(item);
        item.setPayment(this);
    }
    public void clearItems() {
        for (PaymentItem i : items) i.setPayment(null);
        items.clear();
    }

    /* ===== TỰ SINH MÃ & NGÀY GIỜ TRƯỚC KHI LƯU ===== */
    @PrePersist
    public void prePersist() {
        if (this.bookingCode == null || this.bookingCode.isBlank()) {
            // PM-YYYYMMDD-HHmmssSSS (duy nhất theo thời điểm)
            String ts = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmssSSS")
                    .format(LocalDateTime.now());
            this.bookingCode = "PM-" + ts;
        }
        if (this.paidAt == null) {
            this.paidAt = LocalDateTime.now();
        }
    }

    /* ===== Getters/Setters ===== */
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBookingCode() { return bookingCode; }
    public void setBookingCode(String bookingCode) { this.bookingCode = bookingCode; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public Promotion getPromotion() { return promotion; }
    public void setPromotion(Promotion promotion) { this.promotion = promotion; }

    public PaymentMethod getMethod() { return method; }
    public void setMethod(PaymentMethod method) { this.method = method; }

    public LocalDateTime getPaidAt() { return paidAt; }
    public void setPaidAt(LocalDateTime paidAt) { this.paidAt = paidAt; }

    public BigDecimal getTotalBefore() { return totalBefore; }
    public void setTotalBefore(BigDecimal totalBefore) { this.totalBefore = totalBefore; }

    public Integer getDiscountPercent() { return discountPercent; }
    public void setDiscountPercent(Integer discountPercent) { this.discountPercent = discountPercent; }

    public BigDecimal getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(BigDecimal discountAmount) { this.discountAmount = discountAmount; }

    public BigDecimal getTotalAfter() { return totalAfter; }
    public void setTotalAfter(BigDecimal totalAfter) { this.totalAfter = totalAfter; }

    public List<PaymentItem> getItems() { return items; }
    public void setItems(List<PaymentItem> items) { this.items = items; }
}
