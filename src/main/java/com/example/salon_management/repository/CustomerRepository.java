package com.example.salon_management.repository;

import com.example.salon_management.entity.Customer;
import com.example.salon_management.entity.Customer.MemberType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // ================== TÌM KIẾM & PHÂN TRANG ==================

    // Tìm tất cả khách hàng chưa bị xóa
    Page<Customer> findByDeletedFalse(Pageable pageable);

    // Tìm khách hàng theo loại thành viên
    Page<Customer> findByDeletedFalseAndMemberType(MemberType type, Pageable pageable);

    // Tìm kiếm theo từ khóa (Tên / SĐT / Email)
    @Query("""
           SELECT c FROM Customer c
           WHERE c.deleted = false
           AND (LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
             OR LOWER(c.phone) LIKE LOWER(CONCAT('%', :keyword, '%'))
             OR LOWER(c.email) LIKE LOWER(CONCAT('%', :keyword, '%')))
           """)
    Page<Customer> search(String keyword, Pageable pageable);

    // Tìm kiếm có lọc loại thành viên
    @Query("""
           SELECT c FROM Customer c
           WHERE c.deleted = false
           AND c.memberType = :type
           AND (LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
             OR LOWER(c.phone) LIKE LOWER(CONCAT('%', :keyword, '%'))
             OR LOWER(c.email) LIKE LOWER(CONCAT('%', :keyword, '%')))
           """)
    Page<Customer> searchByKeywordAndMemberType(String keyword, MemberType type, Pageable pageable);


    // ================== TÌM KIẾM NÂNG CAO ==================

    List<Customer> findByDeletedFalseAndNameContainingIgnoreCase(String name);
    List<Customer> findByDeletedFalseAndPhoneContaining(String phone);
    List<Customer> findByDeletedFalseAndMemberType(MemberType type);
    List<Customer> findByDeletedFalse();
    List<Customer> findByDeletedTrue();


    // ================== SẮP XẾP ==================
    List<Customer> findAllByDeletedFalseOrderByNameAsc();
    List<Customer> findAllByDeletedFalseOrderByNameDesc();
    List<Customer> findAllByDeletedFalseOrderByPointAsc();
    List<Customer> findAllByDeletedFalseOrderByPointDesc();


    // ================== THỐNG KÊ ==================
    long countByDeletedFalse();
    long countByDeletedTrue();
    long countByDeletedFalseAndMemberType(MemberType type);
}
