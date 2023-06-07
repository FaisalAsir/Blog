package com.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> fieldNotVallid(MethodArgumentNotValidException e) {
        List<String> error = e.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).collect(Collectors.toList());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
