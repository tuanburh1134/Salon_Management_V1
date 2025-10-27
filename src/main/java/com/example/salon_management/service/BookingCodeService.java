package com.example.salon_management.service;

import com.example.salon_management.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookingCodeService {

    private final BookingRepository bookingRepository;

    /**
     * Generates the next available booking code in format DL0001-DL9999
     * @return the next booking code
     */
    @Transactional(readOnly = true)
    public String generateNextBookingCode() {
        // Find the highest existing booking code
        String maxCode = bookingRepository.findMaxBookingCode();
        
        if (maxCode == null) {
            // No bookings exist yet, start with DL0001
            return "DL0001";
        }
        
        // Extract the numeric part
        String numericPart = maxCode.substring(2); // Remove "DL" prefix
        int nextNumber = Integer.parseInt(numericPart) + 1;
        
        // Check if we've reached the limit
        if (nextNumber > 9999) {
            throw new RuntimeException("Đã đạt giới hạn số lượng mã đặt lịch (DL9999)");
        }
        
        // Format with leading zeros
        return String.format("DL%04d", nextNumber);
    }
}
