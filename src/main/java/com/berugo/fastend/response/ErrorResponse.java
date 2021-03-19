package com.berugo.fastend.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class ErrorResponse {
    public static final String VALIDATION_ERROR_CODE = "validation_errors";
    public static final String VALIDATION_ERROR_MESSAGE = "There were some validation errors while processing your request.";


    private String code;

    private String message;

    private List<Error> errors = new ArrayList<>();


    public void addError(
        final String field,
        final String errorType,
        final String message,
        final Map<String, Object> data
    ) {
        this.errors.add(newError(field, errorType, message, data));
    }

    public static ErrorResponse newValidationErrorResponse() {
        final ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setCode(VALIDATION_ERROR_CODE);
        errorResponse.setMessage(VALIDATION_ERROR_MESSAGE);

        return errorResponse;
    }

    public static Error newError(
        final String field,
        final String errorType,
        final String message,
        final Map<String, Object> data
    ) {
        final Error error = new Error();

        error.setField(field);
        error.setErrorType(errorType);
        error.setMessage(message);
        error.setData(data);

        return error;
    }

    @Data
    public static class Error {
        private String field;

        private String errorType;

        private String message;

        private Map<String, Object> data;
    }
}
