package com.bty.datsachpj.datsachpjadmin_btvn.exception;

// the hien viec khong tim thay sach
public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message) {
        super(message);
    }
}
