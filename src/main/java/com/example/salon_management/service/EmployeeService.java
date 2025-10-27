package com.example.salon_management.service;

import com.example.salon_management.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
    Page<Employee> searchByCriteria(String keyword, String specialty, String position, String shift, Pageable pageable);
    Employee get(Long id);
    Employee create(Employee e);
    Employee update(Long id, Employee e);
    void delete(Long id);
}