package com.example.salon_management.service.impl;

import com.example.salon_management.dto.CustomerForm;
import com.example.salon_management.entity.Customer;
import com.example.salon_management.repository.CustomerRepository;
import com.example.salon_management.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repo;

    @Override
    public Page<Customer> search(String keyword, Pageable pageable) {
        return repo.search(keyword == null ? "" : keyword.trim(), pageable);
    }

    @Override
    public Customer get(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng id=" + id));
    }

    @Override
    public void create(CustomerForm f) {
        Customer c = new Customer();
        c.setName(f.getName());
        c.setPhone(f.getPhone());
        c.setEmail(f.getEmail());
        c.setMemberType(f.getMemberType());
        c.setPoint(f.getPoint() == null ? 0 : f.getPoint());
        repo.save(c);
    }

    @Override
    public void update(Long id, CustomerForm f) {
        Customer c = get(id);
        c.setName(f.getName());
        c.setPhone(f.getPhone());
        c.setEmail(f.getEmail());
        c.setMemberType(f.getMemberType());
        c.setPoint(f.getPoint() == null ? 0 : f.getPoint());
        repo.save(c);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
