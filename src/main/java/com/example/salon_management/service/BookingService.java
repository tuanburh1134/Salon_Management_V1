package com.example.salon_management.service;

import com.example.salon_management.dto.BookingForm;
import com.example.salon_management.dto.BookingSearchRequest;
import com.example.salon_management.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookingService {
    Page<Booking> search(String keyword, String status, Pageable pageable);
    Optional<Booking> findById(Long id);
    Booking create(BookingForm form);
    Booking update(Long id, BookingForm form);
    void delete(Long id);
}


