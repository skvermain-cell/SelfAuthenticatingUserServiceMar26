package com.scaler.selfauthenticatinguserservicemar26.advices;

import com.scaler.selfauthenticatinguserservicemar26.dtos.ExceptionDto;
import com.scaler.selfauthenticatinguserservicemar26.exceptions.InvalidTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ExceptionDto> handleInvalidTokenException() {

        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage("Unauthorized Access! Please try with correct credentials.");
        return new ResponseEntity<>(
                exceptionDto,
                HttpStatus.UNAUTHORIZED
        );
    }
}
