package com.example.salon_management.dto;


import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingSearchRequest {
    private String keyword = "";
    private String status = "";
    private String sortBy = "bookingDateTime";
    private String dir = "desc";
    private int page = 0;
    private int size = 10;
}


