package com.example.salon_management.service;

import com.example.salon_management.entity.Payment;
import com.example.salon_management.dto.PaymentForm;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentService {
    Payment create(PaymentForm form);

    // Dùng cho trang danh sách
    List<Payment> findAllDesc();

    List<Payment> findByPaidAtBetweenDesc(LocalDateTime start, LocalDateTime end);
}
