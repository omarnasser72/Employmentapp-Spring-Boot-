package com.employmentBackend.demo.exception;

public class AdminAuthenticateException extends RuntimeException {
    public AdminAuthenticateException() {
        super("wrong email or password");
    }
}
