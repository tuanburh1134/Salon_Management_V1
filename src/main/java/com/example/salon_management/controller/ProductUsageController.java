package com.example.salon_management.controller;

import com.example.salon_management.dto.ProductUsageForm;
import com.example.salon_management.entity.ProductUsage;
import com.example.salon_management.service.ProductUsageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/productusage")
public class ProductUsageController {

    private final ProductUsageService service;

    // Chỉ cho phép sort theo các cột này để tránh lỗi/tiêm tham số
    private static final Set<String> ALLOWED_SORTS =
            Set.of("serviceCode", "productName", "quantityUsed", "price", "createdAt", "id");

    @GetMapping({"", "/", "/list"})
    public String list(@RequestParam(value = "q", required = false) String q,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       @RequestParam(defaultValue = "createdAt") String sortBy,
                       @RequestParam(defaultValue = "desc") String dir,
                       Model model) {

        // Chuẩn hoá tham số
        sortBy = safeSortBy(sortBy);
        dir = ("asc".equalsIgnoreCase(dir)) ? "asc" : "desc";
        if (page < 0) page = 0;
        if (size <= 0 || size > 100) size = 10;

        Sort sort = "asc".equalsIgnoreCase(dir)
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // Thêm tie-breaker theo id để danh sách ổn định
        if (!"id".equals(sortBy)) {
            sort = sort.and(Sort.by(Sort.Order.desc("id")));
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ProductUsage> data = service.search(q, pageable);

        String nextDir = "asc".equalsIgnoreCase(dir) ? "desc" : "asc";

        model.addAttribute("sortByServiceCodeUrl", buildListUrl(q, 0, size, "serviceCode", nextDir));
        model.addAttribute("sortByProductNameUrl", buildListUrl(q, 0, size, "productName", nextDir));
        model.addAttribute("sortByQuantityUrl",    buildListUrl(q, 0, size, "quantityUsed", nextDir));
        model.addAttribute("sortByPriceUrl",       buildListUrl(q, 0, size, "price", nextDir));

        model.addAttribute("serviceCodeIcon", icon(sortBy, dir, "serviceCode"));
        model.addAttribute("productNameIcon", icon(sortBy, dir, "productName"));
        model.addAttribute("quantityIcon",    icon(sortBy, dir, "quantityUsed"));
        model.addAttribute("priceIcon",       icon(sortBy, dir, "price"));

        model.addAttribute("data", data);
        model.addAttribute("q", q == null ? "" : q);
        model.addAttribute("size", size);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("dir", dir);

        return "productusage/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("form", new ProductUsageForm());
        model.addAttribute("pageTitle", "Thêm sản phẩm sử dụng");
        model.addAttribute("formAction", "/productusage/create");
        model.addAttribute("submitLabel", "Lưu mới");
        model.addAttribute("isEdit", false);
        return "productusage/form";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("form") ProductUsageForm form,
                         BindingResult br,
                         RedirectAttributes ra,
                         Model model) {
        if (br.hasErrors()) {
            model.addAttribute("pageTitle", "Thêm sản phẩm sử dụng");
            model.addAttribute("formAction", "/productusage/create");
            model.addAttribute("submitLabel", "Lưu mới");
            model.addAttribute("isEdit", false);
            return "productusage/form";
        }

        try {
            if (form.getQuantityUsed() == null) form.setQuantityUsed(0);
            if (form.getPrice() == null) form.setPrice(BigDecimal.ZERO);

            service.create(form);
            ra.addFlashAttribute("successTitle", "Tạo thành công");
            ra.addFlashAttribute("successMessage", "Bạn đã tạo mới thành công.");
            return "redirect:/productusage";
        } catch (DataIntegrityViolationException ex) {
            model.addAttribute("dbError", "Dữ liệu không hợp lệ: " + ex.getMostSpecificCause().getMessage());
            model.addAttribute("pageTitle", "Thêm sản phẩm sử dụng");
            model.addAttribute("formAction", "/productusage/create");
            model.addAttribute("submitLabel", "Lưu mới");
            model.addAttribute("isEdit", false);
            return "productusage/form";
        } catch (Exception ex) {
            model.addAttribute("dbError", "Có lỗi xảy ra. Vui lòng thử lại.");
            model.addAttribute("pageTitle", "Thêm sản phẩm sử dụng");
            model.addAttribute("formAction", "/productusage/create");
            model.addAttribute("submitLabel", "Lưu mới");
            model.addAttribute("isEdit", false);
            return "productusage/form";
        }
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        ProductUsage p = service.get(id);
        ProductUsageForm f = new ProductUsageForm();
        f.setServiceCode(p.getServiceCode());
        f.setProductName(p.getProductName());
        f.setQuantityUsed(p.getQuantityUsed());
        f.setPrice(p.getPrice());

        model.addAttribute("form", f);
        model.addAttribute("pageTitle", "Chỉnh sửa sản phẩm sử dụng");
        model.addAttribute("formAction", "/productusage/" + id + "/edit");
        model.addAttribute("submitLabel", "Cập nhật");
        model.addAttribute("isEdit", true);
        return "productusage/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("form") ProductUsageForm form,
                         BindingResult br,
                         RedirectAttributes ra,
                         Model model) {
        if (br.hasErrors()) {
            model.addAttribute("pageTitle", "Chỉnh sửa sản phẩm sử dụng");
            model.addAttribute("formAction", "/productusage/" + id + "/edit");
            model.addAttribute("submitLabel", "Cập nhật");
            model.addAttribute("isEdit", true);
            return "productusage/form";
        }

        try {
            if (form.getQuantityUsed() == null) form.setQuantityUsed(0);
            if (form.getPrice() == null) form.setPrice(BigDecimal.ZERO);

            service.update(id, form);
            ra.addFlashAttribute("successTitle", "Sửa thành công");
            ra.addFlashAttribute("successMessage", "Bạn đã cập nhật thành công.");
            return "redirect:/productusage";
        } catch (DataIntegrityViolationException ex) {
            model.addAttribute("dbError", "Dữ liệu không hợp lệ: " + ex.getMostSpecificCause().getMessage());
            model.addAttribute("pageTitle", "Chỉnh sửa sản phẩm sử dụng");
            model.addAttribute("formAction", "/productusage/" + id + "/edit");
            model.addAttribute("submitLabel", "Cập nhật");
            model.addAttribute("isEdit", true);
            return "productusage/form";
        } catch (Exception ex) {
            model.addAttribute("dbError", "Có lỗi xảy ra. Vui lòng thử lại.");
            model.addAttribute("pageTitle", "Chỉnh sửa sản phẩm sử dụng");
            model.addAttribute("formAction", "/productusage/" + id + "/edit");
            model.addAttribute("submitLabel", "Cập nhật");
            model.addAttribute("isEdit", true);
            return "productusage/form";
        }
    }

    // DELETE GIỮ TRẠNG THÁI TÌM KIẾM/SẮP XẾP/PHÂN TRANG
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id,
                         @RequestParam(value = "q", required = false) String q,
                         @RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "10") int size,
                         @RequestParam(defaultValue = "createdAt") String sortBy,
                         @RequestParam(defaultValue = "desc") String dir,
                         RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("successTitle", "Xoá thành công");
            ra.addFlashAttribute("successMessage", "Bạn đã xoá thành công.");

            // Chuẩn hoá lại rồi quay về đúng URL đang đứng
            sortBy = safeSortBy(sortBy);
            dir = ("asc".equalsIgnoreCase(dir)) ? "asc" : "desc";
            if (page < 0) page = 0;
            if (size <= 0 || size > 100) size = 10;

            String backUrl = buildListUrl(q, page, size, sortBy, dir);
            return "redirect:" + backUrl;
        } catch (Exception ex) {
            ra.addFlashAttribute("successTitle", "Không thể xoá");
            ra.addFlashAttribute("successMessage", "Có lỗi xảy ra: " + ex.getMessage());
            return "redirect:/productusage";
        }
    }

    // ================== Helpers ==================
    private String buildListUrl(String q, int page, int size, String sortBy, String dir) {
        String query = (q == null || q.isBlank()) ? "" : URLEncoder.encode(q.trim(), StandardCharsets.UTF_8);
        return "/productusage?q=" + query +
                "&page=" + page +
                "&size=" + size +
                "&sortBy=" + sortBy +
                "&dir=" + dir;
    }

    private String icon(String currentSortBy, String dir, String column) {
        if (!column.equals(currentSortBy)) return "";
        return "asc".equalsIgnoreCase(dir) ? "↑" : "↓";
    }

    private String safeSortBy(String sortBy) {
        return (sortBy != null && ALLOWED_SORTS.contains(sortBy)) ? sortBy : "createdAt";
    }
}
