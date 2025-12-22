package com.bty.datsachpj.datsachpjadmin_btvn.exception;

/*
    Ngoai le nay the hien cho viec xoa category that bai.
*/
public class CategoryDeletionException extends RuntimeException {
    public CategoryDeletionException(String message) {
        super(message);
    }
}
