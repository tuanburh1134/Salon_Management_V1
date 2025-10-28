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
    @Builder.Default
    private String keyword = "";
    @Builder.Default
    private String status = "";
    @Builder.Default
    private String sortBy = "bookingDateTime";
    @Builder.Default
    private String dir = "desc";
    @Builder.Default
    private int page = 0;
    @Builder.Default
    private int size = 10;
}


