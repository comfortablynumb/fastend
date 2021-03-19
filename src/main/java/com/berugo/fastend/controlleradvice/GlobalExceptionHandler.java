package com.berugo.fastend.controlleradvice;

import com.berugo.fastend.response.ErrorResponse;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { RepositoryConstraintViolationException.class })
    protected ResponseEntity<Object> handleRepositoryConstraintViolation(
        RepositoryConstraintViolationException exception,
        WebRequest request
    ) {
        final ErrorResponse errorResponse = ErrorResponse.newValidationErrorResponse();

        for (final ObjectError error : exception.getErrors().getGlobalErrors()) {
            errorResponse.addError(null, error.getCode(), error.getDefaultMessage(), null);
        }

        for (final FieldError error : exception.getErrors().getFieldErrors()) {
            errorResponse.addError(error.getField(), error.getCode(), error.getDefaultMessage(), null);
        }

        return handleExceptionInternal(
            exception,
            errorResponse,
            new HttpHeaders(),
            HttpStatus.BAD_REQUEST,
            request
        );
    }
}
