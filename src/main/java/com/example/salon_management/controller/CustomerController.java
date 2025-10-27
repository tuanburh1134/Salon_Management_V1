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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;
    private final CustomerService customerService;
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
        setupFormModel(model, new CustomerForm(), "Thêm khách hàng", "/customers/create", "Lưu mới", false);
        return "customer/form";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("form") CustomerForm form,
                         BindingResult br, RedirectAttributes ra, Model model) {
        if (br.hasErrors()) {
            setupFormModel(model, form, "Thêm khách hàng", "/customers/create", "Lưu mới", false);
            return "customer/form";
        }

        service.create(form);
        ra.addFlashAttribute("msg", " Đã thêm khách hàng mới thành công!");
        return "redirect:/customers";
    }

    // ========================== CHỈNH SỬA KHÁCH HÀNG ==========================
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
        try {
            Customer c = service.get(id);
            CustomerForm f = new CustomerForm();
            f.setName(c.getName());
            f.setPhone(c.getPhone());
            f.setEmail(c.getEmail());
            f.setMemberType(c.getMemberType() != null ? c.getMemberType().name() : null);
            f.setPoint(c.getPoint());

            setupFormModel(model, f, "Chỉnh sửa khách hàng", "/customers/" + id + "/edit", "Cập nhật", true);
            return "customer/form";
        } catch (RuntimeException e) {
            ra.addFlashAttribute("msg", "⚠️ Không tìm thấy khách hàng có ID " + id);
            return "redirect:/customers";
        }
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("form") CustomerForm form,
                         BindingResult br, RedirectAttributes ra, Model model) {
        if (br.hasErrors()) {
            setupFormModel(model, form, "Chỉnh sửa khách hàng", "/customers/" + id + "/edit", "Cập nhật", true);
            return "customer/form";
        }

        try {
            service.update(id, form);
            ra.addFlashAttribute("msg", " Cập nhật thông tin khách hàng thành công!");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("msg", "⚠️ Cập nhật thất bại: " + e.getMessage());
        }
        return "redirect:/customers";
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

        // 🔁 Giữ nguyên trạng thái lọc / tìm kiếm / phân trang sau khi xoá
        String redirectUrl = buildUrl(q, memberType, page, size, sortBy, dir);
        return "redirect:" + redirectUrl;
    }
    @GetMapping("/{id}/view")
    public String viewCustomer(@PathVariable Long id, Model model) {
        Customer customer = customerService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy khách hàng ID: " + id));

        model.addAttribute("customer", customer);
        return "customer/detail";
    }

    // ========================== HÀM HỖ TRỢ ==========================
    private void setupFormModel(Model model, CustomerForm form, String title, String action, String submit, boolean isEdit) {
        model.addAttribute("form", form);
        model.addAttribute("pageTitle", title);
        model.addAttribute("formAction", action);
        model.addAttribute("submitLabel", submit);
        model.addAttribute("isEdit", isEdit);
    }

    private String buildUrl(String q, String memberType, int page, int size, String sortBy, String dir) {
        String kw = (q == null || q.isBlank()) ? "" : q.trim().replace(" ", "%20");
        String mt = (memberType == null || memberType.isBlank()) ? "" : memberType;
        return "/customers?q=" + kw + "&memberType=" + mt + "&page=" + page + "&size=" + size + "&sortBy=" + sortBy + "&dir=" + dir;
    }
}
