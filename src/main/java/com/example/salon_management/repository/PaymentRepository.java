package com.example.salon_management.repository;

import com.example.salon_management.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /**
     * FIX 6: Trả về tất cả các Payment, sắp xếp theo ngày thanh toán giảm dần (mới nhất trước).
     * Dùng cho trang danh sách thanh toán tổng quát.
     */
    List<Payment> findAllByOrderByPaidAtDesc();

    /**
     * FIX 1: Trả về các Payment trong khoảng thời gian chỉ định,
     * sắp xếp theo ngày thanh toán giảm dần.
     * Dùng cho báo cáo theo ngày/tháng/năm.
     */
    List<Payment> findByPaidAtBetweenOrderByPaidAtDesc(LocalDateTime start, LocalDateTime end);

    /**
     * Kiểm tra xem đã có thanh toán nào cho booking này chưa
     */
    boolean existsByBookingId(Long bookingId);
}
