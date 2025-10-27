package com.example.salon_management.controller;

import com.example.salon_management.dto.EmployeeSearchRequest;
import com.example.salon_management.entity.Employee;
import com.example.salon_management.repository.EmployeeRepository;
import com.example.salon_management.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;
    // 1. Tiêm EmployeeRepository để kiểm tra trùng lặp
    private final EmployeeRepository repository;

    private static final String UPLOAD_DIR = "uploads/";

    @GetMapping
    public String list(@ModelAttribute("q") EmployeeSearchRequest q, Model model) {
        // Sắp xếp mặc định khi tìm kiếm
        Sort sort = Sort.by(Sort.Direction.fromString(q.getSortDir()), q.getSortBy());
        Pageable pageable = PageRequest.of(q.getPage(), q.getSize(), sort);

        Page<Employee> data = service.searchByCriteria(q.getKeyword(), q.getSpecialty(), q.getPosition(), q.getShift(), pageable);

        model.addAttribute("data", data);
        model.addAttribute("q", q);
        model.addAttribute("pageTitle", "Danh sách nhân viên");
        return "employee/list";
    }
    // ===== 2. PHƯƠNG THỨC MỚI - CHỈ XỬ LÝ SẮP XẾP (TỪ LINK TIÊU ĐỀ) =====
    @GetMapping("/sort")
    public String sort(@ModelAttribute("q") EmployeeSearchRequest q, Model model) {
        // Gọi lại chính phương thức list ở trên để tái sử dụng code
        // Spring sẽ tự động binding tất cả các tham số từ URL vào object 'q'
        return list(q, model);
    }
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("pageTitle", "Thêm nhân viên mới");
        model.addAttribute("formAction", "/employees/create");
        return "employee/form";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("employee") Employee e, BindingResult br,
                         @RequestParam("photo") MultipartFile photo, RedirectAttributes ra, Model model) throws IOException {

        // Kiểm tra các validation cơ bản (@NotBlank, @Min,...)
        if (br.hasErrors()) {
            model.addAttribute("pageTitle", "Thêm nhân viên mới");
            model.addAttribute("formAction", "/employees/create");
            return "employee/form";
        }

        // 2. Thêm logic kiểm tra trùng lặp email và SĐT
        if (repository.existsByEmail(e.getEmail())) {
            br.rejectValue("email", "error.employee", "Email này đã được sử dụng.");
        }
        if (repository.existsByPhone(e.getPhone())) {
            br.rejectValue("phone", "error.employee", "Số điện thoại này đã được sử dụng.");
        }
        if (e.getDateOfBirth() != null) {
            int year = e.getDateOfBirth().getYear();
            if (year < 1700 || year > 2007) {
                br.rejectValue("dateOfBirth", "error.employee", "Năm sinh phải trong khoảng từ 1700 đến 2007.");
            }
        }
        // Nếu có lỗi trùng lặp, quay lại form để hiển thị
        if (br.hasErrors()) {
            model.addAttribute("pageTitle", "Thêm nhân viên mới");
            model.addAttribute("formAction", "/employees/create");
            return "employee/form";
        }

        // Xử lý upload ảnh
        if (!photo.isEmpty()) {
            try {
                String fileName = UUID.randomUUID().toString() + "_" + photo.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR + fileName);
                Files.createDirectories(path.getParent());
                Files.write(path, photo.getBytes());
                e.setPhotoPath(fileName);
            } catch (IOException ex) {
                ra.addFlashAttribute("msg", "Lỗi khi tải ảnh: " + ex.getMessage());
                return "employee/form";
            }
        }

        service.create(e);
        ra.addFlashAttribute("msg", "Thêm nhân viên thành công!");
        return "redirect:/employees";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("employee", service.get(id));
        model.addAttribute("pageTitle", "Chỉnh sửa nhân viên");
        model.addAttribute("formAction", "/employees/" + id + "/edit");
        return "employee/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("employee") Employee e,
                         BindingResult br, @RequestParam("photo") MultipartFile photo,
                         RedirectAttributes ra, Model model) throws IOException {

        if (br.hasErrors()) {
            model.addAttribute("pageTitle", "Chỉnh sửa nhân viên");
            model.addAttribute("formAction", "/employees/" + id + "/edit");
            return "employee/form";
        }

        // 3. Thêm logic kiểm tra trùng lặp khi cập nhật
        if (repository.existsByEmailAndIdNot(e.getEmail(), id)) {
            br.rejectValue("email", "error.employee", "Email này đã được sử dụng bởi một nhân viên khác.");
        }
        if (repository.existsByPhoneAndIdNot(e.getPhone(), id)) {
            br.rejectValue("phone", "error.employee", "Số điện thoại này đã được sử dụng bởi một nhân viên khác.");
        }

        if (br.hasErrors()) {
            model.addAttribute("pageTitle", "Chỉnh sửa nhân viên");
            model.addAttribute("formAction", "/employees/" + id + "/edit");
            return "employee/form";
        }

        // Xử lý upload ảnh
        if (!photo.isEmpty()) {
            try {
                String fileName = UUID.randomUUID().toString() + "_" + photo.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR + fileName);
                Files.createDirectories(path.getParent());
                Files.write(path, photo.getBytes());
                e.setPhotoPath(fileName);
            } catch (IOException ex) {
                ra.addFlashAttribute("msg", "Lỗi khi tải ảnh: " + ex.getMessage());
                return "employee/form";
            }
        } else {
            Employee existing = service.get(id);
            e.setPhotoPath(existing.getPhotoPath());
        }

        service.update(id, e);
        ra.addFlashAttribute("msg", "Cập nhật nhân viên thành công!");
        return "redirect:/employees";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id,
                         @RequestParam(value = "keyword", required = false) String keyword,
                         @RequestParam(value = "position", required = false) String position,
                         @RequestParam(value = "shift", required = false) String shift,
                         @RequestParam(value = "specialty", required = false) String specialty,
                         @RequestParam(value = "page", defaultValue = "0") int page,
                         @RequestParam(value = "size", defaultValue = "10") int size,
                         RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("msg", "Đã xoá nhân viên thành công!");
        } catch (Exception ex) {
            ra.addFlashAttribute("msg", "Lỗi khi xoá nhân viên: " + ex.getMessage());
        }

        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/employees")
                .queryParam("keyword", keyword)
                .queryParam("position", position)
                .queryParam("shift", shift)
                .queryParam("specialty", specialty)
                .queryParam("page", page)
                .queryParam("size", size);
        return "redirect:" + builder.toUriString();
    }

    @GetMapping("/{id}/detail")
    public String detail(@PathVariable Long id, Model model) {
        Employee employee = service.get(id);
        model.addAttribute("employee", employee);
        model.addAttribute("pageTitle", "Chi tiết nhân viên: " + employee.getName());
        return "employee/detail";
    }
}
