package com.bty.datsachpj.datsachpjadmin_btvn.service;

import com.bty.datsachpj.datsachpjadmin_btvn.entity.Category;
import com.bty.datsachpj.datsachpjadmin_btvn.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
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
}
