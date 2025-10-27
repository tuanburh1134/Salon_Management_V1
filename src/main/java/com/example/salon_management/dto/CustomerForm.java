package com.example.salon_management.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerForm {

    private Long id; // dùng khi sửa

    // ======= THÔNG TIN CƠ BẢN =======
    @Size(max = 10, message = "STT tối đa 10 ký tự")
    private String stt; // KH001, KH002,...

    @NotBlank(message = "Tên không được để trống")
    @Size(max = 120, message = "Tên tối đa 120 ký tự")
    private String name;

    @Pattern(regexp = "^[0-9]{10}$", message = "Số điện thoại phải gồm đúng 10 chữ số")
    private String phone;

    @Email(message = "Email không hợp lệ")
    @Size(max = 120, message = "Email tối đa 120 ký tự")
    private String email;

    // ======= LOẠI THÀNH VIÊN =======
    @NotBlank(message = "Loại thành viên không được để trống")
    private String memberType = "MOI"; // mặc định là Mới

    // ======= ĐIỂM TÍCH LŨY =======
    @NotNull(message = "Điểm tích lũy không được để trống")
    @Min(value = 0, message = "Điểm tích lũy phải >= 0")
    private Integer point = 0;

    // ======= TRẠNG THÁI =======
    private Boolean deleted = false;
}
