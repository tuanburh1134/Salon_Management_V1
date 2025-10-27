package com.example.salon_management.service.impl;

import com.example.salon_management.dto.BookingForm;
import com.example.salon_management.entity.Booking;
import com.example.salon_management.entity.Customer;
import com.example.salon_management.entity.Employee;
import com.example.salon_management.entity.ServiceItem;
import com.example.salon_management.repository.BookingRepository;
import com.example.salon_management.repository.CustomerRepository;
import com.example.salon_management.repository.EmployeeRepository;
import com.example.salon_management.repository.ServiceItemRepository;
import com.example.salon_management.service.BookingCodeService;
import com.example.salon_management.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final ServiceItemRepository serviceItemRepository;
    private final BookingCodeService bookingCodeService;

    @Override
    @Transactional(readOnly = true)
    public Page<Booking> search(String keyword, String status, Pageable pageable) {
        if (status != null && !status.trim().isEmpty()) {
            return bookingRepository.searchBookingsWithStatus(keyword, status, pageable);
        }
        return bookingRepository.searchBookings(keyword, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }

    @Override
    public Booking create(BookingForm form) {
        Booking booking = new Booking();
        booking.setBookingCode(bookingCodeService.generateNextBookingCode());
        updateBookingFromForm(booking, form);
        booking.setCreatedAt(LocalDateTime.now());
        return bookingRepository.save(booking);
    }

    @Override
    public Booking update(Long id, BookingForm form) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        updateBookingFromForm(booking, form);
        booking.setUpdatedAt(LocalDateTime.now());
        return bookingRepository.save(booking);
    }

    @Override
    public void delete(Long id) {
        bookingRepository.deleteById(id);
    }

    private void updateBookingFromForm(Booking booking, BookingForm form) {
        Customer customer = customerRepository.findById(form.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        booking.setCustomer(customer);

        ServiceItem service = serviceItemRepository.findById(form.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found"));
        booking.setService(service);

        Employee employee = employeeRepository.findById(form.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        booking.setEmployee(employee);

        booking.setBookingDateTime(form.getBookingDateTime());
        booking.setStatus(form.getStatus());
        booking.setNotes(form.getNotes());
    }
}


