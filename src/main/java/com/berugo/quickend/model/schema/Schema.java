package com.berugo.quickend.model.schema;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@SuperBuilder
@NoArgsConstructor
public class Schema {
    @Builder.Default
    private List<ObjectValidation> validations = new ArrayList<>();

    @Builder.Default
    private Map<String, Field> fields = new HashMap<>();


    public Schema addField(final Field field) {
        this.fields.put(field.getName(), field);

        return this;
    }

    public Field getField(final String fieldName) {
        return this.fields.getOrDefault(fieldName, null);
    }
}
