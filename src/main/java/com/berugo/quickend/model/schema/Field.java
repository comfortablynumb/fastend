package com.berugo.quickend.model.schema;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
public class Field {
    private String name;

    private FieldType type;

    @Builder.Default
    private List<FieldValidation> validations = new ArrayList<>();
}
