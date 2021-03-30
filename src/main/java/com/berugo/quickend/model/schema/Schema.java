package com.berugo.quickend.model.schema;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Schema {
    private List<ObjectValidation> validations;

    private Map<String, Field> fields;
}
