package com.bty.datsachpj.datsachpjadmin_btvn.controller;

import com.bty.datsachpj.datsachpjadmin_btvn.entity.Category;
import com.bty.datsachpj.datsachpjadmin_btvn.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryController {

    final CategoryService categoryService;

    @GetMapping("/categories")
    public String getCategories(@RequestParam(name = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                @RequestParam(name = "key", required = false, defaultValue = "") String key,
                                Model model) {
        int pageSize = 5;
        Page<Category> categories = null;
        if(key.isEmpty()) {
            categories = categoryService.getCategories(pageNumber, pageSize);
        } else {
            categories = categoryService.findCategoriesByNameContainingIgnoreCase(key, pageNumber, pageSize);
            model.addAttribute("key", key);
        }
        int totalPages = categories.getTotalPages(); // Tong so trang
        model.addAttribute("categories", categories.getContent());
        model.addAttribute("totalPages", totalPages);
        return "categories";
    }
}
