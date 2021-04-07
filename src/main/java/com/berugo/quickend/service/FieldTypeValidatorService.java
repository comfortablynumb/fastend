package com.berugo.quickend.service;

import com.berugo.quickend.schema.fieldtype.validator.AbstractFieldTypeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FieldTypeValidatorService {
    private final Map<String, AbstractFieldTypeValidator> availableFieldTypesValidators = new HashMap<>();


    public FieldTypeValidatorService(@Autowired final List<AbstractFieldTypeValidator> fieldTypes) {
        for (final AbstractFieldTypeValidator fieldType : fieldTypes) {
            availableFieldTypesValidators.put(fieldType.getExternalId(), fieldType);
        }
    }

    public AbstractFieldTypeValidator getFieldTypeValidator(final String externalId) {
        return this.availableFieldTypesValidators.getOrDefault(externalId, null);
    }
}
