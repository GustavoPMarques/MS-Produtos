package com.github.cidarosa.ms.produto.exceptions.handler;


import com.github.cidarosa.ms.produto.exceptions.ResourceNotFoundException;
import com.github.cidarosa.ms.produto.exceptions.dto.CustomErrorDTO;
import com.github.cidarosa.ms.produto.exceptions.dto.ValidationErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomErrorDTO> handleResourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomErrorDTO errorDTO = new CustomErrorDTO(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(errorDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorDTO> methodArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpServletRequest request) {

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        ValidationErrorDTO errorDTO =
                new ValidationErrorDTO(Instant.now(),
                        status.value(),
                        "Dados inválidos",
                        request.getRequestURI());

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorDTO.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(errorDTO);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomErrorDTO> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpServletRequest request){

        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomErrorDTO errorDTO = new CustomErrorDTO(Instant.now(), status.value(), "Requisição inválida A", request.getRequestURI());
        return ResponseEntity.status(status).body(errorDTO);

    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CustomErrorDTO> handleTypeMismatch(MethodArgumentTypeMismatchException e, HttpServletRequest request){

        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomErrorDTO errorDTO = new CustomErrorDTO(Instant.now(), status.value(), "Requisição inválida B", request.getRequestURI());
        return ResponseEntity.status(status).body(errorDTO);

    }



}
