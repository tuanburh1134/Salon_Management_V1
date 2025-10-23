package com.example.salon_management.service;


import com.example.salon_management.dto.ProductUsageForm;
import com.example.salon_management.entity.ProductUsage;
import org.springframework.data.domain.*;
public interface ProductUsageService {
    Page<ProductUsage> search(String keyword, Pageable pageable);
    ProductUsage create(ProductUsageForm form);
    ProductUsage update(Long id, ProductUsageForm form);
    void delete(Long id);
    ProductUsage get(Long id);
}