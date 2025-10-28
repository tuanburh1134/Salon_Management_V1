package com.example.salon_management.dto;

import lombok.*;
import org.springframework.data.domain.Sort;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class EmployeeSearchRequest {
    @Builder.Default
    private String keyword = "";
    @Builder.Default
    private String position = "";
    @Builder.Default
    private String shift = "";
    @Builder.Default
    private String specialty = "";
    @Builder.Default
    private int page = 0;
    @Builder.Default
    private int size = 10;
    @Builder.Default
    private String sortBy = "id"; // Cột sắp xếp mặc định
    @Builder.Default
    private String sortDir = "desc"; // Hướng sắp xếp mặc định
}