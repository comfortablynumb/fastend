package com.berugo.quickend.schema.fieldtype.validator;

import org.springframework.stereotype.Component;

@Component
public class BooleanFieldTypeValidator extends AbstractFieldTypeValidator {
    public static final String FIELD_TYPE_EXTERNAL_ID = "boolean";


    @Override
    public boolean isValidValue(final Object value) {
        return value instanceof Boolean;
    }

    @Override
    public String getExternalId() {
        return FIELD_TYPE_EXTERNAL_ID;
    }
}
