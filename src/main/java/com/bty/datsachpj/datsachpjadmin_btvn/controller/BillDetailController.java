package com.bty.datsachpj.datsachpjadmin_btvn.controller;

import com.bty.datsachpj.datsachpjadmin_btvn.entity.BillDetail;
import com.bty.datsachpj.datsachpjadmin_btvn.exception.BillDetailPaymentConfirmationException;
import com.bty.datsachpj.datsachpjadmin_btvn.service.BillDetailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BillDetailController {

    final BillDetailService billDetailService;

    @GetMapping("/billdetails/confirmPayment/{billDetailId}")
    public String confirmPayment(@PathVariable(name = "billDetailId") Long billDetailId,
                                 RedirectAttributes redirectAttributes) {
        billDetailService.confirmPayment(billDetailId);
        BillDetail billDetail = billDetailService.getBillDetailById(billDetailId);
        redirectAttributes.addFlashAttribute("confirmPaymentSuccessMsg", "Xác nhận đã thanh toán thành công");
        return "redirect:/bills/details/" + billDetail.getBill().getId();
    }
}
