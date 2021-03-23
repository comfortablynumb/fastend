package com.berugo.fastend.service;

import com.berugo.fastend.schema.fieldtype.AbstractFieldType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FieldTypeService {
    private final Map<String, AbstractFieldType> availableFieldTypes = new HashMap<>();


    public FieldTypeService(@Autowired final List<AbstractFieldType> fieldTypes) {
        for (final AbstractFieldType fieldType : fieldTypes) {
            availableFieldTypes.put(fieldType.getExternalId(), fieldType);
        }
    }

    public AbstractFieldType getFieldType(final String externalId) {
        return this.availableFieldTypes.getOrDefault(externalId, null);
    }
}
