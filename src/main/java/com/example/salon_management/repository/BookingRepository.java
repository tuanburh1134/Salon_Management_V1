package com.example.salon_management.repository;


import com.example.salon_management.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b " +
            "LEFT JOIN b.customer c " +
            "LEFT JOIN b.service s " +
            "LEFT JOIN b.employee e " +
            "WHERE (:keyword IS NULL OR :keyword = '' OR " +
            "       LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "       LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "       LOWER(e.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "       LOWER(b.status) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Booking> searchBookings(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT b FROM Booking b " +
            "LEFT JOIN b.customer c " +
            "LEFT JOIN b.service s " +
            "LEFT JOIN b.employee e " +
            "WHERE (:keyword IS NULL OR :keyword = '' OR " +
            "       LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "       LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "       LOWER(e.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "       LOWER(b.status) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:status IS NULL OR :status = '' OR LOWER(b.status) LIKE LOWER(CONCAT('%', :status, '%')))")
    Page<Booking> searchBookingsWithStatus(@Param("keyword") String keyword,
                                           @Param("status") String status,
                                           Pageable pageable);
}

