package com.example.salon_management.service.impl;

import com.example.salon_management.entity.Promotion;
import com.example.salon_management.entity.Promotion.PromotionStatus;
import com.example.salon_management.entity.Promotion.PromotionType;
import com.example.salon_management.repository.PromotionRepository;
import com.example.salon_management.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    // ============================
    // TRUY VẤN DANH SÁCH
    // ============================
    @Override
    public List<Promotion> findAll() {
        return promotionRepository.findAll();
    }

    @Override
    public Optional<Promotion> findById(Integer id) {
        if (id == null) return Optional.empty();
        return promotionRepository.findById(id);
    }

    @Override
    public List<Promotion> findByStatus(PromotionStatus status) {
        return promotionRepository.findByStatus(status);
    }

    @Override
    public List<Promotion> findActivePromotions(LocalDate currentDate) {
        return promotionRepository.findActivePromotions(currentDate);
    }

    @Override
    public List<Promotion> findPromotionsExpiringSoon(LocalDate currentDate, LocalDate futureDate) {
        return promotionRepository.findPromotionsExpiringSoon(currentDate, futureDate);
    }

    @Override
    public List<Promotion> findAllOrderByName() {
        return promotionRepository.findAllByOrderByNameAsc();
    }

    @Override
    public List<Promotion> findAllOrderByStartAt() {
        return promotionRepository.findAllByOrderByStartAtAsc();
    }

    @Override
    public List<Promotion> findAllOrderByEndAt() {
        return promotionRepository.findAllByOrderByEndAtAsc();
    }

    @Override
    public Page<Promotion> findAllWithPagination(Pageable pageable) {
        return promotionRepository.findAll(pageable);
    }

    @Override
    public Page<Promotion> findByStatusWithPagination(PromotionStatus status, Pageable pageable) {
        return promotionRepository.findByStatus(status, pageable);
    }

    // ============================
    // TẠO MỚI
    // ============================
    @Override
    public Promotion save(Promotion promotion) {
        // Validate không trùng code và name (đã check ở controller nhưng đảm bảo double check)
        if (promotion.getCode() != null && promotionRepository.existsByCode(promotion.getCode())) {
            throw new IllegalArgumentException("Mã khuyến mãi đã tồn tại. Vui lòng chọn mã khác.");
        }
        if (promotion.getName() != null && promotionRepository.existsByName(promotion.getName())) {
            throw new IllegalArgumentException("Tên khuyến mãi đã tồn tại. Vui lòng chọn tên khác.");
        }

        // Validate ngày
        if (promotion.getStartAt() != null && promotion.getEndAt() != null) {
            if (promotion.getStartAt().isAfter(promotion.getEndAt())) {
                throw new IllegalArgumentException("Ngày kết thúc phải sau hoặc bằng ngày bắt đầu.");
            }
        }

        // Luôn gọi refreshStatus() - nó sẽ tự động kiểm tra và update phù hợp
        promotion.refreshStatus();

        return promotionRepository.save(promotion);
    }

    // ============================
    // CẬP NHẬT
    // ============================
    @Override
    public Promotion update(Integer id, Promotion promotionDetails) {
        Promotion existing = promotionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Khuyến mãi không tồn tại."));

        if (existing.getStatus() == PromotionStatus.EXPIRED) {
            throw new IllegalStateException("Không thể sửa/cập nhật khuyến mãi đã hết hạn.");
        }

        // Validate không trùng code và name
        if (promotionDetails.getName() != null && promotionRepository.existsByNameAndIdNot(promotionDetails.getName(), id)) {
            throw new IllegalArgumentException("Tên khuyến mãi đã tồn tại.");
        }
        if (promotionDetails.getCode() != null && promotionRepository.existsByCodeAndIdNot(promotionDetails.getCode(), id)) {
            throw new IllegalArgumentException("Mã khuyến mãi đã tồn tại. Vui lòng chọn mã khác.");
        }

        // Validate ngày
        if (promotionDetails.getStartAt() != null && promotionDetails.getEndAt() != null) {
            if (promotionDetails.getStartAt().isAfter(promotionDetails.getEndAt())) {
                throw new IllegalArgumentException("Ngày kết thúc phải sau hoặc bằng ngày bắt đầu.");
            }
        }

        // Không cho phép thay đổi ngày bắt đầu nếu đang ACTIVE hoặc UPCOMING
        if ((existing.getStatus() == PromotionStatus.ACTIVE || existing.getStatus() == PromotionStatus.UPCOMING)
                && !existing.getStartAt().isEqual(promotionDetails.getStartAt())) {
            throw new IllegalStateException("Không thể thay đổi Ngày bắt đầu của khuyến mãi đang Hoạt động hoặc Sắp diễn ra.");
        }

        // Update fields
        existing.setCode(promotionDetails.getCode());
        existing.setName(promotionDetails.getName());
        existing.setPercent(promotionDetails.getPercent());
        existing.setStartAt(promotionDetails.getStartAt());
        existing.setEndAt(promotionDetails.getEndAt());
        existing.setDescription(promotionDetails.getDescription());
        existing.setType(promotionDetails.getType());

        if (promotionDetails.getStatus() != null) {
            existing.setStatus(promotionDetails.getStatus());
        }

        // Luôn gọi refreshStatus() - nó sẽ tự động kiểm tra và update phù hợp
        existing.refreshStatus();

        return promotionRepository.save(existing);
    }

    // ============================
    // XÓA
    // ============================
    @Override
    public boolean deleteById(Integer id) {
        if (id == null) return false;

        Promotion promotion = promotionRepository.findById(id).orElse(null);
        if (promotion == null) return false;

        if (promotion.getStatus() == PromotionStatus.EXPIRED) {
            throw new IllegalStateException("Không thể xóa khuyến mãi đã hết hạn để lưu lịch sử.");
        }

        promotionRepository.deleteById(id);
        return true;
    }

    // ============================
    // KIỂM TRA TRÙNG LẶP
    // ============================
    @Override
    public boolean existsByName(String name) {
        return promotionRepository.existsByName(name);
    }

    @Override
    public boolean existsByNameAndIdNot(String name, Integer id) {
        return promotionRepository.existsByNameAndIdNot(name, id);
    }

    @Override
    public boolean existsByCode(String code) {
        return promotionRepository.existsByCode(code);
    }

    @Override
    public boolean existsByCodeAndIdNot(String code, Integer id) {
        return promotionRepository.existsByCodeAndIdNot(code, id);
    }

    // ============================
    // TỰ ĐỘNG SINH MÃ KHUYẾN MÃI
    // ============================
    @Override
    public String generateNextCode() {
        Integer maxId = promotionRepository.findMaxId();
        int nextId = (maxId == null || maxId == 0) ? 1 : maxId + 1;

        // Format: 1, 2, 3, ...
        return String.valueOf(nextId);
    }

    // ============================
    // TÌM KIẾM
    // ============================
    @Override
    public List<Promotion> searchPromotions(String keyword) {
        return promotionRepository.searchPromotions(keyword);
    }

    @Override
    public Page<Promotion> searchPromotionsWithPagination(String keyword, Pageable pageable) {
        return promotionRepository.searchPromotionsWithPagination(keyword, pageable);
    }

    @Override
    public Page<Promotion> searchAndFilter(String keyword, PromotionStatus status, PromotionType type,
                                           LocalDate startDate, LocalDate endDate, Pageable pageable) {
        String searchKeyword = (keyword != null && !keyword.trim().isEmpty()) ? keyword.trim() : null;
        Page<Promotion> result = promotionRepository.searchAndFilter(searchKeyword, status, type, startDate, endDate, pageable);

        // Note: Auto-refresh và save được loại bỏ để tránh side effects trong query method
        // Nên implement scheduler job hoặc background task để refresh status
        // Hoặc handle null status ở frontend level

        return result;
    }
}
