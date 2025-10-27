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

}
