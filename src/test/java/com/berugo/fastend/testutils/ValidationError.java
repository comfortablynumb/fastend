package com.berugo.fastend.testutils;

public class ValidationError {
    private final String field;

    private final String errorType;


    public ValidationError(final String field, final String errorType) {
        this.field = field;
        this.errorType = errorType;
    }

    public String getField() {
        return field;
    }

    public String getErrorType() {
        return errorType;
    }
}
