package com.berugo.quickend.schema.fieldtype.validator;

import lombok.NonNull;
import org.springframework.validation.Errors;

public abstract class AbstractFieldTypeValidator {
    public static final String ERROR_INVALID_VALUE_TYPE = "invalid_value_type";


    public boolean validateValue(@NonNull final String field, final Object value, @NonNull final Errors errors) {
        if (!this.isValidValue(value)) {
            errors.rejectValue(field, ERROR_INVALID_VALUE_TYPE);

            return false;
        }

        return true;
    }

    public abstract boolean isValidValue(final Object value);

    public abstract String getExternalId();
}
