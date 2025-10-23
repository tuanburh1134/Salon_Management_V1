package com.example.salon_management.service.impl;
import com.example.salon_management.dto.ProductUsageForm;
import com.example.salon_management.entity.ProductUsage;
import com.example.salon_management.repository.ProductUsageRepository;
import com.example.salon_management.service.ProductUsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
@Service
@RequiredArgsConstructor
@Transactional
public class ProductUsageServiceImpl implements ProductUsageService {
    private final ProductUsageRepository repo;
    @Override @Transactional(readOnly = true)
    public Page<ProductUsage> search(String keyword, Pageable pageable) {
        return repo.search(keyword, pageable);
    }
    @Override
    public ProductUsage create(ProductUsageForm f) {
        ProductUsage p = ProductUsage.builder()
                .serviceCode(f.getServiceCode().trim())
                .productName(f.getProductName().trim())
                .quantityUsed(f.getQuantityUsed())
                .price(f.getPrice() == null ? BigDecimal.ZERO : f.getPrice())
                .build();
        return repo.save(p);
    }
    @Override
    public ProductUsage update(Long id, ProductUsageForm f) {
        ProductUsage p = repo.findById(id).orElseThrow();
        p.setServiceCode(f.getServiceCode().trim());
        p.setProductName(f.getProductName().trim());
        p.setQuantityUsed(f.getQuantityUsed());
        p.setPrice(f.getPrice());
        return repo.save(p);
    }
    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
    @Override @Transactional(readOnly = true)
    public ProductUsage get(Long id) {
        return repo.findById(id).orElseThrow();
    }
}
