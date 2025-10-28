package com.example.salon_management.dto;

import com.example.salon_management.entity.Customer.MemberType;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerForm {

    @NotBlank(message = "Tên không được để trống")
    private String name;

    @Size(max = 15, message = "Số điện thoại tối đa 15 ký tự")
    private String phone;

    @Email(message = "Email không hợp lệ")
    @Size(max = 120)
    private String email;

    @Builder.Default
    private MemberType memberType = MemberType.MOI;

    @Builder.Default
    @Min(value = 0, message = "Điểm tích lũy không được âm")
    private Integer point = 0;

    @Size(max = 255, message = "Địa chỉ tối đa 255 ký tự")
    private String address;

    @Size(max = 500, message = "Ghi chú tối đa 500 ký tự")
    private String note;

    // ✅ Tên trường trùng với input trong form và tham số controller
    private MultipartFile photoFile;

    // ✅ Dùng để lưu tên file ảnh (sau khi upload thành công)
    private String photo;
}
