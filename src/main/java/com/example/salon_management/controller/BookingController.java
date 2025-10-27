package com.example.salon_management.controller;

import com.example.salon_management.dto.BookingForm;
import com.example.salon_management.dto.BookingSearchRequest;
import com.example.salon_management.entity.Customer;
import com.example.salon_management.entity.Employee;
import com.example.salon_management.entity.ServiceItem;
import com.example.salon_management.repository.CustomerRepository;
import com.example.salon_management.repository.EmployeeRepository;
import com.example.salon_management.repository.ServiceItemRepository;
import com.example.salon_management.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.beans.PropertyEditorSupport;
import java.util.List;

@Controller
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final ServiceItemRepository serviceItemRepository;

    /** Cắt trim toàn bộ String khi bind (tránh " tên ") */
    @InitBinder
    void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override public void setAsText(String text) {
                setValue(text == null ? null : text.trim());
            }
        });
    }

    // ========================= LIST =========================
    @GetMapping({"", "/", "/list"})
    public String list(@ModelAttribute("q") BookingSearchRequest q, Model model) {
        normalize(q); // đảm bảo sort/page/size hợp lệ

        Sort sort = "desc".equalsIgnoreCase(q.getDir())
                ? Sort.by(q.getSortBy()).descending()
                : Sort.by(q.getSortBy()).ascending();

        Pageable pageable = PageRequest.of(q.getPage(), q.getSize(), sort);

        model.addAttribute("page", bookingService.search(q.getKeyword(), q.getStatus(), pageable));
        model.addAttribute("q", q);
        return "booking/list";
    }

    // ========================= CREATE =========================
    @GetMapping("/create")
    public String createForm(Model model) {
        prepareForm(model,
                new BookingForm(),
                "Thêm đặt lịch",
                "/bookings/create",
                "Lưu");
        return "booking/form";
    }

    @PostMapping("/create")
    public String createSubmit(@Valid @ModelAttribute("form") BookingForm form,
                               BindingResult br,
                               Model model,
                               RedirectAttributes ra) {
        if (br.hasErrors()) {
            prepareForm(model, form, "Thêm đặt lịch", "/bookings/create", "Lưu");
            return "booking/form";
        }
        bookingService.create(form);
        ra.addFlashAttribute("msg", "Đã thêm đặt lịch thành công!");
        return "redirect:/bookings";
    }

    // ========================= EDIT =========================
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        var booking = bookingService.findById(id).orElseThrow();
        prepareForm(model,
                BookingForm.from(booking),
                "Cập nhật đặt lịch",
                "/bookings/" + id + "/edit",
                "Cập nhật");
        model.addAttribute("id", id);
        return "booking/form";
    }

    @PostMapping("/{id}/edit")
    public String editSubmit(@PathVariable Long id,
                             @Valid @ModelAttribute("form") BookingForm form,
                             BindingResult br,
                             Model model,
                             RedirectAttributes ra) {
        if (br.hasErrors()) {
            prepareForm(model, form, "Cập nhật đặt lịch", "/bookings/" + id + "/edit", "Cập nhật");
            model.addAttribute("id", id);
            return "booking/form";
        }
        bookingService.update(id, form);
        ra.addFlashAttribute("msg", "Đã cập nhật đặt lịch");
        return "redirect:/bookings";
    }

    // ========================= DELETE =========================
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        bookingService.delete(id);
        ra.addFlashAttribute("msg", "Đã xóa đặt lịch");
        return "redirect:/bookings";
    }

    // ========================= Helpers =========================
    private void prepareForm(Model model,
                             BookingForm form,
                             String title,
                             String actionUrl,
                             String submitLabel) {
        List<Customer> customers = customerRepository.findAll();
        List<Employee> employees = employeeRepository.findAll();
        List<ServiceItem> services = serviceItemRepository.findAll();

        model.addAttribute("form", form);
        model.addAttribute("title", title);
        model.addAttribute("actionUrl", actionUrl);
        model.addAttribute("submitLabel", submitLabel);
        model.addAttribute("customers", customers);
        model.addAttribute("employees", employees);
        model.addAttribute("services", services);
    }

    private void normalize(BookingSearchRequest q) {
        if (q.getSortBy() == null || q.getSortBy().isBlank()) q.setSortBy("bookingDateTime");
        if (q.getDir() == null || q.getDir().isBlank()) q.setDir("desc");
        if (q.getPage() < 0) q.setPage(0);
        if (q.getSize() <= 0 || q.getSize() > 100) q.setSize(10);
    }
}

