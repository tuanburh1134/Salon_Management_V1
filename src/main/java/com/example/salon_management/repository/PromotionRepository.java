package com.example.salon_management.repository;

import com.example.salon_management.entity.Promotion;
import com.example.salon_management.entity.Promotion.PromotionStatus;
import com.example.salon_management.entity.Promotion.PromotionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {

    // ✅ Tìm theo trạng thái
    List<Promotion> findByStatus(PromotionStatus status);

    // ✅ Tìm khuyến mãi đang hoạt động
    @Query("""
        SELECT p FROM Promotion p
        WHERE p.status = 'ACTIVE'
          AND p.startAt <= :currentDate
          AND p.endAt >= :currentDate
    """)
    List<Promotion> findActivePromotions(@Param("currentDate") LocalDate currentDate);

    // ✅ Tìm khuyến mãi sắp hết hạn
    @Query("""
        SELECT p FROM Promotion p
        WHERE p.status = 'ACTIVE'
          AND p.endAt BETWEEN :currentDate AND :futureDate
    """)
    List<Promotion> findPromotionsExpiringSoon(@Param("currentDate") LocalDate currentDate,
                                               @Param("futureDate") LocalDate futureDate);

    // ✅ Sắp xếp
    List<Promotion> findAllByOrderByNameAsc();
    List<Promotion> findAllByOrderByStartAtAsc();
    List<Promotion> findAllByOrderByEndAtAsc();

    // ✅ Phân trang theo trạng thái
    Page<Promotion> findByStatus(PromotionStatus status, Pageable pageable);

    // ✅ Kiểm tra trùng tên
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Integer id);

    // ✅ BỔ SUNG: Kiểm tra trùng mã khuyến mãi
    boolean existsByCode(String code);
    boolean existsByCodeAndIdNot(String code, Integer id);

    // ✅ Lấy ID lớn nhất
    @Query("SELECT COALESCE(MAX(p.id), 0) FROM Promotion p")
    Integer findMaxId();

    // ✅ Tìm kiếm theo từ khóa
    @Query("""
        SELECT p FROM Promotion p
        WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(p.code) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    List<Promotion> searchPromotions(@Param("keyword") String keyword);

    @Query("""
        SELECT p FROM Promotion p
        WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(p.code) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    Page<Promotion> searchPromotionsWithPagination(@Param("keyword") String keyword, Pageable pageable);

    // ✅ Tìm kiếm nâng cao
    @Query("""
        SELECT p FROM Promotion p
        WHERE
            (:keyword IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.code) LIKE LOWER(CONCAT('%', :keyword, '%')))
            AND (:status IS NULL OR p.status = :status)
            AND (:type IS NULL OR p.type = :type)
            AND (:startDate IS NULL OR p.startAt >= :startDate)
            AND (:endDate IS NULL OR p.endAt <= :endDate)
    """)
    Page<Promotion> searchAndFilter(
            @Param("keyword") String keyword,
            @Param("status") PromotionStatus status,
            @Param("type") PromotionType type,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable
    );
}
