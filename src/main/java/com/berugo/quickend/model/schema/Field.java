package com.berugo.quickend.model.schema;

import lombok.Data;

import java.util.List;

@Data
public class Field {
    private String name;

    private FieldType type;

    private List<FieldValidation> validations;
}
