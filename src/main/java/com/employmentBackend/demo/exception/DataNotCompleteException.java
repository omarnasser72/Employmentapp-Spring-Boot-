package com.employmentBackend.demo.exception;

public class DataNotCompleteException extends RuntimeException{
    public DataNotCompleteException(){
        super("data isn't complete please complete your data before sending it again.");
    }

}
