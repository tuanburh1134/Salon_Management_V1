package com.example.salon_management.controller;

import com.example.salon_management.dto.CustomerForm;
import com.example.salon_management.entity.Customer;
import com.example.salon_management.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    // ========================== DANH SÁCH KHÁCH HÀNG ==========================
    @GetMapping({"", "/", "/list"})
    public String list(@RequestParam(value = "q", required = false) String keyword,
                       @RequestParam(value = "memberType", required = false) String memberType,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       @RequestParam(defaultValue = "name") String sortBy,
                       @RequestParam(defaultValue = "asc") String dir,
                       Model model) {

        Sort sort = dir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Customer> customers = service.search(keyword, memberType, pageable);

        String nextDir = dir.equalsIgnoreCase("asc") ? "desc" : "asc";

        model.addAttribute("data", customers);
        model.addAttribute("q", keyword == null ? "" : keyword.trim());
        model.addAttribute("memberType", memberType == null ? "" : memberType);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("dir", dir);
        model.addAttribute("nextDir", nextDir);
        model.addAttribute("sortByNameUrl", buildUrl(keyword, memberType, 0, size, "name", nextDir));
        model.addAttribute("sortByPointUrl", buildUrl(keyword, memberType, 0, size, "point", nextDir));
        model.addAttribute("hasPrev", customers.hasPrevious());
        model.addAttribute("hasNext", customers.hasNext());
        model.addAttribute("currentPage", customers.getNumber() + 1);
        model.addAttribute("totalPages", customers.getTotalPages());
        model.addAttribute("prevUrl", customers.hasPrevious()
                ? buildUrl(keyword, memberType, customers.getNumber() - 1, size, sortBy, dir)
                : null);
        model.addAttribute("nextUrl", customers.hasNext()
                ? buildUrl(keyword, memberType, customers.getNumber() + 1, size, sortBy, dir)
                : null);

        model.addAttribute("memberTypes", new String[]{"MOI", "THAN_QUEN", "DAC_BIET"});
        model.addAttribute("pageTitle", "Danh sách khách hàng");

        return "customer/list";
    }

    // ========================== THÊM MỚI KHÁCH HÀNG ==========================
    @GetMapping("/create")
    public String createForm(Model model) {
        setupFormModel(model, new CustomerForm(), "Thêm khách hàng",
                "/customers/create", "Lưu mới", false, "/customers");
        return "customer/form";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("form") CustomerForm form,
                         BindingResult result,
                         RedirectAttributes ra,
                         Model model) {

        if (result.hasErrors()) {
            setupFormModel(model, form, "Thêm khách hàng",
                    "/customers/create", "Lưu mới", false, "/customers");
            return "customer/form";
        }

        try {
            handleFileUpload(form);
            service.create(form);
            ra.addFlashAttribute("msg", "✅ Thêm khách hàng thành công!");
            return "redirect:/customers";
        } catch (IOException e) {
            model.addAttribute("error", "⚠️ Lỗi khi lưu ảnh: " + e.getMessage());
            setupFormModel(model, form, "Thêm khách hàng",
                    "/customers/create", "Lưu mới", false, "/customers");
            return "customer/form";
        }
    }

    // ========================== CHỈNH SỬA KHÁCH HÀNG ==========================
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id,
                           @RequestParam(required = false) String q,
                           @RequestParam(required = false) String memberType,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size,
                           @RequestParam(defaultValue = "name") String sortBy,
                           @RequestParam(defaultValue = "asc") String dir,
                           Model model,
                           RedirectAttributes ra) {
        try {
            Customer c = service.get(id);
            CustomerForm f = new CustomerForm();
            f.setName(c.getName());
            f.setPhone(c.getPhone());
            f.setEmail(c.getEmail());
            f.setMemberType(c.getMemberType());
            f.setPoint(c.getPoint());
            f.setAddress(c.getAddress());
            f.setNote(c.getNote());
            f.setPhoto(c.getPhoto());

            String backUrl = buildUrl(q, memberType, page, size, sortBy, dir);
            String actionUrl = "/customers/" + id + "/edit" + "?q=" + q + "&memberType=" + memberType
                    + "&page=" + page + "&size=" + size + "&sortBy=" + sortBy + "&dir=" + dir;

            setupFormModel(model, f, "Chỉnh sửa khách hàng",
                    actionUrl, "Cập nhật", true, backUrl);
            return "customer/form";
        } catch (RuntimeException e) {
            ra.addFlashAttribute("msg", "⚠️ Không tìm thấy khách hàng có ID " + id);
            return "redirect:/customers";
        }
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("form") CustomerForm form,
                         BindingResult br,
                         RedirectAttributes ra,
                         Model model,
                         @RequestParam(required = false) String q,
                         @RequestParam(required = false) String memberType,
                         @RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "10") int size,
                         @RequestParam(defaultValue = "name") String sortBy,
                         @RequestParam(defaultValue = "asc") String dir) {

        String backUrl = buildUrl(q, memberType, page, size, sortBy, dir);
        String actionUrl = "/customers/" + id + "/edit" + "?q=" + q + "&memberType=" + memberType
                + "&page=" + page + "&size=" + size + "&sortBy=" + sortBy + "&dir=" + dir;

        if (br.hasErrors()) {
            setupFormModel(model, form, "Chỉnh sửa khách hàng",
                    actionUrl, "Cập nhật", true, backUrl);
            return "customer/form";
        }

        try {
            Customer existing = service.get(id);
            if (form.getPhotoFile() != null && !form.getPhotoFile().isEmpty()) {
                handleFileUpload(form);
            } else {
                form.setPhoto(existing.getPhoto());
            }

            service.update(id, form);
            ra.addFlashAttribute("msg", "✅ Cập nhật thông tin khách hàng thành công!");

            // ✅ Giữ lại bộ lọc khi quay về
            ra.addAttribute("q", q);
            ra.addAttribute("memberType", memberType);
            ra.addAttribute("page", page);
            ra.addAttribute("size", size);
            ra.addAttribute("sortBy", sortBy);
            ra.addAttribute("dir", dir);

            return "redirect:/customers";
        } catch (IOException e) {
            model.addAttribute("error", "⚠️ Không thể lưu ảnh: " + e.getMessage());
            setupFormModel(model, form, "Chỉnh sửa khách hàng",
                    actionUrl, "Cập nhật", true, backUrl);
            return "customer/form";
        }
    }

    // ========================== XOÁ KHÁCH HÀNG ==========================
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id,
                         @RequestParam(required = false) String q,
                         @RequestParam(required = false) String memberType,
                         @RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "10") int size,
                         @RequestParam(defaultValue = "name") String sortBy,
                         @RequestParam(defaultValue = "asc") String dir,
                         RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("msg", "🗑 Đã xoá khách hàng thành công!");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("msg", "⚠️ Không thể xoá: " + e.getMessage());
        }

        return "redirect:" + buildUrl(q, memberType, page, size, sortBy, dir);
    }

    // ========================== XEM CHI TIẾT KHÁCH HÀNG ==========================
    @GetMapping("/{id}/view")
    public String viewCustomer(@PathVariable Long id,
                               @RequestParam(required = false) String q,
                               @RequestParam(required = false) String memberType,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "name") String sortBy,
                               @RequestParam(defaultValue = "asc") String dir,
                               Model model) {
        Customer customer = service.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy khách hàng ID: " + id));

        model.addAttribute("customer", customer);

        String backUrl = buildUrl(q, memberType, page, size, sortBy, dir);
        model.addAttribute("backUrl", backUrl);

        return "customer/detail";
    }

    // ========================== HỖ TRỢ ==========================
    private void handleFileUpload(CustomerForm form) throws IOException {
        MultipartFile photoFile = form.getPhotoFile();
        if (photoFile != null && !photoFile.isEmpty()) {
            Path uploadPath = Paths.get(System.getProperty("user.dir"), "uploads", "customers");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String filename = UUID.randomUUID() + "_" + photoFile.getOriginalFilename();
            Path filePath = uploadPath.resolve(filename);
            photoFile.transferTo(filePath.toFile());
            form.setPhoto(filename);
        }
    }

    private void setupFormModel(Model model, CustomerForm form,
                                String title, String action, String submit,
                                boolean isEdit, String backUrl) {
        model.addAttribute("form", form);
        model.addAttribute("pageTitle", title);
        model.addAttribute("formAction", action);
        model.addAttribute("submitLabel", submit);
        model.addAttribute("isEdit", isEdit);
        model.addAttribute("backUrl", backUrl);
    }

    private String buildUrl(String q, String memberType, int page, int size, String sortBy, String dir) {
        String kw = (q == null || q.isBlank()) ? "" : q.trim().replace(" ", "%20");
        String mt = (memberType == null || memberType.isBlank()) ? "" : memberType;
        return "/customers?q=" + kw + "&memberType=" + mt + "&page=" + page + "&size=" + size
                + "&sortBy=" + sortBy + "&dir=" + dir;
    }
}
