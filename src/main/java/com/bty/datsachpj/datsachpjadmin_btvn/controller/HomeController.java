package com.bty.datsachpj.datsachpjadmin_btvn.controller;

import com.bty.datsachpj.datsachpjadmin_btvn.entity.Book;
import com.bty.datsachpj.datsachpjadmin_btvn.service.BillService;
import com.bty.datsachpj.datsachpjadmin_btvn.service.BookService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HomeController {

    final BillService billService;
    final BookService bookService;

    @GetMapping(path = {"/", "/home"})
    public String home(Model model) {
        long revenueForToday = billService.getRevenuesInDay(LocalDate.now());
        model.addAttribute("revenueForToday", revenueForToday);
        List<Book> top10BestSellingBooks = bookService.top10BestSellingBooks();
        model.addAttribute("top10BestSellingBooks", top10BestSellingBooks);
        return "home";
    }
}
