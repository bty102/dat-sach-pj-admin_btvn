package com.bty.datsachpj.datsachpjadmin_btvn.repository;

import com.bty.datsachpj.datsachpjadmin_btvn.entity.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    Page<Bill> findByIsPaid(Boolean isPaid, Pageable pageable);
}
