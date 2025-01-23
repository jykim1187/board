package com.example.board.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public String entityNotFound(EntityNotFoundException e, Model model){
      model.addAttribute("errorMessage", e.getMessage());
      return "common/error_page";
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public String illegalArguments(IllegalArgumentException e ,Model model){
        model.addAttribute("errorMessage", e.getMessage());
        return "common/error_page";
    }

    @ExceptionHandler(BindException.class)
    public String vaildException(BindException e,Model model){
        model.addAttribute("errorMessage", e.getMessage());
        return "common/error_page";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String vaildException(MethodArgumentNotValidException e,Model model){
        model.addAttribute("errorMessage", e.getMessage());
        return "common/error_page";
    }

}
