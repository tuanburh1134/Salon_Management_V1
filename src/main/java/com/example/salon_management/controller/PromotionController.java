package com.example.salon_management.controller;

import com.example.salon_management.dto.PromotionForm;
import com.example.salon_management.entity.Promotion;
import com.example.salon_management.entity.Promotion.PromotionStatus;
import com.example.salon_management.entity.Promotion.PromotionType;
import com.example.salon_management.service.PromotionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/promotions")
public class PromotionController {

    private final PromotionService promotionService;

    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    private void validateDateRange(PromotionForm form, BindingResult bindingResult) {
        if (form.getStartAt() != null && form.getEndAt() != null) {
            if (form.getStartAt().isAfter(form.getEndAt())) {
                bindingResult.rejectValue("endAt", "error.form", "Ngày kết thúc phải sau hoặc bằng Ngày bắt đầu.");
            }
        }
    }

    private void validateCodeAndNameUnique(PromotionForm form, BindingResult bindingResult) {
        Integer id = form.getId();
        String name = form.getName();

        // Không validate code vì tự động sinh khi create
        // Chỉ validate code khi update
        if (id != null) {
            String code = form.getCode();
            if (code != null && !code.trim().isEmpty()) {
                boolean isCodeDuplicate = promotionService.existsByCodeAndIdNot(code.trim(), id);
                if (isCodeDuplicate) {
                    bindingResult.rejectValue("code", "code.duplicate", "Mã khuyến mãi đã tồn tại. Vui lòng chọn mã khác.");
                }
            }
        }

        // Validate name
        if (name != null && !name.trim().isEmpty()) {
            boolean isNameDuplicate = (id == null)
                    ? promotionService.existsByName(name.trim())
                    : promotionService.existsByNameAndIdNot(name.trim(), id);
            if (isNameDuplicate) {
                bindingResult.rejectValue("name", "name.duplicate", "Tên khuyến mãi đã tồn tại. Vui lòng chọn tên khác.");
            }
        }
    }

    private void validateStatusForEdit(Integer id, PromotionForm form, BindingResult bindingResult) {
        if (id != null) {
            var existing = promotionService.findById(id);
            if (existing.isPresent() && existing.get().getStatus() == Promotion.PromotionStatus.EXPIRED) {
                bindingResult.reject("error.form", "Không thể sửa khuyến mãi đã hết hạn.");
            }
        }
    }

    @GetMapping
    public String list(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) PromotionStatus status,
            @RequestParam(required = false) PromotionType type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDir,
            Model model
    ) {
        // Validate và normalize parameters
        page = Math.max(0, page);
        size = Math.max(1, Math.min(size, 100)); // 1-100 per page

        // Set default values for sort
        if (sortDir == null || sortDir.trim().isEmpty()) {
            sortDir = "asc";
        }
        sortDir = sortDir.equalsIgnoreCase("desc") ? "desc" : "asc";

        if (sortField == null || sortField.trim().isEmpty()) {
            sortField = "id";
        }

        // Validate sort field - phải dùng camelCase như trong entity
        java.util.Set<String> validFields = java.util.Set.of("id", "name", "percent", "startAt", "endAt", "status");

        // Chuyển snake_case sang camelCase nếu cần
        sortField = sortField.replace("_", "").replace("-", "");

        // Validate với Set
        if (!validFields.contains(sortField)) {
            sortField = "id";
        }

        // Safe sort với try-catch
        Sort sort;
        try {
            sort = sortDir.equals("desc")
                    ? Sort.by(sortField).descending()
                    : Sort.by(sortField).ascending();
        } catch (IllegalArgumentException e) {
            // Fallback nếu field không tồn tại trong entity
            sortField = "id";
            sort = Sort.by(sortField).ascending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Promotion> promotionPage = promotionService.searchAndFilter(
                keyword, status, type, startDate, endDate, pageable);

        model.addAttribute("promotions", promotionPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", promotionPage.getTotalPages());
        model.addAttribute("totalItems", promotionPage.getTotalElements());

        model.addAttribute("keyword", keyword);
        model.addAttribute("status", status);
        model.addAttribute("type", type);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("pageTitle", "Danh sách khuyến mãi");
        return "promotion/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        PromotionForm form = new PromotionForm();
        // Tự động sinh mã khuyến mãi
        String autoCode = promotionService.generateNextCode();
        form.setCode(autoCode);

        model.addAttribute("form", form);
        model.addAttribute("pageTitle", "Thêm khuyến mãi mới");
        return "promotion/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("form") PromotionForm form,
                         BindingResult br,
                         RedirectAttributes ra,
                         Model model) {

        validateDateRange(form, br);
        validateCodeAndNameUnique(form, br);

        // Validate percent range (already in annotations, but double check)
        if (form.getPercent() != null &&
                (form.getPercent().compareTo(java.math.BigDecimal.ZERO) <= 0 ||
                        form.getPercent().compareTo(new java.math.BigDecimal("100")) > 0)) {
            br.rejectValue("percent", "percent.range", "Phần trăm phải từ 0.01% đến 100%.");
        }

        if (br.hasErrors()) {
            model.addAttribute("form", form);
            model.addAttribute("pageTitle", "Thêm khuyến mãi mới");
            return "promotion/form";
        }

        try {
            promotionService.save(form.toEntity());
            ra.addFlashAttribute("msg", "Đã thêm khuyến mãi mới thành công!");
            return "redirect:/promotions";
        } catch (IllegalArgumentException | IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("form", form);
            model.addAttribute("pageTitle", "Thêm khuyến mãi mới");
            return "promotion/form";
        }
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        Optional<Promotion> opt = promotionService.findById(id);
        if (opt.isEmpty()) {
            ra.addFlashAttribute("error", "Không tìm thấy khuyến mãi!");
            return "redirect:/promotions";
        }

        model.addAttribute("form", PromotionForm.fromEntity(opt.get()));
        model.addAttribute("promotionId", id);
        model.addAttribute("pageTitle", "Chỉnh sửa khuyến mãi");
        return "promotion/form";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id,
                         @Valid @ModelAttribute("form") PromotionForm form,
                         BindingResult br,
                         RedirectAttributes ra,
                         Model model) {

        form.setId(id);
        validateDateRange(form, br);
        validateCodeAndNameUnique(form, br);
        validateStatusForEdit(id, form, br);

        // Validate percent range
        if (form.getPercent() != null &&
                (form.getPercent().compareTo(java.math.BigDecimal.ZERO) <= 0 ||
                        form.getPercent().compareTo(new java.math.BigDecimal("100")) > 0)) {
            br.rejectValue("percent", "percent.range", "Phần trăm phải từ 0.01% đến 100%.");
        }

        if (br.hasErrors()) {
            model.addAttribute("form", form);
            model.addAttribute("promotionId", id);
            model.addAttribute("pageTitle", "Chỉnh sửa khuyến mãi");
            return "promotion/form";
        }

        try {
            promotionService.update(id, form.toEntity());
            ra.addFlashAttribute("msg", "Đã cập nhật khuyến mãi thành công!");
            return "redirect:/promotions";
        } catch (IllegalArgumentException | IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("form", form);
            model.addAttribute("promotionId", id);
            model.addAttribute("pageTitle", "Chỉnh sửa khuyến mãi");
            return "promotion/form";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            boolean deleted = promotionService.deleteById(id);
            if (deleted) {
                ra.addFlashAttribute("msg", "Đã xóa khuyến mãi!");
            } else {
                ra.addFlashAttribute("error", "Không tìm thấy khuyến mãi cần xóa!");
            }
        } catch (IllegalStateException e) {
            ra.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/promotions";
    }
}
