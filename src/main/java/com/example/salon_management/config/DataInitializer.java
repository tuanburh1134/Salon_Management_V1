package com.example.salon_management.config;

import com.example.salon_management.entity.Employee;
import com.example.salon_management.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate; // Thêm import này
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(EmployeeRepository repository) {
        return args -> {
            // Chỉ thêm dữ liệu nếu database trống
            if (repository.count() == 0) {
                List<Employee> employees = List.of(
                        createEmployee("Nguyễn Văn A", "Quản lý", "Chăm sóc tóc", "Ca sáng", 15000000L, "nguyenvana@example.com", "0912345670", LocalDate.of(1990, 5, 15), "Hà Nội"),
                        createEmployee("Trần Thị B", "Nhân viên", "Chăm sóc da", "Ca chiều", 8000000L, "tranthib@example.com", "0912345671", LocalDate.of(1995, 8, 22), "Đà Nẵng"),
                        createEmployee("Lê Văn C", "Nhân viên", "Trang điểm", "Ca tối", 9000000L, "levanc@example.com", "0912345672", LocalDate.of(1998, 1, 30), "TP. Hồ Chí Minh"),
                        createEmployee("Phạm Thị D", "Quản lý", "Mát xa", "Ca sáng", 12000000L, "phamthid@example.com", "0912345673", LocalDate.of(1992, 11, 11), "Hải Phòng"),
                        createEmployee("Hoàng Văn E", "Nhân viên", "Chăm sóc tay", "Ca chiều", 7000000L, "hoangvane@example.com", "0912345674", LocalDate.of(2000, 2, 29), "Cần Thơ"),
                        createEmployee("Vũ Thị F", "Nhân viên", "Chăm sóc tóc", "Ca tối", 8500000L, "vuthif@example.com", "0912345675", LocalDate.of(1999, 7, 7), "Bình Dương"),
                        createEmployee("Đặng Văn G", "Quản lý", "Chăm sóc da", "Ca sáng", 14000000L, "dangvang@example.com", "0912345676", LocalDate.of(1988, 10, 20), "Nghệ An"),
                        createEmployee("Bùi Thị H", "Nhân viên", "Trang điểm", "Ca chiều", 7500000L, "buithih@example.com", "0912345677", LocalDate.of(2001, 3, 8), "Thanh Hóa"),
                        createEmployee("Ngô Văn I", "Nhân viên", "Mát xa", "Ca tối", 9500000L, "ngovani@example.com", "0912345678", LocalDate.of(1997, 6, 1), "Quảng Ninh"),
                        createEmployee("Đỗ Thị K", "Nhân viên", "Chăm sóc tay", "Ca sáng", 8000000L, "dothik@example.com", "0912345679", LocalDate.of(1996, 4, 19), "Lâm Đồng")
                );
                repository.saveAll(employees);
            }
        };
    }

    private Employee createEmployee(String name, String position, String specialty, String shift,
                                    Long salary, String email, String phone, LocalDate dateOfBirth, String hometown) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setPosition(position);
        employee.setSpecialty(specialty);
        employee.setShift(shift);
        employee.setSalary(salary);
        employee.setEmail(email);
        employee.setPhone(phone);
        employee.setDateOfBirth(dateOfBirth);
        employee.setHometown(hometown);
        employee.setDeleted(false);
        return employee;
    }
}