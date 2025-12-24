package com.bty.datsachpj.datsachpjadmin_btvn.repository;

import com.bty.datsachpj.datsachpjadmin_btvn.entity.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail, Long> {
    List<BillDetail> findAllByBill_Id(Long billId);
}
