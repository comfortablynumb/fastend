package com.berugo.quickend.schema.fieldtype;

import org.springframework.stereotype.Component;

@Component
public class BooleanFieldType extends AbstractFieldType {
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
