package com.bty.datsachpj.datsachpjadmin_btvn.exception;

// the hien viec khong tim thay hoa don
public class BillNotFoundException extends RuntimeException {
    public BillNotFoundException(String message) {
        super(message);
    }
}
