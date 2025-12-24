package com.bty.datsachpj.datsachpjadmin_btvn.controller;

import com.bty.datsachpj.datsachpjadmin_btvn.entity.Category;
import com.bty.datsachpj.datsachpjadmin_btvn.exception.CategoryCreationException;
import com.bty.datsachpj.datsachpjadmin_btvn.exception.CategoryDeletionException;
import com.bty.datsachpj.datsachpjadmin_btvn.exception.CategoryNotFoundException;
import com.bty.datsachpj.datsachpjadmin_btvn.exception.CategoryUpdateException;
import com.bty.datsachpj.datsachpjadmin_btvn.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("currentPageNumber", pageNumber);
        return "categories";
    }

    @GetMapping("/categories/update/{id}")
    public String showUpdate(@PathVariable(name = "id") String id, Model model) {
        try {
            Category category = categoryService.getCategoryById(id);
            model.addAttribute("category", category);
        } catch (CategoryNotFoundException e) {
            model.addAttribute("msg", e.getMessage());
        } catch (Exception e) {
            throw e;
        }
        model.addAttribute("mode", "update");
        return "category-form";
    }

    @GetMapping("/categories/create")
    public String showCreate(Model model) {
        model.addAttribute("mode", "create");
        Category category = Category.builder().id(null).name(null).build();
        model.addAttribute("category", category);
        return "category-form";
    }

    @PostMapping("/categories/save")
    public String save(@Valid @ModelAttribute("category") Category category,
                       BindingResult bindingResult,
                       @RequestParam(name = "mode", required = true) String mode,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("mode", mode);
            return "category-form";
        }

        if(mode.equals("update")) {
            try {
                categoryService.updateCategory(category);
                redirectAttributes.addFlashAttribute("updateSuccessMsg", "Đã cập nhật thành công");
                return "redirect:/categories";
            } catch (CategoryUpdateException e) {
                model.addAttribute("updateErrorMsg", e.getMessage());
                model.addAttribute("mode", "update");
                return "category-form";
            } catch (Exception e) {
                throw e;
            }
        } else if(mode.equals("create")) {
            try {
                categoryService.createCategory(category);

                redirectAttributes.addFlashAttribute("CreationSuccessMsg", "Đã tạo thành công");
                return "redirect:/categories";
            } catch (CategoryCreationException e) {
                model.addAttribute("mode", "create");
                model.addAttribute("creationErrorMsg", e.getMessage());
                return "category-form";
            } catch (Exception e) {
                throw e;
            }
        } else {
            return "redirect:/categories";
        }

    }

    @GetMapping("/categories/delete/{id}")
    public String delete(@PathVariable(name = "id") String id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.deleteCategoryById(id);
            redirectAttributes.addFlashAttribute("deletionSuccessMsg", "Đã xóa thành công");
        } catch (CategoryDeletionException e) {
            redirectAttributes.addFlashAttribute("deletionErrorMsg", e.getMessage());
        } catch (Exception e) {
            throw e;
        }
        return "redirect:/categories";
    }

    @GetMapping("/categories/statistics")
    public String statistics(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "statistics-categories";
    }
}
