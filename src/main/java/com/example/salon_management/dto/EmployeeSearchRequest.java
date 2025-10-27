package com.example.salon_management.dto;

import lombok.*;
import org.springframework.data.domain.Sort;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class EmployeeSearchRequest {
    private String keyword = "";
    private String position = "";
    private String shift = "";
    private String specialty = "";
    private int page = 0;
    private int size = 10;

    private String sortBy = "id"; // Cột sắp xếp mặc định
    private String sortDir = "desc"; // Hướng sắp xếp mặc định
}