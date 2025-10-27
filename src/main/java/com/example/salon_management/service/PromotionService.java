package com.example.salon_management.service;

import com.example.salon_management.entity.Promotion;
import com.example.salon_management.entity.Promotion.PromotionStatus;
import com.example.salon_management.entity.Promotion.PromotionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PromotionService {

    // 🔍 Truy vấn cơ bản
    List<Promotion> findAll();
    Optional<Promotion> findById(Integer id);
    List<Promotion> findByStatus(PromotionStatus status);
    List<Promotion> findActivePromotions(LocalDate currentDate);
    List<Promotion> findPromotionsExpiringSoon(LocalDate currentDate, LocalDate futureDate);

    // 🔢 Sắp xếp
    List<Promotion> findAllOrderByName();
    List<Promotion> findAllOrderByStartAt();
    List<Promotion> findAllOrderByEndAt();

    // 📄 Phân trang
    Page<Promotion> findAllWithPagination(Pageable pageable);
    Page<Promotion> findByStatusWithPagination(PromotionStatus status, Pageable pageable);

    // ➕ Thêm mới
    Promotion save(Promotion promotion);

    // ✏️ Cập nhật
    Promotion update(Integer id, Promotion promotion);

    // ❌ Xóa
    boolean deleteById(Integer id);

    // 🔍 Tìm kiếm đơn giản
    List<Promotion> searchPromotions(String keyword);
    Page<Promotion> searchPromotionsWithPagination(String keyword, Pageable pageable);

    // 🔍🔧 Tìm kiếm và lọc nâng cao
    Page<Promotion> searchAndFilter(String keyword, PromotionStatus status, PromotionType type,
                                    LocalDate startDate, LocalDate endDate, Pageable pageable);

    // ✅ Kiểm tra trùng tên
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Integer id);

    // ✅ Kiểm tra trùng mã khuyến mãi
    boolean existsByCode(String code);
    boolean existsByCodeAndIdNot(String code, Integer id);

    // 🔢 Tự động sinh mã khuyến mãi
    String generateNextCode();
}
