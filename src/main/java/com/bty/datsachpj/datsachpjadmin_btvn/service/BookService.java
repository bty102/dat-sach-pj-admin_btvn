package com.bty.datsachpj.datsachpjadmin_btvn.service;

import com.bty.datsachpj.datsachpjadmin_btvn.entity.Book;
import com.bty.datsachpj.datsachpjadmin_btvn.repository.BookRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookService {

    final BookRepository bookRepository;

    /*
        Tham số:
            - pageNumber >= 1
            - pageSize >= 1
    */
    public Page<Book> getBooks(int pageNumber, int pageSize) {
        if(pageNumber < 1) {
            pageNumber = 1;
        }
        if(pageSize < 1) {
            pageSize = 1;
        }
        Sort sort = Sort.by("name").ascending();
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, sort);
        return bookRepository.findAll(pageable);
    }

    /*
        Tham số:
            - pageNumber >= 1
            - pageSize >= 1
    */
    public Page<Book> findByNameContainingIgnoreCaseOrAuthorContainingIgnoreCase(String key, int pageNumber, int pageSize) {
        if(pageNumber < 1) {
            pageNumber = 1;
        }
        if(pageSize <1) {
            pageSize = 1;
        }
        Sort sort = Sort.by("name").ascending();
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, sort);
        return bookRepository.findByNameContainingIgnoreCaseOrAuthorContainingIgnoreCase(key, key, pageable);
    }

    /*
        Tham số:
            - pageNumber >= 1
            - pageSize >= 1
    */
    public Page<Book> findBooksByCategoryId(String categoryId, int pageNumber, int pageSize) {

        if(pageNumber < 1) {
            pageNumber = 1;
        }
        if(pageSize <1) {
            pageSize = 1;
        }

        Sort sort = Sort.by("name").ascending();
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, sort);
        return bookRepository.findByCategory_Id(categoryId, pageable);
    }
}
