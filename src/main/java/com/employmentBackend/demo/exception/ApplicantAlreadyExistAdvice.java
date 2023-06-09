package com.employmentBackend.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApplicantAlreadyExistAdvice {

    @ResponseBody
    @ExceptionHandler(ApplicantAlreadyExistException.class)
    @ResponseStatus(HttpStatus.FOUND)

    public Map<String, String> exceptionHandler(ApplicantAlreadyExistException e){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", e.getMessage());
        return errorMap;
    }
}
