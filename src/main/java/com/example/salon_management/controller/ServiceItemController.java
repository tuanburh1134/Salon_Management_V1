package com.example.salon_management.controller;

import com.example.salon_management.dto.ServiceItemRequest;
import com.example.salon_management.dto.ServiceSearchRequest;
import com.example.salon_management.service.ServiceItemService;
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
import java.util.Set; // NEW

@Controller
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServiceItemController {

    private final ServiceItemService service;

    // NEW: whitelist các cột được phép sort
    private static final Set<String> ALLOWED_SORTS = Set.of("id", "name", "type", "price", "createdAt"); // NEW

    /** Trim toàn bộ chuỗi input để tránh lỗi nhập có khoảng trắng */
    @InitBinder
    void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override public void setAsText(String text) {
                setValue(text == null ? null : text.trim());
            }
        });
    }

    // ======================= LIST =======================
    @GetMapping({"", "/", "/list"})
    public String list(@ModelAttribute("q") ServiceSearchRequest q,
                       Model model,
                       @RequestParam(value = "ok", required = false) String ok) {

        normalize(q); // đảm bảo sort, dir, page, size hợp lệ

        // NEW: tôn trọng q.sortBy + q.dir nhưng chỉ với các field hợp lệ
        String sortBy = safeSortBy(q.getSortBy()); // NEW
        Sort.Direction direction = "asc".equalsIgnoreCase(q.getDir())    // NEW
                ? Sort.Direction.ASC : Sort.Direction.DESC;              // NEW

        Sort sort = Sort.by(new Sort.Order(direction, sortBy).nullsLast()) // NEW
                .and(Sort.by(Sort.Order.desc("id")));               // NEW
        Pageable pageable = PageRequest.of(q.getPage(), q.getSize(), sort);

        model.addAttribute("page", service.search(q.getKeyword(), q.getType(), pageable));
        model.addAttribute("q", q);

        // ===== Thông báo thành công (toast) =====
        if (ok != null) {
            switch (ok) {
                case "created" -> {
                    model.addAttribute("successTitle", "Tạo thành công");
                    model.addAttribute("successMessage", "Dịch vụ mới đã được thêm thành công!");
                }
                case "updated" -> {
                    model.addAttribute("successTitle", "Cập nhật thành công");
                    model.addAttribute("successMessage", "Dịch vụ đã được chỉnh sửa thành công!");
                }
                case "deleted" -> {
                    model.addAttribute("successTitle", "Xóa thành công");
                    model.addAttribute("successMessage", "Dịch vụ đã được xóa thành công!");
                }
            }
        }

        // ==================== URL sort & icon cho tiêu đề cột ====================
        String nextDir = "asc".equalsIgnoreCase(q.getDir()) ? "desc" : "asc";

        model.addAttribute("sortByNameUrl",       buildListUrl(q, 0, q.getSize(), "name",       nextDir));
        model.addAttribute("sortByTypeUrl",       buildListUrl(q, 0, q.getSize(), "type",       nextDir));
        model.addAttribute("sortByPriceUrl",      buildListUrl(q, 0, q.getSize(), "price",      nextDir));
        model.addAttribute("sortByCreatedAtUrl",  buildListUrl(q, 0, q.getSize(), "createdAt",  nextDir));

        model.addAttribute("nameIcon",       icon(q.getSortBy(), q.getDir(), "name"));
        model.addAttribute("typeIcon",       icon(q.getSortBy(), q.getDir(), "type"));
        model.addAttribute("priceIcon",      icon(q.getSortBy(), q.getDir(), "price"));
        model.addAttribute("createdAtIcon",  icon(q.getSortBy(), q.getDir(), "createdAt"));
        // ========================================================================

        return "serviceitem/list";
    }

    // ======================= CREATE =======================
    @GetMapping("/create")
    public String createForm(Model model) {
        prepareForm(model,
                new ServiceItemRequest(),
                "Thêm dịch vụ",
                "/services/create",
                "Lưu");
        return "serviceitem/form";
    }

    @PostMapping("/create")
    public String createSubmit(@Valid @ModelAttribute("form") ServiceItemRequest form,
                               BindingResult br,
                               Model model,
                               RedirectAttributes ra) {
        if (br.hasErrors()) {
            prepareForm(model, form, "Thêm dịch vụ", "/services/create", "Lưu");
            return "serviceitem/form";
        }
        service.create(form);
        return "redirect:/services?ok=created";
    }

    // ======================= EDIT =======================
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        var s = service.findById(id).orElseThrow();
        prepareForm(model,
                ServiceItemRequest.from(s),
                "Cập nhật dịch vụ",
                "/services/" + id + "/edit",
                "Cập nhật");
        model.addAttribute("id", id);
        return "serviceitem/form";
    }

    @PostMapping("/{id}/edit")
    public String editSubmit(@PathVariable Long id,
                             @Valid @ModelAttribute("form") ServiceItemRequest form,
                             BindingResult br,
                             Model model) {
        if (br.hasErrors()) {
            prepareForm(model, form, "Cập nhật dịch vụ", "/services/" + id + "/edit", "Cập nhật");
            model.addAttribute("id", id);
            return "serviceitem/form";
        }
        service.update(id, form);
        return "redirect:/services?ok=updated";
    }

    // ======================= DELETE =======================
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/services?ok=deleted";
    }

    // ======================= HELPERS =======================
    private void prepareForm(Model model,
                             ServiceItemRequest form,
                             String title,
                             String actionUrl,
                             String submitLabel) {
        model.addAttribute("form", form);
        model.addAttribute("title", title);
        model.addAttribute("actionUrl", actionUrl);
        model.addAttribute("submitLabel", submitLabel);
    }

    /**
     * ✅ Sắp xếp mặc định: mới nhất ở đầu (createdAt DESC)
     */
    private void normalize(ServiceSearchRequest q) {
        if (q.getSortBy() == null || q.getSortBy().isBlank()) q.setSortBy("createdAt");
        if (q.getDir() == null || q.getDir().isBlank()) q.setDir("desc"); // mặc định mới nhất
        if (q.getPage() < 0) q.setPage(0);
        if (q.getSize() <= 0 || q.getSize() > 100) q.setSize(10);
    }

    // NEW: chỉ cho phép sort theo cột hợp lệ, tránh lỗi No property 'xxx'
    private String safeSortBy(String sortBy) {
        return (sortBy != null && ALLOWED_SORTS.contains(sortBy)) ? sortBy : "createdAt";
    }

    // ======================= URL & icon =======================
    private String buildListUrl(ServiceSearchRequest q, int page, int size, String sortBy, String dir) {
        String kw = (q.getKeyword() == null || q.getKeyword().isBlank())
                ? "" : q.getKeyword().trim().replace(" ", "%20");
        String type = (q.getType() == null || q.getType().isBlank()) ? "" : q.getType();

        return "/services"
                + "?keyword=" + kw
                + "&type=" + type
                + "&page=" + page
                + "&size=" + size
                + "&sortBy=" + sortBy
                + "&dir=" + dir;
    }

    private String icon(String currentSortBy, String dir, String column) {
        if (currentSortBy == null || !column.equals(currentSortBy)) return "";
        return "asc".equalsIgnoreCase(dir) ? "↑" : "↓";
    }
}
