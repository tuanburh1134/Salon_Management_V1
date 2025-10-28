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
import java.net.URLEncoder;                         // <-- NEW
import java.nio.charset.StandardCharsets;          // <-- NEW
import java.util.Set;

@Controller
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServiceItemController {

    private final ServiceItemService service;

    private static final Set<String> ALLOWED_SORTS = Set.of("id", "name", "type", "price", "createdAt");

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

        normalize(q);

        String sortBy = safeSortBy(q.getSortBy());
        Sort.Direction direction = "asc".equalsIgnoreCase(q.getDir()) ? Sort.Direction.ASC : Sort.Direction.DESC;

        Sort sort = Sort.by(new Sort.Order(direction, sortBy).nullsLast())
                .and(Sort.by(Sort.Order.desc("id")));
        Pageable pageable = PageRequest.of(q.getPage(), q.getSize(), sort);

        model.addAttribute("page", service.search(q.getKeyword(), q.getType(), pageable));
        model.addAttribute("q", q);

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

        String nextDir = "asc".equalsIgnoreCase(q.getDir()) ? "desc" : "asc";
        model.addAttribute("sortByNameUrl",      buildListUrl(q, 0, q.getSize(), "name",      nextDir));
        model.addAttribute("sortByTypeUrl",      buildListUrl(q, 0, q.getSize(), "type",      nextDir));
        model.addAttribute("sortByPriceUrl",     buildListUrl(q, 0, q.getSize(), "price",     nextDir));
        model.addAttribute("sortByCreatedAtUrl", buildListUrl(q, 0, q.getSize(), "createdAt", nextDir));

        model.addAttribute("nameIcon",      icon(q.getSortBy(), q.getDir(), "name"));
        model.addAttribute("typeIcon",      icon(q.getSortBy(), q.getDir(), "type"));
        model.addAttribute("priceIcon",     icon(q.getSortBy(), q.getDir(), "price"));
        model.addAttribute("createdAtIcon", icon(q.getSortBy(), q.getDir(), "createdAt"));

        return "serviceitem/list";
    }

    // ======================= CREATE =======================
    @GetMapping("/create")
    public String createForm(Model model) {
        prepareForm(model, new ServiceItemRequest(), "Thêm dịch vụ", "/services/create", "Lưu");
        return "serviceitem/form";
    }

    @PostMapping("/create")
    public String createSubmit(@Valid @ModelAttribute("form") ServiceItemRequest form,
                               BindingResult br,
                               Model model) {
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
        prepareForm(model, ServiceItemRequest.from(s), "Cập nhật dịch vụ", "/services/" + id + "/edit", "Cập nhật");
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
    public String delete(@PathVariable Long id,
                         // nhận lại trạng thái hiện tại để redirect chính xác
                         @RequestParam(required = false) String keyword,
                         @RequestParam(required = false) String type,
                         @RequestParam(defaultValue = "0")   int page,
                         @RequestParam(defaultValue = "10")  int size,
                         @RequestParam(defaultValue = "createdAt") String sortBy,
                         @RequestParam(defaultValue = "desc")     String dir) {

        service.delete(id);

        // dựng lại ServiceSearchRequest tạm để tái sử dụng buildListUrl()
        ServiceSearchRequest back = new ServiceSearchRequest();
        back.setKeyword(keyword);
        back.setType(type);
        back.setPage(page);
        back.setSize(size);
        back.setSortBy(safeSortBy(sortBy));
        back.setDir(dir);

        String url = buildListUrl(back, back.getPage(), back.getSize(), back.getSortBy(), back.getDir());
        return "redirect:" + url + "&ok=deleted";
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

    private void normalize(ServiceSearchRequest q) {
        if (q.getSortBy() == null || q.getSortBy().isBlank()) q.setSortBy("createdAt");
        if (q.getDir() == null || q.getDir().isBlank()) q.setDir("desc");
        if (q.getPage() < 0) q.setPage(0);
        if (q.getSize() <= 0 || q.getSize() > 100) q.setSize(10);
    }

    private String safeSortBy(String sortBy) {
        return (sortBy != null && ALLOWED_SORTS.contains(sortBy)) ? sortBy : "createdAt";
    }

    private String buildListUrl(ServiceSearchRequest q, int page, int size, String sortBy, String dir) {
        String kw   = q.getKeyword() == null ? "" : URLEncoder.encode(q.getKeyword(), StandardCharsets.UTF_8);
        String type = q.getType()    == null ? "" : URLEncoder.encode(q.getType(),    StandardCharsets.UTF_8);

        return "/services"
                + "?keyword=" + kw
                + "&type="   + type
                + "&page="   + page
                + "&size="   + size
                + "&sortBy=" + sortBy
                + "&dir="    + dir;
    }

    private String icon(String currentSortBy, String dir, String column) {
        if (currentSortBy == null || !column.equals(currentSortBy)) return "";
        return "asc".equalsIgnoreCase(dir) ? "↑" : "↓";
    }
}
