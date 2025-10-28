package com.example.salon_management.dto;

import com.example.salon_management.entity.Customer.MemberType;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerForm {

    @NotBlank(message = "Tên không được để trống")
    @Size(max = 120, message = "Tên tối đa 120 ký tự")
    private String name;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Size(max = 15, message = "SĐT tối đa 15 ký tự")
    @Pattern(
            regexp = "^(?:\\+84\\d{9,10}|0\\d{9})$",
            message = "Số điện thoại không hợp lệ "
    )
    private String phone;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Size(max = 120)
    private String email;

    @NotBlank(message = "Loại thành viên không được để trống")
    @Pattern(
            regexp = "^(?i)(Thường|VIP|Vàng|Bạch kim)$",
            message = "Loại thành viên chỉ chấp nhận: Thường, VIP, Vàng, Bạch kim"
    )
    @Size(max = 30, message = "Loại thành viên tối đa 30 ký tự")
    private String memberType;

    @NotNull(message = "Điểm tích lũy không được để trống")
    @Min(value = 0, message = "Điểm tích lũy phải >= 0")
    private Integer point;
}
