package com.bty.datsachpj.datsachpjadmin_btvn.service;

import com.bty.datsachpj.datsachpjadmin_btvn.entity.Bill;
import com.bty.datsachpj.datsachpjadmin_btvn.entity.BillDetail;
import com.bty.datsachpj.datsachpjadmin_btvn.exception.BillDetailNotFoundException;
import com.bty.datsachpj.datsachpjadmin_btvn.exception.BillDetailPaymentConfirmationException;
import com.bty.datsachpj.datsachpjadmin_btvn.repository.BillDetailRepository;
import com.bty.datsachpj.datsachpjadmin_btvn.repository.BillRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BillDetailService {

    final BillDetailRepository billDetailRepository;
    final BillRepository billRepository;

    public void confirmPayment(Long billDetailId) {
        BillDetail billDetail = billDetailRepository.findById(billDetailId)
                .orElseThrow(() -> {throw new BillDetailPaymentConfirmationException("Không tìm thấy chi tiết hóa đơn");
                });
        billDetail.setIsPaid(true);
        billDetailRepository.save(billDetail);
        List<BillDetail> billDetails = billDetailRepository.findAllByBill_Id(billDetail.getBill().getId());
        boolean paidAll = true;
        for(BillDetail x : billDetails) {
            if(!x.getIsPaid()) {
                paidAll = false;
                break;
            }
        }
        if(paidAll) {
            Bill bill = billDetail.getBill();
            bill.setIsPaid(true);
            billRepository.save(bill);
        }
    }

    public BillDetail getBillDetailById(Long id) {
        BillDetail billDetail = billDetailRepository.findById(id)
                .orElseThrow(() -> {throw  new BillDetailNotFoundException(("Không tìm thấy chi tiết hóa đơn"));
                });
        return billDetail;
    }
}
