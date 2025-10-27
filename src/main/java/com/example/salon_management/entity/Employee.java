package com.example.salon_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Họ tên không được để trống")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Vui lòng chọn chức vụ")
    private String position;

    @NotBlank(message = "Vui lòng chọn chuyên môn")
    private String specialty;

    @NotBlank(message = "Vui lòng chọn ca làm việc")
    private String shift;

    @NotNull(message = "Lương không được để trống")
    @Min(value = 0, message = "Lương phải lớn hơn hoặc bằng 0")
    private Long salary ;

    @Column(name = "photo_path", length = 255)
    private String photoPath;

    @NotNull(message = "Ngày sinh không được để trống")
    @Past(message = "Ngày sinh phải là một ngày trong quá khứ")
    @DateTimeFormat(pattern = "dd/MM/yyyy") // Định dạng ngày tháng client gửi lên
    @Column(name = "date_of_birth") // Tên cột trong database
    private LocalDate dateOfBirth;

    @Email(message = "Email không hợp lệ")
    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Pattern(regexp = "^\\d{10,11}$", message = "Số điện thoại phải có 10-11 chữ số")
    @Column(name = "phone", length = 15, unique = true)
    private String phone;

    @Size(max = 100, message = "Quê quán không được vượt quá 100 ký tự")
    @Column(name = "hometown", length = 100)
    private String hometown;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Boolean deleted = false;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}