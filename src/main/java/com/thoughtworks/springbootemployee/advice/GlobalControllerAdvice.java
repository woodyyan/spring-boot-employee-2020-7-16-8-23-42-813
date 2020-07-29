package com.thoughtworks.springbootemployee.advice;

import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({EmployeeNotFoundException.class})
    public void handleNotFound(EmployeeNotFoundException exception) {
    }
}
