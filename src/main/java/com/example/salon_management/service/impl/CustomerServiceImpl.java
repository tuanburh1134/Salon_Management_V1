package com.example.salon_management.service.impl;

import com.example.salon_management.dto.CustomerForm;
import com.example.salon_management.entity.Customer;
import com.example.salon_management.entity.Customer.MemberType;
import com.example.salon_management.repository.CustomerRepository;
import com.example.salon_management.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repo;

    // ================== TÌM KIẾM & PHÂN TRANG ==================
    @Override
    public Page<Customer> search(String keyword, Pageable pageable) {
        String kw = (keyword == null) ? "" : keyword.trim();
        if (kw.isBlank()) {
            return repo.findByDeletedFalse(pageable);
        }
        return repo.search(kw, pageable);
    }

    @Override
    public Page<Customer> search(String keyword, String memberType, Pageable pageable) {
        String kw = (keyword == null) ? "" : keyword.trim();
        String mt = (memberType == null) ? "" : memberType.trim();

        // Không filter gì cả
        if (kw.isBlank() && mt.isBlank()) {
            return repo.findByDeletedFalse(pageable);
        }

        // Chỉ lọc loại thành viên
        if (kw.isBlank() && !mt.isBlank()) {
            try {
                MemberType type = MemberType.valueOf(mt);
                return repo.findByDeletedFalseAndMemberType(type, pageable);
            } catch (IllegalArgumentException e) {
                return Page.empty(pageable);
            }
        }

        // Chỉ tìm kiếm theo từ khóa
        if (!kw.isBlank() && mt.isBlank()) {
            return repo.search(kw, pageable);
        }

        // Kết hợp tìm kiếm + lọc loại thành viên
        try {
            MemberType type = MemberType.valueOf(mt);
            return repo.searchByKeywordAndMemberType(kw, type, pageable);
        } catch (IllegalArgumentException e) {
            return Page.empty(pageable);
        }
    }

    // ================== LẤY KHÁCH HÀNG THEO ID ==================
    @Override
    public Customer get(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Không tìm thấy khách hàng có ID=" + id));
    }

    // ================== THÊM MỚI KHÁCH HÀNG ==================
    @Override
    public void create(CustomerForm f) {
        Customer c = Customer.builder()
                .name(f.getName())
                .phone(f.getPhone())
                .email(f.getEmail())
                .memberType(MemberType.valueOf(f.getMemberType()))
                .point(f.getPoint() == null ? 0 : f.getPoint())
                .deleted(false)
                .build();

        c.updateMemberTypeByPoints();
        repo.save(c);
    }

    // ================== CẬP NHẬT KHÁCH HÀNG ==================
    @Override
    public void update(Long id, CustomerForm f) {
        Customer c = get(id);
        c.setName(f.getName());
        c.setPhone(f.getPhone());
        c.setEmail(f.getEmail());
        c.setMemberType(MemberType.valueOf(f.getMemberType()));
        c.setPoint(f.getPoint() == null ? 0 : f.getPoint());
        c.updateMemberTypeByPoints();
        repo.save(c);
    }

    // ================== XÓA MỀM (SOFT DELETE) ==================
    @Override
    public void delete(Long id) {
        Customer c = get(id);
        c.setDeleted(true);
        repo.save(c);
    }

    // ================== KHÔI PHỤC KHÁCH HÀNG ==================
    @Override
    public void restore(Long id) {
        Customer c = get(id);
        c.setDeleted(false);
        repo.save(c);
    }

    // ================== TÌM KIẾM NÂNG CAO ==================
    @Override
    public List<Customer> findByName(String name) {
        return repo.findByDeletedFalseAndNameContainingIgnoreCase(name);
    }

    @Override
    public List<Customer> findByPhone(String phone) {
        return repo.findByDeletedFalseAndPhoneContaining(phone);
    }

    @Override
    public List<Customer> findByMemberType(MemberType memberType) {
        return repo.findByDeletedFalseAndMemberType(memberType);
    }

    @Override
    public List<Customer> findActiveCustomers() {
        return repo.findByDeletedFalse();
    }

    @Override
    public List<Customer> findDeletedCustomers() {
        return repo.findByDeletedTrue();
    }

    // ================== SẮP XẾP ==================
    @Override
    public List<Customer> sortByNameAsc() {
        return repo.findAllByDeletedFalseOrderByNameAsc();
    }

    @Override
    public List<Customer> sortByNameDesc() {
        return repo.findAllByDeletedFalseOrderByNameDesc();
    }

    @Override
    public List<Customer> sortByPointsAsc() {
        return repo.findAllByDeletedFalseOrderByPointAsc();
    }

    @Override
    public List<Customer> sortByPointsDesc() {
        return repo.findAllByDeletedFalseOrderByPointDesc();
    }

    // ================== ĐIỂM TÍCH LŨY ==================
    @Override
    public void addPoints(Long id, int points) {
        Customer c = get(id);
        c.addPoints(points);
        c.updateMemberTypeByPoints();
        repo.save(c);
    }

    @Override
    public void subtractPoints(Long id, int points) {
        Customer c = get(id);
        c.subtractPoints(points);
        c.updateMemberTypeByPoints();
        repo.save(c);
    }

    @Override
    public void updateMemberTypeByPoints(Long id) {
        Customer c = get(id);
        c.updateMemberTypeByPoints();
        repo.save(c);
    }

    // ================== THỐNG KÊ ==================
    @Override
    public long countAll() {
        return repo.count();
    }

    @Override
    public long countActive() {
        return repo.countByDeletedFalse();
    }

    @Override
    public long countDeleted() {
        return repo.countByDeletedTrue();
    }

    @Override
    public long countByMemberType(MemberType memberType) {
        return repo.countByDeletedFalseAndMemberType(memberType);
    }
}
