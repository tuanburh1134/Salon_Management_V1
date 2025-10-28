package com.example.salon_management.dto;



import com.example.salon_management.validation.FutureDateTime;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingForm {
    private Long id;

    private String bookingCode; // Mã đặt lịch (read-only)

    @NotNull(message = "Vui lòng chọn khách hàng")
    private Long customerId;

    @NotNull(message = "Vui lòng chọn dịch vụ")
    private Long serviceId;

    @NotNull(message = "Vui lòng chọn thời gian đặt lịch")
    @FutureDateTime(message = "Thời gian đặt lịch phải trước 1 tiếng", hours = 1)
    private LocalDateTime bookingDateTime;

    @NotNull(message = "Vui lòng chọn nhân viên")
    private Long employeeId;

    @NotBlank(message = "Trạng thái không được để trống")
    private String status = "Chờ xử lý";

    private String notes;

    // Helper methods for form binding
    public static BookingForm from(com.example.salon_management.entity.Booking booking) {
        BookingForm form = new BookingForm();
        form.setId(booking.getId());
        form.setBookingCode(booking.getBookingCode());
        form.setCustomerId(booking.getCustomer().getId());
        form.setServiceId(booking.getService().getId());
        form.setBookingDateTime(booking.getBookingDateTime());
        form.setEmployeeId(booking.getEmployee().getId());
        form.setStatus(booking.getStatus());
        form.setNotes(booking.getNotes());
        return form;
    }
}
