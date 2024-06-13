package com.airbnb.exception;

import com.airbnb.payload.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ExceptionHandlerClass extends RuntimeException{

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<?> resourceNotFound(ResourceNotFound resourceNotFound, WebRequest webRequest){
        ErrorDto dto = new ErrorDto(resourceNotFound.getMessage(),new Date(),webRequest.getDescription(false));
        return new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> GlobalExceptionHandler(Exception exception, WebRequest webRequest){
        ErrorDto dto = new ErrorDto(exception.getMessage(),new Date(), webRequest.getDescription(false));
        return new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
