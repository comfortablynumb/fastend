package com.berugo.fastend.schema.fieldtype;

import org.springframework.stereotype.Component;

@Component
public class StringFieldType extends AbstractFieldType {
    public static final String FIELD_TYPE_EXTERNAL_ID = "string";

    @Override
    public String getExternalId() {
        return FIELD_TYPE_EXTERNAL_ID;
    }
}
