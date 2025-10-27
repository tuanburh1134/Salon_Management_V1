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
                        Employee.builder()
                                .name("Nguyễn Văn A")
                                .position("Quản lý")
                                .specialty("Chăm sóc tóc")
                                .shift("Ca sáng")
                                .salary(15000000L)
                                // Thêm các trường bắt buộc mới
                                .email("nguyenvana@example.com")
                                .phone("0912345670")
                                .dateOfBirth(LocalDate.of(1990, 5, 15))
                                .hometown("Hà Nội")
                                .deleted(false)
                                .build(),
                        Employee.builder()
                                .name("Trần Thị B")
                                .position("Nhân viên")
                                .specialty("Chăm sóc da")
                                .shift("Ca chiều")
                                .salary(8000000L)
                                .email("tranthib@example.com")
                                .phone("0912345671")
                                .dateOfBirth(LocalDate.of(1995, 8, 22))
                                .hometown("Đà Nẵng")
                                .deleted(false)
                                .build(),
                        Employee.builder()
                                .name("Lê Văn C")
                                .position("Nhân viên")
                                .specialty("Trang điểm")
                                .shift("Ca tối")
                                .salary(9000000L)
                                .email("levanc@example.com")
                                .phone("0912345672")
                                .dateOfBirth(LocalDate.of(1998, 1, 30))
                                .hometown("TP. Hồ Chí Minh")
                                .deleted(false)
                                .build(),
                        Employee.builder()
                                .name("Phạm Thị D")
                                .position("Quản lý")
                                .specialty("Mát xa")
                                .shift("Ca sáng")
                                .salary(12000000L)
                                .email("phamthid@example.com")
                                .phone("0912345673")
                                .dateOfBirth(LocalDate.of(1992, 11, 11))
                                .hometown("Hải Phòng")
                                .deleted(false)
                                .build(),
                        Employee.builder()
                                .name("Hoàng Văn E")
                                .position("Nhân viên")
                                .specialty("Chăm sóc tay")
                                .shift("Ca chiều")
                                .salary(7000000L)
                                .email("hoangvane@example.com")
                                .phone("0912345674")
                                .dateOfBirth(LocalDate.of(2000, 2, 29))
                                .hometown("Cần Thơ")
                                .deleted(false)
                                .build(),
                        Employee.builder()
                                .name("Vũ Thị F")
                                .position("Nhân viên")
                                .specialty("Chăm sóc tóc")
                                .shift("Ca tối")
                                .salary(8500000L)
                                .email("vuthif@example.com")
                                .phone("0912345675")
                                .dateOfBirth(LocalDate.of(1999, 7, 7))
                                .hometown("Bình Dương")
                                .deleted(false)
                                .build(),
                        Employee.builder()
                                .name("Đặng Văn G")
                                .position("Quản lý")
                                .specialty("Chăm sóc da")
                                .shift("Ca sáng")
                                .salary(14000000L)
                                .email("dangvang@example.com")
                                .phone("0912345676")
                                .dateOfBirth(LocalDate.of(1988, 10, 20))
                                .hometown("Nghệ An")
                                .deleted(false)
                                .build(),
                        Employee.builder()
                                .name("Bùi Thị H")
                                .position("Nhân viên")
                                .specialty("Trang điểm")
                                .shift("Ca chiều")
                                .salary(7500000L)
                                .email("buithih@example.com")
                                .phone("0912345677")
                                .dateOfBirth(LocalDate.of(2001, 3, 8))
                                .hometown("Thanh Hóa")
                                .deleted(false)
                                .build(),
                        Employee.builder()
                                .name("Ngô Văn I")
                                .position("Nhân viên")
                                .specialty("Mát xa")
                                .shift("Ca tối")
                                .salary(9500000L)
                                .email("ngovani@example.com")
                                .phone("0912345678")
                                .dateOfBirth(LocalDate.of(1997, 6, 1))
                                .hometown("Quảng Ninh")
                                .deleted(false)
                                .build(),
                        Employee.builder()
                                .name("Đỗ Thị K")
                                .position("Nhân viên")
                                .specialty("Chăm sóc tay")
                                .shift("Ca sáng")
                                .salary(8000000L)
                                .email("dothik@example.com")
                                .phone("0912345679")
                                .dateOfBirth(LocalDate.of(1996, 4, 19))
                                .hometown("Lâm Đồng")
                                .deleted(false)
                                .build()
                );
                repository.saveAll(employees);
            }
        };
    }
}