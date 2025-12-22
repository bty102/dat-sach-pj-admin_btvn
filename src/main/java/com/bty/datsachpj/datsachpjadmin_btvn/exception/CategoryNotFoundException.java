package com.bty.datsachpj.datsachpjadmin_btvn.exception;

/*
    Ngoai le nay the hien cho viec khong the tim thay category.
*/
public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
