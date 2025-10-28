package com.example.salon_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ======= THÔNG TIN KHÁCH HÀNG =======
    @Column(length = 10)
    private String stt; // Số thứ tự hiển thị trong danh sách

    @NotBlank(message = "Tên không được để trống")
    @Column(nullable = false, length = 120)
    private String name;

    @Size(max = 15, message = "Số điện thoại tối đa 15 ký tự")
    @Column(length = 15)
    private String phone;

    @Email(message = "Email không hợp lệ")
    @Size(max = 120)
    @Column(length = 120)
    private String email;


    /**
     * Loại thành viên:
     * - MOI: Khách hàng mới
     * - THAN_QUEN: Khách hàng thân quen
     * - DAC_BIET: Khách hàng đặc biệt
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private MemberType memberType = MemberType.MOI;

    @Min(0)
    private Integer point = 0; // điểm tích lũy

    @Column(length = 255)
    private String address; // 🏠 Địa chỉ khách hàng

    @Column(length = 500)
    private String note; // 📝 Ghi chú

    @Column(length = 255)
    private String photo; // 🖼️ Đường dẫn ảnh đại diện (VD: "uploads/customers/kh001.jpg")
    private String image;
    // ======= TRẠNG THÁI =======
    @Column(nullable = false)
    private Boolean deleted = false;

    // ======= THỜI GIAN =======
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ======= SỰ KIỆN HỆ THỐNG =======
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (deleted == null) deleted = false;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ======= HÀM TIỆN ÍCH =======

    /** Ảnh mặc định nếu không có */
    public String getPhotoPath() {
        return (photo != null && !photo.isEmpty())
                ? "/uploads/customers/" + photo
                : "/images/default-avatar.png";
    }

    public void softDelete() {
        this.deleted = true;
    }

    public void restore() {
        this.deleted = false;
    }

    public void addPoints(int points) {
        if (points > 0) {
            this.point += points;
        }
    }

    public void subtractPoints(int points) {
        if (points > 0 && this.point >= points) {
            this.point -= points;
        }
    }

    public void updateMemberTypeByPoints() {
        if (this.point < 100) {
            this.memberType = MemberType.MOI;
        } else if (this.point < 300) {
            this.memberType = MemberType.THAN_QUEN;
        } else {
            this.memberType = MemberType.DAC_BIET;
        }
    }

    public boolean isActive() {
        return !this.deleted;
    }

    public String shortInfo() {
        return String.format("[%s] %s - %s (%s) [%s]",
                stt != null ? stt : "?", name, phone, email, memberType);
    }

    public void generateStt(Long index) {
        this.stt = String.format("KH%03d", index);
    }

    // ======= ENUM LOẠI THÀNH VIÊN =======
    public enum MemberType {
        MOI("Mới"),
        THAN_QUEN("Thân quen"),
        DAC_BIET("Đặc biệt");

        private final String displayName;

        MemberType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
