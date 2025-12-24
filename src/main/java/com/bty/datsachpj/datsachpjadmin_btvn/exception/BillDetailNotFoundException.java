package com.bty.datsachpj.datsachpjadmin_btvn.exception;

// the hien viec khong tim thay chi tiet hoa don
public class BillDetailNotFoundException extends RuntimeException {
    public BillDetailNotFoundException(String message) {
        super(message);
    }
}
