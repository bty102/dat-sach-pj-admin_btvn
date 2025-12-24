package com.bty.datsachpj.datsachpjadmin_btvn.service;

import com.bty.datsachpj.datsachpjadmin_btvn.entity.Bill;
import com.bty.datsachpj.datsachpjadmin_btvn.entity.BillDetail;
import com.bty.datsachpj.datsachpjadmin_btvn.exception.BillNotFoundException;
import com.bty.datsachpj.datsachpjadmin_btvn.exception.BillPaymentConfirmationException;
import com.bty.datsachpj.datsachpjadmin_btvn.repository.BillDetailRepository;
import com.bty.datsachpj.datsachpjadmin_btvn.repository.BillRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BillService {

    final BillRepository billRepository;
    final BillDetailRepository billDetailRepository;

    /*
        Tham số:
            - pageNumber >= 1
            - pageSize >= 1
    */
    public Page<Bill> findBillsByIsPaid(Boolean isPaid, int pageNumber, int pageSize) {
        if(pageNumber < 1) pageNumber = 1;
        if(pageSize < 1) pageSize = 1;

        Sort sort = Sort.by("purchaseDate").descending();
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, sort);
        return billRepository.findByIsPaid(isPaid, pageable);
    }

    public void confirmPayment(Long billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> {throw new BillPaymentConfirmationException("Không tìm thấy hóa đơn với có mã hóa đơn này");});
        if(!bill.getIsPaid()) {
            bill.setIsPaid(true);
            List<BillDetail> billDetails = bill.getBillDetails();
            for(BillDetail billDetail : billDetails) {
                billDetail.setIsPaid(true);
            }
            billRepository.save(bill);
        }
    }

    public Bill getBillById(Long id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> {throw new BillNotFoundException("không tìm thấy hóa đơn");});
        return bill;
    }

    public long getRevenuesInDay(LocalDate day) {
        List<Bill> bills = billRepository.findAll();
        long sum = 0;
        for(Bill bill : bills) {
            if(day.equals(bill.getPurchaseDate().toLocalDate())) {
                sum += bill.getTotalBill();
            }
        }
        return sum;
    }
}
