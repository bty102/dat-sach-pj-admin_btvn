package com.bty.datsachpj.datsachpjadmin_btvn.controller;

import com.bty.datsachpj.datsachpjadmin_btvn.entity.Book;
import com.bty.datsachpj.datsachpjadmin_btvn.exception.BookCreationException;
import com.bty.datsachpj.datsachpjadmin_btvn.exception.BookDeletionException;
import com.bty.datsachpj.datsachpjadmin_btvn.exception.BookNotFoundException;
import com.bty.datsachpj.datsachpjadmin_btvn.exception.BookUpdateException;
import com.bty.datsachpj.datsachpjadmin_btvn.infrastructure.FileUtil;
import com.bty.datsachpj.datsachpjadmin_btvn.service.BookService;
import com.bty.datsachpj.datsachpjadmin_btvn.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookController {

    final BookService bookService;
    final CategoryService categoryService;

    @Value("${image.book.location}")
    private String IMAGE_BOOK_LOCATION;

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

    @GetMapping("/books/update/{id}")
    public String showUpdate(@PathVariable(name = "id") String id, Model model) {
        try {
            Book book = bookService.getBookById(id);
            model.addAttribute("book", book);
            model.addAttribute("mode", "update");
            model.addAttribute("categories", categoryService.getAllCategories());
        } catch (BookNotFoundException e) {
            model.addAttribute("bookNotFoundMsg", e.getMessage());
        }
        return "book-form";
    }

    @GetMapping("/books/create")
    public String showCreate(Model model) {
        Book book = Book.builder()
                .id(null)
                .name(null)
                .quantity(null)
                .price(null)
                .numberOfVolumes(null)
                .imagePath(null)
                .importDate(null)
                .author(null)
                .build();
        model.addAttribute("book", book);
        model.addAttribute("mode", "create");
        model.addAttribute("categories", categoryService.getAllCategories());
        return "book-form";
    }

    @PostMapping("/books/save")
    public String save(@Valid @ModelAttribute("book") Book book,
                       BindingResult bindingResult,
                       @RequestParam("mode") String mode,
                       @RequestParam(name = "image", required = true) MultipartFile file,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("mode", mode);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "book-form";
        }

        FileUtil fileUtil = new FileUtil();
        if(mode.equals("update")) {
            if(!file.isEmpty()) {
                try {
                    Path path = fileUtil.saveImageFile(file, IMAGE_BOOK_LOCATION);
                    String fileName = path.getFileName().toString();
                    book.setImagePath("image_sach/" + fileName);
                } catch (Exception e) {
                    redirectAttributes.addFlashAttribute("updateUnsuccessMsg", e.getMessage());
                    return "redirect:/books";
                }
            }
            try {
                bookService.updateBook(book);
                redirectAttributes.addFlashAttribute("updateSuccessMsg", "Đã cập nhật thành công");
                return "redirect:/books";
            } catch (BookUpdateException e) {
                model.addAttribute("mode", mode);
                model.addAttribute("updateErrorMsg", e.getMessage());
                model.addAttribute("categories", categoryService.getAllCategories());
                return "book-form";
            } catch (Exception e) {
                throw e;
            }
        } else if(mode.equals("create")) {
            if(!file.isEmpty()) {
                try {
                    Path path = fileUtil.saveImageFile(file, IMAGE_BOOK_LOCATION);
                    String fileName = path.getFileName().toString();
                    book.setImagePath("image_sach/" + fileName);
                    System.out.println(fileName);
                } catch (Exception e) {
                    redirectAttributes.addFlashAttribute("creationUnsuccessMsg", e.getMessage());
                    return "redirect:/books";
                }
            }

            try {
                bookService.createBook(book);
                redirectAttributes.addFlashAttribute("creationSuccessMsg", "Đã tạo mới thành công");
                return "redirect:/books";
            } catch (BookCreationException e) {
                model.addAttribute("mode", "create");
                model.addAttribute("categories", categoryService.getAllCategories());
                model.addAttribute("createErrorMsg", e.getMessage());
                return "book-form";
            } catch (Exception e) {
                throw e;
            }
        } else {
            return "redirect:/books";
        }

    }

    @GetMapping("/books/delete/{id}")
    public String detele(@PathVariable(name = "id") String id,
                         RedirectAttributes redirectAttributes) throws Exception {

        try {
            Book book = bookService.getBookById(id);
            bookService.deleteBookById(id);
            String fileName = Paths.get(book.getImagePath()).getFileName().toString();
            String filePath = IMAGE_BOOK_LOCATION + fileName;
            FileUtil fileUtil = new FileUtil();
            fileUtil.deleteFile(filePath);
            redirectAttributes.addFlashAttribute("deletionSucessMsg", "Đã xóa thành công");
        } catch (BookNotFoundException e) {
            redirectAttributes.addFlashAttribute("deletionUnsuccessMsg", e.getMessage());
        } catch (BookDeletionException e) {
            redirectAttributes.addFlashAttribute("deletionUnsuccessMsg", e.getMessage());
        } catch (Exception e) {
            throw e;
        }
        return "redirect:/books";
    }
}
