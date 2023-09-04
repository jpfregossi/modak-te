package com.modak.te.notificationservice.exception;

public class QuotaExcededException extends RuntimeException{
    public QuotaExcededException(String message) {
        super(message);
    }
}
