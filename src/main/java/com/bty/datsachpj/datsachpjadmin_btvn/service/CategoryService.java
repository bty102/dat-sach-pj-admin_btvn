package com.bty.datsachpj.datsachpjadmin_btvn.service;

import com.bty.datsachpj.datsachpjadmin_btvn.entity.Category;
import com.bty.datsachpj.datsachpjadmin_btvn.exception.CategoryCreationException;
import com.bty.datsachpj.datsachpjadmin_btvn.exception.CategoryDeletionException;
import com.bty.datsachpj.datsachpjadmin_btvn.exception.CategoryNotFoundException;
import com.bty.datsachpj.datsachpjadmin_btvn.exception.CategoryUpdateException;
import com.bty.datsachpj.datsachpjadmin_btvn.repository.CategoryRepository;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
public class CategoryService {

    final CategoryRepository categoryRepository;

    /*
        Tham số:
            - pageNumber >= 1
            - pageSize >= 1
    */
    public Page<Category> getCategories(int pageNumber, int pageSize) {
        if(pageNumber < 1) {
            pageNumber = 1;
        }
        if(pageSize < 1) {
            pageSize = 1;
        }

        Sort sort = Sort.by("name").ascending();
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, sort);
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories;
    }

    /*
        Tham số:
            - pageNumber >= 1
            - pageSize >= 1
    */
    public Page<Category> findCategoriesByNameContainingIgnoreCase(String key, int pageNumber, int pageSize) {
        if(pageNumber < 1) pageNumber = 1;
        if(pageSize < 1) pageSize = 1;

        Sort sort = Sort.by("name").ascending();
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, sort);
        Page<Category> categories = categoryRepository.findByNameContainingIgnoreCase(key, pageable);
        return categories;
    }

    public Category getCategoryById(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> {throw new CategoryNotFoundException("Không tồn tại loại sách nào có mã như vậy!");
        });
        return category;
    }

    public void updateCategory(@Valid Category category) {
        if(!categoryRepository.existsById(category.getId())) {
            throw new CategoryUpdateException("Không thể cập nhật vì không tồn lại loại sách này");
        }
        categoryRepository.save(category);
    }

    public void createCategory(@Valid Category category) {
        if(categoryRepository.existsById(category.getId())) {
            throw new CategoryCreationException("không thể tạo mới loại sách vì mã loại đã tồn tại");
        }
        categoryRepository.save(category);
    }

    public void deleteCategoryById(String id) {
        if(!categoryRepository.existsById(id)) {
            throw new CategoryDeletionException("Không thể xóa vì không tồn tại loại sách có mã loại như vậy");
        }
        categoryRepository.deleteById(id);
    }
}
