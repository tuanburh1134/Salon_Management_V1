package com.example.salon_management.dto;


import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingForm {
    private Long id;

    @NotNull(message = "Vui lòng chọn khách hàng")
    private Long customerId;

    @NotNull(message = "Vui lòng chọn dịch vụ")
    private Long serviceId;



    @NotNull(message = "Vui lòng chọn nhân viên")
    private Long employeeId;

    @NotBlank(message = "Trạng thái không được để trống")
    private String status = "Chờ xử lý";

    private String notes;

    // Helper methods for form binding
    public static BookingForm from(com.example.salon_management.entity.Booking booking) {
        BookingForm form = new BookingForm();
        form.setId(booking.getId());
        form.setCustomerId(booking.getCustomer().getId());
        form.setServiceId(booking.getService().getId());
        form.setEmployeeId(booking.getEmployee().getId());
        form.setStatus(booking.getStatus());
        form.setNotes(booking.getNotes());
        return form;
    }
}

