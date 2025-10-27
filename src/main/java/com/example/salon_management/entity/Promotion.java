package com.example.salon_management.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "promotion")
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_promotion")
    private Integer id;

    @Column(name = "code", length = 50, nullable = false, unique = true)
    private String code;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "percent", precision = 5, scale = 2, nullable = false)
    private BigDecimal percent;

    @Column(name = "start_at", nullable = false)
    private LocalDate startAt;

    @Column(name = "end_at", nullable = false)
    private LocalDate endAt;

    @Column(name = "description", length = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private PromotionStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private PromotionType type;

    // ===== Constructors =====
    public Promotion() {}

    public Promotion(String code, String name, BigDecimal percent, LocalDate startAt,
                     LocalDate endAt, String description, PromotionStatus status, PromotionType type) {
        this.code = code;
        this.name = name;
        this.percent = percent;
        this.startAt = startAt;
        this.endAt = endAt;
        this.description = description;
        this.status = status;
        this.type = type;
    }

    // ===== Getters & Setters =====
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getPercent() { return percent; }
    public void setPercent(BigDecimal percent) { this.percent = percent; }

    public LocalDate getStartAt() { return startAt; }
    public void setStartAt(LocalDate startAt) { this.startAt = startAt; }

    public LocalDate getEndAt() { return endAt; }
    public void setEndAt(LocalDate endAt) { this.endAt = endAt; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public PromotionStatus getStatus() { return status; }
    public void setStatus(PromotionStatus status) { this.status = status; }

    public PromotionType getType() { return type; }
    public void setType(PromotionType type) { this.type = type; }

    // ====== BUSINESS LOGIC ======
    public void refreshStatus() {
        // Bảo vệ null safety
        if (this.startAt == null || this.endAt == null) {
            return; // Không refresh nếu ngày null
        }

        LocalDate today = LocalDate.now();

        // Chỉ auto-update nếu status là ACTIVE hoặc UPCOMING
        // Giữ nguyên INACTIVE và EXPIRED (manual status)
        if (this.status == PromotionStatus.ACTIVE || this.status == PromotionStatus.UPCOMING) {
            if (today.isBefore(startAt)) {
                this.status = PromotionStatus.UPCOMING;
            } else if (today.isAfter(endAt)) {
                this.status = PromotionStatus.EXPIRED;
            } else {
                this.status = PromotionStatus.ACTIVE;
            }
        }
        // Nếu status là null, tính toán lần đầu
        else if (this.status == null) {
            if (today.isBefore(startAt)) {
                this.status = PromotionStatus.UPCOMING;
            } else if (today.isAfter(endAt)) {
                this.status = PromotionStatus.EXPIRED;
            } else {
                this.status = PromotionStatus.ACTIVE;
            }
        }
    }

    // ===== Enums =====
    public enum PromotionStatus {
        ACTIVE,     // Đang hoạt động
        INACTIVE,   // Ngừng hoạt động thủ công
        EXPIRED,    // Đã hết hạn
        UPCOMING    // Sắp diễn ra
    }

    public enum PromotionType {
        ALL_CUSTOMERS("Tất cả khách hàng"),
        NEW_CUSTOMER("Khách hàng mới"),
        LOYAL_CUSTOMER("Khách hàng thân thiết"),
        SPECIAL_CUSTOMER("Khách hàng đặc biệt");

        private final String vietnameseName;

        PromotionType(String vietnameseName) {
            this.vietnameseName = vietnameseName;
        }

        public String getVietnameseName() {
            return vietnameseName;
        }
    }
}
