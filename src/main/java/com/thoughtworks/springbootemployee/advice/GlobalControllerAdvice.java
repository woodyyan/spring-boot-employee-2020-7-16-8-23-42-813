package com.thoughtworks.springbootemployee.advice;

import com.mongodb.MongoException;
import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({EmployeeNotFoundException.class, CompanyNotFoundException.class})
    public ErrorResponse handleNotFound(EmployeeNotFoundException exception) {
        return new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.name());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({MongoException.class})
    public ErrorResponse handleMongoException(MongoException exception) {
        return new ErrorResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.name());
    }
}
