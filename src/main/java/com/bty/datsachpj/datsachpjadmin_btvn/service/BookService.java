package com.bty.datsachpj.datsachpjadmin_btvn.service;

import com.bty.datsachpj.datsachpjadmin_btvn.entity.Book;
import com.bty.datsachpj.datsachpjadmin_btvn.exception.BookCreationException;
import com.bty.datsachpj.datsachpjadmin_btvn.exception.BookDeletionException;
import com.bty.datsachpj.datsachpjadmin_btvn.exception.BookNotFoundException;
import com.bty.datsachpj.datsachpjadmin_btvn.exception.BookUpdateException;
import com.bty.datsachpj.datsachpjadmin_btvn.repository.BookRepository;
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

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
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

    public Book getBookById(String id) {
        Book book = bookRepository.findById(id).
                orElseThrow(() -> {throw new BookNotFoundException("Không có sách với mã sách như vậy");
                });
        return book;
    }

    public void updateBook(@Valid Book book) {
        Book fbook = bookRepository.findById(book.getId())
                .orElseThrow(() -> {throw new BookUpdateException("không thể cập nhật vì tồn tại sách có mã sách như vậy");
                });
        if(!fbook.getImportDate().equals(book.getImportDate())) {
            throw new BookUpdateException("Cập nhật thất bại");
        }
        bookRepository.save(book);
    }

    public void createBook(@Valid Book book) {
        if(bookRepository.existsById(book.getId())) {
            throw new BookCreationException("Không thể tạo mới vì mã sách đã tồn tại");
        }
        LocalDateTime now = LocalDateTime.now();
        book.setImportDate(now);
        bookRepository.save(book);
    }

    public void deleteBookById(String id) {
        if(!bookRepository.existsById(id)) {
            throw new BookDeletionException("không thể xóa sách vì mã sách không tồn tại");
        }
        bookRepository.deleteById(id);
    }

    public List<Book> top10BestSellingBooks() {
        Pageable pageable = PageRequest.of(0, 10);
        return bookRepository.findTopBooksByBillDetailCount(pageable).getContent();
    }
}
