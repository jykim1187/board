package com.example.board.common.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public String entityNotFound(EntityNotFoundException e){
        return null;
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public String illegalArguments(){
        return null;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String vaildException(MethodArgumentNotValidException e){
        return null;
    }

}
