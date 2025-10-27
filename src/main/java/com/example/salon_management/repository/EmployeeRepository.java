package com.example.salon_management.repository;

import com.example.salon_management.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE e.deleted = false " +
            "AND (:keyword IS NULL OR :keyword = '' OR LOWER(e.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:specialty IS NULL OR :specialty = '' OR e.specialty = :specialty) " +
            "AND (:position IS NULL OR :position = '' OR e.position = :position) " +
            "AND (:shift IS NULL OR :shift = '' OR e.shift = :shift)")
    Page<Employee> searchByCriteria(
            @Param("keyword") String keyword,
            @Param("specialty") String specialty,
            @Param("position") String position,
            @Param("shift") String shift,
            Pageable pageable);

    // THÊM 2 PHƯƠNG THỨC NÀY
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    // Dùng cho việc cập nhật, kiểm tra email của người khác
    boolean existsByEmailAndIdNot(String email, Long id);
    // Dùng cho việc cập nhật, kiểm tra SĐT của người khác
    boolean existsByPhoneAndIdNot(String phone, Long id);
}