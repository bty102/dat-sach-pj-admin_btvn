package com.bty.datsachpj.datsachpjadmin_btvn.controller;

import com.bty.datsachpj.datsachpjadmin_btvn.entity.Book;
import com.bty.datsachpj.datsachpjadmin_btvn.service.BookService;
import com.bty.datsachpj.datsachpjadmin_btvn.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookController {

    final BookService bookService;
    final CategoryService categoryService;

    @GetMapping("/books")
    public String getBooks(@RequestParam(name = "cateId", required = false) String cateId,
                           @RequestParam(name = "key", required = false, defaultValue = "") String key,
                           @RequestParam(name = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                           Model model) {
        int pageSize = 6;
        Page<Book> books = null;
        if(key.isEmpty() && cateId == null) {
            books = bookService.getBooks(pageNumber, pageSize);
        } else if (!key.isEmpty()){
            books = bookService.findByNameContainingIgnoreCaseOrAuthorContainingIgnoreCase(key, pageNumber, pageSize);
            model.addAttribute("key", key);
        } else {
            books = bookService.findBooksByCategoryId(cateId, pageNumber, pageSize);
            model.addAttribute("cateId", cateId);
        }
        model.addAttribute("books", books.getContent());
        int totalPages = books.getTotalPages(); // tong so trang
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "books";
    }
}
