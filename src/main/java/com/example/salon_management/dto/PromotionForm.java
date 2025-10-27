package com.example.salon_management.dto;

import com.example.salon_management.entity.Promotion;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class PromotionForm {

    private Integer id; // Đồng bộ với Entity

    @Size(max = 50, message = "Mã khuyến mãi không được vượt quá 50 ký tự")
    private String code; // Tự động sinh khi create, chỉ edit khi update

    @NotBlank(message = "Tên khuyến mãi không được để trống")
    @Size(max = 100, message = "Tên khuyến mãi không được vượt quá 100 ký tự")
    private String name;

    @NotNull(message = "Phần trăm giảm giá không được để trống")
    @DecimalMin(value = "0.01", message = "Phần trăm giảm giá phải lớn hơn 0")
    @DecimalMax(value = "100.00", message = "Phần trăm giảm giá không được vượt quá 100%")
    @Digits(integer = 3, fraction = 2, message = "Phần trăm giảm giá chỉ được tối đa 2 chữ số thập phân.")
    private BigDecimal percent;

    @NotNull(message = "Ngày bắt đầu không được để trống")
    private LocalDate startAt;

    @NotNull(message = "Ngày kết thúc không được để trống")
    private LocalDate endAt;

    @Size(max = 255, message = "Mô tả không được vượt quá 255 ký tự")
    private String description;

    @NotNull(message = "Trạng thái không được để trống")
    private PromotionStatus status;

    @NotNull(message = "Loại khuyến mãi không được để trống")
    private Promotion.PromotionType type;

    public enum PromotionStatus {
        ACTIVE,     // Đang hoạt động
        INACTIVE,   // Ngừng hoạt động thủ công
        EXPIRED,    // Đã hết hạn
        UPCOMING    // Sắp diễn ra
    }

    // ===== Constructors =====
    public PromotionForm() {}

    public PromotionForm(Integer id, String code, String name, BigDecimal percent,
                         LocalDate startAt, LocalDate endAt, String description,
                         PromotionStatus status, Promotion.PromotionType type) {
        this.id = id;
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

    public Promotion.PromotionType getType() { return type; }
    public void setType(Promotion.PromotionType type) { this.type = type; }

    // ===== Chuyển đổi sang entity =====
    public Promotion toEntity() {
        Promotion p = new Promotion();
        p.setId(id);
        p.setCode(code);
        p.setName(name);
        p.setPercent(percent);
        p.setStartAt(startAt);
        p.setEndAt(endAt);
        p.setDescription(description);
        p.setType(type);

        if (status != null) {
            Promotion.PromotionStatus entityStatus = switch (status) {
                case ACTIVE -> Promotion.PromotionStatus.ACTIVE;
                case INACTIVE -> Promotion.PromotionStatus.INACTIVE;
                case EXPIRED -> Promotion.PromotionStatus.EXPIRED;
                case UPCOMING -> Promotion.PromotionStatus.UPCOMING;
            };
            p.setStatus(entityStatus);
        }
        return p;
    }

    // ===== Chuyển đổi từ entity sang Form =====
    public static PromotionForm fromEntity(Promotion entity) {
        PromotionForm form = new PromotionForm();
        form.setId(entity.getId());
        form.setCode(entity.getCode());
        form.setName(entity.getName());
        form.setPercent(entity.getPercent());
        form.setStartAt(entity.getStartAt());
        form.setEndAt(entity.getEndAt());
        form.setDescription(entity.getDescription());
        form.setType(entity.getType());

        if (entity.getStatus() != null) {
            PromotionStatus formStatus = switch (entity.getStatus()) {
                case ACTIVE -> PromotionStatus.ACTIVE;
                case INACTIVE -> PromotionStatus.INACTIVE;
                case EXPIRED -> PromotionStatus.EXPIRED;
                case UPCOMING -> PromotionStatus.UPCOMING;
            };
            form.setStatus(formStatus);
        }
        return form;
    }
}
