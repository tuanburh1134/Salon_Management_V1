package com.example.salon_management.repository;

import com.example.salon_management.entity.Customer;
import com.example.salon_management.entity.Customer.MemberType;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // =====================================================
    // 🧭 1️⃣ TÌM KIẾM TỔNG HỢP (theo keyword)
    // =====================================================
    @Query("""
           SELECT c FROM Customer c
           WHERE c.deleted = false AND
                 (:kw IS NULL OR :kw = '' OR
                 LOWER(c.name) LIKE LOWER(CONCAT('%', :kw, '%')) OR
                 LOWER(c.phone) LIKE LOWER(CONCAT('%', :kw, '%')) OR
                 LOWER(c.email) LIKE LOWER(CONCAT('%', :kw, '%')))
           """)
    Page<Customer> search(@Param("kw") String keyword, Pageable pageable);


    // =====================================================
    // 🔍 2️⃣ TÌM KIẾM RIÊNG THEO TRƯỜNG CỤ THỂ
    // =====================================================
    List<Customer> findByDeletedFalseAndNameContainingIgnoreCase(String name);

    List<Customer> findByDeletedFalseAndPhoneContaining(String phone);

    List<Customer> findByDeletedFalseAndMemberType(MemberType memberType);


    // =====================================================
    // 🧩 3️⃣ SẮP XẾP (TÊN, ĐIỂM TÍCH LŨY)
    // =====================================================
    @Query("SELECT c FROM Customer c WHERE c.deleted = false ORDER BY LOWER(c.name) ASC")
    List<Customer> findAllOrderByNameAsc();

    @Query("SELECT c FROM Customer c WHERE c.deleted = false ORDER BY LOWER(c.name) DESC")
    List<Customer> findAllOrderByNameDesc();

    @Query("SELECT c FROM Customer c WHERE c.deleted = false ORDER BY c.point DESC")
    List<Customer> findAllOrderByPointDesc();

    @Query("SELECT c FROM Customer c WHERE c.deleted = false ORDER BY c.point ASC")
    List<Customer> findAllOrderByPointAsc();


    // =====================================================
    // 🧾 4️⃣ DANH SÁCH THEO TRẠNG THÁI / LOẠI THÀNH VIÊN
    // =====================================================
    List<Customer> findByDeletedFalse();

    List<Customer> findByDeletedFalseAndMemberTypeIn(List<MemberType> memberTypes);
    Page<Customer> findByDeletedFalse(Pageable pageable);
    @Query("SELECT c FROM Customer c WHERE c.deleted = true")
    List<Customer> findAllDeleted();


    // =====================================================
    // 🧰 5️⃣ XÓA MỀM (SOFT DELETE)
    // =====================================================
    @Modifying
    @Query("UPDATE Customer c SET c.deleted = true WHERE c.id = :id")
    void softDelete(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Customer c SET c.deleted = false WHERE c.id = :id")
    void restore(@Param("id") Long id);


    // =====================================================
    // 🧮 6️⃣ CẬP NHẬT ĐIỂM TÍCH LŨY
    // =====================================================
    @Modifying
    @Query("UPDATE Customer c SET c.point = c.point + :points WHERE c.id = :id AND c.deleted = false")
    void addPoints(@Param("id") Long id, @Param("points") int points);

    @Modifying
    @Query("UPDATE Customer c SET c.point = c.point - :points WHERE c.id = :id AND c.point >= :points AND c.deleted = false")
    void subtractPoints(@Param("id") Long id, @Param("points") int points);


    // =====================================================
    // 🧮 7️⃣ BỔ SUNG CHO FILTER (PHÂN TRANG + MEMBER TYPE)
    // =====================================================

    // 🔹 Lọc phân trang theo loại thành viên (không xóa)
    Page<Customer> findByDeletedFalseAndMemberType(MemberType memberType, Pageable pageable);

    // 🔹 Kết hợp từ khóa + loại thành viên
    @Query("""
           SELECT c FROM Customer c
           WHERE c.deleted = false
             AND c.memberType = :memberType
             AND (
                LOWER(c.name) LIKE LOWER(CONCAT('%', :kw, '%'))
                OR LOWER(c.phone) LIKE LOWER(CONCAT('%', :kw, '%'))
                OR LOWER(c.email) LIKE LOWER(CONCAT('%', :kw, '%'))
             )
           """)
    Page<Customer> searchByKeywordAndMemberType(@Param("kw") String keyword,
                                                @Param("memberType") MemberType memberType,
                                                Pageable pageable);
}
