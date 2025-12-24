package com.bty.datsachpj.datsachpjadmin_btvn.controller;

import com.bty.datsachpj.datsachpjadmin_btvn.entity.Bill;
import com.bty.datsachpj.datsachpjadmin_btvn.exception.BillNotFoundException;
import com.bty.datsachpj.datsachpjadmin_btvn.exception.BillPaymentConfirmationException;
import com.bty.datsachpj.datsachpjadmin_btvn.service.BillService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BillController {

    final BillService billService;

    @GetMapping("/bills")
    public String getBills(@RequestParam(name = "paid", required = true) boolean paid,
                           @RequestParam(name = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                           Model model) {
        int pageSize = 5;
        Page<Bill> bills = null;
        bills = billService.findBillsByIsPaid(paid, pageNumber, pageSize);
        model.addAttribute("bills", bills.getContent());
        int totalPages = bills.getTotalPages();
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPageNumber", pageNumber);
        model.addAttribute("paid", paid);
        return "bills";
    }

    @GetMapping("/bills/confirmPayment/{billId}")
    public String confirmPayment(@PathVariable(name = "billId") Long billId,
                                 RedirectAttributes redirectAttributes) {
        try {
            billService.confirmPayment(billId);
            redirectAttributes.addFlashAttribute("confirmSuccessdMsg", "Xác nhận hóa đơn đã thanh toán thành công");
            return "redirect:/bills?paid=true";
        } catch (BillPaymentConfirmationException e) {
            redirectAttributes.addFlashAttribute("confirmFailedMsg", e.getMessage());
            return "redirect:/bills?paid=false";
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/bills/details/{billId}")
    public String showDetails(@PathVariable(name = "billId") Long id, Model model) {
        try {
            Bill bill = billService.getBillById(id);
            model.addAttribute("bill", bill);
            return "bill-details";
        } catch (BillNotFoundException e) {
            model.addAttribute("billNotfoundMsg", e.getMessage());
            return "bill-details";
        } catch (Exception e) {
            throw e;
        }
    }
}
