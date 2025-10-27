package com.example.salon_management.service.impl;

import com.example.salon_management.entity.Employee;
import com.example.salon_management.repository.EmployeeRepository;
import com.example.salon_management.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repo;

    @Override
    public Page<Employee> searchByCriteria(String keyword, String specialty, String position, String shift, Pageable pageable) {
        Sort sort = pageable.getSort();

        if (sort.isSorted()) {
            Sort.Order order = sort.iterator().next();
            String property = order.getProperty();
            Sort.Direction direction = order.getDirection();

            switch (property) {
                case "name":
                    // Giữ nguyên vì đây là hàm
                    sort = JpaSort.unsafe(direction, "SUBSTRING_INDEX(e.name, ' ', -1)");
                    break;
                case "position":
                    // SỬA Ở ĐÂY: Bọc trong dấu () và thêm lại "e." bên trong
                    sort = JpaSort.unsafe(direction, "(CASE e.position WHEN 'Quản lý' THEN 1 WHEN 'Nhân viên' THEN 2 ELSE 3 END)");
                    break;
                case "shift":
                    // SỬA Ở ĐÂY: Bọc trong dấu () và thêm lại "e." bên trong
                    sort = JpaSort.unsafe(direction, "(CASE e.shift WHEN 'Ca sáng' THEN 1 WHEN 'Ca chiều' THEN 2 WHEN 'Ca tối' THEN 3 ELSE 4 END)");
                    break;
                default:
                    sort = Sort.by(direction, property);
                    break;
            }
        }

        Pageable newPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        return repo.searchByCriteria(keyword, specialty, position, shift, newPageable);
    }
    
    // --- CÁC PHƯƠNG THỨC get, create, update, delete GIỮ NGUYÊN ---
    @Override
    public Employee get(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));
    }

    @Override
    @Transactional
    public Employee create(Employee e) {

        e.setDeleted(false);
        return repo.save(e);
    }

    @Override
    @Transactional
    public Employee update(Long id, Employee e) {
        Employee old = get(id);
        old.setName(e.getName());
        old.setPosition(e.getPosition());
        old.setSpecialty(e.getSpecialty());
        old.setShift(e.getShift());
        old.setSalary(e.getSalary());
        old.setPhotoPath(e.getPhotoPath());
        old.setDateOfBirth(e.getDateOfBirth());
        old.setEmail(e.getEmail());
        old.setPhone(e.getPhone());
        old.setHometown(e.getHometown());
        return repo.save(old);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Không tìm thấy nhân viên với ID: " + id);
        }
        repo.deleteById(id);
    }
}