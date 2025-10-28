package com.example.salon_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers",
        indexes = {
                @Index(name = "idx_customers_phone", columnList = "phone"),
                @Index(name = "idx_customers_email", columnList = "email")
        })
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ======= THÔNG TIN KHÁCH HÀNG =======
    @NotBlank(message = "Tên không được để trống")
    @Size(max = 120, message = "Tên tối đa 120 ký tự")
    @Column(nullable = false, length = 120)
    private String name;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Size(max = 15, message = "SĐT tối đa 15 ký tự")
    @Pattern(
            regexp = "^(?:\\+84\\d{9,10}|0\\d{9})$",
            message = "Số điện thoại không hợp lệ (vd: 0xxxxxxxxx hoặc +84xxxxxxxxx)"
    )
    @Column(nullable = false, length = 15/*, unique = true*/) // bật unique nếu bạn muốn không trùng
    private String phone;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Size(max = 120, message = "Email tối đa 120 ký tự")
    @Column(nullable = false, length = 120/*, unique = true*/) // có thể unique nếu cần
    private String email;

    @NotBlank(message = "Loại thành viên không được để trống")
    @Size(max = 30, message = "Loại thành viên tối đa 30 ký tự")
    @Pattern(
            regexp = "^(?i)(Thường|VIP|Vàng|Bạch kim)$",
            message = "Loại thành viên chỉ chấp nhận: Thường, VIP, Vàng, Bạch kim"
    )
    @Column(nullable = false, length = 30)
    private String memberType; // Thường, VIP, Vàng, Bạch kim

    @NotNull(message = "Điểm tích lũy không được để trống")
    @Min(value = 0, message = "Điểm tích lũy phải >= 0")
    @Column(nullable = false)
    private Integer point = 0;

    // ======= TRẠNG THÁI =======
    @Column(nullable = false)
    private Boolean deleted = false;

    // ======= THỜI GIAN =======
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (deleted == null) deleted = false;
        if (point == null) point = 0;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ======= QUAN HỆ VỚI ĐẶT LỊCH =======
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Booking> bookings = new ArrayList<>();
}
