package com.example.salon_management.repository;

import com.example.salon_management.entity.PaymentItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentItemRepository extends JpaRepository<PaymentItem, Long> { }
