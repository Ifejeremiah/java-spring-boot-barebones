package com.ifejeremiah.web.advice;

import com.ifejeremiah.exception.DuplicateEntityException;
import com.ifejeremiah.exception.NotFoundException;
import com.ifejeremiah.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public Response<NotFoundException> handleNotFoundException(NotFoundException e) {
        return new Response<NotFoundException>(e.getMessage(), String.valueOf(HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response<List<String>> handleValidationException(MethodArgumentNotValidException e) {
        List<String> errors = new ArrayList<String>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        return new Response<List<String>>(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Bad Request Exception", errors);
    }

    @ExceptionHandler(DuplicateEntityException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ResponseBody
    public Response<DuplicateEntityException> handleDuplicateException(DuplicateEntityException e) {
        return new Response<DuplicateEntityException>(e.getMessage(), String.valueOf(HttpStatus.CONFLICT.value()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Response<Exception> handleException(Exception e) {
        log.error("Server Error", e);
        return new Response<Exception>(e.getMessage(), String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}
