package com.example.salon_management.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import jakarta.validation.constraints.*;

@Data
// com.example.salon_management.dto.ProductUsageForm


public class ProductUsageForm {

    @NotBlank(message = "Mã dịch vụ không được để trống")
    private String serviceCode;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String productName;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải >= 1")
    private Integer quantityUsed;

    @NotNull(message = "Giá không được để trống")
    @DecimalMin(value = "0", inclusive = true, message = "Giá phải >= 0")
    private java.math.BigDecimal price;

    // getter/setter
}


